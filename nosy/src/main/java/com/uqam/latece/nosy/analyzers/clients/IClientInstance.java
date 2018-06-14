package com.uqam.latece.nosy.analyzers.clients;

import com.uqam.latece.harissa.models.HarissaBody;

import com.uqam.latece.harissa.models.HarissaMethod;
import soot.Local;

import java.util.ArrayList;
import java.util.List;

public abstract class IClientInstance
{
    protected String instanceType;
    protected HarissaBody declaringBody;
    protected List<Tuple<HarissaBody, Local>> tuplesBodyLocal = new ArrayList<>();
    protected List<String> targettedUrls = new ArrayList<>();

    public IClientInstance(HarissaBody declaringBody, Local firstLocal)
    {
        this.declaringBody = declaringBody;
        this.tuplesBodyLocal.add(new Tuple<>(declaringBody,firstLocal));
    }


    protected void buildInstanceGraph()
    {
        HarissaBody bodyIterator = declaringBody;
        Local localIterator = tuplesBodyLocal.listIterator().next().y;

        do
        {
            List<HarissaBody.InvokeExpression> invokeExprs = bodyIterator.getInvokeExpressions();

            for(HarissaBody.InvokeExpression invokeExpression : invokeExprs)
            {
                if(invokeExpression.findInArgs(instanceType,localIterator.getName()))
                {
                    HarissaMethod method = invokeExpression.getInvokingMethod();
                    if (method.hasActiveBody())
                    {
                        int indexOfArg = invokeExpression.getArgIndex(instanceType, localIterator.getName());

                        localIterator = findLocalAsParamInBody(method.getBody(), indexOfArg);
                        bodyIterator = method.getBody();

                        this.tuplesBodyLocal.add(new Tuple<>(method.getBody(), localIterator));
                    }
                }
            }
        }
         while(localIsTransferedAsParameter(localIterator, bodyIterator));
    }


    protected abstract void buildTargettedUrls();

    private boolean localIsTransferedAsParameter(Local local, HarissaBody body)
    {
        List<HarissaBody.InvokeExpression> invokeExprs = body.getInvokeExpressions();

        return invokeExprs.stream()
                          .anyMatch(invokeExpression ->
                                    invokeExpression.findInArgs(instanceType,local.getName()));

    }

    public boolean localCameAsParameter(Local local, HarissaBody body)
    {
        List<Local> parameterLocals = body.getParameterLocals();

        return parameterLocals.stream()
                              .anyMatch(parameterLocal -> parameterLocal.equals(local));
    }

    private Local findLocalAsParamInBody(HarissaBody body, int indexOfArg)
    {
        return body.getParameterLocals().get(indexOfArg - 1);
    }

    protected abstract class State
    {

    }

}
