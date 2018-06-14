package uqam.latece.harissa.loaders;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static uqam.latece.harissa.loaders.ClientsLoaderHelper.CLIENT_NAME_POSITION_IN_CSV;
import static uqam.latece.harissa.loaders.ClientsLoaderHelper.DELIMITER;

public class CSVReader
{
    private File csvFile;
    private List<HttpClient> loadedHttpClients;

    public CSVReader(String csvFilePath)
    {
        this.csvFile = new File(csvFilePath);
        this.loadedHttpClients = new ArrayList<HttpClient>();
    }

    public void readFile() throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));

        readBuffer(bufferedReader);
    }

    private void readBuffer(BufferedReader bufferedReader) throws IOException {
        String line;
        HttpClient httpClient;

        List<String> packages;

        while ((line = bufferedReader.readLine())!= null)
        {
            String[] httpClientEntry = line.split(DELIMITER);
            httpClient = new HttpClient();
            packages = new ArrayList<String>();

            for(int i = 1; i < httpClientEntry.length; i++)
            {
                packages.add(httpClientEntry[i]);
            }

            httpClient.setClientName(httpClientEntry[CLIENT_NAME_POSITION_IN_CSV]);
            httpClient.setPackageRespresentations(packages);

            loadedHttpClients.add(httpClient);
        }

        int i = 9;
    }

    public List<HttpClient> getLoadedHttpClients() {
        return loadedHttpClients;
    }
}
