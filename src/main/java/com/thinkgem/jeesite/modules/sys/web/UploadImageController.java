/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.associationinfo.entity.AssociationInfo;
import com.thinkgem.jeesite.modules.associationinfo.service.AssociationInfoService;
import com.thinkgem.jeesite.modules.cfca.entity.CfcaElectronicChapter;
import com.thinkgem.jeesite.modules.cfca.service.CfcaElectronicChapterService;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import com.thinkgem.jeesite.modules.counselor.entity.SpecialtyTrain;
import com.thinkgem.jeesite.modules.counselor.service.CounselorAttachmentService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseAttachment;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseAttachmentService;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Map;

/**
 * 上传图片Controller
 * @author ThinkGem
 * @version 2013-5-15  
 */
@Controller
@RequestMapping(value = "${adminPath}/uploadImage")
public class UploadImageController extends BaseController {

	@Autowired
	private EnterpriseAttachmentService enterpriseAttachmentService;
	@Autowired
	private CounselorAttachmentService counselorAttachmentService;
	@Autowired
	private EnterpriseWorkersService enterpriesWorkersService;
	@Autowired
	private AssociationInfoService  associationInfoService;
	@Autowired
	private CfcaElectronicChapterService cfcaElectronicChapterService;

	@Autowired
	private AreaService areaService;
	
/*	@ModelAttribute("enterpriseAttachment")
	public EnterpriseAttachment get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return enterpriseAttachmentService.get(id);
		}else{
			return new EnterpriseAttachment();
		}
	}*/

	
	@RequestMapping(value = "openWindow")
	public String list( Model model,			
			String imagePid,
			String imageType,
			String imageIds,
			String imageOl
			) {
		
		model.addAttribute("imagePid", imagePid);
		model.addAttribute("imageType", imageType);
		model.addAttribute("imageIds", imageIds);
		model.addAttribute("imageOl", imageOl);
		return "modules/sys/uploadImageForWindow";
	}

	@RequestMapping(value = "openWindow1")
	public String list1( Model model,
						String imagePid,
						String imageType,
						String imageIds,
						String imageOl,
						 String tableId,
						 String tableType,
						 String remarks,
						 String expertId,
						 String typeNum
	) {
		model.addAttribute("imagePid", imagePid);
		model.addAttribute("imageType", imageType);
		model.addAttribute("imageIds", imageIds);
		model.addAttribute("imageOl", imageOl);
		model.addAttribute("tableId", tableId);
		model.addAttribute("tableType", tableType);
		model.addAttribute("remarks",remarks);
		model.addAttribute("expertId",expertId);
		model.addAttribute("typeNum",typeNum);
		return "modules/sys/uploadImageForWindow1";
	}
	
	/**
	 * 此处相当于页面跳转
	 * @param model
	 * @param imageIds
	 * @param imageOl
	 * @return
	 */
	@RequestMapping(value = "openWindow2")
	public String list2(Model model, String imageIds, String imageOl, String workerId) {
		model.addAttribute("imageIds", imageIds);
		model.addAttribute("imageOl", imageOl);
		model.addAttribute("workerId", workerId);
		return "modules/sys/uploadImageForWindow2";
	}


	/**
	 * 此处相当于页面跳转
	 * @param model
	 * @param imageIds
	 * @param imageOl
	 * @return
	 */
	@RequestMapping(value = "openWindow3")
	public String list3(Model model, String imageIds, String remarks,String imageOl, String Id,String imagePid,
			String imageType,String tableId,String tableType) {
		model.addAttribute("imageIds", imageIds);
		model.addAttribute("imageOl", imageOl);
		model.addAttribute("imagePid", imagePid);
		model.addAttribute("imageType", imageType);
		model.addAttribute("remarks",remarks);
		model.addAttribute("tableId", tableId);
		model.addAttribute("tableType", tableType);
		return "modules/sys/uploadImageForWindow3";
	}

