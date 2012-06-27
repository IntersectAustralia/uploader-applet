/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.manager;

import java.io.IOException;
import java.net.URL;

import org.json.JSONException;

import au.org.intersect.uploader.config.AppletSettings;
import au.org.intersect.uploader.http.HttpCallResult;
import au.org.intersect.uploader.http.MultipartPostHelper;
import au.org.intersect.uploader.ui.FileOrganiser;

/**
 * 
 *
 * @version $Rev: 29 $
 */
public class PreVerificationManager
{

    private String verificationUrl;
    private String destinationDirectory;

    public PreVerificationManager(AppletSettings settings)
    {
        this.verificationUrl = settings.getVerifyRequestURL();
        this.destinationDirectory = settings.getDestinationDirectory();
    }

    public HttpCallResult verify(FileOrganiser files, String authToken) throws JSONException, IOException
        
    {
        FilePackage filePackage = FilePackageFactory.buildFilePackage(destinationDirectory, files);
        
        String urlWithToken = "";
        URL url = new URL(verificationUrl);
        if (url.getQuery() == null)
        {
            urlWithToken = verificationUrl + "?auth_token=" + authToken;
        }
        else
        {
            urlWithToken = verificationUrl + "&auth_token=" + authToken;
        }

        MultipartPostHelper helper = new MultipartPostHelper(urlWithToken);
        helper.addStringPart("dirStruct", filePackage.getJSON().toString(), "application/json", "utf-8");

        // destination directory (optional - skip if null)
        if (filePackage.getDestinationDirectory() != null)
        {
            helper.addStringPart("destDir", filePackage.getDestinationDirectory(), "text/plain", "utf-8");
        }

        return helper.execute();
    }

}
