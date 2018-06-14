package com.uqam.latece.nosy.Utils;

import com.sun.tools.corba.se.idl.StringGen;

public class VolleyHelper
{
    public static final String VOLLEY_PACKAGE_NAME                 = "com.android.volley";
    public static final String VOLLEY_RESPONSE                     = "com.android.volley.Response";
    public static final String VOLLEY_REQUEST_QUEUE                = "com.android.volley.RequestQueue";
    public static final String VOLLEY_INSTANCE                     = "com.android.volley.toolbox.Volley";
    public static final String VOLLEY_REQUEST                      = "com.android.volley.Request";
    public static final String VOLLEY_FUTURE_REQUEST               = "com.android.volley.toolbox.RequestFuture";
    public static final String VOLLEY_STRING_REQUEST               = "com.android.volley.toolbox.StringRequest";
    public static final String VOLLEY_ARRAY_REQUEST                = "com.android.volley.toolbox.JsonArrayRequest";
    public static final String VOLLEY_OBJECT_REQUEST               = "com.android.volley.toolbox.JsonObjectRequest";
    public static final String VOLLEY_REQUEST_QUEUE_ADD_SIGNATURE  = "<com.android.volley.RequestQueue: com.android.volley.Request add(com.android.volley.Request)>";
    public static final String VOLLEY_NEW_REQUEST_QUEUE_SIGNATURE  = "<com.android.volley.toolbox.Volley: com.android.volley.RequestQueue newRequestQueue(android.content.Context)>";
    public static final String VOLLEY_SET_RETRY_POLICY             = "<com.android.volley.toolbox.JsonObjectRequest: com.android.volley.Request setRetryPolicy(com.android.volley.RetryPolicy)>";
    public static final String VOLLEY_REQUEST_GET_CACHE_KEY        = "<com.android.volley.toolbox.JsonObjectRequest: java.lang.String getCacheKey()>";
    public static final String VOLLEY_REQUEST_GET_CACHE_ENTRY      = "<com.android.volley.toolbox.JsonObjectRequest: com.android.volley.Cache$Entry getCacheEntry()>";
    public static final String VOLLEY_QUEUE_GET_CACHE              = "<com.android.volley.RequestQueue: com.android.volley.Cache getCache()>";
    public static final String VOLLEY_SET_SHOULD_CACHE             = "<com.android.volley.toolbox.JsonObjectRequest: com.android.volley.Request setShouldCache(boolean)>";
    public static final String VOLLEY_REQUEST_FUTURE_NEW           = "<com.android.volley.toolbox.RequestFuture: com.android.volley.toolbox.RequestFuture newFuture()>";
    public static final String VOLLEY_JSONOBJECTREQUEST_INIT       = "<com.android.volley.toolbox.JsonObjectRequest: void <init>(java.lang.String,org.json.JSONObject,com.android.volley.Response$Listener,com.android.volley.Response$ErrorListener)>";
    public static final String VOLLEY_JSONOBJECTREQUEST_INIT_2     = "<com.android.volley.toolbox.JsonObjectRequest: void <init>(int,java.lang.String,org.json.JSONObject,com.android.volley.Response$Listener,com.android.volley.Response$ErrorListener)>";
    public static final String VOLLEY_CACHE                        = "com.android.volley.Cache";
    public static final String VOLLEY_CACHE_ENTRY                  = "com.android.volley.Cache.Entry";
    public static final String VOLLEY_PARSE_CACHE_HEADER           = "<com.android.volley.toolbox.HttpHeaderParser: com.android.volley.Cache$Entry parseCacheHeaders(com.android.volley.NetworkResponse)>";


    public static final String TRUE                                = "1";
    public static final String FALSE                               = "0";


    public static String VOLLET_REST_METHOD(int methNumber)
    {
        String method;

        switch (methNumber) {
            case -1: method = "DEPRECATED_GET_OR_POST";
                break;
            case 1:  method = "GET";
                break;
            case 2:  method = "POST";
                break;
            case 3:  method = "PUT";
                break;
            case 4:  method = "DELETE";
                break;
            case 5:  method = "HEAD";
                break;
            case 6:  method = "OPTIONS";
                break;
            case 7:  method = "TRACE";
                break;
            default: method = "PATH";
        }

        return method;
    }
}

