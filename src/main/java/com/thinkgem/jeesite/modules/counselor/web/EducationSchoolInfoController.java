package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.EducationSchoolInfo;
import com.thinkgem.jeesite.modules.counselor.entity.EducationSchoolInfoExport;
import com.thinkgem.jeesite.modules.counselor.service.EducationSchoolInfoService;
import com.thinkgem.jeesite.modules.reportforms.entity.ExportReconsiderationStatisticsFirst;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportReconsiderationStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/counselor/schoolData")
public class EducationSchoolInfoController extends BaseController {
    @Autowired
    private EducationSchoolInfoService educationSchoolInfoService;


    @RequestMapping(value = "checkschoolCode")
    @ResponseBody
    public String checkschoolCode(String schoolCode) {
        EducationSchoolInfo educationSchoolInfo = educationSchoolInfoService.findListBySchoolCode(schoolCode);
        if (educationSchoolInfo != null && !"".equals(educationSchoolInfo)) {
            return "yes";
        } else {
            return "no";
        }
    }

    @RequestMapping(value = "delEducation")
    public String delEducation(EducationSchoolInfo educationSchoolInfo) {
        educationSchoolInfoService.delete(educationSchoolInfo);
        //跳转list页面
        return "redirect:" + adminPath + "/counselor/schoolData/list";
    }

    //功能. 

    @RequestMapping(value = "list")
    public String list(EducationSchoolInfo educationSchoolInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
//        User user = UserUtils.getUser();
//        EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(user);
//        if( enterpriseWorkers != null ){
//            educationSchoolInfo.setPersonId(enterpriseWorkers.getId());
//        }
        Page<EducationSchoolInfo> page = educationSchoolInfoService.findPage(new Page<EducationSchoolInfo>(request, response), educationSchoolInfo);
        model.addAttribute("page", page);
        return "modules/counselor/EduSchoolDataList";
    }

    @RequestMapping(value = "edit")
    public String edit(String id, EducationSchoolInfo educationSchoolInfo, Model model) {
        if (id == null) {
            return "modules/counselor/EduSchoolDataEdit";
        } else {

            educationSchoolInfo = educationSchoolInfoService.get(id);
            if (educationSchoolInfo != null) {
                model.addAttribute("educationSchoolInfo", educationSchoolInfo);
            }
            return "modules/counselor/EduSchoolDataEdit";
        }
    }

