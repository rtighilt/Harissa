package com.uqam.latece.nosy.analyzers.clients.Retrofit;

import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.harissa.models.HarissaBody;
import com.uqam.latece.harissa.models.HarissaClass;
import com.uqam.latece.harissa.models.HarissaMethod;
import com.uqam.latece.nosy.analyzers.clients.HttpClientAnalyzer;
import com.uqam.latece.nosy.analyzers.clients.IClientInstance;
import com.uqam.latece.nosy.analyzers.clients.OkHttp.OkHttpAnalyzer;
import com.uqam.latece.nosy.analyzers.clients.OkHttp.OkHttpOldAnalyzer;
import soot.Local;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.uqam.latece.nosy.Utils.AnalysisUtils.*;
import static com.uqam.latece.nosy.Utils.RetrofitAnalysisHelper.*;

public class RetrofitAnalyzer extends HttpClientAnalyzer {

    protected List<RetrofitInterface> apiInterfaces;

    public RetrofitAnalyzer(HarissaApp analyzedApp)
    {
        super(analyzedApp);
        this.httpClientPackageName         = RETROFIT_PACKAGE_NAME;
        this.instanceType                  = RETROFIT_INSTANCE;
        this.classesReferencingPackage     = findClassesReferencigPackage(httpClientPackageName);
        this.bodiesInitializingHttpClient  = findBodiesInitializingClient(instanceType);
        this.instances                     = findHttpLibraryInstances();
        this.apiInterfaces                 = findApiInterfaces();
    }

    @Override
    public List<IClientInstance> findHttpLibraryInstances()
    {
        List<RetrofitInstance> retrofitInstances = new ArrayList<>();

        this.bodiesInitializingHttpClient.forEach(body ->
        {
            retrofitInstances.addAll(body.getAssignExpressions().stream()
                                       .filter(assignExpression ->
                                                   assignExpression.leftIsLocal() &&
                                                   assignExpression.isNewAssign() &&
                                                   assignExpression.getRight()
                                                                   .getType()
                                                                   .toString()
                                                                   .equals(instanceType))
                                       .collect(Collectors.toList())
                                       .stream()
                                       .map(assignExpression ->
                                               new RetrofitInstance(body,(Local)assignExpression.getLeft()))
                                       .collect(Collectors.toList()));
        });


        return retrofitInstances.stream()
                                .map(retrofitInstance -> (IClientInstance) retrofitInstance)
                                .collect(Collectors.toList());
    }

    @Override
    public boolean isConnectingToRESTService() {
        boolean isBuildingRetrofit        = isBuildingRetrofit();
        boolean isCreatingAnyApiInterface = isCreatingApiInteface();
        boolean isPreparingApiCall = isPreparingApiCall();
        boolean isMakingAsynchounousCalls = isMakingAsynchronousCalls();
        boolean isMakingSynchounousCalls = isMakingSynchronousCalls();

        return (isBuildingRetrofit && isCreatingAnyApiInterface && isPreparingApiCall && (isMakingSynchounousCalls || isMakingAsynchounousCalls));
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
            okHttpClientIsSettingTimeout = okHttpAnalyzer.isSettingTimeout();
        }

        return isUsingOkHttpClient && okHttpClientIsSettingTimeout;
    }

    private boolean isUsingOkHttpClient(){
        return findMethodUsage(RETROFIT_CLIENT_SIGNATURE);
    }


    @SuppressWarnings("ConstantConditions")
    @Override
    public List<String> findCallsDestinations()
    {
        List<String> urls = new ArrayList<>();
        List<HarissaBody.InvokeExpression> invokeExpressionOfBuildingURL = findInvocationsOfMethod(RETROFIT_BUILDER_SETURL_SIGNATURE);

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
    public boolean isMakingSynchronousCalls() {
        return findMethodUsage(RETROFIT_EXECUTE_SIGNATURE);
    }
    @Override
    public boolean isMakingAsynchronousCalls()
    {
        return findMethodUsage(RETOFIT_ENQUEUE_SIGNATURE);
    }

    public  boolean isBuildingRetrofit()
    {
        return findMethodUsage(RETROFIT_BUILD_SIGNATURE);
    }

    public boolean isPreparingApiCall()
    {
        boolean isInvokingAnyMethod;

        List<HarissaMethod> methodsFromApiInterfaces = new ArrayList<>();

        this.apiInterfaces.forEach(apiInterface -> methodsFromApiInterfaces.addAll(apiInterface.getRetrofitInterface().getMethods()));

        isInvokingAnyMethod = getBodiesOfClassesReferencingPackage().stream().anyMatch(body -> body.getInvokeExpressions()
            .stream()
            .anyMatch(invokeExpression ->
                    methodsFromApiInterfaces.stream()
                                            .anyMatch(method ->
                                                    method.getSootMethod()
                                                          .getSignature()
                                                          .equals(invokeExpression.getMethodSignature()))));
        return isInvokingAnyMethod;
    }

    public List<RetrofitInterface> findApiInterfaces()
    {
        List<RetrofitInterface> retrofitInterfaces = new ArrayList<>();

        this.classesReferencingPackage.stream()
                                      .filter(this::classIsApiInterface)
                                      .forEach(harissaClass -> {
                                          RetrofitInterface retrofitInterface = new RetrofitInterface(harissaClass);
                                          if(isCreatedInterface(retrofitInterface)) retrofitInterface.setCreated(true);
                                          retrofitInterfaces.add(retrofitInterface);
                                      });


        return retrofitInterfaces;
    }

    private List<RetrofitInterface> getApiInterfaces()
    {
        return apiInterfaces;
    }

    protected boolean isCreatingApiInteface()
    {
        return getApiInterfaces().stream()
                                 .anyMatch(RetrofitInterface::isCreated);
    }


    private boolean classIsApiInterface(HarissaClass harissaClass)
    {
        boolean isInterface;
        boolean referencesRetrofit;
        boolean hasMethodsWithRetrofitAnnotations;

        isInterface = harissaClass.isInterface();
        referencesRetrofit = classesReferencingPackage.contains(harissaClass);
        hasMethodsWithRetrofitAnnotations = harissaClass.getMethods().stream().anyMatch(method -> method.hasAnnotationsByPackage(httpClientPackageName));

        return (isInterface && referencesRetrofit && hasMethodsWithRetrofitAnnotations);
    }

    public boolean isCreatedInterface(RetrofitInterface retrofitInterface)
    {
        //TODO
        String retrofitInterfaceName = convertIntoPath(retrofitInterface.getRetrofitInterface().getName());

        List<HarissaBody.InvokeExpression> invokationOfCreateMethod = findInvocationsOfMethod(RETROFIT_CREATE_API_INTERFACE_SIGNATURE);
        boolean isCreated;

        isCreated = invokationOfCreateMethod.stream().anyMatch(invokeExpression -> (invokeExpression.findInArgs(CLASS_PACKAGE_NAME, retrofitInterfaceName)));

        return isCreated;
    }

}