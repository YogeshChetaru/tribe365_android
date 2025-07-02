package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("unused")
public class ModelOrganisationList implements Serializable {

    @SerializedName("address1")
    private String mAddress1;
    @SerializedName("offices")
    private ArrayList<Office> mOffices;
    @SerializedName("address2")
    private String mAddress2;
    @SerializedName("industry")
    private String mIndustry;
    @SerializedName("isDot")
    private Boolean mIsDot;
    @SerializedName("numberOfDepartments")
    private Long mNumberOfDepartments;
    @SerializedName("numberOfEmployees")
    private Long mNumberOfEmployees;
    @SerializedName("numberOfOffices")
    private Long mNumberOfOffices;
    @SerializedName("organisation")
    private String mOrganisation;
    @SerializedName("organisation_id")
    private Long mOrganisationId;
    @SerializedName("organisation_logo")
    private String mOrganisationLogo;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("turnover")
    private String mTurnover;

    @SerializedName("name")
    private String mName;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("lead_name")
    private String mLead_name;
    @SerializedName("lead_email")
    private String mLead_email;
    @SerializedName("lead_phone")
    private String mLead_phone;


    public String getLead_name() {
        return mLead_name;
    }

    public void setLead_name(String mLead_name) {
        this.mLead_name = mLead_name;
    }

    public String getLead_email() {
        return mLead_email;
    }

    public void setLead_email(String mLead_email) {
        this.mLead_email = mLead_email;
    }

    public String getLead_phone() {
        return mLead_phone;
    }

    public void setLead_phone(String mLead_phone) {
        this.mLead_phone = mLead_phone;
    }


    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String name) {
        mEmail = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }


    public String getAddress1() {
        return mAddress1;
    }

    public void setAddress1(String address1) {
        mAddress1 = address1;
    }

    public String getAddress2() {
        return mAddress2;
    }

    public void setAddress2(String address2) {
        mAddress2 = address2;
    }

    public String getIndustry() {
        return mIndustry;
    }

    public void setIndustry(String industry) {
        mIndustry = industry;
    }

    public Boolean getIsDot() {
        return mIsDot;
    }

    public void setIsDot(Boolean isDot) {
        mIsDot = isDot;
    }

    public Long getNumberOfDepartments() {
        return mNumberOfDepartments;
    }

    public void setNumberOfDepartments(Long numberOfDepartments) {
        mNumberOfDepartments = numberOfDepartments;
    }

    public Long getNumberOfEmployees() {
        return mNumberOfEmployees;
    }

    public void setNumberOfEmployees(Long numberOfEmployees) {
        mNumberOfEmployees = numberOfEmployees;
    }

    public Long getNumberOfOffices() {
        return mNumberOfOffices;
    }

    public void setNumberOfOffices(Long numberOfOffices) {
        mNumberOfOffices = numberOfOffices;
    }

    public String getOrganisation() {
        return mOrganisation;
    }

    public void setOrganisation(String organisation) {
        mOrganisation = organisation;
    }

    public Long getOrganisationId() {
        return mOrganisationId;
    }

    public void setOrganisationId(Long organisationId) {
        mOrganisationId = organisationId;
    }

    public String getOrganisationLogo() {
        return mOrganisationLogo;
    }

    public void setOrganisationLogo(String organisationLogo) {
        mOrganisationLogo = organisationLogo;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTurnover() {
        return mTurnover;
    }

    public void setTurnover(String turnover) {
        mTurnover = turnover;
    }

    public ArrayList<Office> getOffices() {
        return mOffices;
    }

    public void setOffices(ArrayList<Office> mOffices) {
        this.mOffices = mOffices;
    }

}
