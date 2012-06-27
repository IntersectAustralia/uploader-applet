/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.ui;

/**
 *
 * @version $Rev: 29 $
 */
public class InvalidFilterStringException extends RuntimeException
{

    private static final long serialVersionUID = 1L;
    
    public InvalidFilterStringException(String filterString)
    {
        super("Invalid Filter String: " + filterString);
    }
}
