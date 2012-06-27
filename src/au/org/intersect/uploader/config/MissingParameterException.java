/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id$
 */
package au.org.intersect.uploader.config;

/**
 * 
 *
 * @version $Rev: 29 $
 */
public class MissingParameterException extends Exception
{
    private static final long serialVersionUID = 1L;

    public MissingParameterException(String parameterKey)
    {
        super("Applet requires parameter [" + parameterKey + "] to be set.");
    }

}
