/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.associationinfo.service.AssociationInfoService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseCertificate;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseCertificateService;

/**
 * 企业获得证书Controller
 * @author xqg
 * @version 2018-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/enterprise/enterpriseCertificate")
public class EnterpriseCertificateController extends BaseController {

	@Autowired
	private EnterpriseCertificateService enterpriseCertificateService;
	
	@Autowired
	private AssociationInfoService associationInfoService;
	
	@ModelAttribute
	public EnterpriseCertificate get(@RequestParam(required=false) String id) {
		EnterpriseCertificate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = enterpriseCertificateService.get(id);
		}
		if (entity == null){
			entity = new EnterpriseCertificate();
		}
		return entity;
	}
	
	@RequiresPermissions("enterprise:enterpriseCertificate:view")
	@RequestMapping(value = {"list", ""})
	public String list(EnterpriseCertificate enterpriseCertificate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EnterpriseCertificate> page = enterpriseCertificateService.findPage(new Page<EnterpriseCertificate>(request, response), enterpriseCertificate); 
		model.addAttribute("page", page);
		return "modules/enterprise/enterpriseCertificateList";
	}

	@RequiresPermissions("enterprise:enterpriseCertificate:view")
	@RequestMapping(value = "form")
	public String form(EnterpriseCertificate enterpriseCertificate, Model model) {
		model.addAttribute("enterpriseCertificate", enterpriseCertificate);
		return "modules/enterprise/enterpriseCertificateForm";
	}

	@RequiresPermissions("enterprise:enterpriseCertificate:edit")
	@RequestMapping(value = "save")
	public String save(EnterpriseCertificate enterpriseCertificate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, enterpriseCertificate)){
			return form(enterpriseCertificate, model);
		}
		enterpriseCertificateService.save(enterpriseCertificate);
		addMessage(redirectAttributes, "保存企业证书成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseCertificate/?repage";
	}
	
	@RequiresPermissions("enterprise:enterpriseCertificate:edit")
	@RequestMapping(value = "delete")
	public String delete(EnterpriseCertificate enterpriseCertificate, RedirectAttributes redirectAttributes) {
		enterpriseCertificateService.delete(enterpriseCertificate);
		addMessage(redirectAttributes, "删除企业证书成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseCertificate/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "getPdfHashCode")
	public String getPdfHashCode(@RequestParam(required=false) String enterpriseCertificateId ,String serialNumber) {
		String hashCode="";
		/*//企业获得的证书
		EnterpriseCertificate certificate = enterpriseCertificateService.get(enterpriseCertificateId) ;
		//签章人的基本信息（里面会有电子章的位置）
		AssociationInfo association = associationInfoService.getAssocicationBySerialNumber(serialNumber);
		
		
		 JSONObject pam = new JSONObject();
		 try {
	            long timeStart = System.currentTimeMillis();// 开始时间
	            System.out.println(PaperlessConfig.extUrl);
	            //初始化客户端对象
	            PaperlessClient clientBean = new PaperlessClient(PaperlessConfig.extUrl, 10000, 60000);// 无纸化辅助接口的访问URL

	            //待计算hash的pdf
	            String filePath = "C:\\nas\\engineer\\uploadimages\\apply\\projectSignature\\433f5346cb5e4471ad9d5bf5003cfc72\\tmp.pdf";
	            //certificate.getCertificatePdf();//
	            String sealImagePath ="D:/engineer/apply/association/8e925e682f0347aaa107f10a1df4a15a.png";
	           // association.getSealPicUrl(); 
	            String pdfSuffix = IoUtil.getFileNameSuffix(filePath);
	            if(!PaperlessConfig.PDF_FILE_SUFFIX.equals(pdfSuffix)){
	                throw new Exception("所选文件不是pdf文件");
	            }
	            byte[] pdf = FileUtils.readFileToByteArray(new File(filePath));

	            
	            String pngSuffix = IoUtil.getFileNameSuffix(sealImagePath);
	            if(!PaperlessConfig.PNG_FILE_SUFFIX.equals(pngSuffix)){
	                throw new Exception("所选文件不是png文件,签章图片文件只支持png格式图片");
	            }
	            //印模图片
	            byte[] sealImage = FileUtils.readFileToByteArray(new File(sealImagePath));
	            
	            //签章位置
	            SignLocation signLocation = new SignLocation(1,200,220);
	            //SignLocation signLocation = new SignLocation("代理人签名", "R", 20, -5);
	            // 策略文件
	            String strategyXml = StrategyUtil.createClientSealStrategyXml("业务", "china", sealImage, signLocation, "sha1");
	           // String sealLayer2Text = "苏才礼&";
	           // String sealLayer2TextStyle = "font-size:12;font-color:0000FF";
	            //String strategyXml = StrategyUtil.createClientSealStrategyXml("业务", "china", sealLayer2Text,sealLayer2TextStyle, signLocation, "sha1");
	            System.out.println(strategyXml);
	            // 操作员编码或者机构号
	            String operatorCode = PaperlessConfig.operatorCode;
	            // 渠道编码，可以为空
	            String channelCode = PaperlessConfig.channelCode;
	            String systemNo = GUID.generateId();//业务流水号
	            
	            // 获取时间戳的方式。默认值为0。0：实时访问CFCA 时间戳服务；1：使用从CFCA购置并在本地部署的时间戳服务器产品；
	            String timestampChannel = "0";
	            
	            //计算hash x509Cert参数可以为空，为空时外部合成签名只能是pkcs7
	            String returnMessage = clientBean.autoCalculatePdfHash(systemNo,pdf, null,strategyXml, operatorCode, channelCode, timestampChannel);

	            System.out.println(systemNo);
//	            System.out.println(cert.getEncoded());
	            System.out.println(strategyXml);
	            System.out.println(operatorCode);
	            System.out.println(channelCode);
	            System.out.println(timestampChannel);
	            
	            String code = StringUtil.getNodeText(returnMessage, "Code");
	            if ("200".equals(code)) {
	                System.out.println("OK:autoCalculatePdfHash");
	                String pdfSealHash = StringUtil.getNodeText(returnMessage, "PdfSealHash");//pdf签名hash的base64值
	                String pdfSealSource = StringUtil.getNodeText(returnMessage, "PdfSealSource");//pdf签名原文的base64值
	                String pdfHash = StringUtil.getNodeText(returnMessage, "PdfHash");//pdf原文的hashBase64
	                String id = StringUtil.getNodeText(returnMessage, "Id");//标识,用来对应后台的缓存数据
	                
	               
	                pam.put("pdfHash", pdfHash);
	                pam.put("hashId", id);
	                
	                System.out.println("pdf原文的hashBase64：" + pdfHash);
	                System.out.println("返回的ID：" + id);
	                
	  
	            } else {
	                System.out.println("获得PDF文档HASH值失败！" + returnMessage);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return pam.toJSONString();*/
		return null;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "signaturePdf")
	public String signaturePdf(@RequestParam(required=false) String enterpriseCertificateId,String signCode,String hashId ,String serialNumber) {
		/*String result= "N";
		//企业获得的证书
		EnterpriseCertificate certificate = enterpriseCertificateService.get(enterpriseCertificateId) ;
				
		 JSONObject pam = new JSONObject();
		 try {
	            long timeStart = System.currentTimeMillis();// 开始时间

	            	// 操作员编码或者机构号
	            	String operatorCode = PaperlessConfig.operatorCode;
	            	// 渠道编码，可以为空
	            	String channelCode = PaperlessConfig.channelCode;
	            	
	            	PaperlessClient clientBean = new PaperlessClient(PaperlessConfig.extUrl, 10000, 60000);// 无纸化辅助接口的访问URL
	                //byte[] signature = signCode.getBytes();//sigUtil.p7SignMessageDetach(Mechanism.SHA1_RSA, pdfHash.getBytes(), privateKey, new X509Cert(x509Cert), mySession);
	              
	            	System.out.println(hashId);
	            	System.out.println(signCode);
	            	
	            	
	                PdfBean pdfBean = new PdfBean("", hashId, "B001", "auto", "");
//	                pdfBean.setReturnPdfOrNot(false);//可以指定不返回pdf,默认返回pdf
	                pdfBean.setPdfId(hashId);
	               // String sealStrategyListXml = createSealStrategyListXml();
	                String filePath = "C:\\nas\\engineer\\uploadimages\\apply\\projectSignature\\433f5346cb5e4471ad9d5bf5003cfc72\\tmp.pdf";
	 	           
	                byte[] sealedPdfData = clientBean.synthesizeOuterSignatureAndSealPdf(Base64.decode(signCode), PaperlessUtil.buildPdfBeanXml(pdfBean), "", operatorCode, channelCode);
	                String errorRsString = PaperlessUtil.isError(sealedPdfData);
	                if ("".equals(errorRsString)) {
	                    System.out.println("OK:synthesizeOuterSignature");
	                    System.out.println(sealedPdfData.length);
	                    if (pdfBean.isReturnPdfOrNot()) {
	                        IoUtil.write("D://sealedPdf-" + new File(filePath).getName() + ".pdf", sealedPdfData);
	                    } else {
	                        System.out.println(new String(sealedPdfData, "UTF-8"));
	                    }
	                    result="Y";
	                } else {
	                    // 处理结果为异常，打印出错误码和错误信息
	                    System.out.println("NG:synthesizeOuterSignature");
	                    System.out.println(sealedPdfData.length);
	                    System.out.println(new String(sealedPdfData, "UTF-8"));
	                } 
	                long timeEnd = System.currentTimeMillis();// 结束时间
	                System.out.println("time:" + (timeEnd - timeStart));
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }*/
	
		
		return null;
		
	}
	
}