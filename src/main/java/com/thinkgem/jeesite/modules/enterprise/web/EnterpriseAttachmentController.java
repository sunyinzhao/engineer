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
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseAttachment;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseAttachmentService;

/**
 * 附件Controller
 * @author xqg
 * @version 2018-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/enterprise/enterpriseAttachment")
public class EnterpriseAttachmentController extends BaseController {

	@Autowired
	private EnterpriseAttachmentService enterpriseAttachmentService;
	
	@ModelAttribute
	public EnterpriseAttachment get(@RequestParam(required=false) String id) {
		EnterpriseAttachment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = enterpriseAttachmentService.get(id);
		}
		if (entity == null){
			entity = new EnterpriseAttachment();
		}
		return entity;
	}
	
	@RequiresPermissions("enterprise:enterpriseAttachment:view")
	@RequestMapping(value = {"list", ""})
	public String list(String declareRecordId,EnterpriseAttachment enterpriseAttachment, HttpServletRequest request, HttpServletResponse response, Model model,String declareRecordStatus,String declareRecordFileNo) {

		enterpriseAttachment.setPid(declareRecordId);
		Page<EnterpriseAttachment> page = enterpriseAttachmentService.findPage(new Page<EnterpriseAttachment>(request, response), enterpriseAttachment); 
		model.addAttribute("page", page);
		model.addAttribute("declareRecordId", declareRecordId);
		model.addAttribute("declareRecordStatus", declareRecordStatus);
        model.addAttribute("declareRecordFileNo", declareRecordFileNo);

		return "modules/enterprise/enterpriseAttachmentList";
	}

	@RequiresPermissions("enterprise:enterpriseAttachment:view")
	@RequestMapping(value = "form")
	public String form(EnterpriseAttachment enterpriseAttachment, Model model,String declareRecordId,String declareRecordStatus,String declareRecordFileNo) {
		if(declareRecordStatus!=null&&declareRecordStatus!=""){
			model.addAttribute("declareRecordStatus", declareRecordStatus);
		}
		model.addAttribute("declareRecordFileNo",declareRecordFileNo);
		model.addAttribute("declareRecordId", declareRecordId);
		model.addAttribute("enterpriseAttachment", enterpriseAttachment);
		return "modules/enterprise/enterpriseAttachmentForm";
	}

	@RequiresPermissions("enterprise:enterpriseAttachment:edit")
	@RequestMapping(value = "save")
	public String save(EnterpriseAttachment enterpriseAttachment, Model model, RedirectAttributes redirectAttributes,String declareRecordStatus,String declareRecordFileNo) {
		if (!beanValidator(model, enterpriseAttachment)){
			return form(enterpriseAttachment, model,null,declareRecordStatus,declareRecordFileNo);
		}
		enterpriseAttachmentService.save(enterpriseAttachment);
		addMessage(redirectAttributes, "保存附件成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseAttachment/?declareRecordId="+enterpriseAttachment.getPid()+"&declareRecordStatus="+declareRecordStatus+"&declareRecordFileNo="+declareRecordFileNo;
	}
	
	@RequiresPermissions("enterprise:enterpriseAttachment:edit")
	@RequestMapping(value = "delete")
	public String delete(EnterpriseAttachment enterpriseAttachment, RedirectAttributes redirectAttributes,String declareRecordStatus,String declareRecordFileNo) {
		enterpriseAttachmentService.delete(enterpriseAttachment);
		addMessage(redirectAttributes, "删除附件成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseAttachment/?declareRecordId="+enterpriseAttachment.getPid()+"&declareRecordStatus="+declareRecordStatus+"&declareRecordFileNo="+declareRecordFileNo;
	}

}