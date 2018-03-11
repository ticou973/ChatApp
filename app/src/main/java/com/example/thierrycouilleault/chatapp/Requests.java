package com.example.thierrycouilleault.chatapp;

/**
 * Created by thierrycouilleault on 11/03/2018.
 */

public class Requests {

    private String request_type;
    private String request_date;

    public Requests() {
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public Requests(String request_type, String request_date) {
        this.request_type = request_type;
        this.request_date = request_date;



    }
}