	@RequestMapping(value = "openWindow5")
	public String list5(Model model, String imageIds, String imageOl, String workerId,String type,String tableType) {
		model.addAttribute("imageIds", imageIds);
		model.addAttribute("imageOl", imageOl);
		model.addAttribute("type", type);
		model.addAttribute("tableType", tableType);
		model.addAttribute("workerId", workerId);
		return "modules/sys/uploadImageForWindow5";
	}


	@RequestMapping(value = "openWindowEduAndTitle")
	public String openWindowEduAndTitle( Model model,
						String imagePid,
						String imageType,
						String imageIds,
						String imageOl
	) {

		model.addAttribute("imagePid", imagePid);
		model.addAttribute("imageType", imageType);
		model.addAttribute("imageIds", imageIds);
		model.addAttribute("imageOl", imageOl);
		return "modules/sys/uploadImageForWindowEduAndTitle";
	}


	@RequestMapping(value = "openWindowAssociation")
	public String listAssociation(Model model, String imageIds, String imageOl, String workerId) {
		model.addAttribute("imageIds", imageIds);
		model.addAttribute("imageOl", imageOl);
		model.addAttribute("workerId", workerId);
		return "modules/sys/uploadImageForWindowAssociation";
	}
	
	@RequestMapping(value = "openWindowElectronicChapter")
	public String listElectronicChapter(Model model, String imageIds, String imageOl, String workerId) {
		model.addAttribute("imageIds", imageIds);
		model.addAttribute("imageOl", imageOl);
		model.addAttribute("workerId", workerId);
		return "modules/sys/uploadImageForWindowElectronicChapter";
	}
	@RequestMapping(value = "openWindow6")
	public String openWindow6( Model model,			
			String imagePid,
			String imageType,
			String imageIds,
			String imageOl
			) {
		
		model.addAttribute("imagePid", imagePid);
		model.addAttribute("imageType", imageType);
		model.addAttribute("imageIds", imageIds);
		model.addAttribute("imageOl", imageOl);
		return "modules/sys/uploadImageForWindow6";
	}
	
    @RequestMapping("/uploader")
    public void upload(HttpServletRequest request,HttpServletResponse response ,
    		String imagePid,
			String imageType){    	
    	Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return ;
		}
		
		//工程师附件基础
		String engineerBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_BASE_URL ;
    	String upaloadUrl = FileUtils.path(engineerBaseDir+principal + "/");
    	
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
      
