/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.responsehandling;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import au.org.intersect.uploader.http.HttpCallResult;
import au.org.intersect.uploader.ui.FileOrganiser;
import au.org.intersect.uploader.ui.JavascriptCallbacks;

/**
 * 
 * 
 * @version $Rev: 29 $
 */
public class PreVerificationResultHandler
{

    private static final Logger LOG = Logger.getLogger(PreVerificationResultHandler.class);

    private JavascriptCallbacks javascriptCallbacks;
    private ResultJsonParser jsonParser;

    public PreVerificationResultHandler(JavascriptCallbacks javascriptCallbacks, ResultJsonParser jsonParser)
    {
        super();
        this.javascriptCallbacks = javascriptCallbacks;
        this.jsonParser = jsonParser;
    }

    public void handleResult(FileOrganiser selectedFiles, HttpCallResult httpResult)
    {
        if (httpResult.success())
        {
            Map<String, FileVerificationResult> parseResult = jsonParser.parseResult(httpResult.getResponseBody());
            for (Entry<String, FileVerificationResult> resultItem : parseResult.entrySet())
            {
                String fileName = resultItem.getKey();
                FileVerificationResult fileResult = resultItem.getValue();

                LOG.debug("Calling callback for file [" + fileName + "] with status [" + fileResult.getStatus()
                        + "] and message [" + fileResult.getMessage() + "]");
                handleFile(selectedFiles, fileName, fileResult, httpResult.getResponseCode());
            }
        }
        else
        {
            handleFailureResponse(selectedFiles, httpResult.getResponseCode());
        }
    }

    protected void handleFailureResponse(FileOrganiser selectedFiles, int responseCode)
    {
        javascriptCallbacks.verificationFailed(responseCode, selectedFiles);
    }

    protected void handleFile(FileOrganiser selectedFiles, String fileName, FileVerificationResult fileResult,
            int responseCode)
    {
        if (fileResult.isAbort())
        {
            javascriptCallbacks.abortAfterVerification(selectedFiles.getAbsolutePath(fileName), fileResult
                    .getMessage());
            selectedFiles.removeFile(fileName);
        }
        else if (fileResult.isProceed())
        {
            // do nothing
        }
        else if (fileResult.isPrompt())
        {
            boolean continueWithUpload = javascriptCallbacks.promptAfterVerification(selectedFiles
                    .getAbsolutePath(fileName), fileResult.getMessage());
            if (!continueWithUpload)
            {
                selectedFiles.removeFile(fileName);
            }
        }
        else
        {
            String errorMessage = "Unexpected verification value [" + fileResult.getStatus() + "] for file ["
                    + fileName + "]";
            LOG.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
    }

}
