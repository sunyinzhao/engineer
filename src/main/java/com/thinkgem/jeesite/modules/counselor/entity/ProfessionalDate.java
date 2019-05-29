package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 验证专业数据得到的结果
 */
public class ProfessionalDate extends DataEntity<ProfessionalDate> {
    private static final long serialVersionUID = 1L;
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    private String reviewCompany;
    private String companyName;
    private String name;
    private String certificatesNum;
    private String isValid;
    private String newValue;
    private String changeType;
    private String result1;
    private String result2;
    private String result3;

    public ProfessionalDate(){

    }

    public ProfessionalDate(ProfessionalDate exportProfessionDate){
        this.reviewCompany=exportProfessionDate.getReviewCompany();
        this.companyName=exportProfessionDate.getCompanyName();
        this.name=exportProfessionDate.getName();
        this.certificatesNum=exportProfessionDate.getCertificatesNum();
        this.isValid=DictUtils.getDictLabel(exportProfessionDate.getIsValid(),"isValid","");
        this.newValue = DictUtils.getDictLabel(exportProfessionDate.getNewValue(),"specialty_type","");
        this.result1 = exportProfessionDate.getResult1();
        this.result2=exportProfessionDate.getResult2();
        this.result3=exportProfessionDate.getResult3();

    }


    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    @ExcelField(title="所属协会", align=2, sort=1)
    public String getReviewCompany() {
        return reviewCompany;
    }

    public void setReviewCompany(String reviewCompany) {
        this.reviewCompany = reviewCompany;
    }

    @ExcelField(title="单位名称", align=2, sort=2)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @ExcelField(title="姓名", align=2, sort=3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelField(title="身份证号", align=2, sort=4)
    public String getCertificatesNum() {
        return certificatesNum;
    }

    public void setCertificatesNum(String certificatesNum) {
        this.certificatesNum = certificatesNum;
    }

    @ExcelField(title="咨询工程师状态", align=2, sort=5)
    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    @ExcelField(title="所报专业", align=2, sort=6)
    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @ExcelField(title="所学专业", align=2, sort=7)
    public String getResult1() {
        return result1;
    }

    public void setResult1(String result1) {
        this.result1 = result1;
    }
    @ExcelField(title="职称专业", align=2, sort=8)
    public String getResult2() {
        return result2;
    }

    public void setResult2(String result2) {
        this.result2 = result2;
    }
    @ExcelField(title="培训专业", align=2, sort=9)
    public String getResult3() {
        return result3;
    }

    public void setResult3(String result3) {
        this.result3 = result3;
    }
}
