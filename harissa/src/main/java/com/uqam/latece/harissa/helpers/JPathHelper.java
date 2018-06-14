package com.uqam.latece.harissa.helpers;

import com.jayway.jsonpath.Filter;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;

public class JPathHelper {

    //region Consts
    public static final String API_VERSION_SWAGGER_URL_PATH = "$..versions..[?].swaggerUrl";
    public static final String API_FULL_INFO_PATH = "$.*";
    public static final String WHERE_TITLE_QUERY = "$..versions..[?(@.info.title CONTAINS '%s')].swaggerUrl";
    //endregion

    public static final String APK_FULL_INFO = API_FULL_INFO_PATH;


    //region Paths
    private static final String API_VERSION_INFO_PATH = "$..versions..info";
    //region Filters&Predicates
    private static final String WHERE_TITLE_FILTER = "[?(@.title == '1Forge Finance APIs')]";
    //endregion

    public static Filter filterSwaggerUrlByTitle(String title){
        return filter(where("@.info.title").contains(title));
    }

    public static Filter getSwaggerUrlWhereProviderFilter(String provider){
        return filter(where("@.info.x-providerName").contains(provider));
    }

    //region StringQueries
    public static String getTitleFromID(){ return API_VERSION_INFO_PATH + WHERE_TITLE_FILTER;}
    //endregion

}
