package com.uqam.latece.nosy.printers;


import com.uqam.latece.nosy.analyzers.clients.IHttpClientAnalyzer;

import java.util.ArrayList;
import java.util.List;

import static com.uqam.latece.nosy.Utils.AnalysisUtils.SEPARATOR;


public class OutputEntry {

    private String packageName;
    private List<IHttpClientAnalyzer> httpClientAnalyzers;
    private boolean isUsingCache;
    private boolean isSettingUpATimeout;
    private boolean connectivityAware;
    private boolean isMakingSynchronousCalls;
    private boolean isMakingAsynchronousCalls;
    private List<Client> subLines;


    private OutputEntry(Builder builder)
    {
        this.packageName = builder.packageName;
        this.httpClientAnalyzers = builder.httpClients;
        this.isUsingCache = builder.isUsingCache;
        this.isSettingUpATimeout = builder.isSettingUpATimeout;
        this.connectivityAware = builder.connectivityAware;
        this.isMakingAsynchronousCalls = builder.isMakingAsynchounousCalls;
        this.isMakingSynchronousCalls = builder.isMakingSynchronousCalls;
        this.buildSubLines();
    }

    public List<Client> getSubLines() {
        return subLines;
    }
    public String[] getArrayEntry(){
        return this.toString().split(SEPARATOR);
    }

    @Override
    public String toString() {
        StringBuilder csvLineBuilder = new StringBuilder();

        csvLineBuilder.append(packageName).append(SEPARATOR);
        csvLineBuilder.append("[");

        for(int i=0 ; i < httpClientAnalyzers.size(); i++)
        {
            if(i == httpClientAnalyzers.size() -1)
            {
                csvLineBuilder.append(httpClientAnalyzers.get(i).getHttpClientPackageName());
            }
            else
            {
                csvLineBuilder.append(httpClientAnalyzers.get(i).getHttpClientPackageName()).append("|");
            }
        }

        csvLineBuilder.append("]").append(SEPARATOR);

        csvLineBuilder.append(String.valueOf(isUsingCache)).append(SEPARATOR);
        csvLineBuilder.append(String.valueOf(isSettingUpATimeout)).append(SEPARATOR);
        csvLineBuilder.append(String.valueOf(isMakingSynchronousCalls)).append(SEPARATOR);
        csvLineBuilder.append(String.valueOf(isMakingAsynchronousCalls)).append(SEPARATOR);
        csvLineBuilder.append(String.valueOf(connectivityAware));

        return csvLineBuilder.toString();
    }

    private void buildSubLines()
    {
        this.subLines = new ArrayList<>();

        this.httpClientAnalyzers.forEach(httpClientAnalyzer -> {
            String clientPackageName = httpClientAnalyzer.getHttpClientPackageName();
            boolean isExcutingCall = httpClientAnalyzer.isConnectingToRESTService();
            boolean isCaching = httpClientAnalyzer.isUsingHttpCaching();
            boolean isSettingTimeout = httpClientAnalyzer.isSettingTimeout();
            boolean synchronous = httpClientAnalyzer.isMakingSynchronousCalls();
            boolean asynchronous = httpClientAnalyzer.isMakingAsynchronousCalls();

            this.subLines.add(new Client(this.packageName, clientPackageName, isExcutingCall, isCaching, isSettingTimeout, synchronous, asynchronous));
        });
    }

    static class Client
    {
        private String  appPackageName;
        private String  clientPackageName;
        private boolean isExecutingCall;
        private boolean isUsingCaching;
        private boolean isUsingTimeout;
        private boolean synchronous;
        private boolean asynchronous;

        public Client(String appPackageName, String clientPackageName, boolean isExecutingCall, boolean isUsingCaching, boolean isUsingTimeout, boolean synchronous, boolean asynchronous)
        {
            this.appPackageName = appPackageName;
            this.clientPackageName = clientPackageName;
            this.isExecutingCall = isExecutingCall;
            this.isUsingCaching = isUsingCaching;
            this.isUsingTimeout = isUsingTimeout;
            this.synchronous = synchronous;
            this.asynchronous = asynchronous;
        }

        public String[] getArrayEntry()
        {
            return toString().split(SEPARATOR);
        }
        @Override
        public String toString()
        {
            StringBuilder csvLineBuilder = new StringBuilder();

            csvLineBuilder.append(this.appPackageName).append(SEPARATOR);
            csvLineBuilder.append(this.clientPackageName).append(SEPARATOR);
            csvLineBuilder.append(this.isExecutingCall).append(SEPARATOR);
            csvLineBuilder.append(this.synchronous).append(SEPARATOR);
            csvLineBuilder.append(this.asynchronous).append(SEPARATOR);
            csvLineBuilder.append(String.valueOf(this.isUsingCaching)).append(SEPARATOR);
            csvLineBuilder.append(String.valueOf(this.isUsingTimeout));

            return csvLineBuilder.toString();
        }
    }

    static class Builder {
        private String packageName;
        private List<IHttpClientAnalyzer> httpClients;
        private boolean isUsingCache;
        private boolean isSettingUpATimeout;
        private boolean connectivityAware;
        private boolean isMakingSynchronousCalls;
        private boolean isMakingAsynchounousCalls;

        public Builder packageName(String packageName){
            this.packageName = packageName;
            return this;
        }

        public Builder httpClients(List<IHttpClientAnalyzer> httpClients){
            this.httpClients = httpClients;
            return this;
        }
        public Builder isUsingCache(boolean isUsingCache){
            this.isUsingCache = isUsingCache;
            return this;
        }

        public Builder isSettingUpeTimeout(boolean isSettingUpATimeout){
            this.isSettingUpATimeout = isSettingUpATimeout;
            return this;
        }

        public Builder connectivityAware(boolean connectivityAware){
            this.connectivityAware = connectivityAware;
            return this;
        }

        public Builder isMakingSynchronousCalls(boolean isMakingSynchronousCalls){
            this.isMakingSynchronousCalls = isMakingSynchronousCalls;
            return this;
        }

        public Builder isMakingAsynchronousCalls(boolean isMakingAsynchronousCalls){
            this.isMakingAsynchounousCalls = isMakingAsynchronousCalls;
            return this;
        }

        OutputEntry build(){
            return new OutputEntry(this);
        }
    }
}
