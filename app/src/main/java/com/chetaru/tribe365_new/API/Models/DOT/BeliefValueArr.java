package com.chetaru.tribe365_new.API.Models.DOT;


import com.google.gson.annotations.SerializedName;


public class BeliefValueArr {

    @SerializedName("downVotes")
    private Long mDownVotes;
    @SerializedName("id")
    private Long mId;
    @SerializedName("index")
    private Long mIndex;
    @SerializedName("name")
    private String mName;
    @SerializedName("ratings")
    private String mRatings;
    @SerializedName("upVotes")
    private Long mUpVotes;

    public Long getDownVotes() {
        return mDownVotes;
    }

    public void setDownVotes(Long downVotes) {
        mDownVotes = downVotes;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getIndex() {
        return mIndex;
    }

    public void setIndex(Long index) {
        mIndex = index;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRatings() {
        return mRatings;
    }

    public void setRatings(String ratings) {
        mRatings = ratings;
    }

    public Long getUpVotes() {
        return mUpVotes;
    }

    public void setUpVotes(Long upVotes) {
        mUpVotes = upVotes;
    }

}
