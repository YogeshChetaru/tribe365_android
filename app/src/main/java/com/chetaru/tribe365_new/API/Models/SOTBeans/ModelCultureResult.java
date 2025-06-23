package com.chetaru.tribe365_new.API.Models.SOTBeans;


import java.io.Serializable;

//using it in action dialog only
@SuppressWarnings("unused")
public class ModelCultureResult implements Serializable {

    String count = "";
    String title = "";

    public ModelCultureResult(String count, String title) {
        this.count = count;
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
