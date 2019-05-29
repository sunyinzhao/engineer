/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cfca.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cfca.PaperlessConfig;
import com.thinkgem.jeesite.modules.cfca.util.AutoGenerateCircleImage;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cfca.paperless.client.servlet.PaperlessClient;
import cfca.paperless.util.GUID;
import cfca.paperless.util.IoUtil;
import cfca.paperless.util.StringUtil;
import cfca.ra.common.vo.request.CertServiceRequestVO;
import cfca.ra.common.vo.response.CertServiceResponseVO;
import cfca.ra.toolkit.RAClient;
import cfca.ra.toolkit.exception.RATKException;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.associationinfo.dao.AssociationInfoDao;
import com.thinkgem.jeesite.modules.associationinfo.entity.AssociationInfo;
import com.thinkgem.jeesite.modules.cfca.CfcaConfig;
import com.thinkgem.jeesite.modules.cfca.CfcaRaConfig;
import com.thinkgem.jeesite.modules.cfca.dao.CfcaElectronicChapterDao;
import com.thinkgem.jeesite.modules.cfca.entity.CfcaElectronicChapter;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseWorkersDao;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * CFCA电子签章申请结果Service
 * @author xqg
 * @version 2018-09-03
 */
@Service
@Transactional(readOnly = true)
public class CfcaElectronicChapterService extends CrudService<CfcaElectronicChapterDao, CfcaElectronicChapter> {
	@Autowired
	private EnterpriseWorkersDao enterpriseWorkersDao;
	@Autowired
	private AssociationInfoDao associationInfoDao;
	@Autowired
	private CfcaElectronicChapterDao cfcaElectronicChapterDao;



	
	public CfcaElectronicChapter get(String id) {
		return super.get(id);
	}
	
	public List<CfcaElectronicChapter> findList(CfcaElectronicChapter cfcaElectronicChapter) {
		return super.findList(cfcaElectronicChapter);
	}
	
	public Page<CfcaElectronicChapter> findPage(Page<CfcaElectronicChapter> page, CfcaElectronicChapter cfcaElectronicChapter) {
		return super.findPage(page, cfcaElectronicChapter);
	}
	
	@Transactional(readOnly = false)
	public void save(CfcaElectronicChapter cfcaElectronicChapter) {
		super.save(cfcaElectronicChapter);
	}

    @Transactional(readOnly = false)
    public void update(CfcaElectronicChapter cfcaElectronicChapter) {
        cfcaElectronicChapterDao.update(cfcaElectronicChapter);
    }
	
	@Transactional(readOnly = false)
	public void delete(CfcaElectronicChapter cfcaElectronicChapter) {
		super.delete(cfcaElectronicChapter);
	}
	
	@Transactional(readOnly = false)
	public boolean applyCfcaElectronicChapter(CfcaElectronicChapter cfcaElectronicChapter) {
		//EnterpriseWorkers work = new EnterpriseWorkers();
        //是否存在有效的电子章
        List <CfcaElectronicChapter>list = cfcaElectronicChapterDao.findSelfList(cfcaElectronicChapter);
        EnterpriseWorkers work =enterpriseWorkersDao.get(cfcaElectronicChapter.getUserId());
        CfcaElectronicChapter chapter=null;

        if(list ==null || list.size()==0){ //若不存在，申请新的
            createChapterImage("","",work.getName(),work.getRegisterCertificateNum());
            chapter= applyCfcaEC(work);
            if(chapter !=null){
            	work.setElectronicChapterFlag("1");
                createChapterImage(chapter.getId(),work.getId(),work.getName(),work.getRegisterCertificateNum());
            	enterpriseWorkersDao.updateElectronicChapterFlag(work);  //更新申请证书状态
                return true;
            }else{
                return false;
            }
        }else { //存在，不再申请
            chapter = list.get(0);
        	work.setElectronicChapterFlag("1");
            createChapterImage(chapter.getId(),work.getId(),work.getName(),work.getRegisterCertificateNum());
            enterpriseWorkersDao.updateElectronicChapterFlag(work);  //更新申请证书状态          
            return true;
        }
	}

