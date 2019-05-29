package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.Counselor;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorTable;
import com.thinkgem.jeesite.modules.counselor.service.CounselorService;
import com.thinkgem.jeesite.modules.counselor.service.CounselorTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${adminPath}/counselor/cancel")
public class CancelController extends BaseController {
    @Autowired
    private CounselorService counselorService;

    @Autowired
    private CounselorTableService counselorTableService;

    @RequestMapping(value = "saveInfo")
    private String saveInfo(Counselor  counselor,String tableId,String flag){
        String cancelType = counselor.getCancelType();
        Counselor counselor1 = counselorService.get(counselor.getId());
        counselor1.setCancelType(cancelType);
        counselor1.setRemarks(counselor.getRemarks());
        if(!cancelType.equals("13")){
            counselor1.setRemarks("");
        }
        counselorService.save(counselor1);
        if(flag!=null&&!flag.equals("")){//修改确认 状态
            CounselorTable counselorTable = counselorTableService.get(tableId);
            counselorTable.setStatus("1");
            counselorTableService.save(counselorTable);
            return "redirect:" + adminPath + "/counselor/firstIndex?id="+counselor1.getId();
        }
        return "redirect:" + adminPath + "/counselor/tableForm?id="+tableId+"&personId="+counselor1.getPersonId()+"&recordId="+counselor1.getId();
    }
}
