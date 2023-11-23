package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostOfficeResponse {

//    @SerializedName("Message")
//    @Expose
//    private String message;
//
//    @SerializedName("Status")
//    @Expose
//    private String status;



//    public String getMessage() {
//        return message;
//    }
//
//    public String getStatus() {
//        return status;
//    }

    @SerializedName("PostOffice")
    @Expose
    private List<PostOffice> postOfficeList;

    public List<PostOffice> getPostOfficeList() {
        return postOfficeList;
    }

    public static class PostOffice {

        @SerializedName("Name")
        @Expose
        private String name;

        // Other properties

        public String getName() {
            return name;
        }

        // Other getters for additional properties
    }
}
