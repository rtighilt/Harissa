package com.uqam.latece.nosy.analyzers.clients.Retrofit;

import com.uqam.latece.harissa.models.HarissaClass;
import com.uqam.latece.harissa.models.HarissaMethod;

import java.util.List;

public class RetrofitInterface {

    private HarissaClass retrofitInterface;
    private boolean isCreated;
    private List<HarissaMethod> methods;

    public RetrofitInterface(HarissaClass harissaClass)
    {
        this.retrofitInterface = harissaClass;
        this.methods = harissaClass.getMethods();
    }

    public RetrofitInterface(HarissaClass harissaClass, boolean isCreated)
    {
        this.retrofitInterface = harissaClass;
        this.isCreated = isCreated;
        this.methods = harissaClass.getMethods();
    }

    public HarissaClass getRetrofitInterface() {
        return retrofitInterface;
    }

    public void setRetrofitInterface(HarissaClass retrofitInterface) {
        this.retrofitInterface = retrofitInterface;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean created) {
        isCreated = created;
    }
}
