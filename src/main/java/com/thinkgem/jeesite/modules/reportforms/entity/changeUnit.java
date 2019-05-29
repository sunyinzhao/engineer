package com.thinkgem.jeesite.modules.reportforms.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
//person_record
public class changeUnit extends DataEntity<changeUnit>{
	private static final long serialVersionUID = 1L;
	
	private String enterprisename;     //姓名
	private String workerSex;          //性别
	private String registerCertificateNum;                  //执业登记证书编号
	private String city;                //所属协会
	private String type;               //登记类型
	private String oldName;            //变更前单位名称
	private String newName;            //变更后单位名称
	private String date;                 //上报日期
	private String expertName;           //终审专家
	private String result;               //终审结论
	private String lastView;             //终审意见
	private String expertView;          //反馈意见
	private String feedBackExpert;       //复议专家
	private String MMMexpertName;          //专家姓名
	public String getMMMexpertName() {
		return MMMexpertName;
	}

	public void setMMMexpertName(String MMMexpertName) {
		this.MMMexpertName = MMMexpertName;
	}
	private String fResult;                   //复议结论
	//private String reconsiderConclusion;  //复议结论
	private String reconsiderAdvice;        //复议意见
	private String officeId;                //sys_user表中的office_id
	private String batchNo;                    //批次号
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getfResult() {
		return fResult;
	}
	public void setfResult(String fResult) {
		this.fResult = fResult;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getEnterprisename() {
		return enterprisename;
	}
	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}
	public String getWorkerSex() {
		return workerSex;
	}
	public void setWorkerSex(String workerSex) {
		this.workerSex = workerSex;
	}

	public String getRegisterCertificateNum() {
		return registerCertificateNum;
	}

	public void setRegisterCertificateNum(String registerCertificateNum) {
		this.registerCertificateNum = registerCertificateNum;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getLastView() {
		return lastView;
	}
	public void setLastView(String lastView) {
		this.lastView = lastView;
	}
	public String getExpertView() {
		return expertView;
	}
	public void setExpertView(String expertView) {
		this.expertView = expertView;
	}
	public String getFeedBackExpert() {
		return feedBackExpert;
	}
	public void setFeedBackExpert(String feedBackExpert) {
		this.feedBackExpert = feedBackExpert;
	}


	public String getReconsiderAdvice() {
		return reconsiderAdvice;
	}
	public void setReconsiderAdvice(String reconsiderAdvice) {
		this.reconsiderAdvice = reconsiderAdvice;
	}
	private String index1;
	@ExcelField(title="序号", align=2, sort=4)
	public String getIndex1() {
		return index1;
	}
	public void setIndex1(String index1) {
		this.index1 = index1;
	}


	


}
