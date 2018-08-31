package com.wulian.business.gateway.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *  Gateway
 *
 * @version : Ver 1.0
 * @date	: 2018-7-25
 */
public class Gateway implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 序号id     
     */	
	private String id;

	/**
     * 网关Id
     */
	private String gwId;

	/**
     * 用户Id
     */
	private String openId;

	/**
     * 网关名称
     */
	private String gwName;

	/**
     * 网关类型
     */
	private String gwType;
	/**
	 * 网关密码
	 */
	private String gwPassword;

	/**
     * 消息码
     */
	private String messageCode;



	/**
     * 创建时间
     */
	private java.util.Date createTime;



	/**
	 * @param id 序号id
	 */
	@ApiModelProperty("序号id")
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return 序号id
	 */
	@ApiModelProperty("序号id")
	public String getId() {
		return this.id;
	}

	/**
	 * @param gwId 网关Id
	 */
	@ApiModelProperty("网关Id")
	public void setGwId(String gwId) {
		this.gwId = gwId;
	}

	/**
	 * @return 网关Id
	 */
	@ApiModelProperty("网关Id")
	public String getGwId() {
		return this.gwId;
	}

	/**
	 * @param openId 用户Id
	 */
	@ApiModelProperty("用户Id")
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @return 用户Id
	 */
	@ApiModelProperty("用户Id")
	public String getOpenId() {
		return this.openId;
	}

	/**
	 * @param gwName 网关名称
	 */
	@ApiModelProperty("网关名称")
	public void setGwName(String gwName) {
		this.gwName = gwName;
	}

	/**
	 * @return 网关名称
	 */
	@ApiModelProperty("网关名称")
	public String getGwName() {
		return this.gwName;
	}

	/**
	 * @param gwType 网关类型
	 */
	@ApiModelProperty("网关类型")
	public void setGwType(String gwType) {
		this.gwType = gwType;
	}

	/**
	 * @return 网关类型
	 */
	@ApiModelProperty("网关类型")
	public String getGwType() {
		return this.gwType;
	}
	@ApiModelProperty("网关密码")
	public String getGwPassword() {
		return gwPassword;
	}
	@ApiModelProperty("网关密码")
	public void setGwPassword(String gwPassword) {
		this.gwPassword = gwPassword;
	}

	/**
	 * @param messageCode 消息码
	 */
	@ApiModelProperty("消息码")
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	/**
	 * @return 消息码
	 */
	@ApiModelProperty("消息码")
	public String getMessageCode() {
		return this.messageCode;
	}
	
	/**
	 * @param createTime 创建时间
	 */
	@ApiModelProperty("创建时间")
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * @return 创建时间
	 */
	@ApiModelProperty("创建时间")
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	 
}
