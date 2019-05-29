package com.thinkgem.jeesite.modules.counselor.web;

import com.demo.client.IJxjyWsService;
import com.demo.client.JxjyWsService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.AESUtil;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.*;
import com.thinkgem.jeesite.modules.counselor.service.*;
import com.thinkgem.jeesite.modules.enterprise.entity.ContinueEducation;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.service.ContinueEducationService;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseAttachmentService;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.enterprise.service.TempPhotoService;
import com.thinkgem.jeesite.modules.sys.entity.Examine;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//咨询师模块
@Controller
@RequestMapping(value = "${adminPath}/counselor")
public class CounselorController extends BaseController {

    @Autowired
    private CounselorService counselorService;

    @Autowired
    private EnterpriseWorkersService enterpriseWorkersService;

    @Autowired
    private CounselorTableService counselorTableService;

    @Autowired
    private EducationtblService educationtblService;

    @Autowired
    private PersonRegisterService personRegisterService;

    @Autowired
    private TitleCertificateService titleCertificateService;

    @Autowired
    private SpecialtyTrainService specialtyTrainService;

    @Autowired
    private JobKnowledgeService jobKnowledgeService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private AccountantStatusService accountantStatusService;

    @Autowired
    private CounselorExamineRejectService counselorExamineRejectService;

    @Autowired
    private EnterpriseAttachmentService enterpriseAttachmentService;

    @Autowired
    private CounselorAttachmentService counselorAttachmentService;

    @Autowired
    private PersonComplianceService personComplianceService;

    @Autowired
    private ProjectInvestmentService projectInvestmentService;
    
    @Autowired
    private ChangeItemService changeItemService;

    @Autowired
    private TempPhotoService tempPhotoService;

    @Autowired
    private ContinueEducationService continueEducationService;


    private final static JxjyWsService jxjyWsService;
    static{
        jxjyWsService = new JxjyWsService();
    }


    //功能. 申请单查询
    /*
    画面初始化：
    1.查询出该工程师的所有申请记录。
    2.未提交、单位退回、地方协会退回、中咨协会退回状态可编辑、删除，其他状态仅可查看，查看画面参考【申请单添加】画面，此时该画面内所有内容不可修改。
    3.点击编辑按钮，参考【申请单添加】画面，此时画面内容可修改。
    4.删除按钮为数据逻辑删除。     */
    @RequestMapping(value = "list")
    public String list(Counselor counselor,HttpServletRequest request, HttpServletResponse response, Model model){
        User user = UserUtils.getUser();
        EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(user);
        //根据userId 查询出该工程师的申请记录
        if( enterpriseWorkers != null ){
            counselor.setPersonId(enterpriseWorkers.getId());
        }
        Page<Counselor> page = counselorService.findPage(new Page<Counselor>(request, response), counselor);
        /*String path = this.getClass().getResource("").getPath();
        System.out.println(path);*/
        model.addAttribute("page",page);
        return "modules/counselor/counselorList";
    }

    @RequestMapping(value = "search")
    public String search(Counselor counselor,HttpServletRequest request, HttpServletResponse response, Model model/*,Date endDate,Date startDate*/) throws ParseException {
        /*if(counselor.getDeclareDate()!=null&&!counselor.getDeclareDate().equals("")){
            Date date = counselor.getDeclareDate();

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
            Date tomorrow = c.getTime();
            counselor.setCreateDate(tomorrow);
        }*/
        //时间需要进行判断
       /* counselor.setStartDate(startDate);
        counselor.setEndDate(endDate);*/
        Page<Counselor> page = counselorService.findPage(new Page<Counselor>(request, response), counselor);
        model.addAttribute("page",page);

       model.addAttribute("counselor",counselor);
        return "modules/counselor/counselorList";
    }





    //跳转到初始登记页面
    @RequestMapping(value = "firstIndex")
    public String firstIndex(String id,Model model,String look){//look 作为标识,用作区分编辑与查看

        model.addAttribute("look",look);
        User user= UserUtils.getUser();
        Counselor counselor = counselorService.get(id);
        //10.17 查看退回原因超链接是否存在. 当状态为22的时候,可以显示
        if(counselor.getHgReturn()!=null){
            model.addAttribute("hgReturn",true);
        }
        if(counselor.getReturnReason()!=null){
            model.addAttribute("ysReturn",true);
        }
        if(counselor.getReceiveReason()!=null){
            model.addAttribute("reReturn",true);
        }
        if (counselor.getFirstZdeclareResult()!=null){
        	model.addAttribute("firstZdeclareResult",counselor.getFirstZdeclareResult());
        }
        if (counselor.getSecondZdeclareResult()!=null){
        	model.addAttribute("secondZdeclareResult",counselor.getSecondZdeclareResult());
        }
        if (counselor.getFyAdvice()!=null){
        	model.addAttribute("fyAdvice",counselor.getFyAdvice());
        }

        String type = counselor.getDeclareType();
        String status = counselor.getDeclareStatus();
        model.addAttribute("status",status);
        //先根据user 的 名字与身份证号取 基本信息
        EnterpriseWorkers enterpriseWorkers = this.getEnterpriseWorkers(user);
       if(enterpriseWorkers==null||enterpriseWorkers.equals("")){
           //这种情况是没有在enterpriseWorker表里查找到数据的

           return null;
       }
        CounselorTable counselorTable = new CounselorTable();
        counselorTable.setPersonRecordId(id);
        List<CounselorTable> tableList = counselorTableService.findList(counselorTable);
        model.addAttribute("personRecordId",id);
        model.addAttribute("tableList",tableList);
        model.addAttribute("enterpriseWorkers",enterpriseWorkers);
        model.addAttribute("counselor",counselor);
        if(type.equals("1")){
            //跳转初次变更
            return "modules/counselor/counselorFirstIndex";
        }else if(type.equals("2")){
            //变更执业
            return "modules/counselor/applyIndex";
        }else if(type.equals("3")){
            //变更专业
            return "modules/counselor/specialtyIndex";
        }else if(type.equals("4")){
            return "modules/counselor/continueIndex";
        }else if(type.equals("0")){
            return "modules/counselor/cancelIndex";
        }
       return null;
    }

    //跳转增加申请单页面
    @RequestMapping(value = "applyIndex")
    public String applyIndex(String id,Model model){
        //添加申请单页面需要判断此用户是否登记有效
        EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
        model.addAttribute("enterpriseWorkers",enterpriseWorkers);
        return "modules/counselor/counselorApplyIndex";

    }


