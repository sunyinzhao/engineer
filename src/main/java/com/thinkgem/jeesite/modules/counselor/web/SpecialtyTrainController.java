package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.SpecialtyTrain;
import com.thinkgem.jeesite.modules.counselor.service.SpecialtyTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${adminPath}/counselor/specialtyTrain")
public class SpecialtyTrainController extends BaseController {
    @Autowired
    private SpecialtyTrainService specialtyTrainService;



    @RequestMapping(value = "delete")
    public String delete(SpecialtyTrain specialtyTrain,String tableId,String personId,String recordId){
        specialtyTrainService.delete(specialtyTrain);
        return "redirect:"+adminPath+"/counselor/tableForm?id="+tableId+"&personId="+personId+"&recordId="+recordId;
    }
}
