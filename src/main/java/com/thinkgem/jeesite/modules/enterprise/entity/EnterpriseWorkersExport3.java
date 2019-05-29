/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 *导出协会的申请单列表
 * @author xqg
 * @version 2018-05-05
 */
public class EnterpriseWorkersExport3 {
	
	private static final long serialVersionUID = 1L;
	private int index;
	private String officeName;
	private String companyName;
	private String name;		// 姓名
	private String certificatesNum;
	private String declareType;
	private String batchStatus; //登记事项批次状态
	private String declareStatus; //登记事项状态
	private String expertResult1;
	private String expertResult2;
	
	public EnterpriseWorkersExport3() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EnterpriseWorkersExport3(EnterpriseWorkers worker) {
		this.index = worker.getIndex();
		this.officeName = worker.getOfficeName();
		this.companyName = worker.getCompanyName();
		this.name = worker.getName();
		this.certificatesNum = worker.getCertificatesNum();
		this.declareType =DictUtils.getDictLabel(worker.getType(), "counselor_type", "");
		this.batchStatus =DictUtils.getDictLabel(worker.getBatchStatus(), "counselor_status", "");
		this.declareStatus =DictUtils.getDictLabel(worker.getDeclareStatus(), "counselor_status", "");
		if("0".equals(worker.getFlag())){
			this.expertResult1 = DictUtils.getDictLabel(worker.getFirstCdeclareResult(),"decaler_result","");
			this.expertResult2 = DictUtils.getDictLabel(worker.getSecondCdeclareResult(),"decaler_result","");
		}
		if("1".equals(worker.getFlag())){
			this.expertResult1 = DictUtils.getDictLabel(worker.getFirstZdeclareResult(),"decaler_result","");
			this.expertResult2 = DictUtils.getDictLabel(worker.getSecondZdeclareResult(),"decaler_result","");
		}
	}
	
	
	
	/**
	 * @return the index
	 */
	@ExcelField(title="序号", align=2, sort=1)
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the officeName
	 */
	@ExcelField(title="预审单位", align=2, sort=2)
	public String getOfficeName() {
		return officeName;
	}

	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	/**
	 * @return the companyName
	 */
	@ExcelField(title="单位名称", align=2, sort=3)
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@ExcelField(title="姓名", align=2, sort=4)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the certificatesNum
	 */
	@ExcelField(title="身份证号", align=2, sort=5)
	public String getCertificatesNum() {
		return certificatesNum;
	}

	/**
	 * @param certificatesNum the certificatesNum to set
	 */
	public void setCertificatesNum(String certificatesNum) {
		this.certificatesNum = certificatesNum;
	}

	/**
	 * @return the declareType
	 */
	@ExcelField(title="登记类型", align=2, sort=6)
	public String getDeclareType() {
		return declareType;
	}

	/**
	 * @param declareType the declareType to set
	 */
	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}


	@ExcelField(title="登记事项批次状态", align=2, sort=7)
	public String getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	
	@ExcelField(title="登记事项状态", align=2, sort=8)
	public String getDeclareStatus() {
		return declareStatus;
	}
	public void setDeclareStatus(String declareStatus) {
		this.declareStatus = declareStatus;
	}
	
	/**
	 * @return the expertResult1
	 */
	@ExcelField(title="专家意见1", align=2, sort=9)
	public String getExpertResult1() {
		return expertResult1;
	}
	
	/**
	 * @param expertResult1 the expertResult1 to set
	 */
	public void setExpertResult1(String expertResult1) {
		this.expertResult1 = expertResult1;
	}
	
	/**
	 * @return the expertResult2
	 */
	@ExcelField(title="专家意见2", align=2, sort=10)
	public String getExpertResult2() {
		return expertResult2;
	}
	
	/**
	 * @param expertResult2 the expertResult2 to set
	 */
	public void setExpertResult2(String expertResult2) {
		this.expertResult2 = expertResult2;
	}
}