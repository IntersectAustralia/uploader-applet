/**
 * Copyright (C) Intersect 2011.
 * 
 * This module contains Proprietary Information of Intersect,
 * and should be treated as Confidential.
 *
 * $Id$
 */
package au.org.intersect.uploader.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.json.JSONException;
import org.junit.Test;

import au.org.intersect.uploader.ui.FileOrganiser;

/**
 * 
 * @version $Rev: 29 $
 */
public class FilePackageFactoryTest
{
    private String destDir = "test/";

    @Test
    public void testCreateFilePackageForSingleFile() throws JSONException
    {
        File file = new File("test-data/testdir/file1.txt");

        FilePackage fp = FilePackageFactory.buildFilePackage(destDir, new FileOrganiser(new File[] {file}));
        assertNotNull(fp.getJSON());
        Map<String, File> fmap = fp.getFiles();
        assertNotNull(fmap);
        assertEquals(1, fmap.size());
        assertEquals(file, fmap.get("file_1"));
        assertTrue(destDir.equals(fp.getDestinationDirectory()));
    }

    @Test
    public void testCreateFilePackageForMultipleFile() throws JSONException
    {
        File file1 = new File("test-data/testdir/file1.txt");
        File file2 = new File("test-data/testdir/file2.txt");

        FilePackage fp = FilePackageFactory.buildFilePackage(destDir, new FileOrganiser(new File[] {file1, file2}));
        assertNotNull(fp.getJSON());
        Map<String, File> fmap = fp.getFiles();
        assertNotNull(fmap);
        assertEquals(2, fmap.size());
        assertEquals(file1, fmap.get("file_1"));
        assertEquals(file2, fmap.get("file_2"));
        assertTrue(destDir.equals(fp.getDestinationDirectory()));
    }

    @Test
    public void testCreateFilePackageForSingleFileNullDestDir() throws JSONException
    {
        File file = new File("test-data/testdir/file1.txt");

        FilePackage fp = FilePackageFactory.buildFilePackage(null, new FileOrganiser(new File[] {file}));
        assertNotNull(fp.getJSON());
        Map<String, File> fmap = fp.getFiles();
        assertNotNull(fmap);
        assertEquals(1, fmap.size());
        assertNull(fp.getDestinationDirectory());
    }

    @Test
    public void testCreateFilePackageForDirectory() throws JSONException
    {
        File file = new File("test-data/testdir");
        FilePackage fp = FilePackageFactory.buildFilePackage(destDir, new FileOrganiser(new File[] {file}));
        assertNotNull(fp.getJSON());
        Map<String, File> fmap = fp.getFiles();
        assertNotNull(fmap);
        assertEquals(2, fmap.size());
        assertTrue(destDir.equals(fp.getDestinationDirectory()));
    }

    @Test
    public void testCreateFilePackageForDirectoryNullDestDir() throws JSONException
    {
        File file = new File("test-data/testdir/");
        FilePackage fp = FilePackageFactory.buildFilePackage(null, new FileOrganiser(new File[] {file}));
        assertNotNull(fp.getJSON());
        assertNull(fp.getDestinationDirectory());
    }

    @Test
    public void testCreateFilePackageForNested() throws JSONException
    {
        File file = new File("test-data/");
        FilePackage fp = FilePackageFactory.buildFilePackage(destDir, new FileOrganiser(new File[] {file}));
        assertNotNull(fp.getJSON());
        Map<String, File> fmap = fp.getFiles();
        assertNotNull(fmap);
        assertEquals(10, fmap.size());
        assertTrue(destDir.equals(fp.getDestinationDirectory()));
    }
}
