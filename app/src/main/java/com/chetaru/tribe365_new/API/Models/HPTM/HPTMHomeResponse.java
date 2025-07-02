package com.chetaru.tribe365_new.API.Models.HPTM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class HPTMHomeResponse {
   /* @SerializedName("learningCheckList")
    @Expose
    private LearningCheckList learningCheckList;*/
    @SerializedName("learningTypeArr")
    @Expose
    private List<String> learningTypeArr = null;
     boolean isSelected = false;

    @SerializedName("learningCheckList")
    @Expose
    private Map<String, List<PrincipleFirst>> learningCheckListMap;



   /* public LearningCheckList getLearningCheckList() {
        return learningCheckList;
    }

    public void setLearningCheckList(LearningCheckList learningCheckList) {
        this.learningCheckList = learningCheckList;
    }*/

    public List<String> getLearningTypeArr() {
        return learningTypeArr;
    }

    public void setLearningTypeArr(List<String> learningTypeArr) {
        this.learningTypeArr = learningTypeArr;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Map<String, List<PrincipleFirst>> getLearningCheckListMap() {
        return learningCheckListMap;
    }

    public void setLearningCheckListMap(Map<String, List<PrincipleFirst>> learningCheckListMap) {
        this.learningCheckListMap = learningCheckListMap;
    }
}
