package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.TitleCertificate;
import com.thinkgem.jeesite.modules.counselor.service.TitleCertificateService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/counselor/title")
public class TitleCertificateController extends BaseController {
    @Autowired
    private SystemService systemService;

    @Autowired
    private TitleCertificateService titleCertificateService;
    //ajax 联动下拉框
    @ResponseBody
    @RequestMapping(value = "ajaxTitle")
    public List<Dict> ajaxTitle(String value ){
        //根据value 去字典表里查询
        Dict dict = new Dict();
        dict.setType("worker_title");
        dict.setDescription(value);
        List<Dict> dictList = titleCertificateService.findTitleList(dict);
        return dictList;
    }
    @RequestMapping(value = "delete")
    public String delete(TitleCertificate titleCertificate,String tableId,String personId,String recordId){
        titleCertificateService.delete(titleCertificate);
        return "redirect:"+adminPath+"/counselor/tableForm?id="+tableId+"&personId="+personId+"&recordId="+recordId;
    }
}
