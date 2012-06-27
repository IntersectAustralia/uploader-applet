/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id$
 */
package au.org.intersect.uploader.http;

import org.apache.commons.httpclient.HttpStatus;

/**
 * Wraps up information about the result of an http call to the server.
 *
 * @version $Rev: 29 $
 */
public class HttpCallResult
{

    private int responseCode;
    private String responseBody;
    private boolean error;
    private Exception exception;

    public HttpCallResult(int responseCode, String responseBody)
    {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.error = responseCode != HttpStatus.SC_OK;
    }

    public HttpCallResult(Exception e)
    {
        this.error = true;
        this.exception = e;
    }

    public boolean success()
    {
        return !this.error;
    }

    public int getResponseCode()
    {
        return responseCode;
    }

    public String getResponseBody()
    {
        return responseBody;
    }

    public String getError()
    {
        if (exception == null)
        {
            return null;
        }
        return exception.getMessage();
    }
}
