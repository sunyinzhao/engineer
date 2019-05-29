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
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseBranchOrg;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseBranchOrgService;

/**
 * 分支机构Controller
 * @author xqg
 * @version 2018-05-06
 */
@Controller
@RequestMapping(value = "${adminPath}/enterprise/enterpriseBranchOrg")
public class EnterpriseBranchOrgController extends BaseController {

	@Autowired
	private EnterpriseBranchOrgService enterpriseBranchOrgService;
	
	@ModelAttribute
	public EnterpriseBranchOrg get(@RequestParam(required=false) String id) {
		EnterpriseBranchOrg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = enterpriseBranchOrgService.get(id);
		}
		if (entity == null){
			entity = new EnterpriseBranchOrg();
		}
		return entity;
	}
	
	@RequiresPermissions("enterprise:enterpriseBranchOrg:view")
	@RequestMapping(value = {"list", ""})
	public String list(String tableType, String declareRecordId ,EnterpriseBranchOrg enterpriseBranchOrg, HttpServletRequest request, HttpServletResponse response, Model model) {
		enterpriseBranchOrg.setPid(UserUtils.getUser().getId());
		Page<EnterpriseBranchOrg> page = enterpriseBranchOrgService.findPage(new Page<EnterpriseBranchOrg>(request, response), enterpriseBranchOrg);
		model.addAttribute("page", page);
		model.addAttribute("tableType", tableType);
		model.addAttribute("declareRecordId", declareRecordId);
		return "modules/enterprise/enterpriseBranchOrgList";
	}

	@RequiresPermissions("enterprise:enterpriseBranchOrg:view")
	@RequestMapping(value = "form")
	public String form(String tableType, String declareRecordId,EnterpriseBranchOrg enterpriseBranchOrg, Model model) {
		model.addAttribute("enterpriseBranchOrg", enterpriseBranchOrg);
		model.addAttribute("tableType", tableType);
		model.addAttribute("declareRecordId", declareRecordId);
		return "modules/enterprise/enterpriseBranchOrgForm";
	}

	@RequiresPermissions("enterprise:enterpriseBranchOrg:edit")
	@RequestMapping(value = "save")
	public String save(String tableType, String declareRecordId,EnterpriseBranchOrg enterpriseBranchOrg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, enterpriseBranchOrg)){
			return form(tableType,declareRecordId,enterpriseBranchOrg, model);
		}
		enterpriseBranchOrg.setPid(UserUtils.getUser().getId());
		enterpriseBranchOrgService.save(enterpriseBranchOrg);
		addMessage(redirectAttributes, "保存分支机构成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseBranchOrg/?repage&tableType="+tableType+"&declareRecordId="+declareRecordId;
	}
	
	@RequiresPermissions("enterprise:enterpriseBranchOrg:edit")
	@RequestMapping(value = "delete")
	public String delete(String tableType, String declareRecordId,EnterpriseBranchOrg enterpriseBranchOrg, RedirectAttributes redirectAttributes) {
		enterpriseBranchOrgService.delete(enterpriseBranchOrg);
		addMessage(redirectAttributes, "删除分支机构成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseBranchOrg/?repage&tableType="+tableType+"&declareRecordId="+declareRecordId;
	}

}