/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseStockProportion;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseStockProportionService;

/**
 * 股权结构Controller
 * @author xqg
 * @version 2018-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/enterprise/enterpriseStockProportion")
public class EnterpriseStockProportionController extends BaseController {

	@Autowired
	private EnterpriseStockProportionService enterpriseStockProportionService;
	
	@ModelAttribute
	public EnterpriseStockProportion get(@RequestParam(required=false) String id) {
		EnterpriseStockProportion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = enterpriseStockProportionService.get(id);
		}
		if (entity == null){
			entity = new EnterpriseStockProportion();
		}
		return entity;
	}
	
	@RequiresPermissions("enterprise:enterpriseStockProportion:view")
	@RequestMapping(value = {"list", ""})
	public String list( String tableType, String declareRecordId ,EnterpriseStockProportion enterpriseStockProportion, HttpServletRequest request, HttpServletResponse response, Model model) {
	
		enterpriseStockProportion.setPid(UserUtils.getUser().getId()); //只查询当前登录人数据
		Page<EnterpriseStockProportion> page = enterpriseStockProportionService.findPage(new Page<EnterpriseStockProportion>(request, response), enterpriseStockProportion);
		
		model.addAttribute("page", page);
		model.addAttribute("tableType", tableType);
		model.addAttribute("declareRecordId", declareRecordId);
		
		return "modules/enterprise/enterpriseStockProportionList";
	}

	@RequiresPermissions("enterprise:enterpriseStockProportion:view")
	@RequestMapping(value = "form")
	public String form(String tableType, String declareRecordId ,EnterpriseStockProportion enterpriseStockProportion, Model model) {
		model.addAttribute("enterpriseStockProportion", enterpriseStockProportion);
		model.addAttribute("tableType", tableType);
		model.addAttribute("declareRecordId", declareRecordId);
		return "modules/enterprise/enterpriseStockProportionForm";
	}

	@RequiresPermissions("enterprise:enterpriseStockProportion:edit")
	@RequestMapping(value = "save")
	public String save(String tableType, String declareRecordId ,EnterpriseStockProportion enterpriseStockProportion, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, enterpriseStockProportion)){
			return form(tableType,declareRecordId, enterpriseStockProportion, model);
		}
		enterpriseStockProportion.setPid(UserUtils.getUser().getId()); //Pid为当前登录人的ID
		enterpriseStockProportionService.save(enterpriseStockProportion);
		addMessage(redirectAttributes, "保存股权结构成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseStockProportion/?repage&tableType="+tableType+"&declareRecordId="+declareRecordId;
	}
	
	@RequiresPermissions("enterprise:enterpriseStockProportion:edit")
	@RequestMapping(value = "delete")
	public String delete(String tableType, String declareRecordId ,EnterpriseStockProportion enterpriseStockProportion, RedirectAttributes redirectAttributes) {
		enterpriseStockProportionService.delete(enterpriseStockProportion);
		addMessage(redirectAttributes, "删除股权结构成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseStockProportion/?repage&tableType="+tableType+"&declareRecordId="+declareRecordId;
	}

}