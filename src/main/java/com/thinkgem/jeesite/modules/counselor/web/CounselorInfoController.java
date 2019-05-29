package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.ChangeItem;
import com.thinkgem.jeesite.modules.counselor.entity.Counselor;
import com.thinkgem.jeesite.modules.counselor.entity.PersonExpert;
import com.thinkgem.jeesite.modules.counselor.service.ChangeItemService;
import com.thinkgem.jeesite.modules.counselor.service.CounselorService;
import com.thinkgem.jeesite.modules.counselor.service.PersonExpertService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/counselor/info")
public class CounselorInfoController extends BaseController {


    @Autowired
    private EnterpriseWorkersService enterpriseWorkersService;

    @Autowired
    private CounselorService counselorService;

    @Autowired
    private ChangeItemService changeItemService;

    @Autowired
    private PersonExpertService personExpertService;
    //用于加载个人中心窗口
    @RequestMapping("infoWindow")
    public String infoWindow(Model model){
        EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
        model.addAttribute("enterpriseWorkers",enterpriseWorkers);
        //还需要照片
        return "modules/counselor/infoWindow";
    }

    private EnterpriseWorkers getEnterpriseWorkers(User user){
        //List<EnterpriseWorkers> list = enterpriseWorkersService.findList(enterpriseWorkers);
        EnterpriseWorkers enterpriseWorkers =  enterpriseWorkersService.getByNameNum(user.getName(),user.getCardNumber());
        if(enterpriseWorkers!=null&&!enterpriseWorkers.equals("")){
            enterpriseWorkers.setEmail(user.getEmail());
        }
        return enterpriseWorkers;
    }


    @RequestMapping(value = "saveAll")
    @ResponseBody
    public String p_add(Date start, Date end, String declareType, String batchNo) throws InterruptedException {//四个参数,1.开始时间,2.结束时间. 3. 类型. 4,公告批次
        Counselor counselor = new Counselor();
        if(declareType!=null&&!declareType.equals("")){
            counselor.setDeclareType(declareType);
        }
        counselor.setDeclareStatus("19");
        counselor.setStartDate(start);
        counselor.setEndDate(end);

        String declareStatus = "19";
       List<Counselor> list = counselorService.findListByDate(start,end,declareType,batchNo,declareStatus);
       addMethod(list,batchNo);
       /* //睡眠2秒.
        //100个100个取
        List<Counselor> tempList = new ArrayList<Counselor>();
        int tempNum = 0;
        for(Counselor c : list){
            tempList.add(c);
            tempNum++;
            if(tempNum==100){//添加到100个的时候,进行循环
                addMethod(tempList);//进行修改
                tempList.clear();//清空
                tempNum=0;//置为0
                Thread.sleep(2000);//休息两秒
            }
        }
        //Thread.sleep(2000);
        //System.out.println(list);
        addMethod(tempList);// 后面还有未到100的数据,再进行修改*/
        return "保存完毕";
    }

