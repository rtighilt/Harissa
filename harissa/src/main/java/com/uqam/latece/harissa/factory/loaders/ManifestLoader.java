package com.uqam.latece.harissa.factory.loaders;

import com.uqam.latece.harissa.factory.loaders.manifest.ManifestReader;
import com.uqam.latece.harissa.models.Apk;
import com.uqam.latece.harissa.models.Manifest;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class ManifestLoader extends Loader {

    private Document manifestXMLDocument;
    private Manifest manifest;

    public ManifestLoader(Apk apk){
        this.apk = apk;
        init();
    }

    @Override
    public void init() {
        try
        {
            manifestXMLDocument = apk.getManifestXMLDocument();
        }
        catch (IOException | ParserConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void runLoading()
    {
        manifest = new ManifestReader(manifestXMLDocument).read();
    }

    public Manifest getManifest()
    {
        return this.manifest;
    }

}
