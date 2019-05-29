/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.entity;

import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 股权结构Entity
 * @author xqg
 * @version 2018-05-05
 */
public class EnterpriseStockProportion extends DataEntity<EnterpriseStockProportion> {
	
	private static final long serialVersionUID = 1L;
	private String shareholder;		// 股东名称
	private BigDecimal stockProportion;		// 股权比例
	private String pid;		// 父对象Id
	
	public EnterpriseStockProportion() {
		super();
	}

	public EnterpriseStockProportion(String id){
		super(id);
	}

	@Length(min=0, max=200, message="股东名称长度必须介于 0 和 200 之间")
	public String getShareholder() {
		return shareholder;
	}

	public void setShareholder(String shareholder) {
		this.shareholder = shareholder;
	}
	
	public BigDecimal getStockProportion() {
		return stockProportion;
	}

	public void setStockProportion(BigDecimal stockProportion) {
		this.stockProportion = stockProportion;
	}
	
	@Length(min=0, max=32, message="父对象Id长度必须介于 0 和 32 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
}