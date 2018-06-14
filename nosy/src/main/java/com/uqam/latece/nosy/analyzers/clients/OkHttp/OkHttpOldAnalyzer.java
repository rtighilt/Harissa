package com.uqam.latece.nosy.analyzers.clients.OkHttp;

import com.uqam.latece.harissa.models.HarissaApp;

import static com.uqam.latece.nosy.Utils.OkHttpHelper.*;

public class OkHttpOldAnalyzer extends OkHttpAnalyzer{


    public OkHttpOldAnalyzer(HarissaApp analyzedApp) {
        super(analyzedApp);

        this.instanceType = OKHTTP_CLIENT_OLD;
        this.httpClientPackageName = OKHTTP_PACKAGE_NAME_OLD;

        this.classesReferencingPackage    = findClassesReferencigPackage(httpClientPackageName);
        this.bodiesInitializingHttpClient = findBodiesInitializingClient(instanceType);
        this.instances                    = findHttpLibraryInstances();
    }

    @Override
    public boolean isUsingHttpCaching()
    {
        boolean outOfTheBoxCaching = this.isBuildingCache();

        return outOfTheBoxCaching;
    }

    public boolean isBuildingCache()
    {
        return findMethodUsage(OKHTTP_CACHE_CONSTRUCTOR_OLD) && findMethodUsage(OKHTTP_BUILDER_CACHE_OLD) ;
    }

    @Override
    public boolean isConnectingToRESTService()
    {
        boolean isBuildingRequest  = this.isBuildingRequest();
        boolean isExecutingRequest = this.isExecutingRequest();
        boolean isEnqueingRequest  = this.isEnqueingRequest();

        return (isBuildingRequest && (isExecutingRequest || isEnqueingRequest));
    }

    @Override
    public boolean isSettingTimeout()
    {
        boolean isSettingConnectionTimeout = isSettingConnectionTimout();
        boolean isSettingReadingTimeout    = isSettingReadingTimout();
        boolean isSettingWritingTimeout    = isSettingWritingTimout();

        return isSettingConnectionTimeout || isSettingReadingTimeout || isSettingWritingTimeout;
    }

    private boolean isSettingConnectionTimout() {
        return findMethodUsage(OKHTPP_CONNECT_TIMEOUT_OLD);
    }

    private boolean isSettingReadingTimout() {
        return findMethodUsage(OKHTTP_READ_TIMEOUT_OLD);
    }

    private boolean isSettingWritingTimout() {
        return findMethodUsage(OKHTTP_WRITE_TIMEOUT_OLD);
    }

    private boolean isBuildingRequest()
    {
        return this.findMethodUsage(OKHTTP_REQUEST_BUILDER_BUILD_SIGNATURE_OLD);
    }

    private boolean isExecutingRequest()
    {
        return this.findMethodUsage(OKHTTP_CALL_EXECUTE_OLD);
    }

    private boolean isEnqueingRequest()
    {
        return this.findMethodUsage(OKHTTP_CALL_ENQUEUE_OLD);
    }

    @Override
    public boolean isMakingAsynchronousCalls() {
        return isEnqueingRequest();
    }

    @Override
    public boolean isMakingSynchronousCalls() {
        return this.isExecutingRequest();
    }
}
