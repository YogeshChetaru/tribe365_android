package com.chetaru.tribe365_new.API.Models.freeVersion;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalenderValue {

    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("image")
    @Expose
    private String image;

    private int drawbleImage;

    public CalenderValue(Integer value, int drawbleImage) {
        this.value = value;
        this.drawbleImage = drawbleImage;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDrawbleImage() {
        return drawbleImage;
    }

    public void setDrawbleImage(int drawbleImage) {
        this.drawbleImage = drawbleImage;
    }
}
