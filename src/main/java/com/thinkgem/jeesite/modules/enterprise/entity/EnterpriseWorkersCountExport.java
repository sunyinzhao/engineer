package com.thinkgem.jeesite.modules.enterprise.entity;


import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 终审各批次导出类
 */
public class EnterpriseWorkersCountExport{
    private static final long serialVersionUID = 1L;

    private int index;//序号
    @ExcelField(title="序号", align=2, sort=1)
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    private String id;
    private String zrc;//总人次
    private String zxdj;//注销登记人数
    private String zxfh;//注销符合
    private String zxbf;//注销不符
    private String csdj;//初始登记人数
    private String csfh;//初始符合
    private String csbffh;//部分符合
    private String csbf;//初始不符
    private String bgdw;//变更单位人数
    private String dwfh;//单位符合
    private String dwbf;//单位不符
    private String bgzy;//变更专业
    private String zyfh;//专业符合
    private String zybffh;//专业部分符合
    private String zybf;//专业不符
    private String jxdj;//继续登记
    private String jxfh;//继续符合
    private String jxbf;//继续不符
    private String sqrs;//申请人数
    private String yxrs;//有效人数
    private String sxrs;//无效人数
    private String batchNo;//批次号
    private String startBatchNo;//批次号
    private String endBatchNo;//批次号
    private String officeName;//地方
    private String officeId;		//
    private String declareType;	// 申请类型       某一个所选的类型

    public EnterpriseWorkersCountExport() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZrc() {
        return zrc;
    }

    public void setZrc(String zrc) {
        this.zrc = zrc;
    }
    @ExcelField(title="注销登记申请", align=2, sort=39)
    public String getZxdj() {
        return zxdj;
    }

    public void setZxdj(String zxdj) {
        this.zxdj = zxdj;
    }
    @ExcelField(title="注销登记符合", align=2, sort=37)
    public String getZxfh() {
        return zxfh;
    }

    public void setZxfh(String zxfh) {
        this.zxfh = zxfh;
    }
    @ExcelField(title="注销登记不符合", align=2, sort=41)
    public String getZxbf() {
        return zxbf;
    }

    public void setZxbf(String zxbf) {
        this.zxbf = zxbf;
    }
    @ExcelField(title="初始登记申请人数", align=2, sort=13)
    public String getCsdj() {
        return csdj;
    }

    public void setCsdj(String csdj) {
        this.csdj = csdj;
    }
    @ExcelField(title="初始登记符合", align=2, sort=11)
    public String getCsfh() {
        return csfh;
    }

    public void setCsfh(String csfh) {
        this.csfh = csfh;
    }
    @ExcelField(title="初始登记部分符合", align=2, sort=15)
    public String getCsbffh() {
        return csbffh;
    }

    public void setCsbffh(String csbffh) {
        this.csbffh = csbffh;
    }
    @ExcelField(title="初始登记不符合", align=2, sort=17)
    public String getCsbf() {
        return csbf;
    }

    public void setCsbf(String csbf) {
        this.csbf = csbf;
    }
    @ExcelField(title="变更单位申请人数", align=2, sort=29)
    public String getBgdw() {
        return bgdw;
    }

    public void setBgdw(String bgdw) {
        this.bgdw = bgdw;
    }
    @ExcelField(title="变更单位符合", align=2, sort=27)
    public String getDwfh() {
        return dwfh;
    }

    public void setDwfh(String dwfh) {
        this.dwfh = dwfh;
    }
    @ExcelField(title="变更单位不符合", align=2, sort=31)
    public String getDwbf() {
        return dwbf;
    }

    public void setDwbf(String dwbf) {
        this.dwbf = dwbf;
    }
    @ExcelField(title="变更专业申请人数", align=2, sort=21)
    public String getBgzy() {
        return bgzy;
    }

