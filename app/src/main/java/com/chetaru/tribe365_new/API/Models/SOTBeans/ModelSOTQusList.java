package com.chetaru.tribe365_new.API.Models.SOTBeans;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelSOTQusList {
    boolean flag = false;
    @SerializedName("qusno")
    private String mQusNo;
    @SerializedName("sotQusDetail")
    private List<ModelSOTOPTIONLIST> mSotQusDetails;

    public ModelSOTQusList(String mQusNo) {
        this.mQusNo = mQusNo;
    }

    public ModelSOTQusList(String mQusNo, List<ModelSOTOPTIONLIST> mSotQusDetails) {
        this.mQusNo = mQusNo;
        this.mSotQusDetails = mSotQusDetails;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<ModelSOTOPTIONLIST> getmSotQusDetails() {
        return mSotQusDetails;
    }

    public void setmSotQusDetails(List<ModelSOTOPTIONLIST> mSotQusDetails) {
        this.mSotQusDetails = mSotQusDetails;
    }

    public String getmQusNo() {
        return mQusNo;
    }

    public void setmQusNo(String mQusNo) {
        this.mQusNo = mQusNo;
    }
}
