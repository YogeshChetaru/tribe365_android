package com.chetaru.tribe365_new.API.Models;


import com.chetaru.tribe365_new.API.Models.MemberHome.PersonalityType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class ModelUserDetails {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("officeId")
    @Expose
    private Integer officeId;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userContact")
    @Expose
    private String userContact;
    @SerializedName("orgId")
    @Expose
    private Integer orgId;
    @SerializedName("organisationName")
    @Expose
    private String organisationName;
    @SerializedName("officeName")
    @Expose
    private String officeName;
    @SerializedName("departmentName")
    @Expose
    private String departmentName;
    @SerializedName("personaliseData")
    @Expose
    private Integer personaliseData;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;
    @SerializedName("organisation_logo")
    @Expose
    private String organisationLogo;
    @SerializedName("cotTeamRoleMap")
    @Expose
    private String cotTeamRoleMap;
    @SerializedName("sotDetail")
    @Expose
    private String sotDetail;
    @SerializedName("sotMotivationDetail")
    @Expose
    private String sotMotivationDetail;
    @SerializedName("cotTeamRoleMapArr")
    @Expose
    private List<String> cotTeamRoleMapArr = null;
    @SerializedName("perTypeStatus")
    @Expose
    private Boolean perTypeStatus;
    @SerializedName("personalityTypeDetails")
    @Expose
    private String personalityTypeDetails;
    @SerializedName("personalityTypeDetailsArr")
    @Expose
    private List<PersonalityType> personalityArr = null;
    @SerializedName("sotMotivationDetailArr")
    @Expose
    private List<String> sotMotivationDetailArr = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getPersonaliseData() {
        return personaliseData;
    }

    public void setPersonaliseData(Integer personaliseData) {
        this.personaliseData = personaliseData;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getOrganisationLogo() {
        return organisationLogo;
    }

    public void setOrganisationLogo(String organisationLogo) {
        this.organisationLogo = organisationLogo;
    }

    public String getCotTeamRoleMap() {
        return cotTeamRoleMap;
    }

    public void setCotTeamRoleMap(String cotTeamRoleMap) {
        this.cotTeamRoleMap = cotTeamRoleMap;
    }

    public String getSotDetail() {
        return sotDetail;
    }

    public void setSotDetail(String sotDetail) {
        this.sotDetail = sotDetail;
    }

    public String getSotMotivationDetail() {
        return sotMotivationDetail;
    }

    public void setSotMotivationDetail(String sotMotivationDetail) {
        this.sotMotivationDetail = sotMotivationDetail;
    }

    public List<String> getCotTeamRoleMapArr() {
        return cotTeamRoleMapArr;
    }

    public void setCotTeamRoleMapArr(List<String> cotTeamRoleMapArr) {
        this.cotTeamRoleMapArr = cotTeamRoleMapArr;
    }

    public Boolean getPerTypeStatus() {
        return perTypeStatus;
    }

    public void setPerTypeStatus(Boolean perTypeStatus) {
        this.perTypeStatus = perTypeStatus;
    }

    public String getPersonalityTypeDetails() {
        return personalityTypeDetails;
    }

    public void setPersonalityTypeDetails(String personalityTypeDetails) {
        this.personalityTypeDetails = personalityTypeDetails;
    }



    public List<String> getSotMotivationDetailArr() {
        return sotMotivationDetailArr;
    }

    public void setSotMotivationDetailArr(List<String> sotMotivationDetailArr) {
        this.sotMotivationDetailArr = sotMotivationDetailArr;
    }

    public List<PersonalityType> getPersonalityArr() {
        return personalityArr;
    }

    public void setPersonalityArr(List<PersonalityType> personalityArr) {
        this.personalityArr = personalityArr;
    }
}
