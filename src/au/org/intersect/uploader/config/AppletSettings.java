/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.config;

import javax.swing.JApplet;
import javax.swing.JFileChooser;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @version $Rev: 29 $
 */
public class AppletSettings
{

    private static final Logger LOG = Logger.getLogger(AppletSettings.class);

    private static final String UPLOAD_URL_ATTR = "uploadURL";
    private static final String DESTINATION_DIRECTORY_ATTR = "destDir";
    private static final String FILTER_ATTR = "filter";
    private static final String BACKGROUND_COLOUR_ATTR = "backgroundColour";
    private static final String ALLOW_MULTISELECT_ATTR = "allowMultiselect";
    private static final String SEND_VERIFY_REQUEST_FIRST = "sendVerifyRequestFirst";
    private static final String VERIFY_REQUEST_URL = "verifyRequestURL";
    private static final String FILE_SELECTION_MODE = "fileSelectionMode";

    private static final String FOLDERS_ONLY_MODE = "folders_only";
    private static final String FILES_ONLY_MODE = "files_only";
    private static final String FILES_AND_FOLDERS_MODE = "files_and_folders";

    private String uploadURL;
    private String destinationDirectory;
    private boolean sendVerifyRequestFirst;
    private String fileFilterString;
    private String backgroundColour;
    private boolean allowMultiselect;
    private String verifyRequestURL;
    private int fileSelectionMode;

    public AppletSettings(JApplet applet) throws MissingParameterException
    {
        this.uploadURL = getParameter(applet, UPLOAD_URL_ATTR, null);
        if (this.uploadURL == null)
        {
            throw new MissingParameterException(UPLOAD_URL_ATTR);
        }
        this.destinationDirectory = getParameter(applet, DESTINATION_DIRECTORY_ATTR, null);
        this.sendVerifyRequestFirst = Boolean.parseBoolean(getParameter(applet, SEND_VERIFY_REQUEST_FIRST, "false"));
        this.allowMultiselect = Boolean.parseBoolean(getParameter(applet, ALLOW_MULTISELECT_ATTR, "false"));
        this.fileFilterString = getParameter(applet, FILTER_ATTR, null);
        this.backgroundColour = getParameter(applet, BACKGROUND_COLOUR_ATTR, "#FFFFFF");
        this.verifyRequestURL = getParameter(applet, VERIFY_REQUEST_URL, null);
        String fileSelectionModeString = getParameter(applet, FILE_SELECTION_MODE, FILES_AND_FOLDERS_MODE);
        parseFileSelectionMode(fileSelectionModeString);
    }

    private void parseFileSelectionMode(String fileSelectionModeString)
    {
        if (FILES_AND_FOLDERS_MODE.equals(fileSelectionModeString))
        {
            this.fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES;
        }
        else if (FILES_ONLY_MODE.equals(fileSelectionModeString))
        {
            this.fileSelectionMode = JFileChooser.FILES_ONLY;
        }
        else if (FOLDERS_ONLY_MODE.equals(fileSelectionModeString))
        {
            this.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY;
        }
        else
        {
            throw new IllegalArgumentException("Invalid fileSelectionMode property [" + fileSelectionModeString
                    + "] - must be one of [" + FILES_ONLY_MODE + "," + FOLDERS_ONLY_MODE + "," + FILES_AND_FOLDERS_MODE
                    + "]");
        }
    }

    private String getParameter(JApplet applet, String key, String defaultValue)
    {
        String value = applet.getParameter(key);
        String parameterValue = value == null ? defaultValue : value;
        LOG.debug("Parameter value for key [" + key + "] is [" + parameterValue + "]");
        return parameterValue;
    }

    public String getUploadURL()
    {
        return uploadURL;
    }

    public String getDestinationDirectory()
    {
        return destinationDirectory;
    }

    public String getFileFilterString()
    {
        return fileFilterString;
    }

    public String getBackgroundColour()
    {
        return backgroundColour;
    }

    public boolean isAllowMultiselect()
    {
        return allowMultiselect;
    }

    public boolean isSendVerifyRequestFirst()
    {
        return sendVerifyRequestFirst;
    }

    public String getVerifyRequestURL()
    {
        return verifyRequestURL;
    }
    
    public int getFileSelectionMode()
    {
        return fileSelectionMode;
    }

}
