/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.certificate.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cfca.paperless.client.bean.PdfBean;
import cfca.paperless.client.bean.SignLocation;
import cfca.paperless.client.servlet.PaperlessClient;
import cfca.paperless.client.util.PaperlessClientUtil;
import cfca.paperless.client.util.StrategyUtil;
import cfca.paperless.util.GUID;
import cfca.paperless.util.IoUtil;
import cfca.paperless.util.StringUtil;
import cfca.sadk.util.Base64;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.modules.associationinfo.service.AssociationInfoService;
import com.thinkgem.jeesite.modules.associationinfo.entity.AssociationInfo;
import com.thinkgem.jeesite.modules.cfca.util.PaperlessConfig;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseCertificate;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.certificate.entity.EngineerCertificate;
import com.thinkgem.jeesite.modules.certificate.service.EngineerCertificateService;

import java.io.*;
import java.util.List;

/**
 * 咨询工程师证书Controller
 * @author xqg
 * @version 2018-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/certificate/engineerCertificate")
public class EngineerCertificateController extends BaseController {

	@Autowired
	private EngineerCertificateService engineerCertificateService;

    @Autowired
	private AssociationInfoService associationInfoService;
    
    @Autowired
    private EnterpriseWorkersService enterpriseWorkersService;
	
	@ModelAttribute
	public EngineerCertificate get(@RequestParam(required=false) String id) {
		EngineerCertificate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = engineerCertificateService.get(id);
		}
		if (entity == null){
			entity = new EngineerCertificate();
		}
		return entity;
	}
	
	@RequiresPermissions("certificate:engineerCertificate:view")
	@RequestMapping(value = {"list", ""})
	public String list(EngineerCertificate engineerCertificate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EngineerCertificate> page = engineerCertificateService.findPage(new Page<EngineerCertificate>(request, response), engineerCertificate); 
		model.addAttribute("page", page);
		return "modules/certificate/engineerCertificateList";
	}


    @RequiresPermissions("certificate:engineerCertificate:view")
    @RequestMapping(value = "getCertificateList")
    @ResponseBody
    public List<EngineerCertificate> getCertificateList(EngineerCertificate engineerCertificate, HttpServletRequest request, HttpServletResponse response, Model model ,Integer count) {

	    if(count ==null || count >20000 || count<0){
	        count=20;
        }

        Page<EngineerCertificate> pagePrm = new Page<EngineerCertificate>(request, response);
	    pagePrm.setPageSize(count);
        engineerCertificate.setCertificateOriginal("0");

        Page<EngineerCertificate> page = engineerCertificateService.findPage(pagePrm, engineerCertificate);
        List list = page.getList();
        //model.addAttribute("page", page);
        return list;
    }


	@RequiresPermissions("certificate:engineerCertificate:view")
	@RequestMapping(value = "form")
	public String form(EngineerCertificate engineerCertificate, Model model) {
		model.addAttribute("engineerCertificate", engineerCertificate);
		return "modules/certificate/engineerCertificateForm";
	}

	@RequiresPermissions("certificate:engineerCertificate:edit")
	@RequestMapping(value = "save")
	public String save(EngineerCertificate engineerCertificate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, engineerCertificate)){
			return form(engineerCertificate, model);
		}
		engineerCertificateService.save(engineerCertificate);
		addMessage(redirectAttributes, "保存咨询工程师证书成功");
		return "redirect:"+Global.getAdminPath()+"/certificate/engineerCertificate/?repage";
	}
	
	@RequiresPermissions("certificate:engineerCertificate:edit")
	@RequestMapping(value = "delete")
	public String delete(EngineerCertificate engineerCertificate, RedirectAttributes redirectAttributes) {
		engineerCertificateService.delete(engineerCertificate);
		addMessage(redirectAttributes, "删除咨询工程师证书成功");
		return "redirect:"+Global.getAdminPath()+"/certificate/engineerCertificate/?repage";
	}


	@RequiresPermissions("certificate:engineerCertificate:createCertificate")
	@RequestMapping(value = "createCertificate")
	public String createCertificate(EngineerCertificate engineerCertificate, RedirectAttributes redirectAttributes) {
		engineerCertificateService.delete(engineerCertificate);
		addMessage(redirectAttributes, "删除咨询工程师证书成功");
		return "redirect:"+Global.getAdminPath()+"/certificate/engineerCertificate/?repage";
	}


	@RequestMapping(value = "downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, Model model ,String type) {
		String savePath ="";
		String filename = "";
		if("1".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/CryptoKit.rar" ;
			filename ="cryptoKit.rar";
		}else if("2".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/setup-JYUKey.rar" ;
			filename ="key.rar";
		}else if("3".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/KeyCtrl.rar" ;
			filename ="KeyCtrl.rar";
		}else if("4".equals(type)){
			savePath = request.getSession().getServletContext().getRealPath("/")+"/download/setup-JYFP.rar" ;
			filename ="setup-JYFP.rar";
		}

		try {
			// path是指欲下载的文件的路径。
			File file = new File(savePath);
			// 取得文件的后缀名。
			String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(savePath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}



	@ResponseBody
	@RequestMapping(value = "engineerCertificateAvailable")
	public String engineerCertificateAvailable(@RequestParam(required=false) String workerId ) {
           EngineerCertificate certificate = engineerCertificateService.getByWorkerId(workerId);
           if(certificate!=null && StringUtils.isNotBlank(certificate.getCertificateOriginal())){
               return "Y";
           }else{
               return "N";
           }
	}





	@ResponseBody
    @RequestMapping(value = "getPdfHashCode")
    public String getPdfHashCode(@RequestParam(required=false) String engineerCertificateId, String serialNumber ) {
        long startTime = System.currentTimeMillis();// 结束时间
        System.out.println("getStartTime:"+startTime);
        String hashCode="";
        //工程师获得的证书
        EngineerCertificate certificate =   engineerCertificateService.get(engineerCertificateId);
        //签章人的基本信息（里面会有电子章的位置）
        AssociationInfo association = associationInfoService.getAssocicationBySerialNumber(serialNumber);
        JSONObject pam = new JSONObject();
        try {
            long timeStart = System.currentTimeMillis();// 开始时间
            //System.out.println(PaperlessConfig.extUrl);
            //初始化客户端对象
            PaperlessClient clientBean = new PaperlessClient(PaperlessConfig.extUrl, 10000, 60000);// 无纸化辅助接口的访问URL
            //待计算hash的pdf
            //给不同文件盖章  1 是正本盖章 2 是副本盖章
            String filePath = certificate.getCertificatePdf();
            //certificate.getCertificatePdf();//
            // String sealImagePath ="D:/engineer/apply/association/8e925e682f0347aaa107f10a1df4a15a.png";
            String sealImagePath =association.getSealPicUrl();
            byte[] pdf = FileUtils.readFileToByteArray(new File(filePath));
            //印模图片
            byte[] sealImage = FileUtils.readFileToByteArray(new File(sealImagePath));
            //签章位置
            SignLocation signLocation = new SignLocation(1,365,108);
            // 策略文件
            String strategyXml = StrategyUtil.createClientSealStrategyXml("业务", "china", sealImage, signLocation, "sha1");
            //System.out.println(strategyXml);
            // 操作员编码或者机构号
            String operatorCode = PaperlessConfig.operatorCode;
            // 渠道编码，可以为空
            String channelCode = PaperlessConfig.channelCode;

            String systemNo = GUID.generateId();//业务流水号
            // 获取时间戳的方式。默认值为0。0：实时访问CFCA 时间戳服务；1：使用从CFCA购置并在本地部署的时间戳服务器产品；
            String timestampChannel = "0";
            //计算hash x509Cert参数可以为空，为空时外部合成签名只能是pkcs7
            String returnMessage = clientBean.autoCalculatePdfHash(systemNo,pdf, null,strategyXml, operatorCode, channelCode, timestampChannel);

            /*System.out.println(systemNo);
            System.out.println(strategyXml);
            System.out.println(operatorCode);
            System.out.println(channelCode);
            System.out.println(timestampChannel);*/

            String code = StringUtil.getNodeText(returnMessage, "Code");
            if ("200".equals(code)) {
                System.out.println("OK:autoCalculatePdfHash");
                /*String pdfSealHash = StringUtil.getNodeText(returnMessage, "PdfSealHash");//pdf签名hash的base64值
                String pdfSealSource = StringUtil.getNodeText(returnMessage, "PdfSealSource");//pdf签名原文的base64值*/
                String pdfHash = StringUtil.getNodeText(returnMessage, "PdfHash");//pdf原文的hashBase64
                String id = StringUtil.getNodeText(returnMessage, "Id");//标识,用来对应后台的缓存数据
                pam.put("pdfHash", pdfHash);
                pam.put("hashId", id);
               // System.out.println("pdf原文的hashBase64：" + pdfHash);
                //System.out.println("返回的ID：" + id);
            } else {
                System.out.println("获得PDF文档HASH值失败！" + returnMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long timeEnd = System.currentTimeMillis();// 结束时间


        System.out.println("GetTime:" + (timeEnd - startTime));
        return pam.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "signaturePdf")
    public String signaturePdf(@RequestParam(required=false) String engineerCertificateId,String signCode,String hashId ) {
        String result= "N";
        System.out.println("进入。。");
		System.out.println("engineerCertificateId:"+engineerCertificateId);
		System.out.println("signCode:"+signCode);
		System.out.println("hashId:"+hashId);
        //企业获得的证书
        EngineerCertificate certificate =   engineerCertificateService.get(engineerCertificateId);
		System.out.println("certificate:"+certificate.getName());
        //签章人的基本信息（里面会有电子章的位置）
        //AssociationInfo association = associationInfoService.getAssocicationBySerialNumber(serialNumber);

        //EnterpriseCertificate certificate = enterpriseCertificateService.get(enterpriseCertificateId) ;


        //项目签章PDF文件存放的基础路径
        String engineerSignatureBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_SIGNATURE_URL ;
        //当前登录企业上 的“项目签章PDF”文件存放的路径

        String engineerSignatureDir = FileUtils.path(engineerSignatureBaseDir+certificate.getWorkerId() + "/");
        //当前登录企业上 的“项目签章PDF”文件存放的路径
        File fileDir = new File(engineerSignatureDir);
        if(!fileDir.exists())//目录不存在则创建
            fileDir.mkdirs();
        //构建pdf文件
        String signaturePdfName=engineerSignatureDir+"/"+certificate.getWorkerId(); //证书记录ID 做为文件名
        JSONObject pam = new JSONObject();
        try {
            long timeStart = System.currentTimeMillis();// 开始时间
            // 操作员编码或者机构号
            String operatorCode = PaperlessConfig.operatorCode;
            // 渠道编码，可以为空
            String channelCode = PaperlessConfig.channelCode;
            PaperlessClient clientBean = new PaperlessClient(PaperlessConfig.extUrl, 10000, 60000);// 无纸化辅助接口的访问URL
            //byte[] signature = signCode.getBytes();//sigUtil.p7SignMessageDetach(Mechanism.SHA1_RSA, pdfHash.getBytes(), privateKey, new X509Cert(x509Cert), mySession);
           // System.out.println(hashId);
            //System.out.println(signCode);
            PdfBean pdfBean = new PdfBean("", hashId, "B001", "auto", "");
//	                pdfBean.setReturnPdfOrNot(false);//可以指定不返回pdf,默认返回pdf
            pdfBean.setPdfId(hashId);
            // String sealStrategyListXml = createSealStrategyListXml();
            // String filePath = "C:\\nas\\engineer\\uploadimages\\apply\\projectSignature\\433f5346cb5e4471ad9d5bf5003cfc72\\tmp.pdf";
          /*  byte[] sealedPdfData = clientBean.synthesizeOuterSignatureAndSealPdf(Base64.decode(signCode), PaperlessUtil.buildPdfBeanXml(pdfBean), "", operatorCode, channelCode);
            String errorRsString = PaperlessUtil.isError(sealedPdfData);*/
            
            byte[] sealedPdfData = clientBean.synthesizeOuterSignatureAndSealPdf(Base64.decode(signCode), PaperlessClientUtil.buildPdfBeanXml(pdfBean), "", operatorCode, channelCode,"true");
            String errorRsString = PaperlessClientUtil.isError(sealedPdfData);
            if ("".equals(errorRsString)) {
                System.out.println("OK:synthesizeOuterSignature");
                //System.out.println(sealedPdfData.length);
                if (pdfBean.isReturnPdfOrNot()) {
                    IoUtil.write(signaturePdfName, sealedPdfData);
                    certificate.setCertificateOriginal(signaturePdfName);
                    engineerCertificateService.save(certificate);
                } else {
                    System.out.println(new String(sealedPdfData, "UTF-8"));
                }
                result="Y";
            } else {
                // 处理结果为异常，打印出错误码和错误信息
                //System.out.println("NG:synthesizeOuterSignature");
                //System.out.println(sealedPdfData.length);
               // System.out.println(new String(sealedPdfData, "UTF-8"));
            }
            long timeEnd = System.currentTimeMillis();// 结束时间
            System.out.println("EndTime:" + (timeEnd));
            System.out.println("time:" + (timeEnd - timeStart));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    @RequiresPermissions("certificate:engineerSelfCertificate:view")
	@RequestMapping(value = "engineerCertificateInfo")
	public String engineerCertificateInfo(EngineerCertificate engineerCertificate, HttpServletRequest request, HttpServletResponse response, Model model) {
    	User user = UserUtils.getUser();
    	Page<EngineerCertificate> page = null;
    	if(user != null){
    		EnterpriseWorkers enterpriseWorkers = new EnterpriseWorkers();
    		enterpriseWorkers.setName(user.getName());
    		enterpriseWorkers.setCertificatesNum(user.getCardNumber());
    		//根据用户的姓名和身份证号获取workerId；
    		enterpriseWorkers = this.enterpriseWorkersService.findByCertificatesNumAndName(enterpriseWorkers);
    		engineerCertificate.setWorkerId(enterpriseWorkers.getId());
    		page = engineerCertificateService.findPage(new Page<EngineerCertificate>(request, response), engineerCertificate); 
    	}
    	model.addAttribute("page", page);
		return "modules/certificate/engineerCertificateInfo";
	}
    
    @RequiresPermissions("certificate:engineerSelfCertificate:view")
	@RequestMapping(value = "infoForm")
	public String infoForm(EngineerCertificate engineerCertificate, Model model) {
		model.addAttribute("engineerCertificate", engineerCertificate);
		return "modules/certificate/engineerCertificateInfoForm";
	}



}