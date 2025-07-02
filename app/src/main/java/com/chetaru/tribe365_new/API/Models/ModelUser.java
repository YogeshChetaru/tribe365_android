package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ModelUser {


    @SerializedName("department")
    private String mDepartment;
    @SerializedName("departmentId")
    private Long mDepartmentId;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private Long mId;


    @SerializedName("departmentPreDefineId")
    private Long mDepartmentPreDefineId;
    @SerializedName("name")
    private String mName;
    @SerializedName("officeId")
    private Long mOfficeId;
    @SerializedName("orgId")
    private Long mOrgId;
    @SerializedName("organisationName")
    private String mOrganisationName;
    @SerializedName("officeName")
    private String mOfficeName;
    @SerializedName("password")
    private String mPassword;


    public Long getDepartmentPreDefineId() {
        return mDepartmentPreDefineId;
    }

    public void setDepartmentPreDefineId(Long mDepartmentPreDefineId) {
        this.mDepartmentPreDefineId = mDepartmentPreDefineId;
    }

    public String getOfficeName() {
        return mOfficeName;
    }

    public void setOfficeName(String mPassword) {
        this.mOfficeName = mPassword;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }


    public String getOrganisationName() {
        return mOrganisationName;
    }

    public void setOrganisationName(String mOrganisationName) {
        this.mOrganisationName = mOrganisationName;
    }


    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
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

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
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

}
