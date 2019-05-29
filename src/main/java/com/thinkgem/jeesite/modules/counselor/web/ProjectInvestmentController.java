package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorTable;
import com.thinkgem.jeesite.modules.counselor.entity.ProjectInvestment;
import com.thinkgem.jeesite.modules.counselor.service.CounselorAttachmentService;
import com.thinkgem.jeesite.modules.counselor.service.CounselorTableService;
import com.thinkgem.jeesite.modules.counselor.service.ProjectInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/counselor/project")
public class ProjectInvestmentController extends BaseController {

    //用于储存project

    @Autowired
    private ProjectInvestmentService projectInvestmentService;

    @Autowired
    private CounselorTableService counselorTableService;

    @Autowired
    private CounselorAttachmentService counselorAttachmentService;

    @RequestMapping(value = "saveProject")
    public String saveProject(ProjectInvestment projectInvestment,String flag){//flag 用于区分是点确定还是点保存, 跳转的方式不同,
      List<ProjectInvestment> projectList = projectInvestment.getProjectList();
        if(projectList!=null&&projectList.size()>0){
        for(ProjectInvestment project : projectList){
            if(project.getDelFlag().equals("1")){
                String id = project.getId();
                projectInvestmentService.delete(project);
                    //删除业绩的时候,同时也需要把附件表的数据也删掉
                deleteAttach(id);
            }else {
                project.setRecordId(projectInvestment.getRecordId());
                project.setTableId(projectInvestment.getTableId());
                project.setPersonId(projectInvestment.getPersonId());
                projectInvestmentService.save(project);
                }
            }
        }
        if(flag!=null&&!flag.equals("")){//此状态为确认状态
                //保存
            CounselorTable counselorTable = counselorTableService.get(projectInvestment.getTableId());
            counselorTable.setStatus("1");
            counselorTableService.save(counselorTable);
            addAttachForm(projectInvestment.getRecordId());
            return "redirect:"+adminPath+"/counselor/firstIndex?id="+counselorTable.getPersonRecordId()+"&type="+counselorTable.getType();
        }
        else {
            return "redirect:" + adminPath + "/counselor/tableForm?id=" + projectInvestment.getTableId() + "&personId=" + projectInvestment.getPersonId() + "&recordId=" + projectInvestment.getRecordId();
        }
    }

    //新增一个相关附件表,type 设置为7
    private void addAttachForm(String record){
        //1.先查询是否存在未7的表,
        CounselorTable table = counselorTableService.getNew(record, "7");
        if(table==null||table.equals("")){//如果不存在,进行添加
            CounselorTable counselorTable = new CounselorTable();
            counselorTable.setStatus("0");
            counselorTable.setType("7");
            counselorTable.setPersonRecordId(record);
            counselorTableService.save(counselorTable);
        }
    }

    private void deleteAttach(String remarks){
        CounselorAttachment attachment = new CounselorAttachment();
        attachment.setRemarks(remarks);
        List<CounselorAttachment> list = counselorAttachmentService.findList(attachment);
        if(list!=null&&list.size()>0){
        for(CounselorAttachment a : list){
            counselorAttachmentService.delete(a);
        }
        }
    }
}
