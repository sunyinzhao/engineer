package com.thinkgem.jeesite.modules.counselor.entity;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import java.util.Date;
import java.util.List;

/**
 * 学历教育情况Entity
 * @author hzy
 * @version 2018-08-16
 */
public class Educationtbl extends DataEntity<Educationtbl> {
    private String tableId;
    private String personId;
    private String schoolType;
    private String school;
    private String specialty;
    private String studyType;
    private String education;
    private String studyYear;
    private Date startTime;
    private Date endTime;
    private String schoolMaster;
    private Date getgctime;
    private String index;//用于区分第几次添加, 存在添加多条学历. 生成的序号根据index进行区分
    private String main;//用于储存主专业选项的personExpertId
    private String assist;//用于储存辅专业选项的personExpertId
    private String zsNo; //证书编号

    public String getZsNo() {
        return zsNo;
    }

    public void setZsNo(String zsNo) {
        this.zsNo = zsNo;
    }

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

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
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

    public String getSchoolMaster() {
        return schoolMaster;
    }

    public void setSchoolMaster(String schoolMaster) {
        this.schoolMaster = schoolMaster;
    }

    public Date getGetgctime() {
        return getgctime;
    }

    public void setGetgctime(Date getgctime) {
        this.getgctime = getgctime;
    }


    private String fileNo;//存放文件路径
//    private List<SysAttachment> sysAttachmentList = new ArrayList<SysAttachment>();
    private List<CounselorAttachment> counselorAttachmentList = Lists.newArrayList();		// 子表列表

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public List<CounselorAttachment> getCounselorAttachmentList() {
        return counselorAttachmentList;
    }

    public void setCounselorAttachmentList(List<CounselorAttachment> counselorAttachmentList) {
        this.counselorAttachmentList = counselorAttachmentList;
    }
}