    /**
     * 生成电子章图片
     */
	private void createChapterImage(String cfcaElectronicChapterId,String workId,String workerNames ,String certificateNum){

        //工程师附件基础
        String counselorBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_BASE_URL ;
        String upaloadUrl = FileUtils.path(counselorBaseDir+workId + "/");

//		System.out.println("收到图片!");
        File dir = new File(upaloadUrl);
        String img_url = upaloadUrl;//图片路径
        if(!dir.exists()) {//目录不存在则创建
            dir.mkdirs();
        }
            File file = null;
            String fileNamePath = upaloadUrl +workId;
            File tagetFile = new File(fileNamePath);//创建文件对象
            img_url += fileNamePath;

            try {
                //根据workerId和图片的路径插入workers表中
                CfcaElectronicChapter cfcaElectronicChapter = new CfcaElectronicChapter();
                cfcaElectronicChapter.setId(cfcaElectronicChapterId);
                cfcaElectronicChapter.setChapterImage(fileNamePath);
                AutoGenerateCircleImage.generateCircleImage(workerNames,certificateNum,fileNamePath);
                //更新图片路径
                cfcaElectronicChapterDao.updateChapterImage(cfcaElectronicChapter);

            } catch (Exception e) {
                e.printStackTrace();
            }


    }


/*
    public void createImage(String imapgPath) throws Exception {

        long timeStart = System.currentTimeMillis();// 开始时间
        System.out.println("AutoGenerateImageTest04 START");
        PaperlessClient clientBean = new PaperlessClient(PaperlessConfig.assistUrl, 10000, 60000);// 无纸化辅助接口的访问URL
        // 1：方型
        // 11：方形（带框）
        // 2：长方形（不带“章”字）
        // 21：长方形（带框）
        // 3：圆形（不带五角星）
        // 4：圆形（带五角星）
        //int shape = 3;
        int shape = 3;

        int width = 450;// 图片宽（单位：像素）
        int height = 450;// 图片高（单位：像素）

        String color = "FF0000";// 十六进制，默认FF0000（适用于方章和长方形章）
        int fontSize = 40;// 字体大小（单位：像素），默认100（适用于方章和长方形章）

        String name = "中华人民共和国咨询工程师（投资）";// 印模文字。长方形时，换行字符为“##”

        String name2 = "艾尔肯·阿布都拉";// 圆形章时用到，印模文字，如：电子回单专用章
        getStyleName(name2);
        // 圆形章时用到，印模文字样式。说明：该字段为空时，使用默认样式:"font-family:Times,font-size:8;x-ratio:0.2;y-ratio:0.8"
        String name2Style = "font-family:宋体,font-size:20;x-ratio:0.35;y-ratio:0.48";

        String businessCode = "ZD0120181100005";// 圆形章时用到，业务码：可以是验证码、查询码等
        String businessCodeStyle = "font-family:宋体,font-size:20;x-ratio:0.3;y-ratio:0.70";// 圆形章时用到，业务码样式。说明：该字段为空时，使用管理页面上设置的“业务码样式”。



        String imageStrategyXml = createImageStrategyXml(name, name2, name2Style, width, height, shape, color, fontSize, businessCode, businessCodeStyle);

        System.out.println(imageStrategyXml);

        // 操作员编码或者机构号
        String operatorCode = PaperlessConfig.operatorCode;
        // 渠道编码，可以为空
        String channelCode = PaperlessConfig.channelCode;

        String systemNo = GUID.generateId();//业务流水号

        // 取得生成的图片文件数据
        byte[] result = clientBean.autoGenerateImage(systemNo, imageStrategyXml, operatorCode, channelCode);

        String errorRsString = "";//PaperlessUtil.isError(result);

        // 处理结果为正常，保存生成的图片文件到本地目录下
        if ("".equals(errorRsString)) {
            System.out.println("AutoGenerateImageTest04 END OK");

            System.out.println(result.length);
            IoUtil.write(imapgPath, result);

        } else {
            // 处理结果为异常，打印出错误码和错误信息
            System.out.println("AutoGenerateImageTest04 END NG");
            System.out.println(new String(result, "UTF-8"));
        }

        long timeEnd = System.currentTimeMillis();// 结束时间
        System.out.println("##########" + "time used:" + (timeEnd - timeStart) + "##########");
    }*/

