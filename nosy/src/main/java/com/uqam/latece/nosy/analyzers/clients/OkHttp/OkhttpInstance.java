package com.uqam.latece.nosy.analyzers.clients.OkHttp;

import com.uqam.latece.harissa.models.HarissaBody;
import com.uqam.latece.nosy.analyzers.clients.IClientInstance;
import soot.Local;

public class OkhttpInstance extends IClientInstance{

    public OkhttpInstance(HarissaBody declaringBody, Local local)
    {
        super(declaringBody, local);

        this.instanceType = local.getType().toString();
        this.buildInstanceGraph();
    }

    @Override
    protected void buildTargettedUrls()
    {
        //TODO build how to find URLs in OKHttp Instances
    }

}
