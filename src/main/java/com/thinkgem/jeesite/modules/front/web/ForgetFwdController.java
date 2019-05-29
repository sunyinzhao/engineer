/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.front.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.utils.SendMessge;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.service.GuestbookService;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 留言板Controller
 * @author ThinkGem
 * @version 2013-3-15
 */
@Controller
@RequestMapping(value = "/forgetPwd")
public class ForgetFwdController extends BaseController{
	
	@Autowired
	private GuestbookService guestbookService;
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private EnterpriseWorkersService enterpriseWorkersService;
	
	private User user;
	
	
	/**
	 * 跳转到忘记密码页面
	 * @param pageNo
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method=RequestMethod.GET)
	public String forgetPwd(@RequestParam(required=false, defaultValue="1") Integer pageNo,
			@RequestParam(required=false, defaultValue="30") Integer pageSize,String flag, Model model) {
		if(flag == null){
			return "modules/front/forgetPwd/forgetPwd";
		}else if(flag.equals("1")){
			model.addAttribute("Reviseresult", "修改密码成功");
			return "modules/front/forgetPwd/pwdReviseResult";
		}else{
			model.addAttribute("Reviseresult", "修改密码失败");
			return "modules/front/forgetPwd/pwdReviseResult";
		}
	}
	
	
	
	/**
	 * 忘记密码
	 */
	@RequestMapping(value = "", method=RequestMethod.POST)
	public String forgetPwdSave(User user, String validateCode, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String flag = "0";//判断修改密码是否成功
		try{
			//User user1 =new User();	
			//user1= this.enterpriseWorkersService.getLoginName(user.getLoginName());
			List<User> userList = this.systemService.getCardNumber(user.getCardNumber());
			if( userList != null && userList.size()>0){
				systemService.updatePasswordById(userList.get(0).getId(), userList.get(0).getLoginName(), user.getNewPassword());
				addMessage(redirectAttributes, "密码已修改，请牢记，谢谢！");
				flag = "1";
			}else{
				addMessage(redirectAttributes, "密码修改失败，请重新输入");
				flag = "0";
			}
		}catch(Exception e){
			flag = "0";
		}
		return "redirect:/forgetPwd?flag="+flag;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}

	// 校检当前申请单中的客户的申请专业与其他表单中的申请专业是否相同
	@ResponseBody
	@RequestMapping(value = "checkcodenumInfo", method = RequestMethod.POST)
	public String checkcodenumInfo(@RequestParam(required = false) String name,@RequestParam(required = false) String certificatesNum,
			@RequestParam(required = false) String checkcode, HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";
		// 创建申请单对象
		User user1 = new User();
		user1.setCardNumber(certificatesNum);
		user1.setName(name);
		user1 = this.systemService.findUserId(user1);
		String[] args = new String[2];

		if (user1 != null) {
			args[0] = "【中国工程咨询协会】尊敬的用户您好,您修改的手机号的验证是" + checkcode + ",请在个人信息页面数输入。";
			args[1] = user1.getMobile();
			SendMessge.maina(args);
			result = "1";
		} else {
			result = "0";
		}
		return result;
	}
	
	// 校检当前申请单中的客户的申请专业与其他表单中的申请专业是否相同
		@ResponseBody
		@RequestMapping(value = "checkcodenum", method = RequestMethod.POST)
		public String checkcodenum(@RequestParam(required = false) String cardNumber,
				@RequestParam(required = false) String checkcode, HttpServletRequest request,
				HttpServletResponse response) {
			String result = "";
			// 创建申请单对象
			//User user1 = new User();
			//user1 = enterpriseWorkersService.getLoginName(loginname);
			
			
			List<User> userList = this.systemService.getCardNumber(cardNumber);
			String[] args = new String[2];
			if(userList != null && userList.size()>0){
				args[0] = "【中国工程咨询协会】尊敬的用户您好,您修改密码的验证是" + checkcode + ",请在找回密码页面数输入。";
				args[1] = userList.get(0).getMobile();
				SendMessge.maina(args);
				result = "1";
			}else{
				result = "0";
			}
			return result;
		}
}
