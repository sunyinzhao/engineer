package com.thinkgem.jeesite.modules.cfca.util;

/**
 * @Description 测试用例的属性配置信息
 * @Author dell
 * @Date 2016-5-13
 * @CodeReviewer TODO
 */
public class PaperlessCloudConfig {

    public static String protocol = "https://";

    // 部署无纸化服务的IP
    //public static String host = "210.74.41.206";
    public static String host = "210.74.42.34";

    // 部署无纸化服务的Port
    public static String port = "8443";

    // 无纸化基本接口的访问URL
    public static String url = protocol + host + ":" + port + "/PaperlessServer/PaperlessServlet";
    // 无纸化辅助接口的访问URL
    public static String assistUrl = protocol + host + ":" + port + "/PaperlessServer/PaperlessAssistServlet";
    // 无纸化扩展接口的访问URL
    public static String extUrl = protocol + host + ":" + port + "/PaperlessServer/PaperlessExtServlet";
    // 无纸化管理接口的访问URL
    public static String managerUrl = protocol + host + ":" + port + "/PaperlessServer/PaperlessManagerServlet";
    // 无纸化查询接口的访问URL
    public static String queryUrl = protocol + host + ":" + port + "/PaperlessServer/PaperlessQueryServlet";

    // 机构编码，需要在无纸化管理系统中添加此机构的信息，登录管理页面->选择系统管理->选择机构管理->添加机构信息，
    public static String operatorCode = "haier_rzzl";

    // 柜员编码，需要在无纸化管理系统中添加此柜员的信息，登录管理页面->选择系统管理->选择柜员管理->添加柜员信息，
    //public static String operatorCode = "haier_rzzl_gy01";

    // 渠道编码，需要在无纸化管理系统中添加此渠道的信息，登录管理页面->选择系统管理->选择渠道管理->添加渠道信息，
    public static String channelCode = "";

    // 
    public static String keyStorePath = "./TestData/cert/haier-test.jks";
    public static String keyStorePassword = "11111111";
    public static String trustStorePath = "./TestData/cert/haier-test.jks";
    public static String trustStorePassword = "11111111";

}
