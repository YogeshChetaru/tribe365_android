package com.chetaru.tribe365_new.UI.Know.COT;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ModelOfficeDepartment {

    @SerializedName("department")
    private List<Department> mDepartment;
    @SerializedName("office")
    private String mOffice;
    @SerializedName("officeId")
    private Long mOfficeId;

    public List<Department> getDepartment() {
        return mDepartment;
    }

    public void setDepartment(List<Department> department) {
        mDepartment = department;
    }

    public String getOffice() {
        return mOffice;
    }

    public void setOffice(String office) {
        mOffice = office;
    }

    public Long getOfficeId() {
        return mOfficeId;
    }

    public void setOfficeId(Long officeId) {
        mOfficeId = officeId;
    }

}
