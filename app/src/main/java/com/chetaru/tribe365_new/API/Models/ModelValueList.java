package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@SuppressWarnings("unused")
public class ModelValueList implements Serializable {

    String previousSelectedId = "";
    Boolean isChecked = false;
    String isCheckedString = "";
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;

    public String getPreviousSelectedId() {
        return previousSelectedId;
    }

    public void setPreviousSelectedId(String previousSelectedId) {
        this.previousSelectedId = previousSelectedId;
    }

    public String getIsCheckedString() {
        return isCheckedString;
    }

    public void setIsCheckedString(String isCheckedString) {
        this.isCheckedString = isCheckedString;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
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

}
