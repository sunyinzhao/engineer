package com.thinkgem.jeesite.modules.counselor.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.*;
import com.thinkgem.jeesite.modules.counselor.service.*;
import com.thinkgem.jeesite.modules.sys.entity.Examine;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping(value = "${adminPath}/counselor/personExpert")
public class PersonExpertController extends BaseController {
    @Autowired
    private CounselorService counselorService;

    @Autowired
    private CounselorTableService counselorTableService;

    @Autowired
    private TitleCertificateService titleCertificateService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private PersonExpertService personExpertService;

    @Autowired
    private EducationtblService educationtblService;

    @Autowired
    private SpecialtyTrainService specialtyTrainService;
    //初始登记--> 基本信息 -->个人信息 -->
/*
* 预审终审增加选项数据
* //预/终审选项
					自动分配项目							默认值
	初次申请					1	不予登记的情形的审查							0：不具有完全民事行为能力
						2	职业资格证书							0：未提供
						3	继续教育学时证明							0：应提供但未提供
						4	劳动（聘用）合同							0：合同不在有效期内
						5	养老保险证明							0：无社保部门公章
						6	是否采取事业单位特殊政策							0：否
						7	申请人提供的工作单位同意申请人执业的证明							0：应提供，但未提供
						8	工作单位具有事业法人证书							0：否
						9	工作单位未在在线平台备案							0：未备案
						10	工作经历（结合职称证书情况）的审查							0：不支持
						11	毕业证书是否支持专业							0:不支持
						12	职称证书是否支持专业							0:不支持
						13	培训证书是否支持专业							0:不支持
						14	业绩证明材料对主专业的支持							0：不符合
						15	业绩证明材料对辅专业的支持							0：不符合
						16	有效业绩符合主专业情况							0：不符合
						17	有效业绩符合辅专业情况							0：不符合
						18	提供的业绩是否属于规定的业绩范围							0：不属于
						19	提供的业绩符合告知性备案的要求							0：业绩完成时间早于证明取得时间
						20	业绩证明材料-咨询成果封面页							0：不符合
						21	业绩证明材料-署名页							0：不符合
						22	业绩证明材料-目录页							0：不符合
						23	业绩证明材料-项目合同							0：不符合
						24	业绩证明材料-委托方出具的完成证明							0：不符合
						25	在业绩中，申请人承担的工作不能支持所申请的专业							0：不支持
						26	提供业绩的完成单位未在申请人简历中出现又无正当理由							0：无
						27	业绩满足近3年内的要求							0：不满足
						28	基本情况表中原执业单位已盖章							0：无证明
						29	原单位解聘证明							0：不符合
						30	上一年度执业检查的结论							0：不合格'
						31.同时申请的其他登记事项是否有弄虚作假的情况    0:否
						32.原执业单位解聘证明        0:不符合
						33.个人是否签字 0 否 1 是
						34.单位是否盖章   0 否

*
* */
    //用于增加
    private String []c0 = {"33","34"};

    //初次登记
    private String [] c11 = {"1","2","4","5",};//初次登记 个人信息
   // private String [] c12 = {"11"};//学历
   // private String [] c13 = {"12"};//职称
   // private String [] c14 = {"13"};//培训
    private String [] c12 = {"35","36"};//学历
    private String [] c13 = {"37","38"};//职称
    private String [] c14 = {"39","40"};//培训

    private String [] c15 = {"3"};//继续教育证明
    private String [] c16 = {"6","7","8","9"};//特殊政策
    private String [] c17 = {"10"};//工作经历

    /*
    1.不予登记的情形的审查：下拉框
    2.基本情况表中原执业单位已盖章
    3.养老保险证明
    4.同时申请的其他登记事项是否有弄虚作假的情况

     */
    //变更执业登记
    private String []c21 = {"1","28","5","31"};//个人信息
    private String []c22 = {"32","4","30"};//执业情况
    private String []c23 = {"6","7","8","9"};//特殊政策

    //变更专业

    private String []c31 = {"1","2","4","5"};//个人信息
    /*private String []c32 = {"11"};//学历
    private String []c33 = {"12"};//职称
    private String []c34 = {"13"};//培训证书*/
    private String [] c32 = {"35","36"};//学历
    private String [] c33 = {"37","38"};//职称
    private String [] c34 = {"39","40"};//培训

    private String []c35 = {"10"};//工作经历
    private String []c36 = {"14","15","16","17","18","19","20","21","22","23","24","25","26","27"};//项目业绩

    //1.不予登记的情形的审查：下拉框
    //2.劳动（聘用）合同
    //3.养老保险证明
    //继续登记
    private String []c41 = {"1","4","5"};//个人信息
    private String []c42 = {"3"};//继续教育
    private String []c43 = {"6","7","8","9"};//特殊政策


