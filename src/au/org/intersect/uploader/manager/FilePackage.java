/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id$
 */
package au.org.intersect.uploader.manager;

import java.io.File;
import java.util.Map;

import org.json.JSONArray;

/**
 * 
 * @version $Rev: 29 $
 */
public class FilePackage
{
    private Map<String, File> files;
    private JSONArray json;
    private String destDir;

    FilePackage(Map<String, File> files, JSONArray json, String destDir)
    {
        this.files = files;
        this.json = json;
        this.destDir = destDir;
    }

    public Map<String, File> getFiles()
    {
        return files;
    }

    public JSONArray getJSON()
    {
        return json;
    }

    public String getDestinationDirectory()
    {
        return destDir;
    }
}
