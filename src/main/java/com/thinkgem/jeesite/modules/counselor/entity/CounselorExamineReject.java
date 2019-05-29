package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

public class CounselorExamineReject extends DataEntity<CounselorExamineReject> {

    private String personRecordId;
    private String examineId;
    private String content;
    private String title;
    private String delFlag;

    public CounselorExamineReject(String personRecordId, String examineId) {
        this.personRecordId = personRecordId;
        this.examineId = examineId;
    }
    public CounselorExamineReject() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CounselorExamineReject> getRejectList() {
        return rejectList;
    }

    public void setRejectList(List<CounselorExamineReject> rejectList) {
        this.rejectList = rejectList;
    }

    private List<CounselorExamineReject> rejectList=null;
    public String getPersonRecordId() {
        return personRecordId;
    }

    public void setPersonRecordId(String personRecordId) {
        this.personRecordId = personRecordId;
    }

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

}