    //申请项进行修改
    @RequestMapping(value = "tableForm")
    public String tableForm(Model model,String id,String personId,String recordId,Educationtbl educationtbl,SpecialtyTrain specialtyTrain,TitleCertificate titleCertificate,String look){
        model.addAttribute("look",look);
        model.addAttribute("tableId",id);
        model.addAttribute("personId",personId);
        model.addAttribute("recordId",recordId);
        model.addAttribute("user",UserUtils.getUser());

        Counselor counselor = counselorService.get(recordId);
        //1.先查询出这一项
        CounselorTable counselorTable = counselorTableService.get(id);
        model.addAttribute("tableType",counselor.getDeclareType());
        //2.根据type 选择跳转不同的页面
        if(counselor.getDeclareType().equals("1")){//初始登记
            if(counselorTable.getType().equals("1")){
            //为1 的时候,跳转初始登记的基本情况页面
            //查询出3项新增加的
            Map infoMap = infoMap(personId);
            //map用于存储三个基本项
            model.addAttribute("infoMap",infoMap);
            //有了tableId 以及 personId 进行联查
            //考虑情况
            //1.当没有申请登记情况的 状态下, 不应关联查询. 应该只查询enterprise
            /*
            步骤. 1.查询申请登记表. 每一个tableId 只能绑定一条数据ss
                  2.判断是否存在,如果存在就用新方法查询,如果不存在就值查enterpriseWorkers,并且也用新方法查询
            * */

                EnterpriseWorkers e1 = enterpriseWorkersService.get(personId);
                String mobile = e1.getMobile();
                model.addAttribute("mobile",mobile);
                EnterpriseWorkers enterpriseWorkers = null;
            PersonRegister personRegister = personRegisterService.getByTableId(id);
            if(personRegister==null){
                enterpriseWorkers=enterpriseWorkersService.getByPersonId(personId);
            }else {
                 enterpriseWorkers = enterpriseWorkersService.getByTablePerson(id, personId);
            }
            
            //展示的时候, 需要把出生日期以及email插入进去.
            Map map = this.showEnterpriseWorers(enterpriseWorkers);
            boolean flag = false;
            Integer result = 200;
            if(enterpriseWorkers!=null&&!enterpriseWorkers.equals("")){
                String temp = enterpriseWorkers.getReviewCompany();
                if(temp!=null&&!temp.equals("")){
                String s = StringEscapeUtils.unescapeHtml4(temp);
                enterpriseWorkers.setReviewCompany(s);
                }
                String isfifth =enterpriseWorkers.getIsRegister();//如果取出的isfifth为空，则赋值默认值0
                if(isfifth==null || isfifth.equals("")){
                enterpriseWorkers.setIsRegister("0");
                }
                model.addAttribute("isfifth",enterpriseWorkers.getIsRegister());
                Integer num = tempPhotoService.findByNameAndCard(enterpriseWorkers.getName(),enterpriseWorkers.getCertificatesNum());
                if(num>0){
                    flag=true;
                }
                Integer resultNum = counselorService.getRead(enterpriseWorkers.getId());
                if(resultNum!=null&&!resultNum.equals("")&&resultNum>0){
                    //不为空,并且大于0; 表示不可编辑
                    result=201;
                }
            }

                //使用新方法进行查询. 查询的enterpriseWorkersService 需要进行

            model.addAttribute("map",map);
            model.addAttribute("result",result);
            model.addAttribute("flag",flag);
            model.addAttribute("user",UserUtils.getUser());
            model.addAttribute("enterpriseWorkers",enterpriseWorkers);
            //取得现执业单位的名称

            return "modules/counselor/counselorFirstInfoForm";
        }else if(counselorTable.getType().equals("2")){
                //10.12 更变需求,工作经历必须在提交后才可以
                String batchStatus = counselor.getBatchStatus();
                //如果 batch的状态不是1,那么就不能修改. 修改的值是以isFiirst进行判断的, 为1的时候,不可修改


                EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
                if(batchStatus!=null&&!batchStatus.equals("1")){
                    enterpriseWorkers.setIsFirst("1");
                }else{
                    enterpriseWorkers.setIsFirst(null);
                }
                /////////////////////////////////////////
                model.addAttribute("enterpriseWorkers",enterpriseWorkers);
            JobKnowledge jobKnowledge = new JobKnowledge();
            jobKnowledge.setPersonId(personId);

                List<JobKnowledge> jobKnowledgeList = jobKnowledgeService.findList(jobKnowledge);
                //10.26 增加新功能, 当list为空, 或者 新查询的endDate 没有至今的时候,才增加
                String now = findNow(jobKnowledgeList);//如果有至今,返回true . 因此
                if(jobKnowledgeList==null||jobKnowledgeList.size()==0||(now!=null&&now.equals("200"))){
                    jobKnowledge.setEndDate("至今");
                    jobKnowledgeService.save(jobKnowledge);
                    jobKnowledgeList = jobKnowledgeService.findList(jobKnowledge);
                }
                //List<JobKnowledge>jobKnowledgeList = jobKnowledgeService.findByTableId(id);
            jobKnowledge.setJobKnowledgeList(jobKnowledgeList);
            model.addAttribute("jobKnowledge",jobKnowledge);
            List<Counselor> lcounselor =counselorService.findWorkRecord(personId);
            if(lcounselor!=null && lcounselor.size()!=0)
            {
            	model.addAttribute("isAdd","1");
            }
            //为2的时候为工作经历
            return "modules/counselor/counselorFirstWorkForm";

        }else if(counselorTable.getType().equals("3")) {
                //为3的时候为相关附件
                //有一条是根据基本情况看出现不出现的
                //需要把所有的类型分开进行封装.


                //附件分为两部分, 1 部分是通过person+type 查找的 ,
                //                2. 直接根据table
                //List<CounselorAttachment>list  = counselorAttachmentService.findNewList(personId,counselorTable.getType(),id);
                CounselorAttachment counselorAttachment = new CounselorAttachment();
                counselorAttachment.setTableId(id);
                List<CounselorAttachment> list = counselorAttachmentService.findList(counselorAttachment);
                model.addAttribute("attachList",list);
                //新增条件,申请过但失效的，无需上传职业资格证书. isValied 字段
                EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
                String isValid = enterpriseWorkers.getIsValid();
                model.addAttribute("isValid",isValid);
                return "modules/counselor/counselorFirstAttachForm";
            }
        }else if(counselor.getDeclareType().equals("2")){//查询变更执业单位
            if(counselorTable.getType().equals("1")){//跳转基本信息
                //查询基本信息
                EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
                //查询执业情况表
                AccountantStatus accountantStatus =  accountantStatusService.getByTableId(id);
                if(accountantStatus==null||accountantStatus.equals("")){
                    accountantStatus= new AccountantStatus();
                }
                model.addAttribute("accountantStatus",accountantStatus);
                String jobCompanyName = accountantStatus.getJobCompanyName();
                if(jobCompanyName!=null&&!jobCompanyName.equals("")){
                    String temp = StringEscapeUtils.unescapeHtml4(jobCompanyName);
                    accountantStatus.setJobCompanyName(temp);
                }

                ChangeItem item = new ChangeItem();
                item.setChangeType("1");
                item.setRecordId(recordId);
                List<ChangeItem> list = changeItemService.findList(item);
                if(list!=null&&list.size()>0){
                    //取第一个最早的一个
                    String newcompanyName = list.get(list.size()-1).getNewValue();
                    String oldCompanyName = list.get(0).getOldValue();
                    enterpriseWorkers.setCompanyName(oldCompanyName);
                    String officeName = enterpriseWorkersService.getOfficeName(newcompanyName);
                    enterpriseWorkers.setOfficeName(officeName);
                }
                //增加调入现执业单位时间,使用
                JobKnowledge jobKnowledge = jobKnowledgeService.findLast(personId);
                if(jobKnowledge!=null&&!jobKnowledge.equals("")){
                String startDate = jobKnowledge.getStartDate();
                model.addAttribute("startDate",startDate);
                }
                model.addAttribute("enterpriseWorkers",enterpriseWorkers);//需要根据最新的item表查找数据
                return "modules/counselor/counselorApplyInfoForm";

            }else if(counselorTable.getType().equals("2")){ //跳转工作经历
                String batchStatus = counselor.getBatchStatus();
                //如果 batch的状态不是1,那么就不能修改. 修改的值是以isFiirst进行判断的, 为1的时候,不可修改
                EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
                if(batchStatus!=null&&!batchStatus.equals("1")){
                    enterpriseWorkers.setIsFirst("1");
                }else{
                    enterpriseWorkers.setIsFirst(null);
                }
                model.addAttribute("enterpriseWorkers",enterpriseWorkers);
                JobKnowledge jobKnowledge = new JobKnowledge();
                jobKnowledge.setPersonId(personId);
                List<JobKnowledge> jobKnowledgeList = jobKnowledgeService.findList(jobKnowledge);
                String now = findNow(jobKnowledgeList);
                if(jobKnowledgeList==null||jobKnowledgeList.size()==0||(now!=null&&now.equals("200"))){
                    jobKnowledge.setEndDate("至今");
                    jobKnowledgeService.save(jobKnowledge);
                    jobKnowledgeList = jobKnowledgeService.findList(jobKnowledge);
                }
                //List<JobKnowledge>jobKnowledgeList = jobKnowledgeService.findByTableId(id);
                jobKnowledge.setJobKnowledgeList(jobKnowledgeList);
                model.addAttribute("jobKnowledge",jobKnowledge);
                List<Counselor> lcounselor =counselorService.findWorkRecord(personId);
                if(lcounselor!=null && lcounselor.size()!=0)
                {
                	model.addAttribute("isAdd","1");
                }
                //为2的时候为工作经历
                return "modules/counselor/counselorApplyWorkForm";
            }else if(counselorTable.getType().equals("3")){//跳转相关附件
                CounselorAttachment counselorAttachment = new CounselorAttachment();
                counselorAttachment.setTableId(id);
                List<CounselorAttachment> list = counselorAttachmentService.findList(counselorAttachment);
                //List<CounselorAttachment>list  = counselorAttachmentService.findNewList(personId,counselorTable.getType(),id);
                model.addAttribute("attachList",list);
                return "modules/counselor/counselorApplyAttachForm";
            }
        }else if(counselor.getDeclareType().equals("3")){//查询变更专业
            if(counselorTable.getType().equals("1")){//跳转基本信息
               // EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.get(personId);
                EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
                //查询执业情况表
                AccountantStatus accountantStatus =  accountantStatusService.getByTableId(id);
                if(accountantStatus==null||accountantStatus.equals("")){
                    accountantStatus= new AccountantStatus();
                }
                String jobCompanyName = accountantStatus.getJobCompanyName();
                if(jobCompanyName!=null&&!jobCompanyName.equals("")){
                    String temp = StringEscapeUtils.unescapeHtml4(jobCompanyName);
                    accountantStatus.setJobCompanyName(temp);
                }
                Map infoMap = infoMap(personId);
                //map用于存储三个基本项
                model.addAttribute("infoMap",infoMap);
                model.addAttribute("accountantStatus",accountantStatus);
                model.addAttribute("enterpriseWorkers",enterpriseWorkers);
                return "modules/counselor/counselorSpecialtyInfoForm";
            }else if(counselorTable.getType().equals("2")){ //跳转工作经历
                String batchStatus = counselor.getBatchStatus();
                //如果 batch的状态不是1,那么就不能修改. 修改的值是以isFiirst进行判断的, 为1的时候,不可修改
                EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
                if(batchStatus!=null&&!batchStatus.equals("1")){
                    enterpriseWorkers.setIsFirst("1");
                }else{
                    enterpriseWorkers.setIsFirst(null);
                }
                model.addAttribute("enterpriseWorkers",enterpriseWorkers);
                JobKnowledge jobKnowledge = new JobKnowledge();
                jobKnowledge.setPersonId(personId);
                List<JobKnowledge> jobKnowledgeList = jobKnowledgeService.findList(jobKnowledge);
                String now = findNow(jobKnowledgeList);
                //10.22 当list为0的时候,添加一个至今值
                if(jobKnowledgeList==null||jobKnowledgeList.size()==0||(now!=null&&now.equals("200"))){
                    jobKnowledge.setEndDate("至今");
                    jobKnowledgeService.save(jobKnowledge);
                    jobKnowledgeList = jobKnowledgeService.findList(jobKnowledge);
                }

                jobKnowledge.setJobKnowledgeList(jobKnowledgeList);
                model.addAttribute("jobKnowledge",jobKnowledge);
                List<Counselor> lcounselor =counselorService.findWorkRecord(personId);
                if(lcounselor!=null && lcounselor.size()!=0)
                {
                	model.addAttribute("isAdd","1");
                }
                //为2的时候为工作经历
                return "modules/counselor/counselorSpecialtyWorkForm";
            }else if(counselorTable.getType().equals("3")){ //跳转相关附件
                //附件需要进行判断,         提交业绩后，相关附件中增加业绩扫描件。
                //业绩的状态为6. .先查询table中是否有6的,再查询状态是否为1.
                CounselorTable table = counselorTableService.getNew(recordId, "6");
                if(table!=null&&!table.equals("")){//判断table是否为空
                    String status = table.getStatus();
                    if(status!=null&&status.equals("1")){
                        //确认状态,给设置一个flag,用于确定他已经提交
                        model.addAttribute("comfirm","comfirm");
                    }
                }

                CounselorAttachment counselorAttachment = new CounselorAttachment();
                counselorAttachment.setTableId(id);
                List<CounselorAttachment> list = counselorAttachmentService.findList(counselorAttachment);
                model.addAttribute("attachList",list);
                return "modules/counselor/counselorSpecialtyAttachForm";
            }else if(counselorTable.getType().equals("6")){ //跳转工程咨询业绩
                ProjectInvestment projectInvestment = new ProjectInvestment();
                EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
                enterpriseWorkers.setRegisterMainSpecialty("");
                enterpriseWorkers.setRegisterAuxiliarySpecialty("");
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("recordId",recordId);
                map.put("changeType",new String[]{"2","3"});
                List<ChangeItem> itemList = changeItemService.findItemList(map);
                if(itemList!=null){
                for(ChangeItem item:itemList){
                    if(item.getChangeType().equals("2")){
                        enterpriseWorkers.setRegisterMainSpecialty(item.getNewValue());
                    }else if(item.getChangeType().equals("3")){
                        enterpriseWorkers.setRegisterAuxiliarySpecialty(item.getNewValue());
                      }
                    }
                }
                projectInvestment.setTableId(id);
                List<ProjectInvestment> list = projectInvestmentService.findList(projectInvestment);
                projectInvestment.setProjectList(list);
                model.addAttribute("enterpriseWorkers",enterpriseWorkers);
                model.addAttribute("projectInvestment",projectInvestment);
                return "modules/counselor/counselorSpecialtyProjectForm";
            }else if(counselorTable.getType().equals("7")){ //跳转相关附件
                //1.value 写方法
                List list = type7(counselorTable.getId(), recordId);
                model.addAttribute("list",list);
                return "modules/counselor/counselorSpecialtyProjectAttachForm";
            }

        }else if(counselor.getDeclareType().equals("4")){//继续登记
            if(counselorTable.getType().equals("1")){//跳转基本信息
               // EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.get(personId);
                EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
                //查询申请登记

                PersonRegister personRegister = personRegisterService.getByTableId(id);
                if(personRegister==null||personRegister.equals("")){
                    personRegister= new PersonRegister();
                }
                String jobCompanyName = personRegister.getJobCompanyName();
                if(jobCompanyName!=null&&!jobCompanyName.equals("")){
                    String temp = StringEscapeUtils.unescapeHtml4(jobCompanyName);
                    personRegister.setJobCompanyName(temp);
                }
                model.addAttribute("personRegister",personRegister);
                model.addAttribute("enterpriseWorkers",enterpriseWorkers);
                return "modules/counselor/counselorContinueInfoForm";

            }else if(counselorTable.getType().equals("2")){ //跳转jobList
                String batchStatus = counselor.getBatchStatus();
                //如果 batch的状态不是1,那么就不能修改. 修改的值是以isFiirst进行判断的, 为1的时候,不可修改
                EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
                if(batchStatus!=null&&!batchStatus.equals("1")){
                    enterpriseWorkers.setIsFirst("1");
                }else{
                    enterpriseWorkers.setIsFirst(null);
                }
                model.addAttribute("enterpriseWorkers",enterpriseWorkers);
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
                model.addAttribute("jobKnowledge",jobKnowledge);
                List<Counselor> lcounselor =counselorService.findWorkRecord(personId);
                if(lcounselor!=null && lcounselor.size()!=0)
                {
                	model.addAttribute("isAdd","1");
                }
                //为2的时候为工作经历
                return "modules/counselor/counselorContinueWorkForm";
            }else if(counselorTable.getType().equals("3")){ //跳转相关附件
                CounselorAttachment counselorAttachment = new CounselorAttachment();
                counselorAttachment.setTableId(id);
                List<CounselorAttachment> list = counselorAttachmentService.findList(counselorAttachment);
               // List<CounselorAttachment>list  = counselorAttachmentService.findNewList(personId,counselorTable.getType(),id);
                model.addAttribute("attachList",list);
                return "modules/counselor/counselorContinueAttachForm";
            }
        }else if(counselor.getDeclareType().equals("0")){
            if(counselorTable.getType().equals("1")){//跳转基本信息
                EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
                model.addAttribute("enterpriseWorkers",enterpriseWorkers);
                model.addAttribute("counselor",counselor);
                return "modules/counselor/counselorCancelInfoForm";
            }else if(counselorTable.getType().equals("3")){//跳转相关附件
                CounselorAttachment counselorAttachment = new CounselorAttachment();
                counselorAttachment.setTableId(id);
                List<CounselorAttachment> list = counselorAttachmentService.findList(counselorAttachment);
                model.addAttribute("attachList",list);
                return "modules/counselor/counselorCancelAttachForm";
            }
        }
        return null;
    }

