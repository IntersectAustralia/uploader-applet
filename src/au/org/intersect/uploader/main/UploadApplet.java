/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.main;

import javax.swing.JApplet;

import org.apache.log4j.Logger;

import netscape.javascript.JSObject;

import au.org.intersect.uploader.config.AppletSettings;
import au.org.intersect.uploader.ui.JavascriptCallbacks;
import au.org.intersect.uploader.ui.JavascriptRunner;
import au.org.intersect.uploader.ui.UploadUI;

/**
 * 
 * @version $Rev: 29 $
 */
public class UploadApplet extends JApplet
{

    private static final Logger LOG = Logger.getLogger(UploadApplet.class);

    private static final long serialVersionUID = 1L;
    private static final int HEIGHT = 500;
    private static final int WIDTH = 500;

    private AppletSettings settings;
    private JavascriptCallbacks javascriptCallbacks;

    static 
    {
        // This is so log4j does not go looking for any BeanInfo classes, 
        // which would result in lots of unnecessary routing error logged
        // on the server console
        // Fix was found here :
        // http://marc.info/?l=log4j-user&m=110111467201877&w=2
        java.beans.Introspector.setBeanInfoSearchPath(new String[0]);
    }
    
    public void init()
    {
        LOG.debug("UploadApplet init start");
        try
        {
            this.settings = new AppletSettings(this);

            // Create a JSObject representing the Window containing the applet
            JSObject javascriptObject = JSObject.getWindow(this);
            javascriptCallbacks = new JavascriptCallbacks(new JavascriptRunner(javascriptObject));

            // Execute a job on the event-dispatching thread: creating this applet's GUI.
            javax.swing.SwingUtilities.invokeAndWait(new Runnable()
            {
                public void run()
                {
                    LOG.debug("UploadApplet init: call createGUI()");
                    createGUI();
                }
            });
        }
        catch (Exception e)
        {
            LOG.error("Applet did not initialise", e);
        }
        LOG.debug("UploadApplet init end");
    }

    private void createGUI()
    {
        setContentPane(new UploadUI(javascriptCallbacks, settings));
        setSize(HEIGHT, WIDTH);
        javascriptCallbacks.appletReady();
    }
}
