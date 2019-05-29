package com.thinkgem.jeesite.modules.counselor.web;


import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.*;
import com.thinkgem.jeesite.modules.counselor.service.*;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//将职称证书,学历证明,工作经历,~ 独立出来
@Controller
@RequestMapping(value = "${adminPath}/counselor/self")
public class SelfController extends BaseController {
    @Autowired
    private EducationtblService educationtblService;

    @Autowired
    private EnterpriseWorkersService enterpriseWorkersService;

    @Autowired
    private TitleCertificateService titleCertificateService;

    @Autowired
    private SpecialtyTrainService specialtyTrainService;

    @Autowired
    private CounselorService counselorService;

    @Autowired
    private CounselorAttachmentService counselorAttachmentService;

    @Autowired
    private JobKnowledgeService jobKnowledgeService;

    //设计思路
    // 控制设定. 编辑删除等操作进行控制

    //1.查看按钮,编辑
            //1.1 查看只根据personId 进行查询. 添加,查看,编辑都是跳转页面 不是弹窗
    //2.给他添加选项的时候, 添加主辅专业
    //3.审查列表查看的时候,使用personId 进行查询
    //详情登记


    ////////////////////////////////////学历部分//////////////////////////////
    //查询学历列表
    @RequestMapping("queryEducation")
    private String queryEducation(Model model){//传递的参数是personId
        //personId查询
        User user = UserUtils.getUser();
        //根据user查询出enterpriseId
        EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(user);
        String personId = enterpriseWorkers.getId();
        String readOnly = ctrlButton(personId);
        model.addAttribute("readOnly",readOnly);//如果readOnly为200 才可以看见 添加 编辑 删除

        Educationtbl educationtbl = new Educationtbl();
        educationtbl.setPersonId(personId);
        List<Educationtbl> list = educationtblService.findList(educationtbl);
        model.addAttribute("list",list);//不使用分页
        model.addAttribute("personId",personId);
        //educationList
        return "modules/counselor/educationList";
    }


    //编辑,添加. 都走这个方法
    //type 区别 . type 1添加 2 编辑
    @RequestMapping("editEducation")
    private String editEducation(Educationtbl educationtbl,Model model,String type){
        if(type.equals("1")){//添加状态
            //只需要把personId 放进去
            // 生成一个id返回去
            // String id0 = educationtbl.getId();
            String uuid = "";
            for (int i=0;i<20;i++){
            uuid = UUID.randomUUID().toString().replaceAll("-", "");
            Educationtbl educationtbluuid = educationtblService.get(uuid);
            if ( "".equals(educationtbluuid) || educationtbluuid == null ){
                educationtbl.setId(uuid);
                break;
            }
            }
            model.addAttribute("educationtbl",educationtbl);

        }else if(type.equals("2")){
            CounselorAttachment counselorAttachment = new CounselorAttachment();
            String id = educationtbl.getId();
            Educationtbl educationtbl1 = educationtblService.get(id);
            counselorAttachment.setPid(id);
            counselorAttachment.setType("16");// 只取type为16的  学历的类型
            List<CounselorAttachment> attachList = counselorAttachmentService.findList(counselorAttachment);
            educationtbl1.setCounselorAttachmentList(attachList);
            model.addAttribute("educationtbl",educationtbl1);
        }
        model.addAttribute("type",type);
        return "modules/counselor/editEducation";
    }

    @RequestMapping("delEducation")
    private String delEducation(String id,String personId){
        Educationtbl educationtbl = new Educationtbl();
        educationtbl.setId(id);
        educationtblService.delete(educationtbl);
        // 删除这条基本信息的附件
        CounselorAttachment counselorAttachment = new CounselorAttachment();
        counselorAttachment.setPid(id);
        List<CounselorAttachment> attachList = counselorAttachmentService.findList(counselorAttachment);
        if(attachList != null){
            for (CounselorAttachment attachment : attachList) {

                this.counselorAttachmentService.delete(attachment);
            }
        }
        return "redirect:" + adminPath + "/counselor/self/queryEducation?personId="+personId;
    }

