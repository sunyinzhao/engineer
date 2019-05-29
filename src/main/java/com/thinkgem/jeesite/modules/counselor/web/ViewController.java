package com.thinkgem.jeesite.modules.counselor.web;


import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.ReturnPojo;
import com.thinkgem.jeesite.modules.counselor.service.ViewService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/counselor/view")
public class ViewController extends BaseController {


    @Autowired
    private ViewService viewService;

    //查询returnList
   @RequestMapping(value = "returnList")
    public String returnList(HttpServletRequest request, HttpServletResponse response,ReturnPojo returnPojo, Model model){//存在参数
       //区分登录人权限
       String id = UserUtils.getUser().getId();
       if(id!=null&&id.equals("1")){
           //表示为中资协会>

       }
       //需要重写page , 因为orderby的方式不同
       Page<ReturnPojo> page = viewService.findPage(new Page<ReturnPojo>(request,response),returnPojo);//多两参数
       model.addAttribute("page",page);
       model.addAttribute("returnPojo",returnPojo);
       return "modules/counselor/recordReturnList";
   }

}
