package uqam.latece.harissa.printers;

import com.opencsv.bean.CsvBindByName;

public class OutputEntry {

    @CsvBindByName
    public String  package_name;
    @CsvBindByName
    public boolean internet_permission;
    @CsvBindByName
    public boolean references_any_http_client;
    public boolean references_multiple_http_client;
    @CsvBindByName
    public String referenced_http_clients;
    @CsvBindByName
    public boolean potentiallyConsumingRESTapi;

    public String name;

    public String[] getArrayEntry(){
        return new String[]{package_name, String.valueOf(internet_permission),String.valueOf(references_any_http_client),String.valueOf(references_multiple_http_client), referenced_http_clients, String.valueOf(potentiallyConsumingRESTapi), name};
    }

    @Override
    public String toString() {
        String COMMA = ",";
        return package_name + COMMA + internet_permission + COMMA + references_any_http_client + COMMA + references_multiple_http_client + COMMA + referenced_http_clients + COMMA + potentiallyConsumingRESTapi;
    }
}
