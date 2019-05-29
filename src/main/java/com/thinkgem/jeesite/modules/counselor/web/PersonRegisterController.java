package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorTable;
import com.thinkgem.jeesite.modules.counselor.entity.PersonRegister;
import com.thinkgem.jeesite.modules.counselor.service.CounselorTableService;
import com.thinkgem.jeesite.modules.counselor.service.PersonRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${adminPath}/counselor/personRegister")
public class PersonRegisterController extends BaseController {
    @Autowired
    private PersonRegisterService personRegisterService;
    @Autowired
    private CounselorTableService counselorTableService;

    @RequestMapping(value = "saveInfo")
    public String saveInfo(PersonRegister personRegister,String tableId,String personId,String recordId,String flag){
        personRegisterService.save(personRegister);
        //当flag不为空的情况下表示已确认 需要进行跳转
        if(flag!=null&&!flag.equals("")){
            CounselorTable counselorTable = counselorTableService.get(tableId);
            counselorTable.setStatus("1");
            counselorTableService.save(counselorTable);
            return "redirect:"+adminPath+"/counselor/firstIndex?id="+recordId;
        }
        return "redirect:"+adminPath+"/counselor/tableForm?id="+tableId+"&personId="+personId+"&recordId="+recordId;
    }
}
//js中取值需要在hidden中通过id 获取值, 而body中直接可以用 $进行获取