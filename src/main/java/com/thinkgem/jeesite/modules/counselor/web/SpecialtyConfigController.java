package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.ProfessionalDate;
import com.thinkgem.jeesite.modules.counselor.entity.SpecialtyConfig;
import com.thinkgem.jeesite.modules.counselor.service.ProfessionalDateService;
import com.thinkgem.jeesite.modules.counselor.service.SpecialtyConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "${adminPath}/counselor/specialtyConfig")
public class SpecialtyConfigController extends BaseController {
    @Autowired
    private SpecialtyConfigService specialtyConfigService;

    @Autowired
    private ProfessionalDateService professionalDateService;

    @RequestMapping(value="listAllConfig")
    public String getAllList(SpecialtyConfig specialtyConfig, HttpServletRequest request, HttpServletResponse response,Model model){
        Page<SpecialtyConfig> page = specialtyConfigService.findPage(new Page<SpecialtyConfig>(request, response), specialtyConfig);
        model.addAttribute(page);
        return "modules/counselor/specialtyConfigList";
    }

    @RequestMapping(value="edit")
    public String edit(String id, HttpServletRequest request, HttpServletResponse response,Model model){
        SpecialtyConfig specialtyConfig=specialtyConfigService.get(id);
        model.addAttribute(specialtyConfig);
        return "modules/counselor/specialtyConfigForm";
    }

    @RequestMapping(value="save")
    public String save(SpecialtyConfig specialtyConfig, HttpServletRequest request, HttpServletResponse response,Model model){
        if(request.getParameter("stage").indexOf("1")>-1){
            return "modules/counselor/specialtyConfigForm";
        }else {
            specialtyConfigService.save(specialtyConfig);
            model.addAttribute("message", "保存成功");
            return "redirect:" + adminPath + "/counselor/specialtyConfig/listAllConfig";
        }
    }

    @RequestMapping(value="delete")
    public String delete(String id, HttpServletRequest request, HttpServletResponse response,Model model){
        specialtyConfigService.delete(id);
        model.addAttribute("message", "删除成功");
        return "redirect:" + adminPath + "/counselor/specialtyConfig/listAllConfig";
    }

    @RequestMapping(value="checkSpecialty")
    @ResponseBody
    public String checkSpecialty( HttpServletRequest request, HttpServletResponse response,Model model){
        String specialty = request.getParameter("specialty");
       String  num =  specialtyConfigService.getSpecialty(specialty);
       return num;
    }

    /**
     * 获得专业比较数据
     * @param professionalDate
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="compare")
    public String compare(ProfessionalDate professionalDate,HttpServletRequest request, HttpServletResponse response, Model model){
        //获得同一个批次下 所有初始申请，变更专业的记录
        if(professionalDate.getName()!=null&&professionalDate.getName()!=""){
            String specialty =  professionalDate.getNewValue();
            Page<ProfessionalDate> page= professionalDateService.findPage(new Page<ProfessionalDate>(request, response), professionalDate);
            model.addAttribute(page);
        }
        return "modules/counselor/professionalDateList";
    }

    /**
     *
     */
    @RequestMapping(value="exportProfessionalDate")
    public String exportProfessionalDate(String batch,ProfessionalDate professionalDate,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes){
        try {
            ProfessionalDate professionalDate1=new ProfessionalDate();
            professionalDate1.setName(batch);
            if (professionalDate1.getName() != null && professionalDate1.getName() != "") {
                String specialty = professionalDate1.getNewValue();
                ArrayList<ProfessionalDate> list = professionalDateService.findListToExport(professionalDate1);
                String fileName = professionalDate1.getName()+"批次导出专业对比信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
                new ExportExcel(professionalDate1.getName()+"批次导出专业对比信息" , ProfessionalDate.class).setDataList(list).write(response, fileName).dispose();
                return "modules/counselor/professionalDateList";
            }
        }catch (Exception e){
            addMessage(redirectAttributes, "导出专业对比信息失败！失败信息："+e.getMessage());
        }
        return "modules/counselor/professionalDateList";
    }




}
