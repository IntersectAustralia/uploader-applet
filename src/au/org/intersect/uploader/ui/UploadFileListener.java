/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.apache.log4j.Logger;
import org.json.JSONException;

import au.org.intersect.uploader.config.AppletSettings;
import au.org.intersect.uploader.http.HttpCallResult;
import au.org.intersect.uploader.manager.PreVerificationManager;
import au.org.intersect.uploader.manager.UploadManager;
import au.org.intersect.uploader.responsehandling.PreVerificationResultHandler;
import au.org.intersect.uploader.responsehandling.ResultJsonParser;
import au.org.intersect.uploader.responsehandling.UploadResultHandler;

/**
 * 
 * @version $Rev: 29 $
 */
public class UploadFileListener implements ActionListener
{
    private static final Logger LOG = Logger.getLogger(UploadFileListener.class);

    private UploadUI parent;
    private JFileChooser chooser;
    private UploadManager uploadManager;
    private FileOrganiser selectedFiles;
    private final AppletSettings settings;
    private JavascriptCallbacks javascriptCallbacks;

    public UploadFileListener(UploadUI parent, UploadManager uploadManager, JavascriptCallbacks javascriptCallbacks,
            JFileChooser chooser, AppletSettings settings)
    {
        LOG.debug("UploadFileListener start");
        this.parent = parent;
        this.chooser = chooser;
        this.uploadManager = uploadManager;
        this.javascriptCallbacks = javascriptCallbacks;
        this.settings = settings;
        LOG.debug("UploadFileListener end");
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        LOG.debug("UploadFileListener actionPerformed: start");
        try
        {
            boolean doUpload = displayFileChooser();

            if (!doUpload)
            {
                return;
            }

            String authToken = javascriptCallbacks.getAuthToken();
            javascriptCallbacks.startingWork();
            javascriptCallbacks.startingFile(selectedFiles);

            if (settings.isSendVerifyRequestFirst())
            {
                verifyAndUpload(authToken);
            }
            else
            {
                upload(authToken);
            }
            javascriptCallbacks.finishedWork();
        }
        catch (UnauthorisedAccessException uae)
        {
            LOG.error("UploadFileListener actionPerformed: " + uae.getMessage());
            javascriptCallbacks.unauthorised();
        }
        catch (Exception e)
        {
            LOG.error("UploadFileListener actionPerformed: " + e.getMessage());
            javascriptCallbacks.somethingWentWrong(e.getMessage());
        }
        LOG.debug("UploadFileListener actionPerformed: end");

    }

    private void verifyAndUpload(String authToken) throws JSONException, IOException
    {
        LOG.debug("UploadFileListener verifyAndUpload: start");
        HttpCallResult result = new PreVerificationManager(settings).verify(selectedFiles, authToken);
        LOG.debug("UploadFileListener PreVerificationManager result: " + result.getResponseCode());
        LOG.debug("UploadFileListener verifyAndUpload: result success");

        new PreVerificationResultHandler(javascriptCallbacks, new ResultJsonParser()).handleResult(selectedFiles,
                result);
        if (result.success() && !selectedFiles.isEmpty())
        {
            LOG.debug("UploadFileListener verifyAndUpload: calling upload()");
            // Get new auth token for upload
            upload(javascriptCallbacks.getAuthToken());
        }
        LOG.debug("UploadFileListener verifyAndUpload: end");
    }

    private void upload(String authToken) throws IOException, JSONException
    {
        HttpCallResult result = uploadManager.upload(selectedFiles, authToken);

        new UploadResultHandler(javascriptCallbacks).handleResult(selectedFiles, result);
    }

    private boolean displayFileChooser()
    {
        parent.disableSelectButton();
        chooser.setVisible(true);
        int chooseResult = chooser.showOpenDialog(parent);
        parent.enableSelectButton();
        if (chooseResult == JFileChooser.CANCEL_OPTION)
        {
            chooser.setVisible(false);
            return false;
        }
        else if (chooseResult == JFileChooser.APPROVE_OPTION)
        {
            if (this.settings.isAllowMultiselect())
            {
                this.selectedFiles = new FileOrganiser(chooser.getSelectedFiles());
            }
            else
            {
                this.selectedFiles = new FileOrganiser(new File[] {chooser.getSelectedFile()});
            }
            return true;
        }
        return false;
    }

}
