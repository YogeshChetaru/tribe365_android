package com.chetaru.tribe365_new.KotlinCode.model.home

import com.chetaru.tribe365_new.API.Models.Home.HomeBelief
import com.chetaru.tribe365_new.API.Models.Home.LatestKudoAward
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HomeDetailResponse(
    @SerializedName("dotId")
    @Expose
    private var dotId: String? = null,
    @SerializedName("vision")
    @Expose
    private val vision: String? = null,

    @SerializedName("visionUrl")
    @Expose
    private val visionUrl: String? = null,

    @SerializedName("visionDesc")
    @Expose
    private val visionDesc: String? = null,

    @SerializedName("userGivenfeedback")
    @Expose
    private val userGivenfeedback: Boolean = false,

    @SerializedName("todayEIScore")
    @Expose
    private val todayEIScore: String? = null,

    @SerializedName("belief")
    @Expose
    private val belief: List<HomeBelief>?= null,

    @SerializedName("appPaymentVersion")
    @Expose
    private var appPaymentVersion: Int? = null,

    @SerializedName("badDayOffload")
    @Expose
    private val badDayOffload: Boolean? = null,

    @SerializedName("kudoAwardKey")
    @Expose
    private val kudoAwardKey: String? = null,

    @SerializedName("kudoAwardValue")
    @Expose
    private val kudoAwardValue: Int? = null,

    @SerializedName("latestKudoAward")
    @Expose
    var latestKudosAward: LatestKudoAward? = null,

    @SerializedName("leaveStatus")
    @Expose
    private val leaveStatus: Int? = null,

    @SerializedName("latestKudosAward")
    @Expose
    var latestKAward: List<LatestKudoAward>? = null,
)
