package com.chetaru.tribe365_new.API.Models.KnowCompany;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class GetSOTcultureStructureReport {

    @SerializedName("IsQuestionnaireAnswerFilled")
    private Boolean mIsQuestionnaireAnswerFilled;
    @SerializedName("IsUserFilledAnswer")
    private Boolean mIsUserFilledAnswer;
    @SerializedName("sotDetailArray")
    private List<Object> mSotDetailArray;
    @SerializedName("sotSummaryDetailArray")
    private List<Object> mSotSummaryDetailArray;

    public Boolean getIsQuestionnaireAnswerFilled() {
        return mIsQuestionnaireAnswerFilled;
    }

    public void setIsQuestionnaireAnswerFilled(Boolean isQuestionnaireAnswerFilled) {
        mIsQuestionnaireAnswerFilled = isQuestionnaireAnswerFilled;
    }

    public Boolean getIsUserFilledAnswer() {
        return mIsUserFilledAnswer;
    }

    public void setIsUserFilledAnswer(Boolean isUserFilledAnswer) {
        mIsUserFilledAnswer = isUserFilledAnswer;
    }

    public List<Object> getSotDetailArray() {
        return mSotDetailArray;
    }

    public void setSotDetailArray(List<Object> sotDetailArray) {
        mSotDetailArray = sotDetailArray;
    }

    public List<Object> getSotSummaryDetailArray() {
        return mSotSummaryDetailArray;
    }

    public void setSotSummaryDetailArray(List<Object> sotSummaryDetailArray) {
        mSotSummaryDetailArray = sotSummaryDetailArray;
    }

}
