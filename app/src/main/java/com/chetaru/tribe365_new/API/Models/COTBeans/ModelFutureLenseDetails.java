package com.chetaru.tribe365_new.API.Models.COTBeans;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class ModelFutureLenseDetails {


    @SerializedName("isCOTfunclensAnswersDone")
    private Boolean isCOTfunclensAnswersDone;
    @SerializedName("funcLensKeyDetail")
    private List<FuncLensKeyDetail> mFuncLensKeyDetail;
    @SerializedName("initialValueList")
    private List<InitialValueList> mInitialValueList;
    @SerializedName("tribeTipsArray")
    private List<TribeTipsArray> mTribeTipsArray;
    @SerializedName("valueCombination")
    private List<ValueCombination> mValueCombination;

    public List<ValueCombination> getValueCombination() {
        return mValueCombination;
    }

    public void setValueCombination(List<ValueCombination> mValueCombination) {
        this.mValueCombination = mValueCombination;
    }

    public Boolean getCOTfunclensAnswersDone() {
        return isCOTfunclensAnswersDone;
    }

    public void setCOTfunclensAnswersDone(Boolean COTfunclensAnswersDone) {
        isCOTfunclensAnswersDone = COTfunclensAnswersDone;
    }

    public List<FuncLensKeyDetail> getFuncLensKeyDetail() {
        return mFuncLensKeyDetail;
    }

    public void setFuncLensKeyDetail(List<FuncLensKeyDetail> funcLensKeyDetail) {
        mFuncLensKeyDetail = funcLensKeyDetail;
    }

    public List<InitialValueList> getInitialValueList() {
        return mInitialValueList;
    }

    public void setInitialValueList(List<InitialValueList> initialValueList) {
        mInitialValueList = initialValueList;
    }

    public List<TribeTipsArray> getTribeTipsArray() {
        return mTribeTipsArray;
    }

    public void setTribeTipsArray(List<TribeTipsArray> tribeTipsArray) {
        mTribeTipsArray = tribeTipsArray;
    }

}
