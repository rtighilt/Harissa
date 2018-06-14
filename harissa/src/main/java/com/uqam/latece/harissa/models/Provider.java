package com.uqam.latece.harissa.models;

import java.util.ArrayList;
import java.util.List;


public class Provider {

    //region PrivateFields
    private String name;
    private List<HarissaRestApi> harissaRestApis;
    //endregion

    public Provider(String name){
        this.name = name;
    }
    public Provider(){
        name = "";
        harissaRestApis = new ArrayList<HarissaRestApi>();
    }
    public Provider(String name, List<HarissaRestApi> harissaRestApis){
        this.name = name;
        this.harissaRestApis = harissaRestApis;
    }

    //region Getters&Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HarissaRestApi> getHarissaRestApis() {
        return harissaRestApis;
    }

    public void setHarissaRestApis(List<HarissaRestApi> harissaRestApis) {
        this.harissaRestApis = harissaRestApis;
    }
    //endregion
}
