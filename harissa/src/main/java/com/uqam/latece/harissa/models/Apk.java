package com.uqam.latece.harissa.models;

import com.uqam.latece.harissa.factory.parsers.axml.CompressedXmlParser;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.uqam.latece.harissa.helpers.ManifestHelper.ANDROID_MANIFEST;
import static com.uqam.latece.harissa.helpers.Utils.FILENAME_FROM_PATH;

public class Apk extends ZipFile{

    private String name;
    private String path;

    public Apk(String path) throws IOException
    {
        super(new File(path));
        this.path = path;
        this.name = FilenameUtils.getName(path);
    }

    private ZipEntry getManifestEntry()
    {
        return this.getEntry(ANDROID_MANIFEST);
    }

    public Document getManifestXMLDocument() throws IOException, ParserConfigurationException
    {
        Document manifestDocument;
        InputStream manifestInputStream = getInputStream(getManifestEntry());

        manifestDocument = new CompressedXmlParser().parseDOM(manifestInputStream);

        return manifestDocument;
    }

    public String getName() {
        return name;
    }

    public void setName()
    {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
