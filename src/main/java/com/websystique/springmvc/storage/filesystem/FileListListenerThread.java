package com.websystique.springmvc.storage.filesystem;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;


/**
 * 
 */

/**
 * @author pshrvst2
 *
 */
public class FileListListenerThread extends Thread{
	
	public static Logger _logger = Logger.getLogger(FileListListenerThread.class);
	private int port;
	private Node node;
	/**
	 * 
	 */
	public FileListListenerThread(int port, Node node) 
	{
		this.port = port;
		this.node = node;
	}

	public void run()
	{
		_logger.info("FileListListenerThread is activated! Listening ....");
		byte[] data = new byte[4096];
		DatagramSocket listernerSocket;
		try 
		{
			listernerSocket = new DatagramSocket(port);
			
			while(!node._fileListListenerThreadStop)
			{
				try 
				{
					boolean validMsg = false;
					DatagramPacket receivedPacket = new DatagramPacket(data, data.length);
					listernerSocket.receive(receivedPacket);
					int port = receivedPacket.getPort();
					InetAddress ipAddress = receivedPacket.getAddress();
					_logger.info("Received FileList from: "+ipAddress+" at port: "+port);

					byte[] receivedBytes = receivedPacket.getData();
					ByteArrayInputStream bais = new ByteArrayInputStream(receivedBytes);
					ObjectInputStream objInpStream = new ObjectInputStream(bais);
					@SuppressWarnings("unchecked")
					
					HashMap<String, List<String>> map = (HashMap<String, List<String>>) objInpStream.readObject();

					// check the message counts and see whether your file list is up to date
					for (HashMap.Entry<String, List<String>> record : map.entrySet())
					{
						String fileName = record.getKey().trim();
						_logger.info("******Received entries for file name = "+fileName+"****************");
						List<String> list = record.getValue();
						if(fileName.equals("msg#"))
						{
							
							String msgCounter = list.get(0);
							if(Integer.valueOf(msgCounter) > node._fileMsgCounter)
							{
								validMsg = true;
								// update the file message count here and copy the list later
								node._fileMsgCounter = Integer.valueOf(msgCounter);
							}
						}
						
					}
					// if the counts is greater than _fileMsgCounter, replace the fileList from map 
					if (validMsg == true)
					{
						_logger.info("FileListListenerThread: the file list got updated with counts:" + node._fileMsgCounter);
						Node._fileMap.clear();
						
						for (HashMap.Entry<String, List<String>> record : map.entrySet())
						{				
							Node._fileMap.put(record.getKey(),record.getValue());		
						}
						
						// this will only happen when the machine is not the leader, the leader only update its filelist after the file operation is done.
						Thread fileListThread = new FileListSenderThread(node._gossipFileListPort,false,null,node);
						fileListThread.run();
					}
					objInpStream.close();
					bais.close();

				}
				catch (EOFException e2 )
				{
					_logger.error(" The Filelist is full!!!");
				}
				catch (IOException e) 
				{
					_logger.error(e);
					//e.printStackTrace();
				}		
				catch (ClassNotFoundException e) 
				{
					_logger.error(e);
					//e.printStackTrace();
				}                
			}
		}
		catch (SocketException e1)
		{
			_logger.error(e1);
			e1.printStackTrace();
		}
	}
}
