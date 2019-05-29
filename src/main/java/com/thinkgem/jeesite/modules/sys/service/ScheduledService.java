/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.sys.dao.ExamineDao;
import com.thinkgem.jeesite.modules.sys.dao.MenuDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;


@Service
@Transactional(readOnly = false)
@Lazy(false)
public class ScheduledService extends BaseService  {

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
	@Autowired
	private SystemService systemService;
	@Autowired
	private EnterpriseWorkersService enterpriseWorkersService;

	
// 每天凌晨2点  0 0 2 * * ?和每天隔一小时 0 * */1 * * ?


//例1：每隔5秒执行一次：*/5 * * * * ?
//
//例2：每隔5分执行一次：0 */5 * * * ?
//
//
//在26分、29分、33分执行一次：0 26,29,33 * * * ?
//
//例3：每天半夜12点30分执行一次：0 30 0 * * ? （注意日期域为0不是24）
//
//
//每天凌晨1点执行一次：0 0 1 * * ?
//
//每天上午10：15执行一次： 0 15 10 ? * * 或 0 15 10 * * ? 或 0 15 10 * * ? *
//
//每天中午十二点执行一次：0 0 12 * * ?
//
//每天14点到14：59分，每1分钟执行一次：0 * 14 * * ?
//
//每天14点到14：05分，每1分钟执行一次：0 0-5 14 * * ?
//
//每天14点到14：55分，每5分钟执行一次：0 0/5 14 * * ?
//
//每天14点到14：55分，和18点到18点55分，每5分钟执行一次：0 0/5 14,18 * * ?
//
//每天18点执行一次：0 0 18 * * ?
//
//每天18点、22点执行一次：0 0 18,22 * * ?
//
//每天7点到23点，每整点执行一次：0 0 7-23 * * ?
//
//每个整点执行一次：0 0 0/1 * * ?
	@Scheduled(cron = "0 0 1 * * ?")  //每天凌晨一点
	@Transactional(readOnly = false)
	public void birthdayReminder() {
//		System.out.println("定时任务执行"+System.currentTimeMillis());
		
		/*EnterpriseWorkers enterpriseWorkers = new EnterpriseWorkers();
		enterpriseWorkers.setIsRegister("1");
		List<EnterpriseWorkers> workersList= enterpriseWorkersService.findIsValid(enterpriseWorkers);
		//获取当前时间
		long currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < workersList.size(); i++) {
			Date date = workersList.get(i).getCreateDate();
			long time = date.getTime();
			if(currentTimeMillis > time+432000000){//432000000（ms） == 5天
				//退回该用户
				User user = new User();
				user.setId(workersList.get(i).getUserId());
				user.setConfimFlg("2");
				user.setOfficeId("");
				systemService.updateUserConfirmFlgNew(user);
				//根据ID清除该用户的公司信息
				enterpriseWorkers.setId(workersList.get(i).getId());
				enterpriseWorkers.setPid("");
				enterpriseWorkers.setCompanyName("");
				enterpriseWorkersService.updateCompanyInfo(enterpriseWorkers);
//				System.out.println("删除登记且无效过期数据");
			}
		}*/
	}
}
