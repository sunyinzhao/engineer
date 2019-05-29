package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

public class ProjectInvestment extends DataEntity<ProjectInvestment> {
    private String orderBy;
    private String tableId;
    private String recordId;
    private Date commitTime;
    private String accountant;
    private String projectName;
    private String compliancerName;
    private String jsgm;
    private String zcSpecialty;
    private String brzy;
    private String nr;
    private List<ProjectInvestment> projectList;
    private String personId;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public List<ProjectInvestment> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectInvestment> projectList) {
        this.projectList = projectList;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public String getAccountant() {
        return accountant;
    }

    public void setAccountant(String accountant) {
        this.accountant = accountant;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompliancerName() {
        return compliancerName;
    }

    public void setCompliancerName(String compliancerName) {
        this.compliancerName = compliancerName;
    }

    public String getJsgm() {
        return jsgm;
    }

    public void setJsgm(String jsgm) {
        this.jsgm = jsgm;
    }

    public String getZcSpecialty() {
        return zcSpecialty;
    }

    public void setZcSpecialty(String zcSpecialty) {
        this.zcSpecialty = zcSpecialty;
    }

    public String getBrzy() {
        return brzy;
    }

    public void setBrzy(String brzy) {
        this.brzy = brzy;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }
}
