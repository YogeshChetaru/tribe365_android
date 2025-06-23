package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ModelDepartmentList {

    @SerializedName("department")
    private String mDepartment;
    @SerializedName("id")
    private Long mId;
    private boolean isSelected;

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
