package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

public class UpdateService extends DataEntity<UpdateService> {
    private Date sourceUpdate; //调用时间. 用这个时间查找参数

    public Date getSourceUpdate() {
        return sourceUpdate;
    }

    public void setSourceUpdate(Date sourceUpdate) {
        this.sourceUpdate = sourceUpdate;
    }
}
