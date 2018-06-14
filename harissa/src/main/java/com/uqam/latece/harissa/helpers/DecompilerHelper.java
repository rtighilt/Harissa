package com.uqam.latece.harissa.helpers;


import com.uqam.latece.harissa.models.HarissaHttpClient;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DecompilerHelper {


    public static final String SOOT_OUTPUT_DIRECTORY = "sootOutput/";
    public static final String ANDROID_JARS_PATH = "/Users/Mac/Documents/Master/HarissaFramework/ressources/android-platforms";
    public static final String HTTP_CLIENT_LIST = "data/httpclients.csv";
    public static final File HTTP_CLIENT_LIST_FILE = new File(HTTP_CLIENT_LIST);
    public static final String CSV_SPLIT = ",";
    public static final List<String> JavaAndAndroidSdkPackages = Arrays.asList(
            "org.apache.*",
            "soot.*",
            "sun.misc.*",
            "java.*",
            "javax.servlet.*",
            "android.*",
            "com.android.*"
    );
    public static final List<String> JavaJdkPackages = Arrays.asList(
            "java.*",
            "javax.servlet.*"
    );
    public static final List<String> AndroidSdkPackages = Arrays.asList(
            "android.*",
            "com.android.*"
    );

    public static List<HarissaHttpClient> getHttpClientListFromCSV() throws FileNotFoundException {
        List<HarissaHttpClient> httpClients = new ArrayList<>();
        Scanner scanner = new Scanner(HTTP_CLIENT_LIST_FILE);

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] httpClientInfoArray = line.split(CSV_SPLIT);

            String httpClientName = httpClientInfoArray[0];
            String httpClientPackage = httpClientInfoArray[1];

            httpClients.add(new HarissaHttpClient(httpClientName, httpClientPackage));
        }
        scanner.close();

        return httpClients;
    }

    public static List<String> readFileInList(String fileName)
    {

        List<String> lines = Collections.emptyList();
        try
        {
            lines =
                    Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        }

        catch (IOException e)
        {

            // do something
            e.printStackTrace();
        }
        return lines;
    }
}
