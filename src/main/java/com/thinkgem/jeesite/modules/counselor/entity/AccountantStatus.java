package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

public class AccountantStatus extends DataEntity<AccountantStatus> {
    /*
    * .accountant_status 执业情况表
id
table_id
person_id
company_name varchar(100)   执业单位id
job_company_name  varchar(100)      工作单位名称
backupnum     varchar(30)   备案编号
backuptype    varchar(1)    备案情况   0：未备案   1：已备案
company_type  varchar(1)    单位性质   1:企业   2：事业     （字典已存在）
register_certificate_num varchar(30)   登记证书编号
accountant_check varchar(1) 上一年度执业检查结论    0：不合格 1：合格
old_main_specialty  varchar(2) 新主专业
old_auxiliary_specialty  varchar(2) 新辅专业
new_main_specialty  varchar(2) 新主专业
new_auxiliary_specialty  varchar(2) 新辅专业
createby
create_date
update_by
update_date
remarks
    * */
    private String tableId;
    private String personId;
    private String companyName;
    private String jobCompanyName;
    private String backupNum;
    private String backupType;
    private String companyType;
    private String registerCertificateNum;
    private String accountantCheck; //已经弃用
    private String oldMainSpecialty;
    private String oldAuxiliarySpecialty;
    private String newMainSpecialty;
    private String newAuxiliarySpecialty;
    private String reason;
    private Date startDate;
    private String confirmFive;//是否符合第五条

    public String getConfirmFive() {
        return confirmFive;
    }

    public void setConfirmFive(String confirmFive) {
        this.confirmFive = confirmFive;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobCompanyName() {
        return jobCompanyName;
    }

    public void setJobCompanyName(String jobCompanyName) {
        this.jobCompanyName = jobCompanyName;
    }

    public String getBackupNum() {
        return backupNum;
    }

    public void setBackupNum(String backupNum) {
        this.backupNum = backupNum;
    }

    public String getBackupType() {
        return backupType;
    }

    public void setBackupType(String backupType) {
        this.backupType = backupType;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getRegisterCertificateNum() {
        return registerCertificateNum;
    }

    public void setRegisterCertificateNum(String registerCertificateNum) {
        this.registerCertificateNum = registerCertificateNum;
    }

    public String getAccountantCheck() {
        return accountantCheck;
    }

    public void setAccountantCheck(String accountantCheck) {
        this.accountantCheck = accountantCheck;
    }

    public String getOldMainSpecialty() {
        return oldMainSpecialty;
    }

    public void setOldMainSpecialty(String oldMainSpecialty) {
        this.oldMainSpecialty = oldMainSpecialty;
    }

    public String getOldAuxiliarySpecialty() {
        return oldAuxiliarySpecialty;
    }

    public void setOldAuxiliarySpecialty(String oldAuxiliarySpecialty) {
        this.oldAuxiliarySpecialty = oldAuxiliarySpecialty;
    }

    public String getNewMainSpecialty() {
        return newMainSpecialty;
    }

    public void setNewMainSpecialty(String newMainSpecialty) {
        this.newMainSpecialty = newMainSpecialty;
    }

    public String getNewAuxiliarySpecialty() {
        return newAuxiliarySpecialty;
    }

    public void setNewAuxiliarySpecialty(String newAuxiliarySpecialty) {
        this.newAuxiliarySpecialty = newAuxiliarySpecialty;
    }



}
