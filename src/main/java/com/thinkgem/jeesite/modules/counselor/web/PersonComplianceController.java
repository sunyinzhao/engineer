package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.PersonCompliance;
import com.thinkgem.jeesite.modules.counselor.service.PersonComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${adminPath}/counselor/compliance")
public class PersonComplianceController extends BaseController {

    @Autowired
    private PersonComplianceService personComplianceService;

    @RequestMapping(value = "saveCompliance")
    public String saveCompliance(PersonCompliance personCompliance,String recordId,String examineId,String type){
        //进行储存.
        for(PersonCompliance p : personCompliance.getCheck()){
            personComplianceService.save(p);
        }
        return "redirect:"+adminPath+"/counselor/examine/jump?id="+examineId+"&recordId="+recordId+"&type="+type;//跳转到本页面
    }
}
