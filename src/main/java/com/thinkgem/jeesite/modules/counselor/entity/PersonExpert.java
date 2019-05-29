package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

public class PersonExpert extends DataEntity<PersonExpert> {
    private String name;

    private String recordId;
    private String type;
    private String items;
    private String itemText;
    private String secondItems;
    private String secondItemText;
    private String examineId;
    private String expertId;
    private String examineType;
    private  String index;
    private List<CounselorAttachment> attachmentList;//用于放置退回图片列表
    private Feedback feedback; //用于存放退回后的回复


    private List<PersonExpert> expertList;//用来接收普通的退回list
    private List<PersonExpert> educationtblList;//学历退回list
    private List<PersonExpert> titleList;
    private List<PersonExpert> specialtyList;

    public List<PersonExpert> getEducationtblList() {
        return educationtblList;
    }

    public void setEducationtblList(List<PersonExpert> educationtblList) {
        this.educationtblList = educationtblList;
    }

    public List<PersonExpert> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<PersonExpert> titleList) {
        this.titleList = titleList;
    }

    public List<PersonExpert> getSpecialtyList() {
        return specialtyList;
    }

    public void setSpecialtyList(List<PersonExpert> specialtyList) {
        this.specialtyList = specialtyList;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public List<CounselorAttachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<CounselorAttachment> attachmentList) {
        this.attachmentList = attachmentList;
    }



    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PersonExpert> getExpertList() {
        return expertList;
    }

    public void setExpertList(List<PersonExpert> expertList) {
        this.expertList = expertList;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public String getSecondItems() {
        return secondItems;
    }

    public void setSecondItems(String secondItems) {
        this.secondItems = secondItems;
    }

    public String getSecondItemText() {
        return secondItemText;
    }

    public void setSecondItemText(String secondItemText) {
        this.secondItemText = secondItemText;
    }

    public String getRecordId() {
        return recordId;
    }
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getExamineType() {
        return examineType;
    }

    public void setExamineType(String examineType) {
        this.examineType = examineType;
    }

   
}
