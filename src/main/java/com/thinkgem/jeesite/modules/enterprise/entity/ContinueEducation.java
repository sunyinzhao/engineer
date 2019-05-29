package com.thinkgem.jeesite.modules.enterprise.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 继续教育往年数据
 */
public class ContinueEducation  extends DataEntity<ContinueEducation> {
    private static final long serialVersionUID = 1L;
    private String workerId;
    private String cardNum;
    private String name;
    private String year;
    private String educationFlag;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEducationFlag() {
        return educationFlag;
    }

    public void setEducationFlag(String educationFlag) {
        this.educationFlag = educationFlag;
    }
}
