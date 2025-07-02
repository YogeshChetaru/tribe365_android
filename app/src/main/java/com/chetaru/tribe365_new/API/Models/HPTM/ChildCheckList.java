package com.chetaru.tribe365_new.API.Models.HPTM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildCheckList {
    @SerializedName("checklistId")
    @Expose
    private Integer checklistId;
    @SerializedName("principleId")
    @Expose
    private Integer principleId;
    @SerializedName("typeId")
    @Expose
    private Integer typeId;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("document")
    @Expose
    private String document;
    @SerializedName("checklistTitle")
    @Expose
    private String checklistTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("learningTypeTitle")
    @Expose
    private String learningTypeTitle;
    @SerializedName("userReadChecklist")
    @Expose
    private Boolean userReadChecklist;

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public Integer getPrincipleId() {
        return principleId;
    }

    public void setPrincipleId(Integer principleId) {
        this.principleId = principleId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getChecklistTitle() {
        return checklistTitle;
    }

    public void setChecklistTitle(String checklistTitle) {
        this.checklistTitle = checklistTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLearningTypeTitle() {
        return learningTypeTitle;
    }

    public void setLearningTypeTitle(String learningTypeTitle) {
        this.learningTypeTitle = learningTypeTitle;
    }

    public Boolean getUserReadChecklist() {
        return userReadChecklist;
    }

    public void setUserReadChecklist(Boolean userReadChecklist) {
        this.userReadChecklist = userReadChecklist;
    }
}
