package com.thinkgem.jeesite.modules.front.web;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import com.thinkgem.jeesite.modules.counselor.service.CounselorAttachmentService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.entity.PersonRecord;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.enterprise.service.PersonRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseCertificate;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseCertificateService;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * 获取证书Controller
 * @author ThinkGem
 * @version 2013-3-15
 */
@Controller
@RequestMapping(value = "/certificateInfo")
public class CertificateInfoController {

	@Autowired
	private EnterpriseCertificateService enterpriseCertificateService;
	@Autowired
	private EnterpriseWorkersService enterpriseWorkersService;
	@Autowired
	private PersonRecordService personRecordService;

	
	//@RequestMapping(value = "viewInfo", method=RequestMethod.GET)
	@RequestMapping("view/{view}")
	public String getCertificateInfo(@PathVariable String view,Model model) {
		/*EnterpriseCertificate enterpriseCertificate = new EnterpriseCertificate();
		enterpriseCertificate.setId(view);
		EnterpriseCertificate certificate = this.enterpriseCertificateService.get(enterpriseCertificate.getId());
		model.addAttribute("certificateInfo", certificate);
		return "/getinfo/getUserInfo";*/

        EnterpriseWorkers enterpriseWorker = this.enterpriseWorkersService.get(view);
		model.addAttribute("enterpriseWorker", enterpriseWorker);

		//历年执业登记记录
		PersonRecord personRecord = new PersonRecord();
		List<PersonRecord> personRcordeList = personRecordService.findByPersonId(enterpriseWorker.getId());
		model.addAttribute("personRcordeList", personRcordeList);
		//return "modules/sys/registrationForm";
        return "/getinfo/getUserInfo";


	}
	@RequestMapping(value="id/{id}",method = RequestMethod.GET)
	public void showImg(@PathVariable String id, HttpServletRequest request, HttpServletResponse response ) throws IOException {
        if(id!=null){
             EnterpriseWorkers workers = enterpriseWorkersService.getPicture(id);
            // path是指欲下载的文件的路径。
            if(workers!=null && StringUtils.isNotBlank(workers.getPictureUrl())  ){
                writeImage(workers.getPictureName(),workers.getPictureUrl(),request,response);
            }
        }
	}


	private void writeImage(String filename ,String filePath, HttpServletRequest request, HttpServletResponse response ) throws IOException{
		InputStream inputStream = null;
		OutputStream outputStream=null;

		File file = new File(filePath);

		try {
		// path是指欲下载的文件的路径。
		response.reset();
		// 设置response的Header
		response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
		response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/octet-stream");
		response.setContentType("multipart/form-data");

		//3. 以流的形式读取文件。
		inputStream = new BufferedInputStream(new FileInputStream(filePath));
		byte[] buffer = new byte[inputStream.available()];
		inputStream.read(buffer);
		inputStream.close();

		//4.将数据写出
		outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(buffer);
		outputStream.flush();
		outputStream.close();
		}catch (Exception  e){
			e.printStackTrace();
//			System.out.println("图片预览出错！");
		}finally{
			if (outputStream!=null){
				outputStream.close();
			}
			if(inputStream!=null){
				inputStream.close();
			}
		}
	}
}
