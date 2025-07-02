package com.chetaru.tribe365_new.API.Models.COTBeans;


import com.google.gson.annotations.SerializedName;

public class Option {

    @SerializedName("initialValue")
    private String mInitialValue;
    @SerializedName("OptionId")
    private Long mOptionId;
    @SerializedName("optionName")
    private String mOptionName;
    @SerializedName("valueName")
    private String mValueName;
    @SerializedName("ischeck")
    private boolean isCheck = false;

    public Option(String mInitialValue, Long mOptionId, String mOptionName, String mValueName, boolean isCheck) {
        this.mInitialValue = mInitialValue;
        this.mOptionId = mOptionId;
        this.mOptionName = mOptionName;
        this.mValueName = mValueName;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getInitialValue() {
        return mInitialValue;
    }

    public void setInitialValue(String initialValue) {
        mInitialValue = initialValue;
    }

    public Long getOptionId() {
        return mOptionId;
    }

    public void setOptionId() {
        mOptionId = mOptionId;
    }

    public String getOptionName() {
        return mOptionName;
    }

    public void setOptionName(String optionName) {
        mOptionName = optionName;
    }

    public String getValueName() {
        return mValueName;
    }

    public void setValueName(String valueName) {
        mValueName = valueName;
    }


}
