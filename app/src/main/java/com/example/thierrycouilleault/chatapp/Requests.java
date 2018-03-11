package com.example.thierrycouilleault.chatapp;

/**
 * Created by thierrycouilleault on 11/03/2018.
 */

public class Requests {

private String requestType;

    public Requests() {
    }

    public Requests(String requestType) {
        this.requestType = requestType;
    }


    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}
