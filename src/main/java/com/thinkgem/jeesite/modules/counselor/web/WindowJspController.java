package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.*;
import com.thinkgem.jeesite.modules.counselor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/counselor/window")
public class WindowJspController extends BaseController {
@Autowired
private EducationtblService educationtblService;

@Autowired
private TitleCertificateService titleCertificateService;

@Autowired
private SpecialtyTrainService specialtyTrainService;

@Autowired
private CounselorService counselorService;

@Autowired
private PersonComplianceService personComplianceService;

    @RequestMapping("edit")
    public String editJsp(String id, String object, Model model,String readOnly){
        if(readOnly!=null&&readOnly.equals("1")){
        model.addAttribute("readOnly",readOnly);
        }
        //根据传递不同的obj,根据不同的表查找
        if(object!=null&&object.equals("educationtbl")){
            Educationtbl educationtbl = educationtblService.get(id);
            String personId = educationtbl.getPersonId();
            String tableId = educationtbl.getTableId();
            model.addAttribute("personId",personId);
            model.addAttribute("tableId",tableId);
            model.addAttribute("educationtbl",educationtbl);
            return "modules/counselor/infoForm1";
        }else if(object!=null&&object.equals("titleCertificate")){
            TitleCertificate titleCertificate = titleCertificateService.get(id);
            String personId = titleCertificate.getPersonId();
            String tableId = titleCertificate.getTableId();
            model.addAttribute("personId",personId);
            model.addAttribute("tableId",tableId);
            model.addAttribute("titleCertificate",titleCertificate);
            return "modules/counselor/infoForm2";
        }else if(object!=null&&object.equals("specialtyTrain")){
            SpecialtyTrain specialtyTrain = specialtyTrainService.get(id);
            String personId = specialtyTrain.getPersonId();
            String tableId = specialtyTrain.getTableId();
            model.addAttribute("personId",personId);
            model.addAttribute("tableId",tableId);
            model.addAttribute("specialtyTrain",specialtyTrain);
            return "modules/counselor/infoForm3";
        }
        return null;
    }

    //展示退回原因
    @RequestMapping(value = "queryReturn")
    public String queryReturn(String id,Model model,String type){//id为recordId
        //1.查询出退回原因的
        String result=null;
        Counselor counselor = counselorService.get(id);
        if(type.equals("22")){//合规退回,需要把所有的remark查询出来
            result = counselor.getHgReturn();
            List<PersonCompliance> hgList = queryHgList(id);
            model.addAttribute("hgList",hgList);
        }else if(type.equals("33")){
           result =  counselor.getReturnReason();
        }else if(type.equals("receive")){
            result = counselor.getReceiveReason();
        }else if(type.equals("44")){
            result = counselor.getFyAdvice();
        }
        
        model.addAttribute("result",result);
        return "modules/counselor/returnWindow";
    }

    private List<PersonCompliance> queryHgList(String recordId){//查询出所有有remarks的comlist
        List<PersonCompliance> list = personComplianceService.findRemark(recordId);
        return list;
    }
}
