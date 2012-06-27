/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id: intersect_codetemplates.xml 29 2010-07-16 05:45:06Z georgina $
 */
package au.org.intersect.uploader.ui;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.junit.Test;

/**
 *
 * @version $Rev: 29 $
 */
public class FileFilterFactoryTest
{
    private FileFilterFactory fff = new FileFilterFactory();
    
    @Test
    public void testValidFileFilterCreation1() throws IOException
    {
        FileNameExtensionFilter filter = fff.createExtensionFilter("asdf");
        File file = File.createTempFile("file1", ".asdf");
        assertTrue(filter.accept(file));
        file.delete();
    }
    
    @Test
    public void testValidFileFilterCreation2() throws IOException
    {
        FileNameExtensionFilter filter = fff.createExtensionFilter("123;asdf; pie");
        File file = File.createTempFile("file1", ".123");
        assertTrue(filter.accept(file));
        file.delete();
        file = File.createTempFile("file2", ".asdf");
        assertTrue(filter.accept(file));     
        file.delete();
        file = File.createTempFile("file3", ".pie");
        assertTrue(filter.accept(file));     
        file.delete();
    }
    
    @Test
    public void testInvalidFileFilterCreation() throws IOException
    {
        assertNull(fff.createExtensionFilter(" $$$; "));
    }
    
    @Test
    public void testNullFileFilterCreation() throws IOException
    {
        assertNull(fff.createExtensionFilter(null));
    }
    
    @Test
    public void testEmptyStringFilterCreation() throws IOException
    {
        assertNull(fff.createExtensionFilter(""));
    }
    
}
