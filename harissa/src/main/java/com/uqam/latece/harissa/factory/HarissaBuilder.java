package com.uqam.latece.harissa.factory;

import com.uqam.latece.harissa.factory.loaders.LoaderConfiguration;
import com.uqam.latece.harissa.factory.loaders.ManifestLoader;
import com.uqam.latece.harissa.factory.loaders.SootLoader;
import com.uqam.latece.harissa.models.*;
import soot.jimple.toolkits.callgraph.CallGraph;

import java.util.List;
import java.util.stream.Collectors;

public class HarissaBuilder extends AbstractHarissaBuilder{

    private SootLoader     sootLoader;
    private ManifestLoader manifestLoader;

    public HarissaBuilder(Apk apk, LoaderConfiguration loaderConfiguration)
    {
        this.sootLoader     = new SootLoader(apk, loaderConfiguration);
        this.manifestLoader = new ManifestLoader(apk);
    }

    private void runLoaders()
    {
        this.sootLoader.runLoading();
        this.manifestLoader.runLoading();
    }

    public HarissaApp buildApp()
    {
        this.runLoaders();

        List<HarissaClass> harissaClasses = sootLoader.getClasses()
                                                      .stream()
                                                      .map(HarissaClass::new)
                                                      .collect(Collectors.toList());

        CallGraph callGraph = sootLoader.getCallGraph();
        Manifest manifest = manifestLoader.getManifest();


        return new HarissaApp.Builder().name(manifest.getApplicationName())
                                       .classes(harissaClasses)
                                       .mainPackage(manifest.getPackageName())
                                       .minSdkVersion(manifest.getMinSDK())
                                       .maxSdkVersion(manifest.getMaxSDK())
                                       .targetSdkVersion(manifest.getTargettedSDK())
                                       .permissions(manifest.getUsedPermissions())
                                       .activities(manifest.getActivities())
                                       .services(manifest.getServices())
                                       .broadcastReceivers(manifest.getReceivers())
                                       .callGraph(callGraph)
                                       .build();
    }
}