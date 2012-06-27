/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.log4j.Logger;


/**
 * Helper for executing a multipart post request. 
 *
 * @version $Rev: 29 $
 */
public class MultipartPostHelper
{

    private static final Logger LOG = Logger.getLogger(MultipartPostHelper.class);

    private List<Part> partList;
    private PostMethod postMethod;
    private HttpClient httpClient;

    private final String url;

    public MultipartPostHelper(String url)
    {
        this.url = url;
        MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
        this.httpClient = new HttpClient(manager);
        this.postMethod = new PostMethod(url);
        this.partList = new ArrayList<Part>();
    }

    public void addStringPart(String name, String value, String contentType, String encoding)
    {
        StringPart spart = new StringPart(name, value);
        spart.setContentType(contentType);
        spart.setCharSet(encoding);
        partList.add(spart);
    }

    public void addFilePart(String name, File file) throws FileNotFoundException
    {
        FilePart filePart = new FilePart(name, file);
        partList.add(filePart);
    }

    public HttpCallResult execute()
    {
        try
        {
            Part[] parts = partList.toArray(new Part[] {});
            MultipartRequestEntity entity = new MultipartRequestEntity(parts, postMethod.getParams());
            postMethod.setRequestEntity(entity);

            LOG.debug("Posting to: " + url);
            int returnStatusCode = httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            LOG.debug("Response code: " + returnStatusCode);
            return new HttpCallResult(returnStatusCode, responseBody);
        }
        catch (Exception e)
        {
            LOG.error("Failed to execute post method", e);
            return new HttpCallResult(e);
        }
        finally
        {
            tryToReleaseConnection(postMethod);
        }

    }

    private void tryToReleaseConnection(HttpMethod postMethod)
    {
        if (postMethod != null)
        {
            try
            {
                postMethod.releaseConnection();
            }
            catch (Exception e)
            {
                LOG.error("Failed to release the http connection, continuing on anyway", e);
            }
        }

    }

}
