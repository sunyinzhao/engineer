package com.thinkgem.jeesite.modules.reportforms.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.reportforms.entity.*;
import com.thinkgem.jeesite.modules.reportforms.service.ReportCounselorCountService;
import com.thinkgem.jeesite.modules.reportforms.service.ReportReconsiderationStatisticsService;
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
 * 咨询师复议统计
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "${adminPath}/report/again")
public class ReportReconsiderationStatisticsController extends BaseController {

    @Autowired
    private ReportReconsiderationStatisticsService reportReconsiderationStatisticsService;



    /**
     * 查询某批次初始登记的人员信息，返回到前台对应页面
     */
    @RequestMapping(value = "findReconsiderationStatisticsFirstInfo")
    public String findReconsiderationStatisticsFirstInfo(String batchNo, String Type, ReportReconsiderationStatistics reportReconsiderationStatistics, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
        String officeId = user.getOffice().getId();
        Page<ReportReconsiderationStatistics> page = null;
        if (batchNo==null || "".equals(batchNo)) {
            return "modules/reportforms/reportReconsiderationStatisticsFirst";
        } else {
            if(!"1".equals(user.getId())){
                reportReconsiderationStatistics.setOfficeId(officeId);
            }
            reportReconsiderationStatistics.setBatchNo(batchNo);
            reportReconsiderationStatistics.setDeclareType("1");
            if ("1".equals(Type)) {
                reportReconsiderationStatistics.setFinalResult("1");
                page = this.reportReconsiderationStatisticsService.findReconsiderationStatisticsFirstInfo(new Page<ReportReconsiderationStatistics>(request, response), reportReconsiderationStatistics);
            } else if ("2".equals(Type)) {
                reportReconsiderationStatistics.setFinalResult("2");
                page = this.reportReconsiderationStatisticsService.findReconsiderationStatisticsFirstInfo(new Page<ReportReconsiderationStatistics>(request, response), reportReconsiderationStatistics);
            }else if ("3".equals(Type)) {
                reportReconsiderationStatistics.setFinalResult("3");
                page = this.reportReconsiderationStatisticsService.findReconsiderationStatisticsFirstInfo(new Page<ReportReconsiderationStatistics>(request, response), reportReconsiderationStatistics);
            } else {
                addMessage(redirectAttributes, "当前操作有误，查询失败");
            }
            model.addAttribute("page", page);
            return "modules/reportforms/reportReconsiderationStatisticsFirst";
        }

    }
    /**
     * 初始登记人员信息导出
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportReconsiderationStatisticsFirstInfoFile")
    public String exportReconsiderationStatisticsFirstInfoFile(String batchNo, String Type, ReportReconsiderationStatistics reportReconsiderationStatistics, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            User user = UserUtils.getUser();
            String officeId = user.getOffice().getId();
            List<ReportReconsiderationStatistics> countList = new ArrayList<ReportReconsiderationStatistics>();
            List<ExportReconsiderationStatisticsFirst> list =new ArrayList<ExportReconsiderationStatisticsFirst>();

            if (user.getOffice() == null) {
            } else {

                if (user.getOffice() == null) {
                    return "modules/reportforms/reportReconsiderationStatisticsFirst";
                } else {

                    if(!"1".equals(user.getId())){
                        reportReconsiderationStatistics.setOfficeId(officeId);
                    }
                    reportReconsiderationStatistics.setBatchNo(batchNo);
                    reportReconsiderationStatistics.setDeclareType("1");
                    if ("1".equals(Type)) {
                        reportReconsiderationStatistics.setFinalResult("1");
                        countList = reportReconsiderationStatisticsService.findReconsiderationStatisticsFirstInfo(reportReconsiderationStatistics);
                    } else if ("2".equals(Type)) {
                        reportReconsiderationStatistics.setFinalResult("2");
                        countList = reportReconsiderationStatisticsService.findReconsiderationStatisticsFirstInfo(reportReconsiderationStatistics);
                    }else if ("3".equals(Type)) {
                        reportReconsiderationStatistics.setFinalResult("3");
                        countList = reportReconsiderationStatisticsService.findReconsiderationStatisticsFirstInfo(reportReconsiderationStatistics);
                    } else {
                        addMessage(redirectAttributes, "当前操作有误，查询失败");
                    }
                }
                if(countList!=null && countList.size()>0){
                    int index = 2;
                    String str1 = "";
                    String str2 = "";
                    for (ReportReconsiderationStatistics counselor : countList) {
                        if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                            index = 2;
                            str1 = counselor.getAreaName();
                            counselor.setIndex("1");
                        }else {
                            str2 = Integer.toString(index++);
                            counselor.setIndex(str2);
                        }
                        list.add(new ExportReconsiderationStatisticsFirst(counselor));
                    }
                }
            }
            String str = "";
            if ("3".equals(Type)) {
                str = "不符合";
            }if ("2".equals(Type)) {
                str = "部分符合";
            }
            String fileName = "咨询工程师（投资）初始登记复核统计结果"+str+"的人员名单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 咨询工程师（投资）初始登记复核统计结果"+str+"的人员名单" , ExportReconsiderationStatisticsFirst.class).setDataList(list).write(response, fileName).dispose();
            return "modules/reportforms/reportReconsiderationStatisticsFirst";
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出人员名单列表！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/report/findReconsiderationStatisticsFirstInfo?repage";
    }





    /**
     * 查询某批次继续登记的人员信息，返回到前台对应页面
     */
    @RequestMapping(value = "findReconsiderationStatisticsContinueInfo")
    public String findReconsiderationStatisticsContinueInfo(String batchNo, String Type, ReportReconsiderationStatistics reportReconsiderationStatistics, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
        String officeId = user.getOffice().getId();
        Page<ReportReconsiderationStatistics> page = null;
        if (batchNo==null || "".equals(batchNo)) {
            return "modules/reportforms/reportReconsiderationStatisticsContinue";
        } else {
            if(!"1".equals(user.getId())){
                reportReconsiderationStatistics.setOfficeId(officeId);
            }
            reportReconsiderationStatistics.setBatchNo(batchNo);
            reportReconsiderationStatistics.setDeclareType("4");
            if ("1".equals(Type)) {
                reportReconsiderationStatistics.setFinalResult("1");
                page = this.reportReconsiderationStatisticsService.findReconsiderationStatisticsContinueInfo(new Page<ReportReconsiderationStatistics>(request, response), reportReconsiderationStatistics);
            } else if ("2".equals(Type)) {
                reportReconsiderationStatistics.setFinalResult("2");
                page = this.reportReconsiderationStatisticsService.findReconsiderationStatisticsContinueInfo(new Page<ReportReconsiderationStatistics>(request, response), reportReconsiderationStatistics);
            }else if ("3".equals(Type)) {
                reportReconsiderationStatistics.setFinalResult("3");
                page = this.reportReconsiderationStatisticsService.findReconsiderationStatisticsContinueInfo(new Page<ReportReconsiderationStatistics>(request, response), reportReconsiderationStatistics);
            } else {
                addMessage(redirectAttributes, "当前操作有误，查询失败");
            }
            model.addAttribute("page", page);
            return "modules/reportforms/reportReconsiderationStatisticsContinue";
        }

    }
    /**
     * 继续登记人员信息导出
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportReconsiderationStatisticsContinueInfoFile")
    public String exportReconsiderationStatisticsContinueInfoFile(String batchNo, String Type, ReportReconsiderationStatistics reportReconsiderationStatistics, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            User user = UserUtils.getUser();
            String officeId = user.getOffice().getId();
            List<ReportReconsiderationStatistics> countList = new ArrayList<ReportReconsiderationStatistics>();
            List<ExportReconsiderationStatisticsContinue> list = new ArrayList<ExportReconsiderationStatisticsContinue>();

            if (user.getOffice() == null) {
            } else {

                if (user.getOffice() == null) {
                    return "modules/reportforms/reportReconsiderationStatisticsContinue";
                } else {

                    if(!"1".equals(user.getId())){
                        reportReconsiderationStatistics.setOfficeId(officeId);
                    }
                    reportReconsiderationStatistics.setBatchNo(batchNo);
                    reportReconsiderationStatistics.setDeclareType("4");
                    if ("1".equals(Type)) {
                        reportReconsiderationStatistics.setFinalResult("1");
                        countList = reportReconsiderationStatisticsService.findReconsiderationStatisticsContinueInfo(reportReconsiderationStatistics);
                    } else if ("2".equals(Type)) {
                        reportReconsiderationStatistics.setFinalResult("2");
                        countList = reportReconsiderationStatisticsService.findReconsiderationStatisticsContinueInfo(reportReconsiderationStatistics);
                    }else if ("3".equals(Type)) {
                        reportReconsiderationStatistics.setFinalResult("3");
                        countList = reportReconsiderationStatisticsService.findReconsiderationStatisticsContinueInfo(reportReconsiderationStatistics);
                    } else {
                        addMessage(redirectAttributes, "当前操作有误，查询失败");
                    }
                }
                if(countList!=null && countList.size()>0){
                    int index = 2;
                    String str1 = "";
                    String str2 = "";
                    for (ReportReconsiderationStatistics counselor : countList) {
                        if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                            index = 2;
                            str1 = counselor.getAreaName();
                            counselor.setIndex("1");
                        }else {
                            str2 = Integer.toString(index++);
                            counselor.setIndex(str2);
                        }
                        list.add(new ExportReconsiderationStatisticsContinue(counselor));
                    }
                }
            }
            String str = "";
            if ("3".equals(Type)) {
                str = "不符合";
            }if ("2".equals(Type)) {
                str = "部分符合";
            }
            String fileName = "咨询工程师（投资）继续登记复核统计结果"+str+"的人员名单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 咨询工程师（投资）继续登记复核统计结果"+str+"的人员名单" , ExportReconsiderationStatisticsContinue.class).setDataList(list).write(response, fileName).dispose();
            return "modules/reportforms/reportReconsiderationStatisticsContinue";
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出人员名单列表！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/report/findReconsiderationStatisticsContinueInfo?repage";
    }






    /**
     * 查询某批次注销登记的人员信息，返回到前台对应页面
     */
    @RequestMapping(value = "findReconsiderationStatisticsCancelInfo")
    public String findReconsiderationStatisticsCancelInfo(String batchNo, String Type, ReportReconsiderationStatistics reportReconsiderationStatistics, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
        String officeId = user.getOffice().getId();
        Page<ReportReconsiderationStatistics> page = null;
        if (batchNo==null || "".equals(batchNo)) {
            return "modules/reportforms/reportReconsiderationStatisticsCancel";
        } else {
            if(!"1".equals(user.getId())){
                reportReconsiderationStatistics.setOfficeId(officeId);
            }
            reportReconsiderationStatistics.setBatchNo(batchNo);
            reportReconsiderationStatistics.setDeclareType("0");
            if ("1".equals(Type)) {
                reportReconsiderationStatistics.setFinalResult("1");
                page = this.reportReconsiderationStatisticsService.findReconsiderationStatisticsCancelInfo(new Page<ReportReconsiderationStatistics>(request, response), reportReconsiderationStatistics);
            } else if ("2".equals(Type)) {
                reportReconsiderationStatistics.setFinalResult("2");
                page = this.reportReconsiderationStatisticsService.findReconsiderationStatisticsCancelInfo(new Page<ReportReconsiderationStatistics>(request, response), reportReconsiderationStatistics);
            }else if ("3".equals(Type)) {
                reportReconsiderationStatistics.setFinalResult("3");
                page = this.reportReconsiderationStatisticsService.findReconsiderationStatisticsCancelInfo(new Page<ReportReconsiderationStatistics>(request, response), reportReconsiderationStatistics);
            } else {
                addMessage(redirectAttributes, "当前操作有误，查询失败");
            }
            model.addAttribute("page", page);
            return "modules/reportforms/reportReconsiderationStatisticsCancel";
        }

    }
    /**
     * 注销登记人员信息导出
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportReconsiderationStatisticsCancelInfoFile")
    public String exportReconsiderationStatisticsCancelInfoFile(String batchNo, String Type, ReportReconsiderationStatistics reportReconsiderationStatistics, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            User user = UserUtils.getUser();
            String officeId = user.getOffice().getId();
            List<ReportReconsiderationStatistics> countList = new ArrayList<ReportReconsiderationStatistics>();
            List<ExportReconsiderationStatisticsCancel> list = new ArrayList<ExportReconsiderationStatisticsCancel>();

            if (user.getOffice() == null) {
            } else {

                if (user.getOffice() == null) {
                    return "modules/reportforms/reportReconsiderationStatisticsCancel";
                } else {

                    if(!"1".equals(user.getId())){
                        reportReconsiderationStatistics.setOfficeId(officeId);
                    }
                    reportReconsiderationStatistics.setBatchNo(batchNo);
                    reportReconsiderationStatistics.setDeclareType("0");
                    if ("1".equals(Type)) {
                        reportReconsiderationStatistics.setFinalResult("1");
                        countList = reportReconsiderationStatisticsService.findReconsiderationStatisticsCancelInfo(reportReconsiderationStatistics);
                    } else if ("2".equals(Type)) {
                        reportReconsiderationStatistics.setFinalResult("2");
                        countList = reportReconsiderationStatisticsService.findReconsiderationStatisticsCancelInfo(reportReconsiderationStatistics);
                    }else if ("3".equals(Type)) {
                        reportReconsiderationStatistics.setFinalResult("3");
                        countList = reportReconsiderationStatisticsService.findReconsiderationStatisticsCancelInfo(reportReconsiderationStatistics);
                    } else {
                        addMessage(redirectAttributes, "当前操作有误，查询失败");
                    }
                }
                if(countList!=null && countList.size()>0){
                    int index = 2;
                    String str1 = "";
                    String str2 = "";
                    for (ReportReconsiderationStatistics counselor : countList) {
                        if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                            index = 2;
                            str1 = counselor.getAreaName();
                            counselor.setIndex("1");
                        }else {
                            str2 = Integer.toString(index++);
                            counselor.setIndex(str2);
                        }
                        list.add(new ExportReconsiderationStatisticsCancel(counselor));
                    }
                }
            }
            String str = "";
            if ("3".equals(Type)) {
                str = "不符合";
            }if ("2".equals(Type)) {
                str = "部分符合";
            }
            String fileName = "咨询工程师（投资）注销登记复核统计结果"+str+"的人员名单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 咨询工程师（投资）注销登记复核统计结果"+str+"的人员名单" , ExportReconsiderationStatisticsCancel.class).setDataList(list).write(response, fileName).dispose();
            return "modules/reportforms/reportReconsiderationStatisticsCancel";
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出人员名单列表！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/report/findReconsiderationStatisticsCancelInfo?repage";
    }






    /**
     * 查询某批次变更专业的人员信息，返回到前台对应页面
     */
    @RequestMapping(value = "findReconsiderationStatisticsSpecialtyInfo")
    public String findReconsiderationStatisticsSpecialtyInfo(String batchNo, String Type, ReportReconsiderationStatistics reportReconsiderationStatistics, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
        String officeId = user.getOffice().getId();
        Page<ReportReconsiderationStatistics> page = null;
        if (batchNo==null || "".equals(batchNo)) {
            return "modules/reportforms/reportReconsiderationStatisticsSpecialty";
        } else {
            if(!"1".equals(user.getId())){
                reportReconsiderationStatistics.setOfficeId(officeId);
            }
            reportReconsiderationStatistics.setBatchNo(batchNo);
            reportReconsiderationStatistics.setDeclareType("3");
            if ("1".equals(Type)) {
                reportReconsiderationStatistics.setFinalResult("1");
                page = this.reportReconsiderationStatisticsService.findReconsiderationStatisticsSpecialtyInfo(new Page<ReportReconsiderationStatistics>(request, response), reportReconsiderationStatistics);
            } else if ("2".equals(Type)) {
                reportReconsiderationStatistics.setFinalResult("2");
                page = this.reportReconsiderationStatisticsService.findReconsiderationStatisticsSpecialtyInfo(new Page<ReportReconsiderationStatistics>(request, response), reportReconsiderationStatistics);
            }else if ("3".equals(Type)) {
                reportReconsiderationStatistics.setFinalResult("3");
                page = this.reportReconsiderationStatisticsService.findReconsiderationStatisticsSpecialtyInfo(new Page<ReportReconsiderationStatistics>(request, response), reportReconsiderationStatistics);
            } else {
                addMessage(redirectAttributes, "当前操作有误，查询失败");
            }
            model.addAttribute("page", page);
            return "modules/reportforms/reportReconsiderationStatisticsSpecialty";
        }

    }
    /**
     * 变更专业人员信息导出
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportReconsiderationStatisticsSpecialtyInfoFile")
    public String exportReconsiderationStatisticsSpecialtyInfoFile(String batchNo, String Type, ReportReconsiderationStatistics reportReconsiderationStatistics, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            User user = UserUtils.getUser();
            String officeId = user.getOffice().getId();
            List<ReportReconsiderationStatistics> countList = new ArrayList<ReportReconsiderationStatistics>();
            List<ExportReconsiderationStatisticsSpecialty> list = new ArrayList<ExportReconsiderationStatisticsSpecialty>();

            if (user.getOffice() == null) {
            } else {

                if (user.getOffice() == null) {
                    return "modules/reportforms/reportReconsiderationStatisticsSpecialty";
                } else {

                    if(!"1".equals(user.getId())){
                        reportReconsiderationStatistics.setOfficeId(officeId);
                    }
                    reportReconsiderationStatistics.setBatchNo(batchNo);
                    reportReconsiderationStatistics.setDeclareType("3");
                    if ("1".equals(Type)) {
                        reportReconsiderationStatistics.setFinalResult("1");
                        countList = reportReconsiderationStatisticsService.findReconsiderationStatisticsSpecialtyInfo(reportReconsiderationStatistics);
                    } else if ("2".equals(Type)) {
                        reportReconsiderationStatistics.setFinalResult("2");
                        countList = reportReconsiderationStatisticsService.findReconsiderationStatisticsSpecialtyInfo(reportReconsiderationStatistics);
                    }else if ("3".equals(Type)) {
                        reportReconsiderationStatistics.setFinalResult("3");
                        countList = reportReconsiderationStatisticsService.findReconsiderationStatisticsSpecialtyInfo(reportReconsiderationStatistics);
                    } else {
                        addMessage(redirectAttributes, "当前操作有误，查询失败");
                    }
                }
                if(countList!=null && countList.size()>0){
                    int index = 2;
                    String str1 = "";
                    String str2 = "";
                    for (ReportReconsiderationStatistics counselor : countList) {
                        if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                            index = 2;
                            str1 = counselor.getAreaName();
                            counselor.setIndex("1");
                        }else {
                            str2 = Integer.toString(index++);
                            counselor.setIndex(str2);
                        }
                        list.add(new ExportReconsiderationStatisticsSpecialty(counselor));
                    }
                }
            }
            String str = "";
            if ("3".equals(Type)) {
                str = "不符合";
            }if ("2".equals(Type)) {
                str = "部分符合";
            }
            String fileName = "咨询工程师（投资）变更专业复核统计结果"+str+"的人员名单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 咨询工程师（投资）变更专业复核统计结果"+str+"的人员名单" , ExportReconsiderationStatisticsSpecialty.class).setDataList(list).write(response, fileName).dispose();
            return "modules/reportforms/reportReconsiderationStatisticsSpecialty";
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出人员名单列表！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/report/findReconsiderationStatisticsSpecialtyInfo?repage";
    }






}
