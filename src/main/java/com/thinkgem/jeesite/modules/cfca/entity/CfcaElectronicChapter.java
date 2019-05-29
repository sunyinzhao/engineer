/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cfca.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * CFCA电子签章申请结果 Entity
 * @author xqg
 * @version 2018-09-03
 */
public class CfcaElectronicChapter   extends DataEntity<CfcaElectronicChapter> {
	
	private static final long serialVersionUID = 1L;
	
	private String dn;             	// 证书DN   512
	private String sequenceNo; 	  	// 证书序号      8
	private String serialNo;      	// 证书序列号40
	private String authCode;      	// 授权码 160
	private Date startTime;     	// 开始时间 14
	private Date endTime;      	// 结束时间 14
	private String resultCode;      // 结果代码 成功为“0000” 8
    private String resultMessage;   // 结果信息 成功为“成功”  200
	private String status;			 //证书状态(与CFCA一致)    未下载	3	激活	4	冻结	5 	吊销	6
	private String ukeyId ;         // Ukey硬件Id


	private String userId;   		// 用户Id
    private String chapterImage;//图片url

	private EnterpriseWorkers worker ;  //拥有证书的咨询师
	private User company;   //咨询师所有在的企业

    
	/**
	 * @return the chapterImage
	 */
	public String getChapterImage() {
		return chapterImage;
	}
	/**
	 * @param chapterImage the chapterImage to set
	 */
	public void setChapterImage(String chapterImage) {
		this.chapterImage = chapterImage;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public EnterpriseWorkers getWorker() {
		return worker;
	}

	public void setWorker(EnterpriseWorkers worker) {
		this.worker = worker;
	}

    public User getCompany() {
        return company;
    }

    public void setCompany(User company) {
        this.company = company;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUkeyId() {
		return ukeyId;
	}
	public void setUkeyId(String ukeyId) {
		this.ukeyId = ukeyId;
	}
}