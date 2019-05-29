package com.thinkgem.jeesite.modules.counselor.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.modules.counselor.entity.ChangeItem;
import com.thinkgem.jeesite.modules.counselor.service.ChangeItemService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseBase;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.entity.PersonRecord;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseBaseService;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.enterprise.service.PersonRecordService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/counselor/coverPrint")
public class CoverPrintController {

	@Autowired
	private PersonRecordService personRecordService;
	
	@Autowired
	private EnterpriseWorkersService enterpriseWorkersService;
	
	@Autowired
	private EnterpriseBaseService enterpriseBaseService;
	
	@Autowired
	private ChangeItemService changeItemService;
	
	/**
	 * 咨询工程师（投资）登记申请表封面
	 * 0.注销登记 1.初始登记 2.变更执业单位申请 3.变更专业 4.继续登记 
	 * @param personRecord
	 * @param model
	 * @return
	 */
	@RequestMapping(value="cover")
	public String coverRrint(PersonRecord personRecord,Model model){
		User user= UserUtils.getUser();
		//先根据user 的 名字与身份证号取 基本信息
		EnterpriseWorkers enterpriseWorkers = this.getEnterpriseWorkers(user);//查询联系人和联系电话
		EnterpriseBase enterpriseBase = this.enterpriseBaseService.getbyUserId(enterpriseWorkers.getPid());
		

		//根据personRecord获取申请时间declare_date
		List<PersonRecord> findById = this.personRecordService.findById(personRecord);
		if("0".equals(personRecord.getDeclareType())){//注销登记
		}else if("1".equals(personRecord.getDeclareType())){//初始登记：
			ChangeItem changeitem1 =this.changeItemService.getByRecordId(personRecord.getId(), "4");
			ChangeItem changeitem2 =this.changeItemService.getByRecordId(personRecord.getId(), "5");
			model.addAttribute("changeitemCS1", changeitem1);
			model.addAttribute("changeitemCS2", changeitem2);
	        model.addAttribute("enterpriseBase", enterpriseBase);
	        model.addAttribute("enterpriseWorkers",enterpriseWorkers);
	        
	        //申请时间
	        if(findById != null && findById.size()>0){
				model.addAttribute("personRecord", findById.get(0));
			}
	        
	        return "modules/counselor/coverPrint1";
		}else if("2".equals(personRecord.getDeclareType())){//变更执业单位申请
			ChangeItem changeItem = this.changeItemService.getByRecordId(personRecord.getId(), "1");//查询原工作单位
			model.addAttribute("changeItem", changeItem);
			model.addAttribute("enterpriseBase", enterpriseBase);
	        model.addAttribute("enterpriseWorkers",enterpriseWorkers);
	        //申请时间
	        if(findById != null && findById.size()>0){
				model.addAttribute("personRecord", findById.get(0));
			}
			return "modules/counselor/coverPrint2";
		}else if("3".equals(personRecord.getDeclareType())){//变更专业
			ChangeItem changeItemZ = this.changeItemService.getByRecordId(personRecord.getId(), "2");//查询变更主专业
			ChangeItem changeItemF = this.changeItemService.getByRecordId(personRecord.getId(), "3");//查询变更主专业
			model.addAttribute("changeItemZ", changeItemZ);
			model.addAttribute("changeItemF", changeItemF);
			model.addAttribute("enterpriseBase", enterpriseBase);
	        model.addAttribute("enterpriseWorkers",enterpriseWorkers);
	        //申请时间
	        if(findById != null && findById.size()>0){
				model.addAttribute("personRecord", findById.get(0));
			}
			return "modules/counselor/coverPrint3";
		}else if("4".equals(personRecord.getDeclareType())){//继续登记 
			model.addAttribute("enterpriseBase", enterpriseBase);
	        model.addAttribute("enterpriseWorkers",enterpriseWorkers);
	        //申请时间
	        if(findById != null && findById.size()>0){
				model.addAttribute("personRecord", findById.get(0));
			}
			return "modules/counselor/coverPrint4";
		}
		return "页面未找到";
	}
	
	
	@RequestMapping(value="fileCover")
	public String fileCover(PersonRecord personRecord,Model model){
		User user= UserUtils.getUser();
		//先根据user 的 名字与身份证号取 基本信息
		EnterpriseWorkers enterpriseWorkers = this.getEnterpriseWorkers(user);//获取用户名
		List<PersonRecord> findById = this.personRecordService.findById(personRecord);
		if(enterpriseWorkers != null){
			model.addAttribute("enterpriseWorkers", enterpriseWorkers);
		}
		if(findById != null && findById.size()>0){
			model.addAttribute("personRecord", findById.get(0));
		}
		return "modules/counselor/fileCover";
	}
	
	 //传递user, 查询出他的enterprise
    private EnterpriseWorkers getEnterpriseWorkers(User user){
        //List<EnterpriseWorkers> list = enterpriseWorkersService.findList(enterpriseWorkers);
    	EnterpriseWorkers enterpriseWorker = new EnterpriseWorkers();
    	enterpriseWorker.setName(user.getName());
    	enterpriseWorker.setCertificatesNum(user.getCardNumber());
        EnterpriseWorkers enterpriseWorkers =  enterpriseWorkersService.findByCertificatesNum(enterpriseWorker);
        return enterpriseWorkers;
    }
}
