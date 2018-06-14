package com.uqam.latece.nosy.analyzers.clients.Retrofit;


import com.uqam.latece.harissa.models.HarissaBody;
import com.uqam.latece.nosy.analyzers.clients.IClientInstance;
import com.uqam.latece.nosy.analyzers.clients.Tuple;
import soot.Local;
import soot.jimple.InvokeExpr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.uqam.latece.nosy.Utils.AnalysisUtils.cleanQuotes;
import static com.uqam.latece.nosy.Utils.RetrofitAnalysisHelper.RETROFIT_BUILDER_SETURL_SIGNATURE;
import static com.uqam.latece.nosy.Utils.RetrofitAnalysisHelper.RETROFIT_BUILD_SIGNATURE;

public class RetrofitInstance extends IClientInstance
{
    private RetrofitBuilderInstance builder;

    public RetrofitInstance(HarissaBody declaringBody, Local local)
    {
        super(declaringBody,local);

        this.instanceType = local.getType().toString();
        this.buildInstanceGraph();
        this.findBuilder();
    }

    private void findBuilder()
    {

        for (Tuple<HarissaBody,Local> tuple : tuplesBodyLocal)
        {

            List<HarissaBody.AssignExpression> assignExpressionsOfLocal = tuple.x.getAssignExpressions()
                                                                                 .stream()
                                                                                 .filter(assignExpression -> assignExpression.rightIsInvokeExpression() && assignExpression.getLeft().equals(tuple.y))
                                                                                 .collect(Collectors.toList());

            Optional<HarissaBody.AssignExpression> optionalAssignExpression = assignExpressionsOfLocal.stream()
                                                                                                      .filter(assignExpression ->
                                                                                                              ((InvokeExpr) assignExpression.getRight()).getMethodRef().getSignature()
                                                                                                                                                                       .equals(RETROFIT_BUILD_SIGNATURE))
                                                                                                      .findFirst();

            optionalAssignExpression.ifPresent(assignExpression ->
                    {
                        Local builderLocal = (Local) assignExpression.getRight().getUseBoxes()
                                                                                .iterator()
                                                                                .next()
                                                                                .getValue();

                        this.builder = new RetrofitBuilderInstance(tuple.x, builderLocal, this);
                    });
        }

        //return new RetrofitBuilderInstance();
    }

    @Override
    protected void buildTargettedUrls()
    {
        List<String> baseUrls = findAllSettingOfBaseUrl();
        List<String> endpoints;
    }

    private List<String> findAllSettingOfBaseUrl()
    {
        List<HarissaBody.InvokeExpression> invokationOfBaseURL = new ArrayList<>();
        List<String> baseUrls = new ArrayList<>();
        for ( Tuple<HarissaBody,Local> tuple : tuplesBodyLocal )
        {
           invokationOfBaseURL = tuple.x.getInvokeExpressionOfMethodWithSignature(RETROFIT_BUILDER_SETURL_SIGNATURE)
                                        .stream()
                                        .filter(invokeExpression ->
                                               invokeExpression.getInvoker()
                                                               .equals(tuple.y.getName()))
                                        .collect(Collectors.toList());
        }

        for (HarissaBody.InvokeExpression invokeExpression : invokationOfBaseURL)
        {
            String url = invokeExpression.getArgs()
                                         .entrySet()
                                         .iterator()
                                         .next()
                                         .getValue();

            baseUrls.add(cleanQuotes(url));
        }

        return baseUrls;
    }

    private void findRetrofitBuiltInstance()
    {

    }


    class RetrofitBuilderInstance extends IClientInstance
    {
        public RetrofitBuilderInstance(HarissaBody declaringBody, Local local, RetrofitInstance retrofitInstance)
        {
            super(declaringBody,local);

            this.instanceType = local.getType().toString();
            this.buildInstanceGraph();
        }

        @Override
        protected void buildTargettedUrls() {

        }
    }

    class RetrofitState extends State
    {

    }

}
