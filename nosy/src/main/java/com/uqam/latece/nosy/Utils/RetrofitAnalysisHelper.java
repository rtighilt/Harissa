package com.uqam.latece.nosy.Utils;

public class RetrofitAnalysisHelper {

    public final static String RETROFIT_BUILDER_INSTANCE                            = "retrofit2.Retrofit$Builder";
    public final static String RETROFIT_INSTANCE                                    = "retrofit2.Retrofit";
    public final static String RETROFIT_PACKAGE_NAME                                = "retrofit2";
    public final static String RETROFIT_BUILDER_INITIALIZATION_SIGNATURE            = "<retrofit2.Retrofit$Builder: void <init>()>";
    public final static String RETROFIT_BUILD_SIGNATURE                             = "<retrofit2.Retrofit$Builder: retrofit2.Retrofit build()>";
    public final static String RETROFIT_BUILDER_SETURL_SIGNATURE                    = "<retrofit2.Retrofit$Builder: retrofit2.Retrofit$Builder baseUrl(java.lang.String)>";
    public final static String RETROFIT_BUILDER_ADD_CONVERTER_FACTORY_SIGNATURE     = "<retrofit2.Retrofit$Builder: retrofit2.Retrofit$Builder addConverterFactory(retrofit2.Converter$Factory)>";
    public final static String RETROFIT_CREATE_API_INTERFACE_SIGNATURE              = "<retrofit2.Retrofit: java.lang.Object create(java.lang.Class)>";
    public final static String RETOFIT_ENQUEUE_SIGNATURE                            = "<retrofit2.Call: void enqueue(retrofit2.Callback)>";
    public final static String RETROFIT_EXECUTE_SIGNATURE                           = "<retrofit2.Call: retrofit2.Response execute()>";
    public final static String RETROFIT_CLIENT_SIGNATURE                            = "<retrofit2.Retrofit$Builder: retrofit2.Retrofit$Builder client(okhttp3.OkHttpClient)>";

    //RETRO_COMPATIBILITY
    public final static String RETROFIT_BUILDER_INSTANCE_OLD                            = "retrofit.RestAdapter$Builder";
    public final static String RETROFIT_INSTANCE_OLD                                    = "retrofit.RestAdapter";
    public final static String RETROFIT_PACKAGE_NAME_OLD                                = "retrofit";
    public final static String RETROFIT_CALLBACK_OLD                                    = "retrofit.Callback";
    public final static String RETROFIT_BUILDER_INITIALIZATION_SIGNATURE_OLD            = "<retrofit.RestAdapter$Builder: void <init>()>";
    public final static String RETROFIT_BUILD_SIGNATURE_OLD                             = "<retrofit.RestAdapter$Builder: retrofit.RestAdapter build()>";
    public final static String RETROFIT_BUILDER_SETURL_SIGNATURE_OLD                    = "<retrofit.RestAdapter$Builder: retrofit.RestAdapter$Builder setEndpoint(java.lang.String)>";
    //public final static String RETROFIT_BUILDER_ADD_CONVERTER_FACTORY_SIGNATURE_OLD     = "<retrofit.Retrofit$Builder: retrofit.Retrofit$Builder addConverterFactory(retrofit.Converter$Factory)>";
    public final static String RETROFIT_CREATE_API_INTERFACE_SIGNATURE_OLD              = "<retrofit.RestAdapter: java.lang.Object create(java.lang.Class)>";
    //public final static String RETOFIT_ENQUEUE_SIGNATURE_OLD                            = "<retrofit.Call: void enqueue(retrofit.Callback)>";
    public final static String RETROFIT_CLIENT_SIGNATURE_OLD                            = "<retrofit.RestAdapter$Builder: retrofit.RestAdapter$Builder setClient(retrofit.client.Client)>";
    public final static String RETROFIT_OKHTTP_OLD_CLIENT                                = "retrofit.client.OkClient";
}
