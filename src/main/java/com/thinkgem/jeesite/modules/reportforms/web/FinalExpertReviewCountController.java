
package com.thinkgem.jeesite.modules.reportforms.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.reportforms.entity.FinalExpertReviewCount;
import com.thinkgem.jeesite.modules.reportforms.service.FinalExpertReviewCountService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * --> 终审专家评审情况统计表
 */
@Controller
@RequestMapping(value = "${adminPath}/report/expertReview")
public class FinalExpertReviewCountController extends BaseController {


	@Autowired
	private FinalExpertReviewCountService finalExpertReviewCountService;


	/**
	 * 终审专家评审情况统计表
	 * @return
	 */

	@RequestMapping(value = "reviewCount")
	public String reviewCount (String batchNo, String type, String id, FinalExpertReviewCount finalExpertReviewCount, Model model, HttpServletRequest request, HttpServletResponse response ){
		if (batchNo == null) {
			return "modules/reportforms/finalExpertReviewCount";
		}
		if(!"1".equals(UserUtils.getUser().getId())){
			//单独获取登录账户的所在地区id
			String id2 = UserUtils.getUser().getOffice().getId();
			finalExpertReviewCount.setOfficeId(id2);
		}
		// else {finalExpertReviewCount.setId(id);}//要officeId，就是省的id.
		Page<FinalExpertReviewCount> page = null;
		if ("1".equals(type)) {
			page = finalExpertReviewCountService.countByExpertReview(new Page<FinalExpertReviewCount>(request, response), finalExpertReviewCount, type);
			finalExpertReviewCount.setReviewType("专家评审");// 回显用
        }
        if ("2".equals(type)) {
			page = finalExpertReviewCountService.countByExpertReview(new Page<FinalExpertReviewCount>(request, response), finalExpertReviewCount, type);
            finalExpertReviewCount.setReviewType("专家复核");// 回显用
        }
        model.addAttribute("page",page);
		return "modules/reportforms/finalExpertReviewCount";
	}


	/**
	 * 终审专家评审情况统计表 - 导出
	 * @return
	 */
	@RequestMapping(value = "reviewCountExport")
	public String reviewCountExport (String batchNo, String type, String id, FinalExpertReviewCount finalExpertReviewCount, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes ){
		try {
			if(!"1".equals(UserUtils.getUser().getId())){
				//单独获取登录账户的所在地区id
				String id2 = UserUtils.getUser().getOffice().getId();
				finalExpertReviewCount.setOfficeId(id2);
			}
			// else {finalExpertReviewCount.setId(id);}//要officeId，就是省的id.
			List<FinalExpertReviewCount> list = new ArrayList<FinalExpertReviewCount>();
			if ("1".equals(type)) {
				list = finalExpertReviewCountService.countByExpertReview(finalExpertReviewCount, type);
                finalExpertReviewCount.setReviewType("专家评审");
            }
			if ("2".equals(type)) {
				list = finalExpertReviewCountService.countByExpertReview(finalExpertReviewCount, type);
                finalExpertReviewCount.setReviewType("专家复核");
            }

			HSSFWorkbook wb = finalExpertReviewCountService.export(list);
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("终审专家评审情况统计表"+ DateUtils.getDate("yyyyMMddHHmmss"), "UTF-8")+".xls");
			OutputStream outputStream = response.getOutputStream();
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出统计列表！失败信息："+e.getMessage());
		}
//		return "modules/reportforms/finalExpertReviewCount";
		return "redirect:" + adminPath + "/report/expertReview/reviewCount?repage";
	}


}