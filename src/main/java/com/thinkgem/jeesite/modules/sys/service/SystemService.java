/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.security.Digests;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.sys.dao.ExamineDao;
import com.thinkgem.jeesite.modules.sys.dao.MenuDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.*;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author ThinkGem
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	@Autowired
	private ExamineDao examineDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;



	
	public SessionDAO getSessionDao() {
		return sessionDao;
	}

//	@Autowired
//	private IdentityService identityService;

	//-- User Service --//
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	
	
	/**
	 * 根据身份证号获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByCardNumber(String cardNumber) {
		List<User> list =userDao.getCardNumber(cardNumber);
		if(list==null||list.size()==0 || list.size()>1){
			return null;
		}else{
			return list.get(0);
		}
	}/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByOrgNum(String orgNum) {
		List<User> list =userDao.getUserByOrgCode(orgNum);
		if(list==null||list.size()==0 || list.size()>1){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	
	public Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findList(user));
		return page;
	}
	
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user){
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		List<User> list = userDao.findList(user);
		return list;
	}


    /**
     * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
     * @param officeId
     * @return
     */
	public List<User> findUserByOfficeId(String officeId) {
		List<User> list = (List<User>)CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
		if (list == null){
			User user = new User();
			user.setOffice(new Office(officeId));
			list = userDao.findUserByOfficeId(user);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}
	
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		if (StringUtils.isBlank(user.getId())){
			user.preInsert();
			userDao.insert(user);
		}else{
			// 清除原用户机构用户缓存
			User oldUser = userDao.get(user.getId());
			if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null){
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice().getId());
			}
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
		}
		if (StringUtils.isNotBlank(user.getId())){
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0){
				userDao.insertUserRole(user);
			}else{
				if(!"4".equals(user.getUserModel())){//非企业用户必须设置角色
					throw new ServiceException(user.getLoginName() + "没有设置角色！");
				}
				
			}
			// 将当前用户同步到Activiti
			// saveActivitiUser(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		}
	}
	
	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updateUserInfoById(User user) {
		user.preUpdate();
		userDao.updateUserInfoById(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userDao.delete(user);
		// 同步到Activiti
		// deleteActivitiUser(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
		user.setLoginDate(new Date());
		userDao.updateLoginInfo(user);
	}
	
	@Transactional(readOnly = false)
	public void updateUserConfirmFlg(User user) {
		
		userDao.updateConfirmFlg(user);
	}

	/**
	 * 修改企业用户信息（暂时只更新用户是否可以登录）
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void updateEnterpriseUser(User user) {
		user.preUpdate();
		userDao.updateEnterpriseUser(user);
	}



	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 获得活动会话
	 * @return
	 */
	public Collection<Session> getActiveSessions(){
		return sessionDao.getActiveSessions(false);
	}
	
	//-- Role Service --//
	
	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}
	
	public Role getRoleByEnname(String enname) {
		Role r = new Role();
		r.setEnname(enname);
		return roleDao.getByEnname(r);
	}
	
	public List<Role> findRole(Role role){
		return roleDao.findList(role);
	}
	
	public List<Role> findAllRole(){
		return UserUtils.getRoleList();
	}

	/**
	 * 根据用户返回此用户可以使用的角色
	 * @param user
	 * @param type 1:返回“专家角色” 2：返回“协会角色”
	 * @return
	 */
	public List<Role> findRoleByUser(User user,String type){

		if(user==null ||StringUtils.isBlank(type) ){
			return null;
		}

/*		1.系统管理员：
			1专家： 6,7,10,11
			2协会: 2,3,4,5
		2.协会管理员：
			1专家： 6,7,10,11
			2协会: 2,3,4,5

		3.地方管理员：
		      1专家：7,11
		      2协会: 3,5
*/


        // 角色数据有效性验证，过滤不在授权内的角色
        List<Role> roleList = Lists.newArrayList();
        String roleString = "";

        boolean t1= false;
        boolean t2= false;
        boolean t3= false;

        //List<String> roleIdListb = user.getRoleIdList();

        for (Role r : user.getRoleList()){
            if ("1".equals(r.getId())){
                t1=true;
            }else if ("2".equals(r.getId())){
                t2=true;
            }else if ("3".equals(r.getId())){
                t3=true;
            }
        }
        //update by gaoyongjian 2018623 增加了10:协会专家文员会,11:地方协会专家委员会
        if(t1){
            if(type.equals("1")){
                roleString="6,7,10,11";
            }else if(type.equals("2")){
                roleString="2,3,4,5";
            }
        }else if(t2){
            if(type.equals("1")){
                roleString="6,7,10,11";
            }else if(type.equals("2")){
                roleString="2,3,4,5";
            }
        }else if(t3){
            if(type.equals("1")){
                roleString="7,11";
            }else if(type.equals("2")){
                roleString="3,5";
            }
        }

        //查询出包含的角色
        List <String> roleIdList = Arrays.asList(roleString.split(","));
        for (Role r : this.findAllRole()){
            if (roleIdList.contains(r.getId())){
                roleList.add(r);
            }
        }
		return roleList;
	}



	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())){
			role.preInsert();
			roleDao.insert(role);
			// 同步到Activiti
			// saveActivitiGroup(role);
		}else{
			role.preUpdate();
			roleDao.update(role);
		}
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0){
			roleDao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		/*roleDao.deleteRoleOffice(role);
		if (role.getOfficeList().size() > 0){
			roleDao.insertRoleOffice(role);
		}*/
		// 同步到Activiti
		//saveActivitiGroup(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteRole(Role role) {
		roleDao.delete(role);
		// 同步到Activiti
		//deleteActivitiGroup(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles){
			if (e.getId().equals(role.getId())){
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) {
		if (user == null){
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	//-- Menu Service --//
	
	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public List<Menu> findAllMenu(){
		return UserUtils.getMenuList();
	}
	
	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {
		
		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds(); 
		
		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds()+menu.getParent().getId()+",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())){
			menu.preInsert();
			menuDao.insert(menu);
		}else{
			menu.preUpdate();
			menuDao.update(menu);
		}
		
		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%,"+menu.getId()+",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	
	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 "+Global.getConfig("productName")+"\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}

	/*----------------------examineService-------------------------------*/

	public Examine getExamine(String id) {

		return examineDao.get(id);
	}




	//根据名字查找id
	public Examine getName(String name){
		return examineDao.getName(name);
	}


	
	///////////////// Synchronized to the Activiti //////////////////
	
	// 已废弃，同步见：ActGroupEntityServiceFactory.java、ActUserEntityServiceFactory.java

	/**
	 * 是需要同步Activiti数据，如果从未同步过，则同步数据。
	 */
	private static boolean isSynActivitiIndetity = true;
	public void afterPropertiesSet() throws Exception {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		/*if (isSynActivitiIndetity){
			isSynActivitiIndetity = false;
	        // 同步角色数据
			List<Group> groupList = identityService.createGroupQuery().list();
			if (groupList.size() == 0){
			 	Iterator<Role> roles = roleDao.findAllList(new Role()).iterator();
			 	while(roles.hasNext()) {
			 		Role role = roles.next();
			 		saveActivitiGroup(role);
			 	}
			}
		 	// 同步用户数据
			List<org.activiti.engine.identity.User> userList = identityService.createUserQuery().list();
			if (userList.size() == 0){
			 	Iterator<User> users = userDao.findAllList(new User()).iterator();
			 	while(users.hasNext()) {
			 		saveActivitiUser(users.next());
			 	}
			}
		}*/
	}
	/*
	private void saveActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		String groupId = role.getEnname();
		
		// 如果修改了英文名，则删除原Activiti角色
		if (StringUtils.isNotBlank(role.getOldEnname()) && !role.getOldEnname().equals(role.getEnname())){
			identityService.deleteGroup(role.getOldEnname());
		}
		
		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null) {
			group = identityService.newGroup(groupId);
		}
		group.setName(role.getName());
		group.setType(role.getRoleType());
		identityService.saveGroup(group);
		
		// 删除用户与用户组关系
		List<org.activiti.engine.identity.User> activitiUserList = identityService.createUserQuery().memberOfGroup(groupId).list();
		for (org.activiti.engine.identity.User activitiUser : activitiUserList){
			identityService.deleteMembership(activitiUser.getId(), groupId);
		}

		// 创建用户与用户组关系
		List<User> userList = findUser(new User(new Role(role.getId())));
		for (User e : userList){
			String userId = e.getLoginName();//ObjectUtils.toString(user.getId());
			// 如果该用户不存在，则创建一个
			org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId).singleResult();
			if (activitiUser == null){
				activitiUser = identityService.newUser(userId);
				activitiUser.setFirstName(e.getName());
				activitiUser.setLastName(StringUtils.EMPTY);
				activitiUser.setEmail(e.getEmail());
				activitiUser.setPassword(StringUtils.EMPTY);
				identityService.saveUser(activitiUser);
			}
			identityService.createMembership(userId, groupId);
		}
	}

	public void deleteActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		if(role!=null) {
			String groupId = role.getEnname();
			identityService.deleteGroup(groupId);
		}
	}

	private void saveActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		String userId = user.getLoginName();//ObjectUtils.toString(user.getId());
		org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId).singleResult();
		if (activitiUser == null) {
			activitiUser = identityService.newUser(userId);
		}
		activitiUser.setFirstName(user.getName());
		activitiUser.setLastName(StringUtils.EMPTY);
		activitiUser.setEmail(user.getEmail());
		activitiUser.setPassword(StringUtils.EMPTY);
		identityService.saveUser(activitiUser);
		
		// 删除用户与用户组关系
		List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
		for (Group group : activitiGroups) {
			identityService.deleteMembership(userId, group.getId());
		}
		// 创建用户与用户组关系
		for (Role role : user.getRoleList()) {
	 		String groupId = role.getEnname();
	 		// 如果该用户组不存在，则创建一个
		 	Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
            if(group == null) {
	            group = identityService.newGroup(groupId);
	            group.setName(role.getName());
	            group.setType(role.getRoleType());
	            identityService.saveGroup(group);
            }
			identityService.createMembership(userId, role.getEnname());
		}
	}

	private void deleteActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		if(user!=null) {
			String userId = user.getLoginName();//ObjectUtils.toString(user.getId());
			identityService.deleteUser(userId);
		}
	}
	
	///////////////// Synchronized to the Activiti end //////////////////
	*/

	public User findTiaoZhengRenShu(User user) {
		// TODO Auto-generated method stub
		User personNumber = this.userDao.findTiaoZhengRenShu(user);
		return personNumber;
	}

	@Transactional(readOnly=false)
	public void updateTiaoZhengRenShu(User user) {
		// TODO Auto-generated method stub
		this.userDao.updateTiaoZhengRenShu(user);
	}
	@Transactional(readOnly=false)
    public List<Examine> findAllNewExamine(String declareRecordId) {
		return examineDao.findAllNewExamine(declareRecordId);
    }

	//根据  例如:甲综id,以及 类型 查找 到类型的rootid,
    public List<Examine> findExamineRootId(String examineId, String kind) {
			return examineDao.findExamineRootId(examineId,kind);
    }

	//查询专家带颜色的树
	public List<Examine> findNewExpertExamine(String declareRecordId, String specialtyId, String kind) {
		return examineDao.findNewExpertExamine(declareRecordId,specialtyId,kind);
	}

	public List<Examine> outNode() {

		return examineDao.outNode();
	}

	public User findUserId(User currentUser) {
		// TODO Auto-generated method stub
		return this.userDao.findUserId(currentUser);
	}

	@Transactional(readOnly=false)
	public void updateUserInfoNew(User user) {
		// TODO Auto-generated method stub
		this.userDao.updateUserInfoNew(user);
	}
	
	public List<User> findSysUserName(User user) {
		// TODO Auto-generated method stub
		return this.userDao.findSysUserName(user);
	}
	
	@Transactional(readOnly=false)
	public void updateUserInfoNew01(User user) {
		// TODO Auto-generated method stub
		this.userDao.updateUserInfoNew01(user);
	}

	@Transactional(readOnly=false)
	public String deleteInfo(User userId) {
		// TODO Auto-generated method stub
		String flag = "true";
		try{
			this.userDao.delete(userId);
			return "true";
		}catch(Exception sql){
			return "false";
		}
	}

	@Transactional(readOnly=false)
	public void updateUserConfirmFlgNew(User user) {
		// TODO Auto-generated method stub
		this.userDao.updateUserConfirmFlgNew(user);
	}

    public List<Examine> findExamineByParentId(String id) {
		return examineDao.findExamineByParentId(id);
    }

	public List<User> findExpertByUserModel(User user1) {
		// TODO Auto-generated method stub
		return this.userDao.findExpertByUserModel(user1);
	}

	public Page<User> findExpert(Page<User> page, User user) {
		// TODO Auto-generated method stub
		user.setPage(page);
		page.setList(this.userDao.findExpert(user));
		return page;
	}

	@Transactional(readOnly=false)
	public void addRoleByUser(Role role) {
		// TODO Auto-generated method stub
		this.roleDao.addRoleByUser(role);
	}

	public List<User> getCardNumber(String cardNumber) {
		// TODO Auto-generated method stub
		List<User> list = this.userDao.getCardNumber(cardNumber);
		return list;
	}

	@Transactional(readOnly=false)
	public void updateRoleByUser(Role role) {
		// TODO Auto-generated method stub
		this.roleDao.updateRoleByUser(role);
	}

	@Transactional(readOnly=false)
	public void updateConfirmFlg(User user) {
		// TODO Auto-generated method stub
		this.userDao.updateConfirmFlg1(user);
	}

	//根据公司名称查找user
	public User getUserByName(String companyName) {
		return userDao.getUserByName(companyName);
	}

	public Page<User> findExpertByUserModel(Page<User> page, User user) {
		// TODO Auto-generated method stub
		user.setPage(page);
		page.setList(this.userDao.findExpertByUserModel(user));
		return page;
	}
}
