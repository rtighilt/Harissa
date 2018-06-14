package com.uqam.latece.nosy.analyzers.android;

import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.nosy.configurations.AnalyzingConfiguraion;


import static com.uqam.latece.nosy.Utils.AndroidHelper.*;

public class ConnectivityManagerAnalyzer extends AndroidAnalyzer{

    public ConnectivityManagerAnalyzer(HarissaApp analyzedApp)
    {
        super(analyzedApp);
        this.androidSdkPackageName = CONNECTIVITY_MANAGER_PACKAGE_NAME;

    }

    public ConnectivityManagerAnalyzer(HarissaApp analyzedApp, AnalyzingConfiguraion configuraion)
    {
        super(analyzedApp, configuraion);

        this.androidSdkPackageName = NETWORK_INFO;
    }


    public boolean isConsideringConnectivity()
    {
        boolean appReferencesConnectivityManagerLibrary = this.isReferencingConnectivityManagerLibrary();
        boolean appReferencesNetworkInfo = this.isReferencingNetworfInfo();
        boolean appRetrievingNetworkType = this.isRetrievingNetworkType();
        boolean appIsChekingConnectionState = this.isCheckingConnectivity();

        return (appReferencesConnectivityManagerLibrary && appReferencesNetworkInfo && (appRetrievingNetworkType || appIsChekingConnectionState));
    }

    public boolean isReferencingConnectivityManagerLibrary()
    {
        return this.appReferencesPackage(CONNECTIVITY_MANAGER_PACKAGE_NAME);
    }

    public boolean isReferencingNetworfInfo()
    {
        return this.appReferencesPackage(androidSdkPackageName);
    }

    public boolean isRetrievingNetworkType()
    {
        return findMethodUsage(NETWORK_INFO_GET_TYPE_SIGNATURE);
    }

    public boolean isCheckingConnectivity()
    {
        boolean isConnectedOrConnecting  = findMethodUsage(NETWORK_INFO_ISCONNECTED_OR_CONNECTING_SIGNATURE);
        boolean isRetrievingNetworkState = findMethodUsage(NETWORK_INFO_GET_STATE_SIGNATURE) || findMethodUsage(NETWORK_INFO_GET_DETAILED_STATE_SIGNATURE);
        boolean isConnected              = findMethodUsage(NETWORK_INFO_ISCONNECTED_SIGNATURE);
        boolean isFailOver               = findMethodUsage(NETWORK_INFO_ISFAILOVER_SIGNATURE);
        boolean isAvailable              = findMethodUsage(NETWORK_INFO_ISAVAILIBLE_SIGNATURE);
        boolean isRoaming                = findMethodUsage(NETWORK_INFO_ISROAMING_SIGNATURE);

        return (isConnectedOrConnecting || isAvailable || isConnected || isFailOver || isRoaming || isRetrievingNetworkState);
    }


}