    /**
     *  给二字名，三字名加入空格 四字以上的不再添加空格
     * @param name
     * @return
     */
/*    private String getStyleName(String name){
        int length = name.length();
        if(length ==2 || length==3){
            String regex ="(.{1})";
            name = name.replaceAll(regex,"$1 ").trim();
            return name;
        }else {
            return name;
        }
    }*/
    /**
     * 生成图片生成策略文件
     *
     * @return
     */
/*
    public static String createImageStrategyXml(String name, String name2, String name2Style, int width, int height, int shape, String color, int fontSize,
                                                String businessCode, String businessCodeStyle) {

        StringBuilder imageStrategyXML = new StringBuilder();

        imageStrategyXML.append("<Request>");
        // 1：方型
        // 11：方形（带框）
        // 2：长方形（不带“章”字）
        // 21：长方形（带框）
        // 3：圆形（不带五角星）
        // 4：圆形（带五角星）
        imageStrategyXML.append("<ImageShape>").append(shape).append("</ImageShape>");
        imageStrategyXML.append("<ImageWidth>").append(width).append("</ImageWidth>");// 图片宽
        imageStrategyXML.append("<ImageHeight>").append(height).append("</ImageHeight>");// 图片高
        imageStrategyXML.append("<Color>").append(color).append("</Color>"); // 十六进制，默认FF0000
        imageStrategyXML.append("<FontSize>").append(fontSize).append("</FontSize>"); // 字体大小，默认100
        imageStrategyXML.append("<ImageName>").append(name).append("</ImageName>");// 长方形换行字符“##”
        imageStrategyXML.append("<ImageName2>").append(name2).append("</ImageName2>");//

        if (StringUtil.isNotEmpty(name2Style)) {
            imageStrategyXML.append("<ImageName2Style>").append(name2Style).append("</ImageName2Style>");
        }

        if (StringUtil.isNotEmpty(businessCode)) {
            imageStrategyXML.append("<BusinessCode>").append(businessCode).append("</BusinessCode>");
            imageStrategyXML.append("<BusinessCodeStyle>").append(businessCodeStyle).append("</BusinessCodeStyle>");
        }

        imageStrategyXML.append("</Request>");

        return imageStrategyXML.toString();
    }
*/





