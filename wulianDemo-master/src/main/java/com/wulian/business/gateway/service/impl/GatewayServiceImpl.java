package com.wulian.business.gateway.service.impl;

import com.github.pagehelper.PageHelper;
import com.wulian.business.gateway.dao.GatewayDao;
import com.wulian.business.gateway.model.Gateway;
import com.wulian.business.gateway.service.GatewayService;
import com.wulian.business.wlink.Wlink;
import com.wulian.common.exception.GlobalException;
import com.wulian.common.page.Page;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * GatewayServiceImpl
 *
 * @version : Ver 1.0
 * @date : 2018-7-25
 */
@Service
public class GatewayServiceImpl implements GatewayService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GatewayServiceImpl.class);

    @Autowired
    private GatewayDao gatewayDao;
    @Autowired
    private Wlink wlink;


    public int insertGateway(Gateway gateway) {
        gateway = wlink.Bonding(gateway);
        int i = gatewayDao.insertGateway(gateway);
        if (i != 1) {
            throw new GlobalException("GW0006");
        }
        return i;
    }

    public int insertGatewayBatch(List<Gateway> list) {
        return gatewayDao.insertGatewayBatch(list);
    }

    public int updateGatewayById(Gateway gateway) {
        return gatewayDao.updateGatewayById(gateway);
    }

    public int updateMessagecodeByGwId(Gateway gateway) {
        return gatewayDao.updateMessagecodeByGwId(gateway);
    }
    //修改网关名称
    public int updateGwNameByGwId(Gateway gateway){
        //输入数据校验
        String gwId=gateway.getGwId()==null?"":gateway.getGwId();
        String openId=gateway.getOpenId()==null?"":gateway.getOpenId();
        String gwName=gateway.getGwName()==null?"":gateway.getGwName();
        if(gwId.equals("")){
            throw new GlobalException("GW0017");
        }
        if(openId.equals("")){
            throw new GlobalException("GW0018");
        }
        if(gwName.equals("")){
            throw new GlobalException("GW0019");
        }
        gateway.setGwId(gwId.trim());
        gateway.setOpenId(openId.trim());
        gateway.setGwName(gwName.trim());
        gateway=wlink.updateGwNameByGwId(gateway);
        return gatewayDao.updateGwNameByGwId(gateway);
    }
//修改密码
    @Override
    public int updatePasswordByGwId(Map map) {
        String gwId=map.get("gwId")==null?"":map.get("gwId").toString();
        String oldPassword=map.get("oldPassword")==null?"":map.get("oldPassword").toString();
        String newPassword=map.get("newPassword")==null?"":map.get("newPassword").toString();
        if(gwId.equals("")){
            throw new GlobalException("GW0011");
        }
        if(oldPassword.equals("")){
            throw new GlobalException("GW0012");
        }
        if(newPassword.equals("")){
            throw new GlobalException("GW0013");
        }
        Gateway gateway=wlink.updatePasswordByGwId(gwId,oldPassword,newPassword);

        int i=gatewayDao.updatePasswordByGwId(gateway);
        if(i<=0){
            throw new GlobalException("GW0016");
        }
        return i;
    }

    public int deleteGatewayById(String id) {
        return gatewayDao.deleteGatewayById(id);
    }

    public Gateway getGatewayById(String id) {
        return gatewayDao.getGatewayById(id);
    }

    public List<Gateway> getGateways(Gateway gateway) {
        return gatewayDao.getGateways(gateway);

    }

    public Page<Gateway> getGatewaysForPage(Gateway gateway, Pageable pageable) {

        long count = gatewayDao.getGateways(gateway).size();
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<Gateway> gateways = gatewayDao.getGateways(gateway);

        Page<Gateway> pageDto = new Page<Gateway>();
        pageDto.setPage(pageable.getPageNumber());
        pageDto.setPageSize(pageable.getPageSize());
        if (gateways != null) {
            pageDto.setRows(gateways);
            pageDto.setTotal(count);
        } else {
            pageDto.setRows(new ArrayList<Gateway>());
            pageDto.setTotal(0l);
        }

        return pageDto;
    }
}
