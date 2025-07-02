package com.chetaru.tribe365_new.API.Models.KnowHome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KudosAwardResponse {
    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("service_name")
    @Expose
    public String serviceName;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<KudosAwardList> kudosAwardLists = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<KudosAwardList> getKudosAwardLists() {
        return kudosAwardLists;
    }

    public void setKudosAwardLists(List<KudosAwardList> kudosAwardLists) {
        this.kudosAwardLists = kudosAwardLists;
    }
}
