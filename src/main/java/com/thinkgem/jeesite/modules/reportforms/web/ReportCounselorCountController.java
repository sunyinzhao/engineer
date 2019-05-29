package com.thinkgem.jeesite.modules.reportforms.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.reportforms.entity.*;
import com.thinkgem.jeesite.modules.reportforms.service.ReportCounselorCountService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 咨询师公示前信息报表统计
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "${adminPath}/report/counselor")
public class ReportCounselorCountController extends BaseController {

    @Autowired
    private ReportCounselorCountService reportCounselorCountService;


    /**
     * 查询某批次初始登记的人员信息，返回到前台对应页面
     */
    @RequestMapping(value = "findReportCounselorFirstInfo")
    public String findReportCounselorFirstInfo(String batchNo, String Type, ReportCounselorCount reportCounselorCount, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
        String officeId = user.getOffice().getId();
        Page<ReportCounselorCount> page = null;
        if (batchNo==null || "".equals(batchNo)) {
            return "modules/reportforms/rptCounselorFirst";
        } else {
            if(!"1".equals(user.getId())){
                reportCounselorCount.setOfficeId(officeId);
            }
            reportCounselorCount.setBatchNo(batchNo);
            if ("1".equals(Type)) {
                page = this.reportCounselorCountService.findReportCounselorFirstYesInfo(new Page<ReportCounselorCount>(request, response), reportCounselorCount);
            } else if ("3".equals(Type)) {
                page = this.reportCounselorCountService.findReportCounselorFirstNoInfo(new Page<ReportCounselorCount>(request, response), reportCounselorCount);
            } else {
                addMessage(redirectAttributes, "当前操作有误，查询失败");
            }
            model.addAttribute("page", page);
            return "modules/reportforms/rptCounselorFirst";
        }

    }
    /**
     * 初始登记人员信息导出
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportReportCounselorFirstInfoFile")
    public String exportReportCounselorFirstInfoFile(String batchNo, String Type, ReportCounselorCount reportCounselorCount, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            User user = UserUtils.getUser();
            String officeId = user.getOffice().getId();
            List<ReportCounselorCount> countList = new ArrayList<ReportCounselorCount>();
            List<ExportCounselorCountFirst> list =new ArrayList<ExportCounselorCountFirst>();

            if (user.getOffice() == null) {
            } else {

                if (user.getOffice() == null) {
                    return "modules/reportforms/rptCounselorFirst";
                } else {

                    if(!"1".equals(user.getId())){
                        reportCounselorCount.setOfficeId(officeId);
                    }
                    reportCounselorCount.setBatchNo(batchNo);
                    if ("1".equals(Type)) {
                        countList = reportCounselorCountService.findReportCounselorFirstYesInfo(reportCounselorCount);
                    } else if ("3".equals(Type)) {
                        countList = reportCounselorCountService.findReportCounselorFirstNoInfo(reportCounselorCount);
                    } else {
                        addMessage(redirectAttributes, "当前操作有误，查询失败");
                    }
                }
                if(countList!=null && countList.size()>0){
                    int index = 2;
                    String str1 = "";
                    String str2 = "";
                    for (ReportCounselorCount counselor : countList) {
                        if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                            index = 2;
                            str1 = counselor.getAreaName();
                            counselor.setIndex1("1");
                        }else {
                            str2 = Integer.toString(index++);
                            counselor.setIndex1(str2);
                        }
                        list.add(new ExportCounselorCountFirst(counselor));
                    }
                }
            }
            String str = "";
            if ("3".equals(Type)) {
                str = "不符合";
            }
            String fileName = "咨询工程师（投资）初始登记"+str+"的人员名单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 咨询工程师（投资）初始登记"+str+"的人员名单" , ExportCounselorCountFirst.class).setDataList(list).write(response, fileName).dispose();
            return "modules/reportforms/rptCounselorFirst";
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出人员名单列表！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/report/findReportCounselorFirstInfo?repage";
    }




    /**
     * 查询某批次继续登记的人员信息，返回到前台对应页面
     */
    @RequestMapping(value = "findReportCounselorContinueInfo")
    public String findReportCounselorContinueInfo(String batchNo, String Type, ReportCounselorCount reportCounselorCount, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        User user = UserUtils.getUser();
        String officeId = user.getOffice().getId();
        Page<ReportCounselorCount> page = null;
        if (batchNo==null || "".equals(batchNo)) {
            return "modules/reportforms/rptCounselorContinue";
        } else {
            if(!"1".equals(user.getId())){
                reportCounselorCount.setOfficeId(officeId);
            }
            reportCounselorCount.setBatchNo(batchNo);
            if ("1".equals(Type)) {
                page = this.reportCounselorCountService.findReportCounselorContinueYesInfo(new Page<ReportCounselorCount>(request, response), reportCounselorCount);
            } else if ("3".equals(Type)) {
                page = this.reportCounselorCountService.findReportCounselorContinueNoInfo(new Page<ReportCounselorCount>(request, response), reportCounselorCount);
            } else {
                addMessage(redirectAttributes, "当前操作有误，查询失败");
            }
            model.addAttribute("page", page);
            return "modules/reportforms/rptCounselorContinue";
        }

    }
    /**
     * 继续登记人员信息导出
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportReportCounselorContinueInfoFile")
    public String exportReportCounselorContinueInfoFile(String batchNo, String Type, ReportCounselorCount reportCounselorCount, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            User user = UserUtils.getUser();
            String officeId = user.getOffice().getId();
            List<ReportCounselorCount> countList = new ArrayList<ReportCounselorCount>();
            List<ExportCounselorCountContinue> list =new ArrayList<ExportCounselorCountContinue>();

            if (user.getOffice() == null) {
            } else {

                if (user.getOffice() == null) {
                    return "modules/reportforms/rptCounselorContinue";
                } else {

                    if(!"1".equals(user.getId())){
                        reportCounselorCount.setOfficeId(officeId);
                    }
                    reportCounselorCount.setBatchNo(batchNo);
                    if ("1".equals(Type)) {
                        countList = reportCounselorCountService.findReportCounselorContinueYesInfo(reportCounselorCount);
                    } else if ("3".equals(Type)) {
                        countList = reportCounselorCountService.findReportCounselorContinueNoInfo(reportCounselorCount);
                    } else {
                        addMessage(redirectAttributes, "当前操作有误，查询失败");
                    }
                }
                if(countList!=null && countList.size()>0){
                    int index = 2;
                    String str1 = "";
                    String str2 = "";
                    for (ReportCounselorCount counselor : countList) {
                        if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                            index = 2;
                            str1 = counselor.getAreaName();
                            counselor.setIndex1("1");
                        }else {
                            str2 = Integer.toString(index++);
                            counselor.setIndex1(str2);
                        }
                        list.add(new ExportCounselorCountContinue(counselor));
                    }
                }
            }
            String str = "";
            if ("3".equals(Type)) {
                str = "不符合";
            }
            String fileName = "咨询工程师（投资）继续登记"+str+"的人员名单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 咨询工程师（投资）继续登记"+str+"的人员名单" , ExportCounselorCountContinue.class).setDataList(list).write(response, fileName).dispose();
            return "modules/reportforms/rptCounselorContinue";
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出人员名单列表！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/report/findReportCounselorContinueInfo?repage";
    }




    /**
     * 查询某批次注销登记的人员信息，返回到前台对应页面
     */
    @RequestMapping(value = "findReportCounselorCancelInfo")
    public String findReportCounselorCancelInfo(String batchNo, String Type, ReportCounselorCount reportCounselorCount, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        User user = UserUtils.getUser();
        String officeId = user.getOffice().getId();
        Page<ReportCounselorCount> page = null;
        if (batchNo==null || "".equals(batchNo)) {
            return "modules/reportforms/rptCounselorCancel";
        } else {
            if(!"1".equals(user.getId())){
                reportCounselorCount.setOfficeId(officeId);
            }
            reportCounselorCount.setBatchNo(batchNo);
            if ("1".equals(Type)) {
                page = this.reportCounselorCountService.findReportCounselorCancelYesInfo(new Page<ReportCounselorCount>(request, response), reportCounselorCount);
            } else if ("3".equals(Type)) {
                page = this.reportCounselorCountService.findReportCounselorCancelNoInfo(new Page<ReportCounselorCount>(request, response), reportCounselorCount);
            } else {
                addMessage(redirectAttributes, "当前操作有误，查询失败");
            }
            model.addAttribute("page", page);
            return "modules/reportforms/rptCounselorCancel";
        }

    }

