package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//变更执业单位
@Controller
@RequestMapping(value = "${adminPath}/counselor/apply")
public class CounselorApplyController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private EnterpriseWorkersService enterpriseWorkersService;

    //跳转窗口页面
    @RequestMapping(value = "companyWindow")
    public String companyWindow(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "modules/counselor/companyWindow";
    }

    //查询
    @RequestMapping(value = "search")
    public String search(Model model, User user, HttpServletRequest request, HttpServletResponse response){

        if(user!=null&&!user.equals("")){
            user.setUserType("7");
           // List<User> list = enterpriseWorkersService.findSysUserName(user);
            List<User> list = enterpriseWorkersService.findSysUserName1(user);
            // Page<User> page = systemService.findUser(new Page<User>(request, response), user);
            model.addAttribute("list",list);
        }

        return "modules/counselor/companyWindow";
    }
}
