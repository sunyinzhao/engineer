package com.thinkgem.jeesite.modules.counselor.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 专业培训Entity
 * @author hzy
 * @version 2018-08-16
 */
public class SpecialtyTrain extends DataEntity<SpecialtyTrain> {
    /*id
               table_id
       person_id
       train_school varchar(100) 培训学校
       train_type   varchar(30)  培训方式
       specialty    varchar(50)所学专业
       starttime    datetime 培训起始时间
       endtime      datetime 培训结束时间
       cardnum      varchar(50)证书编号
       studytype    varchar(50)学习方式
       schoolmaster varchar(50) 校长
       getgctime    datetime 颁证时间*/
    private String tableId;
    private String personId;
    private String trainSchool;
    private String trainType;
    private String specialty;
    private String studyTime;
    
    

	private List<CounselorAttachment> counselorAttachmentList=new ArrayList<CounselorAttachment>();



    private Date startTime;
    private Date endTime;
    private String cardnum;
    private String studyType;
    private String schoolMaster;
    private Date getgctime;
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

    public String getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(String studyTime) {
        this.studyTime = studyTime;
    }

    public Date getGetgctime() {
        return getgctime;
    }

    public void setGetgctime(Date getgctime) {
        this.getgctime = getgctime;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getTrainSchool() {
        return trainSchool;
    }

    public void setTrainSchool(String trainSchool) {
        this.trainSchool = trainSchool;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }


    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getSchoolMaster() {
        return schoolMaster;
    }

    public void setSchoolMaster(String schoolMaster) {
        this.schoolMaster = schoolMaster;
    }

	public List<CounselorAttachment> getCounselorAttachmentList() {
		return counselorAttachmentList;
	}

	public void setCounselorAttachmentList(List<CounselorAttachment> counselorAttachmentList) {
		this.counselorAttachmentList = counselorAttachmentList;
	}
    
    





}
