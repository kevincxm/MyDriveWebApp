package com.websystique.springmvc.storage.filesystem;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.apache.log4j.Logger;

/**
 * 
 */

/**
 * @author pshrvst2
 *
 */
public class FileReceiverInstance extends Thread 
{
	private static Logger log = Logger.getLogger(FileReceiverInstance.class);
	private Socket clientSocket = null;
	
	public FileReceiverInstance(Socket clientSocket) 
	{
		log.info("File transfer connection established at socket = " + clientSocket);
		this.clientSocket = clientSocket;
	}

	public void run()
	{
		try 
		{
			log.info("File transfer started at File receiver instance");
			DataInputStream dataIpStream = new DataInputStream(clientSocket.getInputStream());
			String fileNameWithType = dataIpStream.readUTF();
			String keyWord[] = fileNameWithType.split(":");
			String absoluteFilePath = null;
			byte[] buffer = new byte[16*1024];
			int bytesRead;
			
			// TODO *************************
		    if(keyWord[1].equals("get"))
				absoluteFilePath = Node.localFilePath+keyWord[0];
			else
				absoluteFilePath = Node.sdfsFilePath+keyWord[0]; 
			
			log.info("File saved is: "+absoluteFilePath);
            long fileSize = dataIpStream.readLong();
           
            File downloadedFile = new File(absoluteFilePath);
            FileOutputStream fos = new FileOutputStream(downloadedFile); 
            /*for(int i =0;i<fileSize;i++)
            {          
            	int index;
            	while((index=dataIpStream.read())!=-1)
            	{
            		dos.write(index);
            	}
            }*/
            
            while (fileSize > 0 && (bytesRead = dataIpStream.read(buffer, 0, (int) Math.min(buffer.length, fileSize))) != -1) {
            	fos.write(buffer, 0, bytesRead);
            	fileSize -= bytesRead;
            }
            fos.close();
            dataIpStream.close();
            clientSocket.close();
            log.info("File received. Socket connection instance closed");
            
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	 }

}
