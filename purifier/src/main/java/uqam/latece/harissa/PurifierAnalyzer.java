package uqam.latece.harissa;

import com.uqam.latece.nosy.analyzers.Analyzer;
import com.uqam.latece.harissa.models.HarissaApp;
import uqam.latece.harissa.loaders.HttpClient;

import java.util.ArrayList;
import java.util.List;

public class PurifierAnalyzer extends Analyzer {

    private List<HttpClient> referencedClients;
    private List<HttpClient> httpClientsDictionnay;
    private boolean usesInternetPermission;

    protected PurifierAnalyzer(HarissaApp app, List<HttpClient> httpClientDictionnay)
    {
        super(app);
        this.httpClientsDictionnay = httpClientDictionnay;

        referencedClients = new ArrayList<>();
    }

    public void runAnalysis()
    {
        httpClientsDictionnay.forEach(client ->
        {
            if(appReferencesClient(client))
            {
                referencedClients.add(client);
            }
        });
    }


    public boolean usesPermission(String permissionName)
    {
      return this.analyzedApp.getPermissions().contains(permissionName);
    }

    private boolean appReferencesClient(HttpClient client)
    {
        boolean appReferencesClient = false;

        for(String packageRepresentation : client.getPackageRespresentations())
        {
            if(appReferencesPackage(packageRepresentation))
            {
                appReferencesClient = true;
            }
        }

        return appReferencesClient;
    }

//    private boolean appReferencesPackage(String packageName)
//    {
//        return (!findClassesReferencigPackage(packageName).isEmpty());
//    }
//
//    private List<HarissaClass> findClassesReferencigPackage(String packageName)
//    {
//        List<HarissaClass> classesReferencingThePackage = new ArrayList<>();
//
//        this.harissaApp.getApplicationClasses().forEach(harissaClass -> {
//            if(classReferencesPackage(harissaClass,packageName))
//                classesReferencingThePackage.add(harissaClass);
//        });
//        return classesReferencingThePackage;
//    }
//
//    private boolean classReferencesPackage(HarissaClass harissaClass, String packageName)
//    {
//        boolean hasReferentField;
//        boolean hasReferentMethod;
//        boolean extendsPackage;
//        boolean classHasReferentAnnotations;
//        boolean classMethodsHaveReferentAnnotations;
//
//        try
//        {
//            hasReferentField = harissaClass.getfields().stream().anyMatch(field -> field.getType().contains(packageName));
//            hasReferentMethod = harissaClass.getMethods().stream().anyMatch(harissaMethod -> harissaMethod.hasActiveBody() && harissaMethod.getBody().getPlainBody().contains(packageName));
//            extendsPackage = harissaClass.getOuterHierarchy().stream().anyMatch(parent -> parent.getName().contains(packageName));
//            classMethodsHaveReferentAnnotations = harissaClass.getMethods().stream().anyMatch(method -> method.hasAnnotationsByPackage(packageName));
//
//            return (hasReferentField || hasReferentMethod || extendsPackage || classMethodsHaveReferentAnnotations);
//        }
//        catch (NullPointerException e)
//        {
//            return false;
//        }
//
//    }

    public List<HttpClient> getReferencedClients() {
        return referencedClients;
    }
}
