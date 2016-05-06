/**
 * 
 */
package com.websystique.springmvc.storage;

/**
 * @author Piyush
 *
 */
public class StorageManager {
	
	private boolean dbON = false;
	private DBHandler dbHandler;
	private FSHandler fsHandler;
	private String userName;
	private String location;
	
	public StorageManager(boolean db, String user, String location)
	{
		this.dbON = db;
		this.setUserName(user);
		this.setLocation(location);
		if(db && dbHandler==null)
			dbHandler = new DBHandler(user, location);
		else if(!db && fsHandler==null)
			fsHandler = new FSHandler();
	}
	public boolean isDb() {
		return dbON;
	}
	public void setDbdbON(boolean db) {
		this.dbON = db;
	}
	public FSHandler getFSHandler(){
		return fsHandler;
	}
	public DBHandler getDBHandler(){
		return dbHandler;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
