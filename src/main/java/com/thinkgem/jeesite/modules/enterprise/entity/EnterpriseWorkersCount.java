package com.thinkgem.jeesite.modules.enterprise.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class EnterpriseWorkersCount extends DataEntity<EnterpriseWorkersCount> {
    private int index;//序号
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

    public EnterpriseWorkersCount() {
    }

    public EnterpriseWorkersCount(int index, String id, String zrc, String zxdj, String zxfh, String zxbf, String csdj, String csfh, String csbffh, String csbf, String bgdw, String dwfh, String dwbf, String bgzy, String zyfh, String zybffh, String zybf, String jxdj, String jxfh, String jxbf, String sqrs, String yxrs, String sxrs, String batchNo, String startBatchNo, String endBatchNo, String officeName, String officeId, String declareType) {
        this.index = index;
        this.id = id;
        this.zrc = zrc;
        this.zxdj = zxdj;
        this.zxfh = zxfh;
        this.zxbf = zxbf;
        this.csdj = csdj;
        this.csfh = csfh;
        this.csbffh = csbffh;
        this.csbf = csbf;
        this.bgdw = bgdw;
        this.dwfh = dwfh;
        this.dwbf = dwbf;
        this.bgzy = bgzy;
        this.zyfh = zyfh;
        this.zybffh = zybffh;
        this.zybf = zybf;
        this.jxdj = jxdj;
        this.jxfh = jxfh;
        this.jxbf = jxbf;
        this.sqrs = sqrs;
        this.yxrs = yxrs;
        this.sxrs = sxrs;
        this.batchNo = batchNo;
        this.startBatchNo = startBatchNo;
        this.endBatchNo = endBatchNo;
        this.officeName = officeName;
        this.officeId = officeId;
        this.declareType = declareType;
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

    public String getZxdj() {
        return zxdj;
    }

    public void setZxdj(String zxdj) {
        this.zxdj = zxdj;
    }

    public String getZxfh() {
        return zxfh;
    }

    public void setZxfh(String zxfh) {
        this.zxfh = zxfh;
    }

    public String getZxbf() {
        return zxbf;
    }

    public void setZxbf(String zxbf) {
        this.zxbf = zxbf;
    }

    public String getCsdj() {
        return csdj;
    }

    public void setCsdj(String csdj) {
        this.csdj = csdj;
    }

    public String getCsfh() {
        return csfh;
    }

    public void setCsfh(String csfh) {
        this.csfh = csfh;
    }

    public String getCsbffh() {
        return csbffh;
    }

    public void setCsbffh(String csbffh) {
        this.csbffh = csbffh;
    }

    public String getCsbf() {
        return csbf;
    }

    public void setCsbf(String csbf) {
        this.csbf = csbf;
    }

    public String getBgdw() {
        return bgdw;
    }

    public void setBgdw(String bgdw) {
        this.bgdw = bgdw;
    }

    public String getDwfh() {
        return dwfh;
    }

    public void setDwfh(String dwfh) {
        this.dwfh = dwfh;
    }

    public String getDwbf() {
        return dwbf;
    }

    public void setDwbf(String dwbf) {
        this.dwbf = dwbf;
    }

    public String getBgzy() {
        return bgzy;
    }

    public void setBgzy(String bgzy) {
        this.bgzy = bgzy;
    }

    public String getZyfh() {
        return zyfh;
    }

    public void setZyfh(String zyfh) {
        this.zyfh = zyfh;
    }

    public String getZybffh() {
        return zybffh;
    }

    public void setZybffh(String zybffh) {
        this.zybffh = zybffh;
    }

    public String getZybf() {
        return zybf;
    }

    public void setZybf(String zybf) {
        this.zybf = zybf;
    }

    public String getJxdj() {
        return jxdj;
    }

    public void setJxdj(String jxdj) {
        this.jxdj = jxdj;
    }

    public String getJxfh() {
        return jxfh;
    }

    public void setJxfh(String jxfh) {
        this.jxfh = jxfh;
    }

    public String getJxbf() {
        return jxbf;
    }

    public void setJxbf(String jxbf) {
        this.jxbf = jxbf;
    }

    public String getSqrs() {
        return sqrs;
    }

    public void setSqrs(String sqrs) {
        this.sqrs = sqrs;
    }

    public String getYxrs() {
        return yxrs;
    }

    public void setYxrs(String yxrs) {
        this.yxrs = yxrs;
    }

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
}
