package com.uqam.latece.harissa.models;


import soot.jimple.toolkits.callgraph.CallGraph;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class
HarissaApp extends HarissaEntity
{

    private final List<HarissaClass> classes;
    private final String mainPackage;
    private final boolean isClientApplication;
    private final int minSdkVersion;
    private final int maxSdkVersion;
    private final int targetSdkVersion;
    private final List<String> permissions;
    private final List<String> activities;
    private final List<String> services;
    private final List<String> broadcasrReceivers;
    private final CallGraph callGraph;

    public int getMinSdkVersion() {
        return minSdkVersion;
    }

    public int getMaxSdkVersion() {
        return maxSdkVersion;
    }

    public int getTargetSdkVersion() {
        return targetSdkVersion;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    private HarissaApp(Builder builder)
    {
        name                 = builder.name;
        classes              = builder.classes;
        callGraph            = builder.callGraph;
        mainPackage          = builder.mainPackage;
        isClientApplication  = builder.isClientApplication;
        minSdkVersion        = builder.minSdkVersion;
        maxSdkVersion        = builder.maxSdkVersion;
        targetSdkVersion     = builder.targetSdkVersion;
        permissions          = builder.permissions;
        activities           = builder.activities;
        services             = builder.services;
        broadcasrReceivers   = builder.broadcasrReceivers;

        assignDeclaringAppToClasses();

    }

    private void assignDeclaringAppToClasses()
    {
        classes.forEach(harissaClass -> {
            harissaClass.setHarissaApp(this);
        });
    }

    public List<HarissaClass> getClasses()
    {
        return this.classes;
    }
    public List<HarissaClass> getApplicationClasses(){
        List<HarissaClass> applicationClasses;

        applicationClasses = classes.stream()
                                    .filter(harissaClass -> harissaClass.getClassType().equals(HarissaClass.HarissaClassType.APPLICATION))
                                    .collect(Collectors.toList());
        return applicationClasses;
    }


    public List<HarissaClass> getLibraryClasses(){
        List<HarissaClass> libraryClasses;

        libraryClasses = classes.stream()
                                .filter(harissaClass -> harissaClass.getClassType().equals(HarissaClass.HarissaClassType.LIBRARY))
                                .collect(Collectors.toList());
        return libraryClasses;
    }

    public String getMainPackage() {
        return mainPackage;
    }

    public Optional<HarissaClass> getClass(String name)
    {
        return classes.stream().filter(harissaClass -> harissaClass.getName().equals(name)).findFirst();
    }

    public int getNumberOfMethods()
    {
        int nbr = 0;

        for (HarissaClass harissaClass : this.filtredHarissaClasses())
        {
            nbr += harissaClass.getNumberOfMethods();
        }

        return nbr;
    }

    public List<HarissaClass> filtredHarissaClasses()
    {
        return this.getApplicationClasses().stream()
                                           .filter(harissaClass ->
        !harissaClass.getName().contains(".R$") && !harissaClass.getName().contains("$") && !harissaClass.getName().contains("BuildConfig") && !harissaClass.getName().endsWith(".R") )


                                            .collect(Collectors.toList());
    }

    public int getNumberOfClasses()
    {
        return this.filtredHarissaClasses().size();
    }

    public int getNumberOfLineOfCode()
    {
        int nbr = 0;

        for(HarissaClass harissaClass : this.filtredHarissaClasses())
        {
            nbr += harissaClass.getNumberOfLines();
        }

        return nbr;
    }

    public static class Builder
    {
        private String name;
        private List<HarissaClass> classes;
        private String mainPackage;
        private boolean isClientApplication;
        private int minSdkVersion;
        private int maxSdkVersion;
        private int targetSdkVersion;
        private List<String> permissions;
        private List<String> activities;
        private List<String> services;
        private List<String> broadcasrReceivers;
        private CallGraph callGraph;

        public Builder classes(List<HarissaClass> classes){
            this.classes = classes;
            return this;
        }

        public Builder mainPackage(String mainPackage){
            this.mainPackage= mainPackage;
            return this;
        }

        public Builder isClientApplication(boolean isClientApplication){
            this.isClientApplication= isClientApplication;
            return this;
        }

        public Builder minSdkVersion(int minSdkVersion){
            this.minSdkVersion = minSdkVersion;
            return this;
        }

        public Builder maxSdkVersion(int maxSdkVersion){
            this.maxSdkVersion = maxSdkVersion;
            return this;
        }

        public Builder targetSdkVersion(int targetSdkVersion){
            this.targetSdkVersion = targetSdkVersion;
            return this;
        }

        public Builder permissions(List<String> permissions){
            this.permissions = permissions;
            return this;
        }

        public Builder activities(List<String> activities){
            this.activities = activities;
            return this;
        }

        public Builder services(List<String> services){
            this.services = services;
            return this;
        }

        public Builder broadcastReceivers(List<String> broadcasrReceivers){
            this.broadcasrReceivers = broadcasrReceivers;
            return this;
        }

        public Builder callGraph(CallGraph callGraph){
            this.callGraph = callGraph;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public HarissaApp build(){
            return new HarissaApp(this);
        }

        public CallGraph getCallGraph() {
            return callGraph;
        }
    }
}
