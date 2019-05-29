/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.signature.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 电子签章业绩Entity
 * @author xqg
 * @version 2018-09-03
 */
public class ApplySignature extends DataEntity<ApplySignature> {
	
	private static final long serialVersionUID = 1L;
	private String projectName;		// 项目名称
	private String services;		// 服务范围
	private String childServices;		// 服务范围小类
	private String area;		// 地区
	private String planGrade;		// 规划级别
	private String enterpriseId;		// 企业帐号Id
	private Date completeDate;		// 完成时间
	private String projectSpecialty;		// 项目专业
	private String projectSpecialtyChild;  // 项目专业子项
	private BigDecimal projectInvestAmount;	// 建设规模或投资额
	private BigDecimal projectAbroadAmount;// 项目境外投资额
	private BigDecimal projectAddSubAmount;// 核增减投资额
	private String fundsSource;				// 资金来源
	private Date planBeginDate;            // 拟开工时间
	private String approvalOrg;            // 审批机构
	private String veto; 					// 是否否决
    private String projectProperty;         //项目性质

	private String status;		            // 申请状态  0:未提交 1：提交后待签章  2：签章完毕
	private String signatureFilePath;       // 签章文件路径 相对路径
	private String boundary ;				// 境内或境外
	private String boundaryCase;            // 境内或境外二级
	private String contractNum;             // 合同号或批复号
	private String lockPerson;   			 // 当前签章人
	private Long lockTime;                  // 锁定时间

	private String  buildScale;             //建设规模
	private String  officeId;             //区域ID
	private String  userModel;             //区域ID
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getUserModel() {
		return userModel;
	}

	public void setUserModel(String userModel) {
		this.userModel = userModel;
	}

	
//	用于前面传输数据
	private String[] personIds;
	private String[] dutys;
	
	public ApplySignature() {
		super();
	}

	public ApplySignature(String id){
		super(id);
	}

	@Length(min=0, max=200, message="项目名称长度必须介于 0 和 200 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=0, max=10, message="服务范围不能为空")
	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}
	
	@Length(min=0, max=10, message="服务范围小类长度必须介于 0 和 2 之间")
	public String getChildServices() {
		return childServices;
	}

	public void setChildServices(String childServices) {
		this.childServices = childServices;
	}
	
	@Length(min=0, max=2, message="地区长度必须介于 0 和 2 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=0, max=2, message="规划级别长度必须介于 0 和 2 之间")
	public String getPlanGrade() {
		return planGrade;
	}

	public void setPlanGrade(String planGrade) {
		this.planGrade = planGrade;
	}
	
	//@Length(min=0, max=32, message="企业帐号Id长度必须介于 0 和 32 之间")
	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	
	@Length(min=0, max=2, message="项目专业长度必须介于 0 和 2 之间")
	public String getProjectSpecialty() {
		return projectSpecialty;
	}

	public void setProjectSpecialty(String projectSpecialty) {
		this.projectSpecialty = projectSpecialty;
	}
	
	public BigDecimal getProjectInvestAmount() {
		return projectInvestAmount;
	}

	public void setProjectInvestAmount(BigDecimal projectInvestAmount) {
		this.projectInvestAmount = projectInvestAmount;
	}
	
	//@Length(min=0, max=1, message="申请状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getPersonIds() {
		return personIds;
	}

	public void setPersonIds(String[] personIds) {
		this.personIds = personIds;
	}

	public String[] getDutys() {
		return dutys;
	}

	public void setDutys(String[] dutys) {
		this.dutys = dutys;
	}

	public String getSignatureFilePath() {
		return signatureFilePath;
	}

	public void setSignatureFilePath(String signatureFilePath) {
		this.signatureFilePath = signatureFilePath;
	}

	public String getBoundary() {
		return boundary;
	}

	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}

	public String getBoundaryCase() {
		return boundaryCase;
	}

	public void setBoundaryCase(String boundaryCase) {
		this.boundaryCase = boundaryCase;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getLockPerson() {
		return lockPerson;
	}

	public void setLockPerson(String lockPerson) {
		this.lockPerson = lockPerson;
	}

	public Long getLockTime() {
		return lockTime;
	}

	public void setLockTime(Long lockTime) {
		this.lockTime = lockTime;
	}

	public String getBuildScale() {
		return buildScale;
	}

	public void setBuildScale(String buildScale) {
		this.buildScale = buildScale;
	}

	public String getProjectSpecialtyChild() {
		return projectSpecialtyChild;
	}

	public void setProjectSpecialtyChild(String projectSpecialtyChild) {
		this.projectSpecialtyChild = projectSpecialtyChild;
	}

    public BigDecimal getProjectAbroadAmount() {
        return projectAbroadAmount;
    }

    public void setProjectAbroadAmount(BigDecimal projectAbroadAmount) {
        this.projectAbroadAmount = projectAbroadAmount;
    }

    public BigDecimal getProjectAddSubAmount() {
        return projectAddSubAmount;
    }

    public void setProjectAddSubAmount(BigDecimal projectAddSubAmount) {
        this.projectAddSubAmount = projectAddSubAmount;
    }

    public String getFundsSource() {
        return fundsSource;
    }

    public void setFundsSource(String fundsSource) {
        this.fundsSource = fundsSource;
    }

    public Date getPlanBeginDate() {
        return planBeginDate;
    }

    public void setPlanBeginDate(Date planBeginDate) {
        this.planBeginDate = planBeginDate;
    }

    public String getApprovalOrg() {
        return approvalOrg;
    }

    public void setApprovalOrg(String approvalOrg) {
        this.approvalOrg = approvalOrg;
    }

    public String getVeto() {
        return veto;
    }

    public void setVeto(String veto) {
        this.veto = veto;
    }

    public String getProjectProperty() {
        return projectProperty;
    }

    public void setProjectProperty(String projectProperty) {
        this.projectProperty = projectProperty;
    }
}