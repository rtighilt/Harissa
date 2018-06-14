package com.uqam.latece.nosy.analyzers;

import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.nosy.analyzers.android.ConnectivityManagerAnalyzer;
import com.uqam.latece.nosy.analyzers.clients.HttpClientAnalyzer;
import com.uqam.latece.nosy.analyzers.clients.IHttpClientAnalyzer;
import com.uqam.latece.nosy.configurations.AnalyzingConfiguraion;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NosyClientAnalyzer {

    private HarissaApp                analyzedApplication;
    private AnalyzingConfiguraion     analyzingConfiguraion;
    ConnectivityManagerAnalyzer       androidAnalyzer;
    private List<Class>               clientAnalyzerDictionnary;
    private List<String>              usedClients;

    public NosyClientAnalyzer(HarissaApp app)
    {
        this.analyzedApplication       = app;
        this.clientAnalyzerDictionnary = new ArrayList<>();

        androidAnalyzer = new ConnectivityManagerAnalyzer(this.analyzedApplication);

        loadClientAnalyzersDictionnary();
        findUsedLibraries();

    }

    public String getPackageName(){
        return this.analyzedApplication.getMainPackage();
    }

    public NosyClientAnalyzer(HarissaApp app, AnalyzingConfiguraion configuraion)
    {
        this.analyzedApplication   = app;
        this.analyzingConfiguraion = configuraion;
        this.clientAnalyzerDictionnary = new ArrayList<>();

        this.loadClientAnalyzersDictionnary();
    }

    public List<IHttpClientAnalyzer> getAnalyzers()
    {
        List<IHttpClientAnalyzer> listOfAnalyzers = new ArrayList<>();

//        this.clientAnalyzerDictionnary.forEach(client -> {
//            HttpClientAnalyzer httpClientAnalyzer;
//            try
//            {
//                httpClientAnalyzer = (HttpClientAnalyzer) client.getConstructor(HarissaApp.class).newInstance(this.analyzedApplication);
//
//                if(httpClientAnalyzer.isConnectingToRESTService()) listOfAnalyzers.add(httpClientAnalyzer);
//            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        });

        this.clientAnalyzerDictionnary.forEach(client -> {
            HttpClientAnalyzer httpClientAnalyzer;
            try
            {
                httpClientAnalyzer = (HttpClientAnalyzer) client.getConstructor(HarissaApp.class).newInstance(this.analyzedApplication);

                 if(httpClientAnalyzer.isReferencingHttpClient()) listOfAnalyzers.add(httpClientAnalyzer);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        return listOfAnalyzers;
    }

    public List<IHttpClientAnalyzer> getAnalyzersOld()
    {
        List<IHttpClientAnalyzer> listOfAnalyzers = new ArrayList<>();

        this.clientAnalyzerDictionnary.forEach(client -> {
            HttpClientAnalyzer httpClientAnalyzer;
            try
            {
                httpClientAnalyzer = (HttpClientAnalyzer) client.getConstructor(HarissaApp.class).newInstance(this.analyzedApplication);

                if(httpClientAnalyzer.isConnectingToRESTService()) listOfAnalyzers.add(httpClientAnalyzer);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

//        this.clientAnalyzerDictionnary.forEach(client -> {
//            HttpClientAnalyzer httpClientAnalyzer;
//            try
//            {
//                httpClientAnalyzer = (HttpClientAnalyzer) client.getConstructor(HarissaApp.class).newInstance(this.analyzedApplication);
//
//                if(httpClientAnalyzer.isReferencingHttpClient()) listOfAnalyzers.add(httpClientAnalyzer);
//            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        });

        return listOfAnalyzers;
    }

    private void findUsedLibraries()
    {
        this.usedClients = new ArrayList<>();

        this.clientAnalyzerDictionnary.forEach(client -> {
            HttpClientAnalyzer httpClientAnalyzer;
            try
            {
                httpClientAnalyzer = (HttpClientAnalyzer) client.getConstructor(HarissaApp.class).newInstance(this.analyzedApplication);

                if(httpClientAnalyzer.isConnectingToRESTService()) this.usedClients.add(httpClientAnalyzer.getHttpClientPackageName());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    public List<String> findConsumedApis()
    {
        List<String> consumedApis = new ArrayList<>();

        getAnalyzers().forEach(analyzer ->
        {
            if(!analyzer.findCallsDestinations().isEmpty())
                consumedApis.addAll(analyzer.findCallsDestinations());
        });

        return consumedApis;
    }

    public List<String> getUsedClientLibraries()
    {
        return this.usedClients;
    }

    public void loadClientAnalyzersDictionnary()
    {
        Reflections reflections = new Reflections(HttpClientAnalyzer.class.getPackage().getName());

        Set<Class<? extends HttpClientAnalyzer>> subTypes = reflections.getSubTypesOf(HttpClientAnalyzer.class);

        clientAnalyzerDictionnary.addAll(subTypes);
    }

    public boolean isUsingCache()
    {
         boolean isUsingCache =  this.getAnalyzers().stream().anyMatch(analyzer -> analyzer.isUsingHttpCaching());

         return isUsingCache;
    }

    public boolean isSettingUpTimeout()
    {
        boolean isSettingUpTimeout = this.getAnalyzers().stream().anyMatch(analyzer-> analyzer.isSettingTimeout());

        return isSettingUpTimeout;
    }

    public boolean isConnectivityAware()
    {
        boolean isConnectivityAware = androidAnalyzer.isConsideringConnectivity();

        return isConnectivityAware;
    }

    public boolean isMakingSynchrounousCalls()
    {
        boolean isMakingSynchrounousCalls = this.getAnalyzers().stream().anyMatch(analyzer -> analyzer.isMakingSynchronousCalls());

        return isMakingSynchrounousCalls;
    }

    public boolean isMakingAsynchrouousCalls()
    {
        boolean isMakingAsynchrounousCalls = this.getAnalyzers().stream().anyMatch(analyzer -> analyzer.isMakingAsynchronousCalls());

        return isMakingAsynchrounousCalls;
    }
}
