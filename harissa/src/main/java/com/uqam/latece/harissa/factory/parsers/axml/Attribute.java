//Copyright (C) 2012 by Xavier GOUCHET (http://xgouchet.fr, android@xgouchet.fr) MIT Licence / Expat

package com.uqam.latece.harissa.factory.parsers.axml;

public class Attribute {

    private String mName, mPrefix, mNamespace, mValue;

    public String getName() {
        return mName;
    }

    public String getPrefix() {
        return mPrefix;
    }

    public String getNamespace() {
        return mNamespace;
    }

    public String getValue() {
        return mValue;
    }

    public void setName(final String name) {
        mName = name;
    }

    public void setPrefix(final String prefix) {
        mPrefix = prefix;
    }

    public void setNamespace(final String namespace) {
        mNamespace = namespace;
    }

    public void setValue(final String value) {
        mValue = value;
    }

}
