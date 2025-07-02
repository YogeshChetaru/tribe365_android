package com.chetaru.tribe365_new.KotlinCode.model.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("code")
    @Expose
    private val  code: Int? = null,
    @SerializedName("status")
    @Expose
    public val status: Boolean? = false,
    @SerializedName("service_name")
    @Expose
    public  val serviceName : String? =null,
    @SerializedName("message")
    @Expose
    public val message : String? =null,
    @SerializedName("data")
    @Expose
    public var userinfo : UserInfo? =null,
)
data class UserInfo(
    @SerializedName("id")
    @Expose
    public var id:Int?= null,
    @SerializedName("name")
    @Expose
    public val name: String?=null,
    @SerializedName("lastName")
    @Expose
    public  val lastName:String?= null,
    @SerializedName("email")
    @Expose
    public val email:String?= null,
    @SerializedName("officeId")
    @Expose
    public val officeId: Int?= null,
    @SerializedName("departmentId")
    @Expose
    public val departmentId: Int?= null,
    @SerializedName("orgname")
    @Expose
    public  val orgname: String?= null,
    @SerializedName("office")
    @Expose
    public  val office: String?= null,
    @SerializedName("department")
    @Expose
    public val department: String? = null,
    @SerializedName("organisation_logo")
    @Expose
    public val organisationLogo: String? = null,
    @SerializedName("role")
    @Expose
    public val role: String? = null,
    @SerializedName("orgId")
    @Expose
    public val orgId: Int? = null,
    @SerializedName("profileImage")
    @Expose
    public val profileImage: String? =null,
    @SerializedName("token")
    @Expose
    public val token: String? = null,
    @SerializedName("isDot")
    @Expose
    public val isDot: Boolean?= false,
    @SerializedName("firstLogin")
    @Expose
    public val firstLogin: Boolean?=  false,
    @SerializedName("appPaymentVersion")
    @Expose
    public val appPaymentVersion:Int?= null
)
