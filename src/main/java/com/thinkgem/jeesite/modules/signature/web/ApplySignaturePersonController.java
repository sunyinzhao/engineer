/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.signature.web;

import java.io.File;
import java.security.PrivateKey;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cfca.paperless.client.bean.PdfBean;
import cfca.paperless.client.servlet.PaperlessClient;
import cfca.paperless.client.util.PaperlessClientUtil;
import cfca.paperless.util.GUID;
import cfca.paperless.util.IoUtil;
import cfca.paperless.util.StringUtil;
import cfca.sadk.util.Base64;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.modules.associationinfo.entity.AssociationInfo;
import com.thinkgem.jeesite.modules.certificate.entity.EngineerCertificate;
import com.thinkgem.jeesite.modules.cfca.entity.CfcaElectronicChapter;
import com.thinkgem.jeesite.modules.cfca.service.CfcaElectronicChapterService;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;

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
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignature;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignaturePerson;
import com.thinkgem.jeesite.modules.signature.service.ApplySignaturePersonService;
import com.thinkgem.jeesite.modules.signature.service.ApplySignatureService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 签章咨询师Controller
 * @author xqg
 * @version 2018-09-03
 */
@Controller
@RequestMapping(value = "${adminPath}/signature/applySignaturePerson")
public class ApplySignaturePersonController extends BaseController {

	@Autowired
	private ApplySignaturePersonService applySignaturePersonService;
	@Autowired
	private ApplySignatureService applySignatureService;
	@Autowired
	private EnterpriseWorkersService enterpriseWorkersService;
    @Autowired
	private CfcaElectronicChapterService  cfcaElectronicChapterService;
	
	@ModelAttribute
	public ApplySignaturePerson get(@RequestParam(required=false) String id) {
		ApplySignaturePerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = applySignaturePersonService.getAllPro(id);
		}
		if (entity == null){
			entity = new ApplySignaturePerson();
		}
		return entity;
	}
	
