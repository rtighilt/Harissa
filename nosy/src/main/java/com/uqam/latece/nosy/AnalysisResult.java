package com.uqam.latece.nosy;

import com.uqam.latece.harissa.models.HarissaApp;
import com.uqam.latece.nosy.configurations.AnalyzingConfiguraion;

import java.util.List;

public class AnalysisResult {

    private HarissaApp analyzedApplication;
    private List<String> usedClients;
    private boolean isConsideringConnectivity;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Analyzed Application").append(" : ").append(analyzedApplication).append("\n");
        //stringBuilder.append("Used HTTP Libraries").append(" : ").append()
        stringBuilder.append("Is Considering Connectivity").append(" : ").append(isConsideringConnectivity).append("\n");

        return stringBuilder.toString();
    }
}
