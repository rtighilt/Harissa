package com.uqam.latece.harissa.factory.loaders.manifest;


import com.uqam.latece.harissa.models.Manifest;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import static com.uqam.latece.harissa.helpers.ManifestHelper.*;

public class ManifestReader {

    private Document document;

    public ManifestReader(Document document)
    {
        this.document = document;
    }

    public Manifest read()
    {
        return new Manifest.Builder().applicationName(readApplicationName())
                                     .packageName(readPackageName())
                                     .minSdk(readFromSDK(MIN_SDK_VERSION))
                                     .maxSdk(readFromSDK(MAX_SDK_VERSION))
                                     .targettedSDK(readFromSDK(TARGET_SDK_VERSION))
                                     .usedPermissions(readUsesPermissions())
                                     .activities(readActivities())
                                     .services(readServices())
                                     .receivers(readReceivers())
                                     .build();
    }

    private String readApplicationName()
    {
        try
        {
            return GET_APPLICATION_NODE(document).getAttributes().getNamedItem(NAME_ATTRIBUTE).getNodeValue();
        }
        catch (NullPointerException e)
        {
            //the attibute name is different try with "name" instead of "android:name"
            try
            {
                return GET_APPLICATION_NODE(document).getAttributes().getNamedItem(ANDROID_OBJECT + NAME_ATTRIBUTE).getNodeValue();
            }
            catch (NullPointerException e1)
            {
                return null;
            }
        }
    }

    private String readPackageName()
    {
        return GET_MANIFEST_NODE(document).getAttributes().getNamedItem(PACKAGE_NAME).getNodeValue();
    }

    private int readFromSDK(String attributeName)
    {
        try
        {
            return Integer.parseInt(GET_SDK_NODE(document).getAttributes().getNamedItem(attributeName).getNodeValue());
        }

        catch(NullPointerException e)
        {
            try
            {
                return Integer.parseInt(GET_SDK_NODE(document).getAttributes().getNamedItem(ANDROID_OBJECT + attributeName).getNodeValue());
            }
            catch (NullPointerException e1)
            {
                //TODO print error into log
                return 0;
            }
        }
    }

    private List<String> readUsesPermissions()
    {
        List<String> usedPermissions = new ArrayList<>();
        NodeList permissionNodes = GET_PERMISSION_NODES(document);
        //GET_PERMISSION_NODE().get

        for (int i = 0; i < permissionNodes.getLength(); i++)
        {
            try
            {
                usedPermissions.add(permissionNodes.item(i).getAttributes().getNamedItem(NAME_ATTRIBUTE).getNodeValue());
            }
            catch (NullPointerException e)
            {
                //the attibute name is different try with "name" instead of "android:name"
                usedPermissions.add(permissionNodes.item(i).getAttributes().getNamedItem(ANDROID_OBJECT + NAME_ATTRIBUTE).getNodeValue());
            }
        }

        return usedPermissions;
    }

    private List<String> readActivities()
    {
        List<String> activities = new ArrayList<>();
        NodeList activityNodes = GET_ACTIVITY_NODES(document);

        for (int i = 0; i < activityNodes.getLength(); i++)
        {
            try
            {
                activities.add(activityNodes.item(i).getAttributes().getNamedItem(NAME_ATTRIBUTE).getNodeValue());
            }
            catch (NullPointerException e)
            {
                //the attibute name is different try with "name" instead of "android:name"
                activities.add(activityNodes.item(i).getAttributes().getNamedItem(ANDROID_OBJECT + NAME_ATTRIBUTE).getNodeValue());
            }
        }

        return activities;
    }

    private List<String> readServices()
    {
        List<String> services = new ArrayList<>();
        NodeList serviceNodes = GET_SERVICE_NODES(document);

        for (int i = 0; i < serviceNodes.getLength(); i++)
        {
            try
            {
                services.add(serviceNodes.item(i).getAttributes().getNamedItem(NAME_ATTRIBUTE).getNodeValue());
            }
            catch (NullPointerException e)
            {
                //the attibute name is different try with "name" instead of "android:name"
                services.add(serviceNodes.item(i).getAttributes().getNamedItem(ANDROID_OBJECT + NAME_ATTRIBUTE).getNodeValue());
            }
        }

        return services;
    }

    private List<String> readReceivers()
    {
        List<String> receivers = new ArrayList<>();
        NodeList receiverNodes = GET_RECEIVER_NODES(document);

        for (int i = 0; i < receiverNodes.getLength(); i++)
        {
            try
            {
                receivers.add(receiverNodes.item(i).getAttributes().getNamedItem(NAME_ATTRIBUTE).getNodeValue());
            }
            catch (NullPointerException e)
            {
                //the attibute name is different try with "name" instead of "android:name"
                receivers.add(receiverNodes.item(i).getAttributes().getNamedItem(ANDROID_OBJECT + NAME_ATTRIBUTE).getNodeValue());
            }
        }

        return receivers;
    }

}