/*	@RequiresPermissions("signature:applySignaturePerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(ApplySignaturePerson applySignaturePerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ApplySignaturePerson> page = applySignaturePersonService.findPage(new Page<ApplySignaturePerson>(request, response), applySignaturePerson); 
		model.addAttribute("page", page);
		return "modules/signature/applySignaturePersonList";
	}*/
	
	
	@RequiresPermissions("signature:applySignaturePerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(ApplySignaturePerson applySignaturePerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		User user= UserUtils.getUser();
		if(StringUtils.isNotBlank( user.getCardNumber()) 
			&& 	StringUtils.isNotBlank( user.getName()) 
				){
			//applySignaturePerson.setPersonId(UserUtils.getUser().getId());
			EnterpriseWorkers work =  applySignaturePerson.getEnterpriseWorkers()==null?new EnterpriseWorkers() :applySignaturePerson.getEnterpriseWorkers();
			
			work.setName(user.getName());
			work.setCertificatesNum(user.getCardNumber());			
			applySignaturePerson.setEnterpriseWorkers(work);   //查询出本人的签章数据
			
			
			Page<ApplySignaturePerson> page = applySignaturePersonService.findPageBuySelf(new Page<ApplySignaturePerson>(request, response), applySignaturePerson); 
			model.addAttribute("page", page);
		}		
		return "modules/signature/applySignaturePersonList";
	}

    /**
     *
     * @param id  个人签章记录Id
     * @param serialNumber  签章编号
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "getPdfHashCode")

	public String getPdfHashCode(@RequestParam(required=true) String  id,@RequestParam(required=true) String serialNumber,@RequestParam(required=true) String ukeyId) {

        JSONObject pam = new JSONObject();
        
        if(StringUtil.isEmpty(id ) || StringUtil.isEmpty(serialNumber)){ //传过来的参数有空值
        	   pam.put("result","A");  //有人进行签章请等待
        }else if (StringUtils.isEmpty(ukeyId)){
            pam.put("result","B");  //有人进行签章请等待
        }
        else{
			long startTime = System.currentTimeMillis();// 结束时间
	        //1.根据Id 得到个人签章信息(可以取到签章位置信息)
	        ApplySignaturePerson applySignaturePerson = applySignaturePersonService.get(id);
	        //2.根据签章文件Id:applySignatureId查询出要签章的记录
	        ApplySignature applySignature = applySignatureService.get(applySignaturePerson.getSignatureId());
	        //3.得到电子章文件
	        CfcaElectronicChapter electronicChapter_prm = new CfcaElectronicChapter();
	        electronicChapter_prm.setUserId(applySignaturePerson.getPersonId());//根据personId 进行签章
	        electronicChapter_prm.setSerialNo(serialNumber);
	        List<CfcaElectronicChapter> list   =  cfcaElectronicChapterService.findList(electronicChapter_prm);
	        if(list!=null && list.size()>0){
                boolean  ukeyIdEqual = false;  //是否可以签章
                //电子章信息
                CfcaElectronicChapter electronicChapter = list.get(0);
                if(StringUtils.isBlank(electronicChapter.getUkeyId())){ //第一次签章绑定硬件Id

                    if(cfcaElectronicChapterService.getUkeyExist(ukeyId)){// 此证书已被其他人使用
                        ukeyIdEqual = false;
                        pam.put("result", "C");  // 同一个Ukey中不可以存在多个电子章
                    }else{
                        electronicChapter.setUkeyId(ukeyId);
                        cfcaElectronicChapterService.update(electronicChapter);
                        ukeyIdEqual = true;
                    }

                }else {
                    if(electronicChapter.getUkeyId().equals(ukeyId)){
                        ukeyIdEqual = true;
                    }else {
                        ukeyIdEqual = false;
                        pam.put("result", "D");  // 请解除咨询师原电子章的绑定关系
                    }
                }

                if(ukeyIdEqual) {
                    //判断是否存在锁
                    if (applySignature.getLockTime() == null ||
                            applySignature.getLockPerson().equals(applySignaturePerson.getPersonId()) ||
                            System.currentTimeMillis() - applySignature.getLockTime() > 600000) { //没有人进行签章或 锁定时间超过10分钟

                        applySignature.setLockPerson(applySignaturePerson.getPersonId());
                        applySignature.setLockTime(System.currentTimeMillis());
                        applySignatureService.updateLock(applySignature);  //加锁
                        try {
                            System.out.println("getStartTime:" + startTime);
                            //4.签章Pdf路径
                            String filePath = applySignature.getSignatureFilePath();
                            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
                            String tempPdfName = filePath.replaceAll(fileName, "") + "temp/" + fileName;
		                /*File signaturePdfNameFile = new File(filePath);
		                File tempPdfNameFile = new File(tempPdfName);*/

                            long timeStart = System.currentTimeMillis();// 开始时间
                            //System.out.println(PaperlessConfig.extUrl);
                            //初始化客户端对象
                            PaperlessClient clientBean = new PaperlessClient(com.thinkgem.jeesite.modules.cfca.util.PaperlessConfig.extUrl, 10000, 60000);// 无纸化辅助接口的访问URL
                            //待计算hash的pdf
                            String sealImagePath = electronicChapter.getChapterImage();
                            byte[] pdf = FileUtils.readFileToByteArray(new File(tempPdfName));
                            //印模图片
                            byte[] sealImage = FileUtils.readFileToByteArray(new File(sealImagePath));
                            //签章位置
                            cfca.paperless.client.bean.SignLocation signLocation = new cfca.paperless.client.bean.SignLocation(applySignaturePerson.getAbsolutePage(), applySignaturePerson.getAbsoluteX(), applySignaturePerson.getAbsoluteY());
                            // 策略文件
                            String strategyXml = cfca.paperless.client.util.StrategyUtil.createClientSealStrategyXml("业务", "china", sealImage, signLocation, "sha1");
                            //System.out.println(strategyXml);
                            // 操作员编码或者机构号
                            String operatorCode = com.thinkgem.jeesite.modules.cfca.util.PaperlessConfig.operatorCode;
                            // 渠道编码，可以为空
                            String channelCode = com.thinkgem.jeesite.modules.cfca.util.PaperlessConfig.channelCode;
                            String systemNo = GUID.generateId();//业务流水号
                            // 获取时间戳的方式。默认值为0。0：实时访问CFCA 时间戳服务；1：使用从CFCA购置并在本地部署的时间戳服务器产品；
                            String timestampChannel = "0";
                            //计算hash x509Cert参数可以为空，为空时外部合成签名只能是pkcs7
                            String returnMessage = clientBean.autoCalculatePdfHash(systemNo, pdf, null, strategyXml, operatorCode, channelCode, timestampChannel);
                            String code = StringUtil.getNodeText(returnMessage, "Code");
                            if ("200".equals(code)) {
                                System.out.println("OK:autoCalculatePdfHash");
                                String pdfSealHash = StringUtil.getNodeText(returnMessage, "PdfSealHash");//pdf签名hash的base64值
                                String pdfSealSource = StringUtil.getNodeText(returnMessage, "PdfSealSource");//pdf签名原文的base64值
                                String pdfHash = StringUtil.getNodeText(returnMessage, "PdfHash");//pdf原文的hashBase64
                                String hashId = StringUtil.getNodeText(returnMessage, "Id");//标识,用来对应后台的缓存数据
                                pam.put("pdfHash", pdfHash);
                                pam.put("hashId", hashId);
                                pam.put("result", "Y");  //获取成功
                                // System.out.println("pdf原文的hashBase64：" + pdfHash);
                                //System.out.println("返回的ID：" + id);
                            } else {
                                System.out.println("获得PDF文档HASH值失败！" + returnMessage);
                                pam.put("result", "E");  //获取成功
                                pam.put("returnMessage", returnMessage);  //获取成功

                            }
                            long timeEnd = System.currentTimeMillis();// 结束时间
                            System.out.println("GetTime:" + (timeEnd - startTime));
                        } catch (Exception e) {
                            e.printStackTrace();
                            applySignature.setLockPerson(null);
                            applySignature.setLockTime(null);
                            applySignatureService.updateLock(applySignature);  //释放锁
                            pam.put("result", "E");  //获取失败
                            pam.put("returnMessage", "获得PDF文档HASH值失败");  //获取成功
                        }
                    } else {
                        pam.put("result", "N");  //有人进行签章请等待
                    }
                }else {

                }

	        }else{
	        	pam.put("result","BLANK");  //没有电子章
	        }
        }
		return pam.toJSONString();
	}



	@ResponseBody
	@RequestMapping(value = "signaturePdf")
	public String signaturePdf(@RequestParam(required=false) String id,String signCode,String hashId ) {
		String result= "N";
		//企业获得的证书
        //1.根据Id 得到个人签章信息(可以取到签章位置信息)
        ApplySignaturePerson applySignaturePerson = applySignaturePersonService.get(id);
        //2.根据签章文件Id:applySignatureId查询出要签章的记录
        ApplySignature applySignature = applySignatureService.get(applySignaturePerson.getSignatureId());

		//EngineerCertificate certificate =   engineerCertificateService.get(engineerCertificateId);
		//签章人的基本信息（里面会有电子章的位置）
		//AssociationInfo association = associationInfoService.getAssocicationBySerialNumber(serialNumber);

		//EnterpriseCertificate certificate = enterpriseCertificateService.get(enterpriseCertificateId) ;


		//项目签章PDF文件存放的基础路径
		String engineerSignatureBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_SIGNATURE_URL ;
		//当前登录企业上 的“项目签章PDF”文件存放的路径

		/*String engineerSignatureDir = FileUtils.path(engineerSignatureBaseDir+certificate.getWorkerId() + "/");
		//当前登录企业上 的“项目签章PDF”文件存放的路径
		File fileDir = new File(engineerSignatureDir);
		if(!fileDir.exists())//目录不存在则创建
			fileDir.mkdirs();*/
		//构建pdf文件
		//String signaturePdfName=engineerSignatureDir+"/"+certificate.getWorkerId(); //证书记录ID 做为文件名
        String signaturePdfName=applySignature.getSignatureFilePath();

        String fileName = signaturePdfName.substring(signaturePdfName.lastIndexOf("/")+1);
        String tempPdfName = signaturePdfName.replaceAll(fileName,"")+"temp/"+fileName;
        File signaturePdfNameFile = new File(signaturePdfName);
        File tempPdfNameFile = new File(tempPdfName);
		JSONObject pam = new JSONObject();

		if(applySignature.getLockTime() !=null &&
                System.currentTimeMillis()- applySignature.getLockTime()< 600000&&
                applySignature.getLockPerson().equals(applySignaturePerson.getPersonId())
                ){ //锁定时间不超过10分钟，并且是本人锁定
            try {

                    long timeStart = System.currentTimeMillis();// 开始时间
                    // 操作员编码或者机构号
                    String operatorCode = com.thinkgem.jeesite.modules.cfca.util.PaperlessConfig.operatorCode;
                    // 渠道编码，可以为空
                    String channelCode = com.thinkgem.jeesite.modules.cfca.util.PaperlessConfig.channelCode;
                    PaperlessClient clientBean = new PaperlessClient(com.thinkgem.jeesite.modules.cfca.util.PaperlessConfig.extUrl, 10000, 60000);// 无纸化辅助接口的访问URL
                    //byte[] signature = signCode.getBytes();//sigUtil.p7SignMessageDetach(Mechanism.SHA1_RSA, pdfHash.getBytes(), privateKey, new X509Cert(x509Cert), mySession);
                    // System.out.println(hashId);
                    //System.out.println(signCode);
                    PdfBean pdfBean = new PdfBean("", hashId, "B001", "auto", "");
        //	                pdfBean.setReturnPdfOrNot(false);//可以指定不返回pdf,默认返回pdf
                    pdfBean.setPdfId(hashId);
                    // String sealStrategyListXml = createSealStrategyListXml();
                    // String filePath = "C:\\nas\\engineer\\uploadimages\\apply\\projectSignature\\433f5346cb5e4471ad9d5bf5003cfc72\\tmp.pdf";
//                    byte[] sealedPdfData = clientBean.synthesizeOuterSignatureAndSealPdf(Base64.decode(signCode), PaperlessUtil.buildPdfBeanXml(pdfBean), "", operatorCode, channelCode);
//                    String errorRsString = PaperlessUtil.isError(sealedPdfData);
                    byte[] sealedPdfData = clientBean.synthesizeOuterSignatureAndSealPdf(Base64.decode(signCode), PaperlessClientUtil.buildPdfBeanXml(pdfBean), "", operatorCode, channelCode,"true");
	                String errorRsString = PaperlessClientUtil.isError(sealedPdfData);
                    if ("".equals(errorRsString)) {
                        System.out.println("OK:synthesizeOuterSignature");
                        //System.out.println(sealedPdfData.length);
                        if (pdfBean.isReturnPdfOrNot()) {
                            IoUtil.write(signaturePdfName, sealedPdfData);
                            FileUtils.copyFileCover(signaturePdfName,tempPdfName,true);
                            applySignaturePerson.setStatus("1");
                            applySignaturePersonService.save(applySignaturePerson);
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
            }finally {
                applySignature.setLockPerson(null);
                applySignature.setLockTime(null);
                applySignatureService.updateLock(applySignature);  //加锁
            }
        }
		return result;
	}

	
	
	

	@RequiresPermissions("signature:applySignaturePerson:view")
	@RequestMapping(value = "form")
	public String form(ApplySignaturePerson applySignaturePerson, Model model) {
		model.addAttribute("applySignaturePerson", applySignaturePerson);
		return "modules/signature/applySignaturePersonForm";
	}

	@RequiresPermissions("signature:applySignaturePerson:edit")
	@RequestMapping(value = "save")
	public String save(ApplySignaturePerson applySignaturePerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, applySignaturePerson)){
			return form(applySignaturePerson, model);
		}
		applySignaturePersonService.save(applySignaturePerson);
		addMessage(redirectAttributes, "保存签章咨询工程师（投资）成功");
		return "redirect:"+Global.getAdminPath()+"/signature/applySignaturePerson/?repage";
	}
	
	@RequiresPermissions("signature:applySignaturePerson:edit")
	@RequestMapping(value = "delete")
	public String delete(ApplySignaturePerson applySignaturePerson, RedirectAttributes redirectAttributes) {
		applySignaturePersonService.delete(applySignaturePerson);
		addMessage(redirectAttributes, "删除签章咨询工程师（投资）成功");
		return "redirect:"+Global.getAdminPath()+"/signature/applySignaturePerson/?repage";
	}

}