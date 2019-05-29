package com.thinkgem.jeesite.modules.counselor.web;

//用于查询的数据打印出来

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.Excel4Record;
import com.thinkgem.jeesite.modules.counselor.service.CreateExcelService;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "${adminPath}/counselor/cerateExcel")
public class CreateExcelController extends BaseController {
    @Autowired
    private CreateExcelService createExcelService;



    public String addExcel(){
        //根据传递进来不同的类型,创建excel 并写入数据
        Map resource = createResource();
        //获取假数据进行测试



        return null;
    }

    private Map createResource(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name","老王");
        map.put("age","15");
        return null;
    }


    @RequestMapping(value = "/Excel4Record",method = RequestMethod.GET)
    @ResponseBody
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //一、从后台拿数据
        if (null == request || null == response)
        {
            return;
        }
       /* List<VipConsumes> list = null;
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        int consumesType = Integer.parseInt(request.getParameter("consumesType"));
        int conPaymentStatus =Integer.parseInt(request.getParameter("conPaymentStatus"));*/

        List<Excel4Record> list = createExcelService.excel4Record();
        //list = this.vipConsumesDao.selectByExample(example);
        //二、 数据转成excel
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");

        String fileName = "统计数据.xlsx";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 第一步：定义一个新的工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步：创建一个Sheet页
        XSSFSheet sheet = wb.createSheet("startTimeendTime");
        sheet.setDefaultRowHeight((short) (2 * 256));//设置行高
        sheet.setColumnWidth(0, 4000);//设置列宽
        sheet.setColumnWidth(1,4000);
        sheet.setColumnWidth(2,9000);
        sheet.setColumnWidth(3,3500);
        sheet.setColumnWidth(4,5000);
        sheet.setColumnWidth(5,5000);
        sheet.setColumnWidth(6,9000);
        sheet.setColumnWidth(7,9000);
        sheet.setColumnWidth(11,3000);
        sheet.setColumnWidth(12,3000);
        sheet.setColumnWidth(13,3000);
        sheet.setColumnWidth(16,9000);
        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);

        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("申请类型 ");
        cell = row.createCell(1);
        cell.setCellValue("城市省份 ");
        cell = row.createCell(2);
        cell.setCellValue("公司");
        cell = row.createCell(3);
        cell.setCellValue("咨询师姓名");
        cell = row.createCell(4);
        cell.setCellValue("咨登编号");
        cell = row.createCell(5);
        cell.setCellValue("申请时间 ");
        cell = row.createCell(6);
        cell.setCellValue("旧公司 ");
        cell = row.createCell(7);
        cell.setCellValue("新公司 ");
        cell = row.createCell(8);
        cell.setCellValue("初审专家1");
        cell = row.createCell(9);
        cell.setCellValue("初审结论1");
        cell = row.createCell(10);
        cell.setCellValue("初审专家2 ");
        cell = row.createCell(11);
        cell.setCellValue("初审结论2");
        cell = row.createCell(12);
        cell.setCellValue("终审专家1");
        cell = row.createCell(13);
        cell.setCellValue("终审结论1");
        cell = row.createCell(14);
        cell.setCellValue("终审专家2");
        cell = row.createCell(15);
        cell.setCellValue("终审结论2");
        cell = row.createCell(16);
        cell.setCellValue("专家建议");

        XSSFRow rows;
        XSSFCell cells;
        for (int i = 0; i < list.size(); i++) {
            // 第三步：在这个sheet页里创建一行
            rows = sheet.createRow(i+1);
            // 第四步：在该行创建一个单元格
            cells = rows.createCell(0);
            // 第五步：在该单元格里设置值
            cells.setCellValue(list.get(i).getDeclareType());

            cells = rows.createCell(1);
            cells.setCellValue(list.get(i).getName());
            cells = rows.createCell(2);
            cells.setCellValue(list.get(i).getCompanyName());
            cells = rows.createCell(3);
            cells.setCellValue(list.get(i).getEnterpriseName());
            cells = rows.createCell(4);
            cells.setCellValue(list.get(i).getRegisterCertificateNum());
            cells = rows.createCell(5);
            cells.setCellValue(formatDate(list.get(i).getDeclareDate()));
            cells = rows.createCell(6);
            cells.setCellValue(list.get(i).getOldValue());
            cells = rows.createCell(7);
            cells.setCellValue(list.get(i).getNewValue());
            cells = rows.createCell(8);
            cells.setCellValue(list.get(i).getCexpert1name());
            cells = rows.createCell(9);
            cells.setCellValue(list.get(i).getFirstCdeclareResult());
            cells = rows.createCell(10);
            cells.setCellValue(list.get(i).getCexpert2name());
            cells = rows.createCell(11);
            cells.setCellValue(list.get(i).getSecondCdeclareResult());
            cells = rows.createCell(12);
            cells.setCellValue(list.get(i).getZexpert1name());
            cells = rows.createCell(13);
            cells.setCellValue(list.get(i).getFirstZdeclareResult());
            cells = rows.createCell(14);
            cells.setCellValue(list.get(i).getZexpert2name());
            cells = rows.createCell(15);
            cells.setCellValue(list.get(i).getSecondZdeclareResult());
            cells = rows.createCell(16);
            cells.setCellValue(list.get(i).getAdvice());

        }

        try {
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.close();
            //wb.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    private String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String result = format.format(date);
        return result;
    }
}
