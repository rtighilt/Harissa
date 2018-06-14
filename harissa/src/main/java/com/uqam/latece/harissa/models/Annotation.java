package com.uqam.latece.harissa.models;

import java.util.List;

public class Annotation {

    private String name;
    private List<String> values;
    private ANNOTATION_TYPE type;

    Annotation(String name, List<String> value, ANNOTATION_TYPE annotation_type)
    {
        this.name = name;
        this.values = value;
        this.type = annotation_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValue() {
        return values;
    }

    public void setValue(List<String> value) {
        this.values = value;
    }

    public ANNOTATION_TYPE getType() {
        return type;
    }

    public void setType(ANNOTATION_TYPE type) {
        this.type = type;
    }

    public enum ANNOTATION_TYPE
    {
        VISIBILITY_ANNOTATION,
        VISIBILITY_PARAMETER_ANNOTATION
    }
}
