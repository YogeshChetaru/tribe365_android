package com.chetaru.tribe365_new.API.Models.HPTM;

import java.util.List;

public class HPTMLearningCheckList {
    String HeadingTitle;
    List<PrincipleFirst> learningList;
    boolean isSelected;

    public String getHeadingTitle() {
        return HeadingTitle;
    }

    public void setHeadingTitle(String headingTitle) {
        HeadingTitle = headingTitle;
    }

    public List<PrincipleFirst> getLearningList() {
        return learningList;
    }

    public void setLearningList(List<PrincipleFirst> learningList) {
        this.learningList = learningList;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