    /**
     * 注销人员信息导出
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportReportCounselorCancelInfoFile")
    public String exportReportCounselorCancelInfoFile(String batchNo, String Type, ReportCounselorCount reportCounselorCount, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            User user = UserUtils.getUser();
            String officeId = user.getOffice().getId();
            List<ReportCounselorCount> countList = new ArrayList<ReportCounselorCount>();
            List<ExportCounselorCountCancel> list =new ArrayList<ExportCounselorCountCancel>();

            if (user.getOffice() == null) {
            } else {

                if (user.getOffice() == null) {
                    return "modules/reportforms/rptCounselorCancel";
                } else {

                    if(!"1".equals(user.getId())){
                        reportCounselorCount.setOfficeId(officeId);
                    }
                    reportCounselorCount.setBatchNo(batchNo);
                    if ("1".equals(Type)) {
                        countList = reportCounselorCountService.findReportCounselorCancelYesInfo(reportCounselorCount);
                    } else if ("3".equals(Type)) {
                        countList = reportCounselorCountService.findReportCounselorCancelNoInfo(reportCounselorCount);
                    } else {
                        addMessage(redirectAttributes, "当前操作有误，查询失败");
                    }
                }
                if(countList!=null && countList.size()>0){
                    int index = 2;
                    String str1 = "";
                    String str2 = "";
                    for (ReportCounselorCount counselor : countList) {
                        if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                            index = 2;
                            str1 = counselor.getAreaName();
                            counselor.setIndex1("1");
                        }else {
                            str2 = Integer.toString(index++);
                            counselor.setIndex1(str2);
                        }
                        list.add(new ExportCounselorCountCancel(counselor));
                    }
                }
            }
            String str = "";
            if ("3".equals(Type)) {
                str = "不符合";
            }
            String fileName = "咨询工程师（投资）注销登记"+str+"的人员名单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel(batchNo.substring(0,4)+"年"+ batchNo.substring(4,6) +"月 咨询工程师（投资）注销登记"+str+"的人员名单", ExportCounselorCountCancel.class).setDataList(list).write(response, fileName).dispose();
            return "modules/reportforms/rptCounselorCancel";
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出人员名单列表！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/report/findReportCounselorCancelInfo?repage";
    }




    /**
     * 查询某批次变更执业单位的人员信息，返回到前台对应页面
     */
    @RequestMapping(value = "findReportCounselorApplyWorkInfo")
    public String findReportCounselorApplyWorkInfo(String batchNo, String Type, ReportCounselorCount reportCounselorCount, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        User user = UserUtils.getUser();
        String officeId = user.getOffice().getId();
        Page<ReportCounselorCount> page = null;
        if (batchNo==null || "".equals(batchNo)) {
            return "modules/reportforms/rptCounselorApplyWork";
        } else {
            if(!"1".equals(user.getId())){
                reportCounselorCount.setOfficeId(officeId);
            }
            reportCounselorCount.setBatchNo(batchNo);
            if ("1".equals(Type)) {
                page = this.reportCounselorCountService.findReportCounselorApplyWorkYesInfo(new Page<ReportCounselorCount>(request, response), reportCounselorCount);
            } else if ("3".equals(Type)) {
                page = this.reportCounselorCountService.findReportCounselorApplyWorkNoInfo(new Page<ReportCounselorCount>(request, response), reportCounselorCount);
            } else {
                addMessage(redirectAttributes, "当前操作有误，查询失败");
            }
            model.addAttribute("page", page);
            return "modules/reportforms/rptCounselorApplyWork";
        }

    }
    /**
     * 变更执业单位人员信息导出
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportReportCounselorApplyWorkInfoFile")
    public String exportReportCounselorApplyWorkInfoFile(String batchNo, String Type, ReportCounselorCount reportCounselorCount, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            User user = UserUtils.getUser();
            String officeId = user.getOffice().getId();
            List<ReportCounselorCount> countList = new ArrayList<ReportCounselorCount>();
            List<ExportCounselorCountApplyWork> list =new ArrayList<ExportCounselorCountApplyWork>();

            if (user.getOffice() == null) {
            } else {

                if (user.getOffice() == null) {
                    return "modules/reportforms/rptCounselorApplyWork";
                } else {

                    if(!"1".equals(user.getId())){
                        reportCounselorCount.setOfficeId(officeId);
                    }
                    reportCounselorCount.setBatchNo(batchNo);
                    if ("1".equals(Type)) {
                        countList = reportCounselorCountService.findReportCounselorApplyWorkYesInfo(reportCounselorCount);
                    } else if ("3".equals(Type)) {
                        countList = reportCounselorCountService.findReportCounselorApplyWorkNoInfo(reportCounselorCount);
                    } else {
                        addMessage(redirectAttributes, "当前操作有误，查询失败");
                    }
                }
                if(countList!=null && countList.size()>0){
                    int index = 2;
                    String str1 = "";
                    String str2 = "";
                    for (ReportCounselorCount counselor : countList) {
                        if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                            index = 2;
                            str1 = counselor.getAreaName();
                            counselor.setIndex1("1");
                        }else {
                            str2 = Integer.toString(index++);
                            counselor.setIndex1(str2);
                        }
                        list.add(new ExportCounselorCountApplyWork(counselor));
                    }
                }
            }
            String str = "";
            if ("3".equals(Type)) {
                str = "不符合";
            }
            String fileName = "咨询工程师（投资）变更执业单位"+str+"的人员名单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 咨询工程师（投资）变更执业单位"+str+"的人员名单" , ExportCounselorCountApplyWork.class).setDataList(list).write(response, fileName).dispose();
            return "modules/reportforms/rptCounselorApplyWork";
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出人员名单列表！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/report/findReportCounselorApplyWorkInfo?repage";
    }




    /**
     * 查询某批次变更专业的人员信息，返回到前台对应页面
     */
    @RequestMapping(value = "findReportCounselorSpecialtyInfo")
    public String findReportCounselorSpecialtyInfo(String batchNo, String Type, ReportCounselorCount reportCounselorCount, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        User user = UserUtils.getUser();
        String officeId = user.getOffice().getId();
        Page<ReportCounselorCount> page = null;
        if (batchNo==null || "".equals(batchNo)) {
            return "modules/reportforms/rptCounselorSpecialty";
        } else {
            if(!"1".equals(user.getId())){
                reportCounselorCount.setOfficeId(officeId);
            }
            reportCounselorCount.setBatchNo(batchNo);
            if ("1".equals(Type)) {
                page = this.reportCounselorCountService.findReportCounselorSpecialtyYesInfo(new Page<ReportCounselorCount>(request, response), reportCounselorCount);
            } else if ("3".equals(Type)) {
                page = this.reportCounselorCountService.findReportCounselorSpecialtyNoInfo(new Page<ReportCounselorCount>(request, response), reportCounselorCount);
            } else {
                addMessage(redirectAttributes, "当前操作有误，查询失败");
            }
            model.addAttribute("page", page);
            return "modules/reportforms/rptCounselorSpecialty";
        }

    }
    /**
     * 变更专业人员信息导出
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportReportCounselorSpecialtyInfoFile")
    public String exportReportCounselorSpecialtyInfoFile(String batchNo, String Type, ReportCounselorCount reportCounselorCount, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            User user = UserUtils.getUser();
            String officeId = user.getOffice().getId();
            List<ReportCounselorCount> countList = new ArrayList<ReportCounselorCount>();
            List<ExportCounselorCountSpecialtyYes> listY =new ArrayList<ExportCounselorCountSpecialtyYes>();
            List<ExportCounselorCountSpecialtyNo> listN =new ArrayList<ExportCounselorCountSpecialtyNo>();

            if (user.getOffice() == null) {
            } else {

                if (user.getOffice() == null) {
                    return "modules/reportforms/rptCounselorSpecialty";
                } else {

                    if(!"1".equals(user.getId())){
                        reportCounselorCount.setOfficeId(officeId);
                    }
                    reportCounselorCount.setBatchNo(batchNo);
                    if ("1".equals(Type)) {
                        countList = reportCounselorCountService.findReportCounselorSpecialtyYesInfo(reportCounselorCount);
                        if(countList!=null && countList.size()>0){
                            int index = 2;
                            String str1 = "";
                            String str2 = "";
                            for (ReportCounselorCount counselor : countList) {
                                if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                                    index = 2;
                                    str1 = counselor.getAreaName();
                                    counselor.setIndex1("1");
                                }else {
                                    str2 = Integer.toString(index++);
                                    counselor.setIndex1(str2);
                                }
                                listY.add(new ExportCounselorCountSpecialtyYes(counselor));
                            }
                        }
                        String fileName = "咨询工程师（投资）变更专业的人员名单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
                        new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 咨询工程师（投资）变更专业的人员名单" , ExportCounselorCountSpecialtyYes.class).setDataList(listY).write(response, fileName).dispose();

                    } else if ("3".equals(Type)) {
                        countList = reportCounselorCountService.findReportCounselorSpecialtyNoInfo(reportCounselorCount);
                        if(countList!=null && countList.size()>0){
                            int index = 2;
                            String str1 = "";
                            String str2 = "";
                            for (ReportCounselorCount counselor : countList) {
                                if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                                    index = 2;
                                    str1 = counselor.getAreaName();
                                    counselor.setIndex1("1");
                                }else {
                                    str2 = Integer.toString(index++);
                                    counselor.setIndex1(str2);
                                }
                                listN.add(new ExportCounselorCountSpecialtyNo(counselor));
                            }
                        }
                        String fileName = "咨询工程师（投资）变更专业不符合的人员名单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
                        new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 咨询工程师（投资）变更专业不符合的人员名单" , ExportCounselorCountSpecialtyNo.class).setDataList(listN).write(response, fileName).dispose();

                    } else {
                        addMessage(redirectAttributes, "当前操作有误，查询失败");
                    }
                }
            }
            return "modules/reportforms/rptCounselorSpecialty";
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出人员名单列表！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/report/findReportCounselorSpecialtyInfo?repage";
    }





}