    public  void addMethod(List<Counselor>list,String batchNo){
        for(Counselor c : list){
            String declareType = c.getDeclareType();
            
            if(declareType!=null&&declareType.equals("1")){// //当decaler_type = 初始登记
            	firstMethod(c,batchNo);
            }else if(declareType!=null&&declareType.equals("3")){//变更专业
            	changeSpMethod(c,batchNo);
            }else if(declareType!=null&&declareType.equals("4")){//继续登记
            	contMethod(c,batchNo);
            }else if(declareType!=null&&declareType.equals("2")){//变更单位
            	changeCompanyMethod(c,batchNo);
            }else if(declareType!=null&&declareType.equals("0")){//当decaler_type = 注销登记
            	cancelMethod(c,batchNo);
            }
        }
    }
    //处理初始登记待公告和专家终审完毕数据
    //待公告状态为终审不通过或部分通过的经过复核后的状态（状态为19），专家终审通过的不用复核，状态为17
    public void firstMethod(Counselor c,String batchNo)
    {
    	String fdeclareResult = c.getFdeclareResult();
    	EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getWorkerAndOfficeCode(c.getPersonId());
        if(fdeclareResult!=null){//如果Fdeclare_result是通过或部分通过 ,1.通过 2.部分通过 3.不通过
            if(fdeclareResult.equals("1")||fdeclareResult.equals("2")){
                String firstZdeclareResult = c.getFirstZdeclareResult();
                if(firstZdeclareResult!=null&&firstZdeclareResult.equals("1")){//如果终审专家1的结论first_Zdeclare_result=1
                    ChangeItem item = changeItemService.getByRecordId(c.getId(), "4");//根据recordid查询changeitems表type是4的数据
                    if(item!=null&&!item.equals("")){//如果有数据
                        String newValue = item.getNewValue();
                        enterpriseWorkers.setRegisterMainSpecialty(newValue);//将查询结果的newvalue更新到enterprise_workers表的主专业,
                    }
                }
                String secondZdeclareResult = c.getSecondZdeclareResult();
                if(secondZdeclareResult!=null&&secondZdeclareResult.equals("1")){//如果终审专家2的结论second_Zdeclare_result=1
                    ChangeItem item = changeItemService.getByRecordId(c.getId(), "5");//根据recordid查询changeitems表type是5的数据
                    if(item!=null&&!item.equals("")){//如果有数据，将查询结果的newvalue更新到enterprise_workers表的辅助专业
                        String newValue = item.getNewValue();
                        enterpriseWorkers.setRegisterAuxiliarySpecialty(newValue);
                    }
                }
                //如果enterprise_workser表的is_punish!=1
                // 将enterprise_workers表的 isvalid字段更新为1
                // else
                //			将enterprise_workers表的 isvalid字段更新为0
                String isPunish = c.getIsPunish();
                if("1".equals(isPunish)){
                    enterpriseWorkers.setIsValid("0");
                    enterpriseWorkers.setIsFreeze("1");
                    enterpriseWorkers.setFreezeDate(new Date());
                    enterpriseWorkers.setPid(null);
                    enterpriseWorkers.setCompanyName(null);
                    enterpriseWorkers.setRegisterCertificateNum(null);
                    //todo freeze_cycle为getdate()+3年
                    Date freezeCycle = enterpriseWorkers.getFreezeCycle();
                    Date date = switchDate(freezeCycle, 3);
                    enterpriseWorkers.setFreezeCycle(date);
                }else{
                    enterpriseWorkers.setIsValid("1");
                    enterpriseWorkers.setRegisterCertificateNum(getRegisterNum(batchNo,enterpriseWorkers.getOfficeCode()));
                    enterpriseWorkers.setAollowDate(new Date());
                    c.setPublicDate(new Date());
                }
               
            }else if(fdeclareResult.equals("3")){//如果Fdeclare_result是不通过
                String isPunish = enterpriseWorkers.getIsPunish();
                //          如果申请的is_punish=1
                //				更新enterprise_workers的pid为null，companyname为null，isvalid为0，is_freeze为1，freeze_cycle为getdate()+3年
                //			else
                //				更新enterprise_workers的pid为null，companyname为null，isvalid为0
                enterpriseWorkers.setPid(null);
                enterpriseWorkers.setCompanyName(null);
                enterpriseWorkers.setIsValid("0");
                enterpriseWorkers.setRegisterCertificateNum(null);
                if(isPunish!=null&&isPunish.equals("1")){
                    enterpriseWorkers.setIsFreeze("1");
                    enterpriseWorkers.setFreezeDate(new Date());
                    //todo freeze_cycle为getdate()+3年
                    Date freezeCycle = enterpriseWorkers.getFreezeCycle();
                    Date date = switchDate(freezeCycle, 3);
                    enterpriseWorkers.setFreezeCycle(date);

                }
            }
            c.setBatchStatus("20");
            counselorService.save(c);
            enterpriseWorkersService.save(enterpriseWorkers);
        }
    }
    
