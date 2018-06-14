package com.uqam.latece.harissa.factory.loaders;


import com.uqam.latece.harissa.models.Apk;

import java.io.IOException;

public abstract class Loader {

    protected Apk apk;

    public abstract void init();
    public abstract void runLoading() throws IOException;

}

