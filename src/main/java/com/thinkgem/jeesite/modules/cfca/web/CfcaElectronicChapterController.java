/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cfca.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cfca.ra.common.vo.request.CertServiceRequestVO;
import cfca.ra.common.vo.response.CertServiceResponseVO;
import cfca.ra.toolkit.RAClient;
import cfca.ra.toolkit.exception.RATKException;
import com.thinkgem.jeesite.modules.cfca.CfcaRaConfig;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.associationinfo.entity.AssociationInfo;
import com.thinkgem.jeesite.modules.cfca.entity.CfcaElectronicChapter;
import com.thinkgem.jeesite.modules.cfca.service.CfcaElectronicChapterService;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignaturePerson;
import com.thinkgem.jeesite.modules.signature.service.ApplySignaturePersonService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 电子签章业绩Controller
 * @author xqg
 * @version 2018-09-03
 */
@Controller
@RequestMapping(value = "${adminPath}/cfca/electronicChapter")
public class CfcaElectronicChapterController extends BaseController {
	
	@Autowired
	private CfcaElectronicChapterService cfcaElectronicChapterService;
	@Autowired
	private EnterpriseWorkersService enterpriseWorkersService;

	
	@ModelAttribute
	public CfcaElectronicChapter get(@RequestParam(required=false) String id) {
		CfcaElectronicChapter entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cfcaElectronicChapterService.get(id);
		}
		if (entity == null){
			entity = new CfcaElectronicChapter();
		}
		return entity;
	}
	
	/**
	 * 
	 * @param cfcaElectronicChapter
	 * @param model
	 * @return
	 */
	@RequiresPermissions("cfca:electronicChapter:view")
	@RequestMapping(value = "form")
	public String form(CfcaElectronicChapter cfcaElectronicChapter, Model model) {
		

		EnterpriseWorkers worker_prm = new EnterpriseWorkers();

		worker_prm.setName(UserUtils.getUser().getName());
		worker_prm.setCertificatesNum(UserUtils.getUser().getCardNumber());
		EnterpriseWorkers workers = enterpriseWorkersService.findEnterpriseWorkers(worker_prm);
		if(null !=workers){
			cfcaElectronicChapter.setUserId(workers.getId());
			List<CfcaElectronicChapter> cfcaElectronicChapterList= cfcaElectronicChapterService.findList(cfcaElectronicChapter);

			if(cfcaElectronicChapterList!=null && cfcaElectronicChapterList.size()>0){
				model.addAttribute("cfcaElectronicChapter", cfcaElectronicChapterList.get(0));
			}else{
				model.addAttribute("cfcaElectronicChapter", cfcaElectronicChapter);
			}
		}

		return "modules/cfca/electronicChapterForm";
	}
	
	@RequiresPermissions("cfca:electronicChapter:view")
	@RequestMapping(value = {"list", ""})
	public String list(CfcaElectronicChapter CfcaElectronicChapter, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CfcaElectronicChapter> page = cfcaElectronicChapterService.findPage(new Page<CfcaElectronicChapter>(request, response), CfcaElectronicChapter); 
		model.addAttribute("page", page);
		return "modules/cfca/electronicChapterList";
	}

	

	@RequiresPermissions("cfca:electronicChapter:edit")
	@RequestMapping(value = "save")
	public String save(String submitType, CfcaElectronicChapter CfcaElectronicChapter, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, CfcaElectronicChapter)){
			return form(CfcaElectronicChapter, model);
		}
		
		cfcaElectronicChapterService.save(CfcaElectronicChapter);
		addMessage(redirectAttributes, "保存电子签章业绩成功");
		if (StringUtils.isNotBlank(submitType) && "0".equals(submitType) ) {
			return "redirect:" + Global.getAdminPath() + "/signature/CfcaElectronicChapter/form?id=" + CfcaElectronicChapter.getId();
		} else {
			return "redirect:"+Global.getAdminPath()+"/signature/CfcaElectronicChapter/?repage";
		}
	}
	
	
	@RequiresPermissions("cfca:electronicChapter:edit")
	@RequestMapping(value = "delete")
	public String delete(CfcaElectronicChapter CfcaElectronicChapter, RedirectAttributes redirectAttributes) {
		cfcaElectronicChapterService.delete(CfcaElectronicChapter);
		addMessage(redirectAttributes, "删除电子签章业绩成功");
		return "redirect:"+Global.getAdminPath()+"/signature/CfcaElectronicChapter/?repage";
	}
	
	




	/**
	 * 协会证书查看
	 * @param cfcaElectronicChapter
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("associationinfo:associationInfo:edit")
	@RequestMapping(value = "certification")
	public String certification(CfcaElectronicChapter cfcaElectronicChapter, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cfcaElectronicChapter)){
			return form(cfcaElectronicChapter, model);
		}
		cfcaElectronicChapter.setUserId(cfcaElectronicChapter.getId());
		List<CfcaElectronicChapter> list = cfcaElectronicChapterService.findList(cfcaElectronicChapter);
		CfcaElectronicChapter chapter= null;
		if(list!=null && list.size()>0){
			chapter=list.get(0);
		}else{
			chapter= new CfcaElectronicChapter();
			chapter.setUserId(cfcaElectronicChapter.getId());
		}
		model.addAttribute("cfcaElectronicChapter", chapter);
		
		return "modules/associationinfo/assciationCertificateForm";
	}
	
	/**
	 * 	协会证书申请
	 * @param CfcaElectronicChapter
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("cfca:electronicChapter:edit")
	@RequestMapping(value = "applyAssociationElectronicChapter")
	@ResponseBody
	public String applyAssociationElectronicChapter(CfcaElectronicChapter CfcaElectronicChapter, RedirectAttributes redirectAttributes) {
		
		boolean result =cfcaElectronicChapterService.applyAssociationCfcaElectronicChapter(CfcaElectronicChapter);
		
		if(result){
			return "true";
		}else{
			return "false";
			
		}
	}

	/**
	 * 更新电子证书两码
	 * @param CfcaElectronicChapter
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("cfca:electronicChapter:edit")
	@RequestMapping(value = "updateApplyAssociationElectronicChapterTwoCode")
	@ResponseBody
	public String updateApplyAssociationElectronicChapterTwoCode(CfcaElectronicChapter CfcaElectronicChapter, RedirectAttributes redirectAttributes) {
		Page paramPage = new Page<CfcaElectronicChapter>();
		paramPage.setPageSize(100000);
		CfcaElectronicChapter parm_chapter = new CfcaElectronicChapter();
		//parm_chapter.setSequenceNo("2");
		Page<CfcaElectronicChapter> page = cfcaElectronicChapterService.findNoDonloadList(paramPage, parm_chapter);
		List <CfcaElectronicChapter>list = page.getList();
		for (CfcaElectronicChapter chapter: list) {
			boolean result =cfcaElectronicChapterService.reSendTwoCode(chapter);
		}
		return "Y";
	}


	/**
	 * 更新电子证书两码
	 * @param CfcaElectronicChapter
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("cfca:electronicChapter:edit")
	@RequestMapping(value = "deleteAgain")
	@ResponseBody
	public String deleteAgain(CfcaElectronicChapter CfcaElectronicChapter, RedirectAttributes redirectAttributes) {
		boolean result=false;
		try {
			 //result = cfcaElectronicChapterService.deleteAgain();
			 List <CfcaElectronicChapter>list = cfcaElectronicChapterService.findNoDonloadList();

            for (CfcaElectronicChapter chapter : list) {

                String dn = chapter.getDn();
                try {
                    RAClient client = CfcaRaConfig.getRAClient();
                    CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
                    certServiceRequestVO.setTxCode("2201");
                    //certServiceRequestVO.setLocale(locale);
                    certServiceRequestVO.setDn(dn);
                    CertServiceResponseVO certServiceResponseVO = (CertServiceResponseVO) client.process(certServiceRequestVO);

                    System.out.println(certServiceResponseVO.getResultCode());
                    System.out.println(certServiceResponseVO.getResultMessage());
                    if (RAClient.SUCCESS.equals(certServiceResponseVO.getResultCode()) || "3110".equals(certServiceResponseVO.getResultCode())) {
                        System.out.println("delete");
                        cfcaElectronicChapterService.delete(chapter);
                    }
                } catch (RATKException e) {
                    e.printStackTrace();
                }
            }

		}catch (Exception e){
			e.printStackTrace();
		}
		result=true;
		if(result){
			return "Y";
		}else{
			return "N";
		}
	}



	/**
	 *
	 * @param chapterId
	 * @return
	 */
	@RequiresPermissions("cfca:electronicChapter:edit")
	@RequestMapping(value = "sysStatus")
	@ResponseBody
	public String sysStatus(String chapterId) {
		boolean result=false;
		try {
			//String userId = request.getParameter("userId");
			if(StringUtils.isNotBlank(chapterId)){
				CfcaElectronicChapter chapter = 	cfcaElectronicChapterService.get(chapterId);
				result= cfcaElectronicChapterService.synchronizationChapterStatus(chapter);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		if(result){
			return "Y";
		}else{
			return "N";
		}
	}

	/**
	 *
	 * @param chapterId
	 * @return
	 */
	@RequiresPermissions("cfca:electronicChapter:edit")
	@RequestMapping(value = "updateStatus")
	@ResponseBody
	public String updateStatus(String chapterId) {
		boolean result=false;
		try {
			if(StringUtils.isNotBlank(chapterId)){
				CfcaElectronicChapter chapter = 	cfcaElectronicChapterService.get(chapterId);
				result= cfcaElectronicChapterService.updateChapterStatus(chapter);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		if(result){
			return "Y";
		}else{
			return "N";
		}
	}


}