    @ResponseBody
    @RequestMapping(value = "saveEducation",produces="application/json;charset=UTF-8")
    private String saveEducation(Educationtbl educationtbl,String type,@RequestParam(value="counselorAttachmentListRemarks") String remarks){
        //验证
        String validate = validate(educationtbl);
        if(!validate.equals("200")){
            //如果验证返回结果不是200
            return validate;
        }

        String personId = educationtbl.getPersonId();
        //需要设置index
        if(type.equals("1")){//为1的时候为新增状态,需要进行添加index
            Integer a = educationtblService.getMaxIndex(educationtbl.getPersonId());
            if(a==null){
                a = 1;
            }else{
                a++;
            }
            educationtbl.setIndex(String.valueOf(a));
        }
        //type 为2 为编辑状态, 不需要任何操作
        educationtblService.save(educationtbl,type,remarks);
        return "200";
    }

    /**********************学历部分**********************/


    /////////////////职称部分////////////////////////////
    //1.展示列表
    @RequestMapping(value = "queryTitle")
    private String queryTitle(Model model){
        //personId查询
        User user = UserUtils.getUser();
        //根据user查询出enterpriseId
        EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(user);
        String personId = enterpriseWorkers.getId();
        String readOnly = ctrlButton(personId);
        model.addAttribute("readOnly",readOnly);//如果readOnly为200 才可以看见 添加 编辑 删除
        TitleCertificate titleCertificate = new TitleCertificate();
        titleCertificate.setPersonId(personId);
        List<TitleCertificate> list = titleCertificateService.findList(titleCertificate);
        model.addAttribute("list",list);//不使用分页
        model.addAttribute("personId",personId);
        return "modules/counselor/titleList";
    }


    @RequestMapping("editTitle")
    private String editTile(TitleCertificate titleCertificate,Model model,String type){
        if(type.equals("1")){//添加状态
            //只需要把personId 放进去
            // 生成一个id返回去
            // String id0 = titleCertificate.getId();
            String uuid = "";
            for (int i=0;i<20;i++){
                uuid = UUID.randomUUID().toString().replaceAll("-", "");
                TitleCertificate titleCertificateuuid = titleCertificateService.get(uuid);
                if ( "".equals(titleCertificateuuid) || titleCertificateuuid == null ){
                    titleCertificate.setId(uuid);
                    break;
                }
            }
            model.addAttribute("titleCertificate",titleCertificate);

        }else if(type.equals("2")){
            String id = titleCertificate.getId();
            TitleCertificate titleCertificate1 = titleCertificateService.get(id);
//            // 可以加多条数据
//            String [] i11 = {"5","23","8","9","4"};
//            //根据pid 加上 type数组进行查询attachment列表
//            //把属性放入map中进行查询
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("pid",id);
//            if(type.equals("2")){
//                map.put("types",i11);
//            }
            CounselorAttachment counselorAttachment = new CounselorAttachment();
            counselorAttachment.setPid(id);
            counselorAttachment.setType("17");
            List<CounselorAttachment> attachList = counselorAttachmentService.findList(counselorAttachment);
//            List<CounselorAttachment> attachList = counselorAttachmentService.findListByTypeMap(map);
            titleCertificate1.setCounselorAttachmentList(attachList);

            model.addAttribute("titleCertificate",titleCertificate1);
        }
        model.addAttribute("type",type);
        return "modules/counselor/editTitle";
    }

    @RequestMapping("delTitle")
    private String delTitle(String id,String personId){
        TitleCertificate titleCertificate = new TitleCertificate();
        titleCertificate.setId(id);
        titleCertificateService.delete(titleCertificate);
        // 删除这条基本信息的附件
        CounselorAttachment counselorAttachment = new CounselorAttachment();
        counselorAttachment.setPid(id);
        List<CounselorAttachment> attachList = counselorAttachmentService.findList(counselorAttachment);
        if(attachList != null){
            for (CounselorAttachment attachment : attachList) {

                this.counselorAttachmentService.delete(attachment);
            }
        }
        return "redirect:" + adminPath + "/counselor/self/queryTitle?personId="+personId;
    }

