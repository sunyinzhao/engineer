/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorTable;
import com.thinkgem.jeesite.modules.counselor.entity.ProjectInvestment;
import com.thinkgem.jeesite.modules.counselor.service.CounselorAttachmentService;
import com.thinkgem.jeesite.modules.counselor.service.CounselorTableService;
import com.thinkgem.jeesite.modules.counselor.service.ProjectInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * 咨询师附件Controller
 * @author hzy
 * @version 2018-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/counselor/counselorAttachment")
public class CounselorAttachmentController extends BaseController {

	@Autowired
	private CounselorAttachmentService counselorAttachmentService;

	@Autowired
	private CounselorTableService counselorTableService;

	@Autowired
    private ProjectInvestmentService projectInvestmentService;

	@ModelAttribute
	public CounselorAttachment get(@RequestParam(required=false) String id) {
		CounselorAttachment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = counselorAttachmentService.get(id);
		}
		if (entity == null){
			entity = new CounselorAttachment();
		}
		return entity;
	}

	@ResponseBody
	@RequestMapping(value = "deleteAttach")
	public void deleteAttach(CounselorAttachment counselorAttachment){
		counselorAttachmentService.delete(counselorAttachment);
	}

	@RequestMapping(value = "changeSubmit")
	public String changeSubmit(String tableId,String recordId,String type,String remarks,String ids){
        CounselorTable counselorTable = counselorTableService.get(tableId);
        counselorTable.setStatus("1");
        counselorTableService.save(counselorTable);
        saveAttachMethod(remarks,ids);
            return "redirect:" + adminPath + "/counselor/firstIndex?id=" + recordId + "&type="+type;
	}

	@RequestMapping(value = "saveAttach")
	public String saveAttach(String remarks,String ids,String tableId,String personId,String recordId){
		saveAttachMethod(remarks,ids);

			return "redirect:"+adminPath+"/counselor/tableForm?id="+tableId+"&personId="+personId+"&recordId="+recordId;
	}
        //主要是保存当前附件的remarks
	public void saveAttachMethod(String remarks,String ids){
        if(ids==null||ids.equals("")){
            return;
        }
        String[] idList = ids.split(",");
        String[] remarksList = remarks.split(",");
        for(int i = 0;i<idList.length;i++){
            CounselorAttachment attach = counselorAttachmentService.get(idList[i]);
            if(remarksList!=null&&remarksList.length>0){
            attach.setRemarks(remarksList[i]);
			}
            counselorAttachmentService.save(attach);
        }
	}

	@RequestMapping(value = "projectSubmit")
	public String projectSubmit(String tableId,String recordId,String type){
		CounselorTable counselorTable = counselorTableService.get(tableId);
		counselorTable.setStatus("1");
		counselorTableService.save(counselorTable);
		return "redirect:" + adminPath + "/counselor/firstIndex?id=" + recordId + "&type="+type;
	}

//使用另一种方法进行查询数据

	@ResponseBody
	@RequestMapping(value = "findImage")
	public List<CounselorAttachment> findImage(String tableId,String []types){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("tableId",tableId);
        map.put("types",types);
        List<CounselorAttachment> attachList = counselorAttachmentService.findListByType(map);
		return attachList;
	}


	//根据文件id 查找文件
	@RequestMapping(value = "findImages")
	public String findImages(String fileId,Model model){
		//根据fileId 查找文件
        CounselorAttachment counselorAttachment = new CounselorAttachment();
        counselorAttachment.setRemarks(fileId);
        List<CounselorAttachment> list = counselorAttachmentService.findList(counselorAttachment);
        //根据fileId 查找到对象
        ProjectInvestment project = projectInvestmentService.get(fileId);
        model.addAttribute("project",project);
        model.addAttribute("imageList",list);
        return "modules/counselor/counselorProjectImage";
	}
}