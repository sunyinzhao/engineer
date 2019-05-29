package com.thinkgem.jeesite.modules.counselor.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;//定时器类

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
public class SysContextListener implements ServletContextListener{
	private Timer timer = null;
	public void contextInitialized(ServletContextEvent event){
		timer = new Timer(true);
		event.getServletContext().log("定时器已启动");//添加日志，可在tomcat日志中查看到
		//调用exportHistoryBean，60*60*1000表示一个小时。
		long daySpan = 24*60*60*1000;
		
		String str = "2019-01-10 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			timer.schedule(new exportHistoryBean(event.getServletContext()),sdf.parse(str),daySpan);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event.getServletContext().log("已经添加任务");
	}
	public void contextDestroyed(ServletContextEvent event){
		//在这里关闭监听器，所以在这里销毁定时器。
		timer.cancel();
		event.getServletContext().log("定时器销毁");
	}
}
