package com.chetaru.tribe365_new.API.Models.HPTM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LearningCheckList {
    private List<PrincipleFirst> learningList = null;

    public List<PrincipleFirst> getLearningList() {
        return learningList;
    }

    public void setLearningList(List<PrincipleFirst> learningList) {
        this.learningList = learningList;
    }
}
