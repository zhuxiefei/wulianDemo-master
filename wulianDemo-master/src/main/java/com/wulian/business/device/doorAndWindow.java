package com.wulian.business.device;

import com.wulian.business.gateway.model.Count;
import com.wulian.business.wlink.Wlink;
import com.wulian.business.wlink.mqtt.MqttsClientSubMain;
import com.wulian.common.constant.Constant;
import com.wulian.common.exception.GlobalException;
import com.wulian.common.utils.GsonUtil;
import com.wulian.common.utils.SecretUtil;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
* @Description: 门、窗磁探测器
* @Param:
* @return:
*/
public class doorAndWindow {

    @Autowired
    private Count count;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Wlink.class);

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

    //设备撤防

    //设备告警信息上报

    //设备状态信息上报

}