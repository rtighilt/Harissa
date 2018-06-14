package com.uqam.latece.harissa.factory.parsers.url;


import com.uqam.latece.harissa.models.HarissaApp;
import soot.jimple.JimpleBody;

import java.util.ArrayList;
import java.util.List;

public class UrlFinder {

    //region private fields
    private HarissaApp harissaApp;
    //endregion

    public UrlFinder(HarissaApp harissaApp){
        this.harissaApp = harissaApp;
    }

    //region methods
    public List<String> FindUrls(){
        List<String> urls = new ArrayList<>();

        return urls;
    }

    //endregion


    private boolean containsUrl(JimpleBody jimpleBody){
        List<String> useBoxes = new ArrayList<>();
        jimpleBody.getUseBoxes().forEach(box -> useBoxes.add(box.getValue().toString()));

        return false;
    }
    //endregion
}
