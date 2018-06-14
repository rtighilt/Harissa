package com.uqam.latece.nosy.analyzers.clients.HttpURLConnection;

import com.uqam.latece.harissa.models.HarissaBody;
import com.uqam.latece.nosy.analyzers.clients.IClientInstance;
import soot.Local;

public class HttpURLConnectionInstance extends IClientInstance {

    public HttpURLConnectionInstance(HarissaBody declaringBody, Local local)
    {
        super(declaringBody,local);

        this.instanceType = local.getType().toString();
        this.buildInstanceGraph();
    }

    @Override
    protected void buildTargettedUrls()
    {
            
    }
}
