package com.uqam.latece.harissa.services;

import com.uqam.latece.harissa.helpers.URIHelpers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class DataConnection {

    private static File apiguruFile;
    private static OkHttpClient client;

    public DataConnection()
    {
        apiguruFile = new File(URIHelpers.APIGURU_FILE_URL);
        client = new OkHttpClient();
    }

    public void updateApiListFile() throws IOException {

        PrintWriter writer = new PrintWriter(apiguruFile);


        String apiListResponse = sendRequest(URIHelpers.APIGURU_BASE_URL + URIHelpers.APIGURU_LIST_ENDPOINT);

        if (apiListResponse!=null){
            writer.println(apiListResponse);
        }

        writer.close();
    }

    public String sendRequest(String url) throws IOException {

        Request request = new Request.Builder()
                                     .url(url)
                                     .build();

        ResponseBody responseBody = client.newCall(request)
                                          .execute()
                                          .body();

        return responseBody.string();
    }
}