        File dir = new File(upaloadUrl);
        String img_url = upaloadUrl;//图片路径
        if(!dir.exists())//目录不存在则创建
            dir.mkdirs();
        for(MultipartFile file :files.values()){
           
            String newFileName=upaloadUrl+IdGen.uuid();
            File tagetFile = new File(newFileName);//创建文件对象
            img_url += newFileName;
            if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
                try {
                    tagetFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    file.transferTo(tagetFile);
                    
                    EnterpriseAttachment enterpriseAttachment =new EnterpriseAttachment();
                    enterpriseAttachment.setPid(imagePid);
                    enterpriseAttachment.setType(imageType);
                    enterpriseAttachment.setFileName(file.getOriginalFilename()); //原始文件名称
                    enterpriseAttachment.setPath(newFileName);
                    enterpriseAttachmentService.save(enterpriseAttachment);
                    renderString(response, enterpriseAttachment.getId(), "text");
                    
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
//	    System.out.println(img_url);
	    
	    
	}

	@RequestMapping("/uploader1")
	public void upload1(HttpServletRequest request,HttpServletResponse response ,
					   String imagePid,
					   String imageType,
						String tableId,
						String tableType,
						String remarks,
						String expertId,
						String typeNum
						){
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return ;
		}
		//工程师附件基础
		String counselorBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_BASE_URL ;
		String upaloadUrl = FileUtils.path(counselorBaseDir+principal + "/");

//		System.out.println("收到图片!");
		MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象

		File dir = new File(upaloadUrl);
//		System.out.println(upaloadUrl);
		String img_url = upaloadUrl;//图片路径
		if(!dir.exists())//目录不存在则创建
			dir.mkdirs();
		for(MultipartFile file :files.values()){
			String newFileName=upaloadUrl+IdGen.uuid();
			File tagetFile = new File(newFileName);//创建文件对象
			img_url += newFileName;
			if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
				try {
					tagetFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					file.transferTo(tagetFile);

					CounselorAttachment counselorAttachment =new CounselorAttachment();

					if(typeNum!=null&&!typeNum.equals("undefined")&&!typeNum.equals("")){
						counselorAttachment.setTypeNum(typeNum);
					}

					//10.24 当expertId 存在的时候,不设置type,tableId ,tableType 以及 remarks
					if(expertId!=null&&!expertId.equals("undefined")&&!expertId.equals("")){
						counselorAttachment.setPid(imagePid);
						counselorAttachment.setBackExpertId(expertId);
					}else {
						counselorAttachment.setPid(imagePid);
						counselorAttachment.setType(imageType);
						counselorAttachment.setTableId(tableId);
						counselorAttachment.setTableType(tableType);
						if (remarks != null && !remarks.equals("undefined")&&!remarks.equals("")) {
							counselorAttachment.setRemarks(remarks);
						}
					}
					counselorAttachment.setFileName(file.getOriginalFilename()); //原始文件名称
					counselorAttachment.setPath(newFileName);
					counselorAttachmentService.save(counselorAttachment);
					renderString(response, counselorAttachment.getId(), "text");

				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
//		System.out.println(img_url);


	}
	
	
	
	
	/**
	 * 将图片的路径保存到workers表中
	 * @param request
	 * @param response
	 * @param imagePid
	 * @param imageType
	 */
	@RequestMapping("/uploaderAssociation")
	public void uploaderAssociation(HttpServletRequest request, HttpServletResponse response, String imagePid, String imageType,
			String associationId) {
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return ;
		}
		//工程师附件基础
		String counselorBaseDir = Global.getUserfilesBaseDir() + Global.ASSOCIATION_BASE_URL ;
		String upaloadUrl = FileUtils.path(counselorBaseDir);

//		System.out.println("收到图片!");
		MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象

		File dir = new File(upaloadUrl);
//		System.out.println(upaloadUrl);
		String img_url = upaloadUrl;//图片路径
		if(!dir.exists())//目录不存在则创建
			dir.mkdirs();
		for(MultipartFile file :files.values()){
			String newFileName=upaloadUrl+IdGen.uuid();
			File tagetFile = new File(newFileName);//创建文件对象
			img_url += newFileName;
			if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
				try {
					tagetFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					file.transferTo(tagetFile);

					AssociationInfo associationInfo = new AssociationInfo();
					associationInfo.setId(associationId);
					associationInfo.setSealPicUrl(newFileName);
					associationInfo.setSealPicName(file.getOriginalFilename());
					//更新图片路径
					this.associationInfoService.updateSealUrl(associationInfo);
					renderString(response, associationId, "text");
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
//		System.out.println(img_url);


	}
	
	
	
	
	/**
	 * 将图片的路径保存到workers表中
	 * @param request
	 * @param response
	 * @param imagePid
	 * @param imageType
	 */
	@RequestMapping("/uploader2")
	public void upload2(HttpServletRequest request, HttpServletResponse response, String imagePid, String imageType,
			String workerId) {
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return ;
		}
		//工程师附件基础
		String counselorBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_BASE_URL ;
		String upaloadUrl = FileUtils.path(counselorBaseDir+principal + "/");

//		System.out.println("收到图片!");
		MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象

		File dir = new File(upaloadUrl);
//		System.out.println(upaloadUrl);
		String img_url = upaloadUrl;//图片路径
		if(!dir.exists())//目录不存在则创建
			dir.mkdirs();
		for(MultipartFile file :files.values()){
			String newFileName=upaloadUrl+IdGen.uuid();
			File tagetFile = new File(newFileName);//创建文件对象
			img_url += newFileName;
			if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
				try {
					tagetFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					file.transferTo(tagetFile);

					// CounselorAttachment counselorAttachment =new
					// CounselorAttachment();
					// counselorAttachment.setPid(imagePid);
					// counselorAttachment.setType(imageType);
					// counselorAttachment.setFileName(file.getOriginalFilename());
					// //原始文件名称
					// counselorAttachment.setPath(newFileName);
					//counselorAttachmentService.save(counselorAttachment);
					//根据workerId和图片的路径插入workers表中
					EnterpriseWorkers enterpriseWorkers = new EnterpriseWorkers();
					enterpriseWorkers.setId(workerId);
					enterpriseWorkers.setPictureUrl(newFileName);
					enterpriseWorkers.setPictureName(file.getOriginalFilename());
					//更新图片路径
					this.enterpriesWorkersService.updatePictureUrl(enterpriseWorkers);
					renderString(response, workerId, "text");
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
//		System.out.println(img_url);
		


	}

	@RequestMapping("/uploader3")
	public void upload3(HttpServletRequest request,HttpServletResponse response ,
					   String imagePid,
					   String imageType,
						String tableId,
						String tableType,
						String remarks,
						String expertId,
						String typeNum
						){
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return ;
		}
		//工程师附件基础
		String counselorBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_BASE_URL ;
		String upaloadUrl = FileUtils.path(counselorBaseDir+principal + "/");

//		System.out.println("收到图片!");
		MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象

		File dir = new File(upaloadUrl);
//		System.out.println(upaloadUrl);
		String img_url = upaloadUrl;//图片路径
		if(!dir.exists())//目录不存在则创建
			dir.mkdirs();
		for(MultipartFile file :files.values()){
			String newFileName=upaloadUrl+IdGen.uuid();
			File tagetFile = new File(newFileName);//创建文件对象
			img_url += newFileName;
			if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
				try {
					tagetFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					file.transferTo(tagetFile);

					CounselorAttachment counselorAttachment =new CounselorAttachment();

					if(typeNum!=null&&!typeNum.equals("undefined")&&!typeNum.equals("")){
						counselorAttachment.setTypeNum(typeNum);
					}

					//10.24 当expertId 存在的时候,不设置type,tableId ,tableType 以及 remarks
					if(expertId!=null&&!expertId.equals("undefined")&&!expertId.equals("")){
						counselorAttachment.setPid(imagePid);
						counselorAttachment.setBackExpertId(expertId);
					}else {
						counselorAttachment.setPid(imagePid);
						counselorAttachment.setType(imageType);
						counselorAttachment.setTableId(tableId);
						counselorAttachment.setTableType(tableType);
						if (remarks != null && !remarks.equals("undefined")&&!remarks.equals("")) {
							counselorAttachment.setRemarks(remarks);
						}
					}
					counselorAttachment.setFileName(file.getOriginalFilename()); //原始文件名称
					counselorAttachment.setPath(newFileName);
					counselorAttachmentService.save(counselorAttachment);
					renderString(response, counselorAttachment.getId(), "text");

				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
//		System.out.println(img_url);


	}
	


	/**
	 * 将图片的路径保存到workers表中
	 * @param request
	 * @param response
	 * @param imagePid
	 * @param imageType
	 */
	@RequestMapping("/uploader5")
	public void upload5(HttpServletRequest request, HttpServletResponse response, String imagePid, String imageType,
						String workerId,String type,String tableType,String imageToType) {
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return ;
		}
		//工程师附件基础
		String counselorBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_BASE_URL ;
		String upaloadUrl = FileUtils.path(counselorBaseDir+principal + "/");

//		System.out.println("收到图片!");
		MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象

		File dir = new File(upaloadUrl);
//		System.out.println(upaloadUrl);
		String img_url = upaloadUrl;//图片路径
		if(!dir.exists())//目录不存在则创建
			dir.mkdirs();
		for(MultipartFile file :files.values()){
			String newFileName=upaloadUrl+IdGen.uuid();
			File tagetFile = new File(newFileName);//创建文件对象
			img_url += newFileName;
			if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
				try {
					tagetFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					file.transferTo(tagetFile);

					CounselorAttachment counselorAttachment =new CounselorAttachment();
					//10.24 当expertId 存在的时候,不设置type,tableId ,tableType 以及 remarks
					counselorAttachment.setPid(workerId);
					counselorAttachment.setType(imageToType);
					counselorAttachment.setTableType(tableType);
					counselorAttachment.setFileName(file.getOriginalFilename()); //原始文件名称
					counselorAttachment.setPath(newFileName);
					counselorAttachmentService.save(counselorAttachment);
					renderString(response, counselorAttachment.getId(), "text");

				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}


	}


	
	/**
	 * 将图片的路径保存到workers表中
	 * @param request
	 * @param response
	 * @param imagePid
	 * @param imageType
	 */
	@RequestMapping("/uploader6")
	public void upload6(HttpServletRequest request, HttpServletResponse response, String imagePid, String imageType,
			String workerId) {
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return ;
		}
		//工程师附件基础
		String counselorBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_BASE_URL ;
		String upaloadUrl = FileUtils.path(counselorBaseDir+principal + "/");

//		System.out.println("收到图片!");
		MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象

		File dir = new File(upaloadUrl);
//		System.out.println(upaloadUrl);
		String img_url = upaloadUrl;//图片路径
		if(!dir.exists())//目录不存在则创建
			dir.mkdirs();
		for(MultipartFile file :files.values()){
			String newFileName=upaloadUrl+IdGen.uuid();
			File tagetFile = new File(newFileName);//创建文件对象
			img_url += newFileName;
			if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
				try {
					tagetFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					file.transferTo(tagetFile);
//					enterpriseAttachment.setPid(imagePid);
//                    enterpriseAttachment.setType(imageType);
//                    enterpriseAttachment.setFileName(file.getOriginalFilename()); //原始文件名称
//                    enterpriseAttachment.setPath(newFileName);
					 CounselorAttachment counselorAttachment =new CounselorAttachment();
					 counselorAttachment.setPid(imagePid);
					 counselorAttachment.setType(imageType);
					 counselorAttachment.setPath(newFileName);
					 counselorAttachment.setFileName(file.getOriginalFilename());
					//更新图片路径
					//this.enterpriesWorkersService.updatePictureUrl(counselorAttachment);
					counselorAttachmentService.save(counselorAttachment);
					renderString(response, counselorAttachment.getId(), "text");
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
//		System.out.println(img_url);


	}

    
	
	/**
	 * 将图片的路径保存到workers表中
	 * @param request
	 * @param response
	 * @param imagePid
	 * @param imageType
	 */
	@RequestMapping("/uploaderElectronicChapter")
	public void uploaderElectronicChapter(HttpServletRequest request, HttpServletResponse response, String imagePid, String imageType,
			String cfcaElectronicChapterId) {
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return ;
		}
		//工程师附件基础
		String counselorBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_BASE_URL ;
		String upaloadUrl = FileUtils.path(counselorBaseDir+principal + "/");

//		System.out.println("收到图片!");
		MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象

		File dir = new File(upaloadUrl);
		String img_url = upaloadUrl;//图片路径
		if(!dir.exists())//目录不存在则创建
			dir.mkdirs();
		for(MultipartFile file :files.values()){
			String newFileName=upaloadUrl+IdGen.uuid();
			File tagetFile = new File(newFileName);//创建文件对象
			img_url += newFileName;
			if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
				try {
					tagetFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					file.transferTo(tagetFile);

					//根据workerId和图片的路径插入workers表中
					CfcaElectronicChapter cfcaElectronicChapter = new CfcaElectronicChapter();
					cfcaElectronicChapter.setId(cfcaElectronicChapterId);
					cfcaElectronicChapter.setChapterImage(newFileName);
					//更新图片路径
					this.cfcaElectronicChapterService.updateChapterImage(cfcaElectronicChapter);
					renderString(response, cfcaElectronicChapterId, "text");
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}
    

    @RequestMapping("showImage/id/{id}")
    public String showImage(Model model,@PathVariable String id){ 
    	model.addAttribute("id", id);
    	return "modules/sys/uploadImageShow";
	}

    @RequestMapping("showImage/id2/{id}")
    public String showImage2(Model model,@PathVariable String id){ 
    	model.addAttribute("id", id);
    	return "modules/sys/uploadImageShow2";
	}
    @RequestMapping("showImage/id3/{id}")
    public String showImage3(Model model,@PathVariable String id){ 
    	model.addAttribute("id", id);
    	return "modules/sys/uploadImageShow3";
	}
    
    @RequestMapping(value="id/{id}",method = RequestMethod.GET)
	public void showImg(@PathVariable String id, HttpServletRequest request, HttpServletResponse response ) throws IOException {
		/*ServletOutputStream out = null;
		FileInputStream ips = null;*/
		InputStream inputStream = null;
		OutputStream outputStream=null;
		try {
				
			if(id!=null){
				CounselorAttachment counselorAttachment =	counselorAttachmentService.get(id);
				if(counselorAttachment!=null){
					if(counselorAttachment.getPath()!=null){
						
						// path是指欲下载的文件的路径。
						File file = new File(counselorAttachment.getPath());
						// 取得文件名。
						String filename =counselorAttachment.getFileName();
						
						response.reset();
						// 设置response的Header
						response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
						response.addHeader("Content-Length", "" + file.length());
						response.setContentType("application/octet-stream");
						response.setContentType("multipart/form-data");
						
						//3. 以流的形式读取文件。
						inputStream = new BufferedInputStream(new FileInputStream(counselorAttachment.getPath()));
						byte[] buffer = new byte[inputStream.available()];
						inputStream.read(buffer);
						inputStream.close();
			
						//4.将数据写出
						outputStream = new BufferedOutputStream(response.getOutputStream());
						outputStream.write(buffer);
						outputStream.flush();
						outputStream.close();						
					}
				}
			}
		}catch (FileNotFoundException  e){
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
	@RequestMapping(value="id1/{id}",method = RequestMethod.GET)
	public void showImg1(@PathVariable String id, HttpServletRequest request, HttpServletResponse response ) throws IOException {
		/*ServletOutputStream out = null;
		FileInputStream ips = null;*/
		InputStream inputStream = null;
		OutputStream outputStream=null;
		try {

			if(id!=null){
				EnterpriseAttachment enterpriseAttachment =	enterpriseAttachmentService.get(id);
				if(enterpriseAttachment!=null){
					if(enterpriseAttachment.getPath()!=null){

						// path是指欲下载的文件的路径。
						File file = new File(enterpriseAttachment.getPath());
						// 取得文件名。
						String filename =enterpriseAttachment.getFileName();

						response.reset();
						// 设置response的Header
						response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
						response.addHeader("Content-Length", "" + file.length());
						response.setContentType("application/octet-stream");
						response.setContentType("multipart/form-data");

						//3. 以流的形式读取文件。
						inputStream = new BufferedInputStream(new FileInputStream(enterpriseAttachment.getPath()));
						byte[] buffer = new byte[inputStream.available()];
						inputStream.read(buffer);
						inputStream.close();

						//4.将数据写出
						outputStream = new BufferedOutputStream(response.getOutputStream());
						outputStream.write(buffer);
						outputStream.flush();
						outputStream.close();
					}
				}
			}
		}catch (FileNotFoundException  e){
			e.printStackTrace();
			System.out.println("图片预览出错！");

		}finally{
			if (outputStream!=null){
				outputStream.close();
			}
			if(inputStream!=null){
				inputStream.close();
			}

		}
	}

	@RequestMapping(value="id2/{id}",method = RequestMethod.GET)
	public void showImg2(@PathVariable String id, HttpServletRequest request, HttpServletResponse response ) throws IOException {
		/*ServletOutputStream out = null;
		FileInputStream ips = null;*/
		InputStream inputStream = null;
		OutputStream outputStream=null;
		try {
				
			if(id!=null){
				//CounselorAttachment counselorAttachment =	counselorAttachmentService.get(id);
				EnterpriseWorkers workers = enterpriesWorkersService.getPicture(id);
				if(workers!=null){
					if(workers.getPictureUrl()!=null){
						
						// path是指欲下载的文件的路径。
						File file = new File(workers.getPictureUrl());//获取图片路径
						// 取得文件名。
						String filename =workers.getPictureName();//获取图片名
						
						response.reset();
						// 设置response的Header
						response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
						response.addHeader("Content-Length", "" + file.length());
						response.setContentType("application/octet-stream");
						response.setContentType("multipart/form-data");
						
						//3. 以流的形式读取文件。
						inputStream = new BufferedInputStream(new FileInputStream(workers.getPictureUrl()));
						byte[] buffer = new byte[inputStream.available()];
						inputStream.read(buffer);
						inputStream.close();
			
						//4.将数据写出
						outputStream = new BufferedOutputStream(response.getOutputStream());
						outputStream.write(buffer);
						outputStream.flush();
						outputStream.close();						
					}
				}
			}
		}catch (FileNotFoundException  e){
			e.printStackTrace();
			System.out.println("图片预览出错！");
			
		}finally{
			if (outputStream!=null){
				outputStream.close();
			}
			if(inputStream!=null){
				inputStream.close();
			}
		}
	}


	@RequestMapping(value="idAssociation/{id}",method = RequestMethod.GET)
	public void showImgAssociation(@PathVariable String id, HttpServletRequest request, HttpServletResponse response ) throws IOException {
		/*ServletOutputStream out = null;
		FileInputStream ips = null;*/
		InputStream inputStream = null;
		OutputStream outputStream=null;
		try {
				
			if(id!=null){
				//CounselorAttachment counselorAttachment =	counselorAttachmentService.get(id);
				AssociationInfo associationInfo = associationInfoService.get(id);
				if(associationInfo!=null){
					if(associationInfo.getSealPicUrl()!=null){
						
						// path是指欲下载的文件的路径。
						File file = new File(associationInfo.getSealPicUrl());//获取图片路径
						// 取得文件名。
						String filename =associationInfo.getSealPicName();//获取图片名
						
						response.reset();
						// 设置response的Header
						response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
						response.addHeader("Content-Length", "" + file.length());
						response.setContentType("application/octet-stream");
						response.setContentType("multipart/form-data");
						
						//3. 以流的形式读取文件。
						inputStream = new BufferedInputStream(new FileInputStream(associationInfo.getSealPicUrl()));
						byte[] buffer = new byte[inputStream.available()];
						inputStream.read(buffer);
						inputStream.close();
			
						//4.将数据写出
						outputStream = new BufferedOutputStream(response.getOutputStream());
						outputStream.write(buffer);
						outputStream.flush();
						outputStream.close();						
					}
				}
			}
		}catch (FileNotFoundException  e){
			e.printStackTrace();
			System.out.println("图片预览出错！");
			
		}finally{
			if (outputStream!=null){
				outputStream.close();
			}
			if(inputStream!=null){
				inputStream.close();
			}
		}
	}
	
	
	@RequestMapping(value="idCfcaElectronicChapter/{id}",method = RequestMethod.GET)
	public void showImgElectronicChapter(@PathVariable String id, HttpServletRequest request, HttpServletResponse response ) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream=null;
		try {
				
			if(id!=null){
				CfcaElectronicChapter cfcaElectronicChapter = this.cfcaElectronicChapterService.get(id);
				if(cfcaElectronicChapter!=null){
					if(cfcaElectronicChapter.getChapterImage()!=null){
						
						// path是指欲下载的文件的路径。
						File file = new File(cfcaElectronicChapter.getChapterImage());//获取图片路径
						// 取得文件名。
						//String filename =cfcaElectronicChapter.getSealPicName();//获取图片名
						
						response.reset();
						// 设置response的Header
						response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode("", "UTF-8"));
						response.addHeader("Content-Length", "" + file.length());
						response.setContentType("application/octet-stream");
						response.setContentType("multipart/form-data");
						
						//3. 以流的形式读取文件。
						inputStream = new BufferedInputStream(new FileInputStream(cfcaElectronicChapter.getChapterImage()));
						byte[] buffer = new byte[inputStream.available()];
						inputStream.read(buffer);
						inputStream.close();
			
						//4.将数据写出
						outputStream = new BufferedOutputStream(response.getOutputStream());
						outputStream.write(buffer);
						outputStream.flush();
						outputStream.close();						
					}
				}
			}
		}catch (FileNotFoundException  e){
			e.printStackTrace();
			System.out.println("图片预览出错！");
			
		}finally{
			if (outputStream!=null){
				outputStream.close();
			}
			if(inputStream!=null){
				inputStream.close();
			}
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "deleteWorkerPicture")
	public void deleteWorkerPicture(EnterpriseWorkers enterpriseWorkers){
		if(StringUtils.isNotBlank(enterpriseWorkers.getId())){
			this.enterpriesWorkersService.deletePicture(enterpriseWorkers);
		}
	}

	
	@ResponseBody
	@RequestMapping(value = "deleteAttach")
	public void deleteAttch(CounselorAttachment counselorAttachment){
		if(StringUtils.isNotBlank(counselorAttachment.getId())){
			this.counselorAttachmentService.delete(counselorAttachment);
		}
	}


	@ResponseBody
	@RequestMapping(value = "deleteCounselorPicture")
	public void deletePicture(CounselorAttachment counselorAttachment){
		if(StringUtils.isNotBlank(counselorAttachment.getId())){
			this.counselorAttachmentService.delete(counselorAttachment);
		}
	}

	@RequestMapping("/uploaderEduAndTitle")
	public void uploadEduAndTitle(HttpServletRequest request,HttpServletResponse response ,
						String imagePid,
						String imageType,
						String tableId,
						String tableType,
						String remarks,
						String typeNum){
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return ;
		}

		//工程师附件基础
		String engineerBaseDir = Global.getUserfilesBaseDir() + Global.ENGINEER_BASE_URL ;
		String upaloadUrl = FileUtils.path(engineerBaseDir+principal + "/");

		MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象

		File dir = new File(upaloadUrl);
		String img_url = upaloadUrl;//图片路径
		if(!dir.exists())//目录不存在则创建
			dir.mkdirs();
		for(MultipartFile file :files.values()){

			String newFileName=upaloadUrl+IdGen.uuid();
			File tagetFile = new File(newFileName);//创建文件对象
			img_url += newFileName;
			if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
				try {
					tagetFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					file.transferTo(tagetFile);


					CounselorAttachment counselorAttachment =new CounselorAttachment();

					if(typeNum!=null&&!typeNum.equals("undefined")&&!typeNum.equals("")){
						counselorAttachment.setTypeNum(typeNum);
					}


					counselorAttachment.setPid(imagePid);
					counselorAttachment.setType(imageType);
//					counselorAttachment.setTableId(tableId);
//					counselorAttachment.setTableType(tableType);
					if (remarks != null && !remarks.equals("undefined")&&!remarks.equals("")) {
						counselorAttachment.setRemarks(remarks);
					}

					counselorAttachment.setFileName(file.getOriginalFilename()); //原始文件名称
					counselorAttachment.setPath(newFileName);
					counselorAttachmentService.save(counselorAttachment);
					renderString(response, counselorAttachment.getId(), "text");

				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
//	    System.out.println(img_url);
	}

}
