/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;


import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseBase;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseBaseService;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SendMailUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.common.utils.SendMessge;

/**
 * 企业用户管理
 * @author xqgxie
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/enterprise")
public class UserEnterpriseController extends BaseController {

	@Autowired
	private SystemService systemService;

	@Autowired
	private EnterpriseBaseService enterpriseBaseService;

	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	/**
	 * 查看编辑企业基本信息
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/enterpriseIndex";
	}


	/**
	 * 待审核企业数据
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:enterprise:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		//user.setUserType("7");
		user.setUserModel("4");
		user.setConfimFlg("1");
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/enterpriseList";
	}

	@ResponseBody
	@RequiresPermissions("sys:enterprise:view")
	@RequestMapping(value = {"listData"})
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		return page;
	}

	@RequiresPermissions("sys:enterprise:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/enterpriseForm";
	}

    /**
     * 用于显示审核失败的企业进行修改信息
     * @param user
     * @param model
     * @return
     */
    @RequiresPermissions("enterprise:enterpriseBase:edit")
    @RequestMapping(value = "baseForm")
    public String baseForm(User user, Model model) {

        user = systemService.getUser(UserUtils.getUser().getId());
        if (user.getCompany()==null || user.getCompany().getId()==null){
            user.setCompany(UserUtils.getUser().getCompany());
        }
        if (user.getOffice()==null || user.getOffice().getId()==null){
            user.setOffice(UserUtils.getUser().getOffice());
        }
        model.addAttribute("user", user);

        return "modules/sys/enterpriseInitBaseForm";
    }




    /**
     * 用于显示审核失败的企业进行修改信息
     */
    @RequestMapping(value = "enterpriseSave")
    public String enterpriseSave(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {


        Office off= user.getCompany();
        user.setOffice(off);
        user.setUserType("7"); //企业用户
        user.setUserModel("4"); //企业用户
        user.setLoginFlag("0");//不可登录
        user.setConfimFlg("0");//未审核通过


        //begin add by gaoyongjian 20180410
        File targetFile=null;

        try {
            // 1. 创建DiskFileItemFactory对象，设置缓冲区大小和临时文件目录
            String name=user.getLicenseFile().getOriginalFilename();
            String suffix = name.substring(name.lastIndexOf('.'));
            // 新文件名（唯一）
            String newFileName = "Licensefile" + new Date().getTime() + suffix;
            // 5. 调用FileItem的write()方法，写入文件
            //系统找不到指定路径写入,需要进行判断
            String path =Global.getUserfilesBaseDir()+"/apply";
//            System.out.println("写入路径"+path);
            File file = new File(path);
            //如果没有当前文件夹
            if(!file.exists()){
                //创建新的文件夹
//                System.out.println("文件夹不存在");
                file.mkdir();
            }else {
//                System.out.println("文件夹存在");
            }
//            System.out.println(file.getAbsolutePath());
            user.setLicensePath(newFileName);
            targetFile =new File(file,newFileName);
            user.getLicenseFile().transferTo(targetFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(SystemService.entryptPassword(user.getPassword()));
        }
        // 角色数据有效性验证，过滤不在授权内的角色
        List<Role> roleList = Lists.newArrayList();
        String roleId = "90";
        for (Role r : systemService.findAllRole()){
            if (roleId.equals(r.getId())){
                roleList.add(r);
            }
        }
        user.setRoleList(roleList);
        systemService.saveUser(user);
        addMessage(redirectAttributes, "提交成功请耐心等待短信通知，谢谢！");
        return "redirect:/enterprise";
    }

    /**
	 * 保存企业基本数据
	 * @param user
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:enterprise:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

/*
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		if(user.getCompany()==null || StringUtils.isBlank(user.getCompany().getId())){
			user.setCompany(new Office(request.getParameter("company.id")));

		}
		if(user.getOffice() ==null || StringUtils.isBlank(user.getOffice().getId())){
			user.setOffice(new Office(request.getParameter("office.id")));
		}

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
		systemService.updateUserConfirmFlg(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
		//	UserUtils.getCacheMap().clear();
		}*/

        systemService.updateEnterpriseUser(user);
        UserUtils.clearCache(user);

        addMessage(redirectAttributes, "修改企业信息'" + user.getLoginName() + "'成功");
        return "redirect:" + adminPath + "/sys/enterprise/list";




	}

	/*
	 * 读本地的图片文件,展示在前台
	 * */
	@RequestMapping(value="id/{id}",method = RequestMethod.GET)
	public String showImg(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		/*ServletOutputStream out = null;
		FileInputStream ips = null;*/
		InputStream inputStream = null;
		OutputStream outputStream=null;
		try {

			//1.取得原文件
			String fileBasePath =Global.getUserfilesBaseDir()+"/apply/";
			//根据id查询到本条user的记录
			String filePath = systemService.getUser(id).getLicensePath();
			String path = fileBasePath+filePath;
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

			//2.设置response;
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream");
			response.setContentType("multipart/form-data");
			//out = response.getOutputStream();


			//3. 以流的形式读取文件。
			inputStream = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			inputStream.close();

			//4.将数据写出
			outputStream = new BufferedOutputStream(response.getOutputStream());
			outputStream.write(buffer);
			outputStream.flush();
			outputStream.close();

		}catch (FileNotFoundException  e){
			e.printStackTrace();
//			System.out.println("企业营业执照图片未找到！");
		}finally{
			if (outputStream!=null){
				outputStream.close();
			}
			if(inputStream!=null){
				inputStream.close();
			}

		}
		return null;
	}


	@RequiresPermissions("sys:enterprise:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
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
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:enterprise:view")
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
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}


	//-----------------------------------------******************************-------------------------------------

	/**
	 * 待审企业基本信息
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"approIndex"})
	public String approvalIndex(User user, Model model) {
		return "modules/sys/enterpriseApproIndex";
	}


	/**
	 * 待审核企业数据
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:enterprise:view")
	@RequestMapping(value = {"approList", ""})
	public String approList(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		//user.setUserType("7");
		user.setUserModel("4");
		user.setConfimFlg("0");
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/enterpriseApproList";
	}
	
	/**
	 * 退回企业数据
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:enterprise:view")
	@RequestMapping(value = {"returnList", ""})
	public String returnList(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setUserModel("4");
		user.setConfimFlg("2");
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/enterpriseApproReturnList";
	}
	
	@RequiresPermissions("sys:enterprise:view")
	@RequestMapping(value = "approReturnForm")
	public String approReturnForm(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/enterpriseApproReturnForm";
	}

	@ResponseBody
	@RequiresPermissions("sys:enterprise:view")
	@RequestMapping(value = {"approListData"})
	public Page<User> approListData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		return page;
	}

	@RequiresPermissions("sys:enterprise:view")
	@RequestMapping(value = "approForm")
	public String approForm(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/enterpriseApproForm";
	}

	/**
	 * 审核企业用户
	 * @param user
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:enterprise:view")
	@RequestMapping(value = "approSave")
	public String approSave(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {


        // 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
        if(user.getCompany()==null || StringUtils.isBlank(user.getCompany().getId())){
            user.setCompany(new Office(request.getParameter("company.id")));

        }
        if(user.getOffice() ==null || StringUtils.isBlank(user.getOffice().getId())){
            user.setOffice(new Office(request.getParameter("office.id")));
        }

        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {
            //user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
        	user.setPassword(user.getNewPassword());
        }
        if (!beanValidator(model, user)){
            return form(user, model);
        }
        if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
            addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return form(user, model);
        }

        // 为企业用户添加角色
        List<Role> roleList = Lists.newArrayList();
        String roleId = "8";//系统角色为企业用户

        //设置用户登录权限
        if(user.getConfimFlg().equals("1")){ //通过审核
            user.setLoginFlag("1"); //可以登录
			//通过审核的时候增加base
			EnterpriseBase base =new EnterpriseBase();
			base.setUser(user);
			//企业名称
			base.setCompanyName(user.getName());
			//机构代码
			base.setOrganizationCode(user.getOrgCode());
			//备案编号
			base.setApplicationCode(user.getRecordNumber());
			//企业类型
			base.setComanpyType(user.getCompany().getType());
			//base.setEstablishmentDate(user.set);
			//base.setMasterComanpy();
			//注册地址
			base.setRegisterAddress(user.getRegisterAddress());
			//通讯地址
			base.setPostalAddress(user.getAddress());
			//法人代表
			base.setLegalPerson(user.getLegalPerson());
			//电话
			base.setPhone(user.getPhone());
			//手机
			base.setMobile(user.getMobile());
			//邮箱
			//备注
			enterpriseBaseService.save(base);

        }else{
           // user.setLoginFlag("0"); //不可登录
           // user.setDelFlag("1");   //删除
            roleId = "90";//系统角色为初始企业用户
        }


        //List<String> roleIdList = user.getRoleIdList();

        for (Role r : systemService.findAllRole()){
            if (roleId.equals(r.getId())){
                roleList.add(r);
            }
        }
        user.setRoleList(roleList);

        user.setLoginFlag("1");//都可以登录
        // 保存用户信息
        systemService.saveUser(user);
        systemService.updateUserConfirmFlg(user);

        String[] args =new String[2];
        //args[0]="N166466_N6302264";
        //args[1]="oxy02hmr8H7043";
        //args[2]=Global.getConfig("sendmessageurl");
        //args[2]="http://smssh1.253.com/msg/send/json";
        String message="";
        if(user.getConfimFlg().equals("1")) 
        {
        	args[0]="【中国工程咨询协会】用户您好,您在工程咨询单位资信评价管理系统申请的账户已审批通过。";
        	message="尊敬的用户您好：\n\n\t 您在工程咨询单位资信评价管理系统申请的用户已通过审批，可登录系统进行资信申请。";
        }
        else
        {
        	args[0]="【中国工程咨询协会】用户您好,您在工程咨询单位资信评价管理系统申请的账户未审批通过，请及时登录统一权限系统进行信息维护以便尽快通过审核。";
        	message="尊敬的用户您好：\n\n\t 您在工程咨询单位资信评价管理系统申请的用户经管理员审核未通过，请登录统一权限系统查看审批意见。";
        }
        args[1]=user.getMobile();
        SendMessge.maina(args);
        
        SendMailUtil.sendCommonMail(user.getEmail(), "用户注册审批提醒", message);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
            UserUtils.clearCache();
        }
        addMessage(redirectAttributes, "审核'" + user.getLoginName() + "'成功!已短信通知单位用户。");
        return "redirect:" + adminPath + "/sys/enterprise/approList?repage";
	}

	//修改申请信息

	@RequestMapping(value = "editUser")
	public String editUser(User user,RedirectAttributes redirectAttributes){
		//需要把图片流上传上去,当选择新的图片时需要通过流上传


		Office off= user.getCompany();
		user.setOffice(off);
		user.setUserType("7"); //企业用户
		user.setUserModel("4"); //企业用户
		user.setLoginFlag("0");//不可登录
		user.setConfimFlg("0");//未审核通过
		//BEGIN ADD BY GAOYONGJIAN 20180613
        // 如果新密码为空，则不更换密码
        /*if (StringUtils.isNotBlank(user.getNewPassword())) {
            user.setPassword(user.getNewPassword());
        }*/
        //E N D ADD BY GAOYONGJIAN 20180613
		if(user.getLicenseFile()!=null&&user.getLicenseFile().getOriginalFilename()!=""){


		//begin add by gaoyongjian 20180410
		File targetFile=null;

		try {
			// 1. 创建DiskFileItemFactory对象，设置缓冲区大小和临时文件目录
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// System.out.println(System.getProperty("java.io.tmpdir"));//默认临时文件夹
			// 3.
			// 调用ServletFileUpload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象。

			String name=user.getLicenseFile().getOriginalFilename();

//			System.out.println("原文件名：" + name);// Koala.jpg
			String suffix = name.substring(name.lastIndexOf('.'));
			//String cardsuffix = cardname.substring(cardname.lastIndexOf('.'));
//			System.out.println("扩展名：" + suffix);

			// 新文件名（唯一）
			String newFileName = "Licensefile" + new Date().getTime() + suffix;
			//String newcardFileName = "Cardfile" + new Date().getTime() + suffix;
//			System.out.println("新文件名：" + newFileName);// image\1478509873038.jpg

			// 5. 调用FileItem的write()方法，写入文件

			//系统找不到指定路径写入,需要进行判断
			String path =Global.getUserfilesBaseDir()+"/apply";
//			System.out.println("写入路径"+path);


			//File file = new File("./");

			File file = new File(path);
			//如果没有当前文件夹
			if(!file.exists()){
				//创建新的文件夹
//				System.out.println("文件夹不存在");
				/*file.createNewFile();*/
				file.mkdir();
			}else {
//				System.out.println("文件夹存在");
			}
//			System.out.println(file.getAbsolutePath());
			user.setLicensePath(newFileName);
			//user.setCardNumberPath(newcardFileName);
			targetFile =new File(file,newFileName);
			user.getLicenseFile().transferTo(targetFile);
			//targetFile =new File(file,newcardFileName);
			//user.getCardNumberFile().transferTo(targetFile);


		}
		catch (Exception e) {
			e.printStackTrace();
		}

		}
		//E N D add by gaoyongjian 20180410

//		if (StringUtils.isNotBlank(user.getPassword())) {
//			user.setPassword(SystemService.entryptPassword(user.getPassword()));
//
//		}


		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		String roleId = "90";
		for (Role r : systemService.findAllRole()){
			if (roleId.equals(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		systemService.saveUser(user);
			/*EnterpriseBase base = new EnterpriseBase();
			base.setUser(user);
			//增加
			base.setCompanyName(user.getCompany().getName());
			enterpriseBaseService.save(base);*/
		addMessage(redirectAttributes, "提交成功请耐心等待短信通知，谢谢！");

		return "redirect:"+adminPath+"/sys/enterprise/baseForm";
	}

}
