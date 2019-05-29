/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.certificate.entity.EngineerCertificate;
import com.thinkgem.jeesite.modules.certificate.service.EngineerCertificateService;
import com.thinkgem.jeesite.modules.cfca.entity.CfcaElectronicChapter;
import com.thinkgem.jeesite.modules.cfca.service.CfcaElectronicChapterService;
import com.thinkgem.jeesite.modules.enterprise.entity.PersonRecord;
import com.thinkgem.jeesite.modules.enterprise.service.PersonRecordService;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignature;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignaturePerson;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 企业中所有的人员Controller
 * @author xqg
 * @version 2018-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/enterprise/enterpriseWorkers")
public class EnterpriseWorkersController extends BaseController {
	//华文琥珀
	//private static final String fontPath = "/com/thinkgem/jeesite/common/fonts/STHUPO.TTF";

	//微软雅黑
	private static final String fontWryhPath = "/com/thinkgem/jeesite/common/fonts/微软雅黑.ttf";
	//仿宋
	private static final String fontSongTiPath = "/com/thinkgem/jeesite/common/fonts/SIMFANG.TTF";
	@Autowired
	private EnterpriseWorkersService enterpriseWorkersService;

	@Autowired
	private PersonRecordService personRecordService;
	@Autowired
	private EngineerCertificateService  engineerCertificateService;
    @Autowired
	private CfcaElectronicChapterService cfcaElectronicChapterService;

	
	@ModelAttribute
	public EnterpriseWorkers get(@RequestParam(required=false) String id) {
		EnterpriseWorkers entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = enterpriseWorkersService.get(id);
		}
		if (entity == null){
			entity = new EnterpriseWorkers();
			entity.setType("2");
		}
		return entity;
	}
	
	@RequiresPermissions("enterprise:enterpriseWorkers:view")
	@RequestMapping(value = {"list", ""})
	public String list(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request, HttpServletResponse response, Model model,String specialty) {
		//查询的时候有多一个specialty 数据.根据所选的专业数据 查询子专业或者主专业为 这条数据的列表
		enterpriseWorkers.setPid(UserUtils.getUser().getId());
		enterpriseWorkers.setRegisterMainSpecialty(specialty);
		//查看列表的时候不进行分页,并且排序
		//List<EnterpriseWorkers> list = enterpriseWorkersService.findList(enterpriseWorkers);
		Page<EnterpriseWorkers> page = enterpriseWorkersService.findPage(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
		model.addAttribute("page", page);
		return "modules/enterprise/enterpriseWorkersList";
	}
	
	
	@RequiresPermissions("enterprise:enterpriseWorkers:view")
	@RequestMapping(value = "listForWindow")
	public String listForWindow(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request, HttpServletResponse response, Model model,String specialty) {
		//查询的时候 可能有专业字段传递 查询
		if(specialty!=null){
			enterpriseWorkers.setRegisterMainSpecialty(specialty);
		}
		enterpriseWorkers.setPid(UserUtils.getUser().getId());
		Page<EnterpriseWorkers> page = enterpriseWorkersService.findPage(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
		model.addAttribute("page", page);		
		return "modules/enterprise/enterpriseWorkersListForWindow";
	}

	@RequiresPermissions("enterprise:enterpriseWorkers:view")
	@RequestMapping(value = "listApplyPerson")
	public String listApplyPerson(String tableType,String declareRecordId ,EnterpriseWorkers enterpriseWorkers, HttpServletRequest request, HttpServletResponse response, Model model) {
		enterpriseWorkers.setPid(UserUtils.getUser().getId());
		Page<EnterpriseWorkers> page = enterpriseWorkersService.findPage(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
		model.addAttribute("page", page);
		model.addAttribute("tableType", tableType);
		model.addAttribute("declareRecordId", declareRecordId);		
		return "modules/enterprise/enterpriseWorkersListApplyPerson";
	}

	
	

	@RequiresPermissions("enterprise:enterpriseWorkers:view")
	@RequestMapping(value = "form")
	public String form(EnterpriseWorkers enterpriseWorkers, Model model,String remark) {

        String pid = UserUtils.getUser().getId();
        Integer maxNo = enterpriseWorkersService.findMaxNo(pid);
		//当remark为1的时候,代表是添加页面,需要夹带fileNo参数
		if(remark!=null&&remark!="") {
            //先查询人员列表中最大的fileNo, 并进行加一,需要参数为pid
            //当i 为 null 的时候 赋值为1
            if (maxNo == null || maxNo == 0) {
                    maxNo = 1;
            } else {
                maxNo += 1;
            }
            String fileNo = String.format("%04d", maxNo);
            enterpriseWorkers.setFileNo(fileNo);
        }else{
            String fileNo = enterpriseWorkers.getFileNo();
            //判断这个人员是否有编号
            if(fileNo==null||fileNo.equals("")){
                //为空需要给他增加编号
                //判断maxNo是否为空,为空需要赋值为1,不为空+1
                if (maxNo == null || maxNo == 0) {
                    maxNo = 1;
                } else {
                    maxNo += 1;
                }
                fileNo = String.format("%04d", maxNo);
                enterpriseWorkers.setFileNo(fileNo);
                enterpriseWorkersService.save(enterpriseWorkers);
            }

        }
		model.addAttribute("enterpriseWorkers", enterpriseWorkers);
		return "modules/enterprise/enterpriseWorkersForm";
	}

	@RequiresPermissions("enterprise:enterpriseWorkers:edit")
	@RequestMapping(value = "save")
	public String save(EnterpriseWorkers enterpriseWorkers, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, enterpriseWorkers)){
			return form(enterpriseWorkers, model,null);
		}
		enterpriseWorkers.setPid(UserUtils.getUser().getId());
		enterpriseWorkersService.save(enterpriseWorkers);
		addMessage(redirectAttributes, "保存人员成功");
		return "redirect:"+Global.getAdminPath()+"/enterprise/enterpriseWorkers/?repage";
	}
	


	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
    @RequiresPermissions("enterprise:enterpriseWorkers:edit")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			String tmp = null;
			/**/
			List<EnterpriseWorkers> list = ei.getDataList(EnterpriseWorkers.class);
			for (EnterpriseWorkers workers : list){
				try{
					if(StringUtils.isBlank(workers.getName())) break;
					workers.setRegisterMainSpecialty(DictUtils.getDictValue(workers.getRegisterMainSpecialty(),"specialty_type", ""));
					workers.setRegisterAuxiliarySpecialty(DictUtils.getDictValue(workers.getRegisterAuxiliarySpecialty(),"specialty_type", ""));
					workers.setSex("男".equalsIgnoreCase(workers.getSex()) ? "1" : "2");
					enterpriseWorkersService.save(workers);
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>姓名 "+workers.getName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>姓名 "+workers.getName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/enterprise/enterpriseWorkers/list?repage";
	}
    
    
	
	@RequestMapping(value = "listForSignature")
	public String listForSignature(EnterpriseWorkers enterpriseWorkers, HttpServletRequest request, HttpServletResponse response, Model model,String specialty) {
		//查询的时候 可能有专业字段传递 查询
		if(specialty!=null){
			enterpriseWorkers.setRegisterMainSpecialty(specialty);
		}
		enterpriseWorkers.setPid(UserUtils.getUser().getId());
		enterpriseWorkers.setType("1");
		enterpriseWorkers.setIsValid("1");
		enterpriseWorkers.setIsFreeze("0");
		
		Page<EnterpriseWorkers> page = enterpriseWorkersService.findPage(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
		model.addAttribute("page", page);		
		return "modules/signature/selectPerson";
	}

	/**
	 * 当前所有有效的咨询师，可以在这里生成证书，或置为无效
	 * @param enterpriseWorkers
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value="availableWorkersList")
	public String availableWorkersList(EnterpriseWorkers enterpriseWorkers ,Model model,HttpServletResponse response,HttpServletRequest request){



		Page<EnterpriseWorkers> page = this.enterpriseWorkersService.availableWorkersList(new Page<EnterpriseWorkers>(request, response), enterpriseWorkers);
		model.addAttribute("page", page);
		return "modules/enterprise/availableWorkersList";
	}
	/**
	 *
	 * @param enterpriseWorkers
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value="createWorkerPdf")
	public void createWorkerPdf(EnterpriseWorkers enterpriseWorkers ,Model model,HttpServletResponse response,HttpServletRequest request){
		String result = "N";
		enterpriseWorkers.setIsRegister("");
		try {
			if(saveEngineerCertificate(enterpriseWorkers,request)){
			    result ="Y";
            }
		}catch (Exception e){
			e.printStackTrace();
		}
		super.renderString( response,  result, "text");
	}




	@RequestMapping(value="batchCreateWorkerPdf")
	public void batchCreateWorkerPdf(EnterpriseWorkers enterpriseWorkers ,Model model,HttpServletResponse response,HttpServletRequest request ,Integer count ){
		String result = "N";
		Page<EnterpriseWorkers> page = new Page<EnterpriseWorkers>();
		if(count==null){
            count = 50;  //每次处理的总条数
        }else if(count>100000){
		    count = 100000;
        }
		page.setPageSize(count);

		Page resoultPage = enterpriseWorkersService.availableWorkersList( page, enterpriseWorkers);

		if (resoultPage!=null && resoultPage.getList()!=null && resoultPage.getList().size()>0){
			List<EnterpriseWorkers> list = resoultPage.getList();
			System.out.println(list.size());
			for ( EnterpriseWorkers worker:list) {
				try {
					saveEngineerCertificate(worker,request);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		super.renderString( response,  "Y", "text");
	}



    @RequiresPermissions("cfca:electronicChapter:edit")
    @RequestMapping(value = "applyElectronicChapter")
    @ResponseBody
    public String applyElectronicChapter(EnterpriseWorkers enterpriseWorkers ,Model model,HttpServletResponse response,HttpServletRequest request) {
        CfcaElectronicChapter cfcaElectronicChapter = new CfcaElectronicChapter();
        cfcaElectronicChapter.setUserId(enterpriseWorkers.getId());
        boolean result = cfcaElectronicChapterService.applyCfcaElectronicChapter(cfcaElectronicChapter);
        if (result){
            return "true";
        }else{
            return "false";
        }
    }

    @RequiresPermissions("cfca:electronicChapter:edit")
    @RequestMapping(value = "batchApplyElectronicChapter")
    public void	batchApplyElectronicChapter(EnterpriseWorkers enterpriseWorkers ,Model model,HttpServletResponse response,HttpServletRequest request ,Integer count) {
        String result = "N";
        Page<EnterpriseWorkers> page = new Page<EnterpriseWorkers>();
        if(count==null){
            count = 50;  //每次处理的总条数
        }else if(count>100000){
            count = 100000;
        }
        page.setPageSize(count);
        enterpriseWorkers.setElectronicChapterFlag("0");//未申请
        Page resoultPage = enterpriseWorkersService.availableWorkersList( page, enterpriseWorkers);
        if (resoultPage!=null && resoultPage.getList()!=null && resoultPage.getList().size()>0){
            List<EnterpriseWorkers> list = resoultPage.getList();
            System.out.println(list.size());
            for ( EnterpriseWorkers worker:list) {
                try {
                    CfcaElectronicChapter cfcaElectronicChapter = new CfcaElectronicChapter();
                    cfcaElectronicChapter.setUserId(worker.getId());
                    cfcaElectronicChapterService.applyCfcaElectronicChapter(cfcaElectronicChapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        super.renderString( response,  "Y", "text");
    }



    /**
     *  生成证书记录，及pdf文件
     * @param enterpriseWorkers
     * @param request
     */
    private boolean saveEngineerCertificate(EnterpriseWorkers enterpriseWorkers ,HttpServletRequest request) {

        PersonRecord parm = new PersonRecord();
        parm.setPersonId(enterpriseWorkers.getId());
        PersonRecord personReocrd = personRecordService.getCreateCertificatePersonReocrd(parm);

        if(		personReocrd!=null &&
				enterpriseWorkers!=null &&
		    	StringUtils.isNotBlank(enterpriseWorkers.getCertificatesNum())  &&
				StringUtils.isNotBlank(enterpriseWorkers.getName())  &&
				StringUtils.isNotBlank(enterpriseWorkers.getSex())  &&
				enterpriseWorkers.getAollowDate()!=null &&
				StringUtils.isNotBlank(enterpriseWorkers.getCompanyName())  &&
				StringUtils.isNotBlank(enterpriseWorkers.getRegisterCertificateNum())  &&
				(StringUtils.isNotBlank(enterpriseWorkers.getRegisterMainSpecialty())  ||
						StringUtils.isNotBlank(enterpriseWorkers.getRegisterAuxiliarySpecialty())  )){

            EngineerCertificate  certificateprm = new EngineerCertificate();
            certificateprm.setBatchId((personReocrd.getBatchId()==null || personReocrd.getBatchId().trim().equals(""))?personReocrd.getId():personReocrd.getBatchId());
			certificateprm.setWorkerId(enterpriseWorkers.getId());

			//根据workerId 与 batchid 查询出所有
            List <EngineerCertificate>list = engineerCertificateService.findList(certificateprm); //是否已经存在证书
            EngineerCertificate  certificate ;
            if(list!=null && list.size()>0){
                certificate= list.get(0);                           //存在 编辑
            }else{
                certificate =new EngineerCertificate();             //不存在新建
            }
            certificate.setAwardOrg("中国工程咨询协会");//发证机构
            certificate.setBatchId((personReocrd.getBatchId()!=null && !personReocrd.getBatchId().equals("")?personReocrd.getBatchId():personReocrd.getId()));//设置批次ID
            certificate.setCertificateNum(enterpriseWorkers.getCertificatesNum());  //身份证号
            certificate.setName(enterpriseWorkers.getName());//姓名
            certificate.setSex(   DictUtils.getDictLabel(enterpriseWorkers.getSex() ,"sex","")); //性别
            certificate.setStartDate(enterpriseWorkers.getAollowDate()) ;//批准时间
            certificate.setEndDate(enterpriseWorkers.getValidDate());  //截止日期
            certificate.setPractisingCompany(enterpriseWorkers.getCompanyName()); //执业单位
            certificate.setSpecialtyMain( DictUtils.getDictLabel(enterpriseWorkers.getRegisterMainSpecialty(),"specialty_type","") );  //主专业
            certificate.setSpecialtyAuxiliary(DictUtils.getDictLabel(enterpriseWorkers.getRegisterAuxiliarySpecialty(),"specialty_type",""));//辅专业
            certificate.setWorkerId(enterpriseWorkers.getId());   //人员Id
			certificate.setRegisterCertificateNum(enterpriseWorkers.getRegisterCertificateNum());
            certificate.setCertificatePdf(null);
            certificate.setCertificateOriginal(null);
            String pdfPath = createWorkerPDFFile(certificate, enterpriseWorkers,personReocrd, request);
            if(pdfPath!=null && !pdfPath.trim().equals("")){  //创建PDF成功
                certificate.setCertificatePdf(pdfPath); //pdf文件路径
                //engineerCertificateService.save(certificate); //修改证书
				engineerCertificateService.save(certificate); //保存证书
                enterpriseWorkers.setZhengShuFlag("1");
                enterpriseWorkersService.updateZhengShuFlag(enterpriseWorkers); //更新证书状态为已经生成
                return true;
            }else{
                return false;
            }
        }
        return false;
    }



	private String createWorkerPDFFile(EngineerCertificate  certificate , EnterpriseWorkers enterpriseWorkers,PersonRecord personRecord, HttpServletRequest request){

        String signaturePdfName =null;
        /**
		 * 当前登录人ID
		 */
		String  principal = certificate.getWorkerId();//人员ID 做为目录
		if (principal == null){
			return null;
		}
		try {

			/**
			 文件目录结构
			 上传文件的根目录				:(Global.getUserfilesBaseDir())+
			 工程师证书文件上传基础虚拟路径	:(Global.ENGINEER_SIGNATURE_URL)+
			 用户ID						:((Principal) UserUtils.getPrincipal())+
			 签章申请单Id					: UUId

			 **/

			//项目签章PDF文件存放的基础路径
			String engineerSignatureBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_SIGNATURE_URL ;
			//当前登录企业上 的“项目签章PDF”文件存放的路径
			String upaloadInitDir = FileUtils.path(engineerSignatureBaseDir+principal + "/")+"init/";
			//File fileDir = new File(upaloadDir);
			File initDir = new File(upaloadInitDir);
			if(!initDir.exists())//目录不存在则创建
				initDir.mkdirs();

			//构建pdf文件
			//signaturePdfName=upaloadInitDir+certificate.getId(); //批次ID做为文件名
			signaturePdfName=upaloadInitDir+principal; //以workerId 做为文件名，保证一个用户只有一个证书存在



			String QRCodePath = upaloadInitDir+"/QRC/";
			File QRCPath = new File(QRCodePath);
			if(!QRCPath.exists())//目录不存在则创建
				QRCPath.mkdirs();
			String QRCodeImag=QRCodePath+"/"+principal;
			String viewUrl = Global.getViewEngineerCertificateUrl()+principal;
			ZxingHandler.encode2(viewUrl, 100, 100, QRCodeImag);

			File signaturePdf = new File(signaturePdfName);//创建文件对象
			if(!signaturePdf.exists()){ //创建PDF文件
				signaturePdf.createNewFile();
			}
			Document document = null;
			try {
				BaseFont bfChinese = BaseFont.createFont(fontSongTiPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 设置中文字体
				BaseFont fontWryh = BaseFont.createFont(fontWryhPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 设置中文字体

				Font headFont = new Font(bfChinese, 22, Font.BOLD);// 设置字体大小
				Font contextTitleFont = new Font(fontWryh,14, Font.BOLD);// 设置字体大小
				Font contextFont = new Font(fontWryh,14, Font.NORMAL);// 设置字体大小


				//第一步：创建一个document对象。
				document =  new Document(PageSize.A4, 0, 0, 60, 60);
				//第二步：创建一个PdfWriter实例，将文件输出流指向一个文件。
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(signaturePdf));
				writer.setStrictImageSequence(true);
				//第三步：打开文档。
				document.open();
				String backImagePath = request.getSession().getServletContext().getRealPath("/")+"/static/images/engineerCertificateBackGround.jpg";
				Image image0 = null;

				image0 = Image.getInstance(backImagePath); image0.scaleToFit(595, 842);
				image0.setAbsolutePosition(0, 0);
				document.add(image0);


                Paragraph title0 = new Paragraph(" ", headFont);
                title0.setAlignment(Element.ALIGN_CENTER);
                document.add(title0);


				Paragraph title = new Paragraph("中华人民共和国", headFont);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
                Paragraph title1 = new Paragraph("咨询工程师（投资）登记证书", headFont);
                title1.setAlignment(Element.ALIGN_CENTER);
                document.add(title1);

				Paragraph title2 = new Paragraph(" ", headFont); //加入空行
				document.add(title2);
				//第四添加表格
				//生成三列表格
				PdfPTable table = new PdfPTable(3);
				//设置表格具体宽度
				//table.setTotalWidth(50);
				//设置每一列所占的长度
				table.setWidths(new float[]{20f, 70f,20f});
				table.setLockedWidth(true);
				table.setTotalWidth(430);

				table.addCell(getPdfSell(32,15,contextTitleFont,"姓　　名：",null));
				table.addCell(getPdfSell(32,15,contextFont,certificate.getName(),null));
				table.addCell(getPdfSell(32,15,contextFont,"",null));

				table.addCell(getPdfSell(32,15,contextTitleFont,"性　　别：",null));
				table.addCell(getPdfSell(32,15,contextFont,certificate.getSex(),null));
				table.addCell(getPdfSell(32,15,contextFont,"",null));

				table.addCell(getPdfSell(32,15,contextTitleFont,"身份证号：",null));
				table.addCell(getPdfSell(32,15,contextFont,certificate.getCertificateNum(),null));
				table.addCell(getPdfSell(32,15,contextFont,"",null));

				table.addCell(getPdfSell(32,15,contextTitleFont,"证书编号：",null));
				table.addCell(getPdfSell(32,15,contextFont,certificate.getRegisterCertificateNum(),null));
				table.addCell(getPdfSell(32,15,contextFont,"",null));

				table.addCell(getPdfSell(32,15,contextTitleFont,"主 专 业 ：",null));
				table.addCell(getPdfSell(32,15,contextFont,certificate.getSpecialtyMain(),null));
				table.addCell(getPdfSell(32,15,contextFont,"",null));

				table.addCell(getPdfSell(32,15,contextTitleFont,"辅 专 业 ：",null));
				table.addCell(getPdfSell(32,15,contextFont,certificate.getSpecialtyAuxiliary(),null));
				table.addCell(getPdfSell(32,15,contextFont,"",null));

				table.addCell(getPdfSell(60,15,contextTitleFont,"执业单位：",null));
				table.addCell(getPdfSell(60,15,contextFont,certificate.getPractisingCompany(),null));
				table.addCell(getPdfSell(60,15,contextFont,"",null));


				table.addCell(getPdfSell(80,15,contextTitleFont,"有效期至：",null));
				table.addCell(getPdfSell(80,15,contextFont,	com.thinkgem.jeesite.common.utils.DateUtils.formatDate(certificate.getEndDate(), "yyyy年MM月dd日"),null));
				table.addCell(getPdfSell(80,15,contextFont,"",null));

				PdfPCell cell = getPdfSell(20,15,contextTitleFont,"本电子证书是咨询工程师（投资）的执业凭证。",Element.ALIGN_CENTER);
				cell.setColspan(3);
				table.addCell(cell);

				/*PdfPCell cell2 = getPdfSell(20,15,contextTitleFont,"准许持证人在规定的执业范围内和登记有效期内执业。",Element.ALIGN_CENTER);
				cell2.setColspan(3);
				table.addCell(cell2);*/

				PdfPCell cell3 = getPdfSell(20,15,contextTitleFont,"扫描左下方二维码可进行验证和查询。",Element.ALIGN_CENTER);
				cell3.setColspan(3);
				table.addCell(cell3);

				table.addCell(getPdfSell(100,15,contextFont,"",null));
				table.addCell(getPdfSell(100,15,contextFont,"",null));
				table.addCell(getPdfSell(100,15,contextFont,"",null));
				document.add(table);



				PdfPTable tableBottmo = new PdfPTable(2);
				//设置表格具体宽度
				//table.setTotalWidth(50);
				//设置每一列所占的长度
				tableBottmo.setWidths(new float[]{50f, 50f});
				tableBottmo.setLockedWidth(true);
				tableBottmo.setTotalWidth(430);
				tableBottmo.addCell(getPdfSell(32,15,contextTitleFont,"",null));
				tableBottmo.addCell(getPdfSell(32,15,contextTitleFont,"登记机构（章）：",null));
				tableBottmo.addCell(getPdfSell(32,15,contextTitleFont,"",null));
				tableBottmo.addCell(getPdfSell(32,15,contextTitleFont,"批准日期："+com.thinkgem.jeesite.common.utils.DateUtils.formatDate(certificate.getStartDate(), "yyyy年MM月dd日"),null));
				document.add(tableBottmo);

				/*加入二维码*/
				Image image1 ;
				image1 = Image.getInstance(QRCodeImag); image1.scaleToFit(120, 120);
				image1.setAbsolutePosition(60, 110);
				document.add(image1);

				/*加入头像*/
				try{
					Image image2 ;
					image2 = Image.getInstance(enterpriseWorkers.getPictureUrl()); image2.scaleToFit(100, 140);
					image2.setAbsolutePosition(410, 510);
					document.add(image2);
				}catch (Exception e){
					System.out.println("生成证书插入头像图片出错。。。");
					e.printStackTrace();
				}

				//第四步：在文档中增加一个段落。

			} catch (Exception e) {
                signaturePdfName = null;
				e.printStackTrace();

			}finally{
				if(document!=null){
					//第五步：关闭文档。
					document.close();
				}
			}
			//return relativePath;

		} catch (IOException e) {
            signaturePdfName = null;
			e.printStackTrace();
		}
		return signaturePdfName;
	}

	/**
	 * 初始化单元格
	 * @param minimumHeight
	 * @param disableBorderSide
	 * @param font
	 * @param value
	 * @return
	 */
	private PdfPCell getPdfSell(float minimumHeight ,int disableBorderSide ,Font font ,String value ,Integer alignment){

		PdfPCell cell = new PdfPCell();
		cell.setMinimumHeight(minimumHeight);
		cell.disableBorderSide(disableBorderSide);
		Paragraph paragraph =new Paragraph(value,font);
		if(alignment!=null){
			cell.setHorizontalAlignment(alignment);
		}
		cell.setPhrase(paragraph );
    	return cell;
	}
}