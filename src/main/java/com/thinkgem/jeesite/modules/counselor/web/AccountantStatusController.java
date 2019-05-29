package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.AccountantStatus;
import com.thinkgem.jeesite.modules.counselor.entity.ChangeItem;
import com.thinkgem.jeesite.modules.counselor.entity.Counselor;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorTable;
import com.thinkgem.jeesite.modules.counselor.service.AccountantStatusService;
import com.thinkgem.jeesite.modules.counselor.service.ChangeItemService;
import com.thinkgem.jeesite.modules.counselor.service.CounselorService;
import com.thinkgem.jeesite.modules.counselor.service.CounselorTableService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//执业情况表
@Controller
@RequestMapping(value = "${adminPath}/counselor/accountantStatus")
public class AccountantStatusController extends BaseController {
    @Autowired
    private AccountantStatusService accountantStatusService;

    @Autowired
    private CounselorTableService counselorTableService;

    @Autowired
    private ChangeItemService changeItemService;

    @Autowired
    private EnterpriseWorkersService enterpriseWorkersService;

    @Autowired
    private CounselorService counselorService;



    @RequestMapping(value = "saveInfo")
    public String saveInfo(AccountantStatus accountantStatus,String tableId,String recordId,String flag,String userId,String personId){
        //存在问题,当变更理由为其他的时候,需要把remarks清空
        if(accountantStatus.getReason()!=null&&!accountantStatus.getReason().equals("0")){
                accountantStatus.setRemarks(null);
        }
        Counselor counselor = counselorService.get(recordId);
        String type = counselor.getDeclareType();
        //这两根据不同的类型进行添加, type为2 的变更执业单位 只addItem ,type为3 的变更专业 为saveCHangeItme
        if(type.equals("2")){
            addItem(accountantStatus,personId,recordId);
        }else if(type.equals("3")){
            saveChangeItem(accountantStatus,recordId);
        }
        accountantStatus.setPersonId(personId);
        accountantStatusService.save(accountantStatus);
        changeEnterprise(accountantStatus,personId,userId);
        if(flag!=null&&!flag.equals("")){
            CounselorTable counselorTable = counselorTableService.get(tableId);
            counselorTable.setStatus("1");
            counselorTableService.save(counselorTable);
            return "redirect:"+adminPath+"/counselor/firstIndex?id="+recordId;
        }
        return "redirect:" + adminPath + "/counselor/tableForm?id="+tableId+"&personId="+personId+"&recordId="+recordId;
    }

    public void changeEnterprise(AccountantStatus accountantStatus,String personId,String userId){
        EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getById(personId);
        String companyName = accountantStatus.getCompanyName();
        if(companyName!=null&&userId!=null&&!companyName.equals("")&&!userId.equals("")){
            enterpriseWorkers.setPid(userId);
            enterpriseWorkers.setCompanyName(companyName);
            enterpriseWorkersService.save(enterpriseWorkers);
        }
    }

    //还需要把数据放入变更履历表里
    public void saveChangeItem(AccountantStatus accountantStatus,String recordId){//三个参数
        String oldMain = accountantStatus.getOldMainSpecialty();
        String main = accountantStatus.getNewMainSpecialty();
        String oldAssist = accountantStatus.getOldAuxiliarySpecialty();
        String assist = accountantStatus.getNewAuxiliarySpecialty();
        ChangeItem item = changeItemService.getByRecordId(recordId, "2");
        //1.判断是否存在
        if(main!=null&&!main.equals("")){
            //先查询是否存在
            if(item!=null&&!item.equals("")){//如果存在就只改变main
                item.setNewValue(main);
                changeItemService.save(item);
            }else{
                ChangeItem item1 = new ChangeItem();
                item1.setNewValue(main);
                item1.setOldValue(oldMain);
                item1.setRecordId(recordId);
                item1.setChangeType("2");
                changeItemService.save(item1);
            }
        }else if(main!=null&&main.equals("")){//如果是空的,就把这条item进行删除
            if(item!=null&&!item.equals("")){
                changeItemService.delete(item);
            }
        }
        ChangeItem item2 = changeItemService.getByRecordId(recordId, "3");
        if(assist!=null&&!assist.equals("")){
            //先查询是否存在
            if(item2!=null&&!item2.equals("")){//如果存在就只改变main
                item2.setNewValue(assist);
                changeItemService.save(item2);
            }else{
                ChangeItem item3 = new ChangeItem();
                item3.setNewValue(assist);
                item3.setOldValue(oldAssist);
                item3.setRecordId(recordId);
                item3.setChangeType("3");
                changeItemService.save(item3);
            }
        }else if(assist!=null&&assist.equals("")){//如果是空的,就把这条item进行删除
            if(item2!=null&&!item2.equals("")){
                changeItemService.delete(item2);
            }
        }
        deleteSame(recordId);
    }

    //删除oldvalue 与 newvalue 一样的值
    private void deleteSame(String recordId){
        //只对 2, 3 进行修改
        ChangeItem mainItem = changeItemService.getByRecordId(recordId, "2");//主专业
        if(mainItem!=null&&!mainItem.equals("")){
            String newValue = mainItem.getNewValue();
            String oldValue = mainItem.getOldValue();
            if(newValue!=null&&oldValue!=null&&oldValue.equals(newValue)){
                changeItemService.delete(mainItem);
            }
        }
        ChangeItem assistItem = changeItemService.getByRecordId(recordId, "3");//辅专业
        if(assistItem!=null&&!assistItem.equals("")){
            String newValue = assistItem.getNewValue();
            String oldValue = assistItem.getOldValue();
            if(newValue!=null&&oldValue!=null&&oldValue.equals(newValue)){
                changeItemService.delete(assistItem);
            }
        }
    }

    //添加的时候 需要把把数据带入item表,变更专业的时候,也进入这个方法,需要判断
    private void addItem(AccountantStatus accountantStatus,String personId,String recordId){
        //1.获取修改后公司的名字>
        String newCompanyName = accountantStatus.getCompanyName();
        //先查询是否有这个公司,如果有,就只改newCompanyName
        ChangeItem changeItem = changeItemService.getByRecordId(recordId, "1");
        if(changeItem!=null&&!changeItem.equals("")){
            if (newCompanyName != null && !newCompanyName.equals("")) {
                changeItem.setNewValue(newCompanyName);
                changeItemService.save(changeItem);
            }
        }else {
            if (newCompanyName != null && !newCompanyName.equals("")) {
                //2.获取原来公司的名字
                EnterpriseWorkers workers = enterpriseWorkersService.getById(personId);
                String oldCompanyName = workers.getCompanyName();
                //3.获取recordId
                ChangeItem item = new ChangeItem();
                item.setRecordId(recordId);
                item.setChangeType("1");
                item.setOldValue(oldCompanyName);
                item.setNewValue(newCompanyName);
                changeItemService.save(item);
            }
        }
    }


    /*//1.需要判断现在选的
    @ResponseBody
    @RequestMapping(value = "valiCompany")
    public String vliCompany(){
        return null;
    }*/
}
