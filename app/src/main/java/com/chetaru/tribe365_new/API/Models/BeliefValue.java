package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class BeliefValue {

    String previousSelectedId = "";
    Boolean isChecked = false;
    String isCheckedString = "";
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("valueId")
    private String mValueId;
    @SerializedName("beliefId")
    private Long mBeliefId;
    @SerializedName("dotId")
    private String mDotId;
    @SerializedName("ratings")
    private String mRatings = "";
    @SerializedName("valueUrl")
    @Expose
    private String valueUrl;
    @SerializedName("valueDesc")
    @Expose
    private String valueDesc;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPreviousSelectedId() {
        return previousSelectedId;
    }

    public void setPreviousSelectedId(String previousSelectedId) {
        this.previousSelectedId = previousSelectedId;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getIsCheckedString() {
        return isCheckedString;
    }

    public void setIsCheckedString(String isCheckedString) {
        this.isCheckedString = isCheckedString;
    }


    public String getValueId() {
        return mValueId;
    }

    public void setValueId(String mValueId) {
        this.mValueId = mValueId;
    }

    public String getRatings() {
        return mRatings;
    }

    public void setRatings(String mRatings) {
        this.mRatings = mRatings;
    }


    public String getDotId() {
        return mDotId;
    }

    public void setDotId(String mDotId) {
        this.mDotId = mDotId;
    }

    public Long getBeliefId() {
        return mBeliefId;
    }

    public void setBeliefId(Long beliefId) {
        mBeliefId = beliefId;
    }


    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getValueUrl() {
        return valueUrl;
    }

    public void setValueUrl(String valueUrl) {
        this.valueUrl = valueUrl;
    }

    public String getValueDesc() {
        return valueDesc;
    }

    public void setValueDesc(String valueDesc) {
        this.valueDesc = valueDesc;
    }
}