    //新的需求,每一个openJsp 没有修改, 只有增加与删除 .因此没有查询
    @RequestMapping(value = "openJsp")
    public String openJsp(String personId,String tableId, String num, Model model, Educationtbl educationtbl, SpecialtyTrain specialtyTrain, TitleCertificate titleCertificate){
        //获取personRecord id , 类型 num , 1. 学历教育情况  2. 职称证书情况, 3, 专业培训情况
        model.addAttribute("tableId",tableId);
        model.addAttribute("personId",personId);
        if(num.equals("1")){
            //根据tableid 查找到该对象
            model.addAttribute("educationtbl",educationtbl);
            return "modules/counselor/infoForm1";
        }else if(num.equals("2")){
            Object obj = zhuanyi(titleCertificate);
            model.addAttribute("titleCertificate",obj);
            return "modules/counselor/infoForm2";
        }else if(num.equals("3")){
            model.addAttribute("specialtyTrain",specialtyTrain);
            return "modules/counselor/infoForm3";
        }
        return "modules/counselor/counselorFirstIndex";
    }

    //某些字符串需要进行转义,
    private Object zhuanyi(Object obj){
        if(obj.getClass()==TitleCertificate.class){
        TitleCertificate title = (TitleCertificate) obj;
        if(title.getGetEmployer()!=null&&!title.getGetEmployer().equals("")){
            String temp = StringEscapeUtils.unescapeHtml4(title.getGetEmployer());
            title.setGetEmployer(temp);
            }
            return title;
        }
        return null;
    }


//审查画面
    /*
    * 1.展示左侧树,点击各个子项,展示的东西不同,
    * 跳转树列表
    * */

/////////////////////////////////////////涉及到保存区域///////////////////////////////////
    //保存基本信息的时候需要替换参数
    @RequestMapping(value = "saveInfo")
    public String saveInfo(EnterpriseWorkers enterpriseWorkers,String tableId,String recordId){

        //写一个方法进行封装
        this.saveMethod(enterpriseWorkers,tableId);
        //需要把申请主专业与申请辅专业保存到变更履历表,单独写方法
        this.saveChangeItem(enterpriseWorkers,recordId);

        //申请登记状态 替换的参数有8个
        // 1.company_name 执业单位名称  2.job_company_name 工作单位名称 3.backupnum 备案编号 4.backuptype 备案情况
        // 5.company_type 工作单位性质 6. iseducate 继续教育是否满足 7. register_main_specialty 主专业     8.register_auxiliary_specialty 辅专业
        //接收方式. 1.companyName   2.reviewCompany      3.registerCertificateNum        4.fileNo
        //          5.companyArea           6.mobile               7.registerMainSpecialty     8.registerAuxiliarySpecialty
        return "redirect:" + adminPath + "/counselor/tableForm?id="+tableId+"&personId="+enterpriseWorkers.getId()+"&recordId="+recordId;
    }

