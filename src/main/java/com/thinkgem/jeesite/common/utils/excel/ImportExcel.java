/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 导入Excel文件（支持“XLS”和“XLSX”格式）
 * @author ThinkGem
 * @version 2013-03-10
 */
public class ImportExcel {
	
	private static Logger log = LoggerFactory.getLogger(ImportExcel.class);
			
	/**
	 * 工作薄对象
	 */
	private Workbook wb;
	
	/**
	 * 工作表对象
	 */
	private Sheet sheet;
	
	/**
	 * 标题行号
	 */
	private int headerNum;
	
	/**
	 * 构造函数
	 * @param path 导入文件，读取第一个工作表
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 */
	public ImportExcel(String fileName, int headerNum) 
			throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum);
	}
	
	/**
	 * 构造函数
	 * @param path 导入文件对象，读取第一个工作表
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 */
	public ImportExcel(File file, int headerNum) 
			throws InvalidFormatException, IOException {
		this(file, headerNum, 0);
	}

	/**
	 * 构造函数
	 * @param path 导入文件
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 */
	public ImportExcel(String fileName, int headerNum, int sheetIndex) 
			throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum, sheetIndex);
	}
	
	/**
	 * 构造函数
	 * @param path 导入文件对象
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 */
	public ImportExcel(File file, int headerNum, int sheetIndex) 
			throws InvalidFormatException, IOException {
		this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
	}
	
	/**
	 * 构造函数
	 * @param file 导入文件对象
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 */
	public ImportExcel(MultipartFile multipartFile, int headerNum, int sheetIndex) 
			throws InvalidFormatException, IOException {
		this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
	}

	/**
	 * 构造函数
	 * @param path 导入文件对象
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 */
	public ImportExcel(String fileName, InputStream is, int headerNum, int sheetIndex) 
			throws InvalidFormatException, IOException {
		if (StringUtils.isBlank(fileName)){
			throw new RuntimeException("导入文档为空!");
		}else if(fileName.toLowerCase().endsWith("xls")){    
			this.wb = new HSSFWorkbook(is);    
        }else if(fileName.toLowerCase().endsWith("xlsx")){  
        	this.wb = new XSSFWorkbook(is);
        }else{  
        	throw new RuntimeException("文档格式不正确!");
        }  
		if (this.wb.getNumberOfSheets()<sheetIndex){
			throw new RuntimeException("文档中没有工作表!");
		}
		this.sheet = this.wb.getSheetAt(sheetIndex);
		this.headerNum = headerNum;
		log.debug("Initialize success.");
	}
	
	/**
	 * 获取行对象
	 * @param rownum
	 * @return
	 */
	public Row getRow(int rownum){
		return this.sheet.getRow(rownum);
	}

	/**
	 * 获取数据行号
	 * @return
	 */
	public int getDataRowNum(){
		return headerNum+1;
	}
	
	/**
	 * 获取最后一个数据行号
	 * @return
	 */
	public int getLastDataRowNum(){
		return this.sheet.getLastRowNum()+headerNum;
	}
	
	/**
	 * 获取最后一个列号
	 * @return
	 */
	public int getLastCellNum(){
		return this.getRow(headerNum).getLastCellNum();
	}
	
	/**
	 * 获取单元格值
	 * @param row 获取的行
	 * @param column 获取单元格列号
	 * @return 单元格值
	 */
	public Object getCellValue(Row row, int column){
		Object val = "";
		try{
			Cell cell = row.getCell(column);
			if (cell != null){
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
					val = cell.getNumericCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_STRING){
					val = cell.getStringCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA){
					val = cell.getCellFormula();
				}else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
					val = cell.getBooleanCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_ERROR){
					val = cell.getErrorCellValue();
				}
			}
		}catch (Exception e) {
			return val;
		}
		return val;
	}
	
	public Object getCellValue(Row row, int column, int type){
		Object val = "";
		try{
			Cell cell = row.getCell(column);
			if (cell != null){
				if (type == Cell.CELL_TYPE_NUMERIC){
					val = cell.getNumericCellValue();
				}else if (type == Cell.CELL_TYPE_STRING){
					val = cell.getStringCellValue();
				}else if (type == Cell.CELL_TYPE_FORMULA){
					val = cell.getCellFormula();
				}else if (type == Cell.CELL_TYPE_BOOLEAN){
					val = cell.getBooleanCellValue();
				}else if (type == Cell.CELL_TYPE_ERROR){
					val = cell.getErrorCellValue();
				}
				
				
			}
		}catch (Exception e) {
			return val;
		}
		return val;
	}
	
	/**
	 * 获取导入数据列表
	 * @param cls 导入对象类型
	 * @param groups 导入分组
	 */
	public <E> List<E> getDataList(Class<E> cls, int... groups) throws InstantiationException, IllegalAccessException{
		List<Object[]> annotationList = Lists.newArrayList();
		// Get annotation field 
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs){
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==2)){
				if (groups!=null && groups.length>0){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, f});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, f});
				}
			}
		}
		// Get annotation method
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms){
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==2)){
				if (groups!=null && groups.length>0){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, m});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, m});
				}
			}
		}
		// Field sorting
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField)o1[0]).sort()).compareTo(
						new Integer(((ExcelField)o2[0]).sort()));
			};
		});
		//log.debug("Import column count:"+annotationList.size());
		// Get excel data
		List<E> dataList = Lists.newArrayList();
		for (int i = this.getDataRowNum(); i < this.getLastDataRowNum(); i++) {
			E e = (E)cls.newInstance();
			int column = 0;
			Row row = this.getRow(i);
			StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList){
				Object val = this.getCellValue(row, column++);
				if (val != null){
					ExcelField ef = (ExcelField)os[0];
					// If is dict type, get dict value
					if (StringUtils.isNotBlank(ef.dictType())){
						val = DictUtils.getDictValue(val.toString(), ef.dictType(), "");
						//log.debug("Dictionary type value: ["+i+","+colunm+"] " + val);
					}
					// Get param type and type cast
					Class<?> valType = Class.class;
					if (os[1] instanceof Field){
						valType = ((Field)os[1]).getType();
					}else if (os[1] instanceof Method){
						Method method = ((Method)os[1]);
						if ("get".equals(method.getName().substring(0, 3))){
							valType = method.getReturnType();
						}else if("set".equals(method.getName().substring(0, 3))){
							valType = ((Method)os[1]).getParameterTypes()[0];
						}
					}
					//log.debug("Import value type: ["+i+","+column+"] " + valType);
					try {
						if (valType == String.class){
							String s = String.valueOf(val.toString());
							if(StringUtils.endsWith(s, ".0")){
								val = StringUtils.substringBefore(s, ".0");
							}else{
								val = String.valueOf(val.toString());
							}
						}else if (valType == Integer.class){
							val = Double.valueOf(val.toString()).intValue();
						}else if (valType == Long.class){
							val = Double.valueOf(val.toString()).longValue();
						}else if (valType == Double.class){
							val = Double.valueOf(val.toString());
						}else if (valType == Float.class){
							val = Float.valueOf(val.toString());
						}else if (valType == Date.class){
							val = DateUtil.getJavaDate((Double)val);
						}else{
							if (ef.fieldType() != Class.class){
								val = ef.fieldType().getMethod("getValue", String.class).invoke(null, val.toString());
							}else{
								val = Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), 
										"fieldtype."+valType.getSimpleName()+"Type")).getMethod("getValue", String.class).invoke(null, val.toString());
							}
						}
					} catch (Exception ex) {
						log.info("Get cell value ["+i+","+column+"] error: " + ex.toString());
						val = null;
					}
					// set entity value
					if (os[1] instanceof Field){
						Reflections.invokeSetter(e, ((Field)os[1]).getName(), val);
					}else if (os[1] instanceof Method){
						String mthodName = ((Method)os[1]).getName();
						if ("get".equals(mthodName.substring(0, 3))){
							mthodName = "set"+StringUtils.substringAfter(mthodName, "get");
						}
						Reflections.invokeMethod(e, mthodName, new Class[] {valType}, new Object[] {val});
					}
				}
				sb.append(val+", ");
			}
			dataList.add(e);
			log.debug("Read success: ["+i+"] "+sb.toString());
		}
		return dataList;
	}

	/**
	 * 导入测试
	 */
