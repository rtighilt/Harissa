package uqam.latece.harissa;

import com.uqam.latece.harissa.models.HarissaApp;
import uqam.latece.harissa.loaders.CSVReader;
import uqam.latece.harissa.loaders.HttpClient;

import java.io.IOException;
import java.util.List;

import static uqam.latece.harissa.loaders.ClientsLoaderHelper.CLIENTS_DICTIONNARY_FILE_PATH;
import static uqam.latece.harissa.PurifierHelper.INTERNET_PERMISSION;

public class Purifier
{
    private HarissaApp application;
    private PurifierAnalyzer analyzer;
    private CSVReader csvReader;

    public Purifier(HarissaApp application) throws IOException {
        this.application = application;
        this.csvReader = new CSVReader(CLIENTS_DICTIONNARY_FILE_PATH);
        this.csvReader.readFile();
        this.analyzer = new PurifierAnalyzer(application, this.extractClientsFromCSV());
    }

    public void runAnalysis()
    {
        this.analyzer.runAnalysis();
    }

    public boolean potentiallyConsumingRESTapi()
    {
        boolean referencingAnyHttpClientFromDictionnay = referencesAnyHttpClient();
        boolean usingAndroidInternetPermission         = usesInternetPermission();

        return (referencingAnyHttpClientFromDictionnay && usingAndroidInternetPermission);
    }

    public List<HttpClient> referencedHttpClients()
    {
        return analyzer.getReferencedClients();
    }

    public boolean referencesAnyHttpClient()
    {
        boolean referencesAnyHttpClient = false;

        if(!analyzer.getReferencedClients().isEmpty())
        {
            referencesAnyHttpClient = true;
        }

        return referencesAnyHttpClient;
    }


    private List<HttpClient> extractClientsFromCSV()
    {
        return csvReader.getLoadedHttpClients();
    }

    public boolean usesInternetPermission()
    {
        return this.analyzer.usesPermission(INTERNET_PERMISSION);
    }

}
