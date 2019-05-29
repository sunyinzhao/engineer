package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 信息导出Entity
 *
 * @author
 * @version 2019-05-16
 */
public class EducationSchoolInfoExport {

    private String index;//用于区分第几次添加, 存在添加多条学历. 生成的序号根据index进行区分
    @ExcelField(title = "序号",align = 2,sort = 1)
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    private String areaName; //所属协会
    private String companyName; //单位名称
    private String enterpriseName;  //姓名
    private String certificatesNum; //身份证号
    private String isValid; //咨询师登记状态： 0登记过但无效 1登记且有效 2未申请登记
    private String school;  //毕业学校
    private String type;
    @ExcelField(title = "所属协会",align = 2,sort = 2)
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    @ExcelField(title = "单位名称",align = 2,sort = 4)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    @ExcelField(title = "姓名",align = 2,sort = 5)
    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    @ExcelField(title = "身份证号",align = 2,sort = 7)
    public String getCertificatesNum() {
        return certificatesNum;
    }

    public void setCertificatesNum(String certificatesNum) {
        this.certificatesNum = certificatesNum;
    }
    @ExcelField(title = "咨询工程师(投资)状态",align = 2,sort = 8)
    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
    @ExcelField(title = "毕业院校",align = 2,sort = 10)
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
    @ExcelField(title = "不合格原因",align = 2,sort = 11)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EducationSchoolInfoExport(EducationSchoolInfo esi) {
        this.index = esi.getIndex();
        this.areaName = esi.getAreaName();
        this.companyName = esi.getCompanyName();
        this.enterpriseName = esi.getEnterpriseName();
        this.certificatesNum = esi.getCertificatesNum();
        this.isValid = DictUtils.getDictLabel(esi.getIsValid(),"isValid","");
        this.school = esi.getSchool();
        String typeF = "";
        String esiType = esi.getType();
        String[] splitType = esiType.split(",");
        for (int i = 0; i < splitType.length; i++) {
            if (!"null".equals(splitType[i])) {
                typeF += DictUtils.getDictLabel(splitType[i],"check_zhengshu_type","")+"未通过。 \n";
            }
        }
        System.out.println(typeF);
        this.type = typeF;
    }
}