    @RequestMapping(value = "addCheck")
    public void addCheck(String recordId,String type) {//recordId 为表单id , type 3为初审,4为终审
        //1.查看表单中是否存在,如果存在就直接return;
       int a = personExpertService.findSame(recordId,type);
       if(a>0){
           return;
       }
        Counselor counselor = counselorService.get(recordId);
        String declareType = counselor.getDeclareType();
        Examine examine = null;
        CounselorTable table = null;
        if(declareType.equals("1")){
            examine = systemService.getName("初始登记");
        }else if(declareType.equals("2")){
            examine = systemService.getName("变更执业单位");
        }else if(declareType.equals("3")){
            examine = systemService.getName("变更专业");

        }else if(declareType.equals("4")){
            examine = systemService.getName("继续登记");
        }else{
            return ;
        }
        //查询下面所有子类
        List<Examine> examines = systemService.findExamineByParentId(examine.getId());
        for(Examine e : examines){
            String examineId = e.getId();
            String resultType = e.getResultType();
            if(resultType!=null&&!resultType.equals("")){//用type 设置该项需要增加的内容.
                /*PersonExpert personExpert = new PersonExpert();
                personExpert.setRecordId(recordId);
                personExpert.setExamineType(type);
                List<PersonExpert> list = personExpertService.findList(personExpert);
                if(list!=null&&list.size()>0){
                    return;
                }*/
                addCheckMethod(resultType,examineId,recordId,type);
            }
        }
    }
    public void addCheckMethod(String type,String examineId,String recordId,String examineType){
            if(type.equals("11")){
                addCheckMethod1(examineId,examineType,recordId,c11);
            }else if(type.equals("12")){
                addCheckMethod3(examineId,examineType,recordId,c12,"Educationtbl");
            }else if(type.equals("13")){
                addCheckMethod3(examineId,examineType,recordId,c13,"TitleCertificate");
            }else if(type.equals("14")){
                addCheckMethod3(examineId,examineType,recordId,c14,"SpecialtyTrain");
            }else if(type.equals("15")){
                addCheckMethod1(examineId,examineType,recordId,c15);
            }else if(type.equals("16")){
                //11.15
                Counselor counselor = counselorService.get(recordId);
                String declareType = counselor.getDeclareType();
                if(declareType!=null&&declareType.equals("1")){
                    //根据record进行查询,是否符合第五条,如果不符合
                    String isFifth = counselorService.isFifth(recordId);
                    if(isFifth!=null&&isFifth.equals("0")){
                        //初始登记的基本情况里有个是否符合第五条的  是和否，如果选的是否，专家的审查项里生成数据的时候不生成  跟第五条（特殊政策）有关系的审查项
                    }else {
                        addCheckMethod1(examineId, examineType, recordId, c16);
                    }
                }else{
                    addCheckMethod1(examineId, examineType, recordId, c16);
                }

            }else if(type.equals("17")){
                addCheckMethod1(examineId,examineType,recordId,c17);
            }else if(type.equals("21")){
               addCheckMethod1(examineId,examineType,recordId,c21);
            }else if(type.equals("22")){
              addCheckMethod1(examineId,examineType,recordId,c22);
            }else if(type.equals("23")){
              addCheckMethod1(examineId,examineType,recordId,c23);
            }else if(type.equals("31")){
              addCheckMethod1(examineId,examineType,recordId,c31);
            }else if(type.equals("32")){
                addCheckMethod3(examineId,examineType,recordId,c32,"Educationtbl");
            }else if(type.equals("33")){
                addCheckMethod3(examineId,examineType,recordId,c33,"TitleCertificate");
            }else if(type.equals("34")){
                addCheckMethod3(examineId,examineType,recordId,c14,"SpecialtyTrain");
            }else if(type.equals("35")){
                addCheckMethod1(examineId,examineType,recordId,c35);
            }else if(type.equals("36")){
                //11.20 查看是否有业绩的表单,根据 recordid 与 type = '6'
                CounselorTable table = counselorTableService.getNew(recordId, "6");
                //table 存在就添加审查项
                if(table!=null&&!table.equals("")){
                    addCheckMethod1(examineId,examineType,recordId,c36);
                }
            }else if(type.equals("41")){
                addCheckMethod1(examineId,examineType,recordId,c41);
            }else if(type.equals("42")){
                addCheckMethod1(examineId,examineType,recordId,c42);
            }else if(type.equals("43")){
                addCheckMethod1(examineId,examineType,recordId,c43);
            }else if(type.equals("0")){
                addCheckMethod1(examineId,examineType,recordId,c0);
            }
    }

