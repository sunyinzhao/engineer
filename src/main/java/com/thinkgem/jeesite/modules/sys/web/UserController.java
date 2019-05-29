/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SendMailUtil;
import com.thinkgem.jeesite.common.utils.SendMessge;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.ChangeItem;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import com.thinkgem.jeesite.modules.counselor.service.ChangeItemService;
import com.thinkgem.jeesite.modules.counselor.service.CounselorAttachmentService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.entity.PersonRecord;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.enterprise.service.PersonRecordService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@Autowired
	private EnterpriseWorkersService enterpriseWorkersService;

	@Autowired
	private CounselorAttachmentService counselorAttachmentService;

	@Autowired
	private PersonRecordService personRecordService;
	
	@Autowired
	private ChangeItemService changeItemService;

	@Autowired
	private DictService dictService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	/**
	 * 专家数据列表
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:expert:view")
	@RequestMapping(value = {"expertList", ""})
	public String expertList(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		//user.setUserType("2");
		user.setUserModel("1");
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/expertList";
	}
	
	/**
	 * 查看专家
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:expert:view")
	@RequestMapping(value = {"expertIndex"})
	public String expertIndex(User user, Model model) {
		return "modules/sys/expertIndex";
	}
	

	@ResponseBody
	@RequiresPermissions("sys:expert:view")
	@RequestMapping(value = {"expertListData"})
	public Page<User> expertListData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		return page;
	}



	@RequiresPermissions("sys:expert:edit")
	@RequestMapping(value = "expertSave")
	public String expertSave(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("company.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存专家'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		user.setUserModel("1");
		systemService.saveUser(user);
		
		//BEGIN ADD BY GAOYONGJIAN 20180620 用户发放成功后，发送短信
		String[] args =new String[2];
        String message="";

        args[0]="【中国工程咨询协会】重要误删，专家您好,您在工程咨询单位资信评价管理系统的专家登录登录账号为："+user.getLoginName()+" ,密码为："+user.getNewPassword()+"，请妥善保管。";
        message="尊敬的用户您好：\n\n\t 您在工程咨询单位资信评价管理系统的专家登录登录账号为："+user.getLoginName()+" ,密码为："+user.getNewPassword()+"，请妥善保管。";
        args[1]=user.getMobile();
        SendMessge.maina(args);
        SendMailUtil.sendCommonMail(user.getEmail(), "专家用户发放提醒", message);
		//E N D ADD BY GAOYONGJIAN 20180620
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存专家'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/expertList?repage";
	}
	
	
	
	//---***********************************************************************************----------
	
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}	

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setUserModel("2");
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/userList";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"listData"})
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setUserModel("2");
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		return page;
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
	/*	model.addAttribute("allRoles", systemService.findAllRole());*/
        model.addAttribute("allRoles", systemService.findRoleByUser(UserUtils.getUser(),"2"));



		return "modules/sys/userForm";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("company.id")));
		user.setUserModel("2");
		
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = {"expertDelete", ""})
	public String delete(User user,String url,RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		if (url.equals("expert")) {
			return "redirect:" + adminPath + "/sys/user/expertList?repage";
		}else if (url.equals("engineerExpert")) {
			return "redirect:" + adminPath + "/sys/user/engineerExpertList?repage";
		}else{
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
	}
		
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	//@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 用户信息显示
	 * @param user
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "null" })
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//获取当前用户
		if(currentUser != null){
			// 判断是不是协会管理员，是返回userID为0
			List<Role> roleList = currentUser.getRoleList();
			if(roleList != null){
				for (int i = 0; i < roleList.size(); i++) {
					if (roleList.get(i).getName().equals("系统管理员") || roleList.get(i).getName().equals("协会管理员")
							|| roleList.get(i).getName().equals("地方协会管理员")) {
						model.addAttribute("flag", "0");
					} else {
						model.addAttribute("flag", "1");
					}
				}	
			}
			// 根据身份证号和name查询咨询师信息
			EnterpriseWorkers enterpriseWorkers = new EnterpriseWorkers();
			enterpriseWorkers.setCertificatesNum(currentUser.getCardNumber());
			enterpriseWorkers.setName(currentUser.getName());
			enterpriseWorkers = this.enterpriseWorkersService.findPerInfo(enterpriseWorkers);
			CounselorAttachment counselorAttachment = new CounselorAttachment();

			if(enterpriseWorkers != null){
				counselorAttachment.setPid(enterpriseWorkers.getId());
				counselorAttachment.setType("5");
				counselorAttachment.setTableType("5");
					List<CounselorAttachment> list = counselorAttachmentService.findListByTypeAndPid(counselorAttachment);
				model.addAttribute("attachList",list);
				String certificatesNum = enterpriseWorkers.getCertificatesNum();
				if ("1".equals(enterpriseWorkers.getCertificatesType()) && certificatesNum.length() == 18) {
					String yy1 = certificatesNum.substring(6, 10); // 出生的年份
					String mm1 = certificatesNum.substring(10, 12); // 出生的月份
					String dd1 = certificatesNum.substring(12, 14); // 出生的日期
					String birthday = yy1.concat("-").concat(mm1).concat("-").concat(dd1);
					String sex = certificatesNum.substring(16,17);//性别
					// 计算年龄
					try {
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
						String s1 = sdf.format(date);
						Date today = sdf.parse(s1);
						Date birth = sdf.parse(birthday);
						int year = today.getYear();
						int year2 = birth.getYear();
						enterpriseWorkers.setAge(year - year2);
						if(Integer.parseInt(sex)%2==0){
							enterpriseWorkers.setSex("2");
						}else{
							enterpriseWorkers.setSex("1");
						}
						
						model.addAttribute("enterpriseWorkers", enterpriseWorkers);
						
					} catch (Exception e) {
						e.printStackTrace();
						//System.out.println("a problem has happened!");
					}
				} else {
					model.addAttribute("enterpriseWorkers", enterpriseWorkers);
				}
			}else{
				EnterpriseWorkers enterpriseWorkers1 = new EnterpriseWorkers();
				if(currentUser.getName() != null){
					enterpriseWorkers1.setName(currentUser.getName());
				}
				if(currentUser.getCardNumber() != null){
					enterpriseWorkers1.setCertificatesNum(currentUser.getCardNumber());
				}
				if(currentUser.getMobile() != null){
					enterpriseWorkers1.setMobile(currentUser.getMobile());
				}
				if(currentUser.getEmail() != null){
					enterpriseWorkers1.setEmail(currentUser.getEmail());
				}
				model.addAttribute("enterpriseWorkers", enterpriseWorkers1);
				return "modules/sys/userInfo";
			}
		} else {
			addMessage(redirectAttributes, "该用户个人信息不存在！");
		}
		return "modules/sys/userInfo";
	}
	
	/**
	 * 查看用户信息
	 */
	@SuppressWarnings({ "deprecation", "null" })
	@RequiresPermissions("user")
	@RequestMapping(value = "infoShow")
	public String infoShow(User user, String id,HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//获取当前用户
		EnterpriseWorkers enterpriseWorkers2 = enterpriseWorkersService.getNew(id);
		// 根据身份证号和name查询咨询师信息
		EnterpriseWorkers enterpriseWorkers = new EnterpriseWorkers();
		enterpriseWorkers.setCertificatesNum(enterpriseWorkers2.getCertificatesNum());
		enterpriseWorkers.setName(enterpriseWorkers2.getName());
		enterpriseWorkers = this.enterpriseWorkersService.findPerInfo(enterpriseWorkers);
		if (enterpriseWorkers != null) {
			String certificatesNum = enterpriseWorkers.getCertificatesNum();
			if ("1".equals(enterpriseWorkers.getCertificatesType()) && certificatesNum.length() == 18) {
				String yy1 = certificatesNum.substring(6, 10); // 出生的年份
				String mm1 = certificatesNum.substring(10, 12); // 出生的月份
				String dd1 = certificatesNum.substring(12, 14); // 出生的日期
				String birthday = yy1.concat("-").concat(mm1).concat("-").concat(dd1);
				String sex = certificatesNum.substring(16, 17);// 性别
				// 计算年龄
				try {
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
					String s1 = sdf.format(date);
					Date today = sdf.parse(s1);
					Date birth = sdf.parse(birthday);
					int year = today.getYear();
					int year2 = birth.getYear();
					enterpriseWorkers.setAge(year - year2);
					if (Integer.parseInt(sex) % 2 == 0) {
						enterpriseWorkers.setSex("2");
					} else {
						enterpriseWorkers.setSex("1");
					}

					model.addAttribute("enterpriseWorkers", enterpriseWorkers);
					
					if(currentUser != null){
						// 判断是不是协会管理员，是返回userID为0
						List<Role> roleList = currentUser.getRoleList();
						if(roleList != null){
							for (int i = 0; i < roleList.size(); i++) {
								if (roleList.get(i).getName().equals("系统管理员") || roleList.get(i).getName().equals("协会管理员")) {
									model.addAttribute("flag", "0");
									break;//如果该用户有系统管理员权限，则终止当前循环
								} else {
									model.addAttribute("flag", "1");
								}
							}	
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("a problem has happened!");
				}
			} else {
				model.addAttribute("enterpriseWorkers", enterpriseWorkers);
			}
		} else {//未注册的用户可以修改worker表中的数据
			if ("1".equals(enterpriseWorkers2.getCertificatesType()) && enterpriseWorkers2.getCertificatesNum().length() == 18) {
				String yy1 = enterpriseWorkers2.getCertificatesNum().substring(6, 10); // 出生的年份
				String mm1 = enterpriseWorkers2.getCertificatesNum().substring(10, 12); // 出生的月份
				String dd1 = enterpriseWorkers2.getCertificatesNum().substring(12, 14); // 出生的日期
				String birthday = yy1.concat("-").concat(mm1).concat("-").concat(dd1);
				String sex = enterpriseWorkers2.getCertificatesNum().substring(16, 17);// 性别
				// 计算年龄
				try {
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
					String s1 = sdf.format(date);
					Date today = sdf.parse(s1);
					Date birth = sdf.parse(birthday);
					int year = today.getYear();
					int year2 = birth.getYear();
					enterpriseWorkers2.setAge(year - year2);
					if (Integer.parseInt(sex) % 2 == 0) {
						enterpriseWorkers2.setSex("2");
					} else {
						enterpriseWorkers2.setSex("1");
					}

					model.addAttribute("enterpriseWorkers", enterpriseWorkers2);
					
					if(currentUser != null){
						// 判断是不是协会管理员，是返回userID为0
						List<Role> roleList = currentUser.getRoleList();
						if(roleList != null){
							for (int i = 0; i < roleList.size(); i++) {
								if (roleList.get(i).getName().equals("系统管理员") || roleList.get(i).getName().equals("协会管理员")) {
									model.addAttribute("flag", "0");
									break;//如果该用户有系统管理员权限，则终止当前循环
								} else {
									model.addAttribute("flag", "1");
								}
							}	
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("a problem has happened!");
				}
			}else{
				if(currentUser != null){
					// 判断是不是协会管理员，是返回userID为0
					List<Role> roleList = currentUser.getRoleList();
					if(roleList != null){
						for (int i = 0; i < roleList.size(); i++) {
							if (roleList.get(i).getName().equals("系统管理员") || roleList.get(i).getName().equals("协会管理员")) {
								model.addAttribute("flag", "0");
								break;//如果该用户有系统管理员权限，则终止当前循环
							} else {
								model.addAttribute("flag", "1");
							}
						}	
					}
				}
				model.addAttribute("enterpriseWorkers", enterpriseWorkers2);
			}
		}
		return "modules/sys/userInfoShow";
	}
	@RequestMapping(value = "infoShowAdmin")
	public String infoShowAdmin(String id,HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//获取当前用户
		EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getNew(id);
		model.addAttribute("enterpriseWorkers", enterpriseWorkers);
		return "modules/sys/userInfoAdmin";
	}
	@RequestMapping(value = "infoSaveAdmin")
	public String infoSaveAdmin(EnterpriseWorkers enterpriseWorker, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		//HTML特殊字符转义
		String unescapeHtml4 = StringEscapeUtils.unescapeHtml4(enterpriseWorker.getName());
		enterpriseWorker.setName(unescapeHtml4);
		EnterpriseWorkers enterpriseWorkerbak = this.enterpriseWorkersService.get(enterpriseWorker.getId());
		//-------HTML特殊字符转义--------
		if ("身份证".equals(enterpriseWorker.getCertificatesType())) {
			enterpriseWorker.setCertificatesType("1");
		} else if ("护照".equals(enterpriseWorker.getCertificatesType())) {
			enterpriseWorker.setCertificatesType("2");
		} else if ("侨胞证".equals(enterpriseWorker.getCertificatesType())) {
			enterpriseWorker.setCertificatesType("3");
		}
		if ("男".equals(enterpriseWorker.getSex())) {
			enterpriseWorker.setSex("1");
		}else{
			enterpriseWorker.setSex("2");
		}
		this.enterpriseWorkersService.updateForAdmin(enterpriseWorker);
		User user=new User();
		user = this.systemService.getUserByCardNumber(enterpriseWorkerbak.getCertificatesNum());
		if (user != null)
		{
			user.setName(enterpriseWorker.getName());
			user.setCardNumber(enterpriseWorker.getCertificatesNum());
			user.setMobile(enterpriseWorker.getMobile());
			user.setEmail(enterpriseWorker.getEmail());
			this.systemService.updateUserInfoById(user);
		}
		model.addAttribute("enterpriseWorkers", enterpriseWorker);
		addMessage(redirectAttributes, "修改咨询师个人信息成功");
		return "redirect:" + adminPath + "/sys/user/infoShowAdmin?id="+enterpriseWorker.getId();
		/*return "modules/sys/userInfoAdmin";*/
	}
	/**
	 * 用户信息保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "infoSave")
	public String infoSave(EnterpriseWorkers enterpriseWorker, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		//HTML特殊字符转义
		String unescapeHtml4 = StringEscapeUtils.unescapeHtml4(enterpriseWorker.getName());
		enterpriseWorker.setName(unescapeHtml4);
		//-------HTML特殊字符转义--------
		//保存，将个人信息全部保存到workers表中，并将手机号，身份证号和邮箱，姓名同步到sys_user表中
		//如果不是协会管理员修改，根据身份证号和name为条件，直接修改该条信息，如果是管理员，根据管理员身份证和name先查询出该条信息的id，然后根据id进行修改（管理员可以修改任何信息，非管理员只能修改密码和手机号
		User currentUser = new User();
		//判断enterpriseWorkers的id是否为空，如果为空，说明enterpriseWorkers没有存放该用户信息,这时候只更新user表信息
		if(enterpriseWorker.getId() == null){
			//根据用户Id更新用户信息,更新字段：身份证号，姓名，手机，邮件
			User user = new User();
			user.setId(currentUser.getId());
			user.setCardNumber(enterpriseWorker.getCertificatesNum());
			user.setName(enterpriseWorker.getName());
			user.setMobile(enterpriseWorker.getMobile());
			user.setEmail(enterpriseWorker.getEmail());
			user.setOfficeId(enterpriseWorker.getOfficeId());
			if("2".equals(enterpriseWorker.getConfimFlg())){//将confimFlg设置为0
				user.setConfimFlg("0");
				this.systemService.updateConfirmFlg(user);
			}
			this.systemService.updateUserInfoNew(user);//更新user对象
		}else{
			List<Role> roleList = UserUtils.getUser().getRoleList();
			for (int i = 0; i < roleList.size(); i++) {
				// 判断是不是协会管理员
				if (roleList.get(i).getName().equals("系统管理员") || roleList.get(i).getName().equals("协会管理员")
						|| roleList.get(i).getName().equals("地方协会管理员")) {// 如果是系统管理员,都可以修改
					
					//根据enterpriseWorker中的身份证号和姓名查询user表中该用户是否存在
					User user0 = new User();
					user0.setCardNumber(enterpriseWorker.getCertificatesNum());
					user0.setName(enterpriseWorker.getName());
					User findUserId = this.systemService.findUserId(user0);
					//判断该用户在user表中是否存在，如果不存在只修改worker信息
					if(findUserId == null){
						// 跟新enterpriesWorkers,更新字段：证件类型，证件号，姓名，年龄，性别，手机，邮箱
						if ("身份证".equals(enterpriseWorker.getCertificatesType())) {
							enterpriseWorker.setCertificatesType("1");
						} else if ("护照".equals(enterpriseWorker.getCertificatesType())) {
							enterpriseWorker.setCertificatesType("2");
						} else if ("侨胞证".equals(enterpriseWorker.getCertificatesType())) {
							enterpriseWorker.setCertificatesType("3");
						}
						this.enterpriseWorkersService.updateZXSInfo(enterpriseWorker);
						addMessage(redirectAttributes, "修改个人信息成功！");
						if(enterpriseWorker.getFlag() != null && "0".equals(enterpriseWorker.getFlag())){
							return "redirect:" + adminPath + "/sys/user/infoShow?id="+enterpriseWorker.getId();// 重定向到info
						}
						return "redirect:" + adminPath + "/sys/user/info";// 重定向到info
					}
					// 可以修改任何信息
					EnterpriseWorkers enterpriseWorker1 = this.enterpriseWorkersService
							.getNew(enterpriseWorker.getId());// 根据ID获取enterpeise的身份证号和name信息
					// 将身份证号和name添加到user实体中，查询出当前用户id
					currentUser.setCardNumber(enterpriseWorker1.getCertificatesNum());
					currentUser.setName(enterpriseWorker1.getName());
					currentUser = this.systemService.findUserId(currentUser);
					// 根据用户Id更新用户信息,更新字段：身份证号，姓名，手机，邮件
					User user = new User();
					user.setId(currentUser.getId());
					user.setCardNumber(enterpriseWorker.getCertificatesNum());
					user.setName(enterpriseWorker.getName());
					user.setMobile(enterpriseWorker.getMobile());
					user.setEmail(enterpriseWorker.getEmail());
					user.setOfficeId(enterpriseWorker.getOfficeId());
					if("2".equals(enterpriseWorker.getConfimFlg())){//将confimFlg设置为0
						user.setConfimFlg("0");
						this.systemService.updateConfirmFlg(user);
					}
					this.systemService.updateUserInfoNew(user);// 更新user对象
					// 跟新enterpriesWorkers,更新字段：证件类型，证件号，姓名，年龄，性别，手机，邮箱
					if ("身份证".equals(enterpriseWorker.getCertificatesType())) {
						enterpriseWorker.setCertificatesType("1");
					} else if ("护照".equals(enterpriseWorker.getCertificatesType())) {
						enterpriseWorker.setCertificatesType("2");
					} else if ("侨胞证".equals(enterpriseWorker.getCertificatesType())) {
						enterpriseWorker.setCertificatesType("3");
					}
					this.enterpriseWorkersService.updateZXSInfo(enterpriseWorker);
					// model.addAttribute("message", "修改个人信息成功！");
					addMessage(redirectAttributes, "修改个人信息成功！");
					if(enterpriseWorker.getFlag() != null && "0".equals(enterpriseWorker.getFlag())){
						return "redirect:" + adminPath + "/sys/user/infoShow?id="+enterpriseWorker.getId();// 重定向到info
					}
					return "redirect:" + adminPath + "/sys/user/info";// 重定向到info
				} else {// 不是管理员
					if ("身份证".equals(enterpriseWorker.getCertificatesType())) {// 证件类型是不是身份证，如果是身份证只允许修改电话和邮箱
						this.enterpriseWorkersService.updateZXSInfo01(enterpriseWorker);
						EnterpriseWorkers enterpriseWorker1 = this.enterpriseWorkersService
								.getNew(enterpriseWorker.getId());// 根据ID获取enterpeise的身份证号和name信息
						// 将身份证号和name添加到user实体中，查询出当前用户id
						currentUser.setCardNumber(enterpriseWorker1.getCertificatesNum());
						currentUser.setName(enterpriseWorker1.getName());
						currentUser = this.systemService.findUserId(currentUser);
						// 根据用户Id更新用户信息,更新字段：身份证号，姓名，手机，邮件
						User user = new User();
						user.setId(currentUser.getId());
						user.setMobile(enterpriseWorker.getMobile());
						user.setEmail(enterpriseWorker.getEmail());
						user.setOfficeId(enterpriseWorker.getOfficeId());
						if("2".equals(enterpriseWorker.getConfimFlg())){//将confimFlg设置为0
							user.setConfimFlg("0");
							this.systemService.updateConfirmFlg(user);
						}
						this.systemService.updateUserInfoNew01(user);// 更新user对象，更新字段，手机号和邮箱

						// model.addAttribute("message", "修改个人信息成功！");
						addMessage(redirectAttributes, "修改个人信息成功！");
						return "redirect:" + adminPath + "/sys/user/info";// 重定向到info
					} else {// 证件类型不是身份证时，可修改字段：手机号，邮箱，性别，年龄
						if ("男".equals(enterpriseWorker.getSex())) {
							enterpriseWorker.setSex("1");
						} else {
							enterpriseWorker.setSex("2");
						}
						this.enterpriseWorkersService.updateZXSInfo02(enterpriseWorker);
						EnterpriseWorkers enterpriseWorker1 = this.enterpriseWorkersService
								.getNew(enterpriseWorker.getId());// 根据ID获取enterpeise的身份证号和name信息
						// 将身份证号和name添加到user实体中，查询出当前用户id
						currentUser.setCardNumber(enterpriseWorker1.getCertificatesNum());
						currentUser.setName(enterpriseWorker1.getName());
						currentUser = this.systemService.findUserId(currentUser);
						// 根据用户Id更新用户信息,更新字段：身份证号，姓名，手机，邮件，officeid
						User user = new User();
						user.setId(currentUser.getId());
						user.setMobile(enterpriseWorker.getMobile());
						user.setEmail(enterpriseWorker.getEmail());
						user.setOfficeId(enterpriseWorker.getOfficeId());
						if("2".equals(enterpriseWorker.getConfimFlg())){//将confimFlg设置为0
							user.setConfimFlg("0");
							this.systemService.updateConfirmFlg(user);
						}
						this.systemService.updateUserInfoNew01(user);// 更新user对象，更新字段，手机号和邮箱

						// model.addAttribute("message", "修改个人信息成功！");
						addMessage(redirectAttributes, "修改个人信息成功！");
						return "redirect:" + adminPath + "/sys/user/info";// 重定向到info
					}
				}
			}
		}
		return "redirect:" + adminPath + "/sys/user/info";//重定向到info
	}

	
	
	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}

	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "enterpriseModifyPwd")
	public String enterpriseModifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			/*if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/enterpriseModifyPwd";
			}*/
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/enterpriseModifyPwd";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	
	/**
	 * 执业登记情况表
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "registrationForm")
	public String registrationForm(Model model){
		//获取user用户信息，根据身份证和name值到enterprise_workers表中查询个人信息
		User user = UserUtils.getUser();
		EnterpriseWorkers enterpriseWorker = new EnterpriseWorkers();
		enterpriseWorker.setCertificatesNum(user.getCardNumber());
		enterpriseWorker.setName(user.getName());
		enterpriseWorker = this.enterpriseWorkersService.findByCertificatesNumAndName(enterpriseWorker);
		model.addAttribute("enterpriseWorker", enterpriseWorker);
		
		//执业基本情况，执业单位的取得逻辑
		if(enterpriseWorker != null){
			//获取enterprise_workers的id
			String workers_id = enterpriseWorker.getId();
			//根据enterprise_workers的id查询 取得person_record表的数据 ;条件：type=2 and declare_status ！= 20
			PersonRecord personRecord= this.personRecordService.findByPersonId01(workers_id);
			if(personRecord !=null){
				//根据person_record的id到change_item中查找type=1
				ChangeItem changeItem = this.changeItemService.getByRecordId(personRecord.getId(), "1");
				if(changeItem != null){
					model.addAttribute("companyName", changeItem.getOldValue());
				}else{
					model.addAttribute("companyName", "");
				}
			}
		}
		
		//历年执业登记记录
		List<PersonRecord> personRcordeList = this.personRecordService.findByPersonId(enterpriseWorker.getId());
		model.addAttribute("personRcordeList", personRcordeList);
		return "modules/sys/registrationForm";
	}
	
	/**
	 * 中咨协会咨询师列表点击查看跳转到执业登记情况表
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "zRegistrationForm")
	public String zRegistrationForm(String name,String certificatesNum,Model model){
		EnterpriseWorkers enterpriseWorker = new EnterpriseWorkers();
		enterpriseWorker.setCertificatesNum(certificatesNum);
		enterpriseWorker.setName(name);
		enterpriseWorker = this.enterpriseWorkersService.findByCertificatesNumAndName(enterpriseWorker);
		model.addAttribute("enterpriseWorker", enterpriseWorker);
		
		//历年执业登记记录
		List<PersonRecord> personRcordeList = this.personRecordService.findByPersonId(enterpriseWorker.getId());
		model.addAttribute("personRcordeList", personRcordeList);
		return "modules/sys/registrationForm";
	}
	
	
	
	/**===============================工程师系统Start =====================================**/
	/**
	 * 专家数据列表
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:engineerExpert:view")
	@RequestMapping(value = "engineerExpertList")
	public String engineeExpertList(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		//user.setUserType("2");
		user.setUserModel("5");
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/engineerExpertList";
	}
	
	
	
	@RequiresPermissions("sys:engineerExpert:view")
	@RequestMapping(value = "engineerExpertForm")
	public String engineerExpertForm(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
//		model.addAttribute("allRoles", systemService.findAllRole());
        model.addAttribute("allRoles", systemService.findRoleByUser(UserUtils.getUser(),"1"));

		return "modules/sys/engineerExpertForm";
	}
	
	@RequiresPermissions("sys:engineerExpert:edit")
	@RequestMapping(value = "engineerExpertSave")
	public String engineerExpertSave(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes,String allSpecialty) {
		if(allSpecialty!=null&&allSpecialty.equals("1")){
            user = addSpecialty(user);
        }
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("company.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存专家'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = new ArrayList<String>();
		if (request.getParameter("company.id").equals("1"))
		{
			roleIdList.add("12");
		}
		else
		{
			roleIdList.add("91");
		}
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		user.setUserModel("5");
		systemService.saveUser(user);
		
		//BEGIN ADD BY GAOYONGJIAN 20180620 用户发放成功后，发送短信
		/*	String[] args =new String[2];
        String message="";

        args[0]="【中国工程咨询协会】重要误删，专家您好,您在工程咨询单位资信评价管理系统的专家登录登录账号为："+user.getLoginName()+" ,密码为："+user.getNewPassword()+"，请妥善保管。";
        message="尊敬的用户您好：\n\n\t 您在工程咨询单位资信评价管理系统的专家登录登录账号为："+user.getLoginName()+" ,密码为："+user.getNewPassword()+"，请妥善保管。";
        args[1]=user.getMobile();
        SendMessge.maina(args);
        SendMailUtil.sendCommonMail(user.getEmail(), "专家用户发放提醒", message);*/
		//E N D ADD BY GAOYONGJIAN 20180620
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存专家'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/engineerExpertList?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "getPictureName", method = RequestMethod.POST)
	public String getPictureName(@RequestParam(required = true) String workId , HttpServletRequest request,
			HttpServletResponse response) {
		String picName="";
		
		EnterpriseWorkers work = enterpriseWorkersService.get(workId);
		
		
		if (work != null) {
			picName = work.getPictureName();
		} else {
		}
		return picName;
	}

	public User addSpecialty(User user ){
        Dict dict = new Dict();
        dict.setType("specialty_type");
        List<Dict> list = dictService.findList(dict);
        StringBuffer sb = new StringBuffer();
        for(Dict d:list){
            String result = d.getValue();
            sb.append(result);
            sb.append(",");
        }
        String str = sb.toString();
        String specialtys = str.substring(0, str.length() - 1);
        //去掉最后一个逗号
        user.setSpecialtyType(specialtys);
        return user;
	}
	/**===============================工程师系统End =====================================**/

}
