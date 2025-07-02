package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@SuppressWarnings("unused")
public class ModelTheme implements Serializable {

    boolean isSelected = false;
    @SerializedName("id")
    private Long mId;
    @SerializedName("title")
    private String mTitle;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
