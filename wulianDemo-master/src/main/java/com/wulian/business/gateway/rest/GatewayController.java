package com.wulian.business.gateway.rest;

import basic.common.core.id.IdUtil;
import com.wulian.business.gateway.model.Gateway;
import com.wulian.business.gateway.service.GatewayService;
import com.wulian.common.page.Page;
import com.wulian.common.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  GatewayController
 *
 * @version : Ver 1.0
 * @date	: 2018-7-25
 */
@RestController
@Api(value = "GatewayController", description = "网关相关")
@RequestMapping(value = "/web/gateway")
public class GatewayController { 
	
	   @InitBinder
	    public void initBinder(WebDataBinder binder) {
	        DateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	        dateFormat.setLenient(true);   
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	    }  
	
	@Autowired
	private GatewayService gatewayService;
	
	@ApiOperation(value = "新增网关", notes = "新增网关")
	@RequestMapping(value = "/insertGateway", method = RequestMethod.POST)
	public Response<Integer> insertGateway(@RequestBody Gateway gateway){
		gateway.setId(IdUtil.getId()+"");
    	
		return new Response<Integer>(gatewayService.insertGateway(gateway));
	}

	@ApiOperation(value = "根据ID修改网关", notes = "根据ID修改网关")
	@RequestMapping(value = "/updateGatewayById", method = RequestMethod.POST)
	public Response<Integer> updateGatewayById(@RequestBody Gateway gateway){
		return new Response<Integer>(gatewayService.updateGatewayById(gateway));
	}

	@ApiOperation(value = "根据网关ID修改网关密码", notes = "根据网关ID修改网关密码")
	@RequestMapping(value = "/updateGatewayPwdByGwId", method = RequestMethod.POST)
	public Response<Integer> updatePasswordByGwId(@RequestBody Map map){
		return new Response<Integer>(gatewayService.updatePasswordByGwId(map));
	}

	@ApiOperation(value = "根据网关ID修改网关名称", notes = "根据网关ID修改网关名称")
	@RequestMapping(value = "/updateGwNameByGwId", method = RequestMethod.POST)
	public Response<Integer> updateGwNameByGwId(@RequestBody Gateway gateway){
		return new Response<Integer>(gatewayService.updateGwNameByGwId(gateway));
	}

	@ApiOperation(value = "根据ID删除网关", notes = "根据ID删除网关")
	@RequestMapping(value = "/deleteGatewayById", method = RequestMethod.GET)
	public Response<Integer> deleteGatewayById( @RequestParam String id  ){
		return new Response<Integer>(gatewayService.deleteGatewayById(  id  ));
	}
	@ApiOperation(value = "根据ID获取网关", notes = "根据ID获取网关")
	@RequestMapping(value = "/getGatewayById", method = RequestMethod.GET)
	public Response<Gateway> getGatewayById( @RequestParam String id  ){
		return new Response<Gateway>(gatewayService.getGatewayById(  id  ));
	}
 
	@ApiOperation(value = "根据对象获取网关", notes = "根据对象获取网关")
	@RequestMapping(value = "/getGateways", method = RequestMethod.POST)
	public  Response<List<Gateway>> getGateways( @RequestBody Gateway gateway){
		return new Response<List<Gateway>>(gatewayService.getGateways(gateway));

 	}

	@ApiOperation(value = "根据对象分页获取网关", notes = "根据对象分页获取网关")
	@RequestMapping(value = "/getGatewaysForPage", method = RequestMethod.POST)
	public  Response<Page<Gateway>> getGatewaysForPage(@RequestBody Gateway gateway,
			@RequestParam(value="page", defaultValue="1")  int page,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize){
		Pageable pageable = new PageRequest(page, pageSize);
		Page<Gateway> pageDto= gatewayService.getGatewaysForPage(gateway,pageable);
		return new Response<Page<Gateway>>(pageDto);
 	}
}
