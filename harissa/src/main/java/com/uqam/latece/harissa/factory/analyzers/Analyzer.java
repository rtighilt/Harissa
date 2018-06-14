package com.uqam.latece.harissa.factory.analyzers;


import com.uqam.latece.harissa.factory.loaders.LoaderConfiguration;
import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.harissa.models.HarissaClass;

import java.io.IOException;

public abstract class Analyzer {

    protected HarissaApp harissaApp;
    protected LoaderConfiguration loaderConfiguration;

    Analyzer(HarissaApp app, LoaderConfiguration loaderConfiguration)
    {
        this.harissaApp = app;
        this.loaderConfiguration = loaderConfiguration;
    }
    protected Analyzer(HarissaApp app)
    {
        this.harissaApp = app;
    }

    public abstract void runAnalysis() throws IOException;
}