    private CfcaElectronicChapter applyCfcaEC(EnterpriseWorkers work){
	    boolean result = false;
		System.out.println(System.currentTimeMillis());
        // String locale = "zh_CN";
		//ca名称
		String caName = "OCA33";
        // 普通证书 普通：1 高级：2
        String certType = "1";       			// 证书类型标识（复合证书证书类型用'-'连接，其中1：签名单证，2：双证，3-加密单证） 复合证书 单单1-1 单双1-2 双单2-1 双双2-2 
        String customerType = "1";				// 个人-1  企业-2   服务器-3   设备证书-6   场景证书-7  
        
        String userName = work.getName();     		// 用户姓名
        // String userNameInDn = "testName";
        // String userIdent = "Z1234567890";
        String identType = "0";    				// 证件类型 ‘0’： 居民身份证
        String identNo = work.getCertificatesNum();  // 证件号
        //String keyAlg = "RSA"; 					//加密算法 默认 “RSA”
        //String keyLength = "2048"; 				//密钥长度 默认 “2048”
        String branchCode = "678";               //证书所属机构
        // String email = "tdest";
        // String phoneNo = "12345678";
        // String address = "address";
        // String duration = "24";



        // String endTime = "20160101000001"; // endTime与duration同时非空时，证书截止时间以endTime为准，duration作为证书默认有效期记入数据库

        String endTime =DateUtils.formatDate(work.getValidDate(),"yyyyMMddHHmmss");//截止日期
        // String addIdentNoExt = "false";
        // String addEmailExt = "false";
        // String selfExtValue = "extValue";
        // 设备标识
        String deviceIdentifier = "19+x,2.168.117.101";
        // 部门名称
        String departmentNameInCert = "部门名称";
        // 组织机构名称
        String organizationNameInCert = "组织机构名称";
        // 营业地点所在地市
        String locality = "北京";
        // 营业地点所在省
        String stateOrProvince = "北京";
        // 国家,不填默认为CN
        String country = "CN";
        // 开始时间(不能早于当前时间一定范围，该值可在sys.ini中配置)
        // String startTime = "20170903154323";
        try {
            RAClient client = CfcaRaConfig.getRAClient();

            CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
            certServiceRequestVO.setTxCode("2101");
			certServiceRequestVO.setCaName(caName);
            // certServiceRequestVO.setLocale(locale);
            certServiceRequestVO.setCertType(certType);
            certServiceRequestVO.setCustomerType(customerType);
            certServiceRequestVO.setUserName(userName);
            // certServiceRequestVO.setUserNameInDn(userNameInDn);
            // certServiceRequestVO.setUserIdent(userIdent);
            certServiceRequestVO.setIdentType(identType);
            certServiceRequestVO.setIdentNo(identNo);
            // certServiceRequestVO.setKeyLength(keyLength);
            // certServiceRequestVO.setKeyAlg(keyAlg);
            certServiceRequestVO.setBranchCode(branchCode);
            // certServiceRequestVO.setEmail(email);
            // certServiceRequestVO.setPhoneNo(phoneNo);
            // certServiceRequestVO.setAddress(address);
            // certServiceRequestVO.setDuration(duration);
            //certServiceRequestVO.setStartTime(startTime);
             certServiceRequestVO.setEndTime(endTime);
            // certServiceRequestVO.setAddIdentNoExt(addIdentNoExt);
            // certServiceRequestVO.setAddEmailExt(addEmailExt);
            // certServiceRequestVO.setSelfExtValue(selfExtValue);
            certServiceRequestVO.setDeviceIdentifier(deviceIdentifier);
            certServiceRequestVO.setDepartmentNameInCert(departmentNameInCert);
            certServiceRequestVO.setOrganizationNameInCert(organizationNameInCert);
            certServiceRequestVO.setLocality(locality);
            certServiceRequestVO.setStateOrProvince(stateOrProvince);
            certServiceRequestVO.setCountry(country);

            CertServiceResponseVO certServiceResponseVO = (CertServiceResponseVO) client.process(certServiceRequestVO);

            System.out.println(certServiceResponseVO.getResultCode());
            System.out.println(certServiceResponseVO.getResultMessage());
            if (RAClient.SUCCESS.equals(certServiceResponseVO.getResultCode())) {
            	CfcaElectronicChapter chapter = new CfcaElectronicChapter();
              /*  System.out.println(certServiceResponseVO.getDn());
                System.out.println(certServiceResponseVO.getSequenceNo());
                System.out.println(certServiceResponseVO.getSerialNo());
                System.out.println(certServiceResponseVO.getAuthCode());
                System.out.println(certServiceResponseVO.getStartTime());
                System.out.println(certServiceResponseVO.getEndTime());*/
                chapter.setUserId(work.getId());
                chapter.setDn(certServiceResponseVO.getDn());
            	chapter.setSequenceNo(certServiceResponseVO.getSequenceNo());
            	chapter.setSerialNo(certServiceResponseVO.getSerialNo());
            	chapter.setAuthCode(certServiceResponseVO.getAuthCode());
            	chapter.setStartTime(DateUtils.parseStringToDate(certServiceResponseVO.getStartTime()));
            	chapter.setEndTime(DateUtils.parseStringToDate(certServiceResponseVO.getEndTime()));
            	chapter.setResultCode(certServiceResponseVO.getResultCode());
            	chapter.setResultMessage(certServiceResponseVO.getResultMessage());
            	chapter.setStatus(certServiceResponseVO.getCertStatus());
            	super.save(chapter);
                return chapter;
            }
        } catch (RATKException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	@Transactional(readOnly = false)
	public boolean applyAssociationCfcaElectronicChapter(CfcaElectronicChapter cfcaElectronicChapter) {
		AssociationInfo association =associationInfoDao.get(cfcaElectronicChapter.getUserId());
		return applyAssociationCfcaEC(association);
	}
	
	private boolean applyAssociationCfcaEC(AssociationInfo work){
		System.out.println(System.currentTimeMillis());
        // String locale = "zh_CN";
		//ca名称
		String caName = "OCA33";
        // 普通证书 普通：1 高级：2
       
        String certType = "1";       			// 证书类型标识（复合证书证书类型用'-'连接，其中1：签名单证，2：双证，3-加密单证） 复合证书 单单1-1 单双1-2 双单2-1 双双2-2 
        String customerType = "2";				// 个人-1  企业-2   服务器-3   设备证书-6   场景证书-7  
        
        String userName = work.getAssociationName();     		// 用户姓名
        // String userNameInDn = "testName";
        // String userIdent = "Z1234567890";
        String identType = "N";    				// 证件类型 ‘0’： 居民身份证
        String identNo = work.getOrgNum();  // 证件号
        //String keyAlg = "RSA"; 					//加密算法 默认 “RSA”
        //String keyLength = "2048"; 				//密钥长度 默认 “2048”
        String branchCode = "678";                //证书所属机构
        // String email = "tdest";
        // String phoneNo = "12345678";
        // String address = "address";
        // String duration = "24";
        // String endTime = "20160101000001"; // endTime与duration同时非空时，证书截止时间以endTime为准，duration作为证书默认有效期记入数据库
        // String addIdentNoExt = "false";
        // String addEmailExt = "false";
        // String selfExtValue = "extValue";
        // 设备标识
        String deviceIdentifier = "19+x,2.168.117.101";
        // 部门名称
        String departmentNameInCert = "部门名称";
        // 组织机构名称
        String organizationNameInCert = "组织机构名称";
        // 营业地点所在地市
        String locality = "北京";
        // 营业地点所在省
        String stateOrProvince = "北京";
        // 国家,不填默认为CN
        String country = "CN";
        // 开始时间(不能早于当前时间一定范围，该值可在sys.ini中配置)
        // String startTime = "20170903154323";
        try {
            RAClient client = CfcaRaConfig.getRAClient();

            CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
            certServiceRequestVO.setTxCode("2101");
			certServiceRequestVO.setCaName(caName);
            // certServiceRequestVO.setLocale(locale);
            certServiceRequestVO.setCertType(certType);
            certServiceRequestVO.setCustomerType(customerType);
            certServiceRequestVO.setUserName(userName);
            // certServiceRequestVO.setUserNameInDn(userNameInDn);
            // certServiceRequestVO.setUserIdent(userIdent);
            certServiceRequestVO.setIdentType(identType);
            certServiceRequestVO.setIdentNo(identNo);
            // certServiceRequestVO.setKeyLength(keyLength);
            // certServiceRequestVO.setKeyAlg(keyAlg);
            certServiceRequestVO.setBranchCode(branchCode);
            // certServiceRequestVO.setEmail(email);
            // certServiceRequestVO.setPhoneNo(phoneNo);
            // certServiceRequestVO.setAddress(address);
            // certServiceRequestVO.setDuration(duration);
            //certServiceRequestVO.setStartTime(startTime);
            // certServiceRequestVO.setEndTime(endTime);
            // certServiceRequestVO.setAddIdentNoExt(addIdentNoExt);
            // certServiceRequestVO.setAddEmailExt(addEmailExt);
            // certServiceRequestVO.setSelfExtValue(selfExtValue);
            certServiceRequestVO.setDeviceIdentifier(deviceIdentifier);
            certServiceRequestVO.setDepartmentNameInCert(departmentNameInCert);
            certServiceRequestVO.setOrganizationNameInCert(organizationNameInCert);
            certServiceRequestVO.setLocality(locality);
            certServiceRequestVO.setStateOrProvince(stateOrProvince);
            certServiceRequestVO.setCountry(country);
//            certServiceRequestVO.setDuration(duration);//设置有效期 单位月

            CertServiceResponseVO certServiceResponseVO = (CertServiceResponseVO) client.process(certServiceRequestVO);

            System.out.println(certServiceResponseVO.getResultCode());
            System.out.println(certServiceResponseVO.getResultMessage());
            if (RAClient.SUCCESS.equals(certServiceResponseVO.getResultCode())) {
            	CfcaElectronicChapter chapter = new CfcaElectronicChapter();
            	
               /* System.out.println(certServiceResponseVO.getDn());
                System.out.println(certServiceResponseVO.getSequenceNo());
                System.out.println(certServiceResponseVO.getSerialNo());
                System.out.println(certServiceResponseVO.getAuthCode());
                System.out.println(certServiceResponseVO.getStartTime());
                System.out.println(certServiceResponseVO.getEndTime());*/
                chapter.setUserId(work.getId());
                chapter.setDn(certServiceResponseVO.getDn());
            	chapter.setSequenceNo(certServiceResponseVO.getSequenceNo());
            	chapter.setSerialNo(certServiceResponseVO.getSerialNo());
            	chapter.setAuthCode(certServiceResponseVO.getAuthCode());
            	chapter.setStartTime(DateUtils.parseStringToDate(certServiceResponseVO.getStartTime()));
            	chapter.setEndTime(DateUtils.parseStringToDate(certServiceResponseVO.getEndTime()));
            	chapter.setResultCode(certServiceResponseVO.getResultCode());
            	chapter.setResultMessage(certServiceResponseVO.getResultMessage());
            	super.save(chapter);
            	return true;
            }else{
            	return false;
            }
        } catch (RATKException e) {
            e.printStackTrace();
            return false;
        }
	}

	@Transactional(readOnly = false)
	public void updateChapterImage(CfcaElectronicChapter cfcaElectronicChapter) {
		// TODO Auto-generated method stub
		this.cfcaElectronicChapterDao.updateChapterImage(cfcaElectronicChapter);
	}

    /**
     *
     * @param cfcaElectronicChapter
     * @return
     */
    @Transactional(readOnly = false)
    public boolean againApplyAssociationCfcaElectronicChapter(CfcaElectronicChapter cfcaElectronicChapter) {
        AssociationInfo association =associationInfoDao.get(cfcaElectronicChapter.getUserId());
        return applyAssociationCfcaEC(association);
    }

    /**
     * 与CFCA服务器同步证书状态
     * @param cfcaElectronicChapter
     * @return
     */
    @Transactional(readOnly = false)
    public boolean  synchronizationChapterStatus ( CfcaElectronicChapter cfcaElectronicChapter ){
        // String locale = "zh_CN";
         String dn = cfcaElectronicChapter.getDn();
        try {
            RAClient client = CfcaRaConfig.getRAClient();
            CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
            certServiceRequestVO.setTxCode("2801");
            // certServiceRequestVO.setLocale(locale);
            certServiceRequestVO.setDn(dn);
            CertServiceResponseVO certServiceResponseVO = (CertServiceResponseVO) client.process(certServiceRequestVO);
            System.out.println(certServiceResponseVO.getResultCode());
            System.out.println(certServiceResponseVO.getResultMessage());
            if (RAClient.SUCCESS.equals(certServiceResponseVO.getResultCode()) ) {
                String status = certServiceResponseVO.getCertStatus();
                if( cfcaElectronicChapter.getStatus()==null || !status.equals(cfcaElectronicChapter.getStatus())){
                    cfcaElectronicChapter.setStatus(status);
                    cfcaElectronicChapterDao.update(cfcaElectronicChapter); //更新状态
                }
                System.out.println(certServiceResponseVO.getCertStatus());
                System.out.println(certServiceResponseVO.getStartTime());
                System.out.println(certServiceResponseVO.getEndTime());
            }else if("3110".equals(certServiceResponseVO.getResultCode())){ //证书不存在
                cfcaElectronicChapter.setDelFlag("1");
                cfcaElectronicChapterDao.update(cfcaElectronicChapter); //更新状态
            }
        } catch (RATKException e) {
            e.printStackTrace();
            return false;
        }
        return  true;
    }

    /**
     * 与CFCA服务器同步证书状态
     * @param cfcaElectronicChapter
     * @return
     */
    @Transactional(readOnly = false)
    public boolean  updateChapterStatus ( CfcaElectronicChapter cfcaElectronicChapter ){
        // String locale = "zh_CN";
        String dn = cfcaElectronicChapter.getDn();
        // String locale = "zh_CN";
        //String dn = "CN=051@testName@Z1234567890@28,OU=Individual-1,OU=Local RA,O=CFCA TEST CA,C=CN";
        // String keyAlg = "RSA";
        // String keyLength = "2048";
        // String duration = "24";
        // String endTime = "20150101000000"; // endTime与duration同时非空时，证书截止时间以endTime为准，duration作为证书默认有效期记入数据库
        // String useOldKey = "true";
        try {
            //RAClient client = TestConfig.getRAClient();
            RAClient client = CfcaRaConfig.getRAClient();

            CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
            certServiceRequestVO.setTxCode("2701");
            // certServiceRequestVO.setLocale(locale);
            certServiceRequestVO.setDn(dn);
            // certServiceRequestVO.setKeyAlg(keyAlg);
            // certServiceRequestVO.setKeyLength(keyLength);
            // certServiceRequestVO.setDuration(duration);
             certServiceRequestVO.setEndTime(DateUtils.formatDate(cfcaElectronicChapter.getEndTime(),"yyyyMMddHHmmss"));
            // certServiceRequestVO.setUseOldKey(useOldKey);

            CertServiceResponseVO certServiceResponseVO = (CertServiceResponseVO) client.process(certServiceRequestVO);

            System.out.println(certServiceResponseVO.getResultCode());
            System.out.println(certServiceResponseVO.getResultMessage());
            if (RAClient.SUCCESS.equals(certServiceResponseVO.getResultCode())) {

                System.out.println(certServiceResponseVO.getSerialNo());
                System.out.println(certServiceResponseVO.getAuthCode());
                System.out.println(certServiceResponseVO.getStartTime());
                System.out.println(certServiceResponseVO.getEndTime());
                System.out.println(certServiceResponseVO.getCertStatus());

               // cfcaElectronicChapter.setDn(certServiceResponseVO.getDn());
               // cfcaElectronicChapter.setSequenceNo(certServiceResponseVO.getSequenceNo());
                cfcaElectronicChapter.setSerialNo(certServiceResponseVO.getSerialNo());
                cfcaElectronicChapter.setAuthCode(certServiceResponseVO.getAuthCode());
                cfcaElectronicChapter.setStatus(certServiceResponseVO.getCertStatus());
                cfcaElectronicChapter.setEndTime(DateUtils.parseStringToDate(certServiceResponseVO.getEndTime()));
                cfcaElectronicChapterDao.update(cfcaElectronicChapter); //更新状态
            }
        } catch (RATKException e) {
            e.printStackTrace();
        }
        return  true;
    }


    /**
     *  重新下发两码
     * @param cfcaElectronicChapter
     * @return
     */
    @Transactional(readOnly = false)
    public  boolean reSendTwoCode(CfcaElectronicChapter cfcaElectronicChapter){
        try {
            String status="";
            String dn = cfcaElectronicChapter.getDn();
            RAClient client = CfcaRaConfig.getRAClient();
            CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
            certServiceRequestVO.setTxCode("2801");
            // certServiceRequestVO.setLocale(locale);
            certServiceRequestVO.setDn(dn);
            CertServiceResponseVO certServiceResponseVO = (CertServiceResponseVO) client.process(certServiceRequestVO);
            System.out.println(certServiceResponseVO.getResultCode());
            System.out.println(certServiceResponseVO.getResultMessage());
            if (RAClient.SUCCESS.equals(certServiceResponseVO.getResultCode()) ) {
                status = certServiceResponseVO.getCertStatus();
                cfcaElectronicChapter.setStatus(status);
                   /* System.out.println(certServiceResponseVO.getCertStatus());
                    System.out.println(certServiceResponseVO.getStartTime());
                    System.out.println(certServiceResponseVO.getEndTime());*/
                if("3".equals(status)){ //只有状态为“未下载”的记录才可以重新下发两码
                    try {
                        CertServiceRequestVO certVO = new CertServiceRequestVO();
                        certVO.setTxCode("2301");
                        // certServiceRequestVO.setLocale(locale);
                        certVO.setDn(dn);
                        CertServiceResponseVO certServiceResponseVO1 = (CertServiceResponseVO) client.process(certVO);
                        System.out.println(certServiceResponseVO1.getResultCode());
                        System.out.println(certServiceResponseVO1.getResultMessage());
                        if (RAClient.SUCCESS.equals(certServiceResponseVO1.getResultCode())) {
                            //System.out.println(certServiceResponseVO1.getSerialNo());
                            //System.out.println(certServiceResponseVO1.getAuthCode());
                            cfcaElectronicChapter.setSerialNo(certServiceResponseVO1.getSerialNo());
                            cfcaElectronicChapter.setAuthCode(certServiceResponseVO1.getAuthCode());
                            cfcaElectronicChapter.setStatus("3");
                        }
                    } catch (RATKException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                cfcaElectronicChapter.setUpdateBy( UserUtils.getUser() );
                cfcaElectronicChapter.setUpdateDate(new Date());
                cfcaElectronicChapterDao.update(cfcaElectronicChapter);
            }else if("3110".equals(certServiceResponseVO.getResultCode())){ //证书不存在
                cfcaElectronicChapterDao.delete(cfcaElectronicChapter); //删除
            }
        } catch (RATKException e) {
            e.printStackTrace();
        }
        return  true;
    }

    @Transactional(readOnly = false)
    public  boolean bathSendTwoCode( ){
       List <CfcaElectronicChapter>list =  cfcaElectronicChapterDao.findNoDonloadList( new CfcaElectronicChapter());
        for (CfcaElectronicChapter chapter : list) {
            reSendTwoCode(chapter);
        }
        return  true;
    }

    /**
     *  查询出序号为“2”的证书，用于删除重新申请的电子章
     * @return
     */
    public List<CfcaElectronicChapter> findNoDonloadList(){
        CfcaElectronicChapter parm_chapter = new CfcaElectronicChapter();
        parm_chapter.setSequenceNo("2");
        List <CfcaElectronicChapter>list =  cfcaElectronicChapterDao.findNoDonloadList(parm_chapter);
        return list ;
    }

    /**
     *  分页查询未下载电子证书的记录
     * @param page
     * @param cfcaElectronicChapter
     * @return
     */
    public Page<CfcaElectronicChapter> findNoDonloadList(Page page ,CfcaElectronicChapter cfcaElectronicChapter){
        cfcaElectronicChapter.setPage(page);
        page.setList(cfcaElectronicChapterDao.findNoDonloadList(cfcaElectronicChapter));
        return page;
    }


    /**
     * 批量删除重复数据
     * @return
     */
    @Transactional(readOnly = false)
    public  boolean deleteAgain( ){

        CfcaElectronicChapter parm_chapter = new CfcaElectronicChapter();
        parm_chapter.setSequenceNo("2");
        List <CfcaElectronicChapter>list =  cfcaElectronicChapterDao.findNoDonloadList(parm_chapter);
        int i=0;
        for (CfcaElectronicChapter chapter : list) {
            if(i++ >2)break;
            // String locale = "zh_CN";
            String dn = chapter.getDn();
            try {
                RAClient client = CfcaRaConfig.getRAClient();
                CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
                certServiceRequestVO.setTxCode("2201");
                // certServiceRequestVO.setLocale(locale);
                certServiceRequestVO.setDn(dn);

                CertServiceResponseVO certServiceResponseVO = (CertServiceResponseVO) client.process(certServiceRequestVO);

                System.out.println(certServiceResponseVO.getResultCode());
                System.out.println(certServiceResponseVO.getResultMessage());
                if (RAClient.SUCCESS.equals(certServiceResponseVO.getResultCode()) || "3110".equals(certServiceResponseVO.getResultCode())) {
                    //

                    cfcaElectronicChapterDao.delete(chapter);
                }
            } catch (RATKException e) {
                e.printStackTrace();
                return false;
            }
        }
        return  true;
    }
    public boolean getUkeyExist(String ukeyId){
       if(cfcaElectronicChapterDao.getUkeyIdCount(ukeyId)>0) {
           return true;
       }
       else{
           return false;
       }
    }

	
}