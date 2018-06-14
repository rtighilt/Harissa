package com.uqam.latece.harissa.helpers;

import java.io.File;

public class Utils {

    public static String FILENAME_FROM_PATH(String path)
    {
        String fileName = path.substring(path.lastIndexOf(File.separator) + 1);

        return fileName.substring(0, fileName.lastIndexOf("."));
    }

}
