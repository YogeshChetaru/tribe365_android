package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ModelAddActionUser {

    boolean Selected = false;
    @SerializedName("departmentId")
    private Long mDepartmentId;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("name")
    private String mName;
    @SerializedName("lastName")
    private String mLastName;
    @SerializedName("officeId")
    private Long mOfficeId;
    @SerializedName("orgId")
    private Long mOrgId;
    @SerializedName("id")
    private Long mId;
    @SerializedName("roleId")
    private Long mRoleId;
    @SerializedName("status")
    private String mStatus;

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }


    public Long getDepartmentId() {
        return mDepartmentId;
    }

    public void setDepartmentId(Long departmentId) {
        mDepartmentId = departmentId;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getOfficeId() {
        return mOfficeId;
    }

    public void setOfficeId(Long officeId) {
        mOfficeId = officeId;
    }

    public Long getOrgId() {
        return mOrgId;
    }

    public void setOrgId(Long orgId) {
        mOrgId = orgId;
    }

    public Long getRoleId() {
        return mRoleId;
    }

    public void setRoleId(Long roleId) {
        mRoleId = roleId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }
}