    public void setBgzy(String bgzy) {
        this.bgzy = bgzy;
    }
    @ExcelField(title="变更专业符合", align=2, sort=19)
    public String getZyfh() {
        return zyfh;
    }

    public void setZyfh(String zyfh) {
        this.zyfh = zyfh;
    }
    @ExcelField(title="变更专业部分符合", align=2, sort=23)
    public String getZybffh() {
        return zybffh;
    }

    public void setZybffh(String zybffh) {
        this.zybffh = zybffh;
    }
    @ExcelField(title="变更专业不符合", align=2, sort=25)
    public String getZybf() {
        return zybf;
    }

    public void setZybf(String zybf) {
        this.zybf = zybf;
    }
    @ExcelField(title="继续登记申请人数", align=2, sort=33)
    public String getJxdj() {
        return jxdj;
    }

    public void setJxdj(String jxdj) {
        this.jxdj = jxdj;
    }
    @ExcelField(title="继续登记符合", align=2, sort=31)
    public String getJxfh() {
        return jxfh;
    }

    public void setJxfh(String jxfh) {
        this.jxfh = jxfh;
    }
    @ExcelField(title="继续登记不符合", align=2, sort=35)
    public String getJxbf() {
        return jxbf;
    }

    public void setJxbf(String jxbf) {
        this.jxbf = jxbf;
    }
    @ExcelField(title="申请人数", align=2, sort=5)
    public String getSqrs() {
        return sqrs;
    }

    public void setSqrs(String sqrs) {
        this.sqrs = sqrs;
    }
    @ExcelField(title="有效申请人数", align=2, sort=7)
    public String getYxrs() {
        return yxrs;
    }

    public void setYxrs(String yxrs) {
        this.yxrs = yxrs;
    }
    @ExcelField(title="失效申请人数", align=2, sort=9)
    public String getSxrs() {
        return sxrs;
    }

    public void setSxrs(String sxrs) {
        this.sxrs = sxrs;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getStartBatchNo() {
        return startBatchNo;
    }

    public void setStartBatchNo(String startBatchNo) {
        this.startBatchNo = startBatchNo;
    }

    public String getEndBatchNo() {
        return endBatchNo;
    }

    public void setEndBatchNo(String endBatchNo) {
        this.endBatchNo = endBatchNo;
    }
    @ExcelField(title="归属地", align=2, sort=3)
    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getDeclareType() {
        return declareType;
    }

    public void setDeclareType(String declareType) {
        this.declareType = declareType;
    }


    public EnterpriseWorkersCountExport(EnterpriseWorkersCount ewc) {
        this.index = ewc.getIndex();
        this.id = ewc.getId();
        this.zrc = ewc.getZrc();
        this.zxdj = ewc.getZxdj();
        this.zxfh = ewc.getZxfh();
        this.zxbf = ewc.getZxbf();
        this.csdj = ewc.getCsdj();
        this.csfh = ewc.getCsfh();
        this.csbffh = ewc.getCsbffh();
        this.csbf = ewc.getCsbf();
        this.bgdw = ewc.getBgdw();
        this.dwfh = ewc.getDwfh();
        this.dwbf = ewc.getDwbf();
        this.bgzy = ewc.getBgzy();
        this.zyfh = ewc.getZyfh();
        this.zybffh = ewc.getZybffh();
        this.zybf = ewc.getZybf();
        this.jxdj = ewc.getJxdj();
        this.jxfh = ewc.getJxfh();
        this.jxbf = ewc.getJxbf();
        this.sqrs = ewc.getSqrs();
        this.yxrs = ewc.getYxrs();
        this.sxrs = ewc.getSxrs();
        this.batchNo = ewc.getBatchNo();
        this.startBatchNo = ewc.getStartBatchNo();
        this.endBatchNo = ewc.getEndBatchNo();
        this.officeName = ewc.getOfficeName();
        this.officeId = ewc.getOfficeId();
        this.declareType = ewc.getDeclareType();
    }
}
