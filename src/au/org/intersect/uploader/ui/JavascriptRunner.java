/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.ui;

import org.apache.log4j.Logger;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 * 
 * 
 * @version $Rev: 29 $
 */
public class JavascriptRunner
{
    private static final Logger LOG = Logger.getLogger(JavascriptRunner.class);
    private final JSObject javascriptObject;

    public JavascriptRunner(JSObject jsObject)
    {
        javascriptObject = jsObject;
    }

    public Object executeJavascriptMethod(String method, String... args)
    {
        try
        {
            LOG.debug("Calling " + method + " javascript method");
            Object jsResult = javascriptObject.call(method, args);
            logJsResult(jsResult);
            return jsResult;
        }
        catch (JSException ex)
        {
            LOG.error("Callback to javascript " + method + " method didn't successfully complete", ex);
            throw ex;
        }

    }

    private void logJsResult(Object jsResult)
    {
        if (jsResult != null)
        {
            LOG.debug("Javascript call returned a [" + jsResult.getClass() + "], with value [" + jsResult.toString()
                    + "]");
        }
    }

}
