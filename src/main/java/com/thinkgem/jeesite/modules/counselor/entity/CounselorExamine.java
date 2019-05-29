package com.thinkgem.jeesite.modules.counselor.entity;
//咨询师审查树的实体

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CounselorExamine extends DataEntity<CounselorExamine> {
    private static final long serialVersionUID = 1L;
    private CounselorExamine parent;	// 父级菜单
    private String parentIds; // 所有父级编号
    private String name; 	// 名称
    private String href; 	// 链接
    private String target; 	// 目标（ mainFrame、_blank、_self、_parent、_top）
    private String icon; 	// 图标
    private Integer sort; 	// 排序
    private String isShow; 	// 是否在菜单中显示（1：显示；0：不显示）
    private String permission; // 权限标识
    private String userId;

    public CounselorExamine(){
        super();
        this.sort = 30;
        this.isShow = "1";
    }

    public CounselorExamine(String id){
        super(id);
    }

    @JsonBackReference
    @NotNull
    public CounselorExamine getParent() {
        return parent;
    }

    public void setParent(CounselorExamine parent) {
        this.parent = parent;
    }

    @Length(min=1, max=2000)
    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Length(min=1, max=100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min=0, max=2000)
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Length(min=0, max=20)
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Length(min=0, max=100)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @NotNull
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Length(min=1, max=1)
    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    @Length(min=0, max=200)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parent != null && parent.getId() != null ? parent.getId() : "0";
    }

    @JsonIgnore
    public static void sortList(List<CounselorExamine> list, List<CounselorExamine> sourcelist, String parentId, boolean cascade){
        for (int i=0; i<sourcelist.size(); i++){
            CounselorExamine e = sourcelist.get(i);
            if (e.getParent()!=null && e.getParent().getId()!=null
                    && e.getParent().getId().equals(parentId)){
                list.add(e);
                if (cascade){
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j=0; j<sourcelist.size(); j++){
                        CounselorExamine child = sourcelist.get(j);
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
}
