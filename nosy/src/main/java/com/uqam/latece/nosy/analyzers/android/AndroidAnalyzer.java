package com.uqam.latece.nosy.analyzers.android;

import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.harissa.models.HarissaBody;
import com.uqam.latece.harissa.models.HarissaClass;
import com.uqam.latece.nosy.analyzers.Analyzer;
import com.uqam.latece.nosy.configurations.AnalyzingConfiguraion;

import java.util.List;


public class AndroidAnalyzer extends Analyzer
{
    protected String androidSdkPackageName;

    public AndroidAnalyzer(HarissaApp analyzedApp)
    {
        super(analyzedApp);
    }

    public AndroidAnalyzer(HarissaApp analyzedApp, AnalyzingConfiguraion configuraion)
    {
        super(analyzedApp, configuraion);
    }

    protected List<HarissaClass> getClassesReferencingPackage() {
        return super.getClassesReferencingPackage(androidSdkPackageName);
    }

    protected List<HarissaBody> getBodiesOfClassesReferencingPackage() {
        return super.getBodiesOfClassesReferencingPackage(androidSdkPackageName);
    }

    protected boolean findMethodUsage(String methodSignature) {
        return super.findMethodUsageInClasses(methodSignature, androidSdkPackageName);
    }
}
