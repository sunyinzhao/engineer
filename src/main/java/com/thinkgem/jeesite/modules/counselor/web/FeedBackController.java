package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.*;
import com.thinkgem.jeesite.modules.counselor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//反馈表
@Controller
@RequestMapping(value = "${adminPath}/counselor/feedBack")
public class FeedBackController extends BaseController {
    @Autowired
    private PersonExpertService personExpertService;

    @Autowired
    private CounselorAttachmentService counselorAttachmentService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private EducationtblService educationtblService;

    @Autowired
    private TitleCertificateService titleCertificateService;

    @Autowired
    private SpecialtyTrainService specialtyTrainService;

    @Autowired
    private CounselorTableService counselorTableService;




    @Autowired
    private CounselorService counselorService;

    @Autowired
    private NotRealService notRealService;




    @RequestMapping(value = "openFail")
    public String openFail(PersonExpert personExpert, Model model,String kind){//11.21 kind用于控制返回按钮, 如果有kind 表示,从审查页面点击过来的,不可有返回按钮
        if(kind!=null&&!kind.equals("")){
            model.addAttribute("kind",kind);
        }
        String recordId = personExpert.getRecordId();
        String personId = findParam(recordId);
        String examineType = personExpert.getExamineType();
        if(examineType.equals("5")){//如果传递过来的type是5 那么变为4, 查询4的数据
            examineType = "4";
        }
        model.addAttribute("personId",personId);
        //存放图片
        List<CounselorAttachment> imageList = findImage(personId);
        model.addAttribute("imageList",imageList);

        //11.21 添加flag 如果utilfeed 有值,添加flag
        Counselor counselor = counselorService.get(recordId);
        model.addAttribute("fResult",counselor.getFirstZdeclareResult());
        model.addAttribute("sResult",counselor.getSecondZdeclareResult());

        String utilFeedback = counselor.getUtilFeedback();
        if(utilFeedback!=null&& (utilFeedback.equals("1")||utilFeedback.equals("2"))){
            model.addAttribute("flag","1");
        }
        //普通的选项与 学历等选项进行区分
        String [] types = {"35","36","37","38","39","40"};
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("items","1");
        map.put("recordId",personExpert.getRecordId());
        map.put("examineType",examineType);
        map.put("types",types);
        List<PersonExpert> list = personExpertService.findFailList1(map);
        if(list.size()==0||list==null){
            model.addAttribute("message","没有不真实或不符合的项");
        }else{
            personExpert.setExpertList(list);
            model.addAttribute("personExpert",personExpert);
        }
        //1.获取tableId
        CounselorTable counselorTable = new CounselorTable();
        counselorTable.setPersonRecordId(recordId);
        counselorTable.setStatus("1");//因为所有的这些信息都是关联基本信息这个tableId里, 状态为1的是tableId
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
        return "modules/counselor/feedBackIndex";
    }

    //查找图片的list
    private List<CounselorAttachment> findImage(String personId){
        //通过personId 进行查找, jsp页面通过expertId 进行匹配
        CounselorAttachment counselorAttachment = new CounselorAttachment();
        counselorAttachment.setPid(personId);
        List<CounselorAttachment> imageList = counselorAttachmentService.findList(counselorAttachment);
        return imageList;
    }


    //增加图片需要的参数

    private String findParam(String recordId){
        //1.需要personId
        Counselor counselor = counselorService.get(recordId);
        return counselor.getPersonId();
    }


    @ResponseBody
    @RequestMapping(value = "saveFeedBack")
    public String saveFeedBack(PersonExpert personExpert){
        List<PersonExpert> expertList = personExpert.getExpertList();
        List<PersonExpert> educationtblList = personExpert.getEducationtblList();
        List<PersonExpert> titleList = personExpert.getTitleList();
        List<PersonExpert> specialtyList = personExpert.getSpecialtyList();
        saveMethod(expertList);
        saveMethod(educationtblList);
        saveMethod(titleList);
        saveMethod(specialtyList);
        return null;
    }

