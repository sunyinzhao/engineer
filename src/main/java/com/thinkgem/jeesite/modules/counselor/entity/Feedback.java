package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

public class Feedback extends DataEntity<Feedback> {
    private String expertId;//某一项的id
    private String personRecordId;//recordId
    private String backMemo;//回复内容
    private Date backTime;//回复时间

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getPersonRecordId() {
        return personRecordId;
    }

    public void setPersonRecordId(String personRecordId) {
        this.personRecordId = personRecordId;
    }

    public String getBackMemo() {
        return backMemo;
    }

    public void setBackMemo(String backMemo) {
        this.backMemo = backMemo;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }
}
