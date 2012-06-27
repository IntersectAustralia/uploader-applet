/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id$
 */
package au.org.intersect.uploader.responsehandling;

/**
 * Holds the information returned by the server about the verification of a particular file.
 * 
 * @version $Rev: 29 $
 */
public class FileVerificationResult
{
    private static final String PROMPT_INSTRUCTION = "prompt";
    private static final String PROCEED_INSTRUCTION = "proceed";
    private static final String ABORT_INSTRUCTION = "abort";

    private String status;
    private String message;

    public FileVerificationResult(String status, String message)
    {
        super();
        this.status = status;
        this.message = message;
    }

    public String getStatus()
    {
        return status;
    }

    public String getMessage()
    {
        return message;
    }

    public boolean isAbort()
    {
        return ABORT_INSTRUCTION.equals(status);
    }

    public boolean isProceed()
    {
        return PROCEED_INSTRUCTION.equals(status);
    }

    public boolean isPrompt()
    {
        return PROMPT_INSTRUCTION.equals(status);
    }

}