    @ResponseBody
    @RequestMapping(value = "saveTitle",produces="application/json;charset=UTF-8")
    private String saveTitle(TitleCertificate titleCertificate,String type,@RequestParam(value="counselorAttachmentListRemarks") String remarks){
        //验证
        String validate = validate(titleCertificate);
        if(!validate.equals("200")){
            //如果验证返回结果不是200
            return validate;
        }

        String personId = titleCertificate.getPersonId();
        //需要设置index
        if(type.equals("1")){//为1的时候为新增状态,需要进行添加index
            Integer a = titleCertificateService.getMaxIndex(titleCertificate.getPersonId());
            if(a==null){
                a = 1;
            }else{
                a++;
            }
            titleCertificate.setIndex(String.valueOf(a));
        }
        //type 为2 为编辑状态, 不需要任何操作
        titleCertificateService.save(titleCertificate,type,remarks);
        return "200";
    }


    /*********************职称部分******************/



    ///////////////////培训院校//////////////////

    @RequestMapping("querySpecialty")
    private String querySpecialty(Model model){//传递的参数是personId
        //personId查询
        User user = UserUtils.getUser();
        //根据user查询出enterpriseId
        EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(user);
        String personId = enterpriseWorkers.getId();
        String readOnly = ctrlButton(personId);
        model.addAttribute("readOnly",readOnly);//如果readOnly为200 才可以看见 添加 编辑 删除
        SpecialtyTrain specialtyTrain = new SpecialtyTrain();
        specialtyTrain.setPersonId(personId);
        List<SpecialtyTrain> list = specialtyTrainService.findList(specialtyTrain);
        model.addAttribute("list",list);//不使用分页
        model.addAttribute("personId",personId);
        return "modules/counselor/specialtyList";
    }


    //编辑,添加. 都走这个方法
    //type 区别 . type 1添加 2 编辑
    @RequestMapping("editSpecialty")
    private String editSpecialty(SpecialtyTrain specialtyTrain,Model model,String type){
    	if(type.equals("1")){//添加状态
            //只需要把personId 放进去
            // 生成一个id返回去
            // String id0 = educationtbl.getId();
            String uuid = "";
            for (int i=0;i<20;i++){
            uuid = UUID.randomUUID().toString().replaceAll("-", "");
            SpecialtyTrain specialtyTrainuuid = specialtyTrainService.get(uuid);
            if ( "".equals(specialtyTrainuuid) || specialtyTrainuuid == null ){
            	specialtyTrain.setId(uuid);
                break;
            }
            }
            model.addAttribute("specialtyTrain",specialtyTrain);

        }else if(type.equals("2")){
             CounselorAttachment counselorAttachment = new CounselorAttachment();
             String id = specialtyTrain.getId();
             SpecialtyTrain specialtyTrain1 = specialtyTrainService.get(id);
             counselorAttachment.setPid(id);
             counselorAttachment.setType("13");// 只取type为16的  学历的类型
             List<CounselorAttachment> attachList = counselorAttachmentService.findList(counselorAttachment);
             specialtyTrain1.setCounselorAttachmentList(attachList);
             model.addAttribute("specialtyTrain",specialtyTrain1);
         }
        model.addAttribute("type",type);
        return "modules/counselor/editSpecialty";
    }

    @RequestMapping("delSpecialty")
    private String delSpecialty(String id,String personId){
        SpecialtyTrain delSpecialty = new SpecialtyTrain();
        delSpecialty.setId(id);
        specialtyTrainService.delete(delSpecialty);
        return "redirect:" + adminPath + "/counselor/self/querySpecialty?personId="+personId;
    }

    @ResponseBody
    @RequestMapping("saveSpecialty")
    private String saveSpecialty(SpecialtyTrain specialtyTrain,String type,@RequestParam(value="counselorAttachmentListRemarks") String remarks){
        //验证
        String validate = validate(specialtyTrain);
        if(!validate.equals("200")){
            //如果验证返回结果不是200
            return validate;
        }

        String personId = specialtyTrain.getPersonId();
        
        //需要设置index
        if(type.equals("1")){//为1的时候为新增状态,需要进行添加index
            Integer a = specialtyTrainService.getMaxIndex(specialtyTrain.getPersonId());
            if(a==null){
                a = 1;
            }else{
                a++;
            }
            specialtyTrain.setIndex(String.valueOf(a));
        }
        specialtyTrainService.save(specialtyTrain,type,remarks);
        
        
        return "200";
    }

