/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.signature.web;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cfca.ch.qos.logback.access.servlet.Util;

import com.itextpdf.text.*;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.HeaderFooter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.common.utils.ZxingHandler;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignature;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignaturePerson;
import com.thinkgem.jeesite.modules.signature.service.ApplySignaturePersonService;
import com.thinkgem.jeesite.modules.signature.service.ApplySignatureService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 电子签章业绩Controller
 * @author xqg
 * @version 2018-09-03
 */
@Controller
@RequestMapping(value = "${adminPath}/signature/applySignature")
public class ApplySignatureController extends BaseController {
	//华文琥珀
	private static final String fontPath = "/com/thinkgem/jeesite/common/fonts/STHUPO.TTF";
	//仿宋
	private static final String fontSongTiPath = "/com/thinkgem/jeesite/common/fonts/SIMFANG.TTF";
	

	@Autowired
	private ApplySignatureService applySignatureService;
	
	@Autowired
	private ApplySignaturePersonService applySignaturePersonService;
	
	
	@ModelAttribute
	public ApplySignature get(@RequestParam(required=false) String id) {
		ApplySignature entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = applySignatureService.get(id);
		}
		if (entity == null){
			entity = new ApplySignature();
			entity.setStatus("0");
		}
		return entity;
	}
	
	@RequiresPermissions("signature:applySignature:view")
	@RequestMapping(value = {"list", ""})
	public String list(ApplySignature applySignature, HttpServletRequest request, HttpServletResponse response, Model model) {


        String enterpriseId = UserUtils.getUser().getId();
        String userModel =UserUtils.getUser().getUserModel();
        String officeId =UserUtils.getUser().getOffice().getId();
        applySignature.setUserModel(userModel);
         applySignature.setEnterpriseId(enterpriseId);
         applySignature.setOfficeId(officeId);
         
		Page<ApplySignature> page = applySignatureService.findPage(new Page<ApplySignature>(request, response), applySignature); 


		model.addAttribute("page", page);
		return "modules/signature/applySignatureList";
	}

	@RequiresPermissions("signature:applySignature:view")
	@RequestMapping(value = "form")
	public String form(ApplySignature applySignature, Model model,String id) {
		if(StringUtils.isNotBlank(applySignature.getId()) ){
			ApplySignaturePerson applySignaturePerson = new ApplySignaturePerson();
			applySignaturePerson.setSignatureId(applySignature.getId());
			
			CounselorAttachment counselorAttachment = new CounselorAttachment();
			
			counselorAttachment.setPid(id);
			List<T> list = applySignatureService.findListPid(counselorAttachment);
			model.addAttribute("attachList",list);
			List<ApplySignaturePerson> applySignaturePersonList= applySignaturePersonService.findList(applySignaturePerson);
			model.addAttribute("applySignaturePersonList", applySignaturePersonList);
		}
		model.addAttribute("applySignature", applySignature);
		return "modules/signature/applySignatureForm";
	}
	
	@RequiresPermissions("signature:applySignature:edit")
	@RequestMapping(value = "save")
	public String save(String submitType, ApplySignature applySignature, Model model, RedirectAttributes redirectAttributes,String id) {
		if (!beanValidator(model, applySignature)){
			return form(applySignature, model,id);
		}
		
		//修改人员职责
		if(applySignature.getPersonIds()!=null && applySignature.getPersonIds().length>0){
			ApplySignaturePerson applySignaturePerson = new ApplySignaturePerson();
			for (int i = 0; i < applySignature.getPersonIds().length; i++) {
				applySignaturePerson.setId(applySignature.getPersonIds()[i]);
				applySignaturePerson.setDuty(applySignature.getDutys()[i]);
				applySignaturePersonService.updateDuty(applySignaturePerson);
			}
		}
		
		if ("".equals(submitType) && applySignature.getStatus()!=null && "1".equals(applySignature.getStatus())	
					) { //修改保存时更改Pdf文件
				applySignature.setSignatureFilePath(createPDFFile(applySignature));
				copyFile(applySignature.getSignatureFilePath());
			}
		if (StringUtils.isNotBlank(submitType) && "1".equals(submitType) &&
			applySignature.getStatus()!=null && "1".equals(applySignature.getStatus())	
				) { //提交申请单时生成Pdf文件
			applySignature.setSignatureFilePath(createPDFFile(applySignature));
			copyFile(applySignature.getSignatureFilePath());
		}
		if ("1".equals(submitType) && applySignature.getStatus()!=null && "2".equals(applySignature.getStatus())	
					) { //归档时生成Pdf文件
				applySignature.setSignatureFilePath(createPDFFile(applySignature));
				copyFile(applySignature.getSignatureFilePath());
			}
		
		if(StringUtils.isBlank( applySignature.getId())){
			
			User user = UserUtils.getUser();
			if (StringUtils.isBlank(user.getId())){
				addMessage(redirectAttributes, "保存电子签章业绩失败");
				return "redirect:"+Global.getAdminPath()+"/signature/applySignature/?repage";
			}
			applySignature.setEnterpriseId(user.getId());
		}
		
		applySignatureService.save(applySignature);

		if( StringUtils.isNotBlank(applySignature.getStatus()) && !"0".equals(applySignature.getStatus())){
			ApplySignaturePerson applySignaturePerson = new ApplySignaturePerson();
			applySignaturePerson.setSignatureId(applySignature.getId());
            applySignaturePerson.setStatus("0");
			applySignatureService.updatesignature(applySignaturePerson);
		}


		addMessage(redirectAttributes, "保存电子签章业绩成功");
		if (StringUtils.isNotBlank(submitType) && "0".equals(submitType) ) {
			return "redirect:" + Global.getAdminPath() + "/signature/applySignature/form?id=" + applySignature.getId();
		} else {
			return "redirect:"+Global.getAdminPath()+"/signature/applySignature/?repage";
		}
		
		
	}


	public void copyFile(String filePath){

	    String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
	    String newFileName = filePath.replaceAll(fileName,"")+"temp/"+fileName;
	    FileUtils.copyFileCover(filePath,newFileName,true);

    }


	@RequiresPermissions("signature:applySignature:edit")
	@RequestMapping(value = "delete")
	public String delete(ApplySignature applySignature, RedirectAttributes redirectAttributes) {
		applySignatureService.delete(applySignature);
		addMessage(redirectAttributes, "删除电子签章业绩成功");
		return "redirect:"+Global.getAdminPath()+"/signature/applySignature/?repage";
	}
	@RequiresPermissions("signature:applySignature:edit")
	@RequestMapping(value = "cancellation")
	public String cancellation(ApplySignature applySignature, RedirectAttributes redirectAttributes) {
		applySignature.setStatus("3");
		applySignatureService.cancellation(applySignature);
		addMessage(redirectAttributes, "注销成功");
		return "redirect:"+Global.getAdminPath()+"/signature/applySignature/?repage";
	}
	
	@RequestMapping(value = "savePersonRelesion")
	public String savePersonRelesion(String workersId, ApplySignature applySignature, Model model, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNoneBlank(applySignature.getId())){
			
			ApplySignaturePerson applySignaturePerson = new ApplySignaturePerson();
			applySignaturePerson.setSignatureId(applySignature.getId());
			List<ApplySignaturePerson> applySignaturePersonList= applySignaturePersonService.findList(applySignaturePerson);
			
			String[] work=workersId.split(",");
			for (String workid : work) {
				if(personExistis(workid, applySignaturePersonList)){ //判断咨询师是否在申请单中，若存在不再添加
					continue;
				}
				ApplySignaturePerson person = new ApplySignaturePerson();
				person.setSignatureId(applySignature.getId());
				person.setPersonId(workid);
				person.setStatus("0");
				applySignaturePersonService.save(person);
				
			}
			addMessage(redirectAttributes, "添加咨询工程师（投资）成功");
			return "redirect:" + Global.getAdminPath() + "/signature/applySignature/form?id=" + applySignature.getId();
			
		}
		return "redirect:"+Global.getAdminPath()+"/signature/applySignature/?repage";
	}
	
	/**
	 * 
	 * @param personId  查询咨询师是否已经在申请单中
	 * @param applySignaturePersonList
	 * @return
	 */
	private boolean personExistis(String personId ,List<ApplySignaturePerson> applySignaturePersonList  ){
		if(applySignaturePersonList==null || applySignaturePersonList.size()<=0){

			return false;
		}else{
			for (ApplySignaturePerson applySignaturePerson : applySignaturePersonList) {
				if(personId.equals(applySignaturePerson.getPersonId())){
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	

	@RequestMapping(value = "deletePerson")
	public String deletePerson(ApplySignaturePerson applySignaturePerson, RedirectAttributes redirectAttributes) {
		applySignaturePersonService.delete(applySignaturePerson);
		addMessage(redirectAttributes, "删除咨询工程师（投资）成功");
		//return "redirect:"+Global.getAdminPath()+"/signature/applySignaturePerson/?repage";
		return "redirect:" + Global.getAdminPath() + "/signature/applySignature/form?id=" + applySignaturePerson.getSignatureId();
	}
	
	
	
	private String createPDFFile(ApplySignature applySignature ){
		/**
		 * 当前登录人ID
		 */
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return null;
		}
	    try {
	    	
	    	/**
	    	 文件目录结构
	    	上传文件的根目录				:(Global.getUserfilesBaseDir())+
	    	项目签章文件上传基础虚拟路径	:(Global.PROJECT_SIGNATURE_URL)+
	    	用户ID						:((Principal) UserUtils.getPrincipal())+
	    	签章申请单Id					:(applySignature.getId())
	    	
	    	**/
	    	
			//项目签章PDF文件存放的基础路径
			String engineerProjectSignatureBaseDir = Global.getUserfilesBaseDir() + Global.PROJECT_SIGNATURE_URL ;
			//当前登录企业上 的“项目签章PDF”文件存放的路径
	    	String upaloadDir = FileUtils.path(engineerProjectSignatureBaseDir+principal + "/");
	        File fileDir = new File(upaloadDir);
	        if(!fileDir.exists())//目录不存在则创建
	            fileDir.mkdirs();
	        
	        //构建pdf文件
	        String signaturePdfName=upaloadDir+applySignature.getId();
	        String relativePath = principal+"/"+applySignature.getId();
	        File signaturePdf = new File(signaturePdfName);//创建文件对象
	        
	        if(signaturePdf.exists()){
	            
	        }else{ //文件不存在 
	        	signaturePdf.createNewFile();
	        }
	        
	        Document document = null;
	        try {

                BaseFont bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 设置中文字体
                BaseFont fontSongTi = BaseFont.createFont(fontSongTiPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 设置中文字体
	            
	            //第一步：创建一个document对象。
	            document =  new Document(PageSize.A4, 0, 0, 60, 60);
	            //第二步：创建一个PdfWriter实例，将文件输出流指向一个文件。
	            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(signaturePdf));
	            writer.setStrictImageSequence(true);
	            


                //第三步：打开文档。
	            document.open();
                createPdfBaseInfoTable(document,bfChinese,fontSongTi,applySignature);
	            
	            ApplySignaturePerson applySignaturePerson = new ApplySignaturePerson();
				applySignaturePerson.setSignatureId(applySignature.getId());
				
	            List<ApplySignaturePerson> applySignaturePersonList= applySignaturePersonService.findList(applySignaturePerson);
	            int singaCount = 5; 		//每页签章的行数
	            int currentCount = 1;		//当前行数（第一页由基本数据占4行）
	            int currentPage = 1;        //当前页码
	            
	            for (int i=0 ; i<applySignaturePersonList.size();i++ ) {
	            	    
	            	ApplySignaturePerson signaPerson = applySignaturePersonList.get(i); 				
	            	if(i%3==0){
	            		Image QRCodeImage1 = null;
		 	            QRCodeImage1 = Image.getInstance(getUserQRCodeFileName(signaPerson.getPersonId())); QRCodeImage1.scaleToFit(100, 100);
		 	            
		 	            QRCodeImage1.setAbsolutePosition(20, (singaCount-currentCount)*100);
		 	            document.add(QRCodeImage1);
		 	            
		 	            /*Image image1 = null;
		 	            image1 = Image.getInstance("D://工作/中咨协会/电子公章/0中国工程咨询协会.png"); image1.scaleToFit(80, 80);
		 	            image1.setAbsolutePosition(115, (singaCount-currentCount)*100+10);
                        document.add(image1);*/
                        signaPerson.setAbsoluteX(115);
		 	            signaPerson.setAbsoluteY((singaCount-currentCount)*100+10);

		 	           
	            	}else if (i%3==1){
	            		Image QRCodeImage2 = null;
	    	            QRCodeImage2 = Image.getInstance(getUserQRCodeFileName(signaPerson.getPersonId())); QRCodeImage2.scaleToFit(100, 100);
	    	            QRCodeImage2.setAbsolutePosition(200, (singaCount-currentCount)*100);
	    	            document.add(QRCodeImage2);
	    	            /*Image image2 = null;
	    	            image2 = Image.getInstance("D://工作/中咨协会/电子公章/0中国工程咨询协会.png"); image2.scaleToFit(80, 80);
	    	            image2.setAbsolutePosition(295, (singaCount-currentCount)*100+10);
                        document.add(image2);*/
	    	            signaPerson.setAbsoluteX(295);
		 	            signaPerson.setAbsoluteY((singaCount-currentCount)*100+10);

	            	}else {
                        Image QRCodeImage3 = null;
                        QRCodeImage3 = Image.getInstance(getUserQRCodeFileName(signaPerson.getPersonId())); QRCodeImage3.scaleToFit(100, 100);
                        QRCodeImage3.setAbsolutePosition(380, (singaCount-currentCount)*100);
                        document.add(QRCodeImage3);
                        /*Image image2 = null;
                        image2 = Image.getInstance("D://工作/中咨协会/电子公章/0中国工程咨询协会.png"); image2.scaleToFit(80, 80);
                        image2.setAbsolutePosition(475, (singaCount-currentCount)*100+10);
                        document.add(image2);*/
                        signaPerson.setAbsoluteX(475);
                        signaPerson.setAbsoluteY((singaCount-currentCount)*100+10);

                        currentCount++;
                    }
	            	
	            	////
	            	int width =50;
	            	int height=30;
	            	BufferedImage image = new BufferedImage(width, height,  
	                        BufferedImage.TYPE_INT_BGR);  
	                Graphics g = image.getGraphics();  
	                g.setClip(0, 0, width, height);  
	                g.setColor(Color.white);  
	                g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景  
	                g.setColor(Color.black);// 在换成黑色  
	                 java.awt.Font fond =new java.awt.Font("微软雅黑", Font.NORMAL, 8);
	                g.setFont(fond);// 设置画笔字体  
	                /** 用于获得垂直居中y */  
	                
	                java.awt.Rectangle clip = g.getClipBounds();  
	                FontMetrics fm = g.getFontMetrics(fond);  
	                int ascent = fm.getAscent();  
	                int descent = fm.getDescent();  
	                int y = (clip.height - (ascent + descent)) / 2 + ascent;  
//	                for (int n = 0; n < 6; n++) {// 256 340 0 680  
	                    g.drawString(currentPage+"", 0, y);// 画出字符串  
//	                }  
	                
	                
	                ByteArrayOutputStream out = new ByteArrayOutputStream();
	                ImageIO.write(image, "png", out);
	                Image aa = null;
	                aa= Image.getInstance(out.toByteArray());
	                //aa.scaleToFit(100, 100);
	                aa.setAbsolutePosition(295, 20);
	                g.dispose();
	                document.add(aa);
	                signaPerson.setAbsolutePage(currentPage);
	 	            if(currentCount == singaCount){
	 	            	currentCount =1;
	 	            	currentPage++;
	 	            	document.newPage();
	 	            	createPdfBaseInfoTable(document,bfChinese,fontSongTi,applySignature);
	 	            }
	 	           applySignaturePersonService.updateAbsolute(signaPerson);
				}

	            //第四步：在文档中增加一个段落。    
	            
	        } catch (Exception e) {
	            e.printStackTrace();

	        }finally{
	            if(document!=null){
	                //第五步：关闭文档。 
	                document.close();
	            }
	        }			 
	        return signaturePdfName;
	        
	    } catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}

    /**
     * 设置pdf头部基本信息
     * @param document
     * @param bfChinese
     * @param fontSongTi
     * @param applySignature
     * @throws Exception
     */
    private void createPdfBaseInfoTable( Document document,BaseFont bfChinese ,BaseFont fontSongTi ,ApplySignature applySignature  ) throws Exception{
        Font headFont = new Font(bfChinese, 25, Font.NORMAL);// 设置字体大小
        Font contextTitleFont = new Font(fontSongTi,12, Font.BOLD);// 设置字体大小
        Font contextFont = new Font(fontSongTi,12, Font.NORMAL);// 设置字体大小

        Paragraph title = new Paragraph("业绩签章", headFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph title1 = new Paragraph(" ", headFont); //加入空行
        document.add(title1);
        
//        PdfPTable table1 = new PdfPTable(1);
//        PdfPCell cell13 = new PdfPCell();
//        Paragraph para4 = new Paragraph("第一页",contextFont);
//        cell13.setLeft(20);
//        cell13.setTop(100);
//        cell13.setPhrase(para4);
//        table1.addCell(cell13);
        
        
        //第四添加表格
        //生成三列表格
        PdfPTable table = new PdfPTable(4);
        //设置表格具体宽度
        //table.setTotalWidth(50);
        //设置每一列所占的长度
        table.setWidths(new float[]{20f, 20f, 20f,20f});
        
        table.addCell(new PdfPCell(new Phrase("单位名称：",contextTitleFont)));
        PdfPCell cell11 = new PdfPCell();
        Paragraph para1 = new Paragraph(Encodes.unescapeHtml(UserUtils.getUser().getName()),contextFont);
        cell11.setPhrase(para1);
        cell11.setColspan(3);
        table.addCell(cell11);

        table.addCell(new PdfPCell(new Phrase("项目名称：",contextTitleFont)));
        PdfPCell cell3 = new PdfPCell();
        Paragraph para3 = new Paragraph(Encodes.unescapeHtml(applySignature.getProjectName()) ,contextFont);
        cell3.setPhrase(para3);
        cell3.setColspan(3);
        table.addCell(cell3);
        
        table.addCell(new PdfPCell(new Phrase("所属专业：",contextTitleFont)));
        table.addCell(new PdfPCell(new Phrase(DictUtils.getDictLabel(applySignature.getProjectSpecialty(),"specialty_type",  " "),contextFont)));
        table.addCell(new PdfPCell(new Phrase("服务范围：",contextTitleFont)));
        table.addCell(new PdfPCell(new Phrase(DictUtils.getDictLabel(applySignature.getServices(),"service_rang",  " "),contextFont)));
//        
        table.addCell(new PdfPCell(new Phrase("投资额（万元）：",contextTitleFont)));
        table.addCell(new PdfPCell(new Phrase(applySignature.getProjectInvestAmount()==null ?"":applySignature.getProjectInvestAmount().toString(),contextFont)));
        table.addCell(new PdfPCell(new Phrase("地区：",contextTitleFont)));
        table.addCell(new PdfPCell(new Phrase(DictUtils.getDictLabel(applySignature.getArea(),"area",  " "),contextFont)));
//        
        table.addCell(new PdfPCell(new Phrase("建设规模：",contextTitleFont)));
        PdfPCell cell12 = new PdfPCell();
        Paragraph para2 = new Paragraph(Encodes.unescapeHtml(applySignature.getBuildScale()),contextFont);
        cell12.setPhrase(para2);
        cell12.setColspan(3);
        table.addCell(cell12);
//        
        table.addCell(new PdfPCell(new Phrase("项目性质：",contextTitleFont)));
        table.addCell(new PdfPCell(new Phrase(DictUtils.getDictLabel(applySignature.getProjectProperty(),"project_property",  " "),contextFont)));
        table.addCell(new PdfPCell(new Phrase("项目资金来源：",contextTitleFont)));
        table.addCell(new PdfPCell(new Phrase(DictUtils.getDictLabel(applySignature.getFundsSource(),"project_funds_source",  " "),contextFont)));
//        
        table.addCell(new PdfPCell(new Phrase("工程咨询成果完成日期：",contextTitleFont)));
        table.addCell(new PdfPCell(new Phrase( applySignature.getCompleteDate()==null?"":  DateUtils.formatDate(applySignature.getCompleteDate(), "yyyy-MM-dd") ,contextFont)));
        table.addCell(new PdfPCell(new Phrase("拟开工日期：",contextTitleFont)));
        table.addCell(new PdfPCell(new Phrase( applySignature.getPlanBeginDate()==null?"": DateUtils.formatDate(applySignature.getPlanBeginDate(), "yyyy-MM-dd") ,contextFont)));


        document.add(table);
//        document.add(table1);
    }


	private void createImage(Document document ,List applySignaturePersonList ){

    }



	/**
	 * 根据用户Id获取 咨询师二维码文件   若文件不存在刚生成一个新的
	 * @param workId
	 * @return
	 */
	private String getUserQRCodeFileName(String workId){
		/*//生成用户显示基本信息的二维码
		try {
			String contents=Global.getEngineerBaseInfoUrl()+userId;
			int width= Global.getQRCodeWidth();
			int height =Global.getQRCodeHeight();			
			//工程师附件基础
			String engineerBaseDir = Global.getUserfilesBaseDir()+Global.ENGINEER_BASE_URL+Global.ENGINEER_QR_CODE_URL;
			String imgPath = FileUtils.path(engineerBaseDir+userId);
			File QRCodeImage = new File(imgPath);
			if(QRCodeImage.exists()){
				return imgPath;
			}else{
				File dir = new File(engineerBaseDir);
		        if(!dir.exists())//目录不存在则创建
		            dir.mkdirs();
				ZxingHandler.encode2(contents, width, height, imgPath);
				return imgPath;
			}			
		} catch (Exception e) {
			
		}*/
		//String  principal = "workderId";//人员ID 做为目录
		if (workId == null){
			return null;
		}
		try {
			//项目签章PDF文件存放的基础路径
			String engineerSignatureBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_SIGNATURE_URL;
			//当前登录企业上 的“项目签章PDF”文件存放的路径
			String upaloadInitDir = FileUtils.path(engineerSignatureBaseDir + workId + "/") + "init/";
			String QRCodePath = upaloadInitDir + "/QRC/";
			File QRCPath = new File(QRCodePath);
			if (!QRCPath.exists())//目录不存在则创建
				QRCPath.mkdirs();
			String QRCodeImag = QRCodePath + "/" + workId;
			String viewUrl = Global.getViewEngineerCertificateUrl() + workId;
			ZxingHandler.encode2(viewUrl, 100, 100, QRCodeImag);
			return QRCodeImag;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 下载业绩签章文件
	 * @param applySignature
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "downloadFile")
	public void downloadFile( ApplySignature applySignature, HttpServletRequest request, HttpServletResponse response, Model model ) {
		String savePath =applySignature.getSignatureFilePath();
		String filename = "document.pdf";
		try {
			super.downloadFile(savePath,filename,response);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	/** 项目名称不重复**/
	@RequestMapping(value="checkProjectName")
	@ResponseBody
	public int checkProjectName( ApplySignature applySignature) {
		return applySignatureService.checkProjectName(applySignature);
	}
	
	@ResponseBody
	@RequestMapping(value = "deleteAttach")
	public void deleteAttach(CounselorAttachment counselorAttachment){
		applySignatureService.deleteattach(counselorAttachment);
	}
	/** 更改签章状态**/
	@RequestMapping(value="updatesignature")
	@ResponseBody
	public int updatesignature( ApplySignature applySignature,ApplySignaturePerson applySignaturePerson,String SignatureId,String submitType) {
		 String projectName = applySignature.getProjectName();
		 List<ApplySignaturePerson> list=applySignatureService.selectsignature(projectName);
		 SignatureId = list.get(0).getSignatureId();
		 applySignaturePerson.setSignatureId(SignatureId);
		 applySignaturePerson.setStatus("0");
		 return applySignatureService.updatesignature(applySignaturePerson); 
	}
	

}