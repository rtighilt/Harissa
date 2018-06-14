package com.uqam.latece.nosy.analyzers.clients.Volley;

import com.uqam.latece.harissa.models.HarissaBody;
import com.uqam.latece.nosy.analyzers.clients.IClientInstance;
import soot.Local;

public class VolleyInstance extends IClientInstance{


    public VolleyInstance(HarissaBody declaringBody, Local firstLocal)
    {
        super(declaringBody, firstLocal);

        this.instanceType = firstLocal.getType().toString();
        this.buildInstanceGraph();
    }

    @Override
    protected void buildTargettedUrls() {

    }
}
