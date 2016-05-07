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
	private static MyDriveFSManager mdfsManagerInstance;
	
	protected MyDriveFSManager(String user) 
	{
	      // Exists only to defeat instantiation.
		setNode(new Node(user));
	}
	
	public static MyDriveFSManager getInstance(String user) 
	{
	      if(mdfsManagerInstance == null) {
	    	  mdfsManagerInstance = new MyDriveFSManager(user);
	    	  mdfsManagerInstance.setNode(node);
	    	  node.begin();
	      }
	      return mdfsManagerInstance;
	   }
	
	public void putFile(String file, String userName)
	{
		getNode().putFile(file);
	}
	
	public List<String> getFileList(String userName)
	{
		return getNode().getAllFiles(userName);
	}
	
	public void downloadFile(String userName, String fileName)
	{
		getNode().getFile(fileName);
	}
	
	public void deleteFile(String userName, String fileName)
	{
		getNode().deleteFile(fileName);
	}

	public static Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		MyDriveFSManager.node = node;
	}

}
