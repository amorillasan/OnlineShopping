package com.alvaro.onlineshopping.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Account {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("billing_address")
    @Expose
    private String billing_address;
    @SerializedName("is_closed")
    @Expose
    private Boolean is_closed;
    @SerializedName("open")
    @Expose
    private Date open;
    @SerializedName("closed")
    @Expose
    private Date closed;

    public String getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(String billing_address) { this.billing_address = billing_address; }

    public Boolean getIs_closed() {
        return is_closed;
    }

    public void setIs_closed(Boolean is_closed) {
        this.is_closed = is_closed;
    }

    public Date getOpen() { return open; }

    public void setOpen(Date open) { this.open = open; }

    public Date getClosed() { return closed; }

    public void setClosed(Date closed) { this.closed = closed; }

    public String get_id() { return _id; }
}