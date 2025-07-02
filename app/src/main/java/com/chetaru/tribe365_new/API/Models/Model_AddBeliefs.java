package com.chetaru.tribe365_new.API.Models;

import java.util.ArrayList;
import java.util.List;

public class Model_AddBeliefs {

    //getSelectedValueList is for admin. in which we are getting selected values from list
    public List<ModelValueList> getSelectedValueList = new ArrayList<>();
    String beliefName = "";
    ArrayList<String> values;
    String value = "";
    String valueId = "";
    String beleifId = "";
    String isSelected = "";
    String id = "";
    String beliefUrl = "";
    String beliefDesc = "";
    ArrayList<BeliefValue> valueList = new ArrayList<>();
    ArrayList<ModelValueList> valueSelectedlsit = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getBeleifId() {
        return beleifId;
    }

    public void setBeleifId(String beleifId) {
        this.beleifId = beleifId;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public ArrayList<ModelValueList> getValueSelectedlist() {
        return valueSelectedlsit;
    }

    public void setValueSelectedlsit(ArrayList<ModelValueList> selectedValueList) {
        this.valueSelectedlsit = selectedValueList;
    }

    public List<ModelValueList> getGetSelectedValueList() {
        return getSelectedValueList;
    }

    public void setGetSelectedValueList(List<ModelValueList> getSelectedValueList) {
        this.getSelectedValueList = getSelectedValueList;
    }

    //getValueList is for user when we are showing Belief list to user at that time we will use this
    //thank me later ;)

    public List<BeliefValue> getValueList() {
        return valueList;
    }

    public void setValueList(ArrayList<BeliefValue> valueList) {
        this.valueList = valueList;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getBeliefName() {
        return beliefName;
    }

    public void setBeliefName(String beliefName) {
        this.beliefName = beliefName;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public String getBeliefUrl() {
        return beliefUrl;
    }

    public void setBeliefUrl(String beliefUrl) {
        this.beliefUrl = beliefUrl;
    }

    public String getBeliefDesc() {
        return beliefDesc;
    }

    public void setBeliefDesc(String beliefDesc) {
        this.beliefDesc = beliefDesc;
    }
}