    @RequestMapping(value = "saveConfirm")
    public String saveConfirm(EnterpriseWorkers enterpriseWorkers,String tableId,String recordId){
        //比保存过一个步骤, 需要改变table状态

        this.saveInfo(enterpriseWorkers,tableId,recordId);
        CounselorTable counselorTable = counselorTableService.get(tableId);
        counselorTable.setStatus("1");
        counselorTableService.save(counselorTable);
        return "redirect:" + adminPath + "/counselor/firstIndex?id="+recordId;
    }

    //保存申请单
    @RequestMapping(value = "saveIndex")
    public String saveIndex(){
        //需要进行判断,
        return null;
    }


    @RequestMapping(value = "delete")
    public String delete(String id){//删除的时候还需要把attachment表里的数据删掉
        Counselor counselor = counselorService.get(id);
        changeEnterprise(counselor);
        String batchId = counselor.getBatchId();
        counselorService.delete(counselor);
        //先查询出tableId , 相关附件attachment的type 为3
        CounselorTable table = counselorTableService.getNew(id, "3");
        if(table!=null){
        String tableId = table.getId();
        CounselorAttachment counselorAttachment = new CounselorAttachment();
        counselorAttachment.setTableId(tableId);
        counselorAttachmentService.delete(counselorAttachment);
        }
        //通过批次进行更变
        deleteMethod(batchId);
        return "redirect:" + adminPath + "/counselor/list";
    }
    //删除变更执业单位的时候,需要把enterprise表的 companyName,pid 还原
    private void changeEnterprise(Counselor counselor){
        //1.先查询是否为变更执业单位
        String declareType = counselor.getDeclareType();
        if(declareType.equals("2")) {//为变更执业单位.
            //2.根据type 与recordId 查询item表
            String recordId = counselor.getId();
            ChangeItem item1 = new ChangeItem();
            item1.setChangeType("1");
            item1.setRecordId(recordId);
            List<ChangeItem> list = changeItemService.findList(item1);
            if (list!=null&&list.size()>0) {
                ChangeItem item = list.get(0);
                if (item != null && !item.equals("")) {
                String companyName = item.getOldValue();
                //3.获取 enterprise
                String personId = counselor.getPersonId();
                EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getById(personId);
                enterpriseWorkers.setCompanyName(companyName);
                //4.再根据companyName 查找到pid
                User user = systemService.getUserByName(companyName);
                if (user != null) {
                    String pid = user.getId();
                    enterpriseWorkers.setPid(pid);
                }
                enterpriseWorkersService.save(enterpriseWorkers);
            }
        }
        }
    }

    //删除方法,
    public void deleteMethod(String batchId){//删除的时候还需要进行查询,
        //如果删除完毕后,根据batchId 查询,如果表单还剩一个, 那么他的batchStatus 状态 改为 batch
        Counselor counselor = new Counselor();
        counselor.setBatchId(batchId);
        List<Counselor> list = counselorService.findList(counselor);
        if(list!=null&&list.size()==1){
            Counselor counselor1 = list.get(0);
            String type = counselor1.getDeclareStatus();
            counselor1.setBatchStatus(type);
            counselorService.save(counselor1);
        }

    }

    @RequestMapping(value = "saveWorkForm")
    public String saveWorkForm(JobKnowledge jobKnowledge,String tableId,String personId,String flag,String recordId) throws ParseException {
        List<JobKnowledge> jobKnowledgeList = jobKnowledge.getJobKnowledgeList();
        jobKnowledgeService.save(jobKnowledgeList,tableId,personId);
        //10.30 存储完之后,需要进行更新enterpriseWork里的 工作时间,
        //1 工作时间取值,最早的时间.
        Date date = toDate(jobKnowledgeList);
        EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getById(personId);
        enterpriseWorkers.setEntryDate(date);
        enterpriseWorkersService.save(enterpriseWorkers);
        if(flag!=null&&!flag.equals("")){
            //当flag有值的时候,表示点击的是确认
            CounselorTable counselorTable = counselorTableService.get(tableId);
            counselorTable.setStatus("1");
            counselorTableService.save(counselorTable);
            //点击确定之后,需要把worker表里的 isFirst 进行 修改 为1. 表示后面的操作不可更改

            ///////////
            return "redirect:" + adminPath + "/counselor/firstIndex?id="+recordId;
        }

        //区别此table的状态,如果状态为1的话,跳转路径需要添加flag =0
        CounselorTable table = counselorTableService.get(tableId);
        String status = table.getStatus();
        if(status!=null&&status.equals("1")){//为1 是确认状态, 不可出现确认完成按钮 , 添加flag字段
            return "redirect:" + adminPath + "/counselor/tableForm?id="+tableId+"&personId="+personId+"&recordId="+recordId+"&look=1";
        }else{
            return "redirect:" + adminPath + "/counselor/tableForm?id="+tableId+"&personId="+personId+"&recordId="+recordId;
        }

        //System.out.println(jobKnowledge);
    }


