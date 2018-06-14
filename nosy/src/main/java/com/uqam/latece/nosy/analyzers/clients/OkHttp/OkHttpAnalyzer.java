package com.uqam.latece.nosy.analyzers.clients.OkHttp;

import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.harissa.models.HarissaBody;
import com.uqam.latece.nosy.analyzers.clients.HttpClientAnalyzer;
import com.uqam.latece.nosy.analyzers.clients.IClientInstance;
import soot.Local;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.uqam.latece.nosy.Utils.AnalysisUtils.STRING_CLASS_NAME;
import static com.uqam.latece.nosy.Utils.AnalysisUtils.cleanQuotes;
import static com.uqam.latece.nosy.Utils.OkHttpHelper.*;

public class OkHttpAnalyzer extends HttpClientAnalyzer{


    public OkHttpAnalyzer(HarissaApp analyzedApp)
    {
        super(analyzedApp);

        this.instanceType                 = OKHTTP_CLIENT;
        this.httpClientPackageName        = OKHTTP_PACKAGE_NAME;
        this.classesReferencingPackage    = findClassesReferencigPackage(httpClientPackageName);
        this.bodiesInitializingHttpClient = findBodiesInitializingClient(instanceType);
        this.instances                    = findHttpLibraryInstances();

    }

    @Override
    public List<IClientInstance> findHttpLibraryInstances()
    {
        List<OkhttpInstance> okhttpInstances = new ArrayList<>();

        this.bodiesInitializingHttpClient.forEach(body ->
        {
            okhttpInstances.addAll(body.getAssignExpressions()
                                       .stream()
                                       .filter(assignExpression ->
                                               assignExpression.leftIsLocal() &&
                                               assignExpression.isNewAssign() &&
                                               assignExpression.getRight()
                                                               .getType()
                                                               .toString()
                                                               .equals(this.instanceType))
                                       .collect(Collectors.toList())
                                       .stream()
                                       .map(assignExpression -> new OkhttpInstance(body,(Local) assignExpression.getLeft()))
                                       .collect(Collectors.toList()));
        });

        return okhttpInstances.stream()
                              .map(okhttpInstance -> (IClientInstance) okhttpInstance)
                              .collect(Collectors.toList());
    }

    @Override
    public boolean isConnectingToRESTService()
    {
        boolean isBuildingRequest  = this.isBuildingRequest();
        boolean isMakingSynchounousCalls = this.isMakingSynchronousCalls();
        boolean isMakingAsynchrounousCalls  = this.isMakingAsynchronousCalls();

        return (isBuildingRequest && (isMakingSynchounousCalls || isMakingAsynchrounousCalls));
    }

    @Override
    public boolean isUsingHttpCaching()
    {
        boolean outOfTheBoxCaching = this.isBuildingCache();

        return outOfTheBoxCaching;
    }

    public boolean isBuildingCache()
    {
        return findMethodUsage(OKHTTP_CACHE_CONSTRUCTOR) && findMethodUsage(OKHTTP_BUILDER_CACHE) ;
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
        return findMethodUsage(OKHTPP_CONNECT_TIMEOUT);
    }

    private boolean isSettingReadingTimout() {
        return findMethodUsage(OKHTTP_READ_TIMEOUT);
    }

    private boolean isSettingWritingTimout() {
        return findMethodUsage(OKHTTP_WRITE_TIMEOUT);
    }

    private boolean isBuildingRequest()
    {
        return this.findMethodUsage(OKHTTP_REQUEST_BUILDER_BUILD_SIGNATURE);
    }

    private boolean isExecutingRequest()
    {
        return this.findMethodUsage(OKHTTP_CALL_EXECUTE);
    }

    private boolean isEnqueingRequest()
    {
        return this.findMethodUsage(OKHTTP_CALL_ENQUEUE);
    }

    private List<String> targettedURLs() {
        List<String> urls = new ArrayList<>();

        List<HarissaBody.InvokeExpression> invokationsOfRequestBuilderURL = findInvocationsOfMethod(OKHTTP_REQUEST_BUILDER_URL_SIGNATURE);
        List<HarissaBody.InvokeExpression> invokationsOfHttpUrlParse      = findInvocationsOfMethod(OKHTTP_HTTPURL_PARSE_SIGNATURE);

        invokationsOfRequestBuilderURL.forEach(invokeExpression ->
        {
            if(invokeExpression.getArgs().entrySet().iterator().next().getKey().equals(STRING_CLASS_NAME))
            {
                String url = invokeExpression.getArgs().entrySet().iterator().next().getValue();

                urls.add(cleanQuotes(url));
            }
        });

        invokationsOfHttpUrlParse.forEach(invokeExpression ->
        {
            if(invokeExpression.getArgs().entrySet().iterator().next().getKey().equals(STRING_CLASS_NAME))
            {
                String url = invokeExpression.getArgs().entrySet().iterator().next().getValue();

                urls.add(cleanQuotes(url));
            }
        });

        return  urls;
    }

    @Override
    public List<String> findCallsDestinations() {
        return targettedURLs();
    }

    @Override
    public boolean isMakingSynchronousCalls() {
        return isExecutingRequest();
    }

    @Override
    public boolean isMakingAsynchronousCalls() {
        return isEnqueingRequest();
    }
}
