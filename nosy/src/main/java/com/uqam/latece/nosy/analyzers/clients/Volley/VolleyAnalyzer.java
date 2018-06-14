package com.uqam.latece.nosy.analyzers.clients.Volley;

import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.harissa.models.HarissaBody;
import com.uqam.latece.nosy.analyzers.clients.HttpClientAnalyzer;
import com.uqam.latece.nosy.analyzers.clients.IClientInstance;

import java.util.ArrayList;
import java.util.List;

import static com.uqam.latece.nosy.Utils.VolleyHelper.*;

public class VolleyAnalyzer extends HttpClientAnalyzer
{

    public VolleyAnalyzer(HarissaApp analyzedApp)
    {
        super(analyzedApp);

        this.instanceType                 = VOLLEY_INSTANCE;
        this.httpClientPackageName        = VOLLEY_PACKAGE_NAME;
        this.classesReferencingPackage    = findClassesReferencigPackage(httpClientPackageName);
        this.bodiesInitializingHttpClient = findBodiesInitializingClient(instanceType);
        this.instances                    = findHttpLibraryInstances();
        this.settingResponseListenerOfType(VOLLEY_REQUEST_FUTURE_NEW);
    }

    @Override
    public List<IClientInstance> findHttpLibraryInstances() {
        return null;
    }

    @Override

    public boolean isConnectingToRESTService()
    {
        boolean isInstantiatingRequestQueue = isInstantiatingRequestQueue();
        boolean isCreatingRequest           = isCreatingStringRequest();
        boolean isAddingRequestToQueue      = isAddingRequestToQueue();

        return (isInstantiatingRequestQueue && isCreatingRequest && isAddingRequestToQueue);
    }

    @Override
    public boolean isUsingHttpCaching() {

        boolean isGettingCachedData = isGettingCachedData();
        boolean isReferencingCacheInstance = isReferencingCacheInstance();
        boolean isParsingCacheHeader = isParsingCacheHeader();
        boolean isDesactivatingCache = findInvocationsOfMethod(VOLLEY_SET_SHOULD_CACHE).stream()
                                                                                       .anyMatch(invokeExpression ->
                                                                                                 invokeExpression.getInvoker().equals(FALSE));

        return isGettingCachedData && isReferencingCacheInstance && isParsingCacheHeader;
    }

    private boolean isReferencingCacheInstance()
    {
        return (findInitializationOf(VOLLEY_CACHE) || findInitializationOf(VOLLEY_CACHE_ENTRY));
    }

    private boolean isParsingCacheHeader()
    {
        return findMethodUsage(VOLLEY_PARSE_CACHE_HEADER);
    }

    public boolean isGettingCachedData() {
        return ( findMethodUsage(VOLLEY_QUEUE_GET_CACHE) || findMethodUsage(VOLLEY_REQUEST_GET_CACHE_ENTRY) || findMethodUsage(VOLLEY_REQUEST_GET_CACHE_KEY) );
    }

    @Override
    public boolean isSettingTimeout()
    {
        boolean isSettingRetryPolicy = this.isSettingRetryPolicy();

        return isSettingRetryPolicy;
    }

    private boolean isSettingRetryPolicy(){
        return findMethodUsage(VOLLEY_SET_RETRY_POLICY);
    }


    private boolean isInstantiatingRequestQueue()
    {
        return findMethodUsage(VOLLEY_NEW_REQUEST_QUEUE_SIGNATURE);
    }

    private boolean isCreatingStringRequest()
    {
        return findInitializationOf(VOLLEY_STRING_REQUEST) || findInitializationOf(VOLLEY_ARRAY_REQUEST) || findInitializationOf(VOLLEY_OBJECT_REQUEST);
    }

    private boolean isAddingRequestToQueue()
    {
        return findMethodUsage(VOLLEY_REQUEST_QUEUE_ADD_SIGNATURE);
    }

    @Override
    public List<String> findCallsDestinations() {
        return new ArrayList<>();
    }

    @Override
    public boolean isMakingSynchronousCalls() {

        boolean isInstantiatingFutureRequest = findMethodUsage(VOLLEY_REQUEST_FUTURE_NEW);
        boolean isSettingFutureRequestAsResponseListener = settingResponseListenerOfType(VOLLEY_FUTURE_REQUEST);

        return isSettingFutureRequestAsResponseListener || (isInstantiatingFutureRequest && isSettingFutureRequestAsResponseListener);
    }


    public boolean settingResponseListenerOfType(String responseListenerType)
    {
        return invokationsOfJsonObjectRequests().stream()
                                                .anyMatch(invokeExpression -> invokeExpression.getArgs()
                                                .containsKey(responseListenerType));

    }

    private List<HarissaBody.InvokeExpression> invokationsOfJsonObjectRequests()
    {
        List<HarissaBody.InvokeExpression> invokations = new ArrayList<>();

        invokations.addAll(findInvocationsOfMethod(VOLLEY_JSONOBJECTREQUEST_INIT));
        invokations.addAll(findInvocationsOfMethod(VOLLEY_JSONOBJECTREQUEST_INIT_2));

        return invokations;
    }

    public boolean settingOnlyResponseListenerOfType(String responseListenerType)
    {
        return invokationsOfJsonObjectRequests().stream()
                                                .allMatch(invokeExpression -> invokeExpression.getArgs()
                                                .containsKey(responseListenerType));

    }

    @Override
    public boolean isMakingAsynchronousCalls()
    {
        boolean findInvokationOfJsonRequestInit = (invokationsOfJsonObjectRequests().size() != 0);
        boolean isSettingAsyncResponseTypes = !settingOnlyResponseListenerOfType(VOLLEY_FUTURE_REQUEST);

        return findInvokationOfJsonRequestInit && isSettingAsyncResponseTypes;
    }
}