    private void saveMethod(List<PersonExpert>list){
        if(list!=null&&!list.equals("")){
            for (PersonExpert p :list){
                Feedback feedback = p.getFeedback();
                feedbackService.save(feedback);
            }
        }
    }


    //点击提交,更改util feedBack 跳转到首页.
    @RequestMapping(value = "saveUtil")
    private String saveUtil(String recordId){
        Counselor counselor = counselorService.get(recordId);
        counselor.setUtilFeedback("1");
        counselorService.save(counselor);
        return "redirect:" + adminPath + "/counselor/firstIndex?id=" + recordId+"&look=1";
    }


    //展示所有不真实的数据,
    @RequestMapping(value = "notRealList")
    private String notReal(HttpServletRequest request, HttpServletResponse response,Model model,NotReal notReal){
        //需要展示的有,类型,
        Page<NotReal> page = notRealService.findPage(new Page<NotReal>(request, response), notReal);
        model.addAttribute("page",page);
        model.addAttribute("notReal",notReal);
        return "modules/counselor/notRealList";
    }


    //修改record表有两种方法,
    //1. 通过选择框修改
    //2. 一次全部修改



    @RequestMapping(value = "changeRecordAll")
    private String changeRecordAll(){
        ArrayList<String> slist = new ArrayList<String>();
        List<NotReal> list = notRealService.findList(new NotReal());
        for(NotReal n : list){
            slist.add(n.getRecordId());
        }

        changeRecordMethod(slist);
        return "200";
    }



    @ResponseBody
    @RequestMapping(value = "changeRecord")
    private String changeRecord(String records){
        //切割进入方法
        ArrayList<String> list = new ArrayList<String>();
        String[] s = records.split(",");
        for(String ss : s){
            list.add(ss);
        }
        changeRecordMethod(list);
        return "200";
    }


    //修改record表的内容方法
    public void changeRecordMethod(List<String>strings){
        for(String id : strings){
            //1.查询出recordId
            Counselor c = counselorService.get(id);
            //存在已被修改的表单,如果被修改,直接跳过循环
            String ifFinish = c.getIfFinish();//是否已经处理
            if(ifFinish!=null&&ifFinish.equals("1")){//已处理就跳过
                continue;
            }
            //修改表单内容
            recordMethod(c);
        }
    }


    //修改record表
    //	1.当first_Zexpert_id !='' and first_Zexpert_id != null
    //		修改当first_Zdeclare_result=3
    //	2.当second_Zexpert_id !='' and 当second_Zexpert_id != null
    //		修改当second_Zdeclare_result=3
    //	3.当这个申请单的同批次登记项中存在这个被置为不真实的审查项时
    //		1.当同批次的其他申请单first_Zexpert_id !='' and first_Zexpert_id != null
    //			修改其他登记事项first_Zdeclare_result=3
    //		2.当同批次的其他申请单second_Zexpert_id !='' and 当second_Zexpert_id != null
    //			修改其他登记事项second_Zdeclare_result=3
    //		3.如果同批次下的其他登记事项的审查项中包含 这个被置为不真实的登记事项
    //			将同批次下的其他登记事项的这个审查项也改为不真实
    public void recordMethod(Counselor counselor){
        String id = counselor.getId();
        String batchId = counselor.getBatchId();
        Counselor c1 = new Counselor();
        c1.setBatchId(batchId);
        List<Counselor> list = counselorService.findList(c1);
        for(Counselor c : list){
            String firstZexpertId = c.getFirstZexpertId();
            String secondZexpertId = c.getSecondZexpertId();
            //1.
            if(firstZexpertId!=null&&!firstZexpertId.equals("")){
                c.setFirstZdeclareResult("3");
            }
            //2.
            if(secondZexpertId!=null&&!secondZexpertId.equals("")){
                c.setSecondZdeclareResult("3");
            }
            c.setIfFinish("1");
            counselorService.save(c);
        }
        //3.当这个申请单的同批次登记项中存在这个被置为不真实的审查项时
        // 不真实项有可能有多条,
        // 如果同批次下的其他登记事项的审查项中包含 这个被置为不真实的登记事项
        // 将同批次下的其他登记事项的这个审查项也改为不真实
        findNotReal(counselor);

    }

