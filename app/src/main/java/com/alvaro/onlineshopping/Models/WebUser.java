package com.alvaro.onlineshopping.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebUser {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("state")
    @Expose
    private String state;

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String get_id() { return _id; }
}