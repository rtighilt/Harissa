package com.uqam.latece.harissa.factory.parsers.xpath;

import com.uqam.latece.harissa.helpers.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import soot.jimple.JimpleBody;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XmlFinder {

    private Document xmlDocument;
    private XmlParser xmlParser;

    public XmlFinder(File xmlFile) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        xmlDocument = factory.newDocumentBuilder().parse(xmlFile);
        xmlParser = new XmlParser();
    }

    public List<String> getJimpleStatements() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        NodeList xPathResponse = runQuery(XmlHelper.XPATH_GET_JIMPLES_EXPRESSION);
        List<String> jimpleStatements = new ArrayList<>();

        for (int i = 0; i < xPathResponse.getLength(); i++) jimpleStatements.add(xPathResponse.item(i).getNodeValue());
        return jimpleStatements;
    }

    public List<String> getMethods() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        NodeList xPathResponse = runQuery(XmlHelper.XPATH_GET_METHOD_NAMES_EXPRESSION);
        List<String> methodNames = new ArrayList<>();

        for (int i = 0; i < xPathResponse.getLength(); i++) methodNames.add(xPathResponse.item(i).getNodeValue());
        return methodNames;
    }

    public List<String> getStrings () throws SAXException, ParserConfigurationException, XPathExpressionException, IOException{
        List<String> jimpleStatements = getJimpleStatements();
        List<String> extractedStrings = new ArrayList<>();
        Pattern stringPattern = Pattern.compile("\"(.*?)\"");
        JimpleBody jb = new JimpleBody();
        jimpleStatements.forEach(statement -> {
            Matcher matcher = stringPattern.matcher(statement);
            if (matcher.find()) extractedStrings.add(matcher.group(1));
        });

        return extractedStrings;}

    private NodeList runQuery(String xPathExpression) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        return  xmlParser.executeQuery(xPathExpression);
    }

    private class XmlParser{

        private XPath xpath;

        private XmlParser() throws ParserConfigurationException, IOException, SAXException {
            xpath = XPathFactory.newInstance().newXPath();
        }

         NodeList executeQuery(String xPathExpression) throws XPathExpressionException {
            return (NodeList) xpath.compile(xPathExpression).evaluate(xmlDocument, XPathConstants.NODESET);
         }
    }
}

