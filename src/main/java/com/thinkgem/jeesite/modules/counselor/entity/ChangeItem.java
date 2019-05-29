package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
//变更履历表

public class ChangeItem extends DataEntity<ChangeItem> {
    /*
    person_record_id varchar(100) 变更申请ID
changetype      varchar(1) 变更申请类型   1：变更执业单位 2：变更主专业  3：变更辅专业 4：初始登记主专业	5:初始登记辅专业
oldvalue	varchar(100)变更前值
newvalue	varchar(100)变更后值
result          varchar(1) 变更结论    0：不符合  1：符合*/
    private String recordId;
    private String changeType;
    private String oldValue;
    private String newValue;
    private String result;

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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
