/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.web;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.enterprise.entity.*;
import com.thinkgem.jeesite.modules.reportforms.entity.exportEffectiveCounselorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SendMessge;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.ChangeItem;
import com.thinkgem.jeesite.modules.counselor.entity.JobKnowledge;
import com.thinkgem.jeesite.modules.counselor.service.AddCheckService;
import com.thinkgem.jeesite.modules.counselor.service.ChangeItemService;
import com.thinkgem.jeesite.modules.counselor.service.JobKnowledgeService;
import com.thinkgem.jeesite.modules.counselor.service.SaveExpertService;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.enterprise.service.PersonRecordService;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 个人用户审核和咨询师上报信息Controller
 * 
 * @author
 * @version 2018-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/enterprise/auditAndReport")
public class UserAuditAndConsultantReportController extends BaseController {

	@Autowired
	private EnterpriseWorkersService enterpriseWorkersService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private PersonRecordService personRecordService;
	
	@Autowired
	private AddCheckService addCheckService;
	
	@Autowired
	private SaveExpertService saveExpertService;
	
    @Autowired
    private JobKnowledgeService jobKnowledgeService;
    
    @Autowired
    private ChangeItemService changeItemService;

	// ---------------------------------------单位模块-------------------------------------------
	/**
	 * 单位待审个人用户列表
	 * 
	 * @param model
	 * @return name,sex,certificatesType,certificatesNum,isValid,create_data;
	 *         confimFlg
	 */
	@RequestMapping(value = "pendingList")
	public String pendingList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和sys_user表查询个人用户列表
		User user = UserUtils.getUser();
		if (user.getOffice() == null) {
			return "modules/enterprise/pendingList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				enterpriseWorkers.setPid(user.getId());
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}
		}
		return "modules/enterprise/pendingList";
	}

	/**
	 * 单位审核个人用户通过
	 * 
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "pass")
	public String pass(EnterpriseWorkers enterpriseWorkers, RedirectAttributes redirectAttributes) {
		// 修改sys_user的confim_flag 状态为1
		User user = new User();
		user.setId(enterpriseWorkers.getUserId());
		user.setConfimFlg("1");
		//更新enterprise_workers的isvalid状态为1（登记且有效）
		//enterpriseWorkers.setIsValid(enterpriseWorkers.getIsValid()); //delete by gaoyongjian 通过时不需要修改状态
		//this.enterpriseWorkersService.updateisValid(enterpriseWorkers);
		//给该专家赋予权限
		Role role = new Role();
		role.setUser(user);
		role.setId("9");
		this.systemService.updateRoleByUser(role);
		//修改confirm状态
		this.systemService.updateUserConfirmFlgNew(user);
		addMessage(redirectAttributes, "已通过该用户申请");
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/pendingList";
	}

	/**
	 * 单位退回个人用户申请
	 * 
	 * @param enterpriseBase
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "goBack")
	public String goBack(EnterpriseWorkers enterpriseWorkers, RedirectAttributes redirectAttributes) {
		// 修改sys_user的confim_flag状态为2
		User user = new User();
		user.setId(enterpriseWorkers.getUserId());
		user.setConfimFlg("2");
		user.setOfficeId("");
		this.systemService.updateUserConfirmFlgNew(user);
		//根据ID清除该用户的公司信息，并将isvalid状态修改为0（登记且无效）
//		enterpriseWorkers.setIsValid("0");
		enterpriseWorkers.setPid("");
		enterpriseWorkers.setCompanyName("");
		this.enterpriseWorkersService.updateCompanyInfo(enterpriseWorkers);
		addMessage(redirectAttributes, "已退回该用户申请");
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/pendingList";
	}

	/**
	 * 单位待接收个人用户申请列表
	 * 
	 * @param model
	 * @return name,sex,certificatesType,certificatesNum,isValid,create_data;
	 *         confimFlg
	 */
	@RequestMapping(value = "waitReceiveList")
	public String waitReceiveList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和person_record表查询待接收列表
		User user = UserUtils.getUser();
		if (user.getOffice() == null) {// 判断office是否为空
			return "modules/enterprise/waitReceiveList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				enterpriseWorkers.setBatchStatus("2");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList01(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				enterpriseWorkers.setPid(user.getId());
				enterpriseWorkers.setBatchStatus("2");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList01(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}
		}
		return "modules/enterprise/waitReceiveList";
	}

	/**
	 * 单位接收个人用户
	 * 
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "passWaitReceive")
	public String passWaitReceive(EnterpriseWorkers enterpriseWorkers, RedirectAttributes redirectAttributes) {
		// 修改person_record的person_status 状态为3
		PersonRecord personRecord = new PersonRecord();
		personRecord.setId(enterpriseWorkers.getPersonRecordId());
		personRecord.setBatchStatus("3");
		personRecord.setDeclareStatus("3");
		this.personRecordService.updateBatchStatus(personRecord);//更新批次状态
		this.personRecordService.updateDeclareStatusNew(personRecord);//更新申请单状态
		addMessage(redirectAttributes, "已接收该用户申请");
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/waitReceiveList";
	}

	/**
	 * 单位批量通过个人用户申请
	 * 
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "passWaitReceiveList")
	public String passWaitReceiveList(String[] personRecordId, RedirectAttributes redirectAttributes) {
		// 切割personRecordId
		if (personRecordId.length > 0) {
			PersonRecord personRecord = new PersonRecord();
			for (int i = 0; i < personRecordId.length; i++) {
				// 修改person_record的person_status 状态为3
				personRecord.setId(personRecordId[i]);
				personRecord.setBatchStatus("3");
				personRecord.setDeclareStatus("3");
				this.personRecordService.updateBatchStatus(personRecord);//更新批次状态
				this.personRecordService.updateDeclareStatusNew(personRecord);//更新申请单状态
			}
		} else {
			return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/waitReceiveList";
		}
		addMessage(redirectAttributes, "已接收该用户申请");
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/waitReceiveList";
	}

	/**
	 * 单位退回个人用户申请
	 * 
	 * @param enterpriseBase
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "goBackWaitReceive")
	public String goBackWaitReceive(EnterpriseWorkers enterpriseWorkers, RedirectAttributes redirectAttributes) {
		// 修改person_record的person_status 状态为1
		PersonRecord personRecord = new PersonRecord();
		personRecord.setBatchId(enterpriseWorkers.getBatchId());
		personRecord.setBatchStatus("1");
		personRecord.setDeclareStatus("1");
		//根据batchId查询该批次的所有信息
		List<PersonRecord> findByBatchId = this.personRecordService.findByBatchId(personRecord);
		for (int i = 0; i < findByBatchId.size(); i++) {
			personRecord.setId(findByBatchId.get(i).getId());
			this.personRecordService.updateBatchStatus(personRecord);
			this.personRecordService.updateDeclareStatusNew(personRecord);
		}
		addMessage(redirectAttributes, "已退回该用户申请");
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/waitReceiveList";
	}

	/**
	 * 单位待上报地方协会申请列表
	 * 
	 * @param model
	 * @return name,sex,certificatesType,certificatesNum,isValid,create_data;
	 *         confimFlg
	 */
	@RequestMapping(value = "waitReportList")
	public String waitReportList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和person_record表查询待上报申请列表
		User user = UserUtils.getUser();
		if (user.getOffice() == null) {// 判断office是否为空
			return "modules/enterprise/waitReportList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				//enterpriseWorkers.setDeclareStatus("3");
				enterpriseWorkers.setBatchStatus("3");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList01(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				enterpriseWorkers.setPid(user.getId());
				enterpriseWorkers.setBatchStatus("3");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList01(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}
		}
		return "modules/enterprise/waitReportList";
	}

	/**
	 * 单位已上报地方协会申请列表
	 * 
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "alreadyReportList")
	public String alreadyReportList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和person_record表查询待上报申请列表
		User user = UserUtils.getUser();

		if (user.getOffice() == null) {// 判断office是否为空
			return "modules/enterprise/alreadyReportList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				enterpriseWorkers.setBatchStatus("5");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList02(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				enterpriseWorkers.setPid(user.getId());
				enterpriseWorkers.setBatchStatus("5");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList02(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}
		}
		return "modules/enterprise/alreadyReportList";
	}
	
	/**
	 * 本单位咨询师列表
	 * @param enterpriseWorkers
	 * @param model
	 * @return
	 */
	@RequestMapping(value="counselorList")
	public String counselorList(String export, EnterpriseWorkers enterpriseWorkers,Model model,HttpServletResponse response,HttpServletRequest request, RedirectAttributes redirectAttributes){
		//1.获取当前单位登录人的Id
		String id = UserUtils.getUser().getId();

		//2.根据单位id查询该单位下的所有咨询师（不包括已冻结咨询师）
		if ("2".equals(UserUtils.getUser().getUserModel()))
		{
			if(UserUtils.getUser().getOffice().getId() == null || UserUtils.getUser().getOffice().getId().trim().equals(""))
			{
				return "modules/enterprise/counselorList";
			}
			enterpriseWorkers.setPid("");
			enterpriseWorkers.setOfficeId(UserUtils.getUser().getOffice().getId());
		}
		else
		{
			if(id == null || id.trim().equals(""))
			{
				return "modules/enterprise/counselorList";
			}
			enterpriseWorkers.setPid(id);
		}
		enterpriseWorkers.setIsFreeze("0");
		Page<EnterpriseWorkers> page = this.enterpriseWorkersService.findCounselorList(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
		// 本单位咨询师列表 - 导出
		if ("1".equals(export)) {
			List<EnterpriseWorkers> list = this.enterpriseWorkersService.findCounselorList(enterpriseWorkers);
			List<EnterpriseWorkersUnitExport> exportList = new ArrayList<EnterpriseWorkersUnitExport>();
			if(list!=null && list.size()>0){
				int index = 1;
				for (EnterpriseWorkers work : list) {
					work.setIndex(index++);
					exportList.add(new EnterpriseWorkersUnitExport(work));
				}
			}
			String fileName = "本单位咨询师列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			try {
				new ExportExcel("本单位咨询师列表", EnterpriseWorkersUnitExport.class).setDataList(exportList).write(response, fileName).dispose();
				return "redirect:" + adminPath + "/enterprise/auditAndReport/counselorList";
			} catch (IOException e) {
				e.printStackTrace();
				addMessage(redirectAttributes, "本单位咨询师列表！失败信息："+e.getMessage());
			}
		}
		model.addAttribute("page", page);
		return "modules/enterprise/counselorList";
	}

	/**
	 * 转入咨询师列表
	 * @param enterpriseWorkers
	 * @param model
	 * @return
	 */
	@RequestMapping(value="counselorInList")
	public String counselorInList(EnterpriseWorkers enterpriseWorkers,Model model,HttpServletResponse response,HttpServletRequest request){
		//1.获取当前单位登录人的Id
		String id = UserUtils.getUser().getId();

		//2.根据单位id查询该单位下的所有咨询师（不包括已冻结咨询师）
		if ("2".equals(UserUtils.getUser().getUserModel()))
		{
			if(UserUtils.getUser().getOffice().getId() == null || UserUtils.getUser().getOffice().getId().trim().equals(""))
			{
				return "modules/enterprise/counselorInList";
			}
			enterpriseWorkers.setPid("");
			enterpriseWorkers.setOfficeId(UserUtils.getUser().getOffice().getId());
		}
		else
		{
			if(id == null || id.trim().equals(""))
			{
				return "modules/enterprise/counselorInList";
			}
			enterpriseWorkers.setPid(id);
		}
		enterpriseWorkers.setIsFreeze("0");
		Page<EnterpriseWorkers> page = this.enterpriseWorkersService.findCounselorInList(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
		model.addAttribute("page", page);
		return "modules/enterprise/counselorInList";
	}
	/**
	 * 转出咨询师列表
	 * @param enterpriseWorkers
	 * @param model
	 * @return
	 */
	@RequestMapping(value="counselorOutList")
	public String counselorOutList(EnterpriseWorkers enterpriseWorkers,Model model,HttpServletResponse response,HttpServletRequest request){
		//1.获取当前单位登录人的Id
		String id = UserUtils.getUser().getId();

		//2.根据单位id查询该单位下的所有咨询师（不包括已冻结咨询师）
		if ("2".equals(UserUtils.getUser().getUserModel()))
		{
			if(UserUtils.getUser().getOffice().getId() == null || UserUtils.getUser().getOffice().getId().trim().equals(""))
			{
				return "modules/enterprise/counselorOutList";
			}
			enterpriseWorkers.setPid("");
			enterpriseWorkers.setOfficeId(UserUtils.getUser().getOffice().getId());
		}
		else
		{
			if(id == null || id.trim().equals(""))
			{
				return "modules/enterprise/counselorOutList";
			}
			enterpriseWorkers.setPid(id);
		}
		enterpriseWorkers.setIsFreeze("0");
		Page<EnterpriseWorkers> page = this.enterpriseWorkersService.findCounselorOutList(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
		model.addAttribute("page", page);
		return "modules/enterprise/counselorOutList";
	}
	// ---------------------------------------中咨协会模块------------------------------------------------
	/**
	 * 中咨协会待审核地方协会申请列表
	 * 
	 * @param model
	 * @return name,sex,certificatesType,certificatesNum,isValid,create_data;
	 *         confimFlg
	 */
	@RequestMapping(value = "waitAuditApplyList")
	public String waitAuditApplyList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和person_record、sys_user表查询待审核申请列表
		User user = UserUtils.getUser();
		if (user.getOffice() == null) {// 判断office是否为空
			return "modules/enterprise/waitAuditApplyList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId()) && !"5".equals(user.getUserModel())) {
				enterpriseWorkers.setBatchStatus("12");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList033(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			} else if("1".equals(user.getOffice().getId()) && "5".equals(user.getUserModel())){//如果是专家，只允许看自己的申请单				
				//enterpriseWorkers.setOfficeId(user.getOffice().getId());
				enterpriseWorkers.setBatchStatus("12");
				enterpriseWorkers.setUserId(user.getId());
				
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList033(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}else{// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				enterpriseWorkers.setOfficeId(user.getOffice().getId());
				enterpriseWorkers.setBatchStatus("12");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList033(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}
		}
		model.addAttribute("userid", user.getId());
		return "modules/enterprise/waitAuditApplyList";
	}
	
	@RequestMapping(value = "waitFexpertList")
	public String waitFexpertList(EnterpriseWorkers enterpriseWorkers,String rbtvalue, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和person_record、sys_user表查询待审核申请列表
		User user = UserUtils.getUser();
		if (user.getOffice() == null) {// 判断office是否为空
			return "modules/enterprise/waitAuditApplyList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId()) && "2".equals(user.getUserModel())) {
				enterpriseWorkers.setBatchStatus("17");
				enterpriseWorkers.setConfimFlg(rbtvalue);
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList035(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			} else if("1".equals(user.getOffice().getId()) && "5".equals(user.getUserModel())){//如果是专家，只允许看自己的申请单				
				//enterpriseWorkers.setOfficeId(user.getOffice().getId());
				enterpriseWorkers.setBatchStatus("17");
				enterpriseWorkers.setConfimFlg(rbtvalue);
				enterpriseWorkers.setUserId(user.getId());
				
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList035(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}else{// 如果不是中咨协会管理员
			}
		}
		model.addAttribute("userid", user.getId());
		return "modules/enterprise/waitFexpertList";
	}

	/**
	 * 中咨协会已审核地方协会申请列表
	 * 
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "alreadyAuditApplyList")
	public String alreadyAuditApplyList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和person_record、sys_user表查询已审核申请列表
		User user = UserUtils.getUser();

		if (user.getOffice() == null) {// 判断office是否为空
			return "modules/enterprise/alreadyAuditApplyList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId()) && "2".equals(user.getUserModel())) {
				enterpriseWorkers.setBatchStatus("13");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList034(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			} else if("1".equals(user.getOffice().getId()) && "5".equals(user.getUserModel())){//如果是专家，只允许看自己的申请单
				//enterpriseWorkers.setOfficeId(user.getOffice().getId());
				enterpriseWorkers.setBatchStatus("13");
				enterpriseWorkers.setUserId(user.getId());
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList034(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				enterpriseWorkers.setOfficeId(user.getOffice().getId());
				enterpriseWorkers.setBatchStatus("13");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList034(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}
		}
		model.addAttribute("usermodel", user.getUserModel());
		return "modules/enterprise/alreadyAuditApplyList";
	}
	
	
	/**
	 * 中咨协会专家意见复核
	 * 
	 */
	@RequestMapping(value = "expertSuggestionCheckList")
	public String expertSuggestionCheckList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		//1.判断当前用户是否是中咨协会用户
		if("1".equals(user.getOffice().getId())){
//			if("".equals(enterpriseWorkers.getExpertName())){
////				System.out.println("未获取到专家姓名");
//			}else{
//				User user1 = this.systemService.getUserByName(enterpriseWorkers.getExpertName());
//				if(user1 != null){
//					enterpriseWorkers.setFirstCexpertId(user1.getId());
//				}
//			}
			enterpriseWorkers.setBatchStatus("18");
			Page<EnterpriseWorkers> page = this.enterpriseWorkersService.expertSuggestionCheckList1(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
			model.addAttribute("page", page);
			return "modules/enterprise/expertSuggestionCheckList";
		}else{
			addMessage(redirectAttributes, "操作失败，您不具有该权限");
			return "modules/enterprise/expertSuggestionCheckList";
		}
	}
	
	
	/*
	 * 中咨协会复议分配专家
	 */
	@RequestMapping(value = "reconsiderDistributeExpert")
	public String zzReconsiderDistributeExpertList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		//1.判断当前用户是否是中咨协会用户
		if("1".equals(user.getOffice().getId())){
			if("".equals(enterpriseWorkers.getExpertName())){
				System.out.println("未获取到专家姓名");
			}else{
				if(user != null){
					//enterpriseWorkers.setFirstCexpertId(user1.getId());
				}
			}
			enterpriseWorkers.setBatchStatus("17");
			Page<EnterpriseWorkers> page = this.enterpriseWorkersService.reconsiderDistributeExpert(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
			model.addAttribute("page", page);
			model.addAttribute("flag", enterpriseWorkers.getFlag());
			return "modules/enterprise/zzReconsiderDistributeExpertList";
		}else{
			addMessage(redirectAttributes, "操作失败，您不具有该权限");
			return "modules/enterprise/zzReconsiderDistributeExpertList";
		}
	}
	
	
	/**
	 * 复议分配专家
	 */
	@RequestMapping(value="reconsiderManualDistribute")
	public String reconsiderManualDistribute(EnterpriseWorkers enterpriseWorkers,Model model,RedirectAttributes redirectAttributes){
		//1.根据userId获取该专家的专业信息
		String userId = enterpriseWorkers.getUserId();
		User user = this.systemService.getUser(userId);
		if(user != null){
			enterpriseWorkers.setBatchStatus("17");
			List<EnterpriseWorkers> waitDistributeList = this.enterpriseWorkersService.reconsiderDistributeExpert(enterpriseWorkers);
			for (int i = 0; i < waitDistributeList.size(); i++) {
				PersonRecord personRecord = new PersonRecord();
				personRecord.setId(waitDistributeList.get(i).getPersonRecordId());
				personRecord.setfExpertId(user.getId());
				this.personRecordService.updatefExpertId(personRecord);
			}
		}
		return "modules/enterprise/zzReconsiderDistributeExpertList";
	}
	
	
	/**
	 * 中咨协会专家意见告知列表
	 * expertSuggestionPublicList
	 */
	@RequestMapping(value = "expertSuggestionPublicList")
	public String expertSuggestionPublicList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		//1.判断当前用户是否是中咨协会用户
		if("1".equals(user.getOffice().getId())){
//			if("".equals(enterpriseWorkers.getExpertName())){
////				System.out.println("未获取到专家姓名");
//			}else{
//				User user1 = this.systemService.getUserByName(enterpriseWorkers.getExpertName());
//				if(user1 != null){
//					enterpriseWorkers.setFirstCexpertId(user1.getId());
//				}
//			}
			enterpriseWorkers.setBatchStatus("13");
			Page<EnterpriseWorkers> page = this.enterpriseWorkersService.expertSuggestionCheckList(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
			model.addAttribute("page", page);
			return "modules/enterprise/expertSuggestionPublicList";
		}else{
			addMessage(redirectAttributes, "操作失败，您不具有该权限");
			return "modules/enterprise/expertSuggestionPublicList";
		}
	}
	
	/**
	 * 中咨协会专家意见批量公告
	 * 
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "passExpertSuggestionPublic")
	public String passExpertSuggestionPublic(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes) {
		
		enterpriseWorkers.setBatchStatus("13");
		Page<EnterpriseWorkers> page = this.enterpriseWorkersService.expertSuggestionCheckListPage(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
		
		// 切割personRecordId
		if (page.getList() != null && page.getList().size()>0) {
			PersonRecord personRecord = new PersonRecord();
			for (int i = 0; i < page.getList().size(); i++) {
				// 修改person_record的person_status 状态为3
				personRecord.setId(page.getList().get(i).getPersonRecordId());
				personRecord.setDeclareStatus("17");
				personRecord.setBatchStatus("17");
				this.personRecordService.updateBatchStatus(personRecord);
				this.personRecordService.updateDeclareStatusNew(personRecord);
			}
		} else {
			return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/expertSuggestionPublicList";
		}
		addMessage(redirectAttributes, "专家意见告知完毕");
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/expertSuggestionPublicList";
	}
	
	/**
	 *中咨协会专家意见公告
	 * 
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "expertSuggestionPublic")
	public String expertSuggestionPublic(EnterpriseWorkers enterpriseWorkers,
			RedirectAttributes redirectAttributes) {
		// 根据id修改person_record的person_status 状态为17
		PersonRecord personRecord = new PersonRecord();
		personRecord.setId(enterpriseWorkers.getPersonRecordId());
		personRecord.setBatchStatus("17");
		personRecord.setDeclareStatus("17");
		this.personRecordService.updateDeclareStatusNew(personRecord);
		//setBatchId
		personRecord.setBatchId(enterpriseWorkers.getBatchId());
		//根据batch_id查询declarestatus状态
		List<PersonRecord> findByBatchId = this.personRecordService.findByBatchId(personRecord);
		int index = 0;
		for (int i = 0; i < findByBatchId.size(); i++) {
			if("17".equals(findByBatchId.get(i).getDeclareStatus())){
				index +=1;
			}
		}
		if(index == findByBatchId.size()){
			this.personRecordService.updateBatchStatus(personRecord);
			addMessage(redirectAttributes, "专家意见公告完毕");
		}else{
			addMessage(redirectAttributes, "专家意见公告完毕");
		}
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/expertSuggestionPublicList";
	}
	
	/**
	 * 中咨协会处罚处理信息列表
	 * 
	 */
	@RequestMapping(value = "punishList")
	public String punishList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		//1.判断当前用户是否是中咨协会用户
		if("1".equals(user.getOffice().getId())){
			enterpriseWorkers.setImpropriety("0");
			Page<EnterpriseWorkers> page = this.enterpriseWorkersService.punishList(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
			model.addAttribute("page", page);
			return "modules/enterprise/punishList";
		}else{
			addMessage(redirectAttributes, "操作失败，您不具有该权限");
			return "modules/enterprise/punishList";
		}
	}
	
	
	/**
	 * 中咨协会待公告申请列表
	 * 
	 */
	@RequestMapping(value = "zWaitPublicApplyList")
	public String zWaitPublicApplyList(PersonRecord personRecord, HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if(personRecord != null){
			Date startDate = personRecord.getDeclareStartDate();
			Date endDate = personRecord.getDeclareEndDate();
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
		}
		//1.判断当前用户是否是中咨协会用户
		if("1".equals(user.getOffice().getId())){
			personRecord.setBatchStatus("19");
			Page<PersonRecord> page = this.personRecordService.zWaitPublicApplyList(new Page<PersonRecord>(request, response), personRecord);
			model.addAttribute("page", page);
			return "modules/enterprise/zWaitPublicApplyList";
		}else{
			addMessage(redirectAttributes, "操作失败，您不具有该权限");
			return "modules/enterprise/zWaitPublicApplyList";
		}
	}
	
	/**
	 * 中咨协会公告方法
	 * 
	 */
	@RequestMapping(value = "zPublicApplyFunc")
	public String zPublicApplyFunc(PersonRecord personRecord, HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if("".equals(personRecord.getBatchNo().toString())){
			addMessage(redirectAttributes, "请输入公告批次！");
			return "modules/enterprise/zWaitPublicApplyList";
		}
		if(personRecord.getPublicDate() ==null){
			addMessage(redirectAttributes, "请选择公告日期！");
			return "modules/enterprise/zWaitPublicApplyList";
		}
		
		SimpleDateFormat f3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String publicdate = f3.format(personRecord.getPublicDate());
		if("1".equals(personRecord.getDeclareType())){
			Map<String,String> map =new HashMap();
			map.put("p1", personRecord.getBatchNo());
			map.put("p2", publicdate);
			this.personRecordService.chushigonggao(map);
			Object object = map.get("p3");
			
			addMessage(redirectAttributes,"已公告"+ Integer.parseInt(String.valueOf(object ))+"条初始登记事项！");
			return "modules/enterprise/zWaitPublicApplyList";
		}else if ("2".equals(personRecord.getDeclareType())){
			Map<String,String> map =new HashMap();
			map.put("p1", personRecord.getBatchNo());
			map.put("p2", publicdate);
			this.personRecordService.bdgonggao(map);
			Object object = map.get("p3");
			
			addMessage(redirectAttributes,"已公告"+ Integer.parseInt(String.valueOf(object ))+"条变更单位登记事项！");
			return "modules/enterprise/zWaitPublicApplyList";
		}else if ("3".equals(personRecord.getDeclareType())){
			Map<String,String> map =new HashMap();
			map.put("p1", personRecord.getBatchNo());
			map.put("p2", publicdate);
			this.personRecordService.dzgonggao(map);
			Object object = map.get("p3");
			
			addMessage(redirectAttributes,"已公告"+ Integer.parseInt(String.valueOf(object ))+"条变更专业登记事项！");
			return "modules/enterprise/zWaitPublicApplyList";
		}else if ("4".equals(personRecord.getDeclareType())){
			Map<String,String> map =new HashMap();
			map.put("p1", personRecord.getBatchNo());
			map.put("p2", publicdate);
			this.personRecordService.jxgonggao(map);
			Object object = map.get("p3");
			
			addMessage(redirectAttributes,"已公告"+ Integer.parseInt(String.valueOf(object ))+"条继续登记事项！");
			return "modules/enterprise/zWaitPublicApplyList";
		}else if ("0".equals(personRecord.getDeclareType())){
			Map<String,String> map =new HashMap();
			map.put("p1", personRecord.getBatchNo());
			map.put("p2", publicdate);
			this.personRecordService.zxgonggao(map);
			Object object = map.get("p3");
			
			addMessage(redirectAttributes,"已公告"+ Integer.parseInt(String.valueOf(object ))+"条注销记事项！");
			return "modules/enterprise/zWaitPublicApplyList";
		}else{
			String ggNum="已公告";
			Map<String,String> map =new HashMap();
			map.put("p1", personRecord.getBatchNo());
			map.put("p2", publicdate);
			this.personRecordService.chushigonggao(map);
			Object object = map.get("p3");
			ggNum=ggNum + Integer.parseInt(String.valueOf(object )) +"条初始登记；";
			this.personRecordService.bdgonggao(map);
		    object = map.get("p3");
			ggNum=ggNum + Integer.parseInt(String.valueOf(object )) +"条变更单位登记；";
			this.personRecordService.dzgonggao(map);
			 object = map.get("p3");
			ggNum=ggNum + Integer.parseInt(String.valueOf(object )) +"条变更专业登记；";
			this.personRecordService.jxgonggao(map);
			object = map.get("p3");
			ggNum=ggNum +Integer.parseInt(String.valueOf(object )) +"条继续登记；";
			this.personRecordService.zxgonggao(map);
			object = map.get("p3");
			ggNum=ggNum + Integer.parseInt(String.valueOf(object )) +"条注销登记；";
			
			return "modules/enterprise/zWaitPublicApplyList";
		}
	}
	
	/**
	 * 根据batchId判断当前批次是否存在多条申请单
	 */
	@ResponseBody
	@RequestMapping(value = "batchNumber")
	public void deleteInfo(@RequestParam(required = false) String batchId,@RequestParam(required = false) String personRecordId,
			HttpServletRequest request, HttpServletResponse response,Model model) {
		// 创建一个flag,如果该批次有多条申请单返回true，否则返回false
		String flag = "";
		PersonRecord personRecord = new PersonRecord();
		personRecord.setBatchId(batchId);
		//根据batchId查询该批次是否有多条数据
		List<PersonRecord> findByBatchId = this.personRecordService.findByBatchId(personRecord);
		StringBuffer stringBuffer = new StringBuffer();
		if(findByBatchId !=null && findByBatchId.size()>1){
			for (int i = 0; i < findByBatchId.size(); i++) {
				//排除本条数据
				if(personRecordId.equals(findByBatchId.get(i).getId())){
					
				}else{//将其他申请类型进行拼接
					 if(findByBatchId.get(i).getBatchType().equals("1")){
						 stringBuffer.append("初始登记,");
				        }else if(findByBatchId.get(i).getBatchType().equals("2")){
				        	stringBuffer.append("变更执业单位,");
				        }else if(findByBatchId.get(i).getBatchType().equals("3")){
				        	stringBuffer.append("变更专业,");
				        }else if(findByBatchId.get(i).getBatchType().equals("4")){
				        	stringBuffer.append("继续登记,");
				       }
				}
			}
			flag =stringBuffer.toString();
		}else{
			flag = "";
		}
		try {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		out.print(flag);
		out.flush();
		out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 处罚
	 * @return
	 */
	@RequestMapping(value = "punish")
	public String punish(EnterpriseWorkers enterpriseWorkers,Model model,RedirectAttributes redirectAttributes){
		//根据personRecordId修改ispunish为1
		PersonRecord personRecord = new PersonRecord();
		personRecord.setId(enterpriseWorkers.getPersonRecordId());
		personRecord.setIsPunish("1");
		personRecord.setBatchId(enterpriseWorkers.getBatchId());
		//根据batchId查询该批次是否有多条数据
		List<PersonRecord> findByBatchId = this.personRecordService.findByBatchId(personRecord);
		for (int i = 0; i < findByBatchId.size(); i++) {
			if("1".equals(findByBatchId.get(i).getIsPunish())){
				model.addAttribute("isPunish", "true");
				return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/punishList";
			}
		}
		this.personRecordService.updateIsPunish(personRecord);
		addMessage(redirectAttributes, "处罚成功");
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/punishList";
	}

	/**
	 * 中咨协会待接收地方协会申请列表
	 * 
	 * @param model
	 * @return name,sex,certificatesType,certificatesNum,isValid,create_data;
	 *         confimFlg
	 */
	@RequestMapping(value = "zzWaitReceiveApplyList")
	public String zzWaitReceiveApplyList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/enterprise/zzWaitReceiveApplyList";
	}

	/**
	 * 中咨协会待接收地方协会申请列表
	 * 
	 * @param model
	 * @return name,sex,certificatesType,certificatesNum,isValid,create_data;
	 *         confimFlg
	 */
	@RequestMapping(value = "zzWaitReceiveApplyListSearch")
	public String zzWaitReceiveApplyListSearch(EnterpriseWorkers enterpriseWorkers, String rbtvalue, HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		// 根据enterpriseWorkers和person_record、sys_user表查询已审核申请列表
		User user = UserUtils.getUser();
		if ("1".equals(user.getOffice().getId())) {
			// enterpriseWorkers.setPid(user.getOffice().getId());//将office_id暂时存放到enterprise的pid中进行查询
			//enterpriseWorkers.setDeclareStatus("10");
			enterpriseWorkers.setBatchStatus("10");
			Page<EnterpriseWorkers> page =null;
			if ("1".equals(rbtvalue))
			{
				page = enterpriseWorkersService.findPageList03(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
			}
			else if ("2".equals(rbtvalue))
			{
				page = enterpriseWorkersService.findPageList032(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				}
			else
			{
				page = enterpriseWorkersService.findPageList031(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
			}
			model.addAttribute("page", page);
			model.addAttribute("rbtnvalue", rbtvalue);
		} else {
			addMessage(redirectAttributes, "查询失败，您不具有该权限");
			return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/zzWaitReceiveApplyList";
		}
		return "modules/enterprise/zzWaitReceiveApplyList";
	}

	/**
	 * 中咨协会批量接收地方协会申请单
	 * 
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "passWaitReceiveApplyList")
	public String passWaitReceiveApplyList(EnterpriseWorkers enterpriseWorkers,String[] personRecordId,String rbtvalue,String batchNo, HttpServletResponse response,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		//根据条件查询出待批量接受的申请单
		enterpriseWorkers.setBatchStatus("10");
		Page<EnterpriseWorkers> page = null;
			
		if ("1".equals(rbtvalue))
		{
			page = enterpriseWorkersService.findPageListByAllPass1(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
		}
		else if ("2".equals(rbtvalue))
		{
			page = enterpriseWorkersService.findPageListByAllPass2(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
			}
		else
		{
			page = enterpriseWorkersService.findPageListByAllPass(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
		}
		List<EnterpriseWorkers> list = page.getList();
		// 切割personRecordId
		if (list != null && list.size()>0) {
			PersonRecord personRecord = new PersonRecord();
			for (int i = 0; i < list.size(); i++) {
				// 修改person_record的person_status 状态为3
				personRecord.setId(list.get(i).getPersonRecordId());
				personRecord.setDeclareStatus("11");
				personRecord.setBatchStatus("11");
				personRecord.setBatchNo(batchNo);
				this.personRecordService.updateBatchStatus(personRecord);
				this.personRecordService.updateDeclareStatusBatch(personRecord);
			}
		} else {
			return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/zzWaitReceiveApplyList";
		}
		addMessage(redirectAttributes, "已接收该用户申请");
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/zzWaitReceiveApplyListSearch?rbtvalue="+rbtvalue;
	}

	/**
	 * 中咨协会单个接收地方协会申请单
	 * 
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "passWaitReceiveApply")
	public String passWaitReceiveApply(EnterpriseWorkers enterpriseWorkers,String batchNo,String recordid, RedirectAttributes redirectAttributes) {
		// 修改person_record的person_status 状态为11
		PersonRecord personRecord = new PersonRecord();
		personRecord.setId(recordid);
		personRecord.setDeclareStatus("11");
		personRecord.setBatchStatus("11");
		personRecord.setBatchNo(batchNo);
		this.personRecordService.updateBatchStatus(personRecord);
		this.personRecordService.updateDeclareStatusBatch(personRecord);
		addMessage(redirectAttributes, "已接收该用户申请");
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/zzWaitReceiveApplyListSearch";
	}

	
	/**
	 * 根据数据分配专家，初始化页面时，加载未分配和已分配的数据
	 * 未分配状态：11 	已分配状态12
	 * localdistributeExpertList
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "zzapplyDataDistributeExpert")
	public String zzapplyDataDistributeExpert(EnterpriseWorkers enterpriseWorkers,String radioChoose, RedirectAttributes redirectAttributes,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		String officeId = enterpriseWorkers.getOfficeId();
		User user = UserUtils.getUser();
		if (user.getOffice() == null) {
			return "modules/enterprise/zzapplyDataDistributeExpert";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				if(radioChoose != null){
					//enterpriseWorkers.setDeclareStatus(radioChoose);
					enterpriseWorkers.setBatchStatus(radioChoose);
					
				}
				if ("1".equals(enterpriseWorkers.getOfficeId()))
				{
					enterpriseWorkers.setOfficeId("");
				}
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.zzDistributeExpertList(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
				model.addAttribute("radioChoose", radioChoose);
				model.addAttribute("officeId", officeId);
			} else {
				addMessage(redirectAttributes, "只有中咨协会管理员可以查看该数据");
			}
		}
		return "modules/enterprise/zzdistributeExpertList";
	}
	
	
	/**
	 * 中咨协会咨询师列表
	 * @param enterpriseWorkers
	 * @param model
	 * @return
	 */
	@RequestMapping(value="zzCounselorList")
	public String zzCounselorList(EnterpriseWorkers enterpriseWorkers,String recordId,Model model,HttpServletResponse response,HttpServletRequest request){
		
		enterpriseWorkers.setIsRegister("");
		Page<EnterpriseWorkers> page = this.enterpriseWorkersService.findCounselorList(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
		model.addAttribute("page", page);
		return "modules/enterprise/zzCounselorList";
	}
	
	// --------------------------------地方协会模块---------------------------------------------------
	/**
	 * 地方协会待接收单位申请列表
	 * 
	 * @param model
	 * @return name,sex,certificatesType,certificatesNum,isValid,create_data;
	 *         confimFlg
	 */
	@RequestMapping(value = "localWaitReceiveApplyList")
	public String localWaitReceiveApplyList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/enterprise/localWaitReceiveApplyList";
	}

	/**
	 * 地方协会待接收单位申请列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "localWaitReceiveApplyListSearch")
	public String localWaitReceiveApplyListSearch(EnterpriseWorkers enterpriseWorkers,String rbtvalue, HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		// 根据enterpriseWorkers和person_record、sys_user表查询已审核申请列表
		User user = UserUtils.getUser();
		if (user.getOffice() == null) {// 判断office是否为空
			return "modules/enterprise/alreadyAuditApplyList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				enterpriseWorkers.setBatchStatus("5");
				Page<EnterpriseWorkers> page =null;
				if ("1".equals(rbtvalue)){
					page = enterpriseWorkersService.findPageList03(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				}else if ("2".equals(rbtvalue)){
					page = enterpriseWorkersService.findPageList032(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				}else{
					page = enterpriseWorkersService.findPageList031(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				}
				model.addAttribute("page", page);
				if (page.getList().size() == 0) {
					addMessage(redirectAttributes, "无接收信息");
				}
				model.addAttribute("rbtnvalue", rbtvalue);
			} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				enterpriseWorkers.setOfficeId(user.getOffice().getId());// 将office_id暂时存放到enterprise的pid中进行查询
				enterpriseWorkers.setBatchStatus("5");
				Page<EnterpriseWorkers> page =null;
				if ("1".equals(rbtvalue)){
					page = enterpriseWorkersService.findPageList03(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				}else if ("2".equals(rbtvalue)){
					page = enterpriseWorkersService.findPageList032(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				}else{
					page = enterpriseWorkersService.findPageList031(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				}
				model.addAttribute("page", page);
				if (page.getList().size() == 0) {
					addMessage(redirectAttributes, "无接收信息");
				}
				model.addAttribute("rbtnvalue", rbtvalue);
			}
		}
		return "modules/enterprise/localWaitReceiveApplyList";
	}

	/**
	 * 地方协会批量接受单位申请单
	 * 
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "localPassWaitReceiveApplyList")
	public String localPassWaitReceiveApplyList(String[] personRecordId, String rbtvalue,RedirectAttributes redirectAttributes) {
		// 切割personRecordId
		if (personRecordId.length>0) {
			PersonRecord personRecord = new PersonRecord();
			for (int i = 0; i < personRecordId.length; i++) {
				// 修改person_record的person_status 状态为6
				personRecord.setId(personRecordId[i]);
				personRecord.setDeclareStatus("6");
				personRecord.setBatchStatus("6");
				this.personRecordService.updateBatchStatus(personRecord);
				this.personRecordService.updateDeclareStatusNew(personRecord);
			}
		} else {
			return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/waitReceiveList";
		}
		addMessage(redirectAttributes, "已接收该用户申请");
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/localWaitReceiveApplyListSearch?rbtvalue="+rbtvalue;
	}

	/**
	 * 地方协会单个接收单位申请单
	 * 
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "localPassWaitReceiveApply")
	public String localPassWaitReceiveApply(EnterpriseWorkers enterpriseWorkers,
			RedirectAttributes redirectAttributes) {
		// 修改person_record的person_status 状态为11
		PersonRecord personRecord = new PersonRecord();
		personRecord.setId(enterpriseWorkers.getPersonRecordId());
		personRecord.setDeclareStatus("6");
		personRecord.setBatchStatus("6");
		this.personRecordService.updateBatchStatus(personRecord);
		this.personRecordService.updateDeclareStatusNew(personRecord);
		addMessage(redirectAttributes, "已接收该用户申请");
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/localWaitReceiveApplyListSearch";
	}
	
	/**
	 * 地方协会待接收列表中的退回按钮
	 * 根据Id将所填信息显示到弹出框
	 * @param personRecord
	 * @param model
	 * @return
	 */
	@RequestMapping(value="ReceiveReturn")
	public String ReceiveReturn(PersonRecord personRecord,String workerId,String batchId,Model model){
		List<PersonRecord> personRecordList = this.personRecordService.findById(personRecord);
		model.addAttribute("returnReason", personRecordList.get(0).getLocalReceiveReturnReason());
		model.addAttribute("id", personRecord.getId());
		model.addAttribute("workerId", workerId);
		model.addAttribute("batchId", batchId);
		return "modules/enterprise/receiveReturn";
	}
	
	/**
	 * 根据id保存或更新returnReason信息
	 * @param personRecord
	 * @param model
	 * @return
	 */
	@RequestMapping(value="savalocalReceiveReturnReason")
	public String savalocalReceiveReturnReason(PersonRecord personRecord,Model model){
		String flag = this.personRecordService.updateLocalReceiveReturnReason(personRecord);
		model.addAttribute("flag", flag);
		model.addAttribute("id", personRecord.getId());
		model.addAttribute("batchId", personRecord.getBatchId());
		hgBackMessage(personRecord.getId(),"1");
		return "modules/enterprise/receiveReturn";
	}
	//发送短信
    private void hgBackMessage(String recordid,String type)
    {
    	try
    	{
    		// 创建申请单对象
    		String[] args = new String[2];
    		EnterpriseWorkers enterpriseworkers=  this.enterpriseWorkersService.getMobileByRecordId(recordid);
    		if (enterpriseworkers.getMobile() != null) {
    			String decalertype;
    			
    			if (enterpriseworkers.getDecalerType().equals("0"))
    			{
    				decalertype="注销登记";
    			}
    			else if(enterpriseworkers.getDecalerType().equals("1")){
    				decalertype="初始登记";
    			}
    			else if(enterpriseworkers.getDecalerType().equals("2")){
    				decalertype="变更执业单位";
    			}
    			else if(enterpriseworkers.getDecalerType().equals("3")){
    				decalertype="变更专业";
    			}
    			else if(enterpriseworkers.getDecalerType().equals("4")){
    				decalertype="继续登记";
    			}
    			else
    			{
    				decalertype="未知类型";
    			}
    			args[0] = "【中国工程咨询协会】提示：您单位下的咨询工程师"+ enterpriseworkers.getName() +"的"+ decalertype +"登记事项被退回，请登录系统查看。";
    			args[1] = enterpriseworkers.getMobile();
    			SendMessge.maina(args);
    		} else {

    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		
    }
	/**
	 * 退回
	 */
	@ResponseBody
	@RequestMapping(value="LocalReceiveReturnReport")
	public void LocalReceiveReturnReport(EnterpriseWorkers enterpriseWorkers,
			RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response){
		int flag = 200;
		// 修改person_record的person_status 状态为1
		PersonRecord personRecord = new PersonRecord();
		personRecord.setBatchId(enterpriseWorkers.getBatchId());
		personRecord.setBatchStatus("1");
		personRecord.setDeclareStatus("1");
		//根据batchId查询该批次的所有信息
		List<PersonRecord> findByBatchId = this.personRecordService.findByBatchId(personRecord);
		for (int i = 0; i < findByBatchId.size(); i++) {
			personRecord.setId(findByBatchId.get(i).getId());
			this.personRecordService.updateBatchStatus(personRecord);
			this.personRecordService.updateDeclareStatusNew(personRecord);
		}
		try {
	        //设置页面不缓存
	        response.setContentType("application/json");
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
	        PrintWriter out= null;
	        out = response.getWriter();
	        out.print(flag);
	        out.flush();
	        out.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * 地方协会合规审查
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "localComplianceList")
	public String localComplianceList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和sys_user表查询个人用户列表
		User user = UserUtils.getUser();
		// 根据user的身份证号和name到enterprise_workers表中查询pid
		if (user.getOffice() == null) {
			return "modules/enterprise/localAcceptAndReportList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				//enterpriseWorkers.setDeclareStatus("6");
				enterpriseWorkers.setBatchStatus("6");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList005(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				enterpriseWorkers.setPid(user.getOffice().getId());// 将office_id暂时存放到enterprise的pid中进行查询
				//enterpriseWorkers.setDeclareStatus("6");
				enterpriseWorkers.setBatchStatus("6");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList005(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}
		}
		return "modules/enterprise/localComplianceList";
	}

	/**
	 * 地方协会待审核列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "localWaitAuditList")
	public String localAcceptAndReportList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和sys_user表查询个人用户列表
		User user = UserUtils.getUser();
		// 根据user的身份证号和name到enterprise_workers表中查询pid
		if (user.getOffice() == null) {
			return "modules/enterprise/localWaitAuditList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				enterpriseWorkers.setBatchStatus("7");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList05(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			} else if(!"1".equals(user.getOffice().getId()) && "5".equals(user.getUserModel())){//如果是专家，只允许看自己要审核的申请单
				enterpriseWorkers.setPid(user.getOffice().getId());// 将office_id暂时存放到enterprise的pid中进行查询
				enterpriseWorkers.setUserId(user.getId());
				enterpriseWorkers.setBatchStatus("7");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList05(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}else{// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				enterpriseWorkers.setPid(user.getOffice().getId());// 将office_id暂时存放到enterprise的pid中进行查询
				enterpriseWorkers.setBatchStatus("7");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList05(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}
		}
		return "modules/enterprise/localWaitAuditList";
	}
	
	
	/**
	 * 地方协会已审核列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "localAlreadyAuditList")
	public String localAlreadyAuditList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和sys_user表查询个人用户列表
		User user = UserUtils.getUser();
		// 根据user的身份证号和name到enterprise_workers表中查询pid
		if (user.getOffice() == null) {
			return "modules/enterprise/localAlreadyAuditList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList06(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			} else if(!"1".equals(user.getOffice().getId()) && "5".equals(user.getUserModel())){//如果是专家，只允许看自己要审核的申请单
				enterpriseWorkers.setPid(user.getOffice().getId());// 将office_id暂时存放到enterprise的pid中进行查询
				enterpriseWorkers.setUserId(user.getId());
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList06(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}else{// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				enterpriseWorkers.setPid(user.getOffice().getId());// 将office_id暂时存放到enterprise的pid中进行查询
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList06(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}
		}
		return "modules/enterprise/localAlreadyAuditList";
	}

	/**
	 * 地方协会已上报中咨协会的申请列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "localReportApplyList")
	public String localAlreadyReportList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和sys_user表查询个人用户列表
		User user = UserUtils.getUser();
		// 根据user的身份证号和name到enterprise_workers表中查询pid
		if (user.getOffice() == null) {
			return "modules/enterprise/localAcceptAndReportList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				//enterpriseWorkers.setDeclareStatus("9");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList04(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				enterpriseWorkers.setOfficeId(user.getOffice().getId());// 将office_id暂时存放到enterprise的pid中进行查询
				//enterpriseWorkers.setDeclareStatus("9");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList04(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}
		}
		return "modules/enterprise/localAlreadyReportList";
	}
	
	/**
	 * 地方协会待上报中咨协会申请列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "localWaitReportZZList")
	public String localWaitReportZZList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/enterprise/localWaitReportZZList";
	}
	
	
	/**
	 * 地方协会待上报中咨协会申请列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "localWaitReportZZListSearch")
	public String localWaitReportZZListSearch(EnterpriseWorkers enterpriseWorkers,RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和sys_user表查询个人用户列表
		User user = UserUtils.getUser();
		// 根据user的身份证号和name到enterprise_workers表中查询pid
		if (user.getOffice() == null) {
			return "modules/enterprise/localAcceptAndReportList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				enterpriseWorkers.setBatchStatus("9");//update by gaoyongjian 修改为可查询状态是8/9的数据
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList0051(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
//				Page<EnterpriseWorkers> page = enterpriseWorkersService
//						.findPageList005(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				if(page.getList().size()>0){
					model.addAttribute("page", page);
				}else{
					addMessage(redirectAttributes, "无上报信息");
				}
			} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				enterpriseWorkers.setPid(user.getOffice().getId());// 将office_id暂时存放到enterprise的pid中进行查询
				enterpriseWorkers.setBatchStatus("9");//update by gaoyongjian 修改为可查询状态是8/9的数据
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findPageList0051(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
//				Page<EnterpriseWorkers> page = enterpriseWorkersService
//						.findPageList005(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
			}
		}
		return "modules/enterprise/localWaitReportZZList";
	}
	
	/**
	 *地方协会申请单上报
	 * 
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "localPassWaitReportZZApply")
	public String localPassWaitReportZZApply(EnterpriseWorkers enterpriseWorkers,
			RedirectAttributes redirectAttributes) {
		// 根据id修改person_record的person_status 状态为10
		PersonRecord personRecord = new PersonRecord();
		personRecord.setId(enterpriseWorkers.getPersonRecordId());
		personRecord.setBatchStatus("10");
		personRecord.setDeclareStatus("10");
		this.personRecordService.updateDeclareStatusNew(personRecord);
		//根据id查询出该批数据的batch_id
		List<PersonRecord> findById = this.personRecordService.findById(personRecord);//获取batch_id
		personRecord.setBatchId(findById.get(0).getBatchId());
		
		//修改该人员工作简历为不可编辑状态
        String personId = findById.get(0).getPersonId();
        //根据personId 查找到所有的jobKnow   add by gaoyongjian 20190110  预审上报终审时，将人员的工作经历设置为不可编辑
        JobKnowledge jobKnowledge = new JobKnowledge();
        jobKnowledge.setPersonId(personId);
        List<JobKnowledge> list = jobKnowledgeService.findList(jobKnowledge);
        for(JobKnowledge j : list){
            j.setIsChange("0");
            jobKnowledgeService.save(j);
        }
        
		//根据batch_id查询declarestatus状态
		List<PersonRecord> findByBatchId = this.personRecordService.findByBatchId(personRecord);
		int index = 0;
		for (int i = 0; i < findByBatchId.size(); i++) {
			if("10".equals(findByBatchId.get(i).getDeclareStatus())){
				index +=1;
			}
		}
		if(index == findByBatchId.size()){
			this.personRecordService.updateBatchStatus(personRecord);
			addMessage(redirectAttributes, "已上报该用户申请");
		}else{
			addMessage(redirectAttributes, "已上报该用户申请");
		}
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/localWaitReportZZListSearch";
	}
	
	/**
	 * 根据Id将所填信息显示到弹出框
	 * @param personRecord
	 * @param model
	 * @return
	 */
	@RequestMapping(value="returnReportWindow")
	public String returnReportWindow(PersonRecord personRecord,String workerId,Model model){
		List<PersonRecord> personRecordList = this.personRecordService.findById(personRecord);
		model.addAttribute("returnReason", personRecordList.get(0).getReturnReason());
		model.addAttribute("id", personRecord.getId());
		//model.addAttribute("workerId", workerId);
		return "modules/enterprise/returnReportWindow";
	}
	
	/**
	 * 根据id保存或更新returnReason信息
	 * @param personRecord
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value="savaReturnReason")
	public String savaReturnSuggestion(PersonRecord personRecord,Model model){
		String flag = this.personRecordService.updateReturnReason(personRecord);
		model.addAttribute("id",personRecord.getId());
		model.addAttribute("flag", flag);
		return "modules/enterprise/returnReportWindow";
	}*/
	
	/**
	 * 地方协会退回上报单位申请单
	 */
	@RequestMapping(value="LocalReturnReport")
	public String LocalReturnReport(PersonRecord personRecord,
			RedirectAttributes redirectAttributes){
		//保存或更新退回原因
		String flag = this.personRecordService.updateReturnReason(personRecord);
		if("1".equals(flag)){//保存或更新退回原因执行成功
			//根据id查询batchId
			List<PersonRecord> findById = this.personRecordService.findById(personRecord);
			//根据batchid，查询出当前批次所有申请信息
			List<PersonRecord> findByBatchId = this.personRecordService.findByBatchId(findById.get(0));
			//修改批次中所有申请单的状态
			for (int i = 0; i < findByBatchId.size(); i++) {
				findByBatchId.get(i).setBatchStatus("1");
				findByBatchId.get(i).setDeclareStatus("1");
				findByBatchId.get(i).setReturnType("2");
				this.personRecordService.updateDeclareStatusNew(findByBatchId.get(i));
				this.personRecordService.updateBatchStatus(findByBatchId.get(i));
				this.personRecordService.updateReturnType(findByBatchId.get(i));
			}
			addMessage(redirectAttributes, "已退回该用户申请");
			this.hgBackMessage(personRecord.getId(), "1");
		}else{//保存或更新退回原因执行失败
			addMessage(redirectAttributes, "退回用户申请失败");
		}
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/localWaitReportZZListSearch";
	}
	
	/**
	 * 地方协会申请单批量上报
	 * 
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "localPassWaitReportZZApplyList")
	public String localPassWaitReportZZApplyList(String[] personRecordId, RedirectAttributes redirectAttributes) {
		// 切割personRecordId
		if (personRecordId.length > 0) {
			PersonRecord personRecord = new PersonRecord();
			for (int i = 0; i < personRecordId.length; i++) {
				// 根据id修改person_record的person_status 状态为10
				personRecord.setId(personRecordId[i]);
				personRecord.setBatchStatus("10");
				personRecord.setDeclareStatus("10");
				this.personRecordService.updateDeclareStatusNew(personRecord);
				
				// 根据id查询出该批数据的batch_id
				List<PersonRecord> findById = this.personRecordService.findById(personRecord);// 获取batch_id
				personRecord.setBatchId(findById.get(0).getBatchId());
				
				//修改该人员工作简历为不可编辑状态
		        String personId = findById.get(0).getPersonId();
		        //根据personId 查找到所有的jobKnow   add by gaoyongjian 20190110  预审上报终审时，将人员的工作经历设置为不可编辑
		        JobKnowledge jobKnowledge = new JobKnowledge();
		        jobKnowledge.setPersonId(personId);
		        List<JobKnowledge> list = jobKnowledgeService.findList(jobKnowledge);
		        for(JobKnowledge j : list){
		            j.setIsChange("0");
		            jobKnowledgeService.save(j);
		        }
		        
				// 根据batch_id查询declarestatus状态
				List<PersonRecord> findByBatchId = this.personRecordService.findByBatchId(personRecord);
				int index = 0;
				for (int j = 0; j < findByBatchId.size(); j++) {
					if ("10".equals(findByBatchId.get(j).getDeclareStatus())) {
						index += 1;
					}
				}
				if (index == findByBatchId.size()) {
					this.personRecordService.updateBatchStatus(personRecord);
				}
			}
		} else {
			return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/localWaitReportZZListSearch";
		}
		addMessage(redirectAttributes, "上报成功");
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/localWaitReportZZListSearch";
	}
	
	
	/**
	 * 地方协会
	 * 根据数据分配专家，初始化页面时，加载未分配和已分配的数据
	 * 未分配状态：8 	已分配状态 7
	 * localdistributeExpertList
	 * @param enterpriseWorkers
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "applyDataDistributeExpert")
	public String applyDataDistributeExpert(EnterpriseWorkers enterpriseWorkers,String radioChoose, RedirectAttributes redirectAttributes,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		if (user.getOffice() == null) {
			return "modules/enterprise/distributeExpertList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				addMessage(redirectAttributes, "只有地方协会管理员可以查看该数据");
				/*if(radioChoose != null){
					//enterpriseWorkers.setDeclareStatus(radioChoose);
					enterpriseWorkers.setBatchStatus(radioChoose);
				}
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.localDistributeExpertList(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
				model.addAttribute("radioChoose", radioChoose);*/
			} else {
				// 根据officeId获取该地区的已分配和未分配的申请单
				enterpriseWorkers.setPid(user.getOffice().getId());
				if(radioChoose != null){
					//enterpriseWorkers.setDeclareStatus(radioChoose);
					enterpriseWorkers.setBatchStatus(radioChoose);
				}
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.localDistributeExpertList(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
				model.addAttribute("radioChoose", radioChoose);
			}
		}
		return "modules/enterprise/distributeExpertList";
	}
	
	
	/**
	 * 专家分配（本注释仅供参考,已准确按照需求编写）
	 * 
	 * 分配条件：
	 * 	1.根据查询条件进行分配（申请类型必选，选择“未分配”条件的时候才浮现“自动分配”按钮；只要申请类型是“初始登记”和“变更申请”时才可以选择专业条件）	
	 * 	2.分配时根据前台传过来的查询条件分配，主要分配两种大情况：
	 * 		2.1一种是查询条件有专业的，这种情况说明申请类型必为“初始登记”和“变更申请”，
	 * 		2.2一种是查询条件没有专业的
	 * 	3.如果查询条件专业为空，这时候下面又分为两种情况进行分配：
	 * 		3.1申请类型为“初始登记”和“变更申请”，关联变更履历表，进行专业分配
	 * 			3.1.1如果变更履历表中只有一条关联信息，则将专家分配到专家1，如果有两条，则将专家分配到专家2
	 * 		3.2申请类型不为“初始登记”和“变更申请”，直接分配不用关联变更履历表
	 * 	4.如果查询条件专业不为空，这种情况说明申请类型必为“初始登记”和“变更申请”
	 * 		4.1关联变更履历表，如果变更履历表中只有一条关联信息，则将专家分配到专家1，如果有两条，则将专家分配到专家2
	 * 		
	 */
	@RequestMapping(value = "localDistributeExpert")
	public String localDataDistributeExpert(EnterpriseWorkers enterpriseWorkers, RedirectAttributes redirectAttributes,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		String flag = enterpriseWorkers.getFlag();
		User user = UserUtils.getUser();
		if (user.getOffice() == null) {//判断当前用户office是否为空，如果为空不允许分配，直接返回前台页面
			return jspSkip(flag);
		} else {
				//1.查询出所有专家user_model=5
				User user1= new User();
				user1.setOfficeId(user.getOffice().getId());
				user1.setUserModel("5");
				List<User> expertList = this.systemService.findExpertByUserModel(user1);//专家列表
				int index = 0 ;//专家列表的索引值
				
				if (enterpriseWorkers.getSpecialty() == null || "".equals(enterpriseWorkers.getSpecialty())) {// 判断专业是否为空，如果专业为空，说明申请类型不是初始登记和变更专业或者是这两种但没有选择专业类型
					// 判断申请类型是否是初始登记和变更专业
					if ("1".equals(enterpriseWorkers.getDeclareType()) || "3".equals(enterpriseWorkers.getDeclareType())) {// 如果是初始登记和变更专业，根据条件查询出待分配申请单，并且关联变更履历表，按照专业进行分配
						//将查询条件添加到enterpriseWorkers实体中
						if("1".equals(user.getOffice().getId())){//判断该用户是否中咨管理员，中咨管理员分配时修改declareStatus状态为11，否为6
							enterpriseWorkers.setBatchStatus("11");
						}else{
							enterpriseWorkers.setBatchStatus("8");
							enterpriseWorkers.setOfficeId(user.getOffice().getId());
						}
						//待分配申请单列表
						List<EnterpriseWorkers> personRecordList = this.enterpriseWorkersService.localdistributeExpertList1(enterpriseWorkers);
						if (personRecordList != null || personRecordList.size() > 0) {
							label01: for (int i = 0; i < personRecordList.size(); i++) {// 遍历申请单列表
								if (expertList == null || expertList.size() == 0) {// 专家不存在
									addMessage(redirectAttributes, "贵区没有可供分配的专家，请先添加专家");
									return jspSkip(flag);
								} else {// 存在专家
									if (index < expertList.size()) {// 判断当前专家索引值是否大于专家个数
										if (i > 0) {
											if (personRecordList.get(i).getId()
													.equals(personRecordList.get(i - 1).getId())) {// 判断当前申请单查询对象id是否跟上一个相同，如果相同，说明一个申请单有两个变更履历表,将该专家update到预审专家2中
												String[] userSpecialty = expertList.get(index).getSpecialtyType()
														.split(",");// 专家专业
												label02: for (int j = 0; j < userSpecialty.length; j++) {//遍历专家专业
													if (personRecordList.get(i).getNewValue()
															.equals(userSpecialty[j])) {// 判断专家的专业是否跟newvalue值相似，如果相似则分配，不相识不分配
														PersonRecord personRecord = new PersonRecord();
														personRecord.setId(personRecordList.get(i).getPersonRecordId());
														if("1".equals(user.getOffice().getId())){//如果是中咨分配
															personRecord.setBatchStatus("12");
															personRecord.setSecondZexpertId(expertList.get(index).getId());
															//----------------------------判断申请单不能由同一专家审核开始--------------------------------------------
															//判断：一个申请单不允许同一个专家审核
															//根据申请单person_record的id查询该申请单的专家1信息
															List<PersonRecord> findById = this.personRecordService.findById(personRecord);
															if(findById != null && findById.size()>0){
																if(findById.get(0).getFirstZexpertId() != null){
																	if(expertList.get(index).getId().equals(findById.get(0).getFirstZexpertId())){//判断已分配的专家1与待分配的专家2是否相同
																		//如果相同则不分配，跳转到下一个专家
																		index ++;
																		if (index < expertList.size()) {
																			userSpecialty = expertList.get(index).getSpecialtyType()
																					.split(",");
																			//for重新开始循环遍历下一个专家的专业
																			j=-1;
																			continue label02;
																		} else {
																			index = 0;
																			continue label01;
																		}
																	}
																}
															}
															//----------------------------判断申请单不能由同一专家审核完毕--------------------------------------------
															this.personRecordService.updateSecondZexpertId(personRecord);
															//调用addCheck方法进行数据推送
															this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"4");
															//this.saveExpertService.saveExpert(personRecordList.get(i).getPersonRecordId());
														}else{//地方协会专家分配
															personRecord.setSecondCexpertId(expertList.get(index).getId());
															personRecord.setBatchStatus("7");
															//----------------------------判断申请单不能由同一专家审核开始--------------------------------------------
															//判断：一个申请单不允许同一个专家审核
															//根据申请单person_record的id查询该申请单的专家1信息
															List<PersonRecord> findById = this.personRecordService.findById(personRecord);
															if(findById != null && findById.size()>0){
																if(findById.get(0).getFirstCexpertId() != null){
																	if(expertList.get(index).getId().equals(findById.get(0).getFirstCexpertId())){//判断专家1与待分配的专家2是否相同
																		//如果相同则不分配，跳转到下一个专家
																		index ++;
																		if (index < expertList.size()) {
																			userSpecialty = expertList.get(index).getSpecialtyType()
																					.split(",");
																			//for重新开始循环遍历下一个专家的专业
																			j=-1;
																			continue label02;
																		} else {
																			index = 0;
																			continue label01;
																		}
																	}
																}else{
																	
																}
															}//不同则进行专家分配
															//----------------------------判断申请单不能由同一专家审核完毕--------------------------------------------
															this.personRecordService.updateSecondCexpertId(personRecord);
															//调用addCheck方法进行数据推送
															//this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"3");
														}
														index = 0;//?此处index应该++
														//index++;
														Collections.reverse(expertList);
														continue label01;
													} else {//专家的当前专业跟申请单变更专业不同
														if(j==userSpecialty.length-1)
														{
															index++;
															if (index < expertList.size()) {
																userSpecialty = expertList.get(index).getSpecialtyType()
																		.split(",");
																//for重新开始循环遍历下一个专家的专业
																j=-1;
																continue label02;
															} else {
																index = 0;
																continue label01;
															}
														}
													}
												}
											} else {//该申请单在变更履历表中只有一条信息，将专家分配到预审专家1
												String[] userSpecialty = expertList.get(index).getSpecialtyType()
														.split(",");
												label02: for (int j = 0; j < userSpecialty.length; j++) {
													if (personRecordList.get(i).getNewValue()
															.equals(userSpecialty[j])) {
														// enterpriseWorkers.setFirstCexpertId(expertList.get(index).getId());//
														PersonRecord personRecord = new PersonRecord();
														personRecord.setId(personRecordList.get(i).getPersonRecordId());
														if("1".equals(user.getOffice().getId())){
															personRecord.setBatchStatus("12");
															personRecord.setFirstZexpertId(expertList.get(index).getId());
															this.personRecordService.updateFirstZexpertId(personRecord);
															//调用addCheck方法进行数据推送
															this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"4");
															//this.saveExpertService.saveExpert(personRecordList.get(i).getPersonRecordId());
														}else{
															personRecord.setFirstCexpertId(expertList.get(index).getId());
															personRecord.setBatchStatus("7");
															this.personRecordService.updateFirstCexpertId(personRecord);
															//调用addCheck方法进行数据推送
															//this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"3");
														}
														index = 0;
														//index++;
														Collections.reverse(expertList);
														continue label01;
													} else {
														/*判断是否遍历专家的所有专业，
														 * 如果当前专业索引值等于userSpecialty.length-1,
														 * 		条件true：index++，查询下一个专家的专业是否与当前申请单变更专业相同，
														 * 		条件false：j++，继续遍历当前专家专业
														 */
														if(j==userSpecialty.length-1)	
														{
															index++;
															if (index < expertList.size()) {
																userSpecialty = expertList.get(index).getSpecialtyType()
																		.split(",");
																//for重新开始循环遍历下一个专家的专业
																j=-1;
																continue label02;
															} else {
																index = 0;
																continue label01;
															}
														}
													}
												}
											}
										} else {//第一条申请单数据，分配的专家id直接插入first_Cexpert_id中
											String[] userSpecialty = expertList.get(index).getSpecialtyType()
													.split(",");
											label02: for (int j = 0; j < userSpecialty.length; j++) {
												if (personRecordList.get(i).getNewValue().equals(userSpecialty[j])) {
													PersonRecord personRecord = new PersonRecord();
													personRecord.setId(personRecordList.get(i).getPersonRecordId());
													if("1".equals(user.getOffice().getId())){
														personRecord.setBatchStatus("12");
														personRecord.setFirstZexpertId(expertList.get(index).getId());
														this.personRecordService.updateFirstZexpertId(personRecord);
														//调用addCheck方法进行数据推送
														this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"4");
														//this.saveExpertService.saveExpert(personRecordList.get(i).getPersonRecordId());
													}else{
														personRecord.setFirstCexpertId(expertList.get(index).getId());
														personRecord.setBatchStatus("7");
														this.personRecordService.updateFirstCexpertId(personRecord);
														//调用addCheck方法进行数据推送
														//this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"3");
													}
													index = 0;
													//index++;
													Collections.reverse(expertList);
													continue label01;
												} else {
													if(j==userSpecialty.length-1)	
													{
														index++;
														if (index < expertList.size()) {
															userSpecialty = expertList.get(index).getSpecialtyType()
																	.split(",");
															//for重新开始循环遍历下一个专家的专业
															j=-1;
															continue label02;
														} else {
															index = 0;
															continue label01;
														}
													}
												}
											}
										}
									} else {//第一轮专家遍历分配结束，进入第二轮专家遍历分配
										index = 0;
										if (personRecordList.get(i).getId()
												.equals(personRecordList.get(i - 1).getId())) {
											String[] userSpecialty = expertList.get(index).getSpecialtyType()
													.split(",");
											label02: for (int j = 0; j < userSpecialty.length; j++) {
												if (personRecordList.get(i).getNewValue().equals(userSpecialty[j])) {
													PersonRecord personRecord = new PersonRecord();
													personRecord.setId(personRecordList.get(i).getPersonRecordId());
													if("1".equals(user.getOffice().getId())){//如果是中咨分配
														personRecord.setBatchStatus("12");
														personRecord.setSecondZexpertId(expertList.get(index).getId());
														//----------------------------判断申请单不能由同一专家审核开始--------------------------------------------
														//判断：一个申请单不允许同一个专家审核
														//根据申请单person_record的id查询该申请单的专家1信息
														List<PersonRecord> findById = this.personRecordService.findById(personRecord);
														if(findById != null && findById.size()>0){
															if(findById.get(0).getFirstZexpertId() != null){
																if(expertList.get(index).getId().equals(findById.get(0).getFirstZexpertId())){//判断专家1与待分配的专家2是否相同
																	//如果相同则不分配，跳转到下一个专家
																	index ++;
																	if (index < expertList.size()) {
																		userSpecialty = expertList.get(index).getSpecialtyType()
																				.split(",");
																		//for重新开始循环遍历下一个专家的专业
																		j=-1;
																		continue label02;
																	} else {
																		index = 0;
																		continue label01;
																	}
																}
															}
														}//不同则进行专家分配
														//----------------------------判断申请单不能由同一专家审核完毕--------------------------------------------
														this.personRecordService.updateSecondZexpertId(personRecord);
														//调用addCheck方法进行数据推送
														this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"4");
														//this.saveExpertService.saveExpert(personRecordList.get(i).getPersonRecordId());
													}else{//地方分配
														personRecord.setSecondCexpertId(expertList.get(index).getId());// 想似则分配
														personRecord.setBatchStatus("7");
														//----------------------------判断申请单不能由同一专家审核开始--------------------------------------------
														//判断：一个申请单不允许同一个专家审核
														//根据申请单person_record的id查询该申请单的专家1信息
														List<PersonRecord> findById = this.personRecordService.findById(personRecord);
														if(findById != null && findById.size()>0){
															if(findById.get(0).getFirstCexpertId() != null){
																if(expertList.get(index).getId().equals(findById.get(0).getFirstCexpertId())){//判断专家1与待分配的专家2是否相同
																	//如果相同则不分配，跳转到下一个专家
																	index ++;
																	if (index < expertList.size()) {
																		userSpecialty = expertList.get(index).getSpecialtyType()
																				.split(",");
																		//for重新开始循环遍历下一个专家的专业
																		j=-1;
																		continue label02;
																	} else {
																		index = 0;
																		continue label01;
																	}
																}
															}
														}//不同则进行专家分配
														//----------------------------判断申请单不能由同一专家审核完毕--------------------------------------------
														this.personRecordService.updateSecondCexpertId(personRecord);
														//调用addCheck方法进行数据推送
														//this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"3");
													}
													index = 0;
													//index++;
													Collections.reverse(expertList);
													continue label01;
												} else {
													if(j==userSpecialty.length-1)	
													{
														index++;
														if (index < expertList.size()) {
															userSpecialty = expertList.get(index).getSpecialtyType()
																	.split(",");
															//for重新开始循环遍历下一个专家的专业
															j=-1;
															continue label02;
														} else {
															index = 0;
															continue label01;
														}
													}
												}
											}
										} else {
											String[] userSpecialty = expertList.get(index).getSpecialtyType()
													.split(",");
											label02: for (int j = 0; j < userSpecialty.length; j++) {
												if (personRecordList.get(i).getNewValue().equals(userSpecialty[j])) {
													PersonRecord personRecord = new PersonRecord();
													personRecord.setId(personRecordList.get(i).getPersonRecordId());
													if("1".equals(user.getOffice().getId())){
														personRecord.setBatchStatus("12");
														personRecord.setFirstZexpertId(expertList.get(index).getId());
														this.personRecordService.updateFirstZexpertId(personRecord);
														//调用addCheck方法进行数据推送
														this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"4");
														//this.saveExpertService.saveExpert(personRecordList.get(i).getPersonRecordId());
													}else{
														personRecord.setFirstCexpertId(expertList.get(index).getId());
														personRecord.setBatchStatus("7");
														this.personRecordService.updateFirstCexpertId(personRecord);
														//调用addCheck方法进行数据推送
														//this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"3");
													}
													index = 0;
													//index++;
													Collections.reverse(expertList);
													continue label01;
												} else {
													if(j==userSpecialty.length-1)	
													{
														index++;
														if (index < expertList.size()) {
															userSpecialty = expertList.get(index).getSpecialtyType()
																	.split(",");
															//for重新开始循环遍历下一个专家的专业
															j=-1;
															continue label02;
														} else {
															index = 0;
															continue label01;
														}
													}
												}
											}
										}
									}
									index++;
									Collections.reverse(expertList);
								}
							}
						} else {//没有查到待分配的申请单
							addMessage(redirectAttributes, "贵区没有可供申请单");
							return jspSkip(flag);
						}
					} else {//申请类型不是初始登记和变更专业，不用按照专业分配
						if("1".equals(user.getOffice().getId())){
							enterpriseWorkers.setBatchStatus("11");
						}else{
							enterpriseWorkers.setOfficeId(user.getOffice().getId());
							enterpriseWorkers.setBatchStatus("8");
						}
						//待分配申请单列表
						List<EnterpriseWorkers> personRecordList = this.enterpriseWorkersService.localdistributeExpertList2(enterpriseWorkers);
						if (personRecordList != null || personRecordList.size() > 0) {
							for (int j = 0; j < personRecordList.size(); j++) {
								if (expertList == null || expertList.size() == 0) {
									addMessage(redirectAttributes, "贵区没有可供分配的专家，请先添加专家");
									return jspSkip(flag);
								} else {
									if (index < expertList.size()) {
										PersonRecord personRecord = new PersonRecord();
										personRecord.setId(personRecordList.get(j).getPersonRecordId());
										if("1".equals(user.getOffice().getId())){
											personRecord.setBatchStatus("12");
											personRecord.setFirstZexpertId(expertList.get(index).getId());
											this.personRecordService.updateFirstZexpertId(personRecord);
											//调用addCheck方法进行数据推送
											this.addCheckService.addCheck(personRecordList.get(j).getPersonRecordId(),"4");
											//this.saveExpertService.saveExpert(personRecordList.get(j).getPersonRecordId());
										}else{
											personRecord.setFirstCexpertId(expertList.get(index).getId());
											personRecord.setBatchStatus("7");
											this.personRecordService.updateFirstCexpertId(personRecord);
											//调用addCheck方法进行数据推送
											//this.addCheckService.addCheck(personRecordList.get(j).getPersonRecordId(),"3");
										}
										
									} else {
										index = 0;
										PersonRecord personRecord = new PersonRecord();
										personRecord.setId(personRecordList.get(j).getPersonRecordId());
										if("1".equals(user.getOffice().getId())){
											personRecord.setBatchStatus("12");
											personRecord.setFirstZexpertId(expertList.get(index).getId());
											this.personRecordService.updateFirstZexpertId(personRecord);
											//调用addCheck方法进行数据推送
											this.addCheckService.addCheck(personRecordList.get(j).getPersonRecordId(),"4");
											//this.saveExpertService.saveExpert(personRecordList.get(j).getPersonRecordId());
										}else{
											personRecord.setFirstCexpertId(expertList.get(index).getId());
											personRecord.setBatchStatus("7");
											this.personRecordService.updateFirstCexpertId(personRecord);
											//调用addCheck方法进行数据推送
											//this.addCheckService.addCheck(personRecordList.get(j).getPersonRecordId(),"3");
										}
									}
									index++;
									Collections.reverse(expertList);
								}
							}
						} else {
							addMessage(redirectAttributes, "贵区没有可供申请单");
							return jspSkip(flag);
						}
					}
				} else {//专业查询条件不为空,申请类型是初始登记和变更申请，此时按照专业进行分配
					if("1".equals(user.getOffice().getId())){
						enterpriseWorkers.setBatchStatus("11");
					}else{
						enterpriseWorkers.setBatchStatus("8");
						enterpriseWorkers.setOfficeId(user.getOffice().getId());
					}
					List<EnterpriseWorkers> personRecordList = this.enterpriseWorkersService.localdistributeExpertList1(enterpriseWorkers);
					if (personRecordList != null || personRecordList.size() > 0) {
						label01: for (int i = 0; i < personRecordList.size(); i++) {
							if (expertList == null || expertList.size() == 0) {
								addMessage(redirectAttributes, "贵区没有可供分配的专家，请先添加专家");
								return jspSkip(flag);
							} else {
								if (index < expertList.size()) {
									if (i > 0) {
										if (personRecordList.get(i).getId()
												.equals(personRecordList.get(i - 1).getId())) {
											String[] userSpecialty = expertList.get(index).getSpecialtyType()
													.split(",");
											label02: for (int j = 0; j < userSpecialty.length; j++) {
												if (personRecordList.get(i).getNewValue()
														.equals(userSpecialty[j])) {
													PersonRecord personRecord = new PersonRecord();
													personRecord.setId(personRecordList.get(i).getPersonRecordId());
													if("1".equals(user.getOffice().getId())){//如果是中咨分配
														personRecord.setBatchStatus("12");
														personRecord.setSecondZexpertId(expertList.get(index).getId());
														//----------------------------判断申请单不能由同一专家审核开始--------------------------------------------
														//判断：一个申请单不允许同一个专家审核
														//根据申请单person_record的id查询该申请单的专家1信息
														List<PersonRecord> findById = this.personRecordService.findById(personRecord);
														if(findById != null && findById.size()>0){
															if(findById.get(0).getFirstZexpertId() != null){
																if(expertList.get(index).getId().equals(findById.get(0).getFirstZexpertId())){//判断专家1与待分配的专家2是否相同
																	//如果相同则不分配，跳转到下一个专家
																	index ++;
																	if (index < expertList.size()) {
																		userSpecialty = expertList.get(index).getSpecialtyType()
																				.split(",");
																		//for重新开始循环遍历下一个专家的专业
																		j=-1;
																		continue label02;
																	} else {
																		index = 0;
																		continue label01;
																	}
																}
															}
														}//不同则进行专家分配
														//----------------------------判断申请单不能由同一专家审核完毕--------------------------------------------
														this.personRecordService.updateSecondZexpertId(personRecord);
														//调用addCheck方法进行数据推送
														this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"4");
														//this.saveExpertService.saveExpert(personRecordList.get(i).getPersonRecordId());
													}else{//地方分配
														personRecord.setSecondCexpertId(expertList.get(index).getId());// 想似则分配
														personRecord.setBatchStatus("7");
														//----------------------------判断申请单不能由同一专家审核开始--------------------------------------------
														//判断：一个申请单不允许同一个专家审核
														//根据申请单person_record的id查询该申请单的专家1信息
														List<PersonRecord> findById = this.personRecordService.findById(personRecord);
														if(findById != null && findById.size()>0){
															if(findById.get(0).getFirstCexpertId() != null){
																if(expertList.get(index).getId().equals(findById.get(0).getFirstCexpertId())){//判断专家1与待分配的专家2是否相同
																	//如果相同则不分配，跳转到下一个专家
																	index ++;
																	if (index < expertList.size()) {
																		userSpecialty = expertList.get(index).getSpecialtyType()
																				.split(",");
																		//for重新开始循环遍历下一个专家的专业
																		j=-1;
																		continue label02;
																	} else {
																		index = 0;
																		continue label01;
																	}
																}
															}
														}//不同则进行专家分配
														//----------------------------判断申请单不能由同一专家审核完毕--------------------------------------------
														this.personRecordService.updateSecondCexpertId(personRecord);
														//调用addCheck方法进行数据推送
														//this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"3");
													}
													index = 0;
													//index++;
													Collections.reverse(expertList);
													continue label01;
												} else {
													if(j==userSpecialty.length-1)	
													{
														index++;
														if (index < expertList.size()) {
															userSpecialty = expertList.get(index).getSpecialtyType()
																	.split(",");
															//for重新开始循环遍历下一个专家的专业
															j=-1;
															continue label02;
														} else {
															index = 0;
															continue label01;
														}
													}
												}
											}
										} else {
											String[] userSpecialty = expertList.get(index).getSpecialtyType()
													.split(",");
											label02: for (int j = 0; j < userSpecialty.length; j++) {
												if (personRecordList.get(i).getNewValue()
														.equals(userSpecialty[j])) {
													// enterpriseWorkers.setFirstCexpertId(expertList.get(index).getId());//
													PersonRecord personRecord = new PersonRecord();
													personRecord.setId(personRecordList.get(i).getPersonRecordId());
													if("1".equals(user.getOffice().getId())){
														personRecord.setBatchStatus("12");
														personRecord.setFirstZexpertId(expertList.get(index).getId());
														this.personRecordService.updateFirstZexpertId(personRecord);
														//调用addCheck方法进行数据推送
														this.addCheckService.addCheck(personRecordList.get(j).getPersonRecordId(),"4");
														//this.saveExpertService.saveExpert(personRecordList.get(j).getPersonRecordId());
													}else{
														personRecord.setFirstCexpertId(expertList.get(index).getId());
														personRecord.setBatchStatus("7");
														this.personRecordService.updateFirstCexpertId(personRecord);
														//调用addCheck方法进行数据推送
														//this.addCheckService.addCheck(personRecordList.get(j).getPersonRecordId(),"3");
													}
													index = 0;
													//index++;
													Collections.reverse(expertList);
													continue label01;
												} else {
													if(j==userSpecialty.length-1)	
													{
														index++;
														if (index < expertList.size()) {
															userSpecialty = expertList.get(index).getSpecialtyType()
																	.split(",");
															//for重新开始循环遍历下一个专家的专业
															j=-1;
															continue label02;
														} else {
															index = 0;
															continue label01;
														}
													}
												}
											}
										}
									} else {
										String[] userSpecialty = expertList.get(index).getSpecialtyType()
												.split(",");
										label02: for (int j = 0; j < userSpecialty.length; j++) {
											if (personRecordList.get(i).getNewValue().equals(userSpecialty[j])) {
												PersonRecord personRecord = new PersonRecord();
												personRecord.setId(personRecordList.get(i).getPersonRecordId());
												if("1".equals(user.getOffice().getId())){
													personRecord.setBatchStatus("12");
													personRecord.setFirstZexpertId(expertList.get(index).getId());
													this.personRecordService.updateFirstZexpertId(personRecord);
													//调用addCheck方法进行数据推送
													this.addCheckService.addCheck(personRecordList.get(j).getPersonRecordId(),"4");
													//this.saveExpertService.saveExpert(personRecordList.get(j).getPersonRecordId());
												}else{
													personRecord.setFirstCexpertId(expertList.get(index).getId());
													personRecord.setBatchStatus("7");
													this.personRecordService.updateFirstCexpertId(personRecord);
													//调用addCheck方法进行数据推送
													//this.addCheckService.addCheck(personRecordList.get(j).getPersonRecordId(),"3");
												}
												index = 0;
												//index++;
												Collections.reverse(expertList);
												continue label01;
											} else {
												if(j==userSpecialty.length-1)	
												{
													index++;
													if (index < expertList.size()) {
														userSpecialty = expertList.get(index).getSpecialtyType()
																.split(",");
														//for重新开始循环遍历下一个专家的专业
														j=-1;
														continue label02;
													} else {
														index = 0;
														continue label01;
													}
												}
											}
										}
									}
								} else {
									index = 0;
									if (personRecordList.get(i).getId()
											.equals(personRecordList.get(i - 1).getId())) {
										String[] userSpecialty = expertList.get(index).getSpecialtyType()
												.split(",");
										label02: for (int j = 0; j < userSpecialty.length; j++) {
											if (personRecordList.get(i).getNewValue().equals(userSpecialty[j])) {
												PersonRecord personRecord = new PersonRecord();
												personRecord.setId(personRecordList.get(i).getPersonRecordId());
												if("1".equals(user.getOffice().getId())){//如果是中咨分配
													personRecord.setBatchStatus("12");
													personRecord.setSecondZexpertId(expertList.get(index).getId());
													//----------------------------判断申请单不能由同一专家审核开始--------------------------------------------
													//判断：一个申请单不允许同一个专家审核
													//根据申请单person_record的id查询该申请单的专家1信息
													List<PersonRecord> findById = this.personRecordService.findById(personRecord);
													if(findById != null && findById.size()>0){
														if(findById.get(0).getFirstZexpertId() != null){
															if(expertList.get(index).getId().equals(findById.get(0).getFirstZexpertId())){//判断专家1与待分配的专家2是否相同
																//如果相同则不分配，跳转到下一个专家
																index ++;
																if (index < expertList.size()) {
																	userSpecialty = expertList.get(index).getSpecialtyType()
																			.split(",");
																	//for重新开始循环遍历下一个专家的专业
																	j=-1;
																	continue label02;
																} else {
																	index = 0;
																	continue label01;
																}
															}
														}
													}//不同则进行专家分配
													//----------------------------判断申请单不能由同一专家审核完毕--------------------------------------------
													this.personRecordService.updateSecondZexpertId(personRecord);
													//调用addCheck方法进行数据推送
													this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"4");
													//this.saveExpertService.saveExpert(personRecordList.get(i).getPersonRecordId());
												}else{//地方分配
													personRecord.setSecondCexpertId(expertList.get(index).getId());// 想似则分配
													personRecord.setBatchStatus("7");
													//----------------------------判断申请单不能由同一专家审核开始--------------------------------------------
													//判断：一个申请单不允许同一个专家审核
													//根据申请单person_record的id查询该申请单的专家1信息
													List<PersonRecord> findById = this.personRecordService.findById(personRecord);
													if(findById != null && findById.size()>0){
														if(findById.get(0).getFirstCexpertId() != null){
															if(expertList.get(index).getId().equals(findById.get(0).getFirstCexpertId())){//判断专家1与待分配的专家2是否相同
																//如果相同则不分配，跳转到下一个专家
																index ++;
																if (index < expertList.size()) {
																	userSpecialty = expertList.get(index).getSpecialtyType()
																			.split(",");
																	//for重新开始循环遍历下一个专家的专业
																	j=-1;
																	continue label02;
																} else {
																	index = 0;
																	continue label01;
																}
															}
														}
													}//不同则进行专家分配
													//----------------------------判断申请单不能由同一专家审核完毕--------------------------------------------
													this.personRecordService.updateSecondCexpertId(personRecord);
													//调用addCheck方法进行数据推送
													//this.addCheckService.addCheck(personRecordList.get(i).getPersonRecordId(),"3");
												}
												index = 0;
												//index++;
												Collections.reverse(expertList);
												continue label01;
											} else {
												if(j==userSpecialty.length-1)	
												{
													index++;
													if (index < expertList.size()) {
														userSpecialty = expertList.get(index).getSpecialtyType()
																.split(",");
														//for重新开始循环遍历下一个专家的专业
														j=-1;
														continue label02;
													} else {
														index = 0;
														continue label01;
													}
												}
											}
										}
									} else {
										String[] userSpecialty = expertList.get(index).getSpecialtyType()
												.split(",");
										label02: for (int j = 0; j < userSpecialty.length; j++) {
											if (personRecordList.get(i).getNewValue().equals(userSpecialty[j])) {
												PersonRecord personRecord = new PersonRecord();
												personRecord.setId(personRecordList.get(i).getPersonRecordId());
												if("1".equals(user.getOffice().getId())){
													personRecord.setBatchStatus("12");
													personRecord.setFirstZexpertId(expertList.get(index).getId());
													this.personRecordService.updateFirstZexpertId(personRecord);
													//调用addCheck方法进行数据推送
													this.addCheckService.addCheck(personRecordList.get(j).getPersonRecordId(),"4");
													//this.saveExpertService.saveExpert(personRecordList.get(j).getPersonRecordId());
												}else{
													personRecord.setFirstCexpertId(expertList.get(index).getId());
													personRecord.setBatchStatus("7");
													this.personRecordService.updateFirstCexpertId(personRecord);
													//调用addCheck方法进行数据推送
													//this.addCheckService.addCheck(personRecordList.get(j).getPersonRecordId(),"3");
												}
												index = 0;
												//index++;
												Collections.reverse(expertList);
												continue label01;
											} else {
												if(j==userSpecialty.length-1)	
												{
													index++;
													if (index < expertList.size()) {
														userSpecialty = expertList.get(index).getSpecialtyType()
																.split(",");
														//for重新开始循环遍历下一个专家的专业
														j=-1;
														continue label02;
													} else {
														index = 0;
														continue label01;
													}
												}
											}
										}
									}
								}
								index++;
								Collections.reverse(expertList);
							}
						}
					} else {
						addMessage(redirectAttributes, "贵区没有可供分配的申请单");
						return jspSkip(flag);
					}
				}
		}
		return jspSkip(flag);
	}
	
	/*
	 * 专家分配完成后的页面跳转
	 */
	public String jspSkip(String flag){
		if("0".equals(flag)){//地方
			return "modules/enterprise/distributeExpertList";
		}else if("1".equals(flag)){//中咨
			return "modules/enterprise/zzdistributeExpertList";
		}else{
			return "";
		}
	}
	
	//展示专家列表
	@RequestMapping(value = "form")
	public String form(String personRecordId,String url,User user, Model model,HttpServletRequest request,HttpServletResponse response) {
		// 如果是admin，可分配所有，如果是地方管理员，只能分配自己的
		User user1 = UserUtils.getUser();
		user.setOfficeId(user1.getOffice().getId());
		user.setUserModel("5");
		if (personRecordId != null) {
			// 查询所有专家信息，展示但前台
			Page<User> page = this.systemService
					.findExpertByUserModel(new Page<User>(request, response), user);
			model.addAttribute("page",page);
			model.addAttribute("personRecordId", personRecordId);
			model.addAttribute("url",url);
			return "modules/enterprise/expertDistributeForm";
		}else{
			// 查询所有专家信息，展示但前台
			Page<User> page = this.systemService
				.findExpertByUserModel(new Page<User>(request, response), user);
			model.addAttribute("page",page);
			return "modules/enterprise/manualExpertDistributeForm";
		}
	}
	
	/**
	 * 根据前台查询条件将查询出的申请单分配给某一个专家
	 */
	@RequestMapping(value="manualDistribute")
	public String manualDistribute(EnterpriseWorkers enterpriseWorkers,Model model,int index,RedirectAttributes redirectAttributes){
		//1.根据userId获取该专家的专业信息
		String userId = enterpriseWorkers.getUserId();
		User user = this.systemService.getUser(userId);
		enterpriseWorkers.setIndex(index);
		if(user != null){
			//2.根据条件查询出待分配的申请单信息
			if("1".equals(enterpriseWorkers.getDeclareType()) || "3".equals(enterpriseWorkers.getDeclareType())){
				String[] spcailtyArr = user.getSpecialtyType().split(",");
				enterpriseWorkers.setBatchStatus("11");
				List<EnterpriseWorkers> waitDistributeList = this.enterpriseWorkersService.localdistributeExpertList1(enterpriseWorkers);
				//遍历申请单
				for (int i = 0; i < waitDistributeList.size(); i++) {
					//判断专家的专业与申请单的变更类型和变更专业是否相同
					for (int j = 0; j < spcailtyArr.length; j++) {
						if(spcailtyArr[j].equals(waitDistributeList.get(i).getNewValue())){
							//主专业
							if("2".equals(waitDistributeList.get(i).getChangeType()) || "4".equals(waitDistributeList.get(i).getChangeType())){
								PersonRecord personRecord = new PersonRecord();
								personRecord.setId(waitDistributeList.get(i).getPersonRecordId());
								personRecord.setBatchStatus("12");
								personRecord.setDeclareStatus("12");
								personRecord.setFirstZexpertId(user.getId());
								//判断：一个申请单不允许同一个专家审核
								//根据申请单person_record的id查询该申请单的专家1信息
								List<PersonRecord> findById = this.personRecordService.findById(personRecord);
								if(findById != null && findById.size()>0){
									if(findById.get(0).getSecondZexpertId() != null){
										if(user.getId().equals(findById.get(0).getSecondZexpertId())){//判断专家1与待分配的专家2是否相同
											//如果相同则不分配，跳转到下一个申请单
											break;
										}
									}
								}
								ChangeItem changeItem=new ChangeItem();
								changeItem.setRecordId(personRecord.getId());
								changeItem.setChangeType(waitDistributeList.get(i).getChangeType());
								this.changeItemService.updateIsAssigned(changeItem);
								
								this.personRecordService.updateFirstZexpertId(personRecord);
								this.personRecordService.updateDeclareStatusNew(personRecord);
								//调用addCheck方法进行数据推送
								this.addCheckService.addCheck(waitDistributeList.get(i).getPersonRecordId(),"4");
							}
							//辅专业
							if("3".equals(waitDistributeList.get(i).getChangeType()) || "5".equals(waitDistributeList.get(i).getChangeType())){
								PersonRecord personRecord = new PersonRecord();
								personRecord.setId(waitDistributeList.get(i).getPersonRecordId());
								if("1".equals(user.getOffice().getId())){//如果是中咨分配
									personRecord.setBatchStatus("12");
									personRecord.setDeclareStatus("12");
									personRecord.setSecondZexpertId(user.getId());
									//判断：一个申请单不允许同一个专家审核
									//根据申请单person_record的id查询该申请单的专家1信息
									List<PersonRecord> findById = this.personRecordService.findById(personRecord);
									if(findById != null && findById.size()>0){
										if(findById.get(0).getFirstZexpertId() != null){
											if(user.getId().equals(findById.get(0).getFirstZexpertId())){//判断专家1与待分配的专家2是否相同
												//如果相同则不分配，跳转到下一个申请单
												break;
												}
											}
										}
									}
									ChangeItem changeItem=new ChangeItem();
									changeItem.setRecordId(personRecord.getId());
									changeItem.setChangeType(waitDistributeList.get(i).getChangeType());
									this.changeItemService.updateIsAssigned(changeItem);
									this.personRecordService.updateSecondZexpertId(personRecord);
									this.personRecordService.updateDeclareStatusNew(personRecord);
									//调用addCheck方法进行数据推送
									this.addCheckService.addCheck(waitDistributeList.get(i).getPersonRecordId(),"4");
							}
						}
					}
				}
			}else if("0".equals(enterpriseWorkers.getDeclareType()) || "2".equals(enterpriseWorkers.getDeclareType()) || "4".equals(enterpriseWorkers.getDeclareType())){
				enterpriseWorkers.setBatchStatus("11");
				List<EnterpriseWorkers> waitDistributeList = this.enterpriseWorkersService.localdistributeExpertList1(enterpriseWorkers);
				for (int i = 0; i < waitDistributeList.size(); i++) {
					PersonRecord personRecord = new PersonRecord();
					personRecord.setId(waitDistributeList.get(i).getPersonRecordId());
					personRecord.setBatchStatus("12");
					personRecord.setDeclareStatus("12");
					personRecord.setFirstZexpertId(user.getId());
					this.personRecordService.updateFirstZexpertId(personRecord);
					this.personRecordService.updateDeclareStatusNew(personRecord);
					//调用addCheck方法进行数据推送
					//this.addCheckService.addCheck(waitDistributeList.get(i).getPersonRecordId(),"4");
					this.saveExpertService.saveExpert(waitDistributeList.get(i).getPersonRecordId());
				}
			}else{
				addMessage(redirectAttributes, "分配失败,数据有误,请联系系统运维人员");
				return "modules/enterprise/zzdistributeExpertList";
			}
		}else{
			addMessage(redirectAttributes, "该专家在系统中不存在，请确认该专家是否已经注册");
			return "modules/enterprise/zzdistributeExpertList";
		}
		return "modules/enterprise/zzdistributeExpertList";
	}
	

	
	/** 手动分配专家
	 * 
	 * @param personRecordId
	 * @param url 如果url等于1则表示手动分配专家1，否则为专家2
	 * @param user
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update")
	public String updateExpert(String personRecordId, String url, User user, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		PersonRecord personRecord = new PersonRecord();
		User user1 = UserUtils.getUser();
		if (url.equals("1")) {//手动分配专家1
			personRecord.setId(personRecordId);
			if ("1".equals(user1.getOffice().getId())) {//判断是否是中咨，中咨跟地方状态不同
				personRecord.setBatchStatus("12");
				personRecord.setFirstZexpertId(user.getId());
				//一个申请单不允许同一专家分配
				List<PersonRecord> findById = this.personRecordService.findById(personRecord);
				if (findById != null && findById.size()>0) {
					if (findById.get(0).getSecondZexpertId() != null) {
						if (personRecord.getFirstZexpertId().equals(findById.get(0).getSecondZexpertId())) {
							addMessage(redirectAttributes, "一个申请单不能分配给同一专家，手动分配失败");
						}else{
							addMessage(redirectAttributes, "手动分配成功");
							this.personRecordService.updateFirstZexpertId(personRecord);
						}
					}else{
						addMessage(redirectAttributes, "手动分配成功");
						this.personRecordService.updateFirstZexpertId(personRecord);
					}
				}
			} else {
				personRecord.setBatchStatus("7");
				personRecord.setFirstCexpertId(user.getId());
				//一个申请单不允许同一专家审核
				List<PersonRecord> findById = this.personRecordService.findById(personRecord);
				if (findById != null && findById.size()>0) {
					if (findById.get(0).getSecondCexpertId() != null) {
						if (personRecord.getFirstCexpertId().equals(findById.get(0).getSecondCexpertId())) {
							addMessage(redirectAttributes, "一个申请单不能分配给同一专家，手动分配失败");
						}else{
							addMessage(redirectAttributes, "手动分配成功");
							this.personRecordService.updateFirstCexpertId(personRecord);
						}
					}else{
						addMessage(redirectAttributes, "手动分配成功");
						this.personRecordService.updateFirstCexpertId(personRecord);
					}
				}
			}
		} else if (url.equals("2")) {//手动分配专家2
			personRecord.setId(personRecordId);
			if ("1".equals(user1.getOffice().getId())) {//判断是否是中咨，中咨跟地方状态不同
				personRecord.setBatchStatus("12");
				personRecord.setSecondZexpertId(user.getId());
				
				//一个申请单不允许同一专家审核
				List<PersonRecord> findById = this.personRecordService.findById(personRecord);
				if (findById != null && findById.size()>0) {
					if (findById.get(0).getFirstZexpertId() != null) {
						if (personRecord.getSecondZexpertId().equals(findById.get(0).getFirstZexpertId())) {
							addMessage(redirectAttributes, "一个申请单不能分配给同一专家，手动分配失败");
						}else{
							addMessage(redirectAttributes, "手动分配成功");
							this.personRecordService.updateSecondZexpertId(personRecord);
						}
					}else{
						addMessage(redirectAttributes, "手动分配成功");
						this.personRecordService.updateSecondZexpertId(personRecord);
					}
				}
			} else {
				personRecord.setBatchStatus("7");
				personRecord.setSecondCexpertId(user.getId());
				//一个申请单不允许同一专家审核
				List<PersonRecord> findById = this.personRecordService.findById(personRecord);
				if (findById != null && findById.size()>0) {
					if (findById.get(0).getFirstCexpertId() != null) {
						if (personRecord.getSecondCexpertId().equals(findById.get(0).getFirstCexpertId())) {
							addMessage(redirectAttributes, "一个申请单不能分配给同一专家，手动分配失败");
						}else{
							addMessage(redirectAttributes, "手动分配成功");
							this.personRecordService.updateSecondCexpertId(personRecord);
						}
					}else{
						addMessage(redirectAttributes, "手动分配成功");
						this.personRecordService.updateSecondCexpertId(personRecord);
					}
				}
			}
		}
		return "redirect:" + Global.getAdminPath() + "/enterprise/auditAndReport/applyDataDistributeExpert";
	}
	
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	
    @RequestMapping(value = "exportAlreadyReportList", method=RequestMethod.POST)
    public String exportFile(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			User user = UserUtils.getUser();
			List<EnterpriseWorkersExport> list =new ArrayList<EnterpriseWorkersExport>();
			if (user.getOffice() == null) {// 判断office是否为空
				
			} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
				
				EnterpriseWorkers enterpriseWorkers = new EnterpriseWorkers();
				enterpriseWorkers.setBatchStatus("5");
				if ("1".equals(user.getOffice().getId())) {
					
					
				} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
					enterpriseWorkers.setPid(user.getId());
				
				}
				List<EnterpriseWorkers> workList = enterpriseWorkersService.findPageList02All( enterpriseWorkers);	
				
				if(workList!=null && workList.size()>0){
					 
					
					for (EnterpriseWorkers work : workList) {
						list.add(new EnterpriseWorkersExport(work));
					}
				}
			}
            String fileName = "已上报申请单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		new ExportExcel("申请单数据", EnterpriseWorkersExport.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出已上报申请单！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/enterprise/auditAndReport/alreadyReportList?repage";
    }
    
    
    /**
	 * 导出地方已上报中咨协会的用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	
    @RequestMapping(value = "exportLocalAlreadyReportList", method=RequestMethod.POST)
    public String exportFile2(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			User user = UserUtils.getUser();
			List<EnterpriseWorkersExport2> list =new ArrayList<EnterpriseWorkersExport2>();
			if (user.getOffice() == null) {// 判断office是否为空
				
			} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
				
				EnterpriseWorkers enterpriseWorkers = new EnterpriseWorkers();
				if ("1".equals(user.getOffice().getId())) {
					
				} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
					enterpriseWorkers.setOfficeId(user.getOffice().getId());
				}
				List<EnterpriseWorkers> workList = enterpriseWorkersService.findPageList04All(enterpriseWorkers);	
				
				if(workList!=null && workList.size()>0){
					for (EnterpriseWorkers work : workList) {
						list.add(new EnterpriseWorkersExport2(work));
					}
				}
			}
            String fileName = "地方已上报申请单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		new ExportExcel("申请单数据", EnterpriseWorkersExport2.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出已上报申请单！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/enterprise/auditAndReport/localAlreadyReportList?repage";
    }
	
    /**
	 * 申请单列表
	 * 
	 * @param model
	 * @return name,sex,certificatesType,certificatesNum,isValid,create_data;
	 *         confimFlg
	 */
	@RequestMapping(value = "applicationBillsList")
	public String applicationBillsList(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和person_record表查询待上报申请列表
		User user = UserUtils.getUser();
		if (user.getOffice() == null) {// 判断office是否为空
			return "modules/enterprise/applicationBillsList";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				String flag = "1";
				enterpriseWorkers.setFlag(flag);
				enterpriseWorkers.setBatchStatus("20");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findApplicationBills(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
				model.addAttribute("flag", flag);
			} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				String flag = "0";
				enterpriseWorkers.setFlag(flag);
				enterpriseWorkers.setBatchStatus("20");
				enterpriseWorkers.setOfficeId(user.getOffice().getId());
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findApplicationBills(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
				model.addAttribute("flag", flag);
			}
		}
		return "modules/enterprise/applicationBillsList";
	}
	/**
	 * 已完成申请单列表
	 * 
	 * @param model
	 * @return name,sex,certificatesType,certificatesNum,isValid,create_data;
	 *         confimFlg
	 */
	@RequestMapping(value = "applicationBillsListClose")
	public String applicationBillsListClose(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据enterpriseWorkers和person_record表查询待上报申请列表
		User user = UserUtils.getUser();
		if (user.getOffice() == null) {// 判断office是否为空
			return "modules/enterprise/applicationBillsListClose";
		} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
			if ("1".equals(user.getOffice().getId())) {
				String flag = "1";
				enterpriseWorkers.setFlag(flag);
				enterpriseWorkers.setBatchStatus("20");
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findApplicationBillsClose(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
				model.addAttribute("flag", flag);
			} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
				String flag = "0";
				enterpriseWorkers.setFlag(flag);
				enterpriseWorkers.setBatchStatus("20");
				enterpriseWorkers.setOfficeId(user.getOffice().getId());
				Page<EnterpriseWorkers> page = enterpriseWorkersService
						.findApplicationBillsClose(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
				model.addAttribute("page", page);
				model.addAttribute("flag", flag);
			}
		}
		return "modules/enterprise/applicationBillsListClose";
	}
	
	
	/**
	 * 导出地协会的申请单列表
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	
    @RequestMapping(value = "exportApplyFromList", method=RequestMethod.POST)
    public String exportFile3(EnterpriseWorkers enterpriseWorkers,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
    	String flag="";
		try {
			User user = UserUtils.getUser();
			List<EnterpriseWorkersExport3> list =new ArrayList<EnterpriseWorkersExport3>();
			if (user.getOffice() == null) {// 判断office是否为空
				
			} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
				
				if (user.getOffice() == null) {// 判断office是否为空
					return "modules/enterprise/applicationBillsList";
				} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
					if ("1".equals(user.getOffice().getId())) {
						flag = "1";
						enterpriseWorkers.setFlag(flag);
						enterpriseWorkers.setBatchStatus("20");
					} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
						flag = "0";
						enterpriseWorkers.setFlag(flag);
						enterpriseWorkers.setBatchStatus("20");
						enterpriseWorkers.setOfficeId(user.getOffice().getId());
					}
				}
				List<EnterpriseWorkers> workList = enterpriseWorkersService.findApplicationBills(enterpriseWorkers);	
				
				if(workList!=null && workList.size()>0){
					int index = 1;
					for (EnterpriseWorkers work : workList) {
						work.setIndex(index++);
						work.setFlag(flag);
						list.add(new EnterpriseWorkersExport3(work));
					}
				}
			}
            String fileName = "申请单列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		new ExportExcel("申请单列表", EnterpriseWorkersExport3.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出申请单列表！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/enterprise/auditAndReport/applicationBillsList?repage";
    }
    
}