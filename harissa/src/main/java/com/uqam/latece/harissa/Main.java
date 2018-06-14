package com.uqam.latece.harissa;


import com.uqam.latece.harissa.factory.HarissaBuilder;
import com.uqam.latece.harissa.factory.loaders.LoaderConfiguration;
import com.uqam.latece.harissa.factory.loaders.ManifestLoader;
import com.uqam.latece.harissa.models.*;

import java.io.IOException;

import static com.uqam.latece.harissa.helpers.DecompilerHelper.ANDROID_JARS_PATH;
import static com.uqam.latece.harissa.helpers.DecompilerHelper.JavaAndAndroidSdkPackages;


public class Main {

    public static void main(String[] args) throws IOException {
       testingAnalysis();
       //testingUnzipping();
    }

    private static void testingAnalysis() throws IOException {

        LoaderConfiguration loaderConfiguration = new LoaderConfiguration.Builder().androidPlatformPath(ANDROID_JARS_PATH)
                                                                                   .excludedPackages(JavaAndAndroidSdkPackages)
                                                                                   .build();

        Apk apk = new Apk("/Users/Mac/Documents/Studies/Master/Framworks/ressources/app-debug.apk");

        //harissa Model :
        HarissaApp harissaApp = new HarissaBuilder(apk, loaderConfiguration).buildApp();

    }


}
