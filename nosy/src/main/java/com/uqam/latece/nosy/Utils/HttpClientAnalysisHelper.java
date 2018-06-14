package com.uqam.latece.nosy.Utils;

public class HttpClientAnalysisHelper {

    public final static String HTTPURLCONNECTION_PACKAGE_NAME                = "java.net.HttpURLConnection";
    public final static String URLCONNECTION_OPENCONNECTION_SIGNATURE        = "<java.net.URL: java.net.URLConnection openConnection()>";
    public final static String HTTPURLCONNECTION_CONNECT_SIGNATURE           = "java.net.HttpURLConnection connect()";
    public final static String HTTPURLCONNECTION_GETINPUTSTREAM              = "<java.net.HttpURLConnection: java.io.InputStream getInputStream()>";
    public final static String HTTPURLCONNECTION_GETOUTPUTSTREAM             = "<java.net.HttpURLConnection: java.io.OutputStream getOutputStream()>";
    public final static String HTTPURLCONNECTION_SETREQUESTMETHOD            = "<java.net.HttpURLConnection: void setRequestMethod(java.lang.String)>";
    public final static String URL_OPENSTREAM                                = "<java.net.URL: java.io.InputStream openStream()>";
    public final static String HTTPUTLCONNECTION_SETDOINPUT                  = "<java.net.HttpURLConnection: void setDoInput(boolean)>";
    public final static String HTTPUTLCONNECTION_SETDOOUTPUT                 = "<java.net.HttpURLConnection: void setDoOutput(boolean)>";
    public final static String HTTPURLCONNECTION_SETCHUNKEDSTREAMINGMODE     = "<java.net.HttpURLConnection: void setChunkedStreamingMode(int)>";
    public final static String HTTPURLCONNECTION_SETFixedLentghStreamingMode = "<java.net.HttpURLConnection: void setFixedLengthStreamingMode(int)>";
    public final static String HTTPURLCONNECTION_GETERRORSTREAM              = "<java.net.HttpURLConnection: java.io.InputStream getErrorStream()>";
    public final static String HTTPURLCONNECTION_GETRESPONSECODE             = "<java.net.HttpURLConnection: int getResponseCode()>";
    public final static String HTTPURLCONNECTION_SETCONNECTIONTIMEOUT        = "<java.net.HttpURLConnection: void setConnectTimeout(int)>";
    public final static String HTTPURLCONNECTION_SETREADINGTIMEOUT           = "<java.net.HttpURLConnection: void setReadTimeout(int)>";
    public final static String HTTPURLCONNECTION_GETCONNECTIONTIMEOUT        = "<java.net.HttpURLConnection: int getConnectTimeout()>";
    public final static String HTTPURLCONNECTION_DISCONNECT                  = "<java.net.HttpURLConnection: void disconnect()>";
    public final static String HTTPURLCONNECTION_RESPONSECACHE_CLASS_NAME    = "android.net.http.HttpResponseCache";
    public final static String CLASS_FOR_NAME                                = "<java.lang.Class: java.lang.Class forName(java.lang.String)>";
    public final static String CLASS_GET_METHOD_SIGNATURE                    = "<java.lang.Class: java.lang.reflect.Method getMethod(java.lang.String,java.lang.Class[])>";
    public final static String INSTALL_METHOD                                = "install";
    public final static String HTTPURLCONNECTION_INSTALL_CACHE               = "<android.net.http.HttpResponseCache: android.net.http.HttpResponseCache install(java.io.File,long)>";
    public final static String HTTPURLCONNECTION_SET_USE_CACHE               = "<java.net.HttpURLConnection: void setUseCaches(boolean)>";
    public final static String HTTPURLCONNECTION_GET_HEADER_FIELD_DATE       = "<java.net.HttpURLConnection: long getHeaderFieldDate(java.lang.String,long)>";
    public final static String EXPIRES                                       = "Expires";
    public final static String LAST_MODIFIED                                 = "Last-Modified";
    public final static String TRUE                                          = "1";

}