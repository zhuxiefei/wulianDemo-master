package com.wulian.business.gateway.dao;

import com.wulian.business.gateway.model.Gateway;
import com.wulian.common.mybatis.QueryCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * GatewayDao 网关
 *
 * @version : Ver 1.0
 * @date    : 2018-7-25
 */
@Repository
public interface GatewayDao {

    int insertGateway(Gateway gateway);

    int insertGatewayBatch(List<Gateway> list);

    int updateGatewayById(Gateway gateway);

    int updatePasswordByGwId(Gateway gateway);

    int updateMessagecodeByGwId(Gateway gateway);

    int updateGwNameByGwId(Gateway gateway);

    int deleteGatewayById(@Param("id") String id);

    Gateway getGatewayById(@Param("id") String id);

    List<Gateway> getGateways(@Param("gateway") Gateway gateway);

    List<Gateway> getGatewaysByConditions(@Param("conditions") List<QueryCondition> conditions);

}