    //处理变更专业待公告和专家终审完毕数据
    //待公告状态为终审不通过或部分通过的经过复核后的状态（状态为19），专家终审通过的不用复核，状态为17
    public void changeSpMethod(Counselor c,String batchNo)
    {
    	String fdeclareResult = c.getFdeclareResult();
    	EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getWorkerAndOfficeCode(c.getPersonId());
        if(fdeclareResult!=null){
            if(fdeclareResult.equals("1")||fdeclareResult.equals("2")){//如果Fdeclare_result是通过或部分通过
                String firstZdeclareResult = c.getFirstZdeclareResult();
                if(firstZdeclareResult!=null&&firstZdeclareResult.equals("1")){//如果终审专家1的结论first_Zdeclare_result=1
                    ChangeItem item = changeItemService.getByRecordId(c.getId(), "2");//根据recordid查询changeitems表type是2的数据
                    if(item!=null&&!item.equals("")){
                        String newValue = item.getNewValue();//如果有数据，将查询结果的newvalue更新到enterprise_workers表的主专业
                        enterpriseWorkers.setRegisterMainSpecialty(newValue);
                    }
                }
                String secondZdeclareResult = c.getSecondZdeclareResult();
                if(secondZdeclareResult!=null&&secondZdeclareResult.equals("1")){//如果终审专家2的结论second_Zdeclare_result=1
                    //根据recordid查询changeitems表type是3的数据
                    // 如果有数据，将查询结果的newvalue更新到enterprise_workers表的辅助专业
                    // 如果没有数据不处理
                    ChangeItem item = changeItemService.getByRecordId(c.getId(), "3");
                    if(item!=null&&!item.equals("")){
                        String newValue = item.getNewValue();
                        enterpriseWorkers.setRegisterAuxiliarySpecialty(newValue);
                    }
                }
                String isFreeze = enterpriseWorkers.getIsFreeze();
                //如果enterprise_workser表的is_freeze!=1
                //	将enterprise_workers表的 isvalid字段更新为1
                //	else
                //	将enterprise_workers表的 isvalid字段更新为0
                if(isFreeze!=null&&!isFreeze.equals("1")){
                    enterpriseWorkers.setIsValid("1");
                }else{
                    enterpriseWorkers.setIsValid("0");
                }
            }else if(fdeclareResult.equals("3")){//如果是不通过
                String isPunish = enterpriseWorkers.getIsPunish();
                if(isPunish!=null&&isPunish.equals("1")){
                    enterpriseWorkers.setIsFreeze("1");
                    //todo freeze_cycle为getdate()+3年
                    Date freezeCycle = enterpriseWorkers.getFreezeCycle();
                    Date date = switchDate(freezeCycle, 3);
                    enterpriseWorkers.setFreezeCycle(date);
                }
            }
            counselorService.save(c);
            enterpriseWorkersService.save(enterpriseWorkers);
        }
    }
    
    //处理继续登记待公告和专家终审完毕数据
    //待公告状态为终审不通过或部分通过的经过复核后的状态（状态为19），专家终审通过的不用复核，状态为17
    public void contMethod(Counselor c,String batchNo)
    {
    	String fdeclareResult = c.getFdeclareResult();
       	EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getWorkerAndOfficeCode(c.getPersonId());
        if(fdeclareResult!=null&&fdeclareResult.equals("1")){//如果Fdeclare_result是通过
            //todo 更新enterprise_workers的aollowdate+3年
            Date aollowDate = enterpriseWorkers.getAollowDate();
            Date date = switchDate(aollowDate, 3);
            enterpriseWorkers.setAollowDate(date);

        }else if(fdeclareResult!=null&&fdeclareResult.equals("3")){
            String isPunish = enterpriseWorkers.getIsPunish();
            if(isPunish!=null&&isPunish.equals("1")){
                enterpriseWorkers.setIsFreeze("1");
                //todo freeze_cycle为getdate()+3年
                Date freezeCycle = enterpriseWorkers.getFreezeCycle();
                Date date = switchDate(freezeCycle, 3);
                enterpriseWorkers.setFreezeCycle(date);

            }else{
                enterpriseWorkers.setPid(null);
                enterpriseWorkers.setCompanyName(null);
                enterpriseWorkers.setIsValid("0");
            }
        }
        //todo 更新person_record.anndate =系统当前时间
        c.setAnnDate(new Date());
        counselorService.save(c);
        enterpriseWorkersService.save(enterpriseWorkers);
    }
    
    //处理注销登记待公告和专家终审完毕数据
    //待公告状态为终审不通过或部分通过的经过复核后的状态（状态为19），专家终审通过的不用复核，状态为17
    public void cancelMethod(Counselor c,String batchNo)
    {
        String fdeclareResult = c.getFdeclareResult();
        EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getWorkerAndOfficeCode(c.getPersonId());
        if(fdeclareResult!=null&&fdeclareResult.equals("1")){
            enterpriseWorkers.setIsValid("0");
        }
        //todo 更新person_record.anndate =系统当前时间
        c.setAnnDate(new Date());
        counselorService.save(c);
        enterpriseWorkersService.save(enterpriseWorkers);
    }
    
