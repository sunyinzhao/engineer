package com.thinkgem.jeesite.modules.cfca.util;

/**
 * @Description 测试用例的属性配置信息
 * @Author dell
 * @Date 2016-5-13
 * @CodeReviewer TODO
 */
public class PaperlessConfig {

    /* 通信协议  */
    public static String protocol = "http://";

    /* 部署可信手写签章服务的IP  */
    public static String host = "103.38.227.5";
    
    /* 部署可信手写签章服务的port  */
    public static String port = "8085";

    /* 机构编码，需要预先在管理系统中添加此机构的信息，登录管理系统->选择系统管理->选择组织 机构管理->添加机构信息，   */
    public static String operatorCode = "001";
     
    /* 渠道编码，需要在无纸化管理系统中添加此渠道的信息，登录管理系统->选择系统管理->选择渠道管理->添加渠道信息，  */
    public static String channelCode = ""; 
    
    

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
    // 无纸化联合接口的访问URL
    public static String uniteUrl = protocol + host + ":" + port + "/PaperlessServer/PaperlessUniteServlet";
    // 无纸化云平台的访问URL
    public static String proxyUrl = protocol + host + ":" + port + "/Proxy/PaperlessServer";
    // 无纸化UA认证访问URL
    public static String uaUrl = protocol + host + ":" + port + "/PaperlessServer/IdentityAuthentiServlet";
    //无纸化心跳检测
    public static String heartUrl=protocol + host + ":" + port + "/PaperlessServer/PaperlessHeartBeatServlet";
 
    // 
    public static String keyStorePath = "./TestData/cert/client.jks";
    public static String keyStorePassword = "11111111";
    public static String trustStorePath = "./TestData/cert/client.jks";
    public static String trustStorePassword = "11111111";
    
    public final static String PDF_FILE_SUFFIX ="pdf";
    
    public final static String HTML_FILE_SUFFIX ="html";

    public final static String XML_FILE_SUFFIX ="xml";
    
    public final static String FTL_FILE_SUFFIX ="ftl";
    
    public final static String PNG_FILE_SUFFIX ="png";
    
    public final static String DOCX_FILE_SUFFIX = "docx";
    
    public final static String DOC_FILE_SUFFIX = "doc";
    
    public static final String DEFAULT_CHARSET = "UTF-8";
    
    public static final String HASH_ALG_SHA1= "sha1";
    
    public static final String HASH_ALG_SHA256= "sha256";
    
    public static final String HASH_ALG_SM3= "sm3";
    
}
