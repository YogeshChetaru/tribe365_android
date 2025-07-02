package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;

//using it in action dialog only
@SuppressWarnings("unused")
public class Department {

    @SerializedName("department_id")
    private Long mDepartmentId;
    @SerializedName("name")
    private String mName;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Long getDepartmentId() {
        return mDepartmentId;
    }

    public void setDepartmentId(Long departmentId) {
        mDepartmentId = departmentId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
