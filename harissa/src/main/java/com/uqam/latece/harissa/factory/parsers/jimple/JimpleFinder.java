package com.uqam.latece.harissa.factory.parsers.jimple;


import com.uqam.latece.harissa.models.HarissaClass;
import com.uqam.latece.harissa.models.HarissaMethod;
import com.uqam.latece.harissa.helpers.URIHelpers;
import soot.ValueBox;
import soot.jimple.JimpleBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JimpleFinder {

    //region private fields
    private HarissaClass harissaClass;
    private List<HarissaMethod> harissaMethods;

    //endregion

    public JimpleFinder(HarissaClass harissaClass){
        this.harissaClass = harissaClass;
        harissaMethods = harissaClass.getMethods();
    }

    //region methods
    public Map<JimpleBody, String> findJimplesWithURLs(){
        Map<JimpleBody, String> jimplesWithURLs = new HashMap<>();

//        getJimpleBodies().forEach(jimpleBody -> {
//            jimpleBody.getUseBoxes().forEach(useBox -> {
//                getStringUseBox(useBox).forEach(stringUse -> {
//                    if (URIHelpers.isValidURL(stringUse)) jimplesWithURLs.put(jimpleBody,stringUse);
//                });
//            });
//        });

        jimplesWithURLs.forEach( (jimple,url) -> System.out.println(url +
                                                                    " found in the method -> " +
                                                                    jimple.getMethod().getName() +
                                                                    ", in the class -> " +
                                                                    jimple.getMethod()
                                                                          .getDeclaringClass()
                                                                          .getName()));

        return  jimplesWithURLs;
    }

    //endregion

    //region private methods
//    private List<JimpleBody> getJimpleBodies(){
//        List<JimpleBody> jimpleBodies = new ArrayList<>();
//
//        harissaMethods.forEach( method -> {
//            if (method.hasActiveBody())
//                jimpleBodies.add((JimpleBody) method.getBody());
//        } );
//        return jimpleBodies;
//    }

    private List<String> getStringUseBox(ValueBox valueBox){
        List<String> foundStrings = new ArrayList<>();
        Pattern stringPattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = stringPattern.matcher(valueBox.toString());

        while (matcher.find()) foundStrings.add(matcher.group(1));

        return foundStrings;
    }


    //endregion

}
