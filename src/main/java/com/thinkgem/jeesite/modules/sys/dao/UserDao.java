/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	
	public int updateUserInfoById(User user);
	
	/**
	 * 审批企业用户与个人用户状态
	 * @param user
	 * @return
	 */
	public int updateConfirmFlg(User user);

	/**
	 * 更新企业用户状态（暂时只是登录状态）
	 * @param user
	 * @return
	 */
	public int updateEnterpriseUser(User user);

	/**
	 * 查询调整人数
	 * @param user
	 * @return
	 */
	public User findTiaoZhengRenShu(User user);

	public void updateTiaoZhengRenShu(User user);

	public User findUserId(User currentUser);

	public void updateUserInfoNew(User user);

	public void updateUserInfoNew01(User user);

	public void updateUserConfirmFlgNew(User user);

	public List<User> findExpertByUserModel(User user1);

	public List<User> findExpert(User user);

	public List<User> getCardNumber(String cardNumber);

	public void updateConfirmFlg1(User user);
	
	/**
	 * 根据组织机构代码查询用户
	 * @param OrgCode
	 * @return
	 */
	public List<User> getUserByOrgCode(String OrgCode);

	//根据名字查找user
	User getUserByName(@Param(value = "name") String companyName);

	public List<User> findSysUserName(User user);
}
