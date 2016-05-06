package com.websystique.springmvc.storage.filesystem;
import java.util.List;

/**
 * 
 */

/**
 * @author Piyush
 *
 */
public class MyDriveFSManager {

	/**
	 * @param args
	 */
	private static Node node;
	
	public MyDriveFSManager()
	{
		node = new Node("xiaoming");
	}
	
	public MyDriveFSManager(String user)
	{
		node = new Node(user);
	}
	
	public static void main(String[] args) 
	{
		node = new Node("xiaoming");
		node.begin();
	}
	
	public void putFile(String file, String userName)
	{
		node.putFile(file);
	}
	
	public List<String> getFileList(String userName)
	{
		return node.getAllFiles(userName);
	}
	
	public void downloadFile(String userName, String fileName)
	{
		node.getFile(fileName);
	}
	
	public void deleteFile(String userName, String fileName)
	{
		node.deleteFile(fileName);
	}

}
