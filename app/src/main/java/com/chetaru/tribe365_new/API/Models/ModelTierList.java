package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ModelTierList {

    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
