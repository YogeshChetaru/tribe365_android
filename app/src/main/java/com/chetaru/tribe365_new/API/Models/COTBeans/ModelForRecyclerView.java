package com.chetaru.tribe365_new.API.Models.COTBeans;

public class ModelForRecyclerView {

    String Heading = "";
    String Description = "";
    String type = "";
    String Positives = "";
    String AllowableWeakness = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPositives() {
        return Positives;
    }

    public void setPositives(String positives) {
        Positives = positives;
    }

    public String getAllowableWeakness() {
        return AllowableWeakness;
    }

    public void setAllowableWeakness(String allowableWeakness) {
        AllowableWeakness = allowableWeakness;
    }

}
