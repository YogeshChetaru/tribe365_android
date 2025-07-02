package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class ModelUserCompanyList {

    @SerializedName("address1")
    private String mAddress1;
    @SerializedName("address2")
    private Object mAddress2;
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
    @SerializedName("index")
    private int mIndex;
    @SerializedName("offices")
    private List<Office> mOffices;
    @SerializedName("organisation")
    private String mOrganisation;
    @SerializedName("organisation_id")
    private Long mOrganisationId;
    @SerializedName("reportPdfUrl")
    private String mReportPdfUrl;
    @SerializedName("organisation_logo")
    private String mOrganisationLogo;
    @SerializedName("phone")
    private Object mPhone;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("turnover")
    private Object mTurnover;

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    public String getAddress1() {
        return mAddress1;
    }

    public void setAddress1(String address1) {
        mAddress1 = address1;
    }

    public Object getAddress2() {
        return mAddress2;
    }

    public void setAddress2(Object address2) {
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

    public List<Office> getOffices() {
        return mOffices;
    }

    public void setOffices(List<Office> offices) {
        mOffices = offices;
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

    public String getReportPdfUrl() {
        return mReportPdfUrl;
    }

    public void setReportPdfUrl(String reportPdfUrl) {
        this.mReportPdfUrl = reportPdfUrl;
    }


    public Object getPhone() {
        return mPhone;
    }

    public void setPhone(Object phone) {
        mPhone = phone;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Object getTurnover() {
        return mTurnover;
    }

    public void setTurnover(Object turnover) {
        mTurnover = turnover;
    }

}
