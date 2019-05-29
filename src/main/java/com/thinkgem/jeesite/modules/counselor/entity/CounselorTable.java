package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 咨询人员详情Entity
 * @author hzy
 * @version 2018-08-15
 */
public class CounselorTable extends DataEntity<CounselorTable> {
    private String personRecordId;
    private String type;
    private String status;

    public String getPersonRecordId() {
        return personRecordId;
    }

    public void setPersonRecordId(String personRecordId) {
        this.personRecordId = personRecordId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
