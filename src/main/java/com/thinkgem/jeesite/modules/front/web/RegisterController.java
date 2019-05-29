/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.front.web;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.entity.TempPhoto;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseBaseService;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.enterprise.service.TempPhotoService;
import com.thinkgem.jeesite.modules.sys.entity.Role;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TokenMethod;
import com.thinkgem.jeesite.common.utils.ZxingHandler;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 留言板Controller
 * @author ThinkGem
 * @version 2013-3-15
 */
@Controller
@RequestMapping(value = "/enterprise")
public class RegisterController extends BaseController{
	
	@Autowired
	private SystemService systemService;

	@Autowired
	private EnterpriseBaseService enterpriseBaseService;
	
	@Autowired
	private EnterpriseWorkersService enterpriseWorkersService;
	
	@Autowired
	private TempPhotoService tempPhotoService;
	
	private User user;

	
	/*
	 * 跳转到注册页面
	 */
	@RequestMapping(value = "", method=RequestMethod.GET)
	public String enterprise(@RequestParam(required=false, defaultValue="1") Integer pageNo,
			@RequestParam(required=false, defaultValue="30") Integer pageSize, Model model,EnterpriseWorkers enterpriseWorkers,HttpServletRequest request,HttpServletResponse response) {
		TokenMethod tokenMethod = new TokenMethod();
		String token = tokenMethod.getToken().replace("-", "");
		model.addAttribute("token", token);
		HttpSession session = request.getSession();
		session.setAttribute("token", token);
		return "modules/front/enterprise/enterprise";
	}



	@RequestMapping(value = "overSize")
	public String overSize(@RequestParam(required=false, defaultValue="1") Integer pageNo,
							 @RequestParam(required=false, defaultValue="30") Integer pageSize, Model model) {


		return "error/overSize";
	}




	/**
	 * 添加企业用户基本信息
	 */
	@RequestMapping(value = "", method=RequestMethod.POST)
	public String enterpriseSave(User user, String validateCode, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes ) {

//		System.out.println("用户密码"+user.getPassword());
//		System.out.println("用户身份证"+user.getCardNumberFile());

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
				DiskFileItemFactory factory = new DiskFileItemFactory();  
				// System.out.println(System.getProperty("java.io.tmpdir"));//默认临时文件夹
				// 3.  
				// 调用ServletFileUpload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象。
				@SuppressWarnings("unchecked")  
				String name=user.getLicenseFile().getOriginalFilename();  
				//String cardname=user.getCardNumberFile().getOriginalFilename();
//				System.out.println(name);
				
//				System.out.println("原文件名：" + name);// Koala.jpg  
				String suffix = name.substring(name.lastIndexOf('.'));  
				//String cardsuffix = cardname.substring(cardname.lastIndexOf('.'));
//				System.out.println("扩展名：" + suffix);
				
				// 新文件名（唯一）  
				String newFileName = "Licensefile" + new Date().getTime() + suffix;  
				//String newcardFileName = "Cardfile" + new Date().getTime() + suffix;
//				System.out.println("新文件名：" + newFileName);// image\1478509873038.jpg
				
				// 5. 调用FileItem的write()方法，写入文件

				//系统找不到指定路径写入,需要进行判断
				String path =Global.getUserfilesBaseDir()+"/apply";
//				System.out.println("写入路径"+path);

				
				//File file = new File("./"); 
				
				File file = new File(path);
				//如果没有当前文件夹
				if(!file.exists()){
				//创建新的文件夹
//					System.out.println("文件夹不存在");
					/*file.createNewFile();*/
					file.mkdirs();
				}else {
//					System.out.println("文件夹存在");
				}
//				System.out.println(file.getAbsolutePath());  
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
	
		//E N D add by gaoyongjian 20180410

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
			/*EnterpriseBase base = new EnterpriseBase();
			base.setUser(user);
			//增加
			base.setCompanyName(user.getCompany().getName());

			enterpriseBaseService.save(base);*/
			addMessage(redirectAttributes, "提交成功请耐心等待短信通知，谢谢！");
		return "redirect:/enterprise";
	}


