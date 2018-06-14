package uqam.latece.harissa.printers;

import com.opencsv.CSVWriter;
import org.apache.commons.io.FileUtils;
import uqam.latece.harissa.Purifier;
import uqam.latece.harissa.loaders.HttpClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

public class OutputPrinter {

    String outputFilePath;

    public OutputPrinter(String outputFilePath) throws IOException
    {
        this.outputFilePath = outputFilePath;
    }

    public void print (Purifier purifier, String packageName, String name) throws IOException
    {
        FileWriter fileWriter =  new FileWriter(outputFilePath, true);

        CSVWriter csvWriter = new CSVWriter(fileWriter);

        OutputEntry outputEntry = mapIntoOutputEntry(purifier, packageName, name);

        csvWriter.writeNext(outputEntry.getArrayEntry(),false);

        csvWriter.close();
    }

    public void print() throws IOException {

        BufferedWriter bw = null;

        FileUtils.writeByteArrayToFile(new File(outputFilePath), "sss".getBytes(), true);


    }



    private OutputEntry mapIntoOutputEntry(Purifier purifier, String packageName, String name)
    {
        OutputEntry outputEntry = new OutputEntry();
        StringBuilder httpClientsListBuilder = new StringBuilder();
        Iterator<HttpClient> httpClientsIterator =  purifier.referencedHttpClients().stream().iterator();

        outputEntry.internet_permission         = purifier.usesInternetPermission();
        outputEntry.package_name                = packageName;
        outputEntry.potentiallyConsumingRESTapi = purifier.potentiallyConsumingRESTapi();
        outputEntry.references_any_http_client  = purifier.referencesAnyHttpClient();
        outputEntry.references_multiple_http_client = ( purifier.referencedHttpClients().size() > 1);

        if ( purifier.referencesAnyHttpClient() )
        {
           httpClientsListBuilder.append("[");

           httpClientsListBuilder.append(httpClientsIterator.next().getClientName());

           while (httpClientsIterator.hasNext())
           {
               HttpClient client = httpClientsIterator.next();

               httpClientsListBuilder.append("|");
               httpClientsListBuilder.append(client.getClientName());
           }

           httpClientsListBuilder.append("]");
        }

        outputEntry.referenced_http_clients = httpClientsListBuilder.toString();
        outputEntry.name = name;

        return outputEntry;
    }
}