    public void addCheckMethod1(String examineId,String examineType,String recordId,String [] ss){
        for(String s: ss){
        PersonExpert personExpert = new PersonExpert();
        personExpert.setExamineId(examineId);
        personExpert.setExamineType(examineType);
        personExpert.setRecordId(recordId);
        personExpert.setType(s);
        personExpertService.save(personExpert);
        }
    }

    public void addCheckMethod2(String examineId,String examineType,String recordId,String [] ss,String object){
        CounselorTable counselorTable = new CounselorTable();
        counselorTable.setPersonRecordId(recordId);
        counselorTable.setType("1");
        CounselorTable table = counselorTableService.findList(counselorTable).get(0);
        if(object.equals("Educationtbl")){//判断属于哪部分
            //先查询出list
            List<Educationtbl> list = educationtblService.getByTableId(table.getId());
            if(list==null||list.size()==0){//当没有查询到数据的时候,表示没填.但还是得增加一条选项
                addCheckMethod1(examineId,examineType,recordId,ss);
            }else {
                for (Educationtbl e : list) {
                    for (String s : ss) {
                        PersonExpert personExpert = new PersonExpert();
                        personExpert.setExamineId(examineId);
                        personExpert.setExamineType(examineType);
                        personExpert.setRecordId(recordId);
                        personExpert.setType(s);
                        personExpert.setIndex(e.getIndex());
                        personExpertService.save(personExpert);
                        String id = personExpert.getId();
                        if (s.equals("35")) {//需要把主辅专业放进去>
                            //主
                            e.setMain(id);
                        } else {//辅
                            e.setAssist(id);
                        }
                        educationtblService.save(e);
                    }
                }
            }
        }else if(object.equals("TitleCertificate")){
            List<TitleCertificate> list = titleCertificateService.getByTableId(table.getId());
            if(list==null||list.size()==0){//当没有查询到数据的时候,表示没填.但还是得增加一条选项
                addCheckMethod1(examineId,examineType,recordId,ss);
            }else {
                for (TitleCertificate t : list) {
                    for (String s : ss) {
                        PersonExpert personExpert = new PersonExpert();
                        personExpert.setExamineId(examineId);
                        personExpert.setExamineType(examineType);
                        personExpert.setRecordId(recordId);
                        personExpert.setType(s);
                        personExpert.setIndex(t.getIndex());
                        personExpertService.save(personExpert);
                        String id = personExpert.getId();
                        if (s.equals("37")) {//需要把主辅专业放进去>
                            //主
                            t.setMain(id);
                        } else {//辅
                            t.setAssist(id);
                        }
                        titleCertificateService.save(t);
                    }
                }
            }
        }else if(object.equals("SpecialtyTrain")){
            List<SpecialtyTrain> list = specialtyTrainService.getByTableId(table.getId());
            if(list==null||list.size()==0){//当没有查询到数据的时候,表示没填.但还是得增加一条选项
                addCheckMethod1(examineId,examineType,recordId,ss);
            }else {
                for (SpecialtyTrain st : list) {
                    for (String s : ss) {
                        PersonExpert personExpert = new PersonExpert();
                        personExpert.setExamineId(examineId);
                        personExpert.setExamineType(examineType);
                        personExpert.setRecordId(recordId);
                        personExpert.setType(s);
                        personExpert.setIndex(st.getIndex());
                        personExpertService.save(personExpert);
                        String id = personExpert.getId();
                        if (s.equals("39")) {//需要把主辅专业放进去>
                            //主
                            st.setMain(id);
                        } else {//辅
                            st.setAssist(id);
                        }
                        specialtyTrainService.save(st);
                    }
                }
            }
        }

    }



