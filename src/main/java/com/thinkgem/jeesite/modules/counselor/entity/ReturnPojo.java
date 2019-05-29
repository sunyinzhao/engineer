package com.thinkgem.jeesite.modules.counselor.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

//负责接收 return List
public class ReturnPojo extends DataEntity<ReturnPojo> {
    private String officeName;//预审单位名称
    private String companyName;//执业单位名称
    private String contactsZy;//执业单位联系人
    private String contactZyPhone;//联系人电话
    private String workerName;//姓名
    private String declareType;//登记类型
    private String batchStatus;//批次状态
    private String declareStatus;//申请单状态
    private String returnType;//退回类型
    private String startTime;//传参
    private String endTime;//

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }



    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactsZy() {
        return contactsZy;
    }

    public void setContactsZy(String contactsZy) {
        this.contactsZy = contactsZy;
    }

    public String getContactZyPhone() {
        return contactZyPhone;
    }

    public void setContactZyPhone(String contactZyPhone) {
        this.contactZyPhone = contactZyPhone;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
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

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
