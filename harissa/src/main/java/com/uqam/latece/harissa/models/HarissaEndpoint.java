package com.uqam.latece.harissa.models;


public class HarissaEndpoint {


    //region private fields
    private String name;
    //endregion

    public HarissaEndpoint(String name)
    {
        this.name = name;
    }

    //region getters&setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    //endregion
}
