/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.associationinfo.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.associationinfo.entity.AssociationInfo;
import com.thinkgem.jeesite.modules.associationinfo.service.AssociationInfoService;
import com.thinkgem.jeesite.modules.cfca.service.CfcaElectronicChapterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 协会基本信息Controller
 * @author xqg
 * @version 2018-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/associationinfo/associationInfo")
public class AssociationInfoController extends BaseController {

	@Autowired
	private AssociationInfoService associationInfoService;
	@Autowired
	private CfcaElectronicChapterService cfcaElectronicChapterService;
	
	@ModelAttribute
	public AssociationInfo get(@RequestParam(required=false) String id) {
		AssociationInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = associationInfoService.get(id);
		}
		if (entity == null){
			entity = new AssociationInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("associationinfo:associationInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AssociationInfo associationInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AssociationInfo> page = associationInfoService.findPage(new Page<AssociationInfo>(request, response), associationInfo); 
		model.addAttribute("page", page);
		return "modules/associationinfo/associationInfoList";
	}

	@RequiresPermissions("associationinfo:associationInfo:view")
	@RequestMapping(value = "form")
	public String form(AssociationInfo associationInfo, Model model) {
		model.addAttribute("associationInfo", associationInfo);
		return "modules/associationinfo/associationInfoForm";
	}

	@RequiresPermissions("associationinfo:associationInfo:edit")
	@RequestMapping(value = "save")
	public String save(AssociationInfo associationInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, associationInfo)){
			return form(associationInfo, model);
		}
		associationInfoService.save(associationInfo);
		addMessage(redirectAttributes, "保存协会基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/associationinfo/associationInfo/?repage";
	}
	
	@RequiresPermissions("associationinfo:associationInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AssociationInfo associationInfo, RedirectAttributes redirectAttributes) {
		associationInfoService.delete(associationInfo);
		addMessage(redirectAttributes, "删除协会基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/associationinfo/associationInfo/?repage";
	}
	
	


}