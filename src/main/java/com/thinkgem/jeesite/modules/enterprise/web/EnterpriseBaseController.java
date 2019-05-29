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
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseBase;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseBaseService;

/**
 * 基本数据Controller
 * @author xqg
 * @version 2018-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/enterprise/enterpriseBase")
public class EnterpriseBaseController extends BaseController {

	@Autowired
	private EnterpriseBaseService enterpriseBaseService;
	
	@ModelAttribute
	public EnterpriseBase get(@RequestParam(required=false) String id) {
		EnterpriseBase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = enterpriseBaseService.get(id);
		}
		if (entity == null){
			//查看企业基本信息时，不是进行入列表数据，而是直接进查看页面。查看信息时也只能查看登录人的基本数据
			EnterpriseBase ent = new EnterpriseBase();
			ent.setUser(UserUtils.getUser());
			entity = enterpriseBaseService.get(ent);
			if(entity==null){
				entity= new  EnterpriseBase();
			}
		}
		return entity;
	}
	
	@RequiresPermissions("enterprise:enterpriseBase:view")
	@RequestMapping(value = {"list", ""})
	public String list(EnterpriseBase enterpriseBase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EnterpriseBase> page = enterpriseBaseService.findPage(new Page<EnterpriseBase>(request, response), enterpriseBase); 
		model.addAttribute("page", page);
		return "modules/enterprise/enterpriseBaseList";
	}

	@RequiresPermissions("enterprise:enterpriseBase:view")
	@RequestMapping(value = "form")
	public String form(EnterpriseBase enterpriseBase, Model model) {


		model.addAttribute("enterpriseBase", enterpriseBase);
		return "modules/enterprise/enterpriseBaseForm";
	}

	@RequiresPermissions("enterprise:enterpriseBase:edit")
	@RequestMapping(value = "save")
	public String save(EnterpriseBase enterpriseBase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, enterpriseBase)){
			return form(enterpriseBase, model);
		}
		enterpriseBaseService.save(enterpriseBase);
		addMessage(redirectAttributes, "保存基本数据成功");
		
//		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseBase/?repage";
		
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseBase/form?id="+enterpriseBase.getId();
	}
	
	@RequiresPermissions("enterprise:enterpriseBase:edit")
	@RequestMapping(value = "delete")
	public String delete(EnterpriseBase enterpriseBase, RedirectAttributes redirectAttributes) {
		enterpriseBaseService.delete(enterpriseBase);
		addMessage(redirectAttributes, "删除基本数据成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseBase/?repage";
	}

}