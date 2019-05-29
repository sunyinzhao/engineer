package com.thinkgem.jeesite.modules.reportforms.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.reportforms.entity.ExportCounselorCountFirst;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportCounselorCount;
import com.thinkgem.jeesite.modules.reportforms.entity.changeUnit;
import com.thinkgem.jeesite.modules.reportforms.entity.exportChangeUnit;
import com.thinkgem.jeesite.modules.reportforms.service.checkResultService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/check/result")
public class checkResultController extends BaseController{

	@Autowired
	checkResultService checkresultservice;
    /**
     * 查询变更单位复核统计信息，返回到前台对应页面
     */
	@RequestMapping(value="changeUnit")
	public String changeUnitff(String batchNo, String Type,changeUnit changeunit,Model model,
			HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes){

		User user = UserUtils.getUser();
        String officeId = user.getOffice().getId();
		Page<changeUnit> page = null;
		 if (batchNo==null && "".equals(batchNo)) {
			 return "modules/reportforms/changeUnit";
       } else {
           if(!"1".equals(user.getId())){
        	   changeunit.setOfficeId(officeId);
           }
           changeunit.setBatchNo(batchNo);
           if ("1".equals(Type)) {
        	   changeunit.setfResult("1");
               page = this.checkresultservice.findChangeUnit(new Page<changeUnit>(request, response), changeunit);
           } else if ("2".equals(Type)) {
        	   changeunit.setfResult("2");
               page = this.checkresultservice.findChangeUnit(new Page<changeUnit>(request, response), changeunit);
           }else if ("3".equals(Type)) {
        	   changeunit.setfResult("3");
               page = this.checkresultservice.findChangeUnit(new Page<changeUnit>(request, response), changeunit);
           } else {
              addMessage(redirectAttributes, "当前操作有误，查询失败");
           }
           model.addAttribute("page", page);

		return "modules/reportforms/changeUnit";
	}

	}
    /**
     * 变更单位复核统计信息导出
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
	
	 @RequestMapping(value = "exportChangeUnit")
	    public String exportChangeUnit(String batchNo, String Type, 
	    		changeUnit changeunit, HttpServletRequest request, 
	    		HttpServletResponse response, RedirectAttributes redirectAttributes) {
	        try {
	            User user = UserUtils.getUser();
	            String officeId = user.getOffice().getId();
	            List<changeUnit> countList = new ArrayList<changeUnit>();
	            List<exportChangeUnit> list =new ArrayList<exportChangeUnit>();

	            if (user.getOffice() == null) {
	            } else {

	                if (user.getOffice() == null) {
	                    return "modules/reportforms/changeUnit";
	                } else {

	                    if(!"1".equals(user.getId())){
	                    	changeunit.setOfficeId(officeId);
	                    }
	                    changeunit.setBatchNo(batchNo);
	                    if ("1".equals(Type)) {
	                    	changeunit.setfResult("1");
	                        countList = checkresultservice.exportChangeUnit(changeunit);
	                    }
	                    else if ("2".equals(Type)) {
	                    	changeunit.setfResult("2");
	                        countList = checkresultservice.exportChangeUnit(changeunit);
	                    }else if ("3".equals(Type)) {
	                    	changeunit.setfResult("3");
	                    	countList = checkresultservice.exportChangeUnit(changeunit);
	                    } else {
	                        addMessage(redirectAttributes, "当前操作有误，查询失败");
	                    }
	                }
	                if(countList!=null && countList.size()>0){
	                    int index =1;
	                    for (changeUnit counselor : countList) {
	                            
	                            String str2 = Integer.toString(index++);
	                            counselor.setIndex1(str2);
	                        
	                        list.add(new exportChangeUnit(counselor));
	                    }
	                }
	            }
	            String str = "";
	            if ("3".equals(Type)) {
	                str = "不符合";
	            }
	            String fileName = "变更单位复核统计"+str+"的人员名单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
	            new ExportExcel(batchNo.substring(0,4) + "年" + batchNo.substring(4,6) + "变更单位复核统计"+str+"的人员名单" , exportChangeUnit.class).setDataList(list).write(response, fileName).dispose();
	            return "modules/reportforms/changeUnit";
	        } catch (Exception e) {
	            addMessage(redirectAttributes, "导出人员名单列表！失败信息："+e.getMessage());
	        }
	        return "redirect:" + adminPath + "/report/changeUnit?repage";
	    }
}