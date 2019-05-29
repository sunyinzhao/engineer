package com.thinkgem.jeesite.modules.counselor.entity;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

public class TitleCertificate extends DataEntity<TitleCertificate> {
//参考educationtbl
    /* id
                table_id
        person_id
        titlelevel varchar(1) 职称级别 1:初级   2：中级  3：高级
        titletype  varchar(2) 职称类型
        specialty   varchar(2) 专业
        approve_employer  varchar(50) 批准机构
        approve_time     datetime 批准时间
        get_employer   varchar(100)  回去的职称证书时的工作单位*/
    private String tableId;
    private String personId;
    private String titleLevel;
    private String titleType;
    private String specialty;
    private String approveEmployer;
    private Date approveTime;
    private String getEmployer;
    private String index;
    private String main;
    private String assist;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getAssist() {
        return assist;
    }

    public void setAssist(String assist) {
        this.assist = assist;
    }

    public String getGetEmployer() {
        return getEmployer;
    }

    public void setGetEmployer(String getEmployer) {
        this.getEmployer = getEmployer;
    }


    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getTitleLevel() {
        return titleLevel;
    }

    public void setTitleLevel(String titleLevel) {
        this.titleLevel = titleLevel;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getApproveEmployer() {
        return approveEmployer;
    }

    public void setApproveEmployer(String approveEmployer) {
        this.approveEmployer = approveEmployer;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    private List<CounselorAttachment> counselorAttachmentList =  Lists.newArrayList(); //子表列表

    public List<CounselorAttachment> getCounselorAttachmentList() {
        return counselorAttachmentList;
    }

    public void setCounselorAttachmentList(List<CounselorAttachment> counselorAttachmentList) {
        this.counselorAttachmentList = counselorAttachmentList;
    }
}