    /*****************培训院校************************/


    ///////////////////工作经历///////////////////////

    @RequestMapping(value = "queryJob")
    private String queryJob(Model model){
        User user = UserUtils.getUser();
        EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(user);
        String personId = enterpriseWorkers.getId();
        String readOnly = ctrlButton(personId);//工作经历页面也需要进行按钮控制
        model.addAttribute("readOnly",readOnly);


        //1.根据personId 取得
        JobKnowledge jobKnowledge = new JobKnowledge();
        jobKnowledge.setPersonId(personId);
        List<JobKnowledge> jobKnowledgeList = jobKnowledgeService.findList(jobKnowledge);
        String now = findNow(jobKnowledgeList);
        if(jobKnowledgeList==null||jobKnowledgeList.size()==0||(now!=null&&now.equals("200"))){
            jobKnowledge.setEndDate("至今");
            jobKnowledgeService.save(jobKnowledge);
            jobKnowledgeList = jobKnowledgeService.findList(jobKnowledge);
        }
        jobKnowledge.setJobKnowledgeList(jobKnowledgeList);
        model.addAttribute("personId",personId);
        model.addAttribute("jobKnowledge",jobKnowledge);
        //为2的时候为工作经历
        return "modules/counselor/jobKnowledgeForm";
    }

    //保存工作经历
    @RequestMapping(value = "saveWorkForm")
    public String saveWorkForm(JobKnowledge jobKnowledge,String personId) throws ParseException {
        List<JobKnowledge> jobKnowledgeList = jobKnowledge.getJobKnowledgeList();
        //jobKnowledgeService.save(jobKnowledgeList,tableId,personId);
        jobKnowledgeService.selfSave(jobKnowledgeList,personId);
        //10.30 存储完之后,需要进行更新enterpriseWork里的 工作时间,
        //1 工作时间取值,最早的时间.
        Date date = toDate(jobKnowledgeList);
        EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getById(personId);
        enterpriseWorkers.setEntryDate(date);
        enterpriseWorkersService.save(enterpriseWorkers);

        return "redirect:" + adminPath + "/counselor/self/queryJob";
        //System.out.println(jobKnowledge);
    }

    /*****************工作经历************************/



    /////////////////////////方法区////////////////////////////////

    //根据user 获取personId
    private EnterpriseWorkers getEnterpriseWorkers(User user){
        //List<EnterpriseWorkers> list = enterpriseWorkersService.findList(enterpriseWorkers);
        EnterpriseWorkers enterpriseWorkers =  enterpriseWorkersService.getByNameNum(user.getName(),user.getCardNumber());
        if(enterpriseWorkers!=null&&!enterpriseWorkers.equals("")){
            enterpriseWorkers.setEmail(user.getEmail());
        }
        return enterpriseWorkers;
    }


