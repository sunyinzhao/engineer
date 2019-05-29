package com.thinkgem.jeesite.modules.reportforms.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

public class exportChangeUnit {
	private static final long serialVersionUID = 1L;
    private String index1;
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
	private String MMMexpertname;          //专家姓名
	private String fResult;                   //复议结论
	//private String reconsiderConclusion;  //复议结论
	private String reconsiderAdvice;        //复议意见
	private String officeId;                //sys_user表中的office_id
	private String batchNo;                    //批次号
	
	@ExcelField(title="序号", align=2, sort=1)
	public String getIndex1() {
		return index1;
	}
	public void setIndex1(String index1) {
		this.index1 = index1;
	}
	@ExcelField(title="姓名", align=2, sort=2)
	public String getEnterprisename() {
		return enterprisename;
	}
	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	@ExcelField(title="性别", align=2, sort=3)
	public String getWorkerSex() {
		return workerSex;
	}
	public void setWorkerSex(String workerSex) {
		this.workerSex = workerSex;
	}
	@Length(min=0, max=50, message="职业证书登记编号长度必须介于 0 和 50 之间")
	@ExcelField(title="执业登记证书编号", align=2, sort=4)
	public String getRegisterCertificateNum() {
		return registerCertificateNum;
	}
	public void setRegisterCertificateNum(String registerCertificateNum) {
		this.registerCertificateNum = registerCertificateNum;
	}
	@ExcelField(title="所属协会", align=2, sort=5)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@ExcelField(title="登记类型", align=2, sort=6)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@ExcelField(title="变更前单位名称", align=2, sort=7)
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	@ExcelField(title="变更后单位名称", align=2, sort=8)
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	@ExcelField(title="上报日期", align=2, sort=9)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@ExcelField(title="终审专家", align=2, sort=10)
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	@ExcelField(title="终审结论", align=2, sort=11)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@ExcelField(title="终审意见", align=2, sort=12)
	public String getLastView() {
		return lastView;
	}
	public void setLastView(String lastView) {
		this.lastView = lastView;
	}
	@ExcelField(title="反馈意见", align=2, sort=13)
	public String getExpertView() {
		return expertView;
	}
	public void setExpertView(String expertView) {
		this.expertView = expertView;
	}
	@ExcelField(title="复议专家", align=2, sort=14)

	
	public String getMMMexpertname() {
		return MMMexpertname;
	}
	public void setMMMexpertname(String MMMexpertname) {
		this.MMMexpertname = MMMexpertname;
	}
	@ExcelField(title="复议结论", align=2, sort=15)
	public String getfResult() {
		return fResult;
	}
	public void setfResult(String fResult) {
		this.fResult = fResult;
	}
	@ExcelField(title="复议意见", align=2, sort=16)
	public String getReconsiderAdvice() {
		return reconsiderAdvice;
	}
	public void setReconsiderAdvice(String reconsiderAdvice) {
		this.reconsiderAdvice = reconsiderAdvice;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public exportChangeUnit(changeUnit changeunit){
		this.index1=changeunit.getIndex1();
		this.enterprisename=changeunit.getEnterprisename();
		this.workerSex=changeunit.getWorkerSex();
		this.registerCertificateNum=changeunit.getRegisterCertificateNum();
		this.city=changeunit.getCity();
		this.type=changeunit.getType();
		this.oldName=changeunit.getOldName();
		this.newName=changeunit.getNewName();
		this.date=changeunit.getDate();
		this.result=changeunit.getResult();
		this.lastView=changeunit.getLastView();
		this.expertName=changeunit.getExpertName();
		this.expertView=changeunit.getExpertView();
		this.MMMexpertname=changeunit.getMMMexpertName();
		this.fResult=changeunit.getfResult();
		this.reconsiderAdvice=changeunit.getReconsiderAdvice();
	}

}
