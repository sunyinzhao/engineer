package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.Educationtbl;
import com.thinkgem.jeesite.modules.counselor.service.EducationtblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${adminPath}/counselor/educationtbl")
public class EducationtblController extends BaseController {
    @Autowired
    private EducationtblService educationtblService;

    @RequestMapping(value = "delete")
    public String delete(Educationtbl educationtbl,String tableId,String personId,String recordId){
      educationtblService.delete(educationtbl);
        //跳转本页面
        return "redirect:"+adminPath+"/counselor/tableForm?id="+tableId+"&personId="+personId+"&recordId="+recordId;
    }
}
