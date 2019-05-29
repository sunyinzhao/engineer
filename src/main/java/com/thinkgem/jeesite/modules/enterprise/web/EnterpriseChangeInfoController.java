/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseBase;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseBaseService;
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
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseChangeInfo;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseChangeInfoService;

/**
 * 非咨变更Controller
 * @author xqg
 * @version 2018-05-28
 */
@Controller
@RequestMapping(value = "${adminPath}/enterprise/enterpriseChangeInfo")
public class EnterpriseChangeInfoController extends BaseController {

	@Autowired
	private EnterpriseChangeInfoService enterpriseChangeInfoService;
	@Autowired
	private EnterpriseBaseService enterpriseBaseService;
	
	@ModelAttribute
	public EnterpriseChangeInfo get(@RequestParam(required=false) String id) {
		EnterpriseChangeInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = enterpriseChangeInfoService.get(id);
		}
		if (entity == null){
			entity = new EnterpriseChangeInfo();
			//查看企业基本信息时，不是进行入列表数据，而是直接进查看页面。查看信息时也只能查看登录人的基本数据
			EnterpriseBase ent = new EnterpriseBase();
			ent.setUser(UserUtils.getUser());
			EnterpriseBase entityBase = enterpriseBaseService.get(ent);
			if(entityBase!=null){
				entity.setLegalPerson(entityBase.getLegalPerson());
				entity.setLegalPersonNew(entityBase.getLegalPerson());
				entity.setName(entityBase.getCompanyName());
				entity.setNameNew(entityBase.getCompanyName());
				entity.setApplicationCode(entityBase.getApplicationCode());
				entity.setApplicationCodeNew(entityBase.getApplicationCode());
				entity.setRegisterAddress(entityBase.getRegisterAddress());
				entity.setRegisterAddressNew(entityBase.getRegisterAddress());
			}
		}
		return entity;
	}
	
	@RequiresPermissions("enterprise:enterpriseChangeInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(EnterpriseChangeInfo enterpriseChangeInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EnterpriseChangeInfo> page = enterpriseChangeInfoService.findPage(new Page<EnterpriseChangeInfo>(request, response), enterpriseChangeInfo); 
		model.addAttribute("page", page);
		return "modules/enterprise/enterpriseChangeInfoList";
	}

	@RequiresPermissions("enterprise:enterpriseChangeInfo:view")
	@RequestMapping(value = "form")
	public String form(EnterpriseChangeInfo enterpriseChangeInfo, Model model) {
		model.addAttribute("enterpriseChangeInfo", enterpriseChangeInfo);
		return "modules/enterprise/enterpriseChangeInfoForm";
	}

	@RequiresPermissions("enterprise:enterpriseChangeInfo:edit")
	@RequestMapping(value = "save")
	public String save(EnterpriseChangeInfo enterpriseChangeInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, enterpriseChangeInfo)){
			return form(enterpriseChangeInfo, model);
		}
		enterpriseChangeInfoService.save(enterpriseChangeInfo);
		addMessage(redirectAttributes, "保存非咨变更成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseChangeInfo/?repage";
	}
	
	@RequiresPermissions("enterprise:enterpriseChangeInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(EnterpriseChangeInfo enterpriseChangeInfo, RedirectAttributes redirectAttributes) {
		enterpriseChangeInfoService.delete(enterpriseChangeInfo);
		addMessage(redirectAttributes, "删除非咨变更成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseChangeInfo/?repage";
	}

}