/**
 * 
 */
package com.websystique.springmvc.compression;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Piyush
 *
 */

public class CompressionFactory 
{
    
    interface StreamFactory
    {
        public OutputStream getStream( final OutputStream underlyingStream ) throws IOException;
    }
    
    public void compressUsingGzip(String fileName)
    {
    	System.out.println("CompressionFactory -> FileName = "+fileName);
    	GZip gt = new GZip();
        gt.compressGzipFile(fileName, fileName+".zip");
    }
    
    public void deCompressUsingGzip(String fileName, String newFileName)
    {
    	GZip gt = new GZip();
    	gt.decompressGzipFile(fileName, newFileName);
    }
}