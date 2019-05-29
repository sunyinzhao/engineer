package com.thinkgem.jeesite.modules.counselor.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.SendMessge;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.*;
import com.thinkgem.jeesite.modules.counselor.service.*;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseBase;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseBaseService;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;
import com.thinkgem.jeesite.modules.sys.entity.Examine;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


//咨询师评价树的生成
/*@Controller
@RequestMapping(value = "${adminPath}/counselor/examine")*/
public class CounselorExamineController1 extends BaseController {

	@Autowired
	private CounselorExamineRejectService counselorExamineRejectService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private CounselorService counselorService;

	@Autowired
    private EnterpriseWorkersService enterpriseWorkersService;

	@Autowired
    private CounselorTableService counselorTableService;

	@Autowired
    private EducationtblService educationtblService;

	@Autowired
    private TitleCertificateService titleCertificateService;

	@Autowired
    private SpecialtyTrainService specialtyTrainService;

	@Autowired
    private PersonRegisterService personRegisterService;

	@Autowired
    private JobKnowledgeService jobKnowledgeService;

	@Autowired
    private PersonComplianceService personComplianceService;

	@Autowired
    private AccountantStatusService accountantStatusService;

	@Autowired
    private CounselorAttachmentService counselorAttachmentService;

	@Autowired
    private PersonExpertService personExpertService;

	@Autowired
    private ProjectInvestmentService projectInvestmentService;

	@Autowired
    private ChangeItemService changeItemService;
	
	@Autowired
	private AddCheckService addCheckService;

