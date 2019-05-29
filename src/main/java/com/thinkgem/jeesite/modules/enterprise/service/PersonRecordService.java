/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.enterprise.dao.PersonRecordDao;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkersCount;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkersCountExport;
import com.thinkgem.jeesite.modules.enterprise.entity.PersonRecord;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 人员申请单Service
 * @author xqg
 * @version 2018-05-05
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class PersonRecordService extends CrudService<PersonRecordDao, PersonRecord> {

	@Autowired
	private PersonRecordDao personRecordDao;
	
	@Transactional(readOnly=false)
	public void updateBatchStatus(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		this.personRecordDao.updateBatchStatus(personRecord);
	}
	@Transactional(readOnly=false)
	public void updateBatchStatusByBid(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		this.personRecordDao.updateBatchStatusByBid(personRecord);
	}
	
	@Transactional(readOnly=false)
	public void updateSecondCexpertId(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		this.personRecordDao.updateSecondCexpertId(personRecord);
	}
	
	@Transactional(readOnly=false)
	public void updateFirstCexpertId(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		this.personRecordDao.updateFirstCexpertId(personRecord);
	}

	@Transactional(readOnly=false)
	public void updateSecondZexpertId(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		this.personRecordDao.updateSecondZexpertId(personRecord);
	}
	
	@Transactional(readOnly=false)
	public void updateFirstZexpertId(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		this.personRecordDao.updateFirstZexpertId(personRecord);
	}

	public List<PersonRecord> findByPersonId(String id) {
		// TODO Auto-generated method stub
		List<PersonRecord> list = this.personRecordDao.findByPersonId(id);
		return list;
	}

	public List<PersonRecord> findById(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		List<PersonRecord> list = this.personRecordDao.findById(personRecord);
		return list;
	}

	public List<PersonRecord> findByBatchId(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		List<PersonRecord> list = this.personRecordDao.findByBatchId(personRecord);
		return list;
	}

	@Transactional(readOnly=false)
	public void updateDeclareStatusNew(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		this.personRecordDao.updateDeclareStatusNew(personRecord);
	}
	@Transactional(readOnly=false)
	public void updateDeclareStatusBatch(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		this.personRecordDao.updateDeclareStatusBatch(personRecord);
	}
	
	@Transactional(readOnly = false)
	public String updateReturnReason(PersonRecord personRecord) {
		String flag = "0" ;//判断SQL是否执行成功
		try{
			// TODO Auto-generated method stub
			this.personRecordDao.updateReturnReason(personRecord);
			flag = "1";
			return flag;
		}catch(Exception e){
			return flag;
		}
	}

	@Transactional(readOnly = false)
	public String updateLocalReceiveReturnReason(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		String flag = "0" ;//判断SQL是否执行成功
		try{
			// TODO Auto-generated method stub
			this.personRecordDao.updateLocalReceiveReturnReason(personRecord);
			flag = "1";
			return flag;
		}catch(Exception e){
			return flag;
		}
	}

	@Transactional(readOnly = false)
	public void updateIsPunish(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		this.personRecordDao.updateIsPunish(personRecord);
	}

	public PersonRecord findByPersonId01(String workers_id) {
		// TODO Auto-generated method stub
		PersonRecord personRecord = this.personRecordDao.findByPersonId01(workers_id);
		return personRecord;
	}
	
	public Page<PersonRecord> zWaitPublicApplyList(Page<PersonRecord> page, PersonRecord personRecord) {
		// TODO Auto-generated method stub
		personRecord.setPage(page);
		page.setList(this.personRecordDao.zWaitPublicApplyList(personRecord));
		return page;
	}
	

	/**
	 *
	 * @param personRecord
	 * @return
	 */
	public PersonRecord getCreateCertificatePersonReocrd( PersonRecord personRecord) {
		Page<PersonRecord> page = new Page<PersonRecord>();
		page.setPageSize(1);
		personRecord.setPage(page);
		page.setList(this.personRecordDao.getAvailablePersonReocrd(personRecord));
		List<PersonRecord> list = page.getList();
		if(list!=null && list.size()>0 ){
			return  list.get(0);
		}
		return null;
	}
	
	/*
	 * 更新退回类型状态
	 */
	@Transactional(readOnly = false)
	public void updateReturnType(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		this.personRecordDao.updateReturnType(personRecord);
	}
	
	@Transactional(readOnly = false)
	public void updatefExpertId(PersonRecord personRecord) {
		// TODO Auto-generated method stub
		this.personRecordDao.updatefExpertId(personRecord);
	}
	
	@Transactional(readOnly = false)
	public void bdgonggao(Map map) {
		// TODO Auto-generated method stub
		this.personRecordDao.bdgonggao(map);
	}
	@Transactional(readOnly = false)
	public void chushigonggao(Map map) {
		// TODO Auto-generated method stub
		this.personRecordDao.chushigonggao(map);
	}
	@Transactional(readOnly = false)
	public void dzgonggao(Map map) {
		// TODO Auto-generated method stub
		this.personRecordDao.dzgonggao(map);
	}
	@Transactional(readOnly = false)
	public void jxgonggao(Map map) {
		// TODO Auto-generated method stub
		this.personRecordDao.jxgonggao(map);
	}
	@Transactional(readOnly = false)
	public void zxgonggao(Map map) {
		// TODO Auto-generated method stub
		this.personRecordDao.zxgonggao(map);
	}

	/**
	 * 根据batchno与office_id查询所有personRecord,enterpriseWorkers的相关统计数
	 * @return
	 */
	public Page<EnterpriseWorkersCount> countForPersonRecord(Page<EnterpriseWorkersCount> page, EnterpriseWorkersCount enterpriseWorkersCount){
		enterpriseWorkersCount.setPage(page);
		page.setList(this.personRecordDao.countForPersonRecord(enterpriseWorkersCount));
		return page;
	}
	public List<EnterpriseWorkersCount> count(EnterpriseWorkersCount enterpriseWorkersCount){
		return personRecordDao.countForPersonRecord(enterpriseWorkersCount);
	}
	/**
	 * 导出工具
	 */
	@SuppressWarnings("deprecation")
	public HSSFWorkbook export(List<EnterpriseWorkersCountExport> list,List<String> totalDataList) {

		// 声明一个工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = wb.createSheet("EnterpriseWorkersCountExportDataReport");

		// 生成一种样式
		HSSFCellStyle style = wb.createCellStyle();
		// 设置样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// 生成一种字体
		HSSFFont font = wb.createFont();
		// 设置字体
		font.setFontName("微软雅黑");
		// 设置字体大小
		font.setFontHeightInPoints((short) 12);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 在样式中引用这种字体
		style.setFont(font);

		// 生成并设置另一个样式
		HSSFCellStyle style2 = wb.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// 生成另一种字体2
		HSSFFont font2 = wb.createFont();
		// 设置字体
		font2.setFontName("微软雅黑");
		// 设置字体大小
		font2.setFontHeightInPoints((short) 12);
		// 字体加粗
		// font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 在样式2中引用这种字体
		style2.setFont(font2);


		// 声明String数组，并初始化元素（表头名称）
		/**第一行表头字段，合并单元格时字段跨几列就将该字段重复几次*/
		String[] excelHeader0 = {
				"终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表","终审各批次评审情况统计表",
				"终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表",
				"终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表",
				"终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表", "终审各批次评审情况统计表" };
		//  “0,2,0,0”  ===>  “起始行，截止行，起始列，截止列”
		String[] headnum0 = {  "0,0,0,21" };

		// 生成表格的第一行
		// 第一行表头
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < excelHeader0.length; i++) {

			sheet.autoSizeColumn(i, true);// 根据字段长度自动调整列的宽度
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHeader0[i]);
			cell.setCellStyle(style);

			if (i >= 0 && i <= 21) {
				for (int j = 0; j < excelHeader0.length; j++) {
					// 从第j列开始填充
					cell = row.createCell(j);
					// 填充excelHeader1[j]第j个元素
					cell.setCellValue(excelHeader0[j]);
					cell.setCellStyle(style);
				}
			}
		}

		// 动态合并单元格
		for (int i = 0; i < headnum0.length; i++) {
			sheet.autoSizeColumn(i, true);
			String[] temp = headnum0[i].split(",");
			Integer startrow = Integer.parseInt(temp[0]);
			Integer overrow = Integer.parseInt(temp[1]);
			Integer startcol = Integer.parseInt(temp[2]);
			Integer overcol = Integer.parseInt(temp[3]);
			/*if (!(startrow.equals(overrow) && startcol.equals(overcol))) {sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));}*/
			sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
		}


		/**第二行表头字段*/
		String[] excelHeader1 = { "序号","归属地","申请人数", "申请人数", "申请人数", "初始登记",
				"初始登记", "初始登记", "初始登记", "变更专业", "变更专业",
				"变更专业", "变更专业", "变更单位","变更单位","变更单位",
				"继续登记","继续登记","继续登记","注销登记","注销登记","注销登记" };
		// 合并单元格
		String[] headnum1 = {"1,2,0,0", "1,2,1,1", "1,1,2,4", "1,1,5,8",
				"1,1,9,12", "1,1,13,15", "1,1,16,18","1,1,19,21" };
		// 第二行表头
		row = sheet.createRow(1);
		for (int i = 0; i < excelHeader1.length; i++) {

			sheet.autoSizeColumn(i, true);// 自动调整宽度
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHeader1[i]);
			cell.setCellStyle(style);

			if (i >= 0 && i <= 21) {
				for (int j = 0; j < excelHeader1.length; j++) {
					// 从第j列开始填充
					cell = row.createCell(j);
					// 填充excelHeader1[j]第j个元素
					cell.setCellValue(excelHeader1[j]);
					cell.setCellStyle(style);
				}
			}
		}
		// 动态合并单元格
		for (int i = 0; i < headnum1.length; i++) {

			sheet.autoSizeColumn(i, true);
			String[] temp = headnum1[i].split(",");
			Integer startrow = Integer.parseInt(temp[0]);
			Integer overrow = Integer.parseInt(temp[1]);
			Integer startcol = Integer.parseInt(temp[2]);
			Integer overcol = Integer.parseInt(temp[3]);
			sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
		}



		//第三行表头字段，其中的空的双引号是为了补全表格边框
		String[] excelHeader2 = {  "","", "申 请", "有 效", "失 效", "符 合", "申 请", "部分符合", "不符合", "符 合",
				"申 请", "部分符合", "不符合", "符 合","申 请","不符合","符 合", "申 请","不符合","符 合","申 请","不符合" };

		String[] headnum2 = { "2,2,2,2", "2,2,3,3", "2,2,4,4", "2,2,5,5", "2,2,6,6", "2,2,7,7", "2,2,8,8", "2,2,9,9",
				"2,2,10,10", "2,2,11,11", "2,2,12,12", "2,2,13,13", "2,2,14,14", "2,2,15,15", "2,2,16,16", "2,2,17,17",
				"2,2,18,18", "2,2,19,19", "2,2,20,20", "2,2,21,21" };

		// 第三行表头
		row = sheet.createRow(2);
		for (int i = 0; i < excelHeader2.length; i++) {

			HSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHeader2[i]);
			cell.setCellStyle(style);
			// System.out.println(excelHeader2[i]);
			sheet.autoSizeColumn(i, true);// 自动调整宽度

			if (i > 0 && i <= 21) {
				for (int j = 0; j < excelHeader2.length; j++) {
					// 从第j+2列开始填充
					cell = row.createCell(j);
					// 填充excelHeader1[j]第j个元素
					cell.setCellValue(excelHeader2[j]);
					cell.setCellStyle(style);
				}
			}
		}
