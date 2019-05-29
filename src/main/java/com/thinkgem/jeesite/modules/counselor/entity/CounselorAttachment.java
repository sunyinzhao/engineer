/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 附件Entity
 * @author xqg
 * @version 2018-05-09
 */
public class CounselorAttachment extends DataEntity<CounselorAttachment> {

	private static final long serialVersionUID = 1L;
	private String fileName;		// 文件名称
	private String type;		// 附件类别
	private String fileType;		// 附件类型（文件扩展名）
	private String path;		// 文件地址
	private String pid;		// 文档ID
	private String tableId;	//表id
	private String tableType; //表类型 . 1.初始登记, 2.变更执业单位 3.变更专业. 4.继续登记
	private String backExpertId; //退回id   退回状态,添加退回图片的时候,通过 expert的id  查找图片
	private String typeNum;//业绩图片类型 , 字典 image_type

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTypeNum() {
		return typeNum;
	}

	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}

	public String getBackExpertId() {
		return backExpertId;
	}

	public void setBackExpertId(String backExpertId) {
		this.backExpertId = backExpertId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}



	public CounselorAttachment() {
		super();
	}

	public CounselorAttachment(String id){
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

	private String deletePath; //前台删除的附件
	public String getDeletePath() {
		return deletePath;
	}
	public void setDeletePath(String deletePath) {
		this.deletePath = deletePath;
	}


}