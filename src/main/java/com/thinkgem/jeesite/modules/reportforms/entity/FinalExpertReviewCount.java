package com.thinkgem.jeesite.modules.reportforms.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 终审专家评审情况统计表
 */
public class FinalExpertReviewCount extends DataEntity<FinalExpertReviewCount> {
    private String id;
    @Override
    public String getId() {
        return id;
    }
    private String officeId;		//
    private String declareType;	// 申请类型

    public String getOfficeId() {
        return officeId;
    }
    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }
    public String getDeclareType() {
        return declareType;
    }
    public void setDeclareType(String declareType) {
        this.declareType = declareType;
    }

    private int index;//序号
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    private String reviewType;		//前台回显用评审阶段 专家评审；专家复核
    public String getReviewType() {
        return reviewType;
    }
    public void setReviewType(String reviewType) {
        this.reviewType = reviewType;
    }

    private String batchNo;//批次
    private String reviewStage;//评审阶段
    private String expertName;//专家姓名

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getReviewStage() {
        return reviewStage;
    }

    public void setReviewStage(String reviewStage) {
        this.reviewStage = reviewStage;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    private String initialAllocatedQuantity;   //初始分配数量
    private String initialAccord;                //初始符合
    private String initialIncompatible;         //初始不符合
    private String initialUndone;               //初始未完成数量
    private String unitAllocatedQuantity;       //单位分配数量
    private String unitAccord;                    //单位符合
    private String unitIncompatible;             //单位不符合
    private String unitUndone;                    //单位未完成数量
    private String specialtyAllocatedQuantity;   //专业分配数量
    private String specialtyAccord;                //专业符合
    private String specialtyIncompatible;         //专业不符合
    private String specialtyUndone;                //专业未完成数量
    private String continueAllocatedQuantity;     //继续分配数量
    private String continueAccord;                  //继续符合
    private String continueIncompatible;           //继续不符合
    private String continueUndone;                  //继续未完成数量
    private String logoutAllocatedQuantity;        //注销分配数量
    private String logoutAccord;                     //注销符合
    private String logoutIncompatible;              //注销不符合
    private String logoutUndone;                     //注销未完成数量

    public String getInitialAllocatedQuantity() {
        return initialAllocatedQuantity;
    }

    public void setInitialAllocatedQuantity(String initialAllocatedQuantity) {
        this.initialAllocatedQuantity = initialAllocatedQuantity;
    }

    public String getInitialAccord() {
        return initialAccord;
    }

    public void setInitialAccord(String initialAccord) {
        this.initialAccord = initialAccord;
    }

    public String getInitialIncompatible() {
        return initialIncompatible;
    }

    public void setInitialIncompatible(String initialIncompatible) {
        this.initialIncompatible = initialIncompatible;
    }

    public String getInitialUndone() {
        return initialUndone;
    }

    public void setInitialUndone(String initialUndone) {
        this.initialUndone = initialUndone;
    }


    public String getUnitAllocatedQuantity() {
        return unitAllocatedQuantity;
    }

    public void setUnitAllocatedQuantity(String unitAllocatedQuantity) {
        this.unitAllocatedQuantity = unitAllocatedQuantity;
    }

    public String getUnitAccord() {
        return unitAccord;
    }

    public void setUnitAccord(String unitAccord) {
        this.unitAccord = unitAccord;
    }

    public String getUnitIncompatible() {
        return unitIncompatible;
    }

    public void setUnitIncompatible(String unitIncompatible) {
        this.unitIncompatible = unitIncompatible;
    }

    public String getUnitUndone() {
        return unitUndone;
    }

    public void setUnitUndone(String unitUndone) {
        this.unitUndone = unitUndone;
    }


    public String getSpecialtyAllocatedQuantity() {
        return specialtyAllocatedQuantity;
    }

    public void setSpecialtyAllocatedQuantity(String specialtyAllocatedQuantity) {
        this.specialtyAllocatedQuantity = specialtyAllocatedQuantity;
    }

    public String getSpecialtyAccord() {
        return specialtyAccord;
    }

    public void setSpecialtyAccord(String specialtyAccord) {
        this.specialtyAccord = specialtyAccord;
    }

    public String getSpecialtyIncompatible() {
        return specialtyIncompatible;
    }

    public void setSpecialtyIncompatible(String specialtyIncompatible) {
        this.specialtyIncompatible = specialtyIncompatible;
    }

    public String getSpecialtyUndone() {
        return specialtyUndone;
    }

    public void setSpecialtyUndone(String specialtyUndone) {
        this.specialtyUndone = specialtyUndone;
    }


    public String getContinueAllocatedQuantity() {
        return continueAllocatedQuantity;
    }

    public void setContinueAllocatedQuantity(String continueAllocatedQuantity) {
        this.continueAllocatedQuantity = continueAllocatedQuantity;
    }

    public String getContinueAccord() {
        return continueAccord;
    }

    public void setContinueAccord(String continueAccord) {
        this.continueAccord = continueAccord;
    }

    public String getContinueIncompatible() {
        return continueIncompatible;
    }

    public void setContinueIncompatible(String continueIncompatible) {
        this.continueIncompatible = continueIncompatible;
    }

    public String getContinueUndone() {
        return continueUndone;
    }

    public void setContinueUndone(String continueUndone) {
        this.continueUndone = continueUndone;
    }


    public String getLogoutAllocatedQuantity() {
        return logoutAllocatedQuantity;
    }

    public void setLogoutAllocatedQuantity(String logoutAllocatedQuantity) {
        this.logoutAllocatedQuantity = logoutAllocatedQuantity;
    }

    public String getLogoutAccord() {
        return logoutAccord;
    }

    public void setLogoutAccord(String logoutAccord) {
        this.logoutAccord = logoutAccord;
    }

    public String getLogoutIncompatible() {
        return logoutIncompatible;
    }

    public void setLogoutIncompatible(String logoutIncompatible) {
        this.logoutIncompatible = logoutIncompatible;
    }

    public String getLogoutUndone() {
        return logoutUndone;
    }

    public void setLogoutUndone(String logoutUndone) {
        this.logoutUndone = logoutUndone;
    }
}
