/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportforms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.reportforms.dao.FinalExpertReviewCountDao;
import com.thinkgem.jeesite.modules.reportforms.entity.FinalExpertReviewCount;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 终审专家评审情况统计表Service
 * @author
 * @version 2019-05-27
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class FinalExpertReviewCountService extends CrudService<FinalExpertReviewCountDao, FinalExpertReviewCount> {

	@Autowired
	private FinalExpertReviewCountDao finalExpertReviewCountDao;


	/**
	 * 根据batchno与type查询终审专家分配数据
	 * @return
	 */
	public Page<FinalExpertReviewCount> countByExpertReview(Page<FinalExpertReviewCount> page, FinalExpertReviewCount finalExpertReviewCount, String type){
		finalExpertReviewCount.setPage(page);
		/* 专家评审 */
		if ("1".equals(type)) {
			page.setList(this.finalExpertReviewCountDao.countByExpertReview1(finalExpertReviewCount));
		}
		/* 专家复核 */
		if ("2".equals(type)) {
			page.setList(this.finalExpertReviewCountDao.countByExpertReview2(finalExpertReviewCount));
		}
		return page;
	}
	public List<FinalExpertReviewCount> countByExpertReview(FinalExpertReviewCount finalExpertReviewCount, String type){
		List<FinalExpertReviewCount> list = new ArrayList<FinalExpertReviewCount>();
		/* 专家评审 */
		if ("1".equals(type)) {
			list = finalExpertReviewCountDao.countByExpertReview1(finalExpertReviewCount);
		}
		/* 专家复核 */
		if ("2".equals(type)) {
			list = finalExpertReviewCountDao.countByExpertReview2(finalExpertReviewCount);
		}
		return list;
	}

	/**
	 * 导出工具
	 */
	@SuppressWarnings("deprecation")
	public HSSFWorkbook export(List<FinalExpertReviewCount> list) {

		// 声明一个工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = wb.createSheet("FinalExpertReviewCountExportDataReport");

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
				"序 号","批 次", "评审阶段", "专家姓名", "初始登记", "初始登记","初始登记",
				"初始登记", "变更执业单位", "变更执业单位", "变更执业单位", "变更执业单位", "变更专业",
				"变更专业", "变更专业", "变更专业", "继续登记", "继续登记", "继续登记",
				"继续登记", "注销登记", "注销登记", "注销登记", "注销登记" };
		//  “0,2,0,0”  ===>  “起始行，截止行，起始列，截止列”
		String[] headnum0 = {  "0,1,0,0","0,1,1,1","0,1,2,2","0,1,3,3","0,0,4,7","0,0,8,11","0,0,12,15","0,0,16,19","0,0,20,23" };

		// 生成表格的第一行
		// 第一行表头
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < excelHeader0.length; i++) {

			sheet.autoSizeColumn(i, true);// 根据字段长度自动调整列的宽度
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHeader0[i]);
			cell.setCellStyle(style);

//			if (i >= 0 && i <= 23) {
				for (int j = 0; j < excelHeader0.length; j++) {
					// 从第j列开始填充
					cell = row.createCell(j);
					// 填充excelHeader1[j]第j个元素
					cell.setCellValue(excelHeader0[j]);
					cell.setCellStyle(style);
				}
//			}
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
		String[] excelHeader1 = { "","","", "", "分配数量", "符合", "不符合", "未完成数量",
				"分配数量", "符合", "不符合", "未完成数量",
				"分配数量", "符合", "不符合", "未完成数量",
				"分配数量", "符合", "不符合", "未完成数量",
				"分配数量", "符合", "不符合", "未完成数量" };
		// 合并单元格
		String[] headnum1 = { "1,1,4,4", "1,1,5,5", "1,1,6,6", "1,1,7,7", "1,1,8,8", "1,1,9,9", "1,1,10,10", "1,1,11,11",
				"1,1,12,12", "1,1,13,13", "1,1,14,14", "1,1,15,15", "1,1,16,16", "1,1,17,17", "1,1,18,18", "1,1,19,19",
				"1,1,20,20", "1,1,21,21", "1,1,22,22", "1,1,23,23" };
		// 第二行表头
		row = sheet.createRow(1);
		for (int i = 0; i < excelHeader1.length; i++) {

			sheet.autoSizeColumn(i, true);// 自动调整宽度
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHeader1[i]);
			cell.setCellStyle(style);

