package com.uqam.latece.nosy.printers;

import com.opencsv.CSVWriter;
import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.nosy.analyzers.NosyClientAnalyzer;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class OutputPrinter {

    String outputFilePath;
    String subOutputFilePath;

    public OutputPrinter(String outputFilePath, String subOutputFilePath) throws IOException
    {
        this.outputFilePath = outputFilePath;
        this.subOutputFilePath = subOutputFilePath;
    }

    public OutputPrinter(String outputFilePath)
    {
        this.outputFilePath = outputFilePath;
    }

    public void print (HarissaApp application) throws IOException
    {
        FileWriter fileWriter =  new FileWriter(outputFilePath, true);

        CSVWriter csvWriter = new CSVWriter(fileWriter);

        List<String> entry = new ArrayList<>();
        entry.add(application.getMainPackage());
        entry.add((Integer.toString(application.getNumberOfClasses())));
        entry.add((Integer.toString(application.getNumberOfLineOfCode())));

        csvWriter.writeNext((entry.toArray(new String[entry.size()])), false);

        csvWriter.close();

    }

    public void print (NosyClientAnalyzer nosyClientAnalyzer, String packageName) throws IOException
    {
        FileWriter fileWriter =  new FileWriter(outputFilePath, true);
        FileWriter subFileWriter =  new FileWriter(subOutputFilePath, true);


        CSVWriter csvWriter = new CSVWriter(fileWriter);
        CSVWriter subCsvWriter = new CSVWriter(subFileWriter);

        OutputEntry outputEntry = mapIntoOutputEntry(nosyClientAnalyzer, packageName);

        csvWriter.writeNext(outputEntry.getArrayEntry(),false);

        if(outputEntry.getSubLines().size()!=0){
            outputEntry.getSubLines().forEach(subLine -> {
            subCsvWriter.writeNext(subLine.getArrayEntry(),false);
        });}

        csvWriter.close();
        subCsvWriter.close();
    }


    private OutputEntry mapIntoOutputEntry(NosyClientAnalyzer nosyClientAnalyzer, String packageName)
    {

        OutputEntry outputEntry = new OutputEntry.Builder().packageName(packageName)
                                                           .connectivityAware(nosyClientAnalyzer.isConnectivityAware())
                                                           .isSettingUpeTimeout(nosyClientAnalyzer.isSettingUpTimeout())
                                                           .isUsingCache(nosyClientAnalyzer.isUsingCache())
                                                           .connectivityAware(nosyClientAnalyzer.isConnectivityAware())
                                                           .httpClients(nosyClientAnalyzer.getAnalyzers())
                                                           .isMakingSynchronousCalls(nosyClientAnalyzer.isMakingSynchrounousCalls())
                                                           .isMakingAsynchronousCalls(nosyClientAnalyzer.isMakingAsynchrouousCalls())
                                                           .build();

        return outputEntry;
    }
}
