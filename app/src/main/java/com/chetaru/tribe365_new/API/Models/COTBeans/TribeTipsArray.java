package com.chetaru.tribe365_new.API.Models.COTBeans;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class TribeTipsArray {

    @SerializedName("PersuadeArray")
    private List<PersuadeArray> mPersuadeArray;
    @SerializedName("seekArray")
    private List<SeekArray> mSeekArray;
    @SerializedName("summary")
    private String mSummary;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("value")
    private String mValue;

    public List<PersuadeArray> getPersuadeArray() {
        return mPersuadeArray;
    }

    public void setPersuadeArray(List<PersuadeArray> persuadeArray) {
        mPersuadeArray = persuadeArray;
    }

    public List<SeekArray> getSeekArray() {
        return mSeekArray;
    }

    public void setSeekArray(List<SeekArray> seekArray) {
        mSeekArray = seekArray;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

}
