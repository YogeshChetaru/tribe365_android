package com.chetaru.tribe365_new.API.Models.SOTBeans;

import com.google.gson.annotations.SerializedName;

public class Answer {

    @SerializedName("id")
    private Long mId;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

}
