package com.wulian.business.wlink;

import com.alibaba.fastjson.JSONObject;
import com.wulian.business.gateway.model.Count;
import com.wulian.business.gateway.model.Gateway;
import com.wulian.business.wlink.http.AccessTokenUtil;
import com.wulian.business.wlink.mqtt.MqttsClientSubMain;
import com.wulian.common.constant.Constant;
import com.wulian.common.exception.GlobalException;
import com.wulian.common.utils.GsonUtil;
import com.wulian.common.utils.HttpClientUtil;
import com.wulian.common.utils.MD5;
import com.wulian.common.utils.SecretUtil;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Wlink {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Wlink.class);
    @Autowired
    private Count count;

    public Gateway Bonding(Gateway gateway) {
        //获取accessToken
        String accessToken = AccessTokenUtil.getAccessToken();
        //输入校验
        int i = 0;
        String openId = gateway.getOpenId();
        if (openId == null || openId.equals("")) {
            throw new GlobalException("GW0001");
        }
        String gwId = gateway.getGwId();
        if (gwId == null || gwId.equals("")) {
            throw new GlobalException("GW0002");
        } else if (gwId.length() != 12) {
            throw new GlobalException("GW0003");
        }
        String gwPassword = gateway.getGwPassword();
        if (gwPassword == null || gwPassword.equals("")) {
            throw new GlobalException("GW0004");
        }
        //MD5加密密码
        gwPassword = MD5.Md5(gwPassword);
        //通过HTTP请求绑定设备
        Map map = new HashMap<>();
        map.put("openId", openId);
        map.put("deviceId", gwId);
        map.put("devicePasswd", gwPassword);
        map.put("deviceType", "GW01");
        try {
            String msg = SecretUtil.encrypt(Constant.SECRETKEY, GsonUtil.object2Gson(map));
            String res = HttpClientUtil.post(
                    "https://iotsh.wuliancloud.com/iot/v2/partners/devices?accessToken=" + accessToken,
                    msg);
            String decryptMsg = SecretUtil.decrypt(res);
            System.out.println(decryptMsg);
            JSONObject json = JSONObject.parseObject(decryptMsg);
            String resultDesc = json.get("resultDesc") == null ? "" : json.get("resultDesc").toString();
            if (resultDesc.equals("success")) {
                gateway.setGwId(gateway.getGwId().trim());
                gateway.setOpenId(gateway.getOpenId().trim());
                gateway.setGwName("网关");
                gateway.setGwType("GW01");
                gateway.setGwPassword(gateway.getGwPassword().trim());
                gateway.setMessageCode("未上线");

            } else if (resultDesc.equals("device already bound.")) {
                i = 2;
            } else if (resultDesc.equals("device not exist.")) {
                i = 3;
            } else if (resultDesc.equals("device password incorrect.")) {
                i = 4;
            }

        } catch (Exception e) {
            log.error("异常", e);
        }
        if (i == 2) {
            throw new GlobalException("GW0005");
        }
        if (i == 3) {
            throw new GlobalException("GW0014");
        }
        if (i == 4) {
            throw new GlobalException("GW0015");
        }
        return gateway;
    }

    //修改网关密码
    public Gateway updatePasswordByGwId(String gwId, String oldPassword, String newPassword) {

        Gateway gateway = new Gateway();
        Map map = new HashMap<>();
        map.put("cmd", "509");
        map.put("gwID", gwId);
        map.put("gwPwd", MD5.Md5(oldPassword));
        map.put("data", MD5.Md5(newPassword));
        String gson = GsonUtil.object2Gson(map);
        try {
            gson = SecretUtil.encrypt(Constant.SECRETKEY, gson);
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(gson.getBytes());
            MqttsClientSubMain mqttsClientSubMain = new MqttsClientSubMain();
            //openId为硬编码，后期要和账号联系在一起
            mqttsClientSubMain.initMqtt().publish("wl/partner/" + Constant.PARTNERID + "/123456/req", mqttMessage);
            Thread.sleep(1000);
        } catch (Exception e) {
            log.error("修改网关密码时发布主题失败!", e);
        }
        if (count.getI() == 1) {
            count.setI(0);
            throw new GlobalException("GW0010");
        }
        gateway.setGwId(gwId);
        gateway.setGwPassword(newPassword);
        return gateway;

    }

    //修改网关名称
    public Gateway updateGwNameByGwId(Gateway gateway) {
        int i = 0;
        String accessToken = AccessTokenUtil.getAccessToken();
        Map map = new HashMap<>();
        map.put("openId", gateway.getOpenId());
        map.put("deviceId", gateway.getGwId());
        map.put("deviceName", gateway.getGwName());
        try {
            String msg = SecretUtil.encrypt(Constant.SECRETKEY, GsonUtil.object2Gson(map));
            String res = HttpClientUtil.post(
                    "https://iotsh.wuliancloud.com/iot/v2/partners/devices/name?accessToken=" + accessToken,
                    msg);
            String decryptMsg = SecretUtil.decrypt(res);
            System.out.println(decryptMsg);
            JSONObject json = JSONObject.parseObject(decryptMsg);
            String resultDesc = json.get("resultDesc") == null ? "" : json.get("resultDesc").toString();
            if (!resultDesc.equals("success")) {
                i = 1;
            }
            //这个时间用来订阅主题改变count.i的值
            Thread.sleep(1000);

        } catch (Exception e) {
            log.error("修改网关名称出现异常", e);
        }

        if (i == 1) {
            throw new GlobalException("GW0020");
        }
        if(count.getI()!=2){
            throw new GlobalException("GW0021");
        }
        return gateway;
    }

    //设备布防
    public void deploy(){
        int i = 0;

        Map map = new HashMap<>();
        map.put("devID","");
        map.put("gwID","");
        map.put("cmd", "502");
        map.put("mode", 0);
        map.put("endpointNumber", 1);
        map.put("endpointStatus", "0");//0表示撤防
        String gson = GsonUtil.object2Gson(map);
        try {
            gson = SecretUtil.encrypt(Constant.SECRETKEY, gson);
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(gson.getBytes());
            MqttsClientSubMain mqttsClientSubMain = new MqttsClientSubMain();
            //openId为硬编码，后期要和账号联系在一起
            mqttsClientSubMain.initMqtt().publish("wl/partner/" + Constant.PARTNERID + "/123456/req", mqttMessage);
            Thread.sleep(1000);
        } catch (Exception e) {
            log.error("设备布防时发布主题失败!", e);
        }
        if (count.getI() == 1) {
            count.setI(0);
            throw new GlobalException("DEV0001");
        }

    }



}