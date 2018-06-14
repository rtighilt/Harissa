package com.uqam.latece.harissa.models;


public class HarissaHttpClient {

    private String name;
    private String packageName;

    public HarissaHttpClient(String name, String packageName){
        this.name = name;
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
