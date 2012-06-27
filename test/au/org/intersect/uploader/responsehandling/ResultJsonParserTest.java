/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.responsehandling;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

/**
 * 
 * @version $Rev: 29 $
 */
public class ResultJsonParserTest
{

    @Test
    public void testParsesAWellFormedResponseCorrectly()
    {
        String json = "{\"Ramanstation Map File.fsm\":{\"status\":\"proceed\",\"message\":\"\"},"
                + "\"Ramanstation Spectrum File 1_JCAMPDX.DX\":{\"status\":\"proceed\",\"message\":\"\"},"
                + "\"Ramanstation Spectrum File 1_SP.SP\":{\"status\":\"abort\","
                + "\"message\":\"A similar file type already exists in the dataset.\"}}";
        Map<String, FileVerificationResult> files = new ResultJsonParser().parseResult(json);
        assertEquals(3, files.size());
        FileVerificationResult f1 = files.get("Ramanstation Map File.fsm");
        FileVerificationResult f2 = files.get("Ramanstation Spectrum File 1_JCAMPDX.DX");
        FileVerificationResult f3 = files.get("Ramanstation Spectrum File 1_SP.SP");
        assertEquals("proceed", f1.getStatus());
        assertEquals("", f1.getMessage());
        assertEquals("proceed", f2.getStatus());
        assertEquals("", f2.getMessage());
        assertEquals("abort", f3.getStatus());
        assertEquals("A similar file type already exists in the dataset.", f3.getMessage());
    }

    @Test(expected = RuntimeException.class)
    public void testThrowsExceptionForInappropriatelyFormattedJSON()
    {
        String json = "{\"Ramanstation Map File.fsm\":[\"abc\", \"def\"]}";
        new ResultJsonParser().parseResult(json);
    }
}
