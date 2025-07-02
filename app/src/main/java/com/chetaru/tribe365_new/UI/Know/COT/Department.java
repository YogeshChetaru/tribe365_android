package com.chetaru.tribe365_new.UI.Know.COT;


import com.google.gson.annotations.SerializedName;

public class Department {

    @SerializedName("department")
    private String mDepartment;
    @SerializedName("id")
    private Long mId;
    @SerializedName("officeId")
    private Long mOfficeId;

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

    public Long getOfficeId() {
        return mOfficeId;
    }

    public void setOfficeId(Long officeId) {
        mOfficeId = officeId;
    }

}
