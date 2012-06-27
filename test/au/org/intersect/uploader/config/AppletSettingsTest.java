/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id$
 */
package au.org.intersect.uploader.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.JApplet;
import javax.swing.JFileChooser;

import org.junit.Test;

/**
 * 
 *
 * @version $Rev: 29 $
 */
public class AppletSettingsTest
{

    @Test
    public void testLoadsSettingsCorrectlyWhenSet() throws MissingParameterException
    {
        JApplet applet = mock(JApplet.class);

        when(applet.getParameter("uploadURL")).thenReturn("my-server");
        when(applet.getParameter("destDir")).thenReturn("my-dest");
        when(applet.getParameter("sendVerifyRequestFirst")).thenReturn("true");
        when(applet.getParameter("allowMultiselect")).thenReturn("true");
        when(applet.getParameter("filter")).thenReturn("my-filter");
        when(applet.getParameter("backgroundColour")).thenReturn("my-colour");
        when(applet.getParameter("verifyRequestURL")).thenReturn("my-verify-url");
        when(applet.getParameter("fileSelectionMode")).thenReturn("files_only");

        AppletSettings settings = new AppletSettings(applet);

        assertEquals("my-server", settings.getUploadURL());
        assertEquals("my-dest", settings.getDestinationDirectory());
        assertTrue(settings.isSendVerifyRequestFirst());
        assertTrue(settings.isAllowMultiselect());
        assertEquals("my-filter", settings.getFileFilterString());
        assertEquals("my-colour", settings.getBackgroundColour());
        assertEquals("my-verify-url", settings.getVerifyRequestURL());
        assertEquals(JFileChooser.FILES_ONLY, settings.getFileSelectionMode());
    }

    @Test
    public void testUsesDefaultsWhenNotSet() throws MissingParameterException
    {
        JApplet applet = mock(JApplet.class);

        when(applet.getParameter("uploadURL")).thenReturn("my-server");
        when(applet.getParameter("destDir")).thenReturn(null);
        when(applet.getParameter("sendVerifyRequestFirst")).thenReturn(null);
        when(applet.getParameter("allowMultiselect")).thenReturn(null);
        when(applet.getParameter("filter")).thenReturn(null);
        when(applet.getParameter("backgroundColour")).thenReturn(null);
        when(applet.getParameter("verifyRequestURL")).thenReturn(null);
        when(applet.getParameter("fileSelectionMode")).thenReturn(null);

        AppletSettings settings = new AppletSettings(applet);

        assertNull(settings.getDestinationDirectory());
        assertFalse(settings.isSendVerifyRequestFirst());
        assertFalse(settings.isAllowMultiselect());
        assertNull(settings.getFileFilterString());
        assertEquals("#FFFFFF", settings.getBackgroundColour());
        assertNull(settings.getVerifyRequestURL());
        assertEquals(JFileChooser.FILES_AND_DIRECTORIES, settings.getFileSelectionMode());
    }

    @Test(expected = MissingParameterException.class)
    public void testThrowsExceptionIfServerURLNotSet() throws MissingParameterException
    {
        JApplet applet = mock(JApplet.class);
        when(applet.getParameter("uploadURL")).thenReturn(null);
        new AppletSettings(applet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionIfFileSelectionModeNotValid() throws MissingParameterException
    {
        JApplet applet = mock(JApplet.class);
        when(applet.getParameter("uploadURL")).thenReturn("my-server");
        when(applet.getParameter("destDir")).thenReturn(null);
        when(applet.getParameter("sendVerifyRequestFirst")).thenReturn(null);
        when(applet.getParameter("filter")).thenReturn(null);
        when(applet.getParameter("backgroundColour")).thenReturn(null);
        when(applet.getParameter("allowMultiselect")).thenReturn(null);
        when(applet.getParameter("verifyRequestURL")).thenReturn(null);
        when(applet.getParameter("fileSelectionMode")).thenReturn("blah");
        new AppletSettings(applet);
    }
    
    @Test
    public void testHandlesAllThreeValidFileSelectionModes() throws MissingParameterException
    {
        JApplet applet = mock(JApplet.class);

        when(applet.getParameter("uploadURL")).thenReturn("my-server");
        when(applet.getParameter("destDir")).thenReturn("my-dest");
        when(applet.getParameter("sendVerifyRequestFirst")).thenReturn("true");
        when(applet.getParameter("allowMultiselect")).thenReturn("true");
        when(applet.getParameter("filter")).thenReturn("my-filter");
        when(applet.getParameter("backgroundColour")).thenReturn("my-colour");
        when(applet.getParameter("verifyRequestURL")).thenReturn("my-verify-url");

        when(applet.getParameter("fileSelectionMode")).thenReturn("files_only");
        AppletSettings settings = new AppletSettings(applet);
        assertEquals(JFileChooser.FILES_ONLY, settings.getFileSelectionMode());
        
        when(applet.getParameter("fileSelectionMode")).thenReturn("folders_only");
        settings = new AppletSettings(applet);
        assertEquals(JFileChooser.DIRECTORIES_ONLY, settings.getFileSelectionMode());
        
        when(applet.getParameter("fileSelectionMode")).thenReturn("files_and_folders");
        settings = new AppletSettings(applet);
        assertEquals(JFileChooser.FILES_AND_DIRECTORIES, settings.getFileSelectionMode());
    }
}
