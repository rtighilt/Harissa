package com.uqam.latece.harissa.models;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HarissaRestApi extends HarissaEntity{

    //region PrivateFields
    private URL baseURL;
    private List<HarissaEndpoint> harissaEndpoints;
    private Provider provider;
    private String version;
    private String description;
    private URL logoURL;
    private String category;
    //endregion

    public HarissaRestApi() throws MalformedURLException {
        name = "";
        baseURL = null;
        harissaEndpoints = new ArrayList<>();
        provider = new Provider();
        version = "";
        description = "";
        logoURL = null;
        category = "";
    }

    public HarissaRestApi(String name,
                          URL baseURL,
                          List<HarissaEndpoint> harissaEndpoints,
                          Provider provider,
                          String version,
                          String description,
                          URL logoURL,
                          String category)
    {
        this.name = name;
        this.baseURL = baseURL;
        this.harissaEndpoints = harissaEndpoints;
        this.provider = provider;
        this.version = version;
        this.description = description;
        this.logoURL = logoURL;
        this.category = category;
    }

    //region Getters&Setters

    public URL getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(URL baseURL) {
        this.baseURL = baseURL;
    }

    public List<HarissaEndpoint> getHarissaEndpoints() {
        return harissaEndpoints;
    }

    public void setHarissaEndpoints(List<HarissaEndpoint> harissaEndpoints) {
        this.harissaEndpoints = harissaEndpoints;
    }

    public Provider getProviderName() {
        return provider;
    }

    public void setProviderName(Provider provider) {
        this.provider = provider;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(URL logoURL) {
        this.logoURL = logoURL;
    }
    //endregion
}
