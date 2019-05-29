/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 附件Entity
 * @author xqg
 * @version 2018-05-09
 */
public class EnterpriseAttachment extends DataEntity<EnterpriseAttachment> {
	
	private static final long serialVersionUID = 1L;
	private String fileName;		// 文件名称
	private String type;		// 附件类别
	private String fileType;		// 附件类型（文件扩展名）
	private String path;		// 文件地址
	private String pid;		// 文档ID
	
	public EnterpriseAttachment() {
		super();
	}

	public EnterpriseAttachment(String id){
		super(id);
	}

	@Length(min=0, max=100, message="文件名称长度必须介于 0 和 100 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=1, message="附件类别长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=30, message="附件类型（文件扩展名）长度必须介于 0 和 30 之间")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Length(min=0, max=10000, message="文件地址长度必须介于 0 和 10000 之间")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@Length(min=0, max=32, message="文档ID长度必须介于 0 和 32 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
}