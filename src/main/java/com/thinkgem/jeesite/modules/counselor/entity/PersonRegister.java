package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class PersonRegister extends DataEntity<PersonRegister> {
    /*id
            table_id
    person_id
    company_name varchar(100)   执业单位id
    job_company_name  varchar(100)      工作单位名称
    backupnum     varchar(30)   备案编号
    backuptype    varchar(1)    是否备案   0：未备案   1：已备案
    company_type  varchar(1)    单位性质   1:企业   2：事业     （字典已存在）
    iseducate     varchar(1)    是否满足继续教育需求  0:不满足   1：满足
    register_main_specialty  varchar(2) 主专业
    register_auxiliary_specialty  varchar(2)辅专业*/

    private String tableId;
    private String personId;
    private String companyName;
    private String jobCompanyName;
    private String backupNum;
    private String backupType;
    private String companyType;
    private String iseducate; //已经弃用
    private String registerMainSpecialty;
    private String registerAuxiliarySpecialty;
    private String accountantCheck; //已经弃用
    private String isFifth;
    private String confirmFive;//是否符合第五条

    public String getConfirmFive() {
        return confirmFive;
    }

    public void setConfirmFive(String confirmFive) {
        this.confirmFive = confirmFive;
    }

    public String getIsFifth() {
        return isFifth;
    }

    public void setIsFifth(String isFifth) {
        this.isFifth = isFifth;
    }

    public String getAccountantCheck() {
        return accountantCheck;
    }

    public void setAccountantCheck(String accountantCheck) {
        this.accountantCheck = accountantCheck;
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

    public String getIseducate() {
        return iseducate;
    }

    public void setIseducate(String iseducate) {
        this.iseducate = iseducate;
    }

    public String getRegisterMainSpecialty() {
        return registerMainSpecialty;
    }

    public void setRegisterMainSpecialty(String registerMainSpecialty) {
        this.registerMainSpecialty = registerMainSpecialty;
    }

    public String getRegisterAuxiliarySpecialty() {
        return registerAuxiliarySpecialty;
    }

    public void setRegisterAuxiliarySpecialty(String registerAuxiliarySpecialty) {
        this.registerAuxiliarySpecialty = registerAuxiliarySpecialty;
    }


}