    //判断.string 字段,是否为 date 类型数据
    @ResponseBody
    @RequestMapping("isDate")
    private String isDate(JobKnowledge jobKnowledge){
        ArrayList<String> list = new ArrayList<String>();
        List<JobKnowledge> jobKnowledgeList = jobKnowledge.getJobKnowledgeList();
        EnterpriseWorkers eworker =new EnterpriseWorkers();
        eworker = enterpriseWorkersService.getByNameNum(UserUtils.getUser().getName(), UserUtils.getUser().getCardNumber()) ;
        if(jobKnowledgeList!=null&&jobKnowledgeList.size()>0){
        	int endnum=0;
            for(JobKnowledge j : jobKnowledgeList){
                list.add(j.getStartDate());
                if ("至今".equals(j.getEndDate()))
                {
                	endnum+=1;
                }
                String jobContent = j.getJobContent();//必填的工作内容,为空不让提交
                if(jobContent==null||jobContent.equals("")){
                	if ("1".equals(eworker.getIsValid()) && "至今".equals(j.getEndDate())){
                		return "204";
                	}else if("2".equals(eworker.getIsValid())||"0".equals(eworker.getIsValid())){
                		return "202";
                	}
                	else{}
                }
            }
            if (endnum==0)//如果工作经历里没有出现至今的记录则反馈错误
            {
            	return "203";
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

    ////////////////////////////////////////////////////////////ajax 区域///////////////////////////////////////////////////////////////


    @ResponseBody
    @RequestMapping(value = "saveForm1")
    public String saveForm1(Educationtbl educationtbl,String tableId,String personId){
        String result = validateForm1(educationtbl);
        if(result.equals("201")){
            return "201";
        }
        //save之前需要查询最大index
        Educationtbl e = new Educationtbl();
        e.setPersonId(personId);
        e.setTableId(tableId);
        Integer a = educationtblService.getMax(e);

        educationtbl.setTableId(tableId);
        educationtbl.setPersonId(personId);
        if(a==null){
            a = 1;
        }else{
            a+=1;
        }
        educationtbl.setIndex(String.valueOf(a));
        educationtblService.save(educationtbl);
        return "200";
    }

    //saveForm1  进验证
    private String validateForm1(Educationtbl educationtbl){
        //10项进行验证.
        String v1 = educationtbl.getSchoolType();//学校类型
        String v2 =educationtbl.getSchool();//毕业院校
        String v3 =educationtbl.getSpecialty();//专业
        String v4 =educationtbl.getStudyType();//学习方式
        String v5 =educationtbl.getEducation();//学历
        String v6 =educationtbl.getStudyYear();//学年
        Date v7 =educationtbl.getStartTime();//起始
        Date v8 =educationtbl.getEndTime();//终
        String v9 =educationtbl.getSchoolMaster();//院长
        String v10 =educationtbl.getZsNo();//颁证
        if((v1==null||v1.equals(""))||(v2==null||v2.equals(""))||(v3==null||v3.equals(""))||(v4==null||v4.equals(""))||(v5==null||v5.equals(""))||(v6==null||v6.equals(""))||(v7==null||v7.equals(""))||(v8==null||v8.equals(""))||(v9==null||v9.equals(""))||(v10==null||v10.equals(""))){
                return "201";
        }
        return "200";
    }

    @ResponseBody
    @RequestMapping(value = "saveForm2")
    public String saveForm2(TitleCertificate titleCertificate,String tableId,String personId){
        TitleCertificate t = new TitleCertificate();
        t.setPersonId(personId);
        t.setTableId(tableId);
        Integer a = titleCertificateService.getMax(t);
        titleCertificate.setTableId(tableId);
        titleCertificate.setPersonId(personId);
        if(a==null){
            a = 1;
        }else{
            a+=1;
        }
        titleCertificate.setGetEmployer(StringEscapeUtils.unescapeHtml4(titleCertificate.getGetEmployer()));
        titleCertificate.setIndex(String.valueOf(a));
        titleCertificateService.save(titleCertificate);
        return "200";
    }

    @ResponseBody
    @RequestMapping(value = "saveForm3")
    public String saveForm3(SpecialtyTrain specialtyTrain,String tableId,String personId){
        SpecialtyTrain s = new SpecialtyTrain();
        s.setPersonId(personId);
        s.setTableId(tableId);
        Integer a = specialtyTrainService.getMax(s);
        if(a==null){
            a = 1;
        }else{
            a+=1;
        }

        specialtyTrain.setIndex(String.valueOf(a));
        specialtyTrain.setTableId(tableId);
        specialtyTrain.setPersonId(personId);
        specialtyTrainService.save(specialtyTrain);
        return "200";
    }

    /**
     * 根据Enterprise去全国咨询工程师继续教育系统获得学员考核结果信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "submitGetEducation")
    public String submitGetEducation(String id) throws Exception{
        EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getById(id);
        if(enterpriseWorkers!=null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
            Integer year = null;
            if(enterpriseWorkers.getValidDate()!=null){
                String date = simpleDateFormat.format(enterpriseWorkers.getValidDate());
                year  = Integer.valueOf(date.substring(0,4));
            }else{
                String date = simpleDateFormat.format(new Date());
                year  = Integer.valueOf(date.substring(0,4));
            }
            String key = "ae3dea013432d2fc";
            IJxjyWsService port = jxjyWsService.getJxjyWsServicePort();
            String resultXml = port.getQualifiedForCountyCounselor("",enterpriseWorkers.getId(),key,"1","50");
            String result = AESUtil.AES_Decrypt(key,resultXml);
            if(this.result(result).equals("203")){
                return "203";
            }else if(this.result(result).equals("200")){
                List<String> yearList = continueEducationService.getAllYear(id);
                List<String> list = this.resultData(result,yearList);
                if(list!=null &&list.size()>0){
                    for (String s :list){
                        ContinueEducation continueEducation = new ContinueEducation();
                        continueEducation.setCardNum(enterpriseWorkers.getCertificatesNum());
                        continueEducation.setEducationFlag("1");
                        continueEducation.setName(enterpriseWorkers.getName());
                        continueEducation.setWorkerId(id);
                        continueEducation.setYear(s);
                        continueEducation.setIsNewRecord(true);
                        continueEducation.setId(IdGen.uuid());
                        continueEducationService.save(continueEducation);
                    }
                }

                List<String> newYearList = continueEducationService.getAllYearThree(id,year);
                return newYearList.size()+"";
            }else{
                List<String> newYearList = continueEducationService.getAllYearThree(id,year);
                return newYearList.size()+"";
            }

        }
        return null;
    }

    /**
     * 根据Enterprise去查询这个人去年有没有通过考试
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "submitGetTempPhoto")
    public String submitGetTempPhoto(String id,String year) throws Exception{
        EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getById(id);
        if(enterpriseWorkers!=null){
            Integer count = tempPhotoService.findByCardAndYear(enterpriseWorkers.getCertificatesNum(),Integer.valueOf(year)-1);
            return count+"";
        }

        return null;
    }


    /**
     * 判断是否有效
     * @param xml
     * @return
     */
    private String result(String xml){//type 用于区分两个接口的类型
        try {
            Document doc = DocumentHelper.parseText(xml);
            //指向根节点
            Element root = doc.getRootElement();
            List<Element> elements = root.elements();
            for (Element element : elements) {
                String name = element.getName();
                if(name.equals("Code")){
                    String text = element.getText();
                    if(text.equals("0")){
                        //无合规数据
                        return "202";
                    }else{
                        //传递信息错误
                        return "203";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "200";
    }

    /**
     * 获得所有通过的年数
     * @param xml
     * @param 当前人所有有效的在线教育年限
     * @return
     */
    private List<String> resultData(String xml,List<String> yearList){//type 用于区分两个接口的类型
        try {
            List<String> list= new ArrayList<String>();
            Document doc = DocumentHelper.parseText(xml);
            //指向根节点
            Element root = doc.getRootElement();
            List<Element> elements = root.elements();
            for (Element element : elements) {
                String name = element.getName();
                if(name.equals("UserInfo")){
                    List<Element> e1s = element.elements();
                    String year = "";
                    for(Element e1 : e1s){
                        String name1 = e1.getName();
                        if(name1.equals("isPass")){
                            String text = e1.getText();
                            if(text.equals("1")&&year!=""){
                                if(!yearList.contains(year)){
                                    list.add(year);
                                }
                            }
                        }else{
                            year = e1.getText();
                        }
                    }
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }



    //跳转分类, 301 跳转初始登记,302跳转更变执业单位 303跳转更变专业 304跳转继续登记, 300 跳转注销登记
    //选择申请类型提交
    @ResponseBody
    @RequestMapping(value = "submitCheck")
    public String submitCheck(String[] declareType){
        //11.9 根据公告日期判断是否可以继续登记

        //需要进行判断. 1.不能为空.
        if(declareType==null&&declareType.equals("")){
            //请填选类型
            return "203";
        }else{
            //查询是否有重复
            //
            User user = UserUtils.getUser();
            EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(user);

           String result = isType4(declareType,enterpriseWorkers);
            if(result!=null&&result=="205"){
                return "205";
            }
           /* String check = isCheck(enterpriseWorkers, declareType);
            if(check!=null&&check.equals("201")){
                return "205";
            }*/

            if(enterpriseWorkers!=null){
                //10.10/65: 超过70岁不允许初始登记，从身份证上取年龄做判断添加需求
                if(!ifOld(declareType)){//如果满足其中一项,return false, 因此进入
                    if(!tooOld(enterpriseWorkers)){//超过70岁,不可变更专业,不可继续登记,不可初始登记
                        return "204";
                    }
                }
            String id = enterpriseWorkers.getId();
            int a = counselorService.findSame(id);
            if(a==0){
                //表示可选.进行保存
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                for(String d:declareType){
                    Counselor c = new Counselor();
                    c.setPersonId(id);
                    c.setDeclareType(d);
                    c.setBatchId(uuid);
                    c.setDeclareStatus("1");
                    c.setCompanyName(enterpriseWorkers.getCompanyName());
                    c.setCompanyId(enterpriseWorkers.getPid());
                    c.setRegisterMainSpecialty(enterpriseWorkers.getRegisterMainSpecialty());
                    c.setRegisterAuxiliarySpecialty(enterpriseWorkers.getRegisterAuxiliarySpecialty());
                    //10.24  增加officeName
                    String pid = enterpriseWorkers.getPid();
                    if(pid!=null&&!pid.equals("")){
                        User user1 = systemService.getUser(pid);
                        if(user1!=null&&!user1.equals("")){
                            c.setOfficeName(user1.getOffice().getName());
                        }
                    }
                    counselorService.save(c);
                    //获取c的id 给table创建数据
                    this.saveCounselorTable(c.getId(),d);
                    //把之前的所有的附件全部移动过来,只改变id与tableId
                    this.moveAttach(id,d,c.getId());
                    //创建
                    this.saveCheck(c.getId(),d);
                }
                return "200";
            }else{
                //表示不可选, 不可选择重复的
                return "202";
            }
            }else{
                return "203";
            }
        }
    }

    //撤回表单
    @RequestMapping(value = "recall")
    private String recall(String id){
        Counselor counselor = counselorService.get(id);
        unitMethod2(counselor,"1");
        return "redirect:" + adminPath + "/counselor/list";
    }

    //上报单位
    @RequestMapping(value = "submitUnit")
    @ResponseBody
    public String submitUnit(String id){
        //先查询,是否全部点击确认
        int result = counselorTableService.getSame(id);
        if(result>0){//有未确认的
            return "201";
        }
        Counselor counselor = counselorService.get(id);
        //上报单位的同时,需要把所有的jobKnowe进行修改为0 ,表示不可修改
        updateIsChange(counselor);

        if(counselor.getDeclareStatus().equals("4")){
            unitMethod1(counselor,"3");
        }else if(counselor.getDeclareStatus().equals("14")){
            unitMethod1(counselor,"12");
        }else if(counselor.getDeclareStatus().equals("22")){
            unitMethod1(counselor,"7");
        }else {
            unitMethod(counselor, "2");
        }
        //10.11 74:上报单位的按钮  如果同一批次内有多条申请，加一个消息提醒   还有XXXX没上报的单据请及时上报,否则将影响其他同批次登记事项。
        String message = addMessage(id);
        if(message!=null&&!message.equals("")){
           return message;
        }
        updateDeclareDate(id);
        return "200";
    }

    //上报单位的同时,需要把所有的jobKnowe进行修改为0 ,表示不可修改
    private void updateIsChange(Counselor counselor){
        String personId = counselor.getPersonId();
        //根据personId 查找到所有的jobKnow
        JobKnowledge jobKnowledge = new JobKnowledge();
        jobKnowledge.setPersonId(personId);
        List<JobKnowledge> list = jobKnowledgeService.findList(jobKnowledge);
      /*  for(JobKnowledge j : list){
            j.setIsChange("0");
            jobKnowledgeService.save(j);
        }*/
    }

    private String isType4(String [] types,EnterpriseWorkers enterpriseWorkers){
        if(types==null||types.equals("")){
            return "201";
        }
        for(String type : types){
            if(type.equals("4")){//继续登记不可
                if(enterpriseWorkers==null||enterpriseWorkers.equals("")){
                    return "205";
                }
                Date freezeDate = enterpriseWorkers.getAollowDate();//时间运算
                //如果为null 直接返回205
                if(freezeDate==null||freezeDate.equals("")){
                    return "205";
                }
                Date date = new Date();
                Calendar min = Calendar.getInstance();
                Calendar max = Calendar.getInstance();
                min.setTime(freezeDate);
                max.setTime(freezeDate);
                //min.add(Calendar.YEAR,2);
                min.add(Calendar.MONTH,31);
                //max.add(Calendar.YEAR,2);
                max.add(Calendar.MONTH,35);
                Date time = min.getTime();
               /* System.out.println("最小时间"+time);*/
                Date time1 = max.getTime();
                /*System.out.println("最大时间"+time1);*/
                if(date.getTime()<min.getTime().getTime()||date.getTime()>max.getTime().getTime()){
                     return "205";
                 }
                //判断时间 . 区间为 2年7个月 . 以及 2年10个月 .  31*30.5 = 930 + 1

            }
        }
        return "200";
    }

    //当前批次都提交后,修改全部update的时间
    private void updateDeclareDate(String id){
        Counselor counselor = counselorService.get(id);
        String batchId = counselor.getBatchId();
        //1查询出 此batchId里状态为1 的有几个,如果没有需要全部进行更新时间
        Map map = counselorService.findByBatchId1(batchId, "1");
        Integer num  = (Integer) map.get("num");
       List<Counselor> list = (List<Counselor>) map.get("list");
       if(num!=null&&num==0){//表示查询后没有状态为1的表单, 需要全部进行更新时间
           for(Counselor c : list ){
               counselorService.updateDeclareDate(c);
           }
       }
    }

    private String addMessage(String id){
        //先查询到批次id
        Counselor counselor = counselorService.get(id);
        String batchId = counselor.getBatchId();
        //根据批次id查询状态是1的
        Counselor counselor1 = new Counselor();
        counselor1.setBatchId(batchId);
        counselor1.setDeclareStatus("1");
        List<Counselor> list = counselorService.findList(counselor1);
        if(list!=null&&list.size()>0){//表示有未提交的
            StringBuffer sb = new StringBuffer();
            sb.append("");
            for(Counselor c : list){
                String type = c.getDeclareType();
                if(type.equals("2")){
                    sb.append("变更执业单位 ");
                }else if(type.equals("3")){
                    sb.append("变更专业 ");
                }else if(type.equals("4")){
                    sb.append("继续登记 ");
                }
            }
            sb.append("未上报,未上报的单据请及时上报,否则将影响其他同批次登记事项.");
            return sb.toString();
        }
        return null;
    }

    public void unitMethod1(Counselor counselor,String status){
        counselor.setDeclareStatus(status);
        counselor.setBatchStatus(status);
        counselorService.save(counselor);
    }

    public void unitMethod(Counselor counselor,String status){
        //declare_status   变更6，如果批次ID查出来的是多条，多条数据的declare_status都是6的时候，把batch_status改成6，如果是只有一条则两个状态都变更
        //1.同意上报.
        //1.查看是否为多条>,根据batch查询
        String batchId = counselor.getBatchId();
        Map map = counselorService.findByBatchId(batchId,status);//需要查询两个参数. 用map装, 1.list batchId的所有值,
        List<Counselor>list = (List<Counselor>) map.get("list");
        Integer num = (Integer) map.get("num");
        //1.先判断是否只有一条数据,如果只有一条数据直接进行添加
        if(list.size()==1){//只有一条数据
            counselor.setDeclareStatus(status);
            counselor.setBatchStatus(status);
            counselorService.save(counselor);
        }else{
            //分两种情况, 所有都!= 这个数,也就是只有本身是不等于的, 因此num==1
            if(num==1){//表示全部都得变,包括本身
                counselor.setDeclareStatus(status);
                counselor.setBatchStatus(status);
                counselorService.save(counselor);
                for(Counselor c:list){
                    c.setDeclareStatus(status);
                    c.setBatchStatus(status);
                    counselorService.save(c);
                }
            }else if(num==0){//还有一种可能是人员重复点击,把已经提交过的 再提交一遍,不进行任何操作
                return;
            }else{//只变本身这一条
                counselor.setDeclareStatus(status);
                counselorService.save(counselor);
            }
        }
    }

    //变一个batch  其余的batch都得变
    public void unitMethod2(Counselor counselor,String status){
        String batchId = counselor.getBatchId();
        Map map = counselorService.findByBatchId(batchId,status);//需要查询两个参数. 用map装, 1.list batchId的所有值,
        List<Counselor>list = (List<Counselor>) map.get("list");
        //全部把batchId 变了
        for(Counselor c : list){
            c.setBatchStatus(status);
            if(c.getId().equals(counselor.getId())){//当选到当前对象的时候,需要把decalareType也变了
                c.setDeclareStatus(status);
            }
            counselorService.save(c);
        }
    }
    ////////////////////////////////////////////////////////////////////////////方法区/////////////////////////////////////////////////////////


    //用于移动attachment.
    //应用场景. 当第一次申请初次登记后,给附件列表增加了附件. 而第二次初次登记 需要把第一次(第二次第三次)的附件拿取过来,制空id 以及修改tableId

    public void moveAttach(String personId,String type,String recordId){//需要的参数为 新申请的tableId
        //通过recordId 与 type 找出tableId 是什么.
        CounselorTable counselorTable1 = counselorTableService.getNew(recordId,"3");

        //1.根据type与personId 查询出往届所有的附件
        List<CounselorAttachment> attachList = counselorAttachmentService.findAllAttach(personId,type);
        for (CounselorAttachment c : attachList){
            c.setId(null);
            c.setCreateBy(null);
            c.setCreateDate(null);
            c.setUpdateBy(null);
            c.setUpdateDate(null);
            c.setTableId(counselorTable1.getId());
            counselorAttachmentService.save(c);
        }
    }


    //将personId 与 declareType 封装成一个map
    private Map<String,Object> createMap(String personId,String [] declareType){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("personId",personId);
        //遍历,如果出现了 2 3 4 其中一种. 就设置declareType 为 234
        for(String s : declareType){
            if(s.equals("2")||s.equals("3")||s.equals("4")){
                 String [] result = {"2","3","4"};
                map.put("declareType",result);
                return map;
            }
        }
        map.put("declareType",declareType);
        return map;
    }

    //传递user, 查询出他的enterprise
    private EnterpriseWorkers getEnterpriseWorkers(User user){
        //List<EnterpriseWorkers> list = enterpriseWorkersService.findList(enterpriseWorkers);
        EnterpriseWorkers enterpriseWorkers =  enterpriseWorkersService.getByNameNum(user.getName(),user.getCardNumber());
        if(enterpriseWorkers!=null&&!enterpriseWorkers.equals("")){
        enterpriseWorkers.setEmail(user.getEmail());
        }
        return enterpriseWorkers;
    }


    //创建table的的情况
    //1.基本情况 2.工作经历 3.相关附件 6.工程咨询
    //初始登记
    private String [] first ={"1","2","3"};
    //变更执业
    private String [] apply = {"1","2","3"};
    //变更专业
    private String [] specialty= {"1","2","3","6"};
    //继续登记
    private String [] contin = {"1","2","3"};
    //根据declareType 与 personRecordId 创建table

    //注销登记
    private String []cancel = {"1","3"};//相关附件

    private void saveCounselorTable(String personRecordId,String declareType){
        //分情况. 当 declareType为1 的时候,表示为初始登记 ,需要给type 增加 1 基本情况 , 2 工作经历 ,3 相关附件
        EnterpriseWorkers enterpriseWorkers = getEnterpriseWorkers(UserUtils.getUser());
        String isFirst = enterpriseWorkers.getIsFirst();
        if(declareType.equals("1")){
            saveCounselorTableMethod(first,personRecordId,isFirst);
        }else if(declareType.equals("2")){
            saveCounselorTableMethod(apply,personRecordId,isFirst);
        }else if(declareType.equals("3")){
            //需要进行计算,当执业满6年才可以增加业绩表单.
            int sum = sumYear(enterpriseWorkers);
            if(sum>=6){//大于六年  ,有6 业绩
                saveCounselorTableMethod(specialty,personRecordId,isFirst);
            }else{
                //小于6年, 无6 业绩
                saveCounselorTableMethod(apply,personRecordId,isFirst);
            }
        }else if(declareType.equals("4")){
            saveCounselorTableMethod(contin,personRecordId,isFirst);
        }else if(declareType.equals("0")){
            saveCounselorTableMethod(cancel,personRecordId,null);
        }
    }
    private int sumYear(EnterpriseWorkers enterpriseWorkers){
        //1.判断是否为空
        String num = enterpriseWorkers.getRegisterCertificateNum();
        //样式. 咨登20200500462. 截取2005字段.然后用现在的年份减去
        if(num==null||num.equals("")){
            return 0;
        }
        //1.获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        //2.获取数据时间
        //1.判断长度大于8
        if(num.length()<8){//小于8 直接return
            return 0;
        }
        String numString = num.substring(4,8);
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(numString);
        if( !isNum.matches() ){//判断是否为数字,如果不是.返回0
                return 0;
        }
        Integer integer = Integer.valueOf(numString);
        int sum = year-integer;
        return sum;
    }

    private void saveCounselorTableMethod(String []ss,String recordId,String isFirst){
        for(String s : ss){//给table增加数据
            CounselorTable counselorTable = new CounselorTable();
            counselorTable.setPersonRecordId(recordId);
            counselorTable.setType(s);
            if(s.equals("2")&&(isFirst!=null&&isFirst.equals("1"))){//存在情况, 在enterprise的字段 isFirst 为1 的时候,代表不可以点工作经历的已确定. 因此,在创建表单的时候就给他进行赋值已确定
                counselorTable.setStatus("1");
            }else{
                counselorTable.setStatus("0");
            }
            counselorTableService.save(counselorTable);
        }
    }

    //需要的很多字段都是要从字典表里查询的
    private Map showEnterpriseWorers(EnterpriseWorkers enterpriseWorkers){

        String email = UserUtils.getUser().getEmail();
        HashMap<String, Object> map = new HashMap<String, Object>();
        //需要进行判断,如果不是身份证,不需要进行划分
        String certificatesType = enterpriseWorkers.getCertificatesType();
        if(certificatesType.equals("1")){
        String certificatesNum = enterpriseWorkers.getCertificatesNum();
        String year = certificatesNum.substring(6, 10);
        String mounth = certificatesNum.substring(10, 12);
        String day = certificatesNum.substring(12,14);
        String date = year + "年" + mounth + "月" + day + "日";
        map.put("date",date);
        }
        String pid = enterpriseWorkers.getPid();
        if(pid!=null&&!pid.equals("")){
            User user = systemService.getUser(pid);
            if(user!=null&&!user.equals("")){
                map.put("companyName",user.getOriginalCompanyName());
            }
        }
        map.put("email",email);

        return map;
    }



    //因为字段都是合并的,封装一个方法进行保存
    //申请登记状态 替换的参数有8个
    // 1.company_name 执业单位名称  2.job_company_name 工作单位名称 3.backupnum 备案编号 4.backuptype 备案情况
    // 5.company_type 工作单位性质 6. iseducate 继续教育是否满足 7. register_main_specialty 主专业     8.register_auxiliary_specialty 辅专业
    //接收方式. 1.companyName   2.reviewCompany      3.registerCertificateNum        4.fileNo
    //          5.companyArea           6.mobile               7.registerMainSpecialty     8.registerAuxiliarySpecialty
    // id 使用title进行接收
    private void saveMethod(EnterpriseWorkers enterpriseWorkers,String tableId){
        //需要保存的数据分两部分, 1 保存enterpriseWorerks的 两个字段
        //                          2. 保存person_register 表的字段
        //1.
        EnterpriseWorkers e = enterpriseWorkersService.getById(enterpriseWorkers.getId());
        e.setId(enterpriseWorkers.getId());
        e.setGetyear(enterpriseWorkers.getGetyear());
        e.setProfessioncardNum(enterpriseWorkers.getProfessioncardNum());
        enterpriseWorkersService.save(e);
        //2. 保存personRegister表的字段
        //2.1 需要进行判断数据是否存在.
        //  id 使用title进行接收
        PersonRegister p = new PersonRegister();
        if(enterpriseWorkers.getTitle()!=null&&!enterpriseWorkers.getTitle().equals("")){
            //id 存在的时候, 需要进行setId
            p.setId(enterpriseWorkers.getTitle());
        }
        p.setPersonId(enterpriseWorkers.getId());
        p.setTableId(tableId);
        p.setCompanyName(enterpriseWorkers.getCompanyName());
        p.setJobCompanyName(enterpriseWorkers.getReviewCompany());
        p.setBackupNum(enterpriseWorkers.getRegisterCertificateNum());
        p.setBackupType(enterpriseWorkers.getFileNo());
        p.setCompanyType(enterpriseWorkers.getCompanyArea());
        //p.setIseducate(enterpriseWorkers.getMobile());
        p.setRegisterMainSpecialty(enterpriseWorkers.getRegisterMainSpecialty());
        p.setRegisterAuxiliarySpecialty(enterpriseWorkers.getRegisterAuxiliarySpecialty());
        p.setConfirmFive(enterpriseWorkers.getIsRegister());
        personRegisterService.save(p);
    }
    //更变需求, 一个表单中的 3个jsp都可以是多条
    private Map infoMap(String tableId){
        HashMap<String, Object> map = new HashMap<String, Object>();
       List<Educationtbl> educationtblList= educationtblService.getByTableId(tableId);
        List<SpecialtyTrain> specialtyTrainList= specialtyTrainService.getByTableId(tableId);
            for(SpecialtyTrain specialtyTrain:specialtyTrainList){
            //当起始时间与到期时间都不为空
            if(specialtyTrain.getStartTime()!=null&&!specialtyTrain.getStartTime().equals("")&&specialtyTrain.getEndTime()!=null&&!specialtyTrain.getEndTime().equals("")){
                //需要计算, 5个月为 5个月  15个月 为 1年3月
                Date startTime = specialtyTrain.getStartTime();
                Date endTime = specialtyTrain.getEndTime();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startTime);
                int imonth = calendar.get(Calendar.MONTH);
                int iyear = calendar.get(Calendar.YEAR);
                calendar.setTime(endTime);
                int jmonth = calendar.get(Calendar.MONTH);
                int jyear = calendar.get(Calendar.YEAR);
                int amonth= jmonth-imonth;
                int ayear= jyear - iyear;
                int i = ayear * 12 + amonth;
                if(i>0){
                    specialtyTrain.setStudyTime(i+"个月");
                }else{
                    specialtyTrain.setStudyTime("1个月");
                }
            }
        }
        List<TitleCertificate> titleCertificateList= titleCertificateService.getByTableId(tableId);

        map.put("educationtblList",educationtblList);
        map.put("specialtyTrainList",specialtyTrainList);
        map.put("titleCertificateList",titleCertificateList);
        return map;
    }
    //树子节点的内容,




    //需要给check进行分类
    //1.
    //初始登记--> 基本信息 -->个人信息 --> kind 1
    //private String[] s1 = {"1","2","3","5","6","7","8","9","10"};
    private String[] s11 = {"1","2","3","5","6","7","8","9","10"};//个人信息
    private String[] s12 = {"11"};//学历教育情况
    private String[] s13 ={"12"};//职称情况
    private String[] s14 = {"13"};//培训情况
    private String[] s15 = {"4"};//继续教育证明

    //2 变更执业单位
    //养老保险
    private String[] s21 = {"10"};//个人信息
    //拟登记的执业单位  劳动合同, 解聘证明
    private String[] s22 = {"15","11","14"};//执业情况

    //3.变更专业
    //毕业证书
    private String[] s31 = {"11"};//学历教育情况

    //职称证书
    private String[] s32 = {"12"}; //职称情况

    //培训证书
    private String[] s33 = {"13"};//培训情况

    //工程咨询业绩
    private String[] s34 = {"16","17","18"};//工程咨询业绩

    //承诺书
    private String[]s0 ={"19","20"};

    //在创建申请单的时候就进行插入数据
    public void saveCheck(String recordId, String declareType){
        Examine examine = null;
        if(declareType.equals("1")){
            //表示是初次登记
            examine = systemService.getName("初始登记");
        }else if(declareType.equals("2")){
            examine = systemService.getName("变更执业单位");
        }else if(declareType.equals("3")){
            examine = systemService.getName("变更专业");
        }else if(declareType.equals("4")){
            examine = systemService.getName("继续登记");
        }
        else if(declareType.equals("0")){
            return;
        }
        List<Examine>examines = systemService.findExamineByParentId(examine.getId());
        //遍历所得到的examine数据
        for(Examine e :examines){
            //kindOf 进行匹配选项框
            String kindOf = e.getKindOf();
            //不为空的时候进行下面操作
            if(kindOf!=null&&!kindOf.equals("")){
                saveCheckMethod(kindOf,recordId,e.getId());
            }
        }
    }

    public void saveCheckMethod(String kindOf,String recordId,String examineId){
        if(kindOf.equals("11")){
            saveCheckMethod1(s11,recordId,examineId);
        }else if(kindOf.equals("12")){
            saveCheckMethod1(s12,recordId,examineId);
        }else if(kindOf.equals("13")){
            saveCheckMethod1(s13,recordId,examineId);
        }else if(kindOf.equals("14")){
            saveCheckMethod1(s14,recordId,examineId);
        }else if(kindOf.equals("15")){
            saveCheckMethod1(s15,recordId,examineId);
        }else if(kindOf.equals("21")){
            saveCheckMethod1(s21,recordId,examineId);
        }else if(kindOf.equals("22")){
            saveCheckMethod1(s22,recordId,examineId);
        }else if(kindOf.equals("31")){
            saveCheckMethod1(s31,recordId,examineId);
        }else if(kindOf.equals("32")){
            saveCheckMethod1(s32,recordId,examineId);
        }else if(kindOf.equals("33")){
            saveCheckMethod1(s33,recordId,examineId);
        }else if(kindOf.equals("34")){
            saveCheckMethod1(s34,recordId,examineId);
        }else if(kindOf.equals("0")){
            saveCheckMethod1(s0,recordId,examineId);
        }
    }

    public void saveCheckMethod1(String[] ss ,String recordId,String examineId){
        for(String s : ss){
            PersonCompliance personCompliance = new PersonCompliance();
            personCompliance.setType(s);
            personCompliance.setExamineId(examineId);
            personCompliance.setRecordId(recordId);
            personComplianceService.save(personCompliance);
        }
    }

    public void saveChangeItem(EnterpriseWorkers enterpriseWorkers,String recordId){
        //1.主专业
        String main = enterpriseWorkers.getRegisterMainSpecialty();
        ChangeItem item = changeItemService.getByRecordId(recordId,"4");
        if(main!=null&&!main.equals("")){
            //1.查找是否有这个对象. 每一个recordid 以及 changeType 只存在一个对象
            //如果没有查询到,需要增加新的
            if(item!=null&&!item.equals("")){//查询到了赋值并且储存
                item.setNewValue(main);
            }else{
                item = new ChangeItem();
                item.setNewValue(main);
                item.setChangeType("4");
                item.setRecordId(recordId);
            }
            changeItemService.save(item);
        }else{//如果是空的,需要把这条数据删除
            if(item!=null&&!item.equals("")){
                changeItemService.delete(item);
            }
        }
        //2 辅专业
        String assist = enterpriseWorkers.getRegisterAuxiliarySpecialty();
        ChangeItem item1 = changeItemService.getByRecordId(recordId,"5");
        if(assist!=null&&!assist.equals("")){
            //1.查找是否有这个对象. 每一个recordid 以及 changeType 只存在一个对象
            //如果没有查询到,需要增加新的
            if(item1!=null&&!item1.equals("")){//查询到了赋值并且储存
                item1.setNewValue(assist);
            }else{
                item1 = new ChangeItem();
                item1.setNewValue(assist);
                item1.setChangeType("5");
                item1.setRecordId(recordId);
            }
            changeItemService.save(item1);
        }else{
            if(item1!=null&&!item1.equals("")){
                changeItemService.delete(item1);
            }
        }
    }


    //todo 删除业绩的时候,需要把attachment的数据也删除.
    private List type7(String tableId,String recordId){
        //传递recordId. 根据

        ArrayList<HashMap> lists = new ArrayList<HashMap>();
        //1,查询填写的业绩
        CounselorTable table = counselorTableService.getNew(recordId, "6");
        ProjectInvestment project = new ProjectInvestment();
        project.setTableId(table.getId());
        List<ProjectInvestment> projectList = projectInvestmentService.findList(project);
        //2.查询出所有的附件
        //需要recordId 以及type
        CounselorAttachment attach = new CounselorAttachment();
        attach.setTableId(tableId);
        attach.setType("19");//业绩扫描件
        List<CounselorAttachment> attachList = counselorAttachmentService.findList(attach);
        if(projectList!=null&&projectList.size()>0){
            for(ProjectInvestment p : projectList){
                HashMap<String, Object> map = new HashMap<String, Object>();
                ArrayList<CounselorAttachment>caList  = new ArrayList<CounselorAttachment>();
                map.put("name",p.getProjectName());
                map.put("remarks",p.getId());
                map.put("tableType","3");
                map.put("personId",p.getPersonId());
                map.put("tableId",tableId);
                for(CounselorAttachment a : attachList){
                    if(a.getRemarks()!=null&&a.getRemarks().equals(p.getId())){//附件的的remarks 与 业绩的id 进行关联
                        caList.add(a);
                    }
                }
                map.put("list",caList);
                lists.add(map);
            }
        }
        return lists;
    }

    //10.10/65超过70岁不允许初始登记，从身份证上取年龄做判断
    private boolean tooOld(EnterpriseWorkers enterpriseWorkers){
        //1.判断是否为身份证
        String idType = enterpriseWorkers.getCertificatesType();
        if(idType!=null&&idType.equals("1")){//为1, 身份证
            String yearNum = enterpriseWorkers.getCertificatesNum();
            String substring = yearNum.substring(6, 10);
            Integer year = Integer.valueOf(substring);//出生年
            Calendar c = Calendar.getInstance();
            int newYear = c.get(Calendar.YEAR);
            int sum = newYear-year;
            if(sum>70){//表示年龄大于70,返回false

                return false;
            }else if(sum==70){
                //需要精确到天
                //1.取到现在的月份
                int month = c.get(Calendar.MONTH)+1;
                //2.取到身份证的月份
                String subMotnth = yearNum.substring(10, 12);
                Integer newMonth = Integer.valueOf(subMotnth);
                int sumM = month-newMonth;
                //如果得到的月份大于0
                if(sumM>0){
                    return  false;
                }else if(sumM<0){
                    return true;
                }else if(sumM==0){
                    //需要根据日再进行判断
                    String subDay=yearNum.substring(12,14);
                    Integer newDay = Integer.valueOf(subDay);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int sumD = day-newDay;
                    if(sumD>0){
                        return false;
                    }else{
                        return true;
                    }
                }
            }else if(sum<70){//小于70
                return true;
            }
        }
        return true;
    }
    //判断是否进行判断满足70岁验证, 不可初始登记,变更专业,继续登记
    private Boolean ifOld(String [] declareType){
        for(String s : declareType){
            if(s.equals("1")||s.equals("3")||s.equals("4")){
                    return false;
            }
        }
        return true;
    }


    //功能.同批次有多条的时候,需要进行判断, 如, 变更执业单位,-->变更专业---->,继续登记.  只有第一个提交后,后面的才可以查看
    @ResponseBody
    @RequestMapping(value = "isSubmit")
    private String isSubmit(String id,String type){
        //区别1. 如果取出来的是变更执业单位,直接return .可以进行编辑
        if(type.equals("2")){
            return "200";
        }
        //1.查询出batchId
        Counselor counselor = counselorService.get(id);
        String batchId = counselor.getBatchId();
        //2.查询是否为多条数据
        Counselor resultCounselor = new Counselor();
        resultCounselor.setBatchId(batchId);
        List<Counselor> list = counselorService.findList(resultCounselor);
        //3. 判断list 是否为大于1
        if(list!=null&&list.size()>1){
            StringBuffer sb = new StringBuffer();
            //大于一表示有多条
            String status2 = null;
            String status3 = null;
            String status4 = null;
            //1. 获取多条declare的状态, 根据type 2.变更执业单位. 3,变更专业, 4 继续登记
            for(Counselor c : list){//2
                String declareType = c.getDeclareType();
                if(declareType.equals("2")){
                    status2 = c.getDeclareStatus();
                }else if(declareType.equals("3")){
                    status3 = c.getDeclareStatus();
                }else if(declareType.equals("4")){
                    status4 = c.getDeclareStatus();
                }
            }
            //1.如果点击的是变更执业单位
            if(type.equals("3")){
                //1.先查询status2 存在不存在> ,如果存在并且不为1 .就return
                if(status2==null||!status2.equals("1")){
                    //1.如果为空就return 200;
                    //2.如果不等于1就 return
                    return "200";
                }else{
                    sb.append("变更执业单位未上报,不可编辑变更专业.");
                    //return "201";
                    return sb.toString();
                }
            }else if(type.equals("4")){//点击的是继续登记
                //1.查询status3 是否存在.如果存在. 就判断是否
                if(status3!=null){
                    if(!status3.equals("1")){
                        //不为1
                        return "200";
                    }else{
                        sb.append("变更专业未上报,不可编辑继续登记.");
                        //return "201";
                        return sb.toString();
                    }
                }else {//表示statu3 为空,那2肯定是不为空的
                    if(!status2.equals("1")){
                        return "200";
                    }else {
                        sb.append("变更执业单位未上报,不可编辑继续登记");
                        //return "201";
                        return sb.toString();
                    }
                }
            }
        }else{
            return "200";//等于1,直接返回200
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

    //2.两种情况放过.
    //     1.当companyName 与original相同的时候放过
    //      2. 当companyName不同,但是type 有2的情况下放过
    private String isCheck(EnterpriseWorkers enterpriseWorkers,String []types){
        //1. 查询是否需要变更执业单位的状态
        String companyName = enterpriseWorkers.getCompanyName();
        String pid = enterpriseWorkers.getPid();
        if(companyName!=null&&!companyName.equals("")&&pid!=null&&!pid.equals("")){
            //再查询pid关联的user
            User user = systemService.getUser(pid);
            if(user!=null&&!user.equals("")){
                String originalCompanyName = user.getOriginalCompanyName();
                //判断两个是否相同
                if(originalCompanyName!=null&&!originalCompanyName.equals("")&&originalCompanyName.equals(companyName)){//不同需要进入下一步,必须带上执业单位申请单,相同直接return
                    return "200";
                }
            }
        }
        //出现original 与 companyName 不一致, 进入次步骤,需要进行判断是否存在type 为2 的表单
        for(String type : types){
            if(type!=null&&!type.equals("")&&type.equals("2")){
                return "200";
            }
        }
        return "201";
    }

    @ResponseBody
    @RequestMapping("findBatchId")
    private String findBatchId(String id ){
        String test = this.counselorService.findBatchIdById(id);
        String[] split = test.split(",");
        String newText = "";
        for(int i = 0; i <split.length;i++){
            if(i <split.length-1){
                newText=newText+DictUtils.getDictLabel(split[i],"counselor_type","")+",";

            }else{
                newText+= DictUtils.getDictLabel(split[i],"counselor_type","");
            }
        }
        return newText;
    }

    @RequestMapping("deleteByBatchId")
    public String deleteByBatchId(String batchId){
        this.counselorService.deleteByBatchId(batchId);
        return "redirect:" + adminPath + "/counselor/list";
    }
}
