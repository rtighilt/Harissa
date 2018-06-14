package com.uqam.latece.harissa.helpers;


public class XmlHelper {

    public final static String XML_TEST_FILE_PATH = "sootOutput/App/Hello.xml";
    public final static String XPATH_GET_EXPRESSION = "//Tutorial[descendant::title[text()=\'%s\']]";
    public final static String XPATH_GET_METHOD_NAMES_EXPRESSION = "//method/attribute::name";
    public final static String XPATH_GET_JIMPLES_EXPRESSION = "//jimple/text()";
    public final static String XPATH_GET_STRINGS_EXPRESSION = "substring-after(//jimple/text(),'r')";
    public final static String XPATH_GET_LOCALS_EXPRESSION = "/jil/class/methods[descendant::method]";


}
