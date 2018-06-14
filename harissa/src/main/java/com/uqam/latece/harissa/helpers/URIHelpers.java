package com.uqam.latece.harissa.helpers;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URIHelpers
{

    //region URIs
    public static final String APIGURU_BASE_URL = "https://api.apis.guru/v2";
    public static final String APIGURU_LIST_ENDPOINT = "/list.json";
    public static final String APIGURU_METRICS_ENDPOINT = "/metrics.json";
    public static final String APIGURU_FILE_URL = "data/apiguru-apis.json";
    public static final String JSON_TEST_FILE = "data/jsonTest.json";
    private static final String urlRegex = "^(http|https)://[-a-zA-Z0-9+&@#/%?=~_|,!:.;]*[-a-zA-Z0-9+@#/%=&_|]";
    //endregion


    public static boolean isValidURL(String url) {

        Pattern pattern = Pattern.compile(urlRegex);
        Matcher m = pattern.matcher(url);
        return m.matches();
    }
}
