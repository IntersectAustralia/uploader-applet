/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id$
 */
package au.org.intersect.uploader.responsehandling;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Parses the JSON response received from the server for a package of files. Works for both the verify and and upload
 * request.
 * 
 * Expects format along the lines of: { "chart.txt":{"status" => "abort", "message" => "msg"}, "myfile.txt":{"status" =>
 * "proceed", "message" => "msg"}, }
 * 
 * @version $Rev: 29 $
 */
public class ResultJsonParser
{
    private static final Logger LOG = Logger.getLogger(ResultJsonParser.class);

    public Map<String, FileVerificationResult> parseResult(String responseBody)
    {
        try
        {
            JSONObject resultJson = new JSONObject(responseBody);
            LOG.debug("PreVerificationResultHandler.handleResult verify result: " + resultJson.toString());

            Map<String, FileVerificationResult> files = new HashMap<String, FileVerificationResult>();
            Iterator iter = resultJson.keys();
            while (iter.hasNext())
            {
                String fileName = (String) iter.next();
                JSONObject statusAndMessage = resultJson.getJSONObject(fileName);

                String status = statusAndMessage.getString("status");
                String message = statusAndMessage.getString("message");

                LOG.debug("Creating file package result from JSON, fileName = [" + fileName + "], status = [" + status
                        + "], message = [" + message + "]");

                files.put(fileName, new FileVerificationResult(status, message));
            }
            return files;
        }
        catch (JSONException e)
        {
            LOG.error("Something went wrong trying to parse the JSON response, "
                    + "check that the server is returning the correct format", e);
            throw new RuntimeException("Error parsing JSON response from server");
        }
    }
}
