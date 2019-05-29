package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 学校Entity
 *
 * @author
 * @version 2019-05-16
 */
public class EducationSchoolInfo extends DataEntity<EducationSchoolInfo> {

    private String index;//用于区分第几次添加, 存在添加多条学历. 生成的序号根据index进行区分

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    private String main;//用于储存主专业选项的personExpertId
    private String assist;//用于储存辅专业选项的personExpertId


/*  id                  varchar	32
    person_id           varchar	32
    school_code         varchar	20     学校代号
    school_name         varchar	80     学校名称
    education_type      varchar	20     办学类型代码（证书编号第六位）：  普通高等教育1； 成人高等教育5； 高等教育自学考试和高等教育学历文凭考试6； 网络教育为 7  /（可多选）
    school_startyear    varchar	4      建校年份：4位数字年
    education_code      varchar	20     可陪养层次代码：   博士研究生   01；    硕士研究生  02； 第二学士学位    04；    本科   05；    专科（含高职） 06 。
    del_flag            varchar	1      删除标记    */

    private String id;
    private String personId;
    private String schoolCode;
    private String schoolName;
    private String educationType;
    private String schoolStartyear;
    private String educationCode;
    private String del_flag;
    private String type; //

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getEducationType() {
        return educationType;
    }

    public void setEducationType(String educationType) {
        this.educationType = educationType;
    }

    public String getSchoolStartyear() {
        return schoolStartyear;
    }

    public void setSchoolStartyear(String schoolStartyear) {
        this.schoolStartyear = schoolStartyear;
    }

    public String getEducationCode() {
        return educationCode;
    }

    public void setEducationCode(String educationCode) {
        this.educationCode = educationCode;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String checkType; // 学历编号位数验证    1
                                // 学校代码验证       2
                                // 办学类型验证       3
                                // 毕业年份验证       4
                                // 培养层次验证       5
    private String batchNo; // 批次号

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }


    private String indexNo; //用于区别学历/此处未用
    private String zsNo; //证书编号
    private String companyName; //单位名称
    private String areaName; //所属协会
    private String officeId;
    private String declareDate; //
    private String certificatesNum; //身份证号
    private String isValid; //咨询师登记状态： 0登记过但无效 1登记且有效 2未申请登记
    private String school;  //毕业学校
    private String enterpriseName;  //姓名

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getZsNo() {
        return zsNo;
    }

    public void setZsNo(String zsNo) {
        this.zsNo = zsNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getDeclareDate() {
        return declareDate;
    }

    public void setDeclareDate(String declareDate) {
        this.declareDate = declareDate;
    }

    public String getCertificatesNum() {
        return certificatesNum;
    }

    public void setCertificatesNum(String certificatesNum) {
        this.certificatesNum = certificatesNum;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}
