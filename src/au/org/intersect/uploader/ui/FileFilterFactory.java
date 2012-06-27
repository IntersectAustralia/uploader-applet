/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.ui;

import java.util.List;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

/**
 * 
 * @version $Rev: 29 $
 */
public class FileFilterFactory
{
    private static final Logger LOG = Logger.getLogger(FileFilterFactory.class);

    public FileNameExtensionFilter createExtensionFilter(String fileFilterString)
    {
        if (fileFilterString != null && fileFilterString.trim().length() > 0)
        {
            String[] extensions = parseExtensions(fileFilterString);
            if (extensions == null)
            {
                return null;
            }
            return new FileNameExtensionFilter(fileFilterString, extensions);
        }
        else
        {
            return null;
        }
    }

    private String[] parseExtensions(String fileFilterString) throws InvalidFilterStringException
    {
        String[] rawExtensions = fileFilterString.trim().split("\\s*;\\s*");
        List<String> extensions = new ArrayList<String>();
        for (String ext : rawExtensions)
        {
            if (ext.length() > 0 && ext.matches("^[A-Za-z0-9_\\.]+$"))
            {
                extensions.add(ext);
            }
        }

        if (extensions.size() == 0)
        {
            return null;
        }
        return extensions.toArray(new String[extensions.size()]);
    }
}
