package com.uqam.latece.nosy.analyzers.clients.HttpURLConnection;

import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.harissa.models.HarissaBody;
import com.uqam.latece.harissa.models.HarissaClass;
import com.uqam.latece.harissa.models.HarissaMethod;
import com.uqam.latece.nosy.analyzers.clients.HttpClientAnalyzer;
import com.uqam.latece.nosy.analyzers.clients.IClientInstance;
import soot.jimple.JimpleBody;

import java.util.ArrayList;
import java.util.List;

import static com.uqam.latece.nosy.Utils.HttpClientAnalysisHelper.*;

public class HttpUrlConnectionAnalyzer extends HttpClientAnalyzer {

    static List<HarissaBody> analyzedBodies = new ArrayList<>();

    public HttpUrlConnectionAnalyzer(HarissaApp analyzedApp)
    {
        super(analyzedApp);
        this.httpClientPackageName = HTTPURLCONNECTION_PACKAGE_NAME;
        this.classesReferencingPackage = findClassesReferencigPackage(httpClientPackageName);
    }

    @Override
    public List<IClientInstance> findHttpLibraryInstances() {
        return null;
    }

    @Override
    public boolean isConnectingToRESTService()
    {
        return (findOpennedConnection() && (isGettingInputStream() || isGettingOutputStream() || isGettingStreamFromeURL()));
    }

    @Override
    public boolean isUsingHttpCaching()
    {
        boolean installingResponseCache = isInstallingResponseCache();
        boolean isEnablingCache = isEnablingCache();
        boolean isRetrievingTimeFromHeader = isRetrievingTimeFromHeader();

        return installingResponseCache && isEnablingCache && isRetrievingTimeFromHeader;
    }

    public boolean isRetrievingTimeFromHeader(){
        return findInvocationsOfMethod(HTTPURLCONNECTION_GET_HEADER_FIELD_DATE).stream()
                .anyMatch(invokeExpression ->
                        invokeExpression.getInvoker().equals(EXPIRES) || invokeExpression.getInvoker().equals(LAST_MODIFIED));
    }
    public boolean isInstallingResponseCache() {
        boolean byReflection = findInvocationsOfMethod(CLASS_FOR_NAME).stream()
                .anyMatch(invokeExpression ->
                        invokeExpression.getInvoker().equals(HTTPURLCONNECTION_RESPONSECACHE_CLASS_NAME)) &&
                findInvocationsOfMethod(CLASS_GET_METHOD_SIGNATURE).stream()
                        .anyMatch(invokeExpression ->
                                invokeExpression.getInvoker().equals(INSTALL_METHOD));

        return findMethodUsage(HTTPURLCONNECTION_INSTALL_CACHE) || byReflection;
    }

    public boolean isEnablingCache() {
        return findInvocationsOfMethod(HTTPURLCONNECTION_SET_USE_CACHE).stream()
                .anyMatch(invokeExpression -> invokeExpression.getInvoker().equals(TRUE));
    }

    public boolean isGettingStreamFromeURL() {
        return findMethodUsage(URL_OPENSTREAM);
    }

    @Override
    public boolean isSettingTimeout() {
        boolean isSettingConnectionTimeout = isSettingConnectionTimeout();
        boolean isSettingReadingTimeout = isSettingReadingTimeout();

        return isSettingConnectionTimeout || isSettingReadingTimeout;
    }

    private boolean isSettingConnectionTimeout(){
        return findMethodUsage(HTTPURLCONNECTION_GETCONNECTIONTIMEOUT);
    }

    private boolean isSettingReadingTimeout(){
        return findMethodUsage(HTTPURLCONNECTION_SETREADINGTIMEOUT);
    }

    @Override
    public List<String> findCallsDestinations() {
        return new ArrayList<>();
    }

    @Override
    public boolean isMakingSynchronousCalls() {
        return !isMakingAsynchronousCalls();
    }

    @Override
    public boolean isMakingAsynchronousCalls()
    {
        boolean isMakingAsynchrounousCalls = false;

        if( hasAsyncTasks() )
        {
            List<HarissaBody> doInBackgroundMethods = this.doInBackgroundBodies();
            List<HarissaBody.InvokeExpression> invokeExpressions = new ArrayList<>();
            List<HarissaBody> subBodies = new ArrayList<>();

            doInBackgroundMethods.forEach(method -> invokeExpressions.addAll(method.getInvokeExpressions()));

            invokeExpressions.forEach(invokeExpression -> {

                if(invokeExpression.getInvokeExpr().getMethod().hasActiveBody()) {
                    HarissaBody body = new HarissaBody((JimpleBody) invokeExpression.getInvokeExpr().getMethod().getActiveBody());
                    subBodies.add(body);
                }
            });

            for (HarissaBody subBody : subBodies)
            {
                analyzedBodies.add(subBody);
                if(bodyIsMakingCalls(subBody)) isMakingAsynchrounousCalls = true;
            }

        }
        return isMakingAsynchrounousCalls;
    }

    private boolean bodyIsMakingCalls(HarissaBody body)
    {
        boolean bodyMakingCalls;
        boolean subBodiesMakingCalls = false;

        bodyMakingCalls = body.getPlainBody().contains(httpClientPackageName);

        for (HarissaBody.InvokeExpression invokeExpression : body.getInvokeExpressions())
        {
            if(invokeExpression.getMethod().hasActiveBody())
            {
                HarissaBody subBody = invokeExpression.getMethod().getBody();
                if(!analyzedBodies.contains(body)) {
                    if (bodyIsMakingCalls(subBody) == true) {
                        subBodiesMakingCalls = true;
                        this.analyzedBodies.add(body);
                        return subBodiesMakingCalls;
                    }
                }
            }
        }
        this.analyzedBodies.add(body);
        return bodyMakingCalls || subBodiesMakingCalls;
    }

    private boolean hasAsyncTasks()
    {
        return !this.findAsyncTaskClasses().isEmpty();
    }
    private boolean findOpennedConnection()
    {
        return findMethodUsage(URLCONNECTION_OPENCONNECTION_SIGNATURE);
    }

    private boolean isGettingInputStream()
    {
        return findMethodUsage(HTTPURLCONNECTION_GETINPUTSTREAM);
    }

    private boolean isGettingOutputStream()
    {
        return findMethodUsage(HTTPURLCONNECTION_GETOUTPUTSTREAM);
    }

    private boolean isSettingMethodRequest()
    {
        return findMethodUsage(HTTPURLCONNECTION_SETREQUESTMETHOD);
    }

    private boolean isDisconnecting()
    {
        return findMethodUsage(HTTPURLCONNECTION_DISCONNECT);
    }

}
