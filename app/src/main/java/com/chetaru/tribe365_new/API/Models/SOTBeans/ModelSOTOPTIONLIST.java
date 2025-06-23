package com.chetaru.tribe365_new.API.Models.SOTBeans;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ModelSOTOPTIONLIST implements Serializable {

    boolean flag = false;
    @SerializedName("id")
    private String mId;
    @SerializedName("question")
    private String mQuestion;
    @SerializedName("section")
    private String mSection;
    @SerializedName("type")
    private String mType;
    @SerializedName("ischeck")
    private boolean isCheck = false;

    public ModelSOTOPTIONLIST(String mId, String mQuestion, String mSection, String mType) {
        this.mId = mId;
        this.mQuestion = mQuestion;
        this.mSection = mSection;
        this.mType = mType;
    }

    public ModelSOTOPTIONLIST(String mId, String mQuestion, String mSection, String mType, boolean isCheck) {
        this.mId = mId;
        this.mQuestion = mQuestion;
        this.mSection = mSection;
        this.mType = mType;
        this.isCheck = isCheck;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getId() {
        return mId;
    }

    public String setId(String id) {
        return id;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
