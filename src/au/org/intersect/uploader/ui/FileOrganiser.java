/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to store information about the files the user selected in the file browse dialog
 * 
 * @version $Rev: 29 $
 */
public class FileOrganiser
{

    private Map<String, File> nameToFileMapping;
    private List<File> fileList = new ArrayList<File>();

    public FileOrganiser(File[] files)
    {
        this.nameToFileMapping = new HashMap<String, File>();
        for (File file : files)
        {
            fileList.add(file);
            nameToFileMapping.put(file.getName(), file);
        }
    }

    public File[] files()
    {
        return fileList.toArray(new File[] {});
    }

    public String getAbsolutePath(String name)
    {
        return nameToFileMapping.get(name).getAbsolutePath();
    }

    public void removeFile(String name)
    {
        fileList.remove(nameToFileMapping.get(name));
        nameToFileMapping.remove(name);
    }

    public boolean isEmpty()
    {
        return nameToFileMapping.isEmpty();
    }

}
