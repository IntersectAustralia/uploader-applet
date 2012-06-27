/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.manager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import au.org.intersect.uploader.ui.FileOrganiser;

/**
 * 
 * @version $Rev: 29 $
 */
public final class FilePackageFactory
{
    private static final Logger LOG = Logger.getLogger(FilePackageFactory.class);

    public static final FilePackage buildFilePackage(String destDir, FileOrganiser fileOrganiser) throws JSONException
    {
        int sequence = 1;
        JSONArray json = new JSONArray();
        Map<String, File> fileMap = new HashMap<String, File>();
        for (File file : fileOrganiser.files())
        {
            if (file.isHidden())
            {
                continue;
            }
            if (file.isDirectory())
            {
                // root json entry
                String rootFolderName = file.getName();
                // first indicate which is the root folder
                JSONObject jsonObj = new JSONObject();           
                jsonObj.put("folder_root", rootFolderName);
                // now get the rest
                String baseDir = file.getPath();
                sequence = walkDirectory(file, baseDir, rootFolderName, jsonObj, fileMap, sequence);
                json.put(jsonObj);
            }
            else if (file.isFile())
            {
                String id = "file_" + Integer.toString(sequence++);
                JSONObject jsonObj = new JSONObject();
                jsonObj.put(id, file.getName());
                json.put(jsonObj);
                fileMap.put(id, file);
            }
        }

        FilePackage fp = new FilePackage(fileMap, json, destDir);
        return fp;
    }

    private static int walkDirectory(
        File directory,
        String baseDir,
        String rootFolderName,
        JSONObject json,
        Map<String, File> fileMap,
        int sequenceStart
    ) 
        throws JSONException
    {
        int sequence = sequenceStart;
        File[] allFilesInDir = directory.listFiles();
        for (File file : allFilesInDir)
        {
            if (file.isHidden())
            {
                continue;
            }
            String path = file.getPath();
            String relativePath = path.substring(baseDir.length() + 1);
            // Make sure the path separators are forward slashes
            String genericPath = relativePath.replace(File.separatorChar, '/');
            String destPath = rootFolderName + "/" + path.substring(baseDir.length() + 1);
            String idNum = Integer.toString(sequence++);
            if (file.isFile())
            {
                String id = "file_" + idNum;
                json.put(id, destPath);
                fileMap.put(id, file);
            }
            else if (file.isDirectory())
            {
                json.put("folder_" + idNum, destPath);
                sequence = walkDirectory(file, baseDir, rootFolderName, json, fileMap, sequence);
            }
        }
        return sequence;
    }
   
}
