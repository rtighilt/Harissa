package com.uqam.latece.nosy.Utils;



public class AnalysisUtils {

    public static final String CLASS_PACKAGE_NAME = "java.lang.Class";
    public static final String STRING_CLASS_NAME = "java.lang.String";
    public static final String SEPARATOR = ";";

    public static String composeClassNameFromType(String type)
    {
        String className;

        className = type.replace("/", ".");

        className = className.substring(className.indexOf("\"") +1 , className.lastIndexOf("\"") - 1);

        if(className.startsWith("L"))
            className = className.substring(1);

        return className;
    }

    public static String convertIntoPath(String name)
    {
        return name.replace(".","/");
    }

    public static String cleanQuotes(String path)
    {
        String doubleQuotes = "\"";
        String cleandPath;
        if(path.startsWith(doubleQuotes)|| path.endsWith(doubleQuotes))
                cleandPath = path.substring(1,path.length()-1);
        else cleandPath = path;

        return cleandPath;
    }
}