    //处理变更执业单位待公告和专家终审完毕数据
    //待公告状态为终审不通过或部分通过的经过复核后的状态（状态为19），专家终审通过的不用复核，状态为17
    public void changeCompanyMethod(Counselor c,String batchNo)
    {
        String fdeclareResult = c.getFdeclareResult();
        EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getWorkerAndOfficeCode(c.getPersonId());
        if(fdeclareResult!=null&&fdeclareResult.equals("1")){
            enterpriseWorkers.setIsValid("0");
        }
        //todo 更新person_record.anndate =系统当前时间
        c.setAnnDate(new Date());
        counselorService.save(c);
        enterpriseWorkersService.save(enterpriseWorkers);
    }
    public String getRegisterNum(String batchNo,String officecode)
    {
    	String registernum;
    	int qty=counselorService.getAutoNumber(batchNo, officecode);
        // 0 代表前面补充0   
        // 10代表长度为10   
        // d 代表参数为正数型   
        String str = String.format("%05d", qty);
        registernum = "ZD"+officecode.substring(1)+batchNo+str;
        counselorService.updateAutoNumber(batchNo, officecode);
    	return registernum;
    }
    
    public Date switchDate(Date date,int num){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR,num);
        return cal.getTime();
    }


//    @RequestMapping("updateData")//删除普通项的
//    public void updateData(){
//        //查询出所有重复的数据
//        List<PersonExpert>list=personExpertService.findSameList();
//        ArrayList<String> result = new ArrayList<String>();
//        ArrayList<String> allResult = new ArrayList<String>();
//        for(PersonExpert p: list){
//            List<PersonExpert> uList = personExpertService.findNewList(p);
//            String s = updateMethod(uList);
//            result.add(s);
//            addMethod(uList, allResult,s);
//        }
//        personExpertService.deleteList(allResult);
//    }

    public String updateMethod(List<PersonExpert> list){
        for(int i = 0 ;i<list.size();i++){
            String items = list.get(i).getItems();
            if(items!=null&&!items.equals("1")){
                return list.get(i).getId();
            }
        }
        return list.get(0).getId();
    }

    public List<String> addMethod(List<PersonExpert>temp,List<String> result,String s ){
        for (PersonExpert p : temp){
            if(!p.getId().equals(s)){
            result.add(p.getId());
            }
        }
        return result;
    }


//    @RequestMapping("updateDataSame")//删除大于等于35的
//    public void updateDataSame(){
//        //查询出所有重复的数据
//        List<PersonExpert>list=personExpertService.findNewSameList();
//        ArrayList<String> result = new ArrayList<String>();
//        ArrayList<String> allResult = new ArrayList<String>();
//        for(PersonExpert p: list){
//            List<PersonExpert> uList = personExpertService.findNewList(p);
//            String s = updateMethod(uList);
//            result.add(s);
//            addMethod(uList, allResult,s);
//        }
//        personExpertService.deleteList(allResult);
//    }

    public String updateMethod1(List<PersonExpert> list){
        for(int i = 0 ;i<list.size();i++){
            String items = list.get(i).getItems();
            if(items!=null&&!items.equals("1")){
                return list.get(i).getId();
            }
        }
        return list.get(0).getId();
    }

    public List<String> addMethod1(List<PersonExpert>temp,List<String> result,String s ){
        for (PersonExpert p : temp){
            if(!p.getId().equals(s)){
                result.add(p.getId());
            }
        }
        return result;
    }

//    @RequestMapping("saveExpert3")
//    public void saveexpert(){
//        PersonExpert personExpert = new PersonExpert();
//        personExpert.setExamineType("3");
//        List<PersonExpert> list = personExpertService.findList(personExpert);
//        for(PersonExpert p : list){
//            p.setExamineType("4");
//            p.setId(null);
//            personExpertService.save(p);
//        }
//    }
//
//    @RequestMapping("deleteExpert4")
//    public void deleteExpert4(){
//        PersonExpert personExpert = new PersonExpert();
//        personExpert.setExamineType("4");
//        List<PersonExpert> list = personExpertService.findList(personExpert);
//        ArrayList<String> result = new ArrayList<String>();
//        for(PersonExpert p : list){
//            String id = p.getId();
//            result.add(id);
//        }
//        personExpertService.deleteList(result);
//    }
}
