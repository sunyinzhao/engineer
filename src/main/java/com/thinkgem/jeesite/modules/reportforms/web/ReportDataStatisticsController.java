package com.thinkgem.jeesite.modules.reportforms.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.reportforms.entity.*;
import com.thinkgem.jeesite.modules.reportforms.service.ReportCounselorCountService;
import com.thinkgem.jeesite.modules.reportforms.service.ReportDataStatisticsService;
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
 * 数据统计
 *
 * @author Administrator
 * @version 2019-05-15
 */
@Controller
@RequestMapping(value = "${adminPath}/report/datastatistics")
public class ReportDataStatisticsController extends BaseController {

    @Autowired
    private ReportDataStatisticsService reportDataStatisticsService;


    /**
     * 查询意见数据表
     * @param batchNo
     * @param resultType
     * @param declareType
     * @param reportDataStatistics
     * @param model
     * @param redirectAttributes
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "compareOpinions")
    public String compareOpinions(String batchNo,String resultType,String declareType,ReportDataStatistics reportDataStatistics,Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getUser();
        String officeId = user.getOffice().getId();
        Page<ReportDataStatistics> page = null;
        if (batchNo == null || "".equals(batchNo)) {
            if ("0".equals(declareType)) {
                return "modules/reportforms/reportDataStatisticsCompareOpinionsLogout";
            } else if ("1".equals(declareType)) {
                return "modules/reportforms/reportDataStatisticsCompareOpinionsInitial";
            } else if ("2".equals(declareType)) {
                return "modules/reportforms/reportDataStatisticsCompareOpinionsWorkunit";
            } else if ("3".equals(declareType)) {
                return "modules/reportforms/reportDataStatisticsCompareOpinionsSpecialty";
            } else if ("4".equals(declareType)) {
                return "modules/reportforms/reportDataStatisticsCompareOpinionsContinue";
            }
            return null;
        } else {
            if(!"1".equals(user.getId())){
                reportDataStatistics.setOfficeId(officeId);// 地方协会 e.g:"e670a59fc0c34e8dbb822788cdf861e8"
            }
            reportDataStatistics.setBatchNo(batchNo);  // 批次
            reportDataStatistics.setFinalResult(resultType); // 通过类型
//            reportDataStatistics.setDeclareType(declareType);// 申请类型  | 语句已经写死类型
            if ("0".equals(declareType)) {
                page = this.reportDataStatisticsService.findReportDataStatisticsOpinionLogout(new Page<ReportDataStatistics>(request, response), reportDataStatistics);
                model.addAttribute("page",page);
                return "modules/reportforms/reportDataStatisticsCompareOpinionsLogout";
            } else if ("1".equals(declareType)) {
                page = this.reportDataStatisticsService.findReportDataStatisticsOpinionInitial(new Page<ReportDataStatistics>(request, response), reportDataStatistics);
                model.addAttribute("page",page);
                return "modules/reportforms/reportDataStatisticsCompareOpinionsInitial";
            } else if ("2".equals(declareType)) {
                page = this.reportDataStatisticsService.findReportDataStatisticsOpinionWorkunit(new Page<ReportDataStatistics>(request, response), reportDataStatistics);
                model.addAttribute("page",page);
                return "modules/reportforms/reportDataStatisticsCompareOpinionsWorkunit";
            } else if ("3".equals(declareType)) {
                page = this.reportDataStatisticsService.findReportDataStatisticsOpinionSpecialty(new Page<ReportDataStatistics>(request, response), reportDataStatistics);
                model.addAttribute("page",page);
                return "modules/reportforms/reportDataStatisticsCompareOpinionsSpecialty";
            } else if ("4".equals(declareType)) {
                page = this.reportDataStatisticsService.findReportDataStatisticsOpinionContinue(new Page<ReportDataStatistics>(request, response), reportDataStatistics);
                model.addAttribute("page",page);
                return "modules/reportforms/reportDataStatisticsCompareOpinionsContinue";
            } else {
                addMessage(redirectAttributes, "当前操作有误，查询失败");
            }

            return null;
        }
    }
    /**
     * 导出意见数据表
     * @param batchNo
     * @param resultType
     * @param declareType
     * @param reportDataStatistics
     * @param model
     * @param redirectAttributes
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "exportCompareOpinions")
    public String exportCompareOpinions(String batchNo,String resultType,String declareType,ReportDataStatistics reportDataStatistics,Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = UserUtils.getUser();
            String officeId = user.getOffice().getId();
            List<ReportDataStatistics> countList = new ArrayList<ReportDataStatistics>();
            List<exportDataStatisticsLogout> logoutList = new ArrayList<exportDataStatisticsLogout>();
            List<exportDataStatisticsInitial> initialList = new ArrayList<exportDataStatisticsInitial>();
            List<exportDataStatisticsWorkunit> workunitList = new ArrayList<exportDataStatisticsWorkunit>();
            List<exportDataStatisticsSpecialty> specialtyList = new ArrayList<exportDataStatisticsSpecialty>();
            List<exportDataStatisticsContinue> continueList = new ArrayList<exportDataStatisticsContinue>();

            if (user.getOffice() == null) {
                if ("0".equals(declareType)) {
                    return "modules/reportforms/reportDataStatisticsCompareOpinionsLogout";
                } else if ("1".equals(declareType)) {
                    return "modules/reportforms/reportDataStatisticsCompareOpinionsInitial";
                } else if ("2".equals(declareType)) {
                    return "modules/reportforms/reportDataStatisticsCompareOpinionsWorkunit";
                } else if ("3".equals(declareType)) {
                    return "modules/reportforms/reportDataStatisticsCompareOpinionsSpecialty";
                } else if ("4".equals(declareType)) {
                    return "modules/reportforms/reportDataStatisticsCompareOpinionsContinue";
                }
                return null;
            } else {

                if(!"1".equals(user.getId())){
                    reportDataStatistics.setOfficeId(officeId);// 地方协会 e.g:"e670a59fc0c34e8dbb822788cdf861e8"
                }
                reportDataStatistics.setBatchNo(batchNo);  // 批次
                reportDataStatistics.setFinalResult(resultType); // 通过类型
                if ("0".equals(declareType)) { // 注销登记
                    countList = this.reportDataStatisticsService.findReportDataStatisticsOpinionLogout(reportDataStatistics);
                    if(countList!=null && countList.size()>0){
                        int index = 2;
                        String str1 = "";
                        String str2 = "";
                        for (ReportDataStatistics counselor : countList) {
                            if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                                index = 2;
                                str1 = counselor.getAreaName();
                                counselor.setIndex1("1");
                            }else {
                                str2 = Integer.toString(index++);
                                counselor.setIndex1(str2);
                            }
                            logoutList.add(new exportDataStatisticsLogout(counselor));
                        }
                    }
                    String fileName = "终审专家符合后意见对比-注销登记"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
                    new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 终审专家符合后意见对比-注销登记" , exportDataStatisticsLogout.class).setDataList(logoutList).write(response, fileName).dispose();
                } else if ("1".equals(declareType)) { // 初始登记
                    countList = this.reportDataStatisticsService.findReportDataStatisticsOpinionInitial(reportDataStatistics);
                    if(countList!=null && countList.size()>0){
                        int index = 2;
                        String str1 = "";
                        String str2 = "";
                        for (ReportDataStatistics counselor : countList) {
                            if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                                index = 2;
                                str1 = counselor.getAreaName();
                                counselor.setIndex1("1");
                            }else {
                                str2 = Integer.toString(index++);
                                counselor.setIndex1(str2);
                            }
                            initialList.add(new exportDataStatisticsInitial(counselor));
                        }
                    }
                    String fileName = "终审专家符合后意见对比-初始登记"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
                    new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 终审专家符合后意见对比-初始登记" , exportDataStatisticsInitial.class).setDataList(initialList).write(response, fileName).dispose();
                } else if ("2".equals(declareType)) { // 变更单位
                    countList = this.reportDataStatisticsService.findReportDataStatisticsOpinionWorkunit(reportDataStatistics);
                    if(countList!=null && countList.size()>0){
                        int index = 2;
                        String str1 = "";
                        String str2 = "";
                        for (ReportDataStatistics counselor : countList) {
                            if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                                index = 2;
                                str1 = counselor.getAreaName();
                                counselor.setIndex1("1");
                            }else {
                                str2 = Integer.toString(index++);
                                counselor.setIndex1(str2);
                            }
                            workunitList.add(new exportDataStatisticsWorkunit(counselor));
                        }
                    }
                    String fileName = "终审专家符合后意见对比-变更单位"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
                    new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 终审专家符合后意见对比-变更单位" , exportDataStatisticsWorkunit.class).setDataList(workunitList).write(response, fileName).dispose();
                } else if ("3".equals(declareType)) { // 变更专业
                    countList = this.reportDataStatisticsService.findReportDataStatisticsOpinionSpecialty(reportDataStatistics);
                    if(countList!=null && countList.size()>0){
                        int index = 2;
                        String str1 = "";
                        String str2 = "";
                        for (ReportDataStatistics counselor : countList) {
                            if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                                index = 2;
                                str1 = counselor.getAreaName();
                                counselor.setIndex1("1");
                            }else {
                                str2 = Integer.toString(index++);
                                counselor.setIndex1(str2);
                            }
                            specialtyList.add(new exportDataStatisticsSpecialty(counselor));
                        }
                    }
                    String fileName = "终审专家符合后意见对比-变更专业"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
                    new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 终审专家符合后意见对比-变更专业" , exportDataStatisticsSpecialty.class).setDataList(specialtyList).write(response, fileName).dispose();
                } else if ("4".equals(declareType)) { // 继续登记
                    countList = this.reportDataStatisticsService.findReportDataStatisticsOpinionContinue(reportDataStatistics);
                    if(countList!=null && countList.size()>0){
                        int index = 2;
                        String str1 = "";
                        String str2 = "";
                        for (ReportDataStatistics counselor : countList) {
                            if (counselor.getAreaName() != null && !str1.equals(counselor.getAreaName())) {
                                index = 2;
                                str1 = counselor.getAreaName();
                                counselor.setIndex1("1");
                            }else {
                                str2 = Integer.toString(index++);
                                counselor.setIndex1(str2);
                            }
                            continueList.add(new exportDataStatisticsContinue(counselor));
                        }
                    }
                    String fileName = "终审专家符合后意见对比-继续登记"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
                    new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "月 终审专家符合后意见对比-继续登记" , exportDataStatisticsContinue.class).setDataList(continueList).write(response, fileName).dispose();
                } else {
                    addMessage(redirectAttributes, "当前操作有误，查询失败");
                }
            }
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出意见对比列表！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/report/datastatistics/compareOpinions?repage";
    }

}
