package com.uqam.latece.harissa.helpers;

import com.sun.org.apache.xerces.internal.dom.ElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ManifestHelper {

    public final static String ANDROID_MANIFEST = "AndroidManifest.xml";
    public static final String USES_SDK = "uses-sdk";
    public static final String MIN_SDK_VERSION = "android:minSdkVersion";
    public static final String MAX_SDK_VERSION = "android:maxSdkVersion";
    public static final String TARGET_SDK_VERSION = "android:targetSdkVersion";
    public static final String PACKAGE_NAME = "package";
    public static final String USES_PERMISSION = "uses-permission";
    public static final String FULL_NAME_ATTRIBUTE = "android:name";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String ANDROID_OBJECT = "android:";
    public static final String ACTIVITY = "activity";
    public static final String SERVICE = "service";
    public static final String RECEIVER = "receiver";

    public static Node GET_MANIFEST_NODE(Document manifestDocument)
    {
        return manifestDocument.getChildNodes().item(0);
    }

    public static Node GET_APPLICATION_NODE(Document manifestDocument)
    {
        try
        {
            return GET_MANIFEST_NODE(manifestDocument).getLastChild();
        }

        catch(NullPointerException e)
        {
            return null;
        }
    }

    public static Node GET_SDK_NODE(Document manifestDocument)
    {
        try
        {
            return ((ElementImpl) GET_MANIFEST_NODE(manifestDocument)).getElementsByTagName(USES_SDK).item(0);
        }

        catch(NullPointerException e)
        {
            return null;
        }
    }

    public static NodeList GET_PERMISSION_NODES (Document manifestDocument)
    {
        try
        {
            return ((ElementImpl) GET_MANIFEST_NODE(manifestDocument)).getElementsByTagName(USES_PERMISSION);
        }

        catch(NullPointerException e)
        {
            return null;
        }
    }

    public static NodeList GET_ACTIVITY_NODES (Document manifestDocument)
    {
        try
        {
            return ((ElementImpl) GET_APPLICATION_NODE(manifestDocument)).getElementsByTagName(ACTIVITY);
        }

        catch(NullPointerException e)
        {
            return null;
        }
    }

    public static NodeList GET_SERVICE_NODES (Document manifestDocument)
    {
        try
        {
            return ((ElementImpl) GET_APPLICATION_NODE(manifestDocument)).getElementsByTagName(SERVICE);
        }

        catch(NullPointerException e)
        {
            return null;
        }
    }

    public static NodeList GET_RECEIVER_NODES (Document manifestDocument)
    {
        try
        {
            return ((ElementImpl) GET_APPLICATION_NODE(manifestDocument)).getElementsByTagName(RECEIVER);
        }

        catch(NullPointerException e)
        {
            return null;
        }
    }

}
