package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.SerializedName;


public class Option {

    boolean flag = false;
    String oldValue = "";
    String newValue = "";
    @SerializedName("option")
    private String mOption;
    @SerializedName("OptionId")
    private Long mOptionId;
    @SerializedName("roleMapId")
    private String mRoleMapId;
    @SerializedName("answer")
    private String mAnswer = "";
    @SerializedName("qusId")
    private String mQusId = "";
    @SerializedName("alphabate")
    private String mAlphabate = "";

    public Option() {
    }

    public Option(String mOption, Long mOptionId, String mRoleMapId, String mAlphabate, String mAnswer) {
        this.mOption = mOption;
        this.mOptionId = mOptionId;
        this.mRoleMapId = mRoleMapId;
        this.mAlphabate = mAlphabate;
        this.mAnswer = mAnswer;
    }

    public Option(String mOption, Long mOptionId, String mRoleMapId, String mAlphabate) {
        this.mOption = mOption;
        this.mOptionId = mOptionId;
        this.mRoleMapId = mRoleMapId;
        this.mAlphabate = mAlphabate;

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getOldValue() {
        return oldValue;
    }

//    public Option(String mOption, Long mOptionId, String mRoleMapId, String mAnswer, String mQusId) {
//        this.mOption = mOption;
//        this.mOptionId = mOptionId;
//        this.mRoleMapId = mRoleMapId;
//        this.mAnswer = mAnswer;
//        this.mQusId = mQusId;
//    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getmOption() {
        return mOption;
    }

    public void setmOption(String mOption) {
        this.mOption = mOption;
    }

    public Long getmOptionId() {
        return mOptionId;
    }

    public void setmOptionId(Long mOptionId) {
        this.mOptionId = mOptionId;
    }

    public String getmRoleMapId() {
        return mRoleMapId;
    }

    public void setmRoleMapId(String mRoleMapId) {
        this.mRoleMapId = mRoleMapId;
    }

    public String getmQusId() {
        return mQusId;
    }

    public void setmQusId(String mQusId) {
        this.mQusId = mQusId;
    }

    public String getmAnswer() {
        return mAnswer;
    }

    public void setmAnswer(String mAnswer) {
        this.mAnswer = mAnswer;
    }

    public String getOption() {
        return mOption;
    }

    public void setOption(String option) {
        mOption = option;
    }

    public Long getOptionId() {
        return mOptionId;
    }

    public void setOptionId(Long OptionId) {
        mOptionId = OptionId;
    }

    public String getRoleMapId() {
        return mRoleMapId;
    }

    public void setRoleMapId(String roleMapId) {
        mRoleMapId = roleMapId;
    }

    public String getmAlphabate() {
        return mAlphabate;
    }

    public void setmAlphabate(String mAlphabate) {
        this.mAlphabate = mAlphabate;
    }
}
