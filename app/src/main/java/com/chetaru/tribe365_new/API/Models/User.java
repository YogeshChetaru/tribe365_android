package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;

//using it in action dialog only
@SuppressWarnings("unused")
public class User {

    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    private boolean isSelected = false;

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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
