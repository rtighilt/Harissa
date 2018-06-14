package com.uqam.latece.nosy;

import com.uqam.latece.harissa.factory.HarissaBuilder;
import com.uqam.latece.harissa.factory.loaders.LoaderConfiguration;
import com.uqam.latece.harissa.models.Apk;
import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.nosy.printers.OutputPrinter;
import me.tongfei.progressbar.ProgressBar;
import org.apache.commons.io.FilenameUtils;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


import static com.uqam.latece.harissa.helpers.DecompilerHelper.readFileInList;

public class App {

    public static void main(String[] args) throws IOException {

        File sourceFolder = new File(args[0]);

        List<File> apkPaths = getPaths(sourceFolder);

        LoaderConfiguration loaderConfiguration = buildConfiguration(args);

        ProgressBar progressBar = new ProgressBar("Analyzing", apkPaths.size());


        ExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Future future = null;


        progressBar.start();
        //progressBar.stepBy(1380);


        for (File apk : apkPaths) {

            String apkName = apk.getName().substring(0, apk.getName().length() - 4);

                try {

                    analyzerCallable analyzerCallable = new analyzerCallable(new Apk(apk.getAbsolutePath()), loaderConfiguration, args[3], args[4]);
                    future = service.submit(analyzerCallable);
                    future.get(5, TimeUnit.MINUTES);
                    progressBar.step();


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    System.out.println("Timeout");
                    future.cancel(true);
                } finally {
                    //todo
                }


        }
        progressBar.stop();
        service.shutdownNow();


    }

    public static LoaderConfiguration buildConfiguration(String[] args){

        List<String> blackList = readFileInList(args[2]);

        LoaderConfiguration loaderConfiguration = new LoaderConfiguration.Builder().androidPlatformPath(args[1])
                                                                                   .excludedPackages(blackList)
                                                                                   .build();

        return loaderConfiguration;
    }

    public static Apk buildAPK(String[] args) throws IOException
    {
        return new Apk(args[0]);
    }

    public static List<File> getPaths(File folder)
    {
        List<File> paths = Arrays.asList(folder.listFiles());

        paths = paths.stream().filter(path -> FilenameUtils.getExtension(path.getAbsolutePath()).equals("apk")).collect(Collectors.toList());

        return paths;
    }


    public static void analyzeAPK(Apk apk, LoaderConfiguration loaderConfiguration, String outputFile, String subOutputFile) throws IOException
    {
        HarissaApp application = new HarissaBuilder(apk, loaderConfiguration).buildApp();

        OutputPrinter outputPrinter = new OutputPrinter(outputFile, subOutputFile);


        outputPrinter.print(application);
    }


    static class analyzerCallable implements Callable {

        Apk apk;
        LoaderConfiguration loaderConfiguration;
        String outputFile;
        String subOutputFile;

        public analyzerCallable(Apk apk, LoaderConfiguration loaderConfiguration, String outputFile, String subOutputFile)
        {
            this.apk = apk;
            this.loaderConfiguration = loaderConfiguration;
            this.outputFile = outputFile;
            this.subOutputFile = subOutputFile;
        }

        @Override
        public Object call() throws Exception {
            analyzeAPK(apk,loaderConfiguration,outputFile,subOutputFile);
            return true;
        }
    }

}




