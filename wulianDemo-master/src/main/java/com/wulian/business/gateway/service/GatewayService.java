package com.wulian.business.gateway.service;

import com.wulian.business.gateway.model.Gateway;
import com.wulian.common.page.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * GatewayService
 *
 * @version : Ver 1.0
 * @date    : 2018-7-25
 */
public interface GatewayService {

    int insertGateway(Gateway gateway);

    int insertGatewayBatch(List<Gateway> list);

    int updateGatewayById(Gateway gateway);

    int updateMessagecodeByGwId(Gateway gateway);

    int updatePasswordByGwId(Map map);

    int updateGwNameByGwId(Gateway gateway);

    int deleteGatewayById(String id);

    Gateway getGatewayById(String id);

    List<Gateway> getGateways(Gateway gateway);

    Page<Gateway> getGatewaysForPage(Gateway gateway, Pageable pageable);
}
