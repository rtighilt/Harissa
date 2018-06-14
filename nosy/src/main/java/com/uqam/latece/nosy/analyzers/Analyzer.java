package com.uqam.latece.nosy.analyzers;

import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.harissa.models.HarissaBody;
import com.uqam.latece.harissa.models.HarissaClass;
import com.uqam.latece.harissa.models.HarissaMethod;
import com.uqam.latece.nosy.configurations.AnalyzingConfiguraion;
import soot.jimple.InvokeExpr;
import soot.jimple.JimpleBody;

import java.util.ArrayList;
import java.util.List;

public abstract class Analyzer {

    protected HarissaApp analyzedApp;
    private AnalyzingConfiguraion configuraion;

    public Analyzer(HarissaApp analyzedApp, AnalyzingConfiguraion configuraion) {
        this.analyzedApp = analyzedApp;
        this.configuraion = configuraion;
    }

    public Analyzer(HarissaApp analyzedApp) {
        this.analyzedApp = analyzedApp;
    }

    protected List<HarissaMethod> getAllMethods() {
        List<HarissaMethod> methods = new ArrayList<>();

        this.analyzedApp.getClasses().forEach(harissaClass -> methods.addAll(harissaClass.getMethods()));

        return methods;
    }

    protected List<HarissaMethod> getAllApplicationMethods() {
        List<HarissaMethod> methods = new ArrayList<>();

        this.analyzedApp.getApplicationClasses()
                .forEach(harissaClass -> methods.addAll(harissaClass.getMethods()));

        return methods;
    }

    protected List<HarissaBody> extractBodies() {
        List<HarissaBody> bodies = new ArrayList<>();
        getAllMethods().forEach(method -> bodies.add(method.getBody()));

        return bodies;
    }

    public List<HarissaClass> findClassesReferencigPackage(String packageName) {
        List<HarissaClass> classesReferencingThePackage = new ArrayList<>();

        this.analyzedApp.getApplicationClasses().forEach(harissaClass -> {
            if (classReferencesPackage(harissaClass, packageName))
                classesReferencingThePackage.add(harissaClass);
        });
        return classesReferencingThePackage;
    }

    public boolean appReferencesPackage(String packageName) {
        return (!findClassesReferencigPackage(packageName).isEmpty());
    }

    private boolean classReferencesPackage(HarissaClass harissaClass, String packageName) {
        boolean hasReferentField;
        boolean hasReferentMethod;
        boolean extendsPackage;
        boolean classHasReferentAnnotations;
        boolean classMethodsHaveReferentAnnotations;

        try {
            hasReferentField = harissaClass.getfields().stream().anyMatch(field -> field.getType().contains(packageName));
            hasReferentMethod = harissaClass.getMethods().stream().anyMatch(harissaMethod -> harissaMethod.hasActiveBody() && harissaMethod.getBody().getPlainBody().contains(packageName));
            extendsPackage = harissaClass.getOuterHierarchy().stream().anyMatch(parent -> parent.getName().contains(packageName));
            classMethodsHaveReferentAnnotations = harissaClass.getMethods().stream().anyMatch(method -> method.hasAnnotationsByPackage(packageName));

            return (hasReferentField || hasReferentMethod || extendsPackage || classMethodsHaveReferentAnnotations);
        } catch (NullPointerException e) {
            return false;
        }

    }

    protected boolean findMethodUsageInClasses(String methodSignature, String packageName) {
        return getBodiesOfClassesReferencingPackage(packageName).stream()
                .anyMatch(body ->
                        !body.getInvokeExpressionOfMethodWithSignature(methodSignature).isEmpty());
    }

    protected List<HarissaClass> getClassesReferencingPackage(String packageName) {
        List<HarissaClass> classes = new ArrayList<>();


        return classes;
    }

    protected List<HarissaBody> getBodiesOfClassesReferencingPackage(String packageName) {
        List<HarissaBody> bodies = new ArrayList<>();

        this.findClassesReferencigPackage(packageName).forEach(harissaClass ->
                harissaClass.getMethods()
                        .forEach(method -> {
                            if (method.hasActiveBody()) bodies.add(method.getBody());
                        }));

        return bodies;
    }
}