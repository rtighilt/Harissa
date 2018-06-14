package com.uqam.latece.harissa.models;


public abstract class HarissaEntity {

    protected String name;
    protected boolean isServiceRelated;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