    //根据recordId 查询到多条不真实
    private void findNotReal(Counselor counselor){
        String id = counselor.getId();
        String batchId = counselor.getBatchId();
        //1.查询出list
   /*     PersonExpert personExpert = new PersonExpert();
        personExpert.setExamineType("4");
        personExpert.setRecordId(id);
        personExpert.setItems("99");*/
        //SELECT type FROM person_expert WHERE item = '99' AND record_id = #{id} AND examint_type = '4'
        /*List<PersonExpert> list = personExpertService.findList(personExpert);*/
        //存储type 写sql 为  SELECT * FROM person_expert WHERE type in (#lists) and record_id in (SELECT id FROM person_record WHERE batch_id = #{batchId} AND del_flag = '0' AND id != #{id}) AND examine_type = '4'
      /*  ArrayList<String> lists = new ArrayList<String>();
        for(PersonExpert p : list){
            String type = p.getType();
            lists.add(type);
        }*/
        //根据不真实的所有项,查询这个批次下,所有的这些type,进行修改item
        List<PersonExpert>expertList = personExpertService.findNotReal(batchId,id);
        updateNotReal(expertList,id);

    }

    //修改同批次下的不真实项,把同批次下的type更新为99
    private void updateNotReal(List<PersonExpert> list,String recordId){
        //判断是否为空
        if(list!=null&&list.size()>0){
            //查找,改recordId 下的list 并且不为99的
            List<PersonExpert>resultList = personExpertService.findNot99List(recordId);
            for(PersonExpert p : list){
                for(PersonExpert p1 :resultList){
                    String p1Type = p1.getType();
                    String pType = p.getType();
                    String itemText = p1.getItemText();
                    if(p1Type.equals(pType)){
                        p.setItemText(itemText);
                        p.setItems("99");
                        personExpertService.save(p);
                    }
                }
            }
        }
    }

    //更新education // 等表

    private List<PersonExpert> updateMethod(int type){
        //1. 查找到所有未关联的expert list
        //2. 再根据recordId 查找到 education 实体. 把id 更新到main
        List<PersonExpert> list = null;
        if(type==35){
           list = personExpertService.findNotRelev35(type);//查找到未关联的list
        }else if(type==36){
           list = personExpertService.findNotRelev36(type);//查找到未关联的list
        }else if(type==37){
           list = personExpertService.findNotRelev37(type);
        }else if(type==38){
            list = personExpertService.findNotRelev38(type);
        }else if(type==39){
            list = personExpertService.findNotRelev39(type);
        }else if(type==40){
            list = personExpertService.findNotRelev40(type);
        }

        return list;
    }

    private void educationMethod(int type){
        List<PersonExpert> list = updateMethod(type);
        for(PersonExpert p : list){
            String recordId = p.getRecordId();
            String index = p.getIndex();
            String pid = p.getId();
            //先根据recordId 查询 education
            List<Educationtbl> eList = educationtblService.findNotRelev(recordId);
            //查询出eList, 遍历,用index 与p 对应
            if(eList!=null||eList.size()>0){
            for(Educationtbl e :eList){
                if(index==null||index.equals(e.getIndex())){
                    if(type==35){
                        e.setMain(pid);//35 设置main
                    }else if(type==36){
                        e.setAssist(pid);
                    }
                    educationtblService.save(e);
                }
            }
            }
        }
    }

    private void educationMethod1(){
        educationMethod(35);
        educationMethod(36);
    }


    //更新title 表

