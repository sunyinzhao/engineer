package com.thinkgem.jeesite.modules.counselor.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 专业关键字表
 */
public class SpecialtyConfig extends DataEntity<SpecialtyConfig> {
    private static final long serialVersionUID = 1L;
    private String specialty;
    private String specialtyLabel;

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getSpecialtyLabel() {
        return specialtyLabel;
    }

    public void setSpecialtyLabel(String specialtyLabel) {
        this.specialtyLabel = specialtyLabel;
    }
}
