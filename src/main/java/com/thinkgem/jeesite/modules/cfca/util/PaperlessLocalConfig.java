package com.thinkgem.jeesite.modules.cfca.util;
/**
 * @Description 测试用例的属性配置信息
 * @Author dell
 * @Date 2016-5-13
 * @CodeReviewer TODO
 */
public class PaperlessLocalConfig {

    public static String protocol = "http://";

    // 部署无纸化服务的IP
    public static String host = "127.0.0.1";
    
    // 部署无纸化服务的Port
    public static String port = "8080";

    // 无纸化辅助接口的访问URL
    public static String assistUrl = protocol + host + ":" + port + "/PaperlessServer/PaperlessAssistServlet";
    // 无纸化扩展接口的访问URL
    public static String extUrl = protocol + host + ":" + port + "/PaperlessServer/PaperlessExtServlet";
    
    
    public static String timeServerUrl = "http://127.0.0.1:8080/Proxy/Timestamp";

    // 机构编码，需要在无纸化管理系统中添加此机构的信息，登录管理页面->选择系统管理->选择机构管理->添加机构信息，
    //public static String operatorCode = "org001";

    // 柜员编码，需要在无纸化管理系统中添加此柜员的信息，登录管理页面->选择系统管理->选择柜员管理->添加柜员信息，
    public static String operatorCode = "gy001";

    // 渠道编码，需要在无纸化管理系统中添加此渠道的信息，登录管理页面->选择系统管理->选择渠道管理->添加渠道信息，
    public static String channelCode = "";
    
    //
    public static String keyStorePath = "./TestData/cert/org001.jks";
    public static String keyStorePassword = "11111111";
    public static String trustStorePath = "./TestData/cert/org001.jks";
    public static String trustStorePassword = "11111111";

}