    private void titleMethod(int type){
        List<PersonExpert> list = updateMethod(type);
        for(PersonExpert p : list){
            String recordId = p.getRecordId();
            String index = p.getIndex();
            String pid = p.getId();
            //先根据recordId 查询 education
            List<TitleCertificate> tList = titleCertificateService.findNotRelev(recordId);
            //查询出eList, 遍历,用index 与p 对应
            if(tList!=null||tList.size()>0){
            for(TitleCertificate t :tList){
                if(index==null||index.equals(t.getIndex())){
                    if(type==37){
                        t.setMain(pid);//35 设置main
                    }else if(type==38){
                        t.setAssist(pid);
                    }
                    titleCertificateService.save(t);
                }
            }
            }
        }
    }

    private void titleMethod1(){
        titleMethod(37);
        titleMethod(38);
    }




    private void specialtyMethod(int type){
        List<PersonExpert> list = updateMethod(type);
        for(PersonExpert p : list){
            String recordId = p.getRecordId();
            String index = p.getIndex();
            String pid = p.getId();
            //先根据recordId 查询 education
            List<SpecialtyTrain> sList = specialtyTrainService.findNotRelev(recordId);
            //查询出eList, 遍历,用index 与p 对应
            if(sList!=null||sList.size()>0){
            for(SpecialtyTrain s :sList){
                if(index==null||index.equals(s.getIndex())){
                    if(type==39){
                        s.setMain(pid);//35 设置main
                    }else if(type==40){
                        s.setAssist(pid);
                    }
                    specialtyTrainService.save(s);
                }
            }
            }
        }
    }

    private void specialtyMethod1(){
        specialtyMethod(39);
        specialtyMethod(40);
    }



    @RequestMapping(value = "updateAll")
    private void updateAll(){
        educationMethod1();
        specialtyMethod1();
        titleMethod1();
    }


    //传递recordid
    private String updateExpert(){
        //先查询出所有的education
        List<Educationtbl> elist = educationtblService.findList(new Educationtbl());//查询出所有的的 education
        //1.查询出来的
        for(Educationtbl e : elist){
            int size = elist.size();
            //查询出这条数据归属于哪个表单
            String tableId = e.getTableId();
            CounselorTable table = counselorTableService.get(tableId);
            String recordId = table.getPersonRecordId();//查询到此条学历证明出自哪个表单

            /*List<PersonExpert> pList = personExpertService.findEducation(recordId);*///查询这个表单下的数据有几个
           /* int pSize = pList.size();//数量应该是两倍
            if(size*2!=pSize){
                expertMethod(elist,pList,recordId);
            }*/

        }
        return null;
    }


    private String [] educationList = {"35","36"};//学历

    private void expertMethod(List<Educationtbl> eList,List<PersonExpert>pList,String recordId){
        //如果eList 为空或者0的话 直接return
        if(eList==null||eList.size()==0){
            return;
        }


        //数量不一出现的问题,需要添加
        for(Educationtbl e : eList){
            String main = e.getMain();
            String assist = e.getAssist();
            String flag = "0";
            for(PersonExpert p : pList){
                String pid = p.getId();
                if(main!=null&&pid.equals(main)){
                    flag="1";
                    continue;

                }else if(assist!=null&&pid.equals(assist)){
                    flag="1";
                    continue;
                }
            }
            if(flag=="0"){//表示并没有11对应的id
            for(String s : educationList){
                PersonExpert personExpert = new PersonExpert();
                personExpert.setExamineType("4");
                personExpert.setRecordId(recordId);
                personExpert.setType(s);
                personExpert.setIndex(e.getIndex());
                personExpertService.save(personExpert);
                String id = personExpert.getId();
                if (s.equals("35")) {// 需要把主辅专业放进去>
                    // 主
                    e.setMain(id);
                } else {// 辅
                    e.setAssist(id);
                }
                educationtblService.save(e);
            }
            }

        }
    }
}
