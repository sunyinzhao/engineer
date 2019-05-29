/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.modules.counselor.dao.CreateExcelDao;
import com.thinkgem.jeesite.modules.counselor.entity.Excel4Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 咨询师不符合原因Service
 * @author hzy
 * @version 2018-08-23
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class CreateExcelService{

	@Autowired
	private CreateExcelDao createExcelDao;

	//1.创建统计数据list
	public List<Excel4Record> excel4Record(){
		return createExcelDao.excel4Record();
	}
}