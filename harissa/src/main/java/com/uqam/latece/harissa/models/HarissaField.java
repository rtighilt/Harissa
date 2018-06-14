package com.uqam.latece.harissa.models;

import soot.SootField;

import java.util.List;

public class HarissaField extends HarissaEntity{

    private HarissaClass declaringClass;
    private SootField sootField;
    private String type;
    private List<Annotation> annotations;

    public HarissaField(SootField sootField, HarissaClass declaringClass)
    {
        this.declaringClass = declaringClass;
        this.sootField = sootField;
        this.buildFromSootField();
    }

    private void buildFromSootField()
    {
        this.name = sootField.getName();
        this.type = sootField.getType().toString();

    }

    public String getType()
    {
        return type;
    }

}