	@Autowired
    private EnterpriseBaseService enterpriseBaseService;
	@Autowired
	private DictService dictservice;

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> newtreeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String isShowHide, HttpServletResponse response,String recordId) {
        Counselor counselor = counselorService.get(recordId);
        String declareType = counselor.getDeclareType();
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
        }else if(declareType.equals("0")){
            examine = systemService.getName("注销登记");
        }
        //根据type 查询不同的examine

		List<Map<String, Object>> mapList = Lists.newArrayList();

		//List<Examine> sourcelist = systemService.findAllExamine();
		List<Examine> sourcelist = systemService.findAllNewExamine(null);

		List<Examine> list = new ArrayList<Examine>();
		list.add(examine);
		Examine.sortList(list, sourcelist, examine.getId(), true);

		//11.20 查询是否有咨询业绩这个表单
        CounselorTable table = counselorTableService.getNew(recordId, "6");

        for (int i = 0; i < list.size(); i++) {
			Examine e = list.get(i);
            //11.20 变更专业的时候, 如果没有业绩咨询表单, 就不产生业绩咨询这个节点
            if(table==null||table.equals("")){
                //没有业绩的时候进行判断
                if(e.getName().equals("工程咨询业绩")){
                    //查到这个对象, 进行跳过
                    continue;
                }
            }
			if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
				if (isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")) {
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				// map.put("realValue",e.getRealValue());
				mapList.add(map);
			}
		}
		return mapList;
	}


	@ResponseBody
	@RequestMapping(value = "ajaxSaveReject")
	public Map<String,Object> ajaxSaveReject(CounselorExamineReject counselorExamineReject){
		if (counselorExamineReject.getRejectList()!=null&&counselorExamineReject.getRejectList().size() > 0) {
			for (CounselorExamineReject c : counselorExamineReject.getRejectList()) {
				if(c!=null&&!c.equals("")) {
					c.setExamineId(counselorExamineReject.getExamineId());
					c.setPersonRecordId(counselorExamineReject.getPersonRecordId());
					counselorExamineRejectService.save(c);
				}
			}
			int size = counselorExamineReject.getRejectList().size();
			String id = counselorExamineReject.getRejectList().get(size-1).getId();
//			System.out.println(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("index",size-1);
			map.put("id",id);
			map.put("delFlag","0");
			return map;
		}
		return null;
	}

	@RequestMapping(value = "tree")
	public String examine(String recordId,String type,Model model,String flag){
		if("3".equals(type) || "4".equals(type)){
			PersonExpert personExpert = new PersonExpert();
			personExpert.setRecordId(recordId);
			personExpert.setExamineType(type);
			List<PersonExpert> list = personExpertService.findList(personExpert);
			if(list!=null&&list.size()>0){
				
			}else{
				addCheckService.addCheck(recordId, type);
			}
		}
		//recorid  用于传递 此表单id
		//type 用于区分不同单位看到的按钮不一样
		model.addAttribute("recordId",recordId);
		model.addAttribute("type",type);
		model.addAttribute("flag",flag);
		return "modules/counselor/counselorTreeIndex";
	}
	

    @RequestMapping(value = "openFail")
    public String openFail(PersonExpert personExpert,Model model){
        String recordId = personExpert.getRecordId();
        Counselor counselor = counselorService.get(recordId);
        model.addAttribute("fResult",counselor.getFirstZdeclareResult());
        model.addAttribute("sResult",counselor.getSecondZdeclareResult());

        String examineType = personExpert.getExamineType();
        if(examineType!=null&&(examineType.equals("5")||examineType.equals("6"))){//如果传递进来的参数是5,同样也改为4,查询4的数据
            examineType = "4";
        }
        //普通的选项与 学历等选项进行区分
        String [] types = {"35","36","37","38","39","40"};
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("items","1");
        map.put("recordId",personExpert.getRecordId());
        map.put("examineType",examineType);
        map.put("types",types);
        List<PersonExpert> list = personExpertService.findFailList(map);

        if(list.size()==0||list==null){
            model.addAttribute("message","没有不真实或不符合的项");
        }else{
            model.addAttribute("list",list);
        }
        //1.获取tableId
        CounselorTable counselorTable = new CounselorTable();
        counselorTable.setPersonRecordId(recordId);
        counselorTable.setStatus("1");//因为所有的这些信息都是关联基本信息这个tableId里, 状态为1的是tableId
        String tableId = counselorTableService.findList(counselorTable).get(0).getId();
        //学历的.
        //1.两张表联查,如果查出来的的长度为0 ,那么需要 再查一次expert表
        String [] type1= {"35","36"};
        String [] type2= {"37","38"};
        String [] type3= {"39","40"};
        map.put("types",type1);//通过HashMap的特定,key相同会进行覆盖
       List<PersonExpert>educationList  =  personExpertService.findOtherList(map);
       model.addAttribute("educationtblList",educationList);
        //因为学历,培训,职称 都可能有多个,因此需要单独拿出来进行排序
        map.put("types",type2);//通过HashMap的特定,key相同会进行覆盖
        List<PersonExpert>titleList  =  personExpertService.findOtherList(map);
        model.addAttribute("titleList",titleList);
        map.put("types",type3);//通过HashMap的特定,key相同会进行覆盖
        List<PersonExpert>specialtyList  =  personExpertService.findOtherList(map);
        model.addAttribute("specialtyList",specialtyList);
        return "modules/counselor/failIndex";
    }




	@RequestMapping(value = "jump")
	public String jump(String id,String recordId,Model model,String type,String flag){
        Counselor counselor = counselorService.get(recordId);
        model.addAttribute("counselor",counselor);

        model.addAttribute("user",UserUtils.getUser());
        //flag 用于控制确定按钮. flag为1的时候,表示为查看, 确定按钮不显示
	    model.addAttribute("flag",flag);
	    //一:展示全局需要的内容,
        HashMap<String, Object> info = getInfo(recordId,type);
        model.addAttribute("infoMap",info);
        Examine examine = systemService.getExamine(id);
        model.addAttribute("examineId",id);
        model.addAttribute("recordId",recordId);
        String kind = examine.getKind();//用于判断展示的文字内容
        model.addAttribute("kind",kind);
        String result = examine.getResult();//用于判断是否为最低级,不为最低级展示图片
        String examineType = examine.getType();//用于区别展示的图片集合, 此type的名字有误差,应该是 imageType
        model.addAttribute("resultType",examineType);
        //测试,此type 用于区别终审以及合规审查
        //todo
        //type = "2";

        model.addAttribute("examineType",type);
        //

        if(result!=null&&result.equals("1")){
            return "modules/counselor/counselorExamineIndex";
        }
        Map all = all(recordId, id, kind,examineType,type);
	    //1.查询文字
            /*  * 1.个人信息  enterpriseWorekr
             * 2.学历教育情况 educationtbl
             * 3.职称情况    title_certificate
             * 4.培训情况    specialty_train
             * 5.申请登记情况  person_register
             * 6.工作经历    job_knowledge // 工作经验需要的是List
             * 7.执业情况    accountant_status*/
            if(kind!=null&&!kind.equals("")){
        if(kind.equals("1")){//查询个人信息
            EnterpriseWorkers enterpriseWorkers = (EnterpriseWorkers) all.get("object");
            Map map = null;
            //map.addDate 需要根据证件类型,才能进行切割
            if(enterpriseWorkers.getCertificatesType()!=null&&enterpriseWorkers.getCertificatesType().equals("1")){
                map = addDate(enterpriseWorkers.getCertificatesNum());
            }

            model.addAttribute("enterpriseWorkers",enterpriseWorkers);
            model.addAttribute("map",map);
        }else if(kind.equals("2")){//学历
            Map map = treeContent1(recordId, new Educationtbl());
            List<Educationtbl> educationtblList = (List<Educationtbl>) map.get("educationtblList");
            model.addAttribute("educationtblList",educationtblList);
        }else if(kind.equals("3")){//职称情况
            Map map = treeContent1(recordId, new TitleCertificate());
            List<TitleCertificate> titleCertificateList = (List<TitleCertificate>) map.get("titleCertificateList");
            model.addAttribute("titleCertificateList",titleCertificateList);
        }else if(kind.equals("4")){//培训情况
            Map map = treeContent1(recordId, new SpecialtyTrain());
            List<SpecialtyTrain> specialtyTrainList = (List<SpecialtyTrain>) map.get("specialtyTrainList");
            for(SpecialtyTrain specialtyTrain:specialtyTrainList) {
                if (specialtyTrain != null && !specialtyTrain.equals("")) {
                    if (specialtyTrain.getStartTime() != null && !specialtyTrain.getStartTime().equals("") && specialtyTrain.getEndTime() != null && !specialtyTrain.getEndTime().equals("")) {
                        Integer i = getStadyTime(specialtyTrain);
                        if(i!=null&&i>0){
                            specialtyTrain.setStudyTime(i+ "个月");
                        }
                    }
                }
            }
            model.addAttribute("specialtyTrainList",specialtyTrainList);
        }else if(kind.equals("5")){//申请登记情况
            Map map = treeContent1(recordId, new PersonRegister());
            Object object = map.get("object");
            if(object==null||object.equals("")){
                object = new PersonRegister();
            }
            model.addAttribute("personRegister",object);
        }else if(kind.equals("6")){//工作经历
            Map map = treeContent1(recordId, new JobKnowledge());
            List<JobKnowledge> obj = (List<JobKnowledge>) map.get("jobKnowledgeList");
            if(obj==null||obj.size()==0||obj.equals("")){
                obj = new ArrayList<JobKnowledge>();
            }
            model.addAttribute("jobKnowledgeList",obj);
        }else if(kind.equals("7")){//执业情况
            Map map = treeContent1(recordId, new AccountantStatus());
            Object object = map.get("object");
            if(object==null||object.equals("")){
                object = new AccountantStatus();
            }
            model.addAttribute("accountantStatus",object);
        }else if(kind.equals("8")){//工程咨询业绩
            Map map = treeContent1(recordId, new ProjectInvestment());
            List<JobKnowledge> obj = (List<JobKnowledge>) map.get("projectList");
            if(obj==null||obj.size()==0||obj.equals("")){
                obj = new ArrayList<JobKnowledge>();
            }
            model.addAttribute("projectList",obj);
        }
            }
        //2.查询图片
        //图片需要进行分类, 只展示右侧的使用 imageList 放入model , 全屏的使用 imageList1 表示,
            if(examineType!=null&&!examineType.equals("")){
                //增加图片进行区分,当有kind的时候,表示文字占左边,图片占右半部分div , 以imageList1表示.
                //无kind的时候,imageList 占全屏, 以imageList表示
                if(examineType.equals("37")){//图片进行区分,当点击的是工程业绩的时候,不展示图片,

                }else if(kind!=null&&!kind.equals("")){
                    model.addAttribute("imageList1",all.get("imageList"));
//                    System.out.println(all.get("imageList"));
                }else {
                    model.addAttribute("imageList",all.get("imageList"));
                }
           }
            model.addAttribute("resultMap",all.get("resultMap"));

        //判断根据,  使用kind 是否存在进行判断, 如果存在有值 就是用imageList , 如果没有 使用imageList1 表示

        //3.查询选项
        if(type.equals("2")){
             model.addAttribute("checkList",all.get("check"));
            model.addAttribute("personCompliance",new PersonCompliance());
        }else if(type.equals("3")||type.equals("4")||type.equals("5")||type.equals("6")){
            model.addAttribute("checkLists",all.get("check"));
            PersonExpert personExpert = new PersonExpert();
            personExpert.setExamineType(type);
            model.addAttribute("personExpert",personExpert);
        }
        //4.查询结论
        model.addAttribute("rejectList",all.get("rejectList"));

        model.addAttribute("counselorExamineReject",new CounselorExamineReject(recordId,id));
        model.addAttribute("usermodel",UserUtils.getUser().getUserModel());
        model.addAttribute("userofficeid",UserUtils.getUser().getOffice().getId());
		//id 为examineid , type 用于区分 按钮类型, recordId 此表单的id ,
        //todo
        //
        String expertModel = "0";//用来标记专家审核的是主专业还是辅专业,为0的时候不区分主辅专业 add by gaoyongjian 20181201
        if(counselor.getDeclareType().equals("1")|| (counselor.getDeclareType().equals("3") ))
        {
            if (UserUtils.getUser().getId().equals(counselor.getFirstZexpertId())) 
            {
            	expertModel="1";//主专业专家
            }
            if (UserUtils.getUser().getId().equals(counselor.getSecondZexpertId())) 
            {
            	expertModel="2";//辅专业专家
            }
            if (UserUtils.getUser().getId().equals(counselor.getSecondZexpertId()) && UserUtils.getUser().getId().equals(counselor.getFirstZexpertId())) 
            {
            	expertModel="3";//同时是主辅专家
            }
        }
        model.addAttribute("expertModel",expertModel);
        	
		return "modules/counselor/counselorExamineForm";
		//测试复选框
        //return "modules/counselor/counselorExamineTest";
	}

	//用于获取页面上方的数据
    private HashMap<String,Object> getInfo(String recordId,String type) {//type 区分预审终审
        HashMap<String, Object> map = new HashMap<String, Object>();
	   /* 1.名字      name
        2.所属单位      unit
        3.申请类型      type
        4.申请日期      date //存在疑问, 1.增加申请单时间, 2.修改申请单时间. 3 提交申请单时间
        5.咨询师状态     status
        6.所属地方协会
        7.主专业辅专业
        */
       //1.查询出表单
        Counselor counselor = counselorService.get(recordId);
        //名字可以从enterprise表里获取
        String personId = counselor.getPersonId();
        EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.get(personId);
        String name = enterpriseWorkers.getName();
        map.put("name",name);
        String type1 = counselor.getDeclareType();
        map.put("type",type1);
        //String unit = enterpriseWorkers.getCompanyName();

        Date date = counselor.getUpdateDate();
        map.put("date",date);

        String phone = enterpriseWorkers.getPhone();
        map.put("phone",phone);
        //officeName 根据pid进行查询
        String companyName = enterpriseWorkers.getCompanyName();
        map.put("companyName",companyName);
        String status = enterpriseWorkers.getIsValid();
        map.put("status",status);
        List<ChangeItem> itemList = addChangeItem(type, counselor);
        //查询主辅专业,1.declateTYpe状态,2.
        //1.先判断他的主辅专业
        map.put("itemList",itemList);
        String pid = enterpriseWorkers.getPid();
        if(pid!=null){
            //10.16 加上联系方式,联系人
            //
            User user = systemService.getUser(pid);

           EnterpriseBase enterpriseBase = enterpriseBaseService.getbyUserId(pid);
            String contactsZx = enterpriseBase.getContactsZy();//执业联系人
            String contactPhone = enterpriseBase.getContactsZyPhone();//执业联系电话
            map.put("companyContact",contactsZx);
            map.put("phone",contactPhone);


            String officeName = user.getOffice().getName();
        map.put("officeName",officeName);
        //增加一个调入现执业单位的时间
            JobKnowledge last = jobKnowledgeService.findLast(personId);
            if(last!=null&&!last.equals("")){
            String startDate = last.getStartDate();
            map.put("startDate",startDate);
            }
        }
        if(counselor.getDeclareType().equals("2")){
            ChangeItem item = changeItemService.getByRecordId(recordId, "1");
            if(item!=null&&!item.equals("")){
                map.put("unit",item.getNewValue());
            }
        }else{
            map.put("unit",enterpriseWorkers.getCompanyName());
        }
        map.put("accountantType",counselor.getDeclareType());
        //查找到的原执业单位与现执业单位
        if(counselor.getDeclareType().equals("2")){
            ChangeItem item = changeItemService.getByRecordId(recordId, "1");
            if(item!=null&&!item.equals("")){
                map.put("oldCompanyName",item.getOldValue());
                map.put("newCompanyName",item.getNewValue());

            }
        }else if(counselor.getDeclareType().equals("3")){
            //得到新主
            ChangeItem mainItem = changeItemService.getByRecordId(recordId, "2");
            if(mainItem!=null&&!mainItem.equals("")){
                String mainNewValue = mainItem.getNewValue();
                map.put("mainNewValue",mainNewValue);
            }
            ChangeItem fItem = changeItemService.getByRecordId(recordId, "3");
            if(fItem!=null&&!fItem.equals("")){
                String fNewValue = fItem.getNewValue();
                map.put("fNewValue",fNewValue);
            }
        }
        return map;
    }


    private List<ChangeItem> addChangeItem(String type,Counselor counselor){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("recordId",counselor.getId());
        User user = UserUtils.getUser();
        String id = user.getId();
        String declareType = counselor.getDeclareType();
        //1.判断变更专业还是初始登记
        if(declareType.equals("1")){//初始登记
        //2.判断是不是终审或者预审.
        if(type.equals("3")){//预审
            String cfirstId = counselor.getFirstCexpertId();
            String cSecondId = counselor.getSecondCexpertId();
            if(cfirstId!=null&&cfirstId.equals(id)){//主专业
                map.put("changeType",new String []{"4"});
            }else if(cSecondId!=null&&cSecondId.equals(id)){
                map.put("changeType",new String []{"5"});
            }else{
                map.put("changeType",new String []{"4","5"});
            }
        }else if(type.equals("4")){//终审
            String zfirstId = counselor.getFirstZexpertId();
            String zSecondId = counselor.getSecondZexpertId();
            if(zfirstId!=null&&zfirstId.equals(id)){//主专业
                map.put("changeType",new String []{"4"});
            }else if(zSecondId!=null&&zSecondId.equals(id)){
                map.put("changeType",new String []{"5"});
            }else{
                map.put("changeType",new String []{"4","5"});
            }
        }else if(type.equals("5") || type.equals("6")){//复议
            map.put("changeType",new String []{"4","5"});
        }else{
            List<ChangeItem> itemList = changeItemService.findItemList(map);
            return itemList;
        }
        }else if(declareType.equals("3")){//变更专业
            if(type.equals("3")){//预审
                String cfirstId = counselor.getFirstCexpertId();
                String cSecondId = counselor.getSecondCexpertId();
                if(cfirstId!=null&&cfirstId.equals(id)){//主专业
                    map.put("changeType",new String []{"2"});
                }else if(cSecondId!=null&&cSecondId.equals(id)){
                    map.put("changeType",new String []{"3"});
                }else{
                    map.put("changeType",new String []{"2","3"});
                }
            }else if(type.equals("4")){//终审
                String zfirstId = counselor.getFirstZexpertId();
                String zSecondId = counselor.getSecondZexpertId();
                if(zfirstId!=null&&zfirstId.equals(id)){//主专业
                    map.put("changeType",new String []{"2"});
                }else if(zSecondId!=null&&zSecondId.equals(id)){
                    map.put("changeType",new String []{"3"});
                }else{
                    map.put("changeType",new String []{"2","3"});
                }
            }else if(type.equals("5") || type.equals("6")){//复议
                map.put("changeType",new String []{"2","3"});
            }else{
                List<ChangeItem> itemList = changeItemService.findItemList(map);
                return itemList;
            }
        }else{
            //剩下的全部带出
            List<ChangeItem> itemList = changeItemService.findItemList(map);
            return itemList;

        }
       List<ChangeItem>list =  changeItemService.findItemList(map);
	        return list;
    }


    private Map addDate(String date){
        String year = date.substring(6, 10);
        String mounth = date.substring(10, 12);
        String day = date.substring(12,14);
        date = year + "年" + mounth + "月" + day + "日";
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("date",date);
        return map;
    }

    private Map treeContent(String recordId,Object object){
        HashMap<String, Object> map = new HashMap<String, Object>();
        CounselorTable ct = new CounselorTable();
        //type是 基本情况的type , 1.是基本情况 2.工作经历 3.相关附件 6.工程咨询
        ct.setType("1");
        ct.setPersonRecordId(recordId);
        Counselor counselor = counselorService.get(recordId);
        String personId = counselor.getPersonId();
        CounselorTable counselorTable = counselorTableService.findList(ct).get(0);
        String tableId = counselorTable.getId();
        Object objectResult=null;
        if(object.getClass()==Educationtbl.class){
            List<Educationtbl> educationtblList = educationtblService.getByTableId(tableId);
            map.put("educationtblList",educationtblList);
        }else if(object.getClass()==TitleCertificate.class){
            List<TitleCertificate> titleCertificateList = titleCertificateService.getByTableId(tableId);
            //11.13 需要把工作单位进行转义
            if(titleCertificateList!=null&&titleCertificateList.size()>0){
                for(TitleCertificate t : titleCertificateList){
                    String employer = t.getGetEmployer();
                    if(employer!=null&&!employer.equals("")){
                    String temp = StringEscapeUtils.unescapeHtml4(employer);
                    t.setGetEmployer(temp);
                    }
                }
            }
            map.put("titleCertificateList",titleCertificateList);
        }else if(object.getClass()==SpecialtyTrain.class){
            List<SpecialtyTrain> specialtyTrainList = specialtyTrainService.getByTableId(tableId);
            map.put("specialtyTrainList",specialtyTrainList);
        }else if(object.getClass()==PersonRegister.class){
            objectResult = personRegisterService.getByTableId(tableId);
        }else if(object.getClass()==AccountantStatus.class){
            objectResult = accountantStatusService.getByTableId(tableId);
        }else if(object.getClass()==JobKnowledge.class){
            CounselorTable ct1 = new CounselorTable();
            ct.setType("2");
            ct.setPersonRecordId(recordId);
            /*CounselorTable counselorTable1 = counselorTableService.findList(ct).get(0);
            String tableId1 = counselorTable1.getId();
            List<JobKnowledge> jobKnowledgeList = jobKnowledgeService.findByTableId(tableId1);*/
            JobKnowledge jobKnowledge = new JobKnowledge();
            jobKnowledge.setPersonId(personId);
            List<JobKnowledge> jobKnowledgeList = jobKnowledgeService.findList(jobKnowledge);
            map.put("jobKnowledgeList",jobKnowledgeList);
        }

        else if(object.getClass()==ProjectInvestment.class){
            CounselorTable table = counselorTableService.getNew(recordId, "6");
            if(table!=null&&!table.equals("")){
            String id = table.getId();
            ProjectInvestment projectInvestment = new ProjectInvestment();
            projectInvestment.setTableId(id);
            List<ProjectInvestment> list = projectInvestmentService.findList(projectInvestment);
            map.put("projectList",list);
            }
        }
        map.put("object",objectResult);
        //
        return map;
    }
    //
    //职业资格证书： 查验原件□  未查验□  一致□  不一致□
    //职业资格证书-取得证书年份填写：    正确□  不正确□
    //职业资格证书-证书编号填写：        正确□  不正确□
    //身份证件-姓名：  正确□  不正确□
    //身份证件-性别：  正确□  不正确□
    //身份证件-身份证件号：正确□  不正确□
    //身份证件-有效期 ：  有效期内□  失效□
    //劳动（聘用）合同：查验原件□  未查验□  一致□  不一致□
    //养老保险证明：查验□  未查验□

    /*
    * 1:职业资格证书 2:取得证书年份填写是否正确 3：职业资格证书编号填写是否正确 4：继续教育学时证明 5：身份证件-姓名 6：身份证件-性别 7：身份证件-身份证件号
                8：有效期 9：劳动（聘用）合同 10：养老保险证明 11：毕业证书（根据申请人填写的带出） 12：职称证书（根据申请人填写的带出） 13：培训证书（根据申请人填写的带                 出
    * */


    //此map 包含,1. 展示文字内容. 2.展示图片内容. 3.选项. 4.不符合规定列表
    private Map all(String recordId,String examineId,String type,String examineType,String type1){
        HashMap<String, Object> map = new HashMap<String, Object>();
        //1.展示文字内容
        Object object = getTable(recordId, type);
        map.put("object",object);
        //2.展示图片内容
        if(examineType!=null&&!examineType.equals("")){
        List<CounselorAttachment> imageList = getImage(recordId, examineType);
           /* Map resultMap = getResultMap(recordId, examineType);*/
           /* map.put("resultMap",resultMap);*/
            map.put("imageList",imageList);
        }
        //3.展示选项
        map = getCheck(recordId, examineId,type1,map);

        //TODO
        //4.展示不符合规定列表
        List<CounselorExamineReject> reject = getReject(examineId, recordId);
        map.put("rejectList",reject);

        return map;
    }
    //1. 获取展示的文字内容
    private Object getTable(String recordId,String kind){
        //文字分类
        /*
        * 1.个人信息  enterpriseWorekr
        * 2.学历教育情况 educationtbl
        * 3.职称情况    title_certificate
        * 4.培训情况    specialty_train
        * 5.申请登记情况  person_register
        * 6.工作经历    job_knowledge // 工作经验需要的是List
        * 7.执业情况    accountant_status
         * */
        if(kind!=null&&!kind.equals("")){
        if(kind.equals("1")){
            //1. 个人信息. 文字展示个人信息表格, 图片展示列表类型 身份证、基本情况表、劳动合同、养老保险证明、退休证
            //   1.通过表单找到人
            Counselor counselor = counselorService.get(recordId);
            String personId = counselor.getPersonId();
            //使用新方法查询,并且绑定邮箱. 使用
            EnterpriseWorkers enterpriseWorkers = enterpriseWorkersService.getAddEmail(personId);
            return enterpriseWorkers;
        }
        }
        return null;
    }
    private Integer getStadyTime(SpecialtyTrain specialtyTrain) {
        Date startTime = specialtyTrain.getStartTime();
        Date endTime = specialtyTrain.getEndTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        int imonth = calendar.get(Calendar.MONTH);
        int iyear = calendar.get(Calendar.YEAR);
        calendar.setTime(endTime);
        int jmonth = calendar.get(Calendar.MONTH);
        int jyear = calendar.get(Calendar.YEAR);
        int amonth = jmonth - imonth;
        int ayear = jyear - iyear;
        int i = ayear * 12 + amonth;
        if (i > 0) {
            return i;
        }
        return null;
    }


    //重新进行分配查看图片的map
    //使用 1,2,3,4 来区分 1.初次登记 2.变更执业单位 3.变更专业 4,继续登记
    //examine 设置type

    //////////////////////////////////////////////////
    // 初始登记
    //i11 ,基本情况
    //身份证、
    //基本情况表、
    //劳动合同、
    //养老保险证明（退休人员提供退休证）、
    //职业资格证书
    private String [] i11 = {"5","3","8","9","4"};
    //i12 学历学位
    //学历学位证
    private String [] i12 = {"7"};

    //i13 职称情况
    //职称证书
    private String [] i13 = {"6"};

    //i14 培训情况
    //培训证书
    private String [] i14 = {"13"};

    //i15 承诺书
    // 承诺书
    private String [] i15 = {"2"};

    //i16 继续教育证明
    //继续教育证明
    private String [] i16 = {"10"};

    //i17 单位法人证书及同意执业证明
    //单位法人证书及同意执业证明
    private String [] i17 = {"11"};

    //i18 其他附件
    private String [] i18 = {"12"};

    ///////////////////////////////////////
    //变更执业单位

    //i21
    //基本情况
    //基本情况表、 养老保险证明（退休人员提供证明）
    private String []i21 = {"15","9"};

    //i22
    //基本情况里的执业情况
    //新单位劳动合同,变更核准通知书或解聘证明
    private String [] i22 ={"17","16"};

    //i23
    //承诺书
    //承诺书
    private String [] i23 ={"2"};

    //i24
    //法人证书和单位同意执业证明
    private String [] i24 ={"11"};

    /////////////////////////////////////
    //变更专业

    //i31
    //基本情况表
    //基本情况表
    private String []i31 = {"23"};


    //i32
    //学历教育
    //专业培训,学历或学位证明
    private String []i32 = {"24"};

    //i33
    //职称情况
    //职称证书
    private String []i33 = {"6"};

    //i34
    //培训情况
    //专业培训, 学历或学位证明

    private String []i34 = {"24"};

    //i35
    //承诺书
    //承诺书
    private String []i35 = {"2"};


    //i36
    //其他附件
    //其他附件
    private String []i36 = {"12"};

    //i37
    //工程咨询业绩内容
    //项目业绩证明

    private String []i37 = {"19"};