    @RequestMapping(value = "save")
    public String save(String id, EducationSchoolInfo educationSchoolInfo, HttpServletResponse response) {
        PrintWriter writer = null;
        String msg = null;
        String schoolCode = educationSchoolInfo.getSchoolCode();
        try {
            writer = response.getWriter();
            if (id != null && !"".equals(id)) {
                educationSchoolInfo.setId(id);
            }else {
                EducationSchoolInfo educationSchoolInfo1 = educationSchoolInfoService.findListBySchoolCode(schoolCode);
                if (educationSchoolInfo1 != null && !"".equals(educationSchoolInfo1)) {
                    msg = "alert( '学校代码重复!!!' );history.go(-1)";
                    writer.print("<script type='text/javascript'>" + msg + "</script>");
                    writer.flush();
                    writer.close();
                    return null;
                }
            }
            if ("undefined".equals(id)) {
                educationSchoolInfo.setId(null);
            }

//            if (schoolCode == null || "".equals(schoolCode)) {
//                msg = "alert( '请输入学校代码！' );history.go(-1)";
//                writer.print("<script type='text/javascript'>" + msg + "</script>");
//                writer.flush();
//                writer.close();
//                return null;
//            }
//            if (educationSchoolInfo.getSchoolName() == null || "".equals(educationSchoolInfo.getSchoolName())) {
//                msg = "alert( '请输入学校名称！' );history.go(-1)";
//                writer.print("<script type='text/javascript'>" + msg + "</script>");
//                writer.flush();
//                writer.close();
//                return null;
//            }

            educationSchoolInfoService.save(educationSchoolInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:" + adminPath + "/counselor/schoolData/list";
    }

    /**
     * 证书验证
     * @param educationSchoolInfo
     * @param model
     * @return
     */
    @RequestMapping(value = "checkEducationalList")
    public String checkEducationalList(String flag1, String export, EducationSchoolInfo educationSchoolInfo, Model model, HttpServletRequest request, HttpServletResponse response) {
        if ("1".equals(flag1)) {
            return "modules/counselor/EduCertificateVerificationForm";
        }
        String batchNo = educationSchoolInfo.getBatchNo(); // 批次
        // 获取所有的高校信息
        EducationSchoolInfo educationinfo = new EducationSchoolInfo();
        List<EducationSchoolInfo> list = educationSchoolInfoService.findList(educationinfo);

        List<EducationSchoolInfo> conformList = new ArrayList<EducationSchoolInfo>();
        List<EducationSchoolInfoExport> expList = new ArrayList<EducationSchoolInfoExport>();
        List<EducationSchoolInfo> checkEducationalList = new ArrayList<EducationSchoolInfo>();
        if (educationSchoolInfo.getBatchNo() != null || !"".equals(educationSchoolInfo.getBatchNo())) {
            checkEducationalList = educationSchoolInfoService.findCheckEducationalList(educationSchoolInfo);
        }
//        Page<EducationSchoolInfo> page = educationSchoolInfoService.getPageList(new Page<EducationSchoolInfo>(request, response),checkEducationalList);

        // 学历编号位数验证 1--%>
        // 学校代码验证 2--%>
        // 办学类型验证 3--%>
        // 毕业年份验证 4--%>
        // 培养层次验证 5--%>
        String type = "";// 存验证符不符合的数字
        String zsNo = ""; // 证书编号
        String checkType = educationSchoolInfo.getCheckType();
        if (checkType != null) {
            String[] checkStr = checkType.split(",");
//            for (int i = 0; i < checkStr.length; i++) {
            for (String str : checkStr) {
                if (checkEducationalList.size() > 0) {

//                System.out.println(str);
                    // 如果不符合就把状态放进去存到type ，:  str6 += ","+str3;
                    // 最后循环将符合checkType等于type的放到一个集合返回前端显示
                    if ("1".equals(str)) {
                        System.out.println("学历编号位数验证");
                        /** 成人高考、全日制本科证编号（2002年之后为18位） （7-10位）毕业年份*/  //（13-18）学校对毕（结）业证书编排的序号
                        String nian = "";
                        for (int i = 0; i < checkEducationalList.size(); i++) {
                            EducationSchoolInfo info = checkEducationalList.get(i);
                            zsNo = info.getZsNo();type = info.getType();
                            if (zsNo != null && !"".equals(zsNo) && zsNo.length() > 16) {
                                nian = zsNo.substring(6,10);
                                try {
                                    if(Integer.parseInt(nian) > 2002){
                                        // 18位证书编号
                                        if (zsNo.length() != 18) {
                                            type += ",1";
                                        }
                                    }
                                    if(Integer.parseInt(nian) == 2002){
                                        // 17位或18位证书编号
                                        if (zsNo.length() != 18 && zsNo.length() != 17) {
                                            type += ",1";
                                        }
                                    }
                                    if(Integer.parseInt(nian) < 2002){
                                        // 17位证书编号
                                        if (zsNo.length() != 17) {
                                            type += ",1";
                                        }
                                    }
                                    info.setType(type);
                                } catch (NumberFormatException e) {
//                                    e.printStackTrace();
                                    System.out.println("当前数据错误，继续向下执行");
                                    type+=",1";
                                    info.setType(type);
                                }

                            }else{type+=",1";info.setType(type);}
                        }

                    }
                    if ("2".equals(str)) {
                        System.out.println("学校代码验证");
                        /** (前5位)代表代表学校代码 */
                        String code = "";
                        for (int i = 0; i < checkEducationalList.size(); i++) {
                            EducationSchoolInfo info = checkEducationalList.get(i);
                            zsNo = info.getZsNo();
                            type = info.getType();
                            if (zsNo != null && !"".equals(zsNo) && zsNo.length() > 16) {
                                code = zsNo.substring(0,5);
                                Boolean tempCode = true;
                                for (EducationSchoolInfo schoolInfo : list) { // 有没有该学校代码
                                    if ( code.equals(schoolInfo.getSchoolCode()) ) {
                                        tempCode = false;break;
                                    }
                                }
                                if (tempCode) { // 如果没有对应学校代码，type加等于",2"
                                    type+=",2"; info.setType(type);
                                }

                            }else {type+=",2"; info.setType(type);}
                        }
                    }
                    if ("3".equals(str)) {
                        System.out.println("办学类型验证");
                        /** （第六位）代表办学类型代码
                         普通高等教育1；成人高等教育5；高等教育自学考试和高等教育学历文凭考试6；网络教育为7 */
                        String xueType = "";
                        String code = "";
                        for (int i = 0; i < checkEducationalList.size(); i++) {
                            EducationSchoolInfo info = checkEducationalList.get(i);
                            zsNo = info.getZsNo();
                            type = info.getType();
                            if (zsNo != null && !"".equals(zsNo) && zsNo.length() > 16) { // 无论2002年前后证书位数都大于 16 ；不大于16就不符合
                                xueType = zsNo.substring(5,6);
                                code = zsNo.substring(0,5);
                                Boolean tempCode = true;
                                for (EducationSchoolInfo schoolInfo : list) {
                                    if ( code.equals(schoolInfo.getSchoolCode()) ) { // 要有对应学校a
                                        if ( schoolInfo.getEducationType().contains(xueType) ) { // 该高校数据不包含其办学类型，则不符合
                                            tempCode = false;break; // 符合了就让下面的if不执行，并跳出循环
                                        }
                                    }
                                   /* else {
                                        System.out.println("没有对应学校，办学类型自然不对");
                                        tempCode = true;
                                    }*/
                                }
                                if (tempCode) { //
                                    type+=",3"; info.setType(type);
                                }
                            }else {type+=",3"; info.setType(type); }
                        }
                    }
                    if ("4".equals(str)) {
                        System.out.println("毕业年份验证");
                        /** （7-10位）毕业年份 */
                        String nian = "";
                        String code = "";
                        for (int i = 0; i < checkEducationalList.size(); i++) {
                            EducationSchoolInfo info = checkEducationalList.get(i);
                            zsNo = info.getZsNo();type = info.getType();
                            if (zsNo != null && !"".equals(zsNo) && zsNo.length() > 16) {
                                nian = zsNo.substring(6,10);
                                code = zsNo.substring(0,5);
                                Boolean tempCode = true;
                                for (EducationSchoolInfo schoolInfo : list) {
                                    if ( code.equals(schoolInfo.getSchoolCode()) ) { // 要有对应学校

                                        try {
                                            if( Integer.parseInt(nian) >= Integer.parseInt(schoolInfo.getSchoolStartyear()) + 3 ){ // 证书编号年份要大于建校后3年，小于的话则添加类型
                                                tempCode = false;break;
                                            }
                                        } catch (NumberFormatException e) {
//                                    e.printStackTrace();
                                            System.out.println("当前数据错误，继续向下执行");
                                            break;
                                        }
                                    }
                                    /*else {
                                        System.out.println("没有对应学校，验年份没用");
                                        type+=",4"; info.setType(type);
                                    }*/
                                }
                                if (tempCode) { //
                                    type+=",4"; info.setType(type);
                                }
                            }else{type+=",4";info.setType(type);}
                        }

                    }
                    if ("5".equals(str)) {
                        System.out.println("培养层次验证");
                        /** （11-12位）培养层次代码  培养层次代码：博士研究生01；硕士研究生02；第二学士学位04；本科05；专科（含高职）06*/
                        String cengciType = "";
                        String code = "";
                        for (int i = 0; i < checkEducationalList.size(); i++) {
                            EducationSchoolInfo info = checkEducationalList.get(i);
                            zsNo = info.getZsNo();
                            type = info.getType();
                            if (zsNo != null && !"".equals(zsNo) && zsNo.length() > 16) { // 无论2002年前后证书位数都大于 16 ；不大于16就不符合
                                cengciType = zsNo.substring(10,12);
                                code = zsNo.substring(0,5);
                                Boolean tempCode = true;
                                for (EducationSchoolInfo schoolInfo : list) {
                                    if ( code.equals(schoolInfo.getSchoolCode()) ) { // 要有对应学校
                                        if ( schoolInfo.getEducationCode().contains(cengciType) ) { // 该高校数据不包含其办学类型，则不符合
                                            tempCode = false;break;// 对应学校包含其办学类型，不让if执行
                                        }
                                    }
                                    /*else {
                                        System.out.println("没有对应学校，培养层次自然不对");
                                        type+=",5"; info.setType(type);
                                    }*/
                                }
                                if (tempCode) { //
                                    type+=",5"; info.setType(type);
                                }
                            }else {type+=",5"; info.setType(type); }
                        }
                    }
                }
            }

//            }
        }
        // 实体中 type 的主要作用在这
        for (EducationSchoolInfo schoolInfo : checkEducationalList) {
            // type的初始值是null，用+=的话前面加上
            if (checkType != null && schoolInfo.getType() != null) {
                conformList.add(schoolInfo);
            }
        }

        if ("1".equals(export)) {
            // 导出excel
            if( conformList.size()>0 ){
                int index = 1;
                String str2 = "";
                for (EducationSchoolInfo counselor : conformList) {
                    str2 = Integer.toString(index++);
                    counselor.setIndex(str2);
                    expList.add(new EducationSchoolInfoExport(counselor));
                }
            }
            String fileName = "学历证书验证结果"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            try {
                new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 学历证书验证结果" , EducationSchoolInfoExport.class).setDataList(expList).write(response, fileName).dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

//        model.addAttribute("page",page);
        if (checkType != null) { // 验证类型要有的，必须~的~
            model.addAttribute("conformList",conformList);
        } else {
            model.addAttribute("conformList",checkEducationalList);
        }
        model.addAttribute("educationSchoolInfo",educationSchoolInfo);
        return "modules/counselor/EduCertificateVerificationForm";
    }
    /*
    证书导出
     */
    @RequestMapping(value = "checkEducationalListExport")
    public String checkEducationalListExport(EducationSchoolInfo educationSchoolInfo, Model model) {

        model.addAttribute("educationSchoolInfo",educationSchoolInfo);
        return "modules/counselor/EduCertificateVerificationForm";
    }


}
