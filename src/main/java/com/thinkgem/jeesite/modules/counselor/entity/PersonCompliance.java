package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

public class PersonCompliance extends DataEntity<PersonCompliance> {
    private String recordId;
    private String examineId;
    private String isCheck;
    private String isRight;
    private String type;
    private String name;
    private List<PersonCompliance> check;

    public List<PersonCompliance> getCheck() {
        return check;
    }

    public void setCheck(List<PersonCompliance> check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getIsRight() {
        return isRight;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
