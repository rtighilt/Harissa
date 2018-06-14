package com.uqam.latece.harissa.models;


import soot.SootClass;
import soot.SootMethod;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.uqam.latece.harissa.helpers.AndroidUtils.*;


public class HarissaClass{

    private SootClass sootClass;
    private HarissaClassType classType;
    private HarissaApp harissaApp;
    private List<HarissaMethod> methods;
    private List<HarissaField> fields;
    private List<Annotation> annotations;

    public HarissaClass(SootClass sootClass)
    {
        this.sootClass = sootClass;
    }

    private HarissaClass(SootClass sootClass, HarissaApp harissaApp)
    {
        this.sootClass = sootClass;
        this.harissaApp = harissaApp;
        this.setClassType();
        this.loadMethods();
        this.loadFields();
    }

    public void setHarissaApp(HarissaApp harissaApp)
    {
        this.harissaApp = harissaApp;
        this.setClassType();
        this.loadMethods();
        this.loadFields();
    }


    //private builders
        private void loadMethods()
        {
            List<SootMethod> sootMethods = sootClass.getMethods();

            this.methods = sootMethods.stream().map(sootMethod -> new HarissaMethod(sootMethod,this)).collect(Collectors.toList());
        }

        private void setClassType()
        {
            //TODO : i need to find a better way to determine whether it is an application or library class
            if(sootClass.getPackageName().startsWith(harissaApp.getMainPackage()))
                this.classType = HarissaClassType.APPLICATION;
            else
                this.classType = HarissaClassType.LIBRARY;
        }

        private void loadFields()
        {
            List<HarissaField> fields = new ArrayList<>();
            sootClass.getFields().forEach(field -> fields.add(new HarissaField(field, this)));

            this.fields = fields;
        }
    //endregion

    public List<HarissaField> getfields()
    {
        return this.fields;
    }

    private SootClass getsootClass(){
        return sootClass;
    }


    public String getName()
    {
        return sootClass.getName();
    }

    public HarissaClassType getClassType()
    {
        return classType;
    }


    public List<HarissaMethod> getMethods()
    {
        return methods;
    }

    public List<HarissaClass> getOuterHierarchy()
    {
        List<HarissaClass> parents = new ArrayList<>();
        SootClass sootClassLoop = getsootClass();

        while (sootClassLoop.hasSuperclass())
        {
            HarissaClass parent = new HarissaClass(sootClassLoop.getSuperclass(),harissaApp);

            parents.add(parent);

            sootClassLoop = sootClassLoop.getSuperclass();
        }

        return parents;
    }

    public int getNumberOfMethods()
    {
        return this.getMethods().size();
    }

    public int getNumberOfLines()
    {
        int nbr = 0;

        for (HarissaMethod method : this.getMethods())
        {
          nbr += method.getNumberOfLines();
        }

        return nbr;
    }

    public boolean isAsyncTask()
    {
        return getOuterHierarchy().stream().anyMatch(superClass -> superClass.getName().equals(ASYNCTASK_CLASS_NAME));
    }

    public boolean isActivity()
    {
        return getOuterHierarchy().stream().anyMatch(superClass -> superClass.getName().equals(ACTIVITY_CLASS_NAME));
    }

    public boolean isService()
    {
        return getOuterHierarchy().stream().anyMatch(superClass -> superClass.getName().equals(SERVICE_CLASS_NAME));
    }

    public boolean isFragment()
    {
        return getOuterHierarchy().stream().anyMatch(superClass -> superClass.getName().equals(FRAGMENT_CLASS_NAME));
    }

    public boolean isInterface()
    {
        return this.sootClass.isInterface();
    }
    public enum HarissaClassType
    {
        APPLICATION,
        LIBRARY
    }
}
