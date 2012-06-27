/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.ui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import au.org.intersect.uploader.config.AppletSettings;
import au.org.intersect.uploader.manager.UploadManager;

/**
 * 
 * @version $Rev: 29 $
 */
public class UploadUI extends JPanel
{

    private static final long serialVersionUID = 3L;

    private static final Logger LOG = Logger.getLogger(UploadUI.class);

    private final JButton selectButton = new JButton("Select...");
    private final JFileChooser fileChooser = new JFileChooser();

    public UploadUI(JavascriptCallbacks javascriptCallbacks, AppletSettings settings)
    {
        LOG.debug("UploadUI start");
        FileNameExtensionFilter filter = new FileFilterFactory().createExtensionFilter(settings.getFileFilterString());
        if (filter != null)
        {
            fileChooser.setFileFilter(filter);
        }
        fileChooser.setFileSelectionMode(settings.getFileSelectionMode());
        fileChooser.setApproveButtonText("Upload");
        fileChooser.setMultiSelectionEnabled(settings.isAllowMultiselect());

        add(selectButton);
        LOG.debug("UploadUI: about to add UploadFileListener to selectButton");
        selectButton.addActionListener(new UploadFileListener(this, new UploadManager(settings), javascriptCallbacks,
                fileChooser, settings));
        setColour(settings.getBackgroundColour());
        LOG.debug("UploadUI end");
    }

    private void setColour(String colourValue)
    {
        int red = Integer.parseInt(colourValue.substring(1, 3), 16);
        int green = Integer.parseInt(colourValue.substring(3, 5), 16);
        int blue = Integer.parseInt(colourValue.substring(5, 7), 16);
        super.setBackground(new Color(red, green, blue));
    }

    public void disableSelectButton()
    {
        LOG.debug("disabling select button");
        selectButton.setEnabled(false);
    }

    public void enableSelectButton()
    {
        LOG.debug("enabling select button");
        selectButton.setEnabled(true);
    }

}
