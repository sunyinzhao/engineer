/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.web.LoginController;

/**
 * 系统安全认证实现类
 * @author ThinkGem
 * @version 2014-7-5
 */
@Service
//@DependsOn({"userDao","roleDao","menuDao"})
public class SystemAuthorizingRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private SystemService systemService;

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		
		
		
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
		//得到所有的活动Session 
		Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(false);
		int activeSessionSize = sessions.size();
		System.out.println("活动线程数："+activeSessionSize);
//		
		Collection<Session> allsessions = getSystemService().getSessionDao().getActiveSessions(true);
		int allactiveSessionSize = allsessions.size();
		System.out.println("所有线程数："+allactiveSessionSize);
		
		if (logger.isDebugEnabled()){
			logger.debug("login submit, active session size: {}, username: {}", activeSessionSize, token.getUsername());
		}	
		
		Integer loginTotal =Integer.parseInt(Global.getConfig("user.loginTotal"));
		
		if(loginTotal>0){ //当总登录人数小于1时，不限制登录人数
			if(activeSessionSize>loginTotal){//在线总人数的限制
	        	throw new AuthenticationException("msg:当前在线用户过多, 请稍后再试.");
	        } 
		}
		
		//System.out.println("登录时调用");
		
		//System.out.println( "123123:"+getName());
		// 校验登录验证码
		if (LoginController.isValidateCodeLogin(token.getUsername(), false, false)){
			Session session = UserUtils.getSession();
			String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
			if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
				throw new AuthenticationException("msg:验证码错误, 请重试.");
			}
		}
		
		// 校验用户名密码
		User user = getSystemService().getUserByLoginName(token.getUsername());
		if(user==null){
			user = getSystemService().getUserByCardNumber(token.getUsername());
		}
		if(user==null){
			user = getSystemService().getUserByOrgNum(token.getUsername());
		}
		
		if (user != null) {
			if (Global.NO.equals(user.getLoginFlag())){
				throw new AuthenticationException("msg:该帐号已禁止登录.");
			}
			
			//同一时刻只能有一个账号登录,将其它登录的session删除掉
			if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))){
			    for(Session session:sessions){
		            //清除该用户以前登录时保存的session
			    	if(//String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)).equals("null")||
		            	user.getId().equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
		            	getSystemService().getSessionDao().delete(session);
		            	//System.out.println("移除session");
		            }
		        }
			}
			
			byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
			return new SimpleAuthenticationInfo(new Principal(user, token.isMobileLogin()), 
					user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}


	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		
		
		Collection<Session> sessions1 = getSystemService().getSessionDao().getActiveSessions(false);
		int activeSessionSize = sessions1.size();
	//	System.out.println("所有线程数："+activeSessionSize);
		
		Integer loginTotal =Integer.parseInt(Global.getConfig("user.loginTotal"));
		if(loginTotal>0){ //当总登录人数小于1时，不限制登录人数
			if(activeSessionSize>loginTotal){//在线总人数的限制
	        	throw new AuthenticationException("msg:当前在线用户过多, 请稍后再试.");
	        } 
		}
	    
		//System.out.println("授权方法调用");
		// 获取当前已登录的用户
		if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))){
			//Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, principal, UserUtils.getSession());
			Collection<Session> sessions = getSystemService().getSessionDao().getOtherActiveSessions( principal, UserUtils.getSession());
			
			if (sessions.size() > 0){
				// 如果是登录进来的，则踢出已在线用户
				if (UserUtils.getSubject().isAuthenticated()){
					for (Session session : sessions){
						getSystemService().getSessionDao().delete(session);
					}
				}
				// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
				else{
					UserUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
		}
		User user = getSystemService().getUserByLoginName(principal.getLoginName());
		
		
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<Menu> list = UserUtils.getMenuList();
			for (Menu menu : list){
				if (StringUtils.isNotBlank(menu.getPermission())){
					// 添加基于Permission的权限信息
					for (String permission : StringUtils.split(menu.getPermission(),",")){
						info.addStringPermission(permission);
					}
				}
			}
			// 添加用户权限
			info.addStringPermission("user");
			// 添加用户角色信息
			for (Role role : user.getRoleList()){
				info.addRole(role.getEnname());
			}
			// 更新登录IP和时间
			getSystemService().updateUserLoginInfo(user);
			// 记录登录日志
			LogUtils.saveLog(Servlets.getRequest(), "系统登录");
			return info;
		} else {
			return null;
		}
	}
	
	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}
	
	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
        		authorizationValidate(permission);
            }
        }
		return super.isPermitted(permissions, info);
	}
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}
	
	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
            	authorizationValidate(permission);
            }
        }
		return super.isPermittedAll(permissions, info);
	}
	
	/**
	 * 授权验证方法
	 * @param permission
	 */
	private void authorizationValidate(Permission permission){
		// 模块授权预留接口
	}
	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
		matcher.setHashIterations(SystemService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
//	/**
//	 * 清空用户关联权限认证，待下次使用时重新加载
//	 */
//	public void clearCachedAuthorizationInfo(Principal principal) {
//		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
//		clearCachedAuthorizationInfo(principals);
//	}

	/**
	 * 清空所有关联认证
	 * @Deprecated 不需要清空，授权缓存保存到session中
	 */
	@Deprecated
	public void clearAllCachedAuthorizationInfo() {
//		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
//		if (cache != null) {
//			for (Object key : cache.keys()) {
//				cache.remove(key);
//			}
//		}
	}

	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}
	
	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String id; // 编号
		private String loginName; // 登录名
		private String name; // 姓名
		private boolean mobileLogin; // 是否手机登录
		
//		private Map<String, Object> cacheMap;

		public Principal(User user, boolean mobileLogin) {
			this.id = user.getId();
			this.loginName = user.getLoginName();
			this.name = user.getName();
			this.mobileLogin = mobileLogin;
		}

		public String getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}

//		@JsonIgnore
//		public Map<String, Object> getCacheMap() {
//			if (cacheMap==null){
//				cacheMap = new HashMap<String, Object>();
//			}
//			return cacheMap;
//		}

		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try{
				return (String) UserUtils.getSession().getId();
			}catch (Exception e) {
				return "";
			}
		}
		
		@Override
		public String toString() {
			return id;
		}

	}
}
