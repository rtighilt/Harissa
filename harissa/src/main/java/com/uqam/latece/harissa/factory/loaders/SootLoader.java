package com.uqam.latece.harissa.factory.loaders;


import com.uqam.latece.harissa.models.Apk;
;
import com.uqam.latece.harissa.helpers.DecompilerHelper;
import soot.*;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.options.Options;
import soot.util.Chain;

import java.security.PrivateKey;
import java.util.Collections;


public class SootLoader extends Loader {

    //private SetupApplication app;

    private LoaderConfiguration loaderConfiguration;

    public SootLoader(Apk apk, LoaderConfiguration options)
    {
        super.apk = apk;
        this.loaderConfiguration = options;
        //this.app = new SetupApplication(this.loaderConfiguration.getAndroidPlatformPath(), apk.getPath());
        init();
    }

    public SootLoader(Apk apk)
    {
        super.apk = apk;
        init();
    }

    @Override
    public void init(){

//        app.setCallbackFile("/Users/Mac/Documents/Studies/Master/Framworks/Harissa/src/main/resources/AndroidCallBacks.txt");
//        app.constructCallgraph();
//        app.printEntrypoints();
//        app.calculateSourcesSinksEntrypoints("D:\\Arbeit\\Android Analyse\\soot-infoflow-android\\SourcesAndSinks.txt");
        G.reset();

        Options.v().set_allow_phantom_refs(true);
        Options.v().set_process_dir(Collections.singletonList(apk.getPath()));
        Options.v().set_src_prec(Options.src_prec_apk);
        Options.v().set_android_jars(this.loaderConfiguration.getAndroidPlatformPath());
        Options.v().set_output_format(Options.output_format_jimple);
        Options.v().set_output_dir(DecompilerHelper.SOOT_OUTPUT_DIRECTORY + apk.getName());
        Options.v().set_app(true);
        Options.v().set_whole_program(true);
        Options.v().set_process_multiple_dex(true);
        Options.v().setPhaseOption("cg.spark", "on");


        Options.v().set_exclude(loaderConfiguration.getExcludedPackages());
        Options.v().set_no_bodies_for_excluded(true);
    }

    @Override
    public void runLoading()
    {
        Scene.v().loadNecessaryClasses();
        PackManager.v().runPacks();

    }

    public Chain<SootClass> getClasses(){
        return Scene.v().getClasses();
    }

    public CallGraph getCallGraph() {
        return  Scene.v().getCallGraph();
    }

    public void writeOutput(){
        PackManager.v().writeOutput();
    }

}

