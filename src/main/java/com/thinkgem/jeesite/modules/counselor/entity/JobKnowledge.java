package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

public class JobKnowledge extends DataEntity<JobKnowledge> {
    private String tableId;
    private String personId;
    private String startDate;
    private String endDate;
    private String companyName;
    private String jobContent;
    private String duties;
    private String title;
    private String reterence;
    private String reterencetel;

    private String isChange; //是否能够修改 . 0:表示不可修改. . 提交表单的时候,把根据personId 查询的jobList 全部更新isChange为1

    public String getIsChange() {
        return isChange;
    }

    public void setIsChange(String isChange) {
        this.isChange = isChange;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<JobKnowledge> getJobKnowledgeList() {
        return jobKnowledgeList;
    }

    public void setJobKnowledgeList(List<JobKnowledge> jobKnowledgeList) {
        this.jobKnowledgeList = jobKnowledgeList;
    }

    private List<JobKnowledge> jobKnowledgeList = null;
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



    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReterence() {
        return reterence;
    }

    public void setReterence(String reterence) {
        this.reterence = reterence;
    }

    public String getReterencetel() {
        return reterencetel;
    }

    public void setReterencetel(String reterencetel) {
        this.reterencetel = reterencetel;
    }

    /*
        * id
    table_id
    person_id varchar(100)
    startdate datetime      开始时间
    enddate  datetime      结束时间
    companyname  varchar(100)  单位名称
    job_content varchar(100)  工作内容
    duties   varchar(20)  职务
    title varchar(20)     职称
    reterence varchar(50)  证明人
    reterencetel varchar(100) 证明人联系方式
        * */


}