//		// 动态合并单元格
		for (int i = 0; i < headnum2.length; i++) {

			sheet.autoSizeColumn(i, true);
			String[] temp = headnum2[i].split(",");
			Integer startrow = Integer.parseInt(temp[0]);
			Integer overrow = Integer.parseInt(temp[1]);
			Integer startcol = Integer.parseInt(temp[2]);
			Integer overcol = Integer.parseInt(temp[3]);
			sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
		}

		String [] sjcs = {}; //公用定义数组
		String bHno = ""; //批次
		String subBatchNoY = ""; //年份切分
		String subBatchNoN = ""; //月份切分
        int tdlCount = 0;
        // 第4行开始数据
        int r= 0; // 数据插入行
		for (int i = 0; i < list.size(); i++) {
			EnterpriseWorkersCountExport report = list.get(i);
			if ( !bHno.equals(list.get(i).getBatchNo()) ) {
				// 第一次不动，第二次开始行数加2开始插入数据，不然会盖掉上一条的最后两行数据
			    // 第四行
                subBatchNoY = list.get(i).getBatchNo().substring(0,4);
                subBatchNoN = list.get(i).getBatchNo().substring(4,6);
                String[] batchData1 = {
						"批次","批次",subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",
						subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",
						subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",
						subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",
						subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月",subBatchNoY+"年"+subBatchNoN+"月"
				};
				String[] batchIndex1 = { (i+3+r)+","+(i+3+r)+",0,1",(i+3+r)+","+(i+3+r)+",2,21" };
				// 第四行表头
				row = sheet.createRow(i+3+r);
				sjcs = batchData1;
				for (int k = 0; k < sjcs.length; k++) {

					sheet.autoSizeColumn(k, true);// 自动调整宽度
					HSSFCell cell = row.createCell(k);
					cell.setCellValue(sjcs[k]);
					cell.setCellStyle(style);

					if (k >= 0 && k <= 21) {
						for (int j = 0; j < sjcs.length; j++) {
							// 从第j+1列开始填充
							cell = row.createCell(j);
							// 填充excelHeader1[j]第j个元素
							cell.setCellValue(sjcs[j]);
							cell.setCellStyle(style);
						}
					}
				}
				// 动态合并单元格
				sjcs = batchIndex1;
				for (int k = 0; k < sjcs.length; k++) {

					sheet.autoSizeColumn(k, true);
					String[] temp = sjcs[k].split(",");
					Integer startrow = Integer.parseInt(temp[0]);
					Integer overrow = Integer.parseInt(temp[1]);
					Integer startcol = Integer.parseInt(temp[2]);
					Integer overcol = Integer.parseInt(temp[3]);
					sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
				}

				String[] totalData1 = {
						"总计", "总计", totalDataList.get(tdlCount)
						, totalDataList.get(tdlCount+1), totalDataList.get(tdlCount+2),
                        totalDataList.get(tdlCount+3),totalDataList.get(tdlCount+4),totalDataList.get(tdlCount+5),totalDataList.get(tdlCount+6),
						totalDataList.get(tdlCount+7),totalDataList.get(tdlCount+8),totalDataList.get(tdlCount+9),totalDataList.get(tdlCount+10),
						totalDataList.get(tdlCount+11),totalDataList.get(tdlCount+12),totalDataList.get(tdlCount+13),
                        totalDataList.get(tdlCount+14),totalDataList.get(tdlCount+15),totalDataList.get(tdlCount+16),
						totalDataList.get(tdlCount+17),totalDataList.get(tdlCount+18),totalDataList.get(tdlCount+19)
				};tdlCount+=20;
				String[] totalIndex1 = {(i+4+r)+","+(i+4+r)+",0,1",
						(i+4+r)+","+(i+4+r)+",2,2",(i+4+r)+","+(i+4+r)+",3,3",(i+4+r)+","+(i+4+r)+",4,4",(i+4+r)+","+(i+4+r)+",5,5",(i+4+r)+","+(i+4+r)+",4,4",(i+4+r)+","+(i+4+r)+",7,7",
						(i+4+r)+","+(i+4+r)+",8,8",(i+4+r)+","+(i+4+r)+",9,9",(i+4+r)+","+(i+4+r)+",10,10",(i+4+r)+","+(i+4+r)+",11,11",(i+4+r)+","+(i+4+r)+",12,12",(i+4+r)+","+(i+4+r)+",13,13",
						(i+4+r)+","+(i+4+r)+",14,14",(i+4+r)+","+(i+4+r)+",15,15",(i+4+r)+","+(i+4+r)+",16,16",(i+4+r)+","+(i+4+r)+",17,17",(i+4+r)+","+(i+4+r)+",18,18",(i+4+r)+","+(i+4+r)+",19,19",
						(i+4+r)+","+(i+4+r)+",20,20",(i+4+r)+","+(i+4+r)+",21,21"};
				//
				row = sheet.createRow(i+4+r);
				sjcs = totalData1;
				for (int k = 0; k < sjcs.length; k++) {

					sheet.autoSizeColumn(k, true);// 自动调整宽度
					HSSFCell cell = row.createCell(k);
					cell.setCellValue(sjcs[k]);
					cell.setCellStyle(style);

					if (k >= 0 && k <= 21) {
						for (int j = 0; j < sjcs.length; j++) {
							cell = row.createCell(j);
							// 填充excelHeader1[j]第j个元素
							cell.setCellValue(sjcs[j]);
							cell.setCellStyle(style);
						}
					}
				}
				// 动态合并单元格
				sjcs = totalIndex1;
				for (int k = 0; k < sjcs.length; k++) {

					sheet.autoSizeColumn(k, true);
					String[] temp = sjcs[k].split(",");
					Integer startrow = Integer.parseInt(temp[0]);
					Integer overrow = Integer.parseInt(temp[1]);
					Integer startcol = Integer.parseInt(temp[2]);
					Integer overcol = Integer.parseInt(temp[3]);
					sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
				}
			}

			bHno = list.get(i).getBatchNo();
			if ( bHno.equals(list.get(i).getBatchNo()) ) {
				row = sheet.createRow(i+5+r);
				// 导入对应列的数据
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(report.getIndex());
				cell.setCellStyle(style2);

				HSSFCell cell1 = row.createCell(1);
				cell1.setCellValue(report.getOfficeName());
				cell1.setCellStyle(style2);

				HSSFCell cell2 = row.createCell(2);
				cell2.setCellValue(report.getSqrs());
				cell2.setCellStyle(style2);
				HSSFCell cell3 = row.createCell(3);
				cell3.setCellValue(report.getYxrs());
				cell3.setCellStyle(style2);
				HSSFCell cell4 = row.createCell(4);
				cell4.setCellValue(report.getSxrs());
				cell4.setCellStyle(style2);

				HSSFCell cell5 = row.createCell(5);
				cell5.setCellValue(report.getCsfh());
				cell5.setCellStyle(style2);
				HSSFCell cell6 = row.createCell(6);
				cell6.setCellValue(report.getCsdj());
				cell6.setCellStyle(style2);
				HSSFCell cell7 = row.createCell(7);
				cell7.setCellValue(report.getCsbffh());
				cell7.setCellStyle(style2);
				HSSFCell cell8 = row.createCell(8);
				cell8.setCellValue(report.getCsbf());
				cell8.setCellStyle(style2);

				HSSFCell cell9 = row.createCell(9);
				cell9.setCellValue(report.getZyfh());
				cell9.setCellStyle(style2);
				HSSFCell cell10 = row.createCell(10);
				cell10.setCellValue(report.getBgzy());
				cell10.setCellStyle(style2);
				HSSFCell cell11 = row.createCell(11);
				cell11.setCellValue(report.getZybffh());
				cell11.setCellStyle(style2);
				HSSFCell cell12 = row.createCell(12);
				cell12.setCellValue(report.getZybf());
				cell12.setCellStyle(style2);

				HSSFCell cell13 = row.createCell(13);
				cell13.setCellValue(report.getDwfh());
				cell13.setCellStyle(style2);
				HSSFCell cell14 = row.createCell(14);
				cell14.setCellValue(report.getBgdw());
				cell14.setCellStyle(style2);
				HSSFCell cell15 = row.createCell(15);
				cell15.setCellValue(report.getDwbf());
				cell15.setCellStyle(style2);

				HSSFCell cell16 = row.createCell(16);
				cell16.setCellValue(report.getJxfh());
				cell16.setCellStyle(style2);
				HSSFCell cell17 = row.createCell(17);
				cell17.setCellValue(report.getJxdj());
				cell17.setCellStyle(style2);
				HSSFCell cell18 = row.createCell(18);
				cell18.setCellValue(report.getJxbf());
				cell18.setCellStyle(style2);

				HSSFCell cell19 = row.createCell(19);
				cell19.setCellValue(report.getZxfh());
				cell19.setCellStyle(style2);
				HSSFCell cell20 = row.createCell(20);
				cell20.setCellValue(report.getZxdj());
				cell20.setCellStyle(style2);
				HSSFCell cell21 = row.createCell(21);
				cell21.setCellValue(report.getZxbf());
				cell21.setCellStyle(style2);
			}
            if (i+1<list.size()) {  // 防止下标越界
                if (!bHno.equals(list.get(i + 1).getBatchNo())) { //判断从第几行开始插入数据
                    r = r + 2;
                }
            }
		}

		return wb;
	}


}