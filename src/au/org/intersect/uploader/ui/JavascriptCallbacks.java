/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.ui;

import java.io.File;

/**
 *
 * @version $Rev: 29 $
 */
public class JavascriptCallbacks
{

    private static final String APPLET_READY = "appletReady";
    
    private static final String STARTING_WORK_METHOD = "startingWork";
    private static final String FINISHED_WORK_METHOD = "finishedWork";

    private static final String GET_AUTH_TOKEN_METHOD = "getAuthToken";

    private static final String UPLOAD_STARTING_METHOD = "uploadStarting";

    private static final String HANDLE_ABORT_VERIFICATION_RESULT_METHOD = "handleAbortVerificationResult";
    private static final String HANDLE_PROMPT_VERIFICATION_RESULT_METHOD = "handlePromptVerificationResult";

    private static final String UPLOAD_FINISHED = "uploadFinished";
    private static final String UPLOAD_FAILED_METHOD = "uploadFailed";

    private static final String VERIFICATION_FAILED_METHOD = "verificationFailed";
    private static final String SOMETHING_WENT_WRONG_METHOD = "unexpectedError";
    private static final String UNAUTHORISED_METHOD = "unauthorisedError";

    private final JavascriptRunner runner;

    public JavascriptCallbacks(JavascriptRunner runner)
    {
        this.runner = runner;
    }

    public void appletReady()
    {
        runner.executeJavascriptMethod(APPLET_READY, new String[] {});
    }

    public String getAuthToken()
    {
        Object token = runner.executeJavascriptMethod(GET_AUTH_TOKEN_METHOD, new String[] {});
        if (!(token instanceof String))
        {
            throw new UnauthorisedAccessException("Could not obtain token");
        }
        return ((String) token).toString();

    }

    public void startingWork()
    {
        runner.executeJavascriptMethod(STARTING_WORK_METHOD, new String[] {});
    }

    public void finishedWork()
    {
        runner.executeJavascriptMethod(FINISHED_WORK_METHOD, new String[] {});
    }

    public void startingFile(FileOrganiser selectedFiles)
    {
        executeJavascriptMethodPerFile(UPLOAD_STARTING_METHOD, null, selectedFiles);
    }

    public void uploadFinished(String responseBody)
    {
        runner.executeJavascriptMethod(UPLOAD_FINISHED, new String[] {responseBody});
    }

    public void uploadDidNotComplete(int responseCode, FileOrganiser selectedFiles)
    {
        executeJavascriptMethodPerFile(UPLOAD_FAILED_METHOD, Integer.toString(responseCode), selectedFiles);
    }

    public void abortAfterVerification(String fileAbsolutePath, String message)
    {
        runner.executeJavascriptMethod(HANDLE_ABORT_VERIFICATION_RESULT_METHOD,
                new String[] {fileAbsolutePath, message});
    }

    public boolean promptAfterVerification(String fileAbsolutePath, String message)
    {
        Object promptResult = runner.executeJavascriptMethod(HANDLE_PROMPT_VERIFICATION_RESULT_METHOD, new String[] {
            fileAbsolutePath, message});
        if (!(promptResult instanceof Boolean))
        {
            throw new IllegalStateException("Javascript call to handlePromptVerificationResult must return a boolean");
        }
        return ((Boolean) promptResult).booleanValue();
    }

    public void verificationFailed(int responseCode, FileOrganiser selectedFiles)
    {
        executeJavascriptMethodPerFile(VERIFICATION_FAILED_METHOD, Integer.toString(responseCode), selectedFiles);
    }

    public void somethingWentWrong(String message)
    {
        runner.executeJavascriptMethod(SOMETHING_WENT_WRONG_METHOD, new String[] {message});
    }

    public void unauthorised()
    {
        runner.executeJavascriptMethod(UNAUTHORISED_METHOD, new String[] {});
    }

    private void executeJavascriptMethodPerFile(String method, String secondArgument, FileOrganiser selectedFiles)
    {
        for (File file : selectedFiles.files())
        {
            String[] args = secondArgument == null ? new String[] {file.getAbsolutePath()} : new String[] {
                file.getAbsolutePath(), secondArgument};
            runner.executeJavascriptMethod(method, args);
        }
    }

}