	/**
	 * 验证登录名是否有效
	 * @param loginName
	 * @return
	 */
	@ResponseBody
/*	@RequiresPermissions("cus:cusCustom:edit")*/
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String loginName) {
		if (StringUtils.isBlank(loginName) ) {
			return "false";
		} else {//查询当前sys_user表中是否存在相同登录名
			User user = this.systemService.getUserByLoginName(loginName);
			if(user == null){
				return "true";
			}
		}
		return "false";
	}

	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * 验证统一社会信用代码已经存在
	 * @param organization_code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkOrganizationCode")
	public String checkOrganizationCode(String orgCode) {
		User user = new User();
		user.setOrgCode(orgCode);
		List<User> organizationCodeList = enterpriseBaseService.checkOrganizationCode(user);
		if (organizationCodeList.isEmpty()) {
			return "true";
		}
		return "false";
	}
	
	/**
	 * 添加人员信息时验证身份证号是否已经存在
	 * @param organization_code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "cardNumber")
	public String checkCardNumber(String certificatesNum,String id) {
		EnterpriseWorkers enterprise = new EnterpriseWorkers();
		enterprise.setCertificatesNum(certificatesNum);
		enterprise.setId(id);
		//判断id是否存在，如果存在这是修改模式，如果不存在就是添加模式
		if (id.isEmpty()) {
			List<EnterpriseWorkers> enterpriseWorkList = enterpriseWorkersService.checkCardNumberAdd(enterprise);
			if (enterpriseWorkList.isEmpty()) {
				return "true";
			}
		}else{
			List<EnterpriseWorkers> enterpriseWorkList = enterpriseWorkersService.checkCardNumberUpdate(enterprise);
			if (enterpriseWorkList.isEmpty()) {
				return "true";
			}
		}
		return "false";
	}
	
	

	
	@RequestMapping(value = "download")
	public void download(HttpServletRequest request, HttpServletResponse response, Model model ,String type) {
		/*Page<DeclareRecord> page = declareRecordService.findPage(new Page<DeclareRecord>(request, response), declareRecord);
		model.addAttribute("page", page);*/
		String savePath ="";
		String filename = "";
		if("1".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/1.pdf" ;
			filename ="《工程咨询行业管理办法》(国家发展改革委2017年第9号令).pdf";
		}else if("2".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/2.pdf" ;
			filename ="工程咨询单位资信评价标准.pdf";
		}else if("3".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/3.pdf" ;
			filename ="中国工程咨询协会关于2018年工程咨询单位甲级资信评价有关事项的公告.pdf";
		}else if("4".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/4.pdf" ;
			filename ="2018年工程咨询单位甲级资信申报有关事项的说明.pdf";
		}else if("5".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/5.pdf" ;
			filename ="工程咨询单位甲级资信评价申请表.pdf";
		}else if("6".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/6.pdf" ;
			filename ="系统使用手册.pdf";
		}else if("7".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/7.pdf" ;
			filename ="申报注意事项.pdf";
		}else if("8".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/8.docx" ;
			filename ="专家评审使用说明.docx";
		}else if("9".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/9.docx" ;
			filename ="咨询工程师（投资）修改管理系统有关信息申请表.docx";
		}else if("10".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/10.pdf" ;
			filename ="登记证书（电子版）和执业专用章电子签章上线的通知.pdf";
		}else if("11".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/11.pdf" ;
			filename ="咨询工程师（投资）执业专用章电子签章使用用指南.pdf";
		}
		
		
		
		
		
		try {
			// path是指欲下载的文件的路径。
			File file = new File(savePath);

			// 取得文件名。
			
			// 取得文件的后缀名。
			String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(savePath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();

			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	
	
	/**
	 * 根据身份证号和姓名判断该用户是否已经存在，如果存在返回用户信息到前台
	 * @param certificatesNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findByCertificatesNum")
	public EnterpriseWorkers findByCertificatesNum(String name,String certificatesNum,Model model) {
		String strName = StringEscapeUtils.unescapeHtml4(name);
		EnterpriseWorkers enterprise = new EnterpriseWorkers();
		enterprise.setName(strName);
		enterprise.setCertificatesNum(certificatesNum);
		//判断id是否存在，如果存在这是修改模式，如果不存在就是添加模式
		if (certificatesNum.isEmpty() && name.isEmpty()) {
			//为空时直接返回该注册页面
			return enterprise;
		}else{
			//List<EnterpriseWorkers> enterpriseWorkList = enterpriseWorkersService.checkCardNumberUpdate(enterprise);
			EnterpriseWorkers enterpriseWork = enterpriseWorkersService.findByCertificatesNum(enterprise);
			return enterpriseWork;
		}
	}
	
	/**
	 * 单位名称窗口
	 * @param certificatesNum
	 * @return
	 * resultType="java.util.List"
	 */
	@RequestMapping(value="companyNameWidow")
	public String companyNameWidow(String flag,User user,Model model,HttpServletRequest request,HttpServletResponse response) {
		return "modules/front/enterprise/companyNameListWindow";
	}
	
	/**
	 * 单位名称窗口
	 * @param certificatesNum
	 * @return
	 * resultType="java.util.List"
	 */
	@RequestMapping(value="companyNameWidowSearch")
	public String companyNameWidowSearch(String flag,User user,Model model,HttpServletRequest request,HttpServletResponse response) {
		List<User> companyNameList = this.systemService.findSysUserName(user);
		model.addAttribute("page", companyNameList);
		return "modules/front/enterprise/companyNameListWindow";
	}
	/**
	 * 注册
	 * @param certificatesNum
	 * @return
	 * resultType="java.util.List"
	 */
	@RequestMapping(value="register")
	public String register(EnterpriseWorkers enterpriseWorks,String token,Model model,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		//HTML特殊转义字符
		String unescapeHtml4 = StringEscapeUtils.unescapeHtml4(enterpriseWorks.getName());
		enterpriseWorks.setName(unescapeHtml4);
		//--------------HTML特殊转义字符-------------
		String correctToken  = (String)request.getSession().getAttribute("token");
		System.out.println("注册表单中的token："+token);
		System.out.print("session中的token:"+correctToken);
		if (StringUtils.isBlank(token) || !token.equals(correctToken)) {
			System.out.println("注册表中的token与session中的token不一致，重复注册");
			//清空session中的token
			request.getSession().removeAttribute("token");
			return "modules/front/enterprise/error";
		}else{
			if(enterpriseWorks == null){
				addMessage(redirectAttributes, "数据为空");
				request.getSession().removeAttribute("token");
				return "modules/front/enterprise/enterprise";
			}else{
				User userlogin = this.systemService.getUserByLoginName(enterpriseWorks.getLoginName());
				if (userlogin != null){
					addMessage(redirectAttributes, "注册失败，该用户名已存在");
					return "modules/front/enterprise/enterprise";
				}				

				if("登记且有效".equals(enterpriseWorks.getIsValid()) && "0".equals(enterpriseWorks.getIsRegister())){
					//根据名字和身份号查询该用户是否已存在
					EnterpriseWorkers findResult = this.enterpriseWorkersService.findByNameAndCertificatesNum(enterpriseWorks);
					if(findResult == null){
						User user = new User();
						user.preInsert();
						user.setUserType("1");
						user.setConfimFlg("1");
						user.setLoginFlag("1");
						user.setDelFlag("0");
						user.setLoginName(enterpriseWorks.getLoginName());
						user.setEmail(enterpriseWorks.getEmail());
						user.setName(enterpriseWorks.getName());
						user.setOfficeId(enterpriseWorks.getOfficeId());
						user.setCardNumber(enterpriseWorks.getCertificatesNum());
						user.setPassword(SystemService.entryptPassword(enterpriseWorks.getPassword()));
						user.setMobile(enterpriseWorks.getMobile());
						user.setUserFrom("1");//咨询工程师系统
						this.enterpriseWorkersService.savaCounselorToUser(user);
						//更新更新Enterpres_works表里的isregister=1（已注册）
						enterpriseWorks.setIsRegister("1");
						enterpriseWorks.setAge(getAge(enterpriseWorks.getCertificatesNum()));//add by gaoyongjian 增加年龄
						this.enterpriseWorkersService.updateIsRegister(enterpriseWorks);
						//赋予当前注册用户权限
						Role role = new Role();
						role.setUser(user);
						role.setId("9");
						this.systemService.addRoleByUser(role);
						model.addAttribute("loginName", enterpriseWorks.getLoginName());
						//清空session
						request.getSession().removeAttribute("token");
						return "modules/front/enterprise/success";
					}else{
						//清空session
						request.getSession().removeAttribute("token");
						addMessage(redirectAttributes, "注册失败，该用户已存在");
						return "modules/front/enterprise/enterprise";
					}
				}else if("登记过但无效".equals(enterpriseWorks.getIsValid()) && "0".equals(enterpriseWorks.getIsRegister())){
					//根据名字和身份号查询该用户是否已存在
					EnterpriseWorkers findResult = this.enterpriseWorkersService.findByNameAndCertificatesNum(enterpriseWorks);
					if(findResult == null){
						User user = new User();
						user.preInsert();
						user.setUserType("1");
						user.setConfimFlg("0");
						user.setLoginFlag("1");
						user.setDelFlag("0");
						user.setLoginName(enterpriseWorks.getLoginName());
						user.setEmail(enterpriseWorks.getEmail());
						user.setName(enterpriseWorks.getName());
						user.setOfficeId(enterpriseWorks.getOfficeId());
						user.setCardNumber(enterpriseWorks.getCertificatesNum());
						user.setPassword(SystemService.entryptPassword(enterpriseWorks.getPassword()));
						user.setMobile(enterpriseWorks.getMobile());
						user.setUserFrom("1");//咨询工程师系统
						this.enterpriseWorkersService.savaCounselorToUser(user);
						//更新更新Enterpres_works表里的isregister=1（已注册）
						enterpriseWorks.setIsRegister("1");
						enterpriseWorks.setAge(getAge(enterpriseWorks.getCertificatesNum()));//add by gaoyongjian 增加年龄
						this.enterpriseWorkersService.updateIsRegister(enterpriseWorks);
						//给该专家赋予权限
						Role role = new Role();
						role.setUser(user);
						role.setId("92");
						this.systemService.addRoleByUser(role);
						model.addAttribute("loginName", enterpriseWorks.getLoginName());
						//清空session
						request.getSession().removeAttribute("token");
						return "modules/front/enterprise/success";
					}else{
						//清空session
						request.getSession().removeAttribute("token");
						addMessage(redirectAttributes, "注册失败，该用户已存在");
						return "modules/front/enterprise/enterprise";
					}
				}else if("未申请登记".equals(enterpriseWorks.getIsValid()) && "".equals(enterpriseWorks.getIsRegister())){
					
					EnterpriseWorkers oldWorkers = this.enterpriseWorkersService.getByNameNum("", enterpriseWorks.getCertificatesNum());
					if (oldWorkers != null){
						addMessage(redirectAttributes, "注册失败，该身份证号已存在");
						return "modules/front/enterprise/enterprise";
					}
					
					TempPhoto tempPhoto = this.tempPhotoService.findByCardNum(enterpriseWorks.getCertificatesNum());
					if(tempPhoto != null && !tempPhoto.getName().equals(enterpriseWorks.getName())){
						//清空session
						request.getSession().removeAttribute("token");
						addMessage(redirectAttributes, "注册失败");
						return "modules/front/enterprise/enterprise";
					}else{
						//根据名字和身份号查询该用户是否已存在
						EnterpriseWorkers findResult = this.enterpriseWorkersService.findByNameAndCertificatesNum(enterpriseWorks);
						if(findResult == null){
							User user = new User();
							user.preInsert();
							user.setUserType("1");
							user.setConfimFlg("0");
							user.setLoginFlag("1");
							user.setDelFlag("0");
							user.setLoginName(enterpriseWorks.getLoginName());
							user.setEmail(enterpriseWorks.getEmail());
							user.setName(enterpriseWorks.getName());
							user.setOfficeId(enterpriseWorks.getOfficeId());
							user.setCardNumber(enterpriseWorks.getCertificatesNum());
							user.setPassword(SystemService.entryptPassword(enterpriseWorks.getPassword()));
							user.setMobile(enterpriseWorks.getMobile());
							user.setUserFrom("1");//咨询工程师系统
							this.enterpriseWorkersService.savaCounselorToUser(user);
							//更新更新Enterpres_works表里的isregister=1（已注册）
							enterpriseWorks.preInsert();
							enterpriseWorks.setIsRegister("1");
							enterpriseWorks.setIsValid("2");//还未申请登记
							//如果证件类型是身份证，根据身份证号判断性别和年龄
							String certificatesNum = enterpriseWorks.getCertificatesNum();
							if ("1".equals(enterpriseWorks.getCertificatesType()) && certificatesNum.length() == 18) {
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
									enterpriseWorks.setAge(year - year2);
									if(Integer.parseInt(sex)%2==0){
										enterpriseWorks.setSex("2");
									}else{
										enterpriseWorks.setSex("1");
									}
								} catch (Exception e) {
									//							System.out.println("a problem has happened!");
								}
							}
							//取得考试数据中的一寸照片地址、一寸照片名称、取得年份、证书编号
							EnterpriseWorkers tempphoto = this.enterpriseWorkersService.getTempPhoto(enterpriseWorks);
							if (tempphoto != null)
							{
								enterpriseWorks.setPictureUrl(tempphoto.getPictureUrl());
								enterpriseWorks.setGetyear(tempphoto.getGetyear());
								enterpriseWorks.setPictureName(tempphoto.getPictureName());
								enterpriseWorks.setProfessioncardNum(tempphoto.getProfessioncardNum());
							}
							this.enterpriseWorkersService.insertZXS(enterpriseWorks);
							//给该专家赋予权限
							Role role = new Role();
							role.setUser(user);
							role.setId("92");
							this.systemService.addRoleByUser(role);
							model.addAttribute("loginName", enterpriseWorks.getLoginName());
							
							//生成用户显示基本信息的二维码
							try {
								String contents=Global.getEngineerBaseInfoUrl()+user.getId();
								int width= Global.getQRCodeWidth();
								int height =Global.getQRCodeHeight();
								//String  imgPath=Global.getUserfilesBaseDir()+Global.ENGINEER_BASE_URL+Global.ENGINEER_QR_CODE_URL;
								
								//工程师附件基础
								String engineerBaseDir = Global.getUserfilesBaseDir()+Global.ENGINEER_BASE_URL+Global.ENGINEER_QR_CODE_URL;
								File dir = new File(engineerBaseDir);
								//System.out.println(engineerBaseDir);
								if(!dir.exists())//目录不存在则创建
									dir.mkdirs();
								
								String imgPath = FileUtils.path(engineerBaseDir+user.getId());
								//System.out.println(imgPath);
								
								ZxingHandler.encode2(contents, width, height, imgPath);
							} catch (Exception e) {
								//						System.out.println("生成用户二维码失败！");
							}
							//清空session
							request.getSession().removeAttribute("token");
							return "modules/front/enterprise/success";
						}else{
							//清空session
							request.getSession().removeAttribute("token");
							addMessage(redirectAttributes, "注册失败，该用户已存在");
							return "modules/front/enterprise/enterprise";
						}
					}
				}else if("未申请登记".equals(enterpriseWorks.getIsValid()) && "0".equals(enterpriseWorks.getIsRegister())){//个人用户删除后再注册
					TempPhoto tempPhoto = this.tempPhotoService.findByCardNum(enterpriseWorks.getCertificatesNum());
					if(tempPhoto != null && !tempPhoto.getName().equals(enterpriseWorks.getName())){
						//清空session
						request.getSession().removeAttribute("token");
						addMessage(redirectAttributes, "注册失败");
						return "modules/front/enterprise/enterprise";
					}else{
						//根据名字和身份号查询该用户是否已存在
						EnterpriseWorkers findResult = this.enterpriseWorkersService.findEnterpriseWorkers(enterpriseWorks);
						if(findResult != null && "2".equals(findResult.getIsValid())){
							User user = new User();
							user.preInsert();
							user.setUserType("1");
							user.setConfimFlg("0");
							user.setLoginFlag("1");
							user.setDelFlag("0");
							user.setLoginName(enterpriseWorks.getLoginName());
							user.setEmail(enterpriseWorks.getEmail());
							user.setName(enterpriseWorks.getName());
							user.setOfficeId(enterpriseWorks.getOfficeId());
							user.setCardNumber(enterpriseWorks.getCertificatesNum());
							user.setPassword(SystemService.entryptPassword(enterpriseWorks.getPassword()));
							user.setMobile(enterpriseWorks.getMobile());
							user.setUserFrom("1");//咨询工程师系统
							this.enterpriseWorkersService.savaCounselorToUser(user);
							//更新更新Enterpres_works表里的isregister=1（已注册）
							enterpriseWorks.setIsRegister("1");
							this.enterpriseWorkersService.updateEnterpriseWorkers(enterpriseWorks);
							//给该专家赋予权限
							Role role = new Role();
							role.setUser(user);
							role.setId("92");
							this.systemService.addRoleByUser(role);
							model.addAttribute("loginName", enterpriseWorks.getLoginName());
							
							//生成用户显示基本信息的二维码
							try {
								String contents=Global.getEngineerBaseInfoUrl()+user.getId();
								int width= Global.getQRCodeWidth();
								int height =Global.getQRCodeHeight();
								//String  imgPath=Global.getUserfilesBaseDir()+Global.ENGINEER_BASE_URL+Global.ENGINEER_QR_CODE_URL;
								
								//工程师附件基础
								String engineerBaseDir = Global.getUserfilesBaseDir()+Global.ENGINEER_BASE_URL+Global.ENGINEER_QR_CODE_URL;
								File dir = new File(engineerBaseDir);
								//System.out.println(engineerBaseDir);
								if(!dir.exists())//目录不存在则创建
									dir.mkdirs();
								
								String imgPath = FileUtils.path(engineerBaseDir+user.getId());
								//System.out.println(imgPath);
								
								ZxingHandler.encode2(contents, width, height, imgPath);
							} catch (Exception e) {
								//						System.out.println("生成用户二维码失败！");
							}
							//清空session
							request.getSession().removeAttribute("token");
							return "modules/front/enterprise/success";
						}else{
							//清空session
							request.getSession().removeAttribute("token");
							addMessage(redirectAttributes, "注册失败，该用户已存在");
							return "modules/front/enterprise/enterprise";
						}
					}
				}
			}
		}
		//清空session
		request.getSession().removeAttribute("token");
		addMessage(redirectAttributes, "注册失败，请重新注册");
		return "modules/front/enterprise/enterprise";
	}
	public int getAge(String certificatesNum)
	{
		int age=0;
		if (certificatesNum.length() == 18) {
			String yy1 = certificatesNum.substring(6, 10); // 出生的年份
			String mm1 = certificatesNum.substring(10, 12); // 出生的月份
			String dd1 = certificatesNum.substring(12, 14); // 出生的日期
			String birthday = yy1.concat("-").concat(mm1).concat("-").concat(dd1);
			// 计算年龄
			try {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
				String s1 = sdf.format(date);
				Date today = sdf.parse(s1);
				Date birth = sdf.parse(birthday);
				int year = today.getYear();
				int year2 = birth.getYear();
				age = year - year2;
			} catch (Exception e) {
				e.printStackTrace();
				//System.out.println("a problem has happened!");
			}
		}
		return age;
	}
	/**
	 * 验证name是否已经存在
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "checkName")
	public String checkName(String name) {
		if (name !=null) {
			Integer count = this.enterpriseWorkersService.findName(name);
			if (count.SIZE > 0) {
				return "false";
			}
		}
		return "false";
	}
	
	/**
	 * 删除个人信息
	 */
	@ResponseBody
	@RequestMapping(value = "deleteInfo")
	public void deleteInfo(@RequestParam(required = false) String cardNumber,
			@RequestParam(required = false) String name, 
			@RequestParam(required = false) String workerId,
			HttpServletRequest request, HttpServletResponse response) {
		// 创建一个flag,记录删除是否成功，成功返回true
		String flag = "true";
		//根据身份证号和name查询用户信息
		User user = new User();
		user.setCardNumber(cardNumber);
		user.setName(name);
		User userId = this.systemService.findUserId(user);
		//修改worker表中的isRegister为未注册
		EnterpriseWorkers enterpriseWorkers = new EnterpriseWorkers();
		enterpriseWorkers.setId(workerId);
		enterpriseWorkers.setIsRegister("0");
		this.enterpriseWorkersService.updateIsRegister1(enterpriseWorkers);
		//根据userId修改当前用户在sys_user表中del_flag的信息
		String deleteInfo = this.systemService.deleteInfo(userId);
		if (deleteInfo.equals("false")) {
			flag = "false";
		}else{
			flag = "true";
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
	 * 验证手机号是否有效
	 * @param loginName
	 * @return
	 */
	@ResponseBody
/*	@RequiresPermissions("cus:cusCustom:edit")*/
	@RequestMapping(value = "checkMobile",method=RequestMethod.POST)
	public String checkMobile(@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "id", required = false) String workerId) {
		if (StringUtils.isBlank(mobile)) {
			return "false";
		} else {//查询当前sys_user表中是否存在相同登录名
			User user = new User();
			user.setMobile(mobile);
			user.setId(workerId);
			List<User> userList = this.enterpriseWorkersService.checkMobile(user);
			if(userList == null || userList.size()<1){
				return "true";
			}
		}
		return "false";
	}
	/**
	 * 验证身份号是否已注册
	 * @param checkCertificatesNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkCertificatesNum",method=RequestMethod.POST)
	public String checkCertificatesNum(@RequestParam(value = "certificatesNum", required = false)String certificatesNum) {
		EnterpriseWorkers enterpriseWorkers = new EnterpriseWorkers();
		enterpriseWorkers.setCertificatesNum(certificatesNum);
		enterpriseWorkers.setIsRegister("1");
		List<EnterpriseWorkers> list = this.enterpriseWorkersService.checkCertificatesNumisRegister(enterpriseWorkers);
		if (list != null && list.size()>0) {
			return "false";
		}else{
			return "true";
		}
	}
	
	
	/**
	 * 根据身份证判断当前用户是否存在，如果存在获取用户名，判断数据库中的用户名与用户输入的用户名是否相同，如果不同，不允许注册，提示身份证号与姓名不匹配，请联系010-68353661
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkNameEqualDBName",method=RequestMethod.POST)
	public String checkNameEqualDBName(@RequestParam(value = "certificatesNum", required = false) String certificatesNum,
			@RequestParam(value = "name", required = false) String name) {
		String unescapeHtml4 = StringEscapeUtils.unescapeHtml4(name);
		EnterpriseWorkers enterpriseWorkers = new EnterpriseWorkers();
		enterpriseWorkers.setCertificatesNum(certificatesNum);
		enterpriseWorkers.setIsRegister("1");
		List<EnterpriseWorkers> list = this.enterpriseWorkersService.checkCertificatesNum(enterpriseWorkers);
		if (list != null && list.size()>0) {
			//判断当前用户名与数据库名是否相同，如果相同，返回true，否则返回false
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getName().equals(unescapeHtml4)){
					return "true";
				}else{
					return "false";
				}
			}
			return "false";
		}else{
			return "true";
		}
	}
	
}
