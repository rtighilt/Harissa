package com.uqam.latece.harissa.factory.analyzers;

import com.uqam.latece.harissa.factory.parsers.jimple.JimpleFinder;
import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.harissa.models.HarissaClass;
import com.uqam.latece.harissa.models.HarissaHttpClient;
import com.uqam.latece.harissa.helpers.DecompilerHelper;
import soot.jimple.JimpleBody;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class ClientAnalyzer{

    private static HarissaApp harissaApp;
    private static String httpClient;

    ClientAnalyzer(HarissaApp app){
        harissaApp = app;
        httpClient = "";
    }

    Map<JimpleBody, String> fetchURLs(){
        Map<JimpleBody, String> jimpleBodyUrlHashMap = new HashMap<>();

        harissaApp.getApplicationClasses().forEach(applicationClass ->
                jimpleBodyUrlHashMap .putAll(new JimpleFinder(applicationClass).findJimplesWithURLs())
        );

        return jimpleBodyUrlHashMap;
    }

    void findHttpClient() throws FileNotFoundException {
        List<HarissaClass> libraryClasses = harissaApp.getLibraryClasses();
        List<HarissaHttpClient> commonHttpClients = DecompilerHelper.getHttpClientListFromCSV();

        libraryClasses.forEach(libraryClass -> {
            List<HarissaHttpClient> foundHttpClients = new ArrayList<>();
            commonHttpClients.forEach(httpClient -> {
                if(libraryClass.getName().startsWith(httpClient.getPackageName()) && !foundHttpClients.contains(httpClient))
                {
                    foundHttpClients.add(httpClient);
                    System.out.println(httpClient.getName());
                }
            });
        });
    }


}