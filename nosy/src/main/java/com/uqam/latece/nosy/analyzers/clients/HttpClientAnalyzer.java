package com.uqam.latece.nosy.analyzers.clients;

import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.harissa.models.HarissaBody;
import com.uqam.latece.harissa.models.HarissaClass;
import com.uqam.latece.harissa.models.HarissaMethod;
import com.uqam.latece.nosy.analyzers.Analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class HttpClientAnalyzer extends Analyzer implements IHttpClientAnalyzer
{
    protected String httpClientPackageName;
    protected List<HarissaClass> classesReferencingPackage;
    protected List<HarissaBody> bodiesInitializingHttpClient;
    protected String instanceType;
    protected List<IClientInstance> instances;

    public HttpClientAnalyzer(HarissaApp analyzedApp)
    {
        super(analyzedApp);
    }

    //protected abstract List<IClientInstance> findHttpLibraryInstances();

    //protected abstract boolean isConnectingToRESTService();

    public abstract List<String> findCallsDestinations();

    protected List<HarissaBody> findBodiesInvokingMethod(String methodSignature)
    {
        List<HarissaBody> bodiesInvokingMethod;

         bodiesInvokingMethod = getBodiesOfClassesReferencingPackage().stream()
                                                                      .filter(body ->
                                                                              body!=null && !body.getInvokeExpressionOfMethodWithSignature(methodSignature).isEmpty())
                                                                      .collect(Collectors.toList());

         return bodiesInvokingMethod;
    }

    protected List<HarissaBody> findBodiesInitializingClient(String instanceType)
    {
        List<HarissaBody>  bodiesWithAssignExpression;
        List<HarissaBody>  bodiesInitializingClient = new ArrayList<>();

        bodiesWithAssignExpression = getBodiesOfClassesReferencingPackage().stream()
                                                                           .filter(body -> body != null && !body.getAssignExpressions().isEmpty())
                                                                           .collect(Collectors.toList());

        bodiesWithAssignExpression.forEach(body ->
        {
                                                   if(bodyInitializeClient(body, instanceType))
                                                   {
                                                       bodiesInitializingClient.add(body);
                                                   }
        });

        return bodiesInitializingClient;
    }

    private boolean bodyInitializeClient(HarissaBody body, String instanceType)
    {
       return body.getAssignExpressions().stream()
                                         .anyMatch(assignExpression ->
                                                assignExpression.leftIsLocal() && assignExpression.isNewAssign() && assignExpression.getRight()
                                                        .getType()
                                                        .toString()
                                                        .equals(instanceType));
    }

    protected List<HarissaBody.InvokeExpression> findInvocationsOfMethod(String methodSignature)
    {
        List<HarissaBody.InvokeExpression> invokeExpressions = new ArrayList<>();

        getBodiesOfClassesReferencingPackage().forEach( body ->
        {
            if(!body.getInvokeExpressionOfMethodWithSignature(methodSignature).isEmpty())
                invokeExpressions.addAll(body.getInvokeExpressionOfMethodWithSignature(methodSignature));
        });

        return invokeExpressions;
    }

    protected boolean findMethodUsage(String methodSignature)
    {
        return getBodiesOfClassesReferencingPackage().stream()
                                                     .anyMatch(body ->
                                                             !body.getInvokeExpressionOfMethodWithSignature(methodSignature).isEmpty());
    }

    protected boolean findInitializationOf(String type)
    {
        return getBodiesOfClassesReferencingPackage().stream()
                                                     .anyMatch(body ->
                                                             body.getAssignExpressions().stream()
                                                                                        .anyMatch(assignExpression ->
                                                                                                  assignExpression.getRight()
                                                                                                                  .getType()
                                                                                                                  .toString()
                                                                                                                  .equals(type)));
    }
    public String getHttpClientPackageName() {
        return httpClientPackageName;
    }

    public List<IClientInstance> getInstances() {
        return instances;
    }

    public List<HarissaClass> getClassesReferencingPackage() {
        return classesReferencingPackage;
    }

    protected List<HarissaBody> getBodiesOfClassesReferencingPackage()
    {
        List<HarissaBody> bodies = new ArrayList<>();

        classesReferencingPackage.forEach(harissaClass ->
                harissaClass.getMethods()
                            .forEach(method -> {
                               if(method.hasActiveBody()) bodies.add(method.getBody());
                        }));
        return bodies;
    }

    public boolean isReferencingHttpClient(){
        return !getBodiesOfClassesReferencingPackage(httpClientPackageName).isEmpty();
    }

    public abstract boolean isMakingSynchronousCalls();

    public abstract boolean isMakingAsynchronousCalls();

    protected List<HarissaClass> findAsyncTaskClasses()
    {
        List<HarissaClass> asyncTasks = this.analyzedApp.getApplicationClasses().stream()
                                                                                .filter(harissaClass -> harissaClass.isAsyncTask())
                                                                                .collect(Collectors.toList());

        return asyncTasks;
    }

    protected List<HarissaBody> doInBackgroundBodies()
    {
        List<HarissaMethod> methods = new ArrayList<>();
        List<HarissaBody> bodies = new ArrayList<>();

        this.findAsyncTaskClasses().stream().forEach(asyncTask ->
        {
            methods.addAll( asyncTask.getMethods().stream().filter(method -> method.getName().equals("doInBackground")).collect(Collectors.toList()));
        });

        methods.stream().forEach(method -> bodies.add(method.getBody()));

        return bodies;
    }

}
