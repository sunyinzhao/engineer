package com.thinkgem.jeesite.modules.counselor.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

//导入excel ,关联person_record表
public class Excel4Record extends DataEntity<Excel4Record> {
    private String declareType;//申请类型
    private String name; // 城市省份
    private String companyName;//公司名字
    private String enterpriseName;//咨询师名字
    private String registerCertificateNum;//咨登编号
    private Date declareDate;//申请时间
    private String oldValue; //旧公司
    private String newValue; //新公司
    private String Cexpert1name;//初审专家1名字
    private String firstCdeclareResult; //初审1结论
    private String Cexpert2name;//初审专家2 名字
    private String secondCdeclareResult;//初审2结论
    private String Zexpert1name;//终审专家1名字
    private String firstZdeclareResult;//终审1结论
    private String Zexpert2name;//终审专家2名字
    private String secondZdeclareResult;//终审2结论
    private String advice;//专家建议

    public String getDeclareType() {
        return declareType;
    }

    public void setDeclareType(String declareType) {
        this.declareType = declareType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getRegisterCertificateNum() {
        return registerCertificateNum;
    }

    public void setRegisterCertificateNum(String registerCertificateNum) {
        this.registerCertificateNum = registerCertificateNum;
    }

    public Date getDeclareDate() {
        return declareDate;
    }

    public void setDeclareDate(Date declareDate) {
        this.declareDate = declareDate;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getCexpert1name() {
        return Cexpert1name;
    }

    public void setCexpert1name(String cexpert1name) {
        Cexpert1name = cexpert1name;
    }

    public String getFirstCdeclareResult() {
        return firstCdeclareResult;
    }

    public void setFirstCdeclareResult(String firstCdeclareResult) {
        this.firstCdeclareResult = firstCdeclareResult;
    }

    public String getCexpert2name() {
        return Cexpert2name;
    }

    public void setCexpert2name(String cexpert2name) {
        Cexpert2name = cexpert2name;
    }

    public String getSecondCdeclareResult() {
        return secondCdeclareResult;
    }

    public void setSecondCdeclareResult(String secondCdeclareResult) {
        this.secondCdeclareResult = secondCdeclareResult;
    }

    public String getZexpert1name() {
        return Zexpert1name;
    }

    public void setZexpert1name(String zexpert1name) {
        Zexpert1name = zexpert1name;
    }

    public String getFirstZdeclareResult() {
        return firstZdeclareResult;
    }

    public void setFirstZdeclareResult(String firstZdeclareResult) {
        this.firstZdeclareResult = firstZdeclareResult;
    }

    public String getZexpert2name() {
        return Zexpert2name;
    }

    public void setZexpert2name(String zexpert2name) {
        Zexpert2name = zexpert2name;
    }

    public String getSecondZdeclareResult() {
        return secondZdeclareResult;
    }

    public void setSecondZdeclareResult(String secondZdeclareResult) {
        this.secondZdeclareResult = secondZdeclareResult;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