/*	public static void main(String[] args) throws Throwable {
		
	//	ImportExcel ei = new ImportExcel("target/export.xlsx", 1);
		ImportExcel ei = new ImportExcel("D:/Work/客户导入格式.xlsx", 0);
		
		
		for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 0; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				System.out.print(val+", ");
			}
			System.out.print("\n");
		}
		
	}*/
	
	public static void main(String[] args) throws Throwable {
		//ImportExcel ei = new ImportExcel(file, 0, 0);
		//ImportExcel ei = new ImportExcel(file.getOriginalFilename(), 0);	
		/*
		ImportExcel ei = new ImportExcel("D:/Work/客户导入格式2.xlsx", 0);
		System.out.println(ei.getLastDataRowNum());
		List <CusCustom> cusList = new ArrayList<CusCustom>();
		int t=0 ;
		
		
		
		Row row0 =ei.getRow(0);	
		System.out.print(ei.getCellValue(row0, 0));
		if("家属区".equals(ei.getCellValue(row0, 0))){//区分家属区与其它区域，并进行不同的处理方式
			t=1;
		}else if("办公区".equals(ei.getCellValue(row0, 0))){
			t=2;
		}else if("特殊区".equals(ei.getCellValue(row0, 0))){
			t=3;
		}
		
		if(t!=0){
			for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
				CusCustom cusCustom = new CusCustom();
				Row row = ei.getRow(i);				
				if("基本信息".equals(ei.getCellValue(row, 0))){	
					
					//1.获取基本信息
						if(t==1){//家属区
							cusCustom.setName(ei.getCellValue(row, 1)==null?"":ei.getCellValue(row, 1).toString());
							cusCustom.setPhone(ei.getCellValue(row, 2)==null?"":ei.getCellValue(row, 2).toString());
							cusCustom.setRegion(ei.getCellValue(row, 3)==null?"":ei.getCellValue(row, 3).toString());
							cusCustom.setAddressArea(ei.getCellValue(row, 4)==null?"":ei.getCellValue(row, 4).toString());
							cusCustom.setAddressFloor(ei.getCellValue(row, 5)==null?"":ei.getCellValue(row, 5).toString());
							cusCustom.setAddressNum(ei.getCellValue(row, 6)==null?"":ei.getCellValue(row, 6).toString());
							cusCustom.setPosition(ei.getCellValue(row, 7)==null?"":ei.getCellValue(row, 7).toString());
							cusCustom.setCenter(ei.getCellValue(row, 8)==null?"":ei.getCellValue(row, 8).toString());
							cusCustom.setBuchu(ei.getCellValue(row, 9)==null?"":ei.getCellValue(row, 9).toString());
							cusCustom.setKezu(ei.getCellValue(row, 10)==null?"":ei.getCellValue(row, 10).toString());
							cusCustom.setLeaseholder(ei.getCellValue(row, 11)==null?"":ei.getCellValue(row, 11).toString());
							cusCustom.setLeaseholderPhone(ei.getCellValue(row, 12)==null?"":ei.getCellValue(row, 12).toString());
							cusCustom.setHouseProperty(ei.getCellValue(row, 13)==null?"":ei.getCellValue(row, 13).toString());
							
						}else {//办公区与特殊区
							cusCustom.setName(ei.getCellValue(row, 1)==null?"":ei.getCellValue(row, 1).toString());
							cusCustom.setPhone(ei.getCellValue(row, 2)==null?"":ei.getCellValue(row, 2).toString());
							cusCustom.setRegion(ei.getCellValue(row, 3)==null?"":ei.getCellValue(row, 3).toString());
							cusCustom.setAddressArea(ei.getCellValue(row, 4)==null?"":ei.getCellValue(row, 4).toString());
							cusCustom.setAddressFloor(ei.getCellValue(row, 5)==null?"":ei.getCellValue(row, 5).toString());
							cusCustom.setAddressNum(ei.getCellValue(row, 6)==null?"":ei.getCellValue(row, 6).toString());
							cusCustom.setPosition(ei.getCellValue(row, 7)==null?"":ei.getCellValue(row, 7).toString());
							cusCustom.setCenter(ei.getCellValue(row, 8)==null?"":ei.getCellValue(row, 8).toString());
							cusCustom.setBuchu(ei.getCellValue(row, 9)==null?"":ei.getCellValue(row, 9).toString());
							cusCustom.setKezu(ei.getCellValue(row, 10)==null?"":ei.getCellValue(row, 10).toString());
							cusCustom.setLeaseholder(ei.getCellValue(row, 11)==null?"":ei.getCellValue(row, 11).toString());
							cusCustom.setLeaseholderPhone(ei.getCellValue(row, 12)==null?"":ei.getCellValue(row, 12).toString());
							cusCustom.setHouseProperty(ei.getCellValue(row, 13)==null?"":ei.getCellValue(row, 13).toString());
						}
						
	
						
						cusList.add(cusCustom);
					//2.获取设备信息
						while((i+1) < ei.getLastDataRowNum()){
							Row equipmentRow = ei.getRow(i+1);
							if("基本信息".equals(ei.getCellValue(equipmentRow, 0))){
								break;
							}else{//设备信息
								CusEquipment cusEquipment = new CusEquipment();								
								cusEquipment.setNam(ei.getCellValue(equipmentRow, 1)==null?"":ei.getCellValue(equipmentRow, 1).toString());
								cusEquipment.setType(ei.getCellValue(equipmentRow, 2)==null?"":ei.getCellValue(equipmentRow, 2).toString());
								cusEquipment.setMac(ei.getCellValue(equipmentRow, 3)==null?"":ei.getCellValue(equipmentRow, 3).toString());
								cusEquipment.setNumber(ei.getCellValue(equipmentRow, 4)==null?"":ei.getCellValue(equipmentRow, 4).toString());								
								cusEquipment.setAssetNumber(ei.getCellValue(equipmentRow, 5)==null?"":ei.getCellValue(equipmentRow, 5).toString());
								
								cusCustom.getCusEquipmentList().add(cusEquipment);
								++i;
							}
						}
				}	
			}
		}
		System.out.println(cusList);*/
		
	}

}
