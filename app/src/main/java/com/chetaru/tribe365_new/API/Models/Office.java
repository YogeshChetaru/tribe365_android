package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Office implements Serializable {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("city")
    private String mCity;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("office")
    private String mOffice;
    @SerializedName("office_id")
    private Long mOfficeId;
    @SerializedName("officeId")
    private Long mOfficeIdForUserProfile;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("numberOfEmployees")
    private String numberOfEmployees;

    public Long getmOfficeIdForUserProfile() {
        return mOfficeIdForUserProfile;
    }

    public void setmOfficeIdForUserProfile(Long mOfficeIdForUserProfile) {
        this.mOfficeIdForUserProfile = mOfficeIdForUserProfile;
    }

    public String getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(String numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
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

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

}
