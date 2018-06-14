package com.uqam.latece.nosy.analyzers.clients.Retrofit;

import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.harissa.models.HarissaBody;
import com.uqam.latece.harissa.models.HarissaMethod;
import com.uqam.latece.nosy.analyzers.clients.OkHttp.OkHttpAnalyzer;
import com.uqam.latece.nosy.analyzers.clients.OkHttp.OkHttpOldAnalyzer;

import java.util.ArrayList;
import java.util.List;

import static com.uqam.latece.nosy.Utils.AnalysisUtils.*;
import static com.uqam.latece.nosy.Utils.RetrofitAnalysisHelper.*;


public class RetrofitOldAnalyzer extends RetrofitAnalyzer {


    public RetrofitOldAnalyzer(HarissaApp analyzedApp) {
        super(analyzedApp);
        this.instanceType                  = RETROFIT_BUILDER_INSTANCE_OLD;
        this.httpClientPackageName         = RETROFIT_PACKAGE_NAME_OLD;
        this.classesReferencingPackage     = findClassesReferencigPackage(httpClientPackageName);
        this.bodiesInitializingHttpClient  = findBodiesInitializingClient(instanceType);
        this.instances                     = findHttpLibraryInstances();
        this.apiInterfaces                 = findApiInterfaces();
    }

    @Override
    public boolean isConnectingToRESTService() {

        boolean isBuildingRetrofit        = isBuildingRetrofit();
        boolean isCreatingAnyApiInterface = isCreatingApiInteface();
        boolean isEnqueing = isEnqueingCall();

        return (isBuildingRetrofit && isCreatingAnyApiInterface && isEnqueing);
    }

    public boolean isEnqueingCall()
    {
        boolean isEnqueingCall;

        isEnqueingCall = this.methodsFromApiInterfaces().stream().anyMatch(method -> this.findMethodUsage(method.getSignature()));

        return isEnqueingCall;
    }

    private List<HarissaMethod> methodsFromApiInterfaces()
    {
        List<HarissaMethod> methodsFromApiInterfaces = new ArrayList<>();

        this.apiInterfaces.forEach(apiInterface -> methodsFromApiInterfaces.addAll(apiInterface.getRetrofitInterface().getMethods()));

        return methodsFromApiInterfaces;
    }

    public boolean isMakingAsynchronousCalls()
    {
        boolean isMakingAsynchronousCall = false;

        List<HarissaMethod> methodsFromApiInterfaces = this.methodsFromApiInterfaces();

        for (HarissaMethod method : methodsFromApiInterfaces)
        {
            String signature = method.getSignature();
            boolean methodIsCalled = this.findMethodUsage(signature);
            boolean asynchronous   = method.hasParameterOfType(RETROFIT_CALLBACK_OLD);

            if(methodIsCalled && asynchronous) isMakingAsynchronousCall = true;
        }

        return isMakingAsynchronousCall;
    }



    public boolean isMakingSynchronousCalls()
    {
        boolean isMakingSynchronousCall = false;

        List<HarissaMethod> methodsFromApiInterfaces = this.methodsFromApiInterfaces();

        for (HarissaMethod method : methodsFromApiInterfaces)
        {
            String signature = method.getSignature();
            boolean methodIsCalled = this.findMethodUsage(signature);
            boolean synchronous   = !method.hasParameterOfType(RETROFIT_CALLBACK_OLD);

            if(methodIsCalled && synchronous) isMakingSynchronousCall = true;
        }

        return isMakingSynchronousCall;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public List<String> findCallsDestinations()
    {
        List<String> urls = new ArrayList<>();
        List<HarissaBody.InvokeExpression> invokeExpressionOfBuildingURL = findInvocationsOfMethod(RETROFIT_BUILDER_SETURL_SIGNATURE_OLD);

        if(!invokeExpressionOfBuildingURL.isEmpty())
        {
            invokeExpressionOfBuildingURL.forEach(invokeExpression ->
            {
                String url = invokeExpression.getArgs()
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equals(STRING_CLASS_NAME))
                        .findFirst()
                        .get()
                        .getValue();

                urls.add(cleanQuotes(url));
            });
        }

        return urls;
    }

    @Override
    public boolean isUsingHttpCaching() {

        boolean isUsingCaching = false;
        boolean isUsingOkHttpClient = this.isUsingOkHttpClient();

        if(isUsingOkHttpClient)
        {
            OkHttpAnalyzer okHttpAnalyzer = new OkHttpAnalyzer(this.analyzedApp);
            OkHttpOldAnalyzer okHttpOldAnalyzer = new OkHttpOldAnalyzer(this.analyzedApp);

            isUsingCaching = (okHttpAnalyzer.isSettingTimeout() || okHttpOldAnalyzer.isUsingHttpCaching());
        }

        return isUsingCaching;
    }

    @Override
    public boolean isSettingTimeout() {
        boolean isUsingOkHttpClient = this.isUsingOkHttpClient();
        boolean okHttpClientIsSettingTimeout = false;

        if(isUsingOkHttpClient)
        {
            OkHttpAnalyzer okHttpAnalyzer = new OkHttpAnalyzer(this.analyzedApp);
            OkHttpOldAnalyzer okHttpOldAnalyzer = new OkHttpOldAnalyzer(this.analyzedApp);

            okHttpClientIsSettingTimeout = (okHttpAnalyzer.isSettingTimeout() || okHttpOldAnalyzer.isSettingTimeout());
        }

        return isUsingOkHttpClient && okHttpClientIsSettingTimeout;
    }

    public boolean isUsingOkHttpClient()
    {
        boolean isUsingOkhttpClient = false;

        if(isUsingOtherCient())
        {
            isUsingOkhttpClient = this.findInvocationsOfMethod(RETROFIT_CLIENT_SIGNATURE_OLD).stream().anyMatch(invokeExpression ->
                                                            invokeExpression.getArgs()
                                                            .keySet()
                                                            .contains(RETROFIT_OKHTTP_OLD_CLIENT));
        }
        return isUsingOkhttpClient;
    }

    private boolean isUsingOtherCient()
    {
        return this.findMethodUsage(RETROFIT_CLIENT_SIGNATURE_OLD);
    }

    public  boolean isBuildingRetrofit()
    {
        return findMethodUsage(RETROFIT_BUILD_SIGNATURE_OLD);
    }

    public boolean isCreatedInterface(RetrofitInterface retrofitInterface)
    {
        //TODO
        String retrofitInterfaceName = convertIntoPath(retrofitInterface.getRetrofitInterface().getName());

        List<HarissaBody.InvokeExpression> invokationOfCreateMethod = findInvocationsOfMethod(RETROFIT_CREATE_API_INTERFACE_SIGNATURE_OLD);
        boolean isCreated;

        isCreated = invokationOfCreateMethod.stream().anyMatch(invokeExpression -> (invokeExpression.findInArgs(CLASS_PACKAGE_NAME, retrofitInterfaceName)));

        return isCreated;
    }

}
