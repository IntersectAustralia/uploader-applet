/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.responsehandling;

import au.org.intersect.uploader.http.HttpCallResult;
import au.org.intersect.uploader.ui.FileOrganiser;
import au.org.intersect.uploader.ui.JavascriptCallbacks;

/**
 * 
 * @version $Rev: 29 $
 */
public class UploadResultHandler
{

    private final JavascriptCallbacks javascriptCallbacks;

    public UploadResultHandler(JavascriptCallbacks javascriptCallbacks)
    {
        this.javascriptCallbacks = javascriptCallbacks;
        
    }

    public void handleResult(FileOrganiser selectedFiles, HttpCallResult httpResult)
    {
        if (httpResult.success())
        {
            javascriptCallbacks.uploadFinished(httpResult.getResponseBody());
        }
        else
        {
            javascriptCallbacks.uploadDidNotComplete(httpResult.getResponseCode(), selectedFiles);
        }
    }
}