    //用于验证表单是否填写完整,
    //返回三种类型. 200 正确. 201 填写少了. 202 填写时间不对
    private String validate(Object object){
        //1.传递的是Ecuation
        if(object.getClass().equals(Educationtbl.class)){
            Educationtbl educationtbl  = (Educationtbl)object;
            String v1 = educationtbl.getSchoolType();//办学类型
            String v2 = educationtbl.getSchool();//毕业院校
            String v3 = educationtbl.getSpecialty();//所学专业
            String v4 = educationtbl.getStudyType();//学习方式
            String v5 = educationtbl.getEducation();//学历
            String v6 = educationtbl.getStudyYear();//学制
            Date v7 = educationtbl.getStartTime();//起始时间
            Date v8 = educationtbl.getEndTime();//结束时间
            String v9 = educationtbl.getSchoolMaster();//校长
            String v10 = educationtbl.getZsNo();//证书编号
            //1.查看是否填写完整
            if(v1==null||v1.equals("")||v2==null||v2.equals("")||v3==null||v3.equals("")||v4==null||v4.equals("")||v5==null||v5.equals("")
                    ||v6==null||v6.equals("")||v7==null||v7.equals("")||v8==null||v8.equals("")||v9==null||v9.equals("")||v10==null||v10.equals("")){
                //其中有一项为空, 返回201
                return "201";
            }

            if(v8.getTime()<v7.getTime()){//如果结束时间小于起始时间, 返回202 时间错误
                return "202";
            }
        }else if(object.getClass().equals(TitleCertificate.class)){//职称证书类型
            TitleCertificate title = (TitleCertificate)object;
            String v1 = title.getTitleLevel();//职称级别
            String v2 = title.getTitleType();//职称类型
            String v3 = title.getApproveEmployer();//批准机构
            Date v4 =  title.getApproveTime();//批准时间
            if(v1==null||v1.equals("")||v2==null||v2.equals("")||v3==null||v3.equals("")||v4==null||v4.equals("")){
                //其中有一项为空, 返回201
                return "201";
            }

        }else if(object.getClass().equals(SpecialtyTrain.class)){//培训院校类型
            SpecialtyTrain specialtyTrain = (SpecialtyTrain)object;
            String v1 = specialtyTrain.getTrainSchool();//培训院校
            String v2 = specialtyTrain.getTrainType();//证书类型
            String v3 = specialtyTrain.getSpecialty();//所学专业
            Date v4 = specialtyTrain.getStartTime();//起始时间
            Date v5 = specialtyTrain.getEndTime();//终止时间
            String v6 = specialtyTrain.getCardnum();//证书编号
            String v7 = specialtyTrain.getStudyType();//学习方式
            Date v8 = specialtyTrain.getGetgctime();//颁证时间
            if(v1==null||v1.equals("")||v2==null||v2.equals("")||v3==null||v3.equals("")||v4==null||v4.equals("")||v5==null||v5.equals("")
                    ||v6==null||v6.equals("")||v7==null||v7.equals("")||v8==null||v8.equals("")){
                //其中有一项为空, 返回201
                return "201";
            }
            if(v5.getTime()<v4.getTime()){//如果结束时间小于起始时间, 返回202 时间错误
                return "202";
            }

        }
        return "200";
    }


    //用于控制学历...的添加以及编辑删除 按钮
    private String ctrlButton(String personId){
        //查询declare status !=1 并且 declare status <6 的值. 如果有,表示不可编辑删除
        Integer result = counselorService.getRead(personId);
        if(result!=null&&!result.equals("")&&result>0){
            //不为空,并且大于0; 表示不可编辑
            return "201";
        }
        return "200";
    }

    //查询是否有至今数据
    private String findNow(List<JobKnowledge>list){
        if(list!=null&&!list.equals("")){
            for(JobKnowledge j : list){
                String endDate = j.getEndDate();
                if(endDate!=null&&endDate.equals("至今")){
                    return "201";
                }
            }
        }
        return "200";
    }

    //判断.string 字段,是否为 date 类型数据
    @ResponseBody
    @RequestMapping("isDate")
    private String isDate(JobKnowledge jobKnowledge){
        ArrayList<String> list = new ArrayList<String>();
        List<JobKnowledge> jobKnowledgeList = jobKnowledge.getJobKnowledgeList();
        if(jobKnowledgeList!=null&&jobKnowledgeList.size()>0){
            for(JobKnowledge j : jobKnowledgeList){
                list.add(j.getStartDate());
                String jobContent = j.getJobContent();//必填的工作内容,为空不让提交
                if(jobContent==null||jobContent.equals("")){
                    return "202";
                }
            }
            //通过java util方法进行排序
            Collections.sort(list);
            //String startDate = jobKnowledgeList.get(jobKnowledgeList.size() - 1).getStartDate();
            String startDate = list.get(0);
            String flag="200";
            // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
                format.setLenient(false);
                format.parse(startDate);
            } catch (ParseException e) {
                //e.printStackTrace();
                // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
                flag="201";
            }
            return flag;
        }
        return null;
    }

    private Date toDate(List<JobKnowledge> jobKnowledgeList) throws ParseException {
        ArrayList<String> list = new ArrayList<String>();
        if(jobKnowledgeList!=null&&jobKnowledgeList.size()>0) {
            for(JobKnowledge j : jobKnowledgeList){
                list.add(j.getStartDate());
            }
            Collections.sort(list);
            String date = list.get(0);
            //String date = jobKnowledgeList.get(jobKnowledgeList.size() - 1).getStartDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            Date parse = format.parse(date);
            return parse;
        }
        return null;
    }

    /********************方法区*******************/
}
