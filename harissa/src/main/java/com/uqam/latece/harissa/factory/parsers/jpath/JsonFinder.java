package com.uqam.latece.harissa.factory.parsers.jpath;

import com.uqam.latece.harissa.factory.parsers.Query;
import com.uqam.latece.harissa.exceptions.UncheckedMalformedUrlException;
import com.uqam.latece.harissa.models.HarissaEndpoint;
import com.uqam.latece.harissa.models.HarissaRestApi;
import com.uqam.latece.harissa.models.Provider;
import com.uqam.latece.harissa.services.DataConnection;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.uqam.latece.harissa.helpers.JPathHelper;
import com.uqam.latece.harissa.helpers.URIHelpers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class JsonFinder {

    //region PrivateFields
    private String jsonString;
    private JsonParser jsonParser;
    //endregion

    public JsonFinder() throws IOException {
        jsonParser = new JsonParser(new File(URIHelpers.JSON_TEST_FILE)); //by default parse the apis file
    }

    public JsonFinder(String jsonString) throws IOException {
        jsonParser = new JsonParser(jsonString);
        this.jsonString = jsonString;
    }

    public JsonFinder(File jsonFile) throws IOException {
        jsonParser = new JsonParser(jsonFile);
    }

    public JsonFinder(URL jsonLink) throws IOException {
        jsonString = new DataConnection().sendRequest(jsonLink.toString());
        jsonParser = new JsonParser(jsonString);
    }

    //region Methods

    /**
     *
     * @param provider
     * @return
     * @throws IOException, UncheckedMalformedUrlException
     */
    public List<URL> getSwaggerUrlsByProvider(String provider) throws IOException {
        List<URL> urls = new ArrayList<>();
        Query getSwaggerUrlByProviderQuery = new Query(JPathHelper.API_VERSION_SWAGGER_URL_PATH,
                                                       JPathHelper.getSwaggerUrlWhereProviderFilter(provider));
        List<Object> results = this.jsonParser.executeQuery(getSwaggerUrlByProviderQuery);

        results.forEach(e -> {
            URL url = null;
            try {
                url = new URL((String) e);
            } catch (MalformedURLException e1) {
                throw new UncheckedMalformedUrlException(e1, e1.getMessage());
            }
            urls.add(url);
        });

        return  urls;
    }

    public HarissaRestApi getApiBySwaggerUrl(URL swaggerUrl) throws IOException {
        HarissaRestApi harissaRestApi = new HarissaRestApi();
        setJsonString(new DataConnection().sendRequest(swaggerUrl.toString()));
        Query getInfoBySwaggerQuery = new Query(JPathHelper.API_FULL_INFO_PATH);
        List<Object> lst = jsonParser.executeQuery(getInfoBySwaggerQuery);
        mapResultsIntoApi(lst);
        return harissaRestApi;
    }
    //endregion

    //region PrivateMethods
    private HarissaRestApi mapResultsIntoApi(List<Object> results) throws MalformedURLException {
        HarissaRestApi harissaRestApi = new HarissaRestApi();

        if(results.get(4) instanceof LinkedHashMap){
            LinkedHashMap temp = ((LinkedHashMap) results.get(4));
            harissaRestApi.setDescription(temp.values().toArray()[1].toString());
            harissaRestApi.setName(temp.values().toArray()[2].toString());
            harissaRestApi.setVersion(temp.values().toArray()[3].toString());
            harissaRestApi.setProviderName(new Provider(((temp.values().toArray()[8].toString()))));
            harissaRestApi.setLogoURL(new URL((((LinkedHashMap)(temp.values().toArray()[5])).values().toArray()[1].toString())));
        }

        if(results.get(6) instanceof LinkedHashMap){
            List<HarissaEndpoint> harissaEndpoints = new ArrayList();
            for (Object o : ((LinkedHashMap) results.get(6)).keySet().toArray()) {
                harissaEndpoints.add(new HarissaEndpoint(o.toString()));
            }
            harissaRestApi.setHarissaEndpoints(harissaEndpoints);
        }
        return harissaRestApi;
    }
    //endregion

    //region Setters&Getters
    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) throws IOException {
        this.jsonString = jsonString;
        jsonParser = new JsonParser(jsonString);
    }

    public JsonParser getJsonParser() {
        return jsonParser;
    }

    public void setJsonParser(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }
    //endregion

    private class JsonParser{
        //region PrivateFields
        private ReadContext readContext;
        //endregion

        JsonParser(File jsonFile) throws  IOException{
            readContext = JsonPath.parse(jsonFile);
        }

        JsonParser(String jsonString) throws IOException {
            readContext = JsonPath.parse(jsonString);
        }

        //region Methods
         List<Object> executeQuery(Query query) throws IOException {

            if (query.getFilter()!=null)
                return readContext.read(query.getPathToNode(), List.class,query.getFilter());
            else
                return readContext.read(query.getPathToNode(), List.class);
         }
        //endregion
    }

}

