package uqam.latece.harissa;


import com.uqam.latece.harissa.factory.HarissaBuilder;
import com.uqam.latece.harissa.factory.loaders.LoaderConfiguration;
import com.uqam.latece.harissa.models.Apk;
import com.uqam.latece.harissa.models.HarissaApp;

import me.tongfei.progressbar.ProgressBar;
import org.apache.commons.io.FilenameUtils;
import uqam.latece.harissa.printers.OutputPrinter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.uqam.latece.harissa.helpers.DecompilerHelper.readFileInList;

public class Main {

    public static Apk apk;
    public static LoaderConfiguration loaderConfiguration;

    public static void main(String[] args) throws IOException
    {
        File sourceFolder = new File(args[0]);

        List<File> apkPaths = getPaths(sourceFolder);


        loaderConfiguration = buildConfig(args);

        List<String> aymenApps = readFileInList(args[4]);

        ProgressBar progressBar = new ProgressBar("Analyzing", apkPaths.size());

        progressBar.start();

        apkPaths.forEach(apk ->
        {
            String apkName =  apk.getName().substring(0,apk.getName().length()-4);
//            if(aymenApps.contains("\uFEFF"+apkName))
//            {
                try
                {
                    analyzeAPK(new Apk(apk.getAbsolutePath()), loaderConfiguration, args[3]);
                    progressBar.step();
                }

                catch (Exception e) {
                    progressBar.step();
                }
            //}
           // else progressBar.step();
        });

        progressBar.stop();

    }

    public static LoaderConfiguration buildConfig(String[] args)
    {
        String androidJarPath = args[1];
        List<String> blackList = readFileInList(args[2]);

        return new LoaderConfiguration.Builder().androidPlatformPath(androidJarPath)
                                                .excludedPackages(blackList)
                                                .build();
    }

    public static Apk buildAPK(String[] args) throws IOException
    {
        return new Apk(args[0]);
    }

    public static List<String> getAymenApps(){
        return readFileInList("to_analyze.csv");

    }


    public static List<File> getPaths(File folder)
    {
        List<File> paths = Arrays.asList(folder.listFiles());
        paths = paths.stream().filter(path -> FilenameUtils.getExtension(path.getAbsolutePath()).equals("apk")).collect(Collectors.toList());

        return paths;
    }

    public static void analyzeAPK(Apk apk, LoaderConfiguration loaderConfiguration, String outputFile) throws IOException
    {
        HarissaApp application = new HarissaBuilder(apk, loaderConfiguration).buildApp();

        String apkName =  apk.getName();


        Purifier purifier = new Purifier(application);
        purifier.runAnalysis();

        OutputPrinter outputPrinter = new OutputPrinter(outputFile);

        outputPrinter.print(purifier, application.getMainPackage(), apkName);
    }

}
