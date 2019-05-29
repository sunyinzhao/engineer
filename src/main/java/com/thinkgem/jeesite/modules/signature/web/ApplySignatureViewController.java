/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.signature.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseAttachment;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignature;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignaturePerson;
import com.thinkgem.jeesite.modules.signature.service.ApplySignaturePersonService;
import com.thinkgem.jeesite.modules.signature.service.ApplySignatureService;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 电子签章业绩Controller
 * @author xqg
 * @version 2018-09-03
 */
@Controller
@RequestMapping(value = "${adminPath}/signature/applySignatureView")
public class ApplySignatureViewController extends BaseController {

	@Autowired
	private ApplySignatureService applySignatureService;
	
	@Autowired
	private ApplySignaturePersonService applySignaturePersonService;
	
	
	@RequestMapping(value = "viewSignature")
	public String viewSignature( Model model,String id) {		
		model.addAttribute("id", id);
		return "modules/signature/viewSignature";
	} 
	
	//@RequestMapping(value = "readSignature")
	@RequestMapping("readSignature/id/{id}")
	public void readSignature (HttpServletResponse response ,@PathVariable String id) {
		
		ApplySignature applySignature =applySignatureService.get(id);
		
		FileInputStream fileInputStream =null;
		OutputStream outputStream =null;
		try {
			
			//项目签章PDF文件存放的基础路径
			String engineerBaseDir = Global.getUserfilesBaseDir() + Global.PROJECT_SIGNATURE_URL ;
			
			//当前登录企业上 的“项目签章PDF”文件存放的路径
	    	String filePath = engineerBaseDir+applySignature.getSignatureFilePath() ;
			File file = new File(filePath);
			fileInputStream = new FileInputStream(file);
			response.setHeader("Content-Disposition",
					"attachment;fileName=signature.pdf");
			response.setContentType("multipart/form-data");
			outputStream = response.getOutputStream();
			IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
            if (outputStream != null) {
                try {
                	outputStream.close(); // 关闭流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                	fileInputStream.close(); // 关闭流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

		}
	}
}