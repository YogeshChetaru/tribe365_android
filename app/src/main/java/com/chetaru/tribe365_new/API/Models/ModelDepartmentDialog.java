package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class ModelDepartmentDialog {

    @SerializedName("departments")
    private List<Department> mDepartments;
    @SerializedName("users")
    private List<User> mUsers;

    public List<Department> getDepartments() {
        return mDepartments;
    }

    public void setDepartments(List<Department> departments) {
        mDepartments = departments;
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public void setUsers(List<User> users) {
        mUsers = users;
    }

}
