/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.qualificationcertificate.web;

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
import com.thinkgem.jeesite.modules.qualificationcertificate.entity.QualificationCertificate;
import com.thinkgem.jeesite.modules.qualificationcertificate.service.QualificationCertificateService;

/**
 * 执业资格证书Controller
 * @author xqg
 * @version 2018-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/qualificationcertificate/qualificationCertificate")
public class QualificationCertificateController extends BaseController {

	@Autowired
	private QualificationCertificateService qualificationCertificateService;
	
	@ModelAttribute
	public QualificationCertificate get(@RequestParam(required=false) String id) {
		QualificationCertificate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qualificationCertificateService.get(id);
		}
		if (entity == null){
			entity = new QualificationCertificate();
		}
		return entity;
	}
	
	@RequiresPermissions("qualificationcertificate:qualificationCertificate:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualificationCertificate qualificationCertificate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QualificationCertificate> page = qualificationCertificateService.findPage(new Page<QualificationCertificate>(request, response), qualificationCertificate); 
		model.addAttribute("page", page);
		return "modules/qualificationcertificate/qualificationCertificateList";
	}

	@RequiresPermissions("qualificationcertificate:qualificationCertificate:view")
	@RequestMapping(value = "form")
	public String form(QualificationCertificate qualificationCertificate, Model model) {
		model.addAttribute("qualificationCertificate", qualificationCertificate);
		return "modules/qualificationcertificate/qualificationCertificateForm";
	}

	@RequiresPermissions("qualificationcertificate:qualificationCertificate:edit")
	@RequestMapping(value = "save")
	public String save(QualificationCertificate qualificationCertificate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qualificationCertificate)){
			return form(qualificationCertificate, model);
		}
		qualificationCertificateService.save(qualificationCertificate);
		addMessage(redirectAttributes, "保存执业资格证书成功");
		return "redirect:"+Global.getAdminPath()+"/qualificationcertificate/qualificationCertificate/?repage";
	}
	
	@RequiresPermissions("qualificationcertificate:qualificationCertificate:edit")
	@RequestMapping(value = "delete")
	public String delete(QualificationCertificate qualificationCertificate, RedirectAttributes redirectAttributes) {
		qualificationCertificateService.delete(qualificationCertificate);
		addMessage(redirectAttributes, "删除执业资格证书成功");
		return "redirect:"+Global.getAdminPath()+"/qualificationcertificate/qualificationCertificate/?repage";
	}

}