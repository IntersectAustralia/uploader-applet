/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 * @version $Rev: 29 $
 */
public class HttpCallResultTest
{

    @Test
    public void testResultIsConsideredSuccessIfResponseCodeIs200()
    {
        HttpCallResult result = new HttpCallResult(200, "response");
        assertTrue(result.success());
        assertEquals(200, result.getResponseCode());
        assertEquals("response", result.getResponseBody());
        assertNull(result.getError());
    }

    @Test
    public void testResultIsConsidereErrorIfResponseCodeAnythingOtherThan200()
    {
        HttpCallResult result = new HttpCallResult(500, "response");
        assertFalse(result.success());
        assertEquals(500, result.getResponseCode());
        assertEquals("response", result.getResponseBody());
        assertNull(result.getError());

    }

    @Test
    public void testResultIsConsideredErrorIfExceptionConstructorUsed()
    {
        HttpCallResult result = new HttpCallResult(new RuntimeException("some exception"));
        assertFalse(result.success());
        assertNull(result.getResponseBody());
        assertEquals("some exception", result.getError());
    }
}