////////////////////////////////////////////////
    //继续登记
    //i41
    //基本情况
    //基本情况表、劳动合同、养老保险证明（退休人员提供退休证）
    private String []i41 = {"17","21","9"};


    //i42
    //承诺书
    //承诺书
    private String []i42 = {"2"};


    //i43
    //继续教育证明
    //继续教育证明
    private String []i43 = {"22"};


    //i44
    //单位法人证书及同意执业的证明
    //单位法人证书及同意执业的证明
    private String []i44 = {"11"};


    //////////////////////////////////////
    //注销登记
    //基本情况
    //基本情况

    private String []i01 = {"3"};
    //1	封面
    //2	承诺书
    //3	基本情况表
    //4	职业资格证书
    //5	身份证
    //6	职称证书
    //7	学历学位证
    //8	劳动(聘用)合同
    //9	退休证
    //10	继续教育证明
    //11	是否事业单位特殊政策 ---->修改为 单位法人证书及统一执业证明
    //12	其他附件
    //13	培训证书
    //14	变更原因证明
    //15	变更执业单位申请表
    //16	原单位解聘证明 ----> 变更核准通知书或解聘证明
    //17	新单位劳动（聘用）合同或养老保险证明
    //19	项目业绩
    //21	继续登记申请表
    //22	继续教育学时证明




    //存在问题. 不同类型的表单, 基本情况表的 type 也不同, 1.初次登记3      2.继续登记21   3.变更专业登记23    4.注销登记3      5.变更执业单位登记 15
   /* private String [] i1 = {"5","3","8","17","9","4","21","23","15"};//新增21,23,15  表示基本情况全部在个人信息里展示
    private String [] i2 = {"7"};
    private String [] i3 = {"6"};
    private String [] i4 = {"13"};
    private String [] i5 = {"1"};
    private String [] i6 = {"2"};
    private String [] i7 = {"10","22"};
    private String [] i8 = {"11"};
    private String [] i9 = {"3","17","9"};
    private String [] i10 = {"14","16","17",};//法人证书
    private String [] i11 = {"11"};
    private String [] i12 = {"3","8","17","9","4"};
    private String [] i13 = {"7"};
    private String [] i14 = {"12"};//
    private String [] i0 = {"24"};*/

   @ResponseBody
   @RequestMapping(value = "findImage")
   private List findImage(String recordId,String type){
       CounselorTable table = counselorTableService.getNew(recordId, "3");
       if(table!=null){
           String tableId = table.getId();
           //根据tableId 加上 type数组进行查询attachment列表
           //把属性放入map中进行查询
           HashMap<String, Object> map = new HashMap<String, Object>();
           map.put("tableId",tableId);
           if(type.equals("11")){
               map.put("types",i11);
           }else if(type.equals("12")){
               map.put("types",i12);
           }else if(type.equals("13")){
               map.put("types",i13);
           }else if(type.equals("14")){
               map.put("types",i14);
           }else if(type.equals("15")){
               map.put("types",i15);
           }else if(type.equals("16")){
               map.put("types",i16);
           }else if(type.equals("17")){
               map.put("types",i17);
           }else if(type.equals("18")){
               map.put("types",i18);
           }else if(type.equals("21")){
               map.put("types",i21);
           }else if(type.equals("22")){
               map.put("types",i22);
           }else if(type.equals("23")){
               map.put("types",i23);
           }else if(type.equals("24")){
               map.put("types",i24);
           }else if(type.equals("31")){
               map.put("types",i31);
           }else if(type.equals("32")){
               map.put("types",i32);
           }else if(type.equals("33")){
               map.put("types",i33);
           }else if(type.equals("34")){
               map.put("types",i34);
           }else if(type.equals("35")){
               map.put("types",i35);
           }else if(type.equals("36")){
               map.put("types",i36);
           }else if(type.equals("37")){
               map.put("types",i37);
           }else if(type.equals("41")){
               map.put("types",i41);
           }else if(type.equals("42")){
               map.put("types",i42);
           }else if(type.equals("43")){
               map.put("types",i43);
           }else if(type.equals("44")){
               map.put("types",i44);
           }else if(type.equals("01")){
               map.put("types",i01);
           }
           List<CounselorAttachment> attachList = counselorAttachmentService.findListByType(map);
           return attachList;
       }
       return null;
   }
    //2, 获取图片
    private List<CounselorAttachment> getImage(String recordId,String type){
        //分类型取不同的图片
        //获取图片类型的字典
        //获取的图片也根据类型展示
        //1 .身份证、基本情况表、劳动合同、养老保险证明、退休证、职业资格证书
        //2 .学历学位证
        //3 .职称证书
        //4 . 培训证书
        //5 . 封面
        //6 .承诺书
        //7 . 继续教育证明
        //8 . 特殊政策
        //9 .基本情况表、养老保险证明、退休证
        //10 变更原因证明材料、原单位解聘证明、新单位劳动（聘用）合同、法人证书
        //11.是否事业单位特殊政策
        //12 . 基本情况表、劳动合同、养老保险证明、退休证、职业资格证书
        //13 . 专业培训、学历或学位证明
        //14    其他附件
        //需要用数组的方式把参数传入进去
        //查询语句. map
        //1. 因为传递的是recordId , 而附件表数据库中存储的是tableId, 因此通过recordId 查询到tableId 查询到附件
        CounselorTable table = null;
        if(type!=null&&type.equals("37")){
            //工程业绩的type为7
            table = counselorTableService.getNew(recordId, "7");
        }else {
           table = counselorTableService.getNew(recordId, "3");
        }

        if(table!=null){
        String tableId = table.getId();
        //根据tableId 加上 type数组进行查询attachment列表
        //把属性放入map中进行查询
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("tableId",tableId);
        if(type.equals("11")){
            map.put("types",i11);
        }else if(type.equals("12")){
            map.put("types",i12);
        }else if(type.equals("13")){
            map.put("types",i13);
        }else if(type.equals("14")){
            map.put("types",i14);
        }else if(type.equals("15")){
            map.put("types",i15);
        }else if(type.equals("16")){
            map.put("types",i16);
        }else if(type.equals("17")){
            map.put("types",i17);
        }else if(type.equals("18")){
            map.put("types",i18);
        }else if(type.equals("21")){
            map.put("types",i21);
        }else if(type.equals("22")){
            map.put("types",i22);
        }else if(type.equals("23")){
            map.put("types",i23);
        }else if(type.equals("24")){
            map.put("types",i24);
        }else if(type.equals("31")){
            map.put("types",i31);
        }else if(type.equals("32")){
            map.put("types",i32);
        }else if(type.equals("33")){
            map.put("types",i33);
        }else if(type.equals("34")){
            map.put("types",i34);
        }else if(type.equals("35")){
            map.put("types",i35);
        }else if(type.equals("36")){
            map.put("types",i36);
        }else if(type.equals("37")){
            map.put("types",i37);
        }else if(type.equals("41")){
            map.put("types",i41);
        }else if(type.equals("42")){
            map.put("types",i42);
        }else if(type.equals("43")){
            map.put("types",i43);
        }else if(type.equals("44")){
            map.put("types",i44);
        }else if(type.equals("01")){
            map.put("types",i01);
        }
        List<CounselorAttachment> attachList = counselorAttachmentService.findListByType(map);
            return attachList;
        }
       return null;
    }



    //3.获取展示的选项
    private HashMap<String,Object> getCheck(String recordId, String examineId, String type,HashMap<String,Object>map){
        //设计,type 分为 1,2,3,4,5
        //1 为单位查看,什么都没有
        //2 为合规审查. 查询的是personCompliance的值
        //3 为预审 查看type为2的
        // 4 终审 查看 type为3 的
        // 5 复审 查看 type为3的
        //根据不同的type 查询不同的表内的数据
        if(type.equals("2")){
            PersonCompliance personCompliance = new PersonCompliance();
            personCompliance.setExamineId(examineId);
            personCompliance.setRecordId(recordId);
            List<PersonCompliance> list = personComplianceService.findList(personCompliance);
            //判断是否存在.
            map.put("check",list);
            return map;
        }else if(type.equals("3")){
           PersonExpert personExpert = new PersonExpert();
           personExpert.setExamineType("3");
           personExpert.setRecordId(recordId);
           personExpert.setExamineId(examineId);
           List<PersonExpert> list = personExpertService.findList(personExpert);
           map.put("check",list);
           return map;
       }else if(type.equals("4")||type.equals("5")||type.equals("6")){
            //4与5 都看到的是4
            PersonExpert personExpert = new PersonExpert();
            personExpert.setExamineType("4");
            personExpert.setRecordId(recordId);
            personExpert.setExamineId(examineId);
            List<PersonExpert> list = personExpertService.findList(personExpert);
            map.put("check",list);
            return map;
       }
       return map;
    }

    //4.获取结论列表
    private List<CounselorExamineReject> getReject(String examineId,String recordId){
        CounselorExamineReject counselorExamineReject = new CounselorExamineReject();
        counselorExamineReject.setPersonRecordId(recordId);
        counselorExamineReject.setExamineId(examineId);
        List<CounselorExamineReject> rejectList = counselorExamineRejectService.findList(counselorExamineReject);
        return rejectList;
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
//根据不同的type 给不同的状态
    /*
    * type:
    * 单位              11:通过          12:退回
    * 地方协会合规      21:合规完毕
    * 地方协会预审      31:通过         32 :不通过         33:退回
    * 中咨协会终审      41:通过          42:不通过          43:退回预审单位
    * 中咨协会复审      51:通过          52:不通过          53:部分通过
    * */

    @ResponseBody
    @RequestMapping(value = "changeRecord")
    public String changeRecord(String recordId,String type,String hgReturn,String ysReturn,String impropriety,String updatetype,String fyAdvice,String firOrSec){//impropriety 为处罚标识, fyAdvcie 复议建议
        if(type.equals("51")||type.equals("53")){//通过与部分通过需要判断
            if(!improMethod(impropriety)){
                return "203";
            }
        }
        String userId = UserUtils.getUser().getId();
        String usermodel=UserUtils.getUser().getUserModel();
        String result = null;
        Counselor counselor = counselorService.get(recordId);
        if(type.equals("11")){
            changeMethod1(counselor,"5",null);
            updateDeclareDate(recordId);//add by gaoyongjian  登记时间修改到上报单位的时间
            return "11";
        }else if(type.equals("12")){
            //10.12,
            changeMethod2(counselor,"1",null,null);
            this.hgBackMessage(counselor.getId(), "1");//退回内容发送短信
            return "12";
            //changeMethod0(counselor,"4");
        }else if(type.equals("21")||type.equals("23")){
            if(type.equals("21")){
                changeMethod1(counselor,"8","21");
            }else if (type.equals("23")){
                changeMethod1(counselor,"8","23");
            }
            this.addCheckService.addCheck(recordId, "3");//add by gaoyongjian 修改预审流程，合规完成后不用专家审核也可以进行上报中咨
            //11.8 需要把当前用户的id 插入


            return "21";//跳转合规页面
        }else if(type.equals("22")){
            //10.13 在合规退回的时候,有值
            changeMethod2(counselor,"1",hgReturn,null);
            this.hgBackMessage(counselor.getId(), "1");//退回内容发送短信
            return "22";//跳转合规页面
        }else if(type.equals("31")){
            result = yushenMethod(counselor, "1",updatetype);
            //11.16 需要把所有的expert 复制出来一份, 把examinetype 为3 改成4
        }else if(type.equals("32")){
            result =yushenMethod(counselor,"3",updatetype);
        }else if(type.equals("33")){
            //10.12
            changeMethod2(counselor,"1",null,ysReturn);
            this.hgBackMessage(counselor.getId(), "1");//退回内容发送短信
            return result;
            //changeMethod0(counselor,"22");
        }else if(type.equals("41")){//12.3 如果是系统管理员,使用的result1 的updateType
            if("2".equals(usermodel)){
                result = changeResult1(counselor, updatetype, "1",firOrSec);
            }else{
                result = changeResult(counselor, userId, "1",firOrSec);
            }
        }else if(type.equals("42")){
            if("2".equals(usermodel)){
                result = changeResult1(counselor, updatetype, "3",firOrSec);
            }else{
                result = changeResult(counselor, userId, "3",firOrSec);
            }

        }else if(type.equals("91")){//12.3 如果是系统管理员,使用的result1 的updateType
            if("2".equals(usermodel)){
                result = changeResult2(counselor, updatetype, "1");
            }
        }else if(type.equals("92")){
            if("2".equals(usermodel)){
                result = changeResult2(counselor, updatetype, "3");
            }
        }else if(type.equals("43")){
            changeMethod0(counselor,"21");
        }else if(type.equals("51")){
            changeMethod3(counselor,"51",impropriety,fyAdvice,updatetype);
            if("5".equals(usermodel)){
            	return "206";
            }else{
            	return "204";
            }
        }else if(type.equals("52")){
            changeMethod3(counselor,"52",impropriety,fyAdvice,updatetype);
            if("5".equals(usermodel)){
            	return "206";
            }else{
            	return "204";
            }
        }else if(type.equals("53")){
            changeMethod3(counselor,"53",impropriety,fyAdvice,updatetype);
            if("5".equals(usermodel)){
            	return "206";
            }else{
            	return "204";
            }
        }
        //counselorService.save(counselor);
        return result;
    }
  //发送短信
    private void hgBackMessage(String recordid,String type)
    {
    	try
    	{
    		// 创建申请单对象
    		String[] args = new String[2];
    		EnterpriseWorkers enterpriseworkers=  this.enterpriseWorkersService.getMobileByRecordId(recordid);
    		if (enterpriseworkers.getMobile() != null) {
    			String decalertype;
    			
    			if ("0".equals(enterpriseworkers.getDecalerType()))
    			{
    				decalertype="注销登记";
    			}
    			else if("1".equals(enterpriseworkers.getDecalerType())){
    				decalertype="初始登记";
    			}
    			else if("2".equals(enterpriseworkers.getDecalerType())){
    				decalertype="变更执业单位";
    			}
    			else if("3".equals(enterpriseworkers.getDecalerType())){
    				decalertype="变更专业";
    			}
    			else if("4".equals(enterpriseworkers.getDecalerType())){
    				decalertype="继续登记";
    			}
    			else
    			{
    				decalertype="未知类型";
    			}
    			args[0] = "【中国工程咨询协会】提示：您单位下的咨询工程师"+ enterpriseworkers.getName() +"的"+ decalertype +"登记事项被退回，请登录系统查看。";
    			args[1] = enterpriseworkers.getMobile();
    			SendMessge.maina(args);
    		} else {

    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		
    }
    //11.16 需要把所有的expert 复制出来一份, 把examinetype 为3 改成4
    private void saveExpert(Counselor counselor){

        String recordId = counselor.getId();

        //防止重复创建,如果表中有数据了,就不再往下走
        PersonExpert p1 = new PersonExpert();
        p1.setExamineType("4");
        p1.setRecordId(recordId);
        List<PersonExpert> p1List = personExpertService.findList(p1);
        if(p1List!=null&&p1List.size()>0){
            return;
        }
        PersonExpert personExpert = new PersonExpert();
        personExpert.setExamineType("3");
        personExpert.setRecordId(recordId);
        List<PersonExpert> list = personExpertService.findList(personExpert);
        if(list!=null&&list.size()>0){
            for(PersonExpert p : list){
                p.setId(null);
                p.setExamineType("4");
                personExpertService.save(p);
            }
        }
    }


    public boolean improMethod(String impropri){//处罚规则. 通过或者部分通过,必须是无异常状态,impro为1的状态
            if(impropri!=null&&impropri.equals("1")){
                return true;
            }else{
                return false;
            }
    }

    public void changeMethod3(Counselor counselor,String type,String impropriety,String fyAdvice,String updatetype){

        String batchId = counselor.getBatchId();
        counselor.setDeclareStatus("19");
        if(type!=null&&type.equals("51")){
            counselor.setFdeclareResult("1");
            if ("1".equals(updatetype)){
            	counselor.setFirstFexpertResult("1");
            }else{
            	counselor.setSecondFexpertResult("1");
            }
            	
        }else if(type!=null&&type.equals("52")){
            counselor.setFdeclareResult("3");
            if ("1".equals(updatetype)){
            	counselor.setFirstFexpertResult("3");
            }else{
            	counselor.setSecondFexpertResult("3");
            }
        }else if(type!=null&&type.equals("53")){
            counselor.setFdeclareResult("2");
        }
        if("1".equals(updatetype)&&"51".equals(type)&&"1".equals(counselor.getSecondFexpertResult()) || 
     		   "2".equals(updatetype)&&"51".equals(type)&&"1".equals(counselor.getFirstFexpertResult()))//修改前判断，如果修改后的firstresult和secondresult结果都是1，则将Zresult修改为1
        {
     	   counselor.setFdeclareResult("1");
        }else if("1".equals(updatetype)&&"52".equals(type)&&"3".equals(counselor.getSecondFexpertResult()) || 
     		   "2".equals(updatetype)&&"52".equals(type)&&"3".equals(counselor.getFirstFexpertResult()))//修改前判断，如果修改后的firstresult和secondresult结果都是3，则将Zresult修改为3
        {
     	   counselor.setFdeclareResult("3");
        }else if("1".equals(updatetype)&&"51".equals(type)&& !"3".equals(counselor.getSecondFexpertResult())){//如果准备将firstresult修改为1，当secondresult!=3，则将Zresult修改为1
     	   counselor.setFdeclareResult("1");
        }
        else if("2".equals(updatetype)&&"51".equals(type)&& !"3".equals(counselor.getFirstFexpertResult())){//如果准备将secondresult修改为1，当firstresult!=3，则将Zresult修改为1
     	   counselor.setFdeclareResult("1");
        }else if("1".equals(updatetype)&&"52".equals(type)&& !"1".equals(counselor.getSecondFexpertResult())){//如果准备将firstresult修改为3，当secondresult!=1，则将Zresult修改为3
     	   counselor.setFdeclareResult("3");
        }
        else if("2".equals(updatetype)&&"52".equals(type)&& !"1".equals(counselor.getFirstFexpertResult())){//如果准备将secondresult修改为3，当firstresult!=1，则将Zresult修改为3
     	   counselor.setFdeclareResult("3");
        }
        else
        {
     	   counselor.setFdeclareResult("2");
        }
        //1.现在需求只是添加标识
        counselor.setImpropriety(impropriety);
        if(fyAdvice!=null&&!fyAdvice.equals("")){
            counselor.setFyAdvice(fyAdvice);
        }
        counselorService.save(counselor);
        //根据batchId 查询批次,如果declare都为19, 把所有的batchstatus 改为19
        Map batchList = counselorService.findByBatchId(batchId, "19");
        Integer num = (Integer) batchList.get("num");//num 查询的是!= 19 的个数,如果为0 全部修改batchId
        if(num!=null&&num==0){
            List<Counselor>list = (List<Counselor>) batchList.get("list");
            for(Counselor c: list){
                c.setBatchStatus("19");
                if(fyAdvice!=null&&!fyAdvice.equals("")){
                    c.setFyAdvice(fyAdvice);
                }
                counselorService.save(c);
            }
        }
    }

    public void changeMethod2(Counselor counselor,String status,String hgReturn,String ysReturn){
        //10.12
        //地方专家的退回按钮   将整批次batch_status和declarestatus都改成1   条件是batch_id
        //1.查询本批次
        String batchId = counselor.getBatchId();
        Counselor counselor1 = new Counselor();
        counselor1.setBatchId(batchId);
        List<Counselor> list = counselorService.findList(counselor1);
        for(Counselor c : list){
            if(hgReturn!=null&&!hgReturn.equals("")){//不为空,不为"" 的时候进行赋值
                c.setHgReturn(hgReturn);
                c.setReturnType("1");//合规退回类型
            }
            if(ysReturn!=null&&!ysReturn.equals("")){//不为空,不为"" 的时候进行赋值
                c.setReturnReason(ysReturn);
                c.setReturnType("2");//预审
            }
            c.setBatchStatus(status);
            c.setDeclareStatus(status);
            counselorService.save(c);
        }
    }

    //前三种情况.
    public void changeMethod1(Counselor counselor,String status,String hgFlag){//新增内容，hg的Flag， 分21 与 23 ，如果是21 就设置hdeclect 为1， 如果是23 就设置为3
        String userId = UserUtils.getUser().getId();
        //declare_status   变更6，如果批次ID查出来的是多条，多条数据的declare_status都是6的时候，把batch_status改成6，如果是只有一条则两个状态都变更
        //1.同意上报.
        //1.查看是否为多条>,根据batch查询
        String batchId = counselor.getBatchId();
        Map map = counselorService.findByBatchId(batchId,status);//需要查询两个参数. 用map装, 1.list batchId的所有值,
        List<Counselor>list = (List<Counselor>) map.get("list");
        Integer num = (Integer) map.get("num");

        //11.8 存储当前审核用户的id . 合规完毕才进行此步骤. 合规完毕的状态为8
        if(status!=null&&status.equals("8")){
            counselor.setHgExpertId(userId);
        }

        //1.先判断是否只有一条数据,如果只有一条数据直接进行添加
        if(list.size()==1){//只有一条数据
            counselor.setDeclareStatus(status);
            counselor.setBatchStatus(status);
            if(hgFlag!=null&&hgFlag.equals("21")){
                counselor.setHdeclareResult("1");
            }else if (hgFlag!=null&&hgFlag.equals("23")){
                counselor.setHdeclareResult("3");
            }
            counselorService.save(counselor);
        }else{
            //分两种情况, 所有都!= 这个数,也就是只有本身是不等于的, 因此num==1
            if(num==1){//表示全部都得变,包括本身
               /* counselor.setDeclareStatus(status);
                counselor.setBatchStatus(status);
                counselorService.save(counselor);*/
                for(Counselor c:list){
                    //需要给现在的counse进行设置
                    if(status!=null&&status.equals("8")){
                        if(counselor.getId().equals(c.getId())){
                            c.setHgExpertId(userId);
                        }
                    }

                    if(hgFlag!=null&&hgFlag.equals("21")){
                        c.setHdeclareResult("1");
                    }else if (hgFlag!=null&&hgFlag.equals("23")){
                        c.setHdeclareResult("3");
                    }
                    c.setDeclareStatus(status);
                    c.setBatchStatus(status);
                    counselorService.save(c);
                }
            }else if(num==0){//还有一种可能是人员重复点击,把已经提交过的 再提交一遍,不进行任何操作
                return;
            }else{//只变本身这一条
                counselor.setDeclareStatus(status);
                if(hgFlag!=null&&hgFlag.equals("21")){
                    counselor.setHdeclareResult("1");
                }else if (hgFlag!=null&&hgFlag.equals("23")){
                    counselor.setHdeclareResult("3");
                }
                counselorService.save(counselor);
            }
        }
    }


    public void changeMethod0(Counselor counselor,String status){

        counselor.setDeclareStatus(status);
        counselor.setBatchStatus(status);
        counselor.setReturnType("3");//退回预审单位状态
        counselorService.save(counselor);
    }

    public String yushenMethod(Counselor counselor,String result,String updateType){
        String firstId = counselor.getFirstCexpertId();
        String secondId = counselor.getSecondCexpertId();
        //1.查询出该user
        User user = UserUtils.getUser();
        String id = user.getId();
        //看id 是和那个匹配
        if(updateType==null||updateType.equals("")){
            if(firstId!=null&&id.equals(firstId)){
                counselor.setFirstCdeclareResult(result);
            }else if(secondId!=null&&id.equals(secondId)){
                counselor.setSecondCdeclareResult(result);
            }else {
                // return "201";//无匹配专家
            }
        }else{
            if("1".equals(updateType)) //如果画面上选的是修改专家1结论
            {
                counselor.setFirstCdeclareResult(result);
            }
            else if("2".equals(updateType))//如果画面上选的是修改专家2结论
            {
                counselor.setSecondCdeclareResult(result);
            }
            else
            {return "202";}//不是专家点的  或是管理员没有选择修改哪个专家的结果
        }

        counselorService.save(counselor);
        yushenMethod2(counselor,result);
        //11.16 创建一份type 为4的表单
        //saveExpert(counselor);
        return "200";
    }

    public void yushenMethod2(Counselor counselor,String result){
        //需要根据分配专家id,
        String first = counselor.getFirstCexpertId();
        String second = counselor.getSecondCexpertId();
        if(first!=null&&!first.equals("")&&second!=null&&!second.equals("")){
            String firstResult = counselor.getFirstCdeclareResult();
            String secondResult = counselor.getSecondCdeclareResult();
            if(firstResult!=null&&secondResult!=null&&firstResult.equals("1")&&secondResult.equals("1")){
                counselor.setCdeclareResult("1");
            }else if(firstResult!=null&&secondResult!=null&&firstResult.equals("3")&&secondResult.equals("3")){
                counselor.setCdeclareResult("3");
            }else{
                counselor.setCdeclareResult("2");
            }
        }else{
            counselor.setCdeclareResult(result);
        }
        counselorService.save(counselor);
        changeMethod1(counselor,"9",null);
    }

    public String zhongshenMethod(Counselor counselor,String result,String updatetype){
        String firstId = counselor.getFirstZexpertId();
        String secondId = counselor.getSecondZexpertId();
        //1.查询出该user
        User user = UserUtils.getUser();
        String id = user.getId();
        //看id 是和那个匹配
        if ("".equals(updatetype)){ //modify by gaoyongjian 20181118 增加管理员可修改不同专家的结论，如果是“”，说明是专家点击的
        	if(firstId!=null&&id.equals(firstId)){
                counselor.setFirstZdeclareResult(result);
            }else if(secondId!=null&&id.equals(secondId)){
                counselor.setSecondZdeclareResult(result);
            }else {
                return "201";//无匹配专家
            }
        }
        else
        {
        	if("1".equals(updatetype)) //如果画面上选的是修改专家1结论
        	{
        		counselor.setFirstZdeclareResult(result);
        	}
        	else if("2".equals(updatetype))//如果画面上选的是修改专家2结论
        	{
        		counselor.setSecondZdeclareResult(result);
        	}
        	else
        	{return "202";}//不是专家点的  或是管理员没有选择修改哪个专家的结果
        }
        counselorService.save(counselor);
        zhongshenMethod2(counselor,result);
        return "200";
    }

    public void zhongshenMethod2(Counselor counselor,String result){
        String first = counselor.getFirstZexpertId();
        String second = counselor.getSecondZexpertId();
        if(first!=null&&!first.equals("")&&second!=null&&!second.equals("")){
            String firstResult = counselor.getFirstZdeclareResult();
            String secondResult = counselor.getSecondZdeclareResult();
            if(firstResult!=null&&secondResult!=null&&firstResult.equals("1")&&secondResult.equals("1")){
                counselor.setZdeclareResult("1");
            }else if(firstResult!=null&&secondResult!=null&&firstResult.equals("3")&&secondResult.equals("3")){
                counselor.setZdeclareResult("3");
            }else{
                counselor.setZdeclareResult("2");
            }
        }else{
            counselor.setZdeclareResult(result);
        }
        counselorService.save(counselor);
        changeMethod1(counselor,"13",null);
    }

    ///当first_Zexpert_id和second_Zexpert_id都有值的情况时
    //	1.哪个专家点的就修改那个专家的结论
    //	2.first_Zexpert_reslut和second_Zdeclare_reslut都不为空时（也就是两个专家都审核完了）
    //		修改declare_status='13'
    //	  else
    //		不修改
    //else
    //	哪个专家点的就修改那个专家的结论

    //12.3


    public String changeResult(Counselor counselor,String thisId,String result,String firOrSec){
        //当前专家的id
        String firstId = counselor.getFirstZexpertId();
        String secondId = counselor.getSecondZexpertId();
        //1.哪个专家点 就修改哪个专家结论
        if (!"2".equals(UserUtils.getUser().getUserModel()))//如果是管理员修改则直接修改
        {
        	if(firstId.equals(secondId))
        	{
		        if(firstId!=null&&firstId.equals(thisId)&&"1".equals(firOrSec)){
		                counselor.setFirstZdeclareResult(result);
		        }else if(secondId!=null&&secondId.equals(thisId)&&"2".equals(firOrSec)){
		                counselor.setSecondZdeclareResult(result);
		        }else{
		            //无匹配专家.
		            return "202";
		        }
        	}
        	else
        	{
        		if(firstId!=null&&firstId.equals(thisId)){
	                counselor.setFirstZdeclareResult(result);
		        }else if(secondId!=null&&secondId.equals(thisId)){
		                counselor.setSecondZdeclareResult(result);
		        }else{
		            //无匹配专家.
		            return "202";
		        }
        	}
        }

        String fResult = counselor.getFirstZdeclareResult();
        String sResult = counselor.getSecondZdeclareResult();
        
        if(firstId!=null&&secondId!=null&&!firstId.equals("")&&!secondId.equals("")){
            if(fResult!=null&&sResult!=null&&!fResult.equals("")&&!sResult.equals("")){
                counselor.setDeclareStatus("13");
            }
        }else{
            counselor.setDeclareStatus("13");
        }
        counselorService.save(counselor);
        String batchId = counselor.getBatchId();

        Map batchList = counselorService.findByBatchId(batchId, "13");
        Integer num = (Integer) batchList.get("num");//num 查询的是!= 13 的个数,如果为0 全部修改batchId
        if(num!=null&&num==0){
            List<Counselor>list = (List<Counselor>) batchList.get("list");
            for(Counselor c: list){
                c.setBatchStatus("13");
                counselorService.save(c);
            }
        }
        //两个result都不为空的情况下
        return "200";
    }

    public String changeResult1(Counselor counselor,String updateId,String result,String firOrSec){
        String thisId= "";
       if(updateId!=null&&updateId.equals("1")){//为1的时候,表示是修改专家1, 因此把
           thisId=counselor.getFirstZexpertId();
           counselor.setFirstZdeclareResult(result);
       }else if(updateId!=null&&updateId.equals("2")){
           thisId=counselor.getSecondZexpertId();
           counselor.setSecondZdeclareResult(result);
       }
       return changeResult(counselor,thisId,result,firOrSec);
    }
    public String changeResult2(Counselor counselor,String updateId,String result){
        String thisId= "";
       if(updateId!=null&&updateId.equals("1")){//为1的时候,表示是修改专家1, 因此把
           thisId=counselor.getFirstZexpertId();
           counselor.setFirstZdeclareResult(result);
       }else if(updateId!=null&&updateId.equals("2")){
           thisId=counselor.getSecondZexpertId();
           counselor.setSecondZdeclareResult(result);
       }
       if("1".equals(updateId)&&"1".equals(result)&&"1".equals(counselor.getSecondZdeclareResult()) || 
    		   "2".equals(updateId)&&"1".equals(result)&&"1".equals(counselor.getFirstZdeclareResult()))//修改前判断，如果修改后的firstresult和secondresult结果都是1，则将Zresult修改为1
       {
    	   counselor.setZdeclareResult("1");
       }else if("1".equals(updateId)&&"3".equals(result)&&"3".equals(counselor.getSecondZdeclareResult()) || 
    		   "2".equals(updateId)&&"3".equals(result)&&"3".equals(counselor.getFirstZdeclareResult()))//修改前判断，如果修改后的firstresult和secondresult结果都是3，则将Zresult修改为3
       {
    	   counselor.setZdeclareResult("3");
       }else if("1".equals(updateId)&&"1".equals(result)&& !"3".equals(counselor.getSecondZdeclareResult())){//如果准备将firstresult修改为1，当secondresult!=3，则将Zresult修改为1
    	   counselor.setZdeclareResult("1");
       }
       else if("2".equals(updateId)&&"1".equals(result)&& !"3".equals(counselor.getFirstZdeclareResult())){//如果准备将secondresult修改为1，当firstresult!=3，则将Zresult修改为1
    	   counselor.setZdeclareResult("1");
       }else if("1".equals(updateId)&&"3".equals(result)&& !"1".equals(counselor.getSecondZdeclareResult())){//如果准备将firstresult修改为3，当secondresult!=1，则将Zresult修改为3
    	   counselor.setZdeclareResult("3");
       }
       else if("2".equals(updateId)&&"3".equals(result)&& !"1".equals(counselor.getFirstZdeclareResult())){//如果准备将secondresult修改为3，当firstresult!=1，则将Zresult修改为3
    	   counselor.setZdeclareResult("3");
       }
       else
       {
    	   counselor.setZdeclareResult("2");
       }
       
       counselorService.save(counselor);
       //两个result都不为空的情况下
       return "200";
    }



    //12.20  修改查询条件
    // 学历证明,职称证书,培训院校 是根据personId 进行查询的
    private Map treeContent1(String recordId,Object object){
        HashMap<String, Object> map = new HashMap<String, Object>();
        CounselorTable ct = new CounselorTable();
        //type是 基本情况的type , 1.是基本情况 2.工作经历 3.相关附件 6.工程咨询
        ct.setType("1");
        ct.setPersonRecordId(recordId);
        Counselor counselor = counselorService.get(recordId);
        String personId = counselor.getPersonId();
        CounselorTable counselorTable = counselorTableService.findList(ct).get(0);
        String tableId = counselorTable.getId();
        Object objectResult=null;
        if(object.getClass()==Educationtbl.class){
            Educationtbl educationtbl = new Educationtbl();
            educationtbl.setPersonId(personId);
            List<Educationtbl> educationtblList = educationtblService.findList(educationtbl);
            // List<Educationtbl> educationtblList = educationtblService.getByTableId(tableId);
            map.put("educationtblList",educationtblList);
        }else if(object.getClass()==TitleCertificate.class){
            TitleCertificate titleCertificate = new TitleCertificate();
            titleCertificate.setPersonId(personId);
            List<TitleCertificate> titleCertificateList = titleCertificateService.findList(titleCertificate);
           // List<TitleCertificate> titleCertificateList = titleCertificateService.getByTableId(tableId);
            //11.13 需要把工作单位进行转义
            if(titleCertificateList!=null&&titleCertificateList.size()>0){
                for(TitleCertificate t : titleCertificateList){
                    String employer = t.getGetEmployer();
                    if(employer!=null&&!employer.equals("")){
                        String temp = StringEscapeUtils.unescapeHtml4(employer);
                        t.setGetEmployer(temp);
                    }
                }
            }
            map.put("titleCertificateList",titleCertificateList);
        }else if(object.getClass()==SpecialtyTrain.class){
            SpecialtyTrain specialtyTrain = new SpecialtyTrain();
            specialtyTrain.setPersonId(personId);
            List<SpecialtyTrain> specialtyTrainList = specialtyTrainService.findList(specialtyTrain);
            //List<SpecialtyTrain> specialtyTrainList = specialtyTrainService.getByTableId(tableId);
            map.put("specialtyTrainList",specialtyTrainList);
        }else if(object.getClass()==PersonRegister.class){
            objectResult = personRegisterService.getByTableId(tableId);
        }else if(object.getClass()==AccountantStatus.class){
            objectResult = accountantStatusService.getByTableId(tableId);
        }else if(object.getClass()==JobKnowledge.class){
            CounselorTable ct1 = new CounselorTable();
            ct.setType("2");
            ct.setPersonRecordId(recordId);
            /*CounselorTable counselorTable1 = counselorTableService.findList(ct).get(0);
            String tableId1 = counselorTable1.getId();
            List<JobKnowledge> jobKnowledgeList = jobKnowledgeService.findByTableId(tableId1);*/
            JobKnowledge jobKnowledge = new JobKnowledge();
            jobKnowledge.setPersonId(personId);
            List<JobKnowledge> jobKnowledgeList = jobKnowledgeService.findList(jobKnowledge);
            map.put("jobKnowledgeList",jobKnowledgeList);
        }

        else if(object.getClass()==ProjectInvestment.class){
            CounselorTable table = counselorTableService.getNew(recordId, "6");
            if(table!=null&&!table.equals("")){
                String id = table.getId();
                ProjectInvestment projectInvestment = new ProjectInvestment();
                projectInvestment.setTableId(id);
                List<ProjectInvestment> list = projectInvestmentService.findList(projectInvestment);
                map.put("projectList",list);
            }
        }
        map.put("object",objectResult);
        //
        return map;
    }
}
