package com.thinkgem.jeesite.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

public class Examine extends DataEntity<Examine> {
    //咨询师审查用此类的属性定义
    //1 .result 用于设置是否最低级,不是最低级设置为1, 跳转到图片页面
    //2. kind 用于设置文字. 不同的低级展示不同的文字. 1.基本信息 2.~~~
    //3. kindOf  用于设置页面的选项列表. 不同的kindof展示不同的选项 使用 s11~~~{"1","2"}~~
    //4.type 用于展示图片. type 展示不同的types ["1","2"]~~~
    private String name; 	// 名称
    private String href; 	// 链接
    private String target; 	// 目标（ mainFrame、_blank、_self、_parent、_top）
    private String icon; 	// 图标
    private Integer sort; 	// 排序
    private String isShow; 	// 是否在菜单中显示（1：显示；0：不显示）
    private String permission; // 权限标识
    private String rejectType ;//驳回原因的类型

    private String userId;
    private String kind;    //类型, 1 展示的是基本信息以及专业技术力量,2,展示的是 信用, 3展示的是业绩

    private String kindOf;  //类型 .例如 当点击的是基本类型的项时,1.展示图片列表   当点击的是专业力量时 2.展示人员列表  当点击的是 业绩的时候,3 展示的是project列表
    public String getKindOf() {
        return kindOf;
    }

    public void setKindOf(String kindOf) {
        this.kindOf = kindOf;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type; //用于存储附件的类型,. 每一个项都对应着一个type



    public Examine(){
        super();
        this.sort = 30;
        this.isShow = "1";
    }
    public Examine(String id){
        super(id);
    }
    //预计值
    private String predict;

    public String getRealValue() {
        return realValue;
    }

    public void setRealValue(String realValue) {
        this.realValue = realValue;
    }

    //实际值
    private String realValue;
    //结果类型
    private String resultType;
    //结果
    private String result;

    public Integer getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(Integer applyNo) {
        this.applyNo = applyNo;
    }

    //申请单号
    private Integer applyNo;

    public Examine getParent() {
        return parent;
    }

    public void setParent(Examine parent) {
        this.parent = parent;
    }

    private Examine parent;	// 父级菜单
    private String parentIds; // 所有父级编号



    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPredict() {
        return predict;
    }

    public void setPredict(String predict) {
        this.predict = predict;
    }


    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }


    @JsonIgnore
    public static void sortList(List<Examine> list, List<Examine> sourcelist, String parentId, boolean cascade){
        for (int i=0; i<sourcelist.size(); i++){
            Examine e = sourcelist.get(i);
            if (e.getParent()!=null && e.getParent().getId()!=null
                    && e.getParent().getId().equals(parentId)){
                list.add(e);
                if (cascade){
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j=0; j<sourcelist.size(); j++){
                        Examine child = sourcelist.get(j);
                        if (child.getParent()!=null && child.getParent().getId()!=null
                                && child.getParent().getId().equals(e.getId())){
                            sortList(list, sourcelist, e.getId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }


    /*
    * 找到最低端的子节点, 查找方法,通过遍历,查询是否有对象的parentid 有 此对象的id
    * */
    /*@JsonIgnore*/
    /*public static void findLastNode(List<Examine>lastList,List<Examine> sourceList,String RootId){
        //用来存放最后节点

        //循环原列表
        for(int i = 0 ;i < sourceList.size();i++){
            //拿出每个对象
            Examine e = sourceList.get(i);
            for(int j = 0; j <sourceList.size();j++){
                //判断此对象的id 是否与其他对象的父id相等,如果相等就跳出循环,不相等由list加上
                System.out.println("此对象的id为:"+e.getId()+"--- 对比对象的父id为:"+sourceList.get(j).getParentId());
                if(e.getId().equals(sourceList.get(j).getParentId())){
                    break;
                }
            }
            //如果循环结束还没找到,那么他就是最低级的节点
            lastList.add(e);
        }
    }*/



    public String getParentId() {
        return parent != null && parent.getId() != null ? parent.getId() : "0";
    }


    @JsonIgnore
    public static String getRootId(){
        return "1";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return name;
    }
	public String getRejectType() {
		return rejectType;
	}
	public void setRejectType(String rejectType) {
		this.rejectType = rejectType;
	}

}
