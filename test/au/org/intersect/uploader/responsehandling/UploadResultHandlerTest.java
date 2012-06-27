/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.responsehandling;

import static org.mockito.Mockito.mock;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import au.org.intersect.uploader.http.HttpCallResult;
import au.org.intersect.uploader.ui.FileOrganiser;
import au.org.intersect.uploader.ui.JavascriptCallbacks;

/**
 * 
 * @version $Rev: 29 $
 */
public class UploadResultHandlerTest
{
    private UploadResultHandler handler;
    private HttpCallResult callResult;

    @Before
    public void setUp()
    {
        JavascriptCallbacks jsRunner = mock(JavascriptCallbacks.class);
        handler = new UploadResultHandler(jsRunner);
        callResult = mock(HttpCallResult.class);

    }

    @Test
    public void testCallsAppropriateJavascriptCallbackForEachFile() throws JSONException
    {
        FileOrganiser fileOrganiser = mock(FileOrganiser.class);
        handler.handleResult(fileOrganiser, callResult);

    }
}
