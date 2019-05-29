package com.thinkgem.jeesite.common.utils;


import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.utils.sms.request.SmsSendRequest;
import com.thinkgem.jeesite.common.utils.sms.response.SmsSendResponse;
import com.thinkgem.jeesite.common.utils.sms.util.ChuangLanSmsUtil;

public class SendMessge {
	public static final String charset = "utf-8";


	public static void maina(String[] args){
		// 用户平台API账号(非登录账号,示例:N1234567)
		String account = "N014016_N6302264";
		// 用户平台API密码(非登录密码)
		String pswd = "GlyLhrjHmY5273";
		//请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
		String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";
		// 短信内容
	    String msg = args[0];
		//手机号码
		String phone = args[1];
		//状态报告
		String report= "true";

		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone,report);
		
		String requestJson = JSON.toJSONString(smsSingleRequest);
		
		System.out.println("before request string is: " + requestJson);

		String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
		
		System.out.println("response after request result is :" + response);
		
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		
		System.out.println("response  toString is :" + smsSingleResponse);
		
	
	}


}
