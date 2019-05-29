/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseProject;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseProjectService;

/**
 * 企业完成项目Controller
 * @author xqg
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/enterprise/enterpriseProject")
public class EnterpriseProjectController extends BaseController {

	@Autowired
	private EnterpriseProjectService enterpriseProjectService;
	
	@ModelAttribute
	public EnterpriseProject get(@RequestParam(required=false) String id) {
		EnterpriseProject entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = enterpriseProjectService.get(id);
		}
		if (entity == null){
			entity = new EnterpriseProject();
		}
		return entity;
	}
	
	@RequiresPermissions("enterprise:enterpriseProject:view")
	@RequestMapping(value = {"list", ""})
	public String list(String tableType, String declareRecordId ,EnterpriseProject enterpriseProject, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EnterpriseProject> page = enterpriseProjectService.findPage(new Page<EnterpriseProject>(request, response), enterpriseProject); 
		model.addAttribute("page", page);
		
		model.addAttribute("tableType", tableType);
		model.addAttribute("declareRecordId", declareRecordId);
		
		return "modules/enterprise/enterpriseProjectList";
	}

	@RequiresPermissions("enterprise:enterpriseProject:view")
	@RequestMapping(value = "form")
	public String form(String tableType, String declareRecordId ,EnterpriseProject enterpriseProject, Model model) {
		model.addAttribute("enterpriseProject", enterpriseProject);
		model.addAttribute("tableType", tableType);
		model.addAttribute("declareRecordId", declareRecordId);
		return "modules/enterprise/enterpriseProjectForm";
	}

	@RequiresPermissions("enterprise:enterpriseProject:edit")
	@RequestMapping(value = "save")
	public String save(String tableType, String declareRecordId ,EnterpriseProject enterpriseProject, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, enterpriseProject)){
			return form(tableType,declareRecordId,enterpriseProject, model);
		}
		enterpriseProjectService.save(enterpriseProject);
		addMessage(redirectAttributes, "保存项目成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseProject/?repage&tableType="+tableType+"&declareRecordId="+declareRecordId;
	}
	
	@RequiresPermissions("enterprise:enterpriseProject:edit")
	@RequestMapping(value = "delete")
	public String delete(String tableType, String declareRecordId ,EnterpriseProject enterpriseProject, RedirectAttributes redirectAttributes) {
		enterpriseProjectService.delete(enterpriseProject);
		addMessage(redirectAttributes, "删除项目成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseProject/?repage&tableType="+tableType+"&declareRecordId="+declareRecordId;
	}

}