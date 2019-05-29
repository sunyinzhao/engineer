package com.thinkgem.jeesite.modules.counselor.entity;

//咨询师

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 咨询人员Entity
 * @author hzy
 * @version 2018-08-14
 */
public class Counselor extends DataEntity<Counselor> {

    private String batchId;//批次id
    private String personId;//咨询师id
    private Date declareDate;//申请时间
    private String batchType;//批次申请类型
    private String declareType;//申请类型   0.注销登记 1.初始登记 2.变更执业单位 3.变更专业申请 4.继续登记
    private String batchStatus; //批次申请的流程节点
    private String declareStatus;//某一个申请的流程节点
    private String expertId;//专家ID
    private String declareResult;//某一个申请的结果 1:通过  2：部分通过 3：不通过
    private String batchResult;// 批次的结果   1:通过  2：部分通过 3：不通过
    private String delFlag;
    private String companyName;//执业单位名字
    private String companyId;//执业单位ID
    private String registerMainSpecialty;//主专业
    private String registerAuxiliarySpecialty;//辅专业
    private String cancelType;//注销原因
    private String hgReturn;//合规退回原因
    private String returnReason;//预审退回
    private String receiveReason;//预审退回
    private String impropriety;//处罚标识 1.无异常 2.执业检查不通过, 3.材料作假
    private String officeName;
    private Date annDate;
    private String announcement; //批次字段
    private String returnType;//退回类型 1：合规退回  2：预审退回 3：待接收退回
    private String HdeclareResult;//合规通过不通过，1. 通过 3.不通过
    private String fyAdvice;//复议意见字段
    private String ifFinish;//是否处理, 在不真实列表里进行修改, 如果修改后 状态为 1
    private String firstFexpertResult;//
    private String secondFexpertResult;//
    private String isPunish;//处罚标识
    private Date publicDate;//公告日期
    private String batchNo;//批次号
    private int qty;//批次号
    private String officeCode;//officecode
    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }
    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }
    
    public String getIsPunish() {
        return isPunish;
    }

    public void setIsPunish(String isPunish) {
        this.isPunish = isPunish;
    }
    

    public String getSecondFexpertResult() {
        return secondFexpertResult;
    }

    public void setSecondFexpertResult(String secondFexpertResult) {
        this.secondFexpertResult = secondFexpertResult;
    }

    public String getFirstFexpertResult() {
        return firstFexpertResult;
    }

    public void setFirstFexpertResult(String firstFexpertResult) {
        this.firstFexpertResult = firstFexpertResult;
    }

    public String getIfFinish() {
        return ifFinish;
    }

    public void setIfFinish(String ifFinish) {
        this.ifFinish = ifFinish;
    }
    
    public String getFyAdvice() {
        return fyAdvice;
    }

    public void setFyAdvice(String fyAdvice) {
        this.fyAdvice = fyAdvice;
    }

    public String getHdeclareResult() {
        return HdeclareResult;
    }

    public void setHdeclareResult(String hdeclareResult) {
        HdeclareResult = hdeclareResult;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public Date getAnnDate() {
        return annDate;
    }

    public void setAnnDate(Date annDate) {
        this.annDate = annDate;
    }

    private String hgExpertId;//用于存储审核单据的人

    public String getHgExpertId() {
        return hgExpertId;
    }

    public void setHgExpertId(String hgExpertId) {
        this.hgExpertId = hgExpertId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getImpropriety() {
        return impropriety;
    }

    public void setImpropriety(String impropriety) {
        this.impropriety = impropriety;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getReceiveReason() {
        return receiveReason;
    }

    public void setReceiveReason(String receiveReason) {
        this.receiveReason = receiveReason;
    }

    public String getHgReturn() {
        return hgReturn;
    }

    public void setHgReturn(String hgReturn) {
        this.hgReturn = hgReturn;
    }

    public String getCancelType() {
        return cancelType;
    }

    public void setCancelType(String cancelType) {
        this.cancelType = cancelType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getRegisterMainSpecialty() {
        return registerMainSpecialty;
    }

    public void setRegisterMainSpecialty(String registerMainSpecialty) {
        this.registerMainSpecialty = registerMainSpecialty;
    }

    public String getRegisterAuxiliarySpecialty() {
        return registerAuxiliarySpecialty;
    }

    public void setRegisterAuxiliarySpecialty(String registerAuxiliarySpecialty) {
        this.registerAuxiliarySpecialty = registerAuxiliarySpecialty;
    }

    private String firstCexpertId;//预审专家1
    private String firstCdeclareResult;//1:通过 3:不通过
    private String secondCexpertId;//预审专家2
    private String secondCdeclareResult;//1 通过  3.不通过
    private String firstZexpertId;//终审专家1
    private String firstZdeclareResult;//1.通过  3.不通过
    private String secondZexpertId;//终审专家2
    private String secondZdeclareResult;//1.通过  3.不通过
    private String utilFeedback; //单位是否反馈
    private String cdeclareResult;//预审结论
    private String zdeclareResult;//终审结论
    private String fdeclareResult;//复合结论

    //用于查询条件
    private Date startDate;
    private Date endDate;

    public String getFirstCdeclareResult() {
        return firstCdeclareResult;
    }

    public void setFirstCdeclareResult(String firstCdeclareResult) {
        this.firstCdeclareResult = firstCdeclareResult;
    }

    public String getSecondCdeclareResult() {
        return secondCdeclareResult;
    }

    public void setSecondCdeclareResult(String secondCdeclareResult) {
        this.secondCdeclareResult = secondCdeclareResult;
    }

    public String getFirstZdeclareResult() {
        return firstZdeclareResult;
    }

    public void setFirstZdeclareResult(String firstZdeclareResult) {
        this.firstZdeclareResult = firstZdeclareResult;
    }

    public String getSecondZdeclareResult() {
        return secondZdeclareResult;
    }

    public void setSecondZdeclareResult(String secondZdeclareResult) {
        this.secondZdeclareResult = secondZdeclareResult;
    }

    public void setCdeclareResult(String cdeclareResult) {
        this.cdeclareResult = cdeclareResult;
    }
    public String getCdeclareResult() {
        return cdeclareResult;
    }

    public void setZdeclareResult(String zdeclareResult) {
        this.zdeclareResult = zdeclareResult;
    }
    public String getZdeclareResult() {
        return zdeclareResult;
    }
    public void setFdeclareResult(String fdeclareResult) {
        this.fdeclareResult = fdeclareResult;
    }
    public String getFdeclareResult() {
        return fdeclareResult;
    }
    public String getUtilFeedback() {
        return utilFeedback;
    }

    public void setUtilFeedback(String utilFeedback) {
        this.utilFeedback = utilFeedback;
    }


    public String getFirstCexpertId() {
        return firstCexpertId;
    }

    public void setFirstCexpertId(String firstCexpertId) {
        this.firstCexpertId = firstCexpertId;
    }



    public String getSecondCexpertId() {
        return secondCexpertId;
    }

    public void setSecondCexpertId(String secondCexpertId) {
        this.secondCexpertId = secondCexpertId;
    }



    public String getFirstZexpertId() {
        return firstZexpertId;
    }

    public void setFirstZexpertId(String firstZexpertId) {
        this.firstZexpertId = firstZexpertId;
    }


    public String getSecondZexpertId() {
        return secondZexpertId;
    }

    public void setSecondZexpertId(String secondZexpertId) {
        this.secondZexpertId = secondZexpertId;
    }



    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Date getDeclareDate() {
        return declareDate;
    }

    public void setDeclareDate(Date declareDate) {
        this.declareDate = declareDate;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    public String getDeclareType() {
        return declareType;
    }

    public void setDeclareType(String declareType) {
        this.declareType = declareType;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getDeclareStatus() {
        return declareStatus;
    }

    public void setDeclareStatus(String declareStatus) {
        this.declareStatus = declareStatus;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getDeclareResult() {
        return declareResult;
    }

    public void setDeclareResult(String declareResult) {
        this.declareResult = declareResult;
    }

    public String getBatchResult() {
        return batchResult;
    }

    public void setBatchResult(String batchResult) {
        this.batchResult = batchResult;
    }

    
    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }



}