    //  12.20
    // 添加选项的方式. 学历,职称,培训 都是根据personId 查询的
    public void addCheckMethod3(String examineId,String examineType,String recordId,String [] ss,String object){
        Counselor counselor = counselorService.get(recordId);
        String personId = counselor.getPersonId();

        if(object.equals("Educationtbl")){//判断属于哪部分
            Educationtbl educationtbl = new Educationtbl();
            educationtbl.setPersonId(personId);
            //先查询出list
            List<Educationtbl> list = educationtblService.findList(educationtbl);
            //List<Educationtbl> list = educationtblService.getByTableId(table.getId());
            if(list==null||list.size()==0){//当没有查询到数据的时候,表示没填.但还是得增加一条选项
                addCheckMethod1(examineId,examineType,recordId,ss);
            }else {
                for (Educationtbl e : list) {
                    for (String s : ss) {
                        PersonExpert personExpert = new PersonExpert();
                        personExpert.setExamineId(examineId);
                        personExpert.setExamineType(examineType);
                        personExpert.setRecordId(recordId);
                        personExpert.setType(s);
                        personExpert.setIndex(e.getIndex());
                        personExpertService.save(personExpert);
                        String id = personExpert.getId();
                        if (s.equals("35")) {//需要把主辅专业放进去>
                            //主
                            e.setMain(id);
                        } else {//辅
                            e.setAssist(id);
                        }
                        educationtblService.save(e);
                    }
                }
            }
        }else if(object.equals("TitleCertificate")){
            TitleCertificate titleCertificate = new TitleCertificate();
            titleCertificate.setPersonId(personId);
            List<TitleCertificate> list = titleCertificateService.findList(titleCertificate);
            //List<TitleCertificate> list = titleCertificateService.getByTableId(table.getId());
            if(list==null||list.size()==0){//当没有查询到数据的时候,表示没填.但还是得增加一条选项
                addCheckMethod1(examineId,examineType,recordId,ss);
            }else {
                for (TitleCertificate t : list) {
                    for (String s : ss) {
                        PersonExpert personExpert = new PersonExpert();
                        personExpert.setExamineId(examineId);
                        personExpert.setExamineType(examineType);
                        personExpert.setRecordId(recordId);
                        personExpert.setType(s);
                        personExpert.setIndex(t.getIndex());
                        personExpertService.save(personExpert);
                        String id = personExpert.getId();
                        if (s.equals("37")) {//需要把主辅专业放进去>
                            //主
                            t.setMain(id);
                        } else {//辅
                            t.setAssist(id);
                        }
                        titleCertificateService.save(t);
                    }
                }
            }
        }else if(object.equals("SpecialtyTrain")){
            SpecialtyTrain specialtyTrain = new SpecialtyTrain();
            specialtyTrain.setPersonId(personId);
            List<SpecialtyTrain> list = specialtyTrainService.findList(specialtyTrain);
            //List<SpecialtyTrain> list = specialtyTrainService.getByTableId(table.getId());
            if(list==null||list.size()==0){//当没有查询到数据的时候,表示没填.但还是得增加一条选项
                addCheckMethod1(examineId,examineType,recordId,ss);
            }else {
                for (SpecialtyTrain st : list) {
                    for (String s : ss) {
                        PersonExpert personExpert = new PersonExpert();
                        personExpert.setExamineId(examineId);
                        personExpert.setExamineType(examineType);
                        personExpert.setRecordId(recordId);
                        personExpert.setType(s);
                        personExpert.setIndex(st.getIndex());
                        personExpertService.save(personExpert);
                        String id = personExpert.getId();
                        if (s.equals("39")) {//需要把主辅专业放进去>
                            //主
                            st.setMain(id);
                        } else {//辅
                            st.setAssist(id);
                        }
                        specialtyTrainService.save(st);
                    }
                }
            }
        }

    }


    @RequestMapping(value = "saveExpert")
    public String saveExpert(PersonExpert personExpert){
        List<PersonExpert> expertList = personExpert.getExpertList();
        //不为空的时候执行
       if(expertList!=null&&expertList.size()>0){
           //flag用于存储 type为6的值.
           String  flag = null;
            for(PersonExpert p :expertList){
                //增加判断,当type 为6 的时候,他的item为0 ,7,8,9的items 都为0
                if(p.getType().equals("6")&&p.getItems().equals("0")){//如果 type6 的items为0 ,将flag 设置为1
                        flag = "1";
                }
                if(flag!=null&&flag.equals("1")){
                    if(p.getType().equals("7")||p.getType().equals("8")||p.getType().equals("9")){//当为7 8 9 的时候,把items设置为0
                        p.setItems("0");
                        p.setExamineType(personExpert.getExamineType());
                        p.setExamineId(personExpert.getExamineId());
                        p.setRecordId(personExpert.getRecordId());
                        personExpertService.save(p);
                    }else{
                        p.setExamineType(personExpert.getExamineType());
                        p.setExamineId(personExpert.getExamineId());
                        p.setRecordId(personExpert.getRecordId());
                        personExpertService.save(p);
                    }
                }else {
                    p.setExamineType(personExpert.getExamineType());
                    p.setExamineId(personExpert.getExamineId());
                    p.setRecordId(personExpert.getRecordId());
                    personExpertService.save(p);
                }
            }
        }
        return "redirect:"+adminPath+"/counselor/examine/jump?id="+personExpert.getExamineId()+"&recordId="+personExpert.getRecordId()+"&type="+personExpert.getExamineType();
    }

    @RequestMapping(value = "savePersonExpert")
    public String savePersonExpert(PersonExpert personExpert,String recordId,String examineId,String type){
        List<PersonExpert> expertList = personExpert.getExpertList();
        if(expertList!=null&&expertList.size()>0){
            for(PersonExpert p : expertList){
                personExpertService.save(p);
            }
        }
        return "redirect:"+adminPath+"/counselor/examine/jump?id="+examineId+"&recordId="+recordId+"&type="+type;
    }
}