//			if (i >= 0 && i <= 23) {
				for (int j = 0; j < excelHeader1.length; j++) {
					// 从第j列开始填充
					cell = row.createCell(j);
					// 填充excelHeader1[j]第j个元素
					cell.setCellValue(excelHeader1[j]);
					cell.setCellStyle(style);
				}
//			}
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


		// 第3行开始数据
		for (int i = 0 ,r=1; i < list.size(); i++,r++) {
			row = sheet.createRow(2+i);
			// 导入对应列的数据
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(r);
			cell.setCellStyle(style2);
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue(list.get(i).getBatchNo());
			cell1.setCellStyle(style2);
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue(list.get(i).getReviewStage());
			cell2.setCellStyle(style2);
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue(list.get(i).getExpertName());
			cell3.setCellStyle(style2);

			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue(list.get(i).getInitialAllocatedQuantity());
			cell4.setCellStyle(style2);
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue(list.get(i).getInitialAccord());
			cell5.setCellStyle(style2);
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue(list.get(i).getInitialIncompatible());
			cell6.setCellStyle(style2);
			HSSFCell cell7 = row.createCell(7);
			cell7.setCellValue(list.get(i).getInitialUndone());
			cell7.setCellStyle(style2);

			HSSFCell cell8 = row.createCell(8);
			cell8.setCellValue(list.get(i).getUnitAllocatedQuantity());
			cell8.setCellStyle(style2);
			HSSFCell cell9 = row.createCell(9);
			cell9.setCellValue(list.get(i).getUnitAccord());
			cell9.setCellStyle(style2);
			HSSFCell cell10 = row.createCell(10);
			cell10.setCellValue(list.get(i).getUnitIncompatible());
			cell10.setCellStyle(style2);
			HSSFCell cell11 = row.createCell(11);
			cell11.setCellValue(list.get(i).getUnitUndone());
			cell11.setCellStyle(style2);

			HSSFCell cell12 = row.createCell(12);
			cell12.setCellValue(list.get(i).getSpecialtyAllocatedQuantity());
			cell12.setCellStyle(style2);
			HSSFCell cell13 = row.createCell(13);
			cell13.setCellValue(list.get(i).getSpecialtyAccord());
			cell13.setCellStyle(style2);
			HSSFCell cell14 = row.createCell(14);
			cell14.setCellValue(list.get(i).getSpecialtyIncompatible());
			cell14.setCellStyle(style2);
			HSSFCell cell15 = row.createCell(15);
			cell15.setCellValue(list.get(i).getSpecialtyUndone());
			cell15.setCellStyle(style2);

			HSSFCell cell16 = row.createCell(16);
			cell16.setCellValue(list.get(i).getContinueAllocatedQuantity());
			cell16.setCellStyle(style2);
			HSSFCell cell17 = row.createCell(17);
			cell17.setCellValue(list.get(i).getContinueAccord());
			cell17.setCellStyle(style2);
			HSSFCell cell18 = row.createCell(18);
			cell18.setCellValue(list.get(i).getContinueIncompatible());
			cell18.setCellStyle(style2);
			HSSFCell cell19 = row.createCell(19);
			cell19.setCellValue(list.get(i).getContinueUndone());
			cell19.setCellStyle(style2);

			HSSFCell cell20 = row.createCell(20);
			cell20.setCellValue(list.get(i).getLogoutAllocatedQuantity());
			cell20.setCellStyle(style2);
			HSSFCell cell21 = row.createCell(21);
			cell21.setCellValue(list.get(i).getLogoutAccord());
			cell21.setCellStyle(style2);
			HSSFCell cell22 = row.createCell(22);
			cell22.setCellValue(list.get(i).getLogoutIncompatible());
			cell22.setCellStyle(style2);
			HSSFCell cell23 = row.createCell(23);
			cell23.setCellValue(list.get(i).getLogoutUndone());
			cell23.setCellStyle(style2);
		}

		return wb;
	}



}