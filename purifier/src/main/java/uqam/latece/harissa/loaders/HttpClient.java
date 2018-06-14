package uqam.latece.harissa.loaders;

import java.util.List;

public class HttpClient {

    private String clientName;
    private List<String> packageRespresentations;

    public HttpClient(String clientName, List<String> packageRespresentations)
    {
        this.clientName = clientName;
        this.packageRespresentations = packageRespresentations;
    }

    public HttpClient()
    {
        this.clientName = clientName;
        this.packageRespresentations = packageRespresentations;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<String> getPackageRespresentations() {
        return packageRespresentations;
    }

    public void setPackageRespresentations(List<String> packageRespresentations) {
        this.packageRespresentations = packageRespresentations;
    }
}
