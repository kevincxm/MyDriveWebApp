/**
 * 
 */
package com.websystique.springmvc.compression;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Piyush
 *
 */
public class GZip extends CompressionFactory{
	
	public void decompressGzipFile(String gzipFile, String newFile) 
	{
        try 
        {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1)
            {
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            gis.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
         
    }
 
    public void compressGzipFile(String file, String gzipFile) 
    {
        try 
        {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(gzipFile);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1)
            {
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

}
