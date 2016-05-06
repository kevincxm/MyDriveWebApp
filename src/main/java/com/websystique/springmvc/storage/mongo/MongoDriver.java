/**
 * 
 */
package com.websystique.springmvc.storage.mongo;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.gridfs.*;
import com.websystique.springmvc.model.MyDriveFile;
import com.websystique.springmvc.model.MyDriveFileInfo;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piyush
 *
 */
public class MongoDriver {
	
	private DB db = null;
	private MongoClient mongoClient = null;
	private String mongoServerPrimary = "172.22.152.54";
	private String mongoServerSecondary1 = "172.22.152.53";
	private String mongoServerSecondary2 = "172.22.152.55";
	
	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}
	
	public MongoDriver(String db)
	{
		connect(db);
	}
	
	public void connect(String db)
	{
		// To connect to mongodb server
		
		List<ServerAddress> list = new ArrayList<ServerAddress>();
		ServerAddress add1 = null;
		ServerAddress add2 = null;
		ServerAddress add3 = null;
		try 
		{
			add1 = new ServerAddress(mongoServerPrimary, 27017);
			add2 = new ServerAddress(mongoServerSecondary1, 27017);
			add3 = new ServerAddress(mongoServerSecondary2, 27017);
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
		list.add(add1);
		list.add(add2);
		list.add(add3);
		
		this.mongoClient = new MongoClient(list);
		// Now connect to your databases
		this.db = this.mongoClient.getDB(db);
	}
	
	public void disConnect()
	{
		this.mongoClient.close();
	}
	
	
	public void createCollection(String collName)
	{
		// do not use, deprecated. Use Insert
		
		// an empty collection does not work
		BasicDBObject doc = new BasicDBObject();
		//DBCollection coll = db.getCollection("mycol2");
        doc.append("title", "mongo");
        doc.append("description", "database").append("likes", new Integer(200));
        doc.append("url", "http://www.tutorialspoint.com/mongo/").append("by", "tutorials point");
        
        this.db.createCollection(collName, doc);
		System.out.println("Collection created successfully");
	}
	
	public void insert()
	{
		// Insert logic
		// example. do not use
        BasicDBObject doc = new BasicDBObject();
        
		DBCollection coll = this.db.getCollection("mycol1");
        doc.append("title", "Mongo");
        doc.append("description", "database").append("likes", new Integer(200));
        doc.append("url", "http://www.tutorialspoint.com/mongo/").append("by", "tutorials point");
        
        coll.insert(doc);
	}
	
	public void insert(String fileType, String fileName, File file )
	{
		// Insert logic
        BasicDBObject doc = new BasicDBObject();
		DBCollection coll = this.db.getCollection("fileDtls");
        doc.append("type", fileType);
        doc.append("fileName", fileName);
        coll.insert(doc);
        gridFSInsert(file);
	}
	
	public void insert(MyDriveFile file)
	{
		// Insert logic
        BasicDBObject doc = new BasicDBObject();
		DBCollection coll = this.db.getCollection("fileDtls");
		doc.append("fileName", file.getFileName());
        doc.append("type", file.getFileType());
        doc.append("orgSize", file.getFileSize());
        doc.append("compressedSize", file.getCompressedFileSize());
        doc.append("createdDate", file.getCreatedDate());
        coll.insert(doc);
        gridFSInsert(file.getFile());
	}
	
	public boolean deleteFile(String fileName)
	{
		DBCollection coll = db.getCollection("fileDtls");
        System.out.println("Collection fileDtls selected successfully for deleting file : "+fileName);
        BasicDBObject doc = new BasicDBObject();
        doc.append("fileName", fileName);
        DBObject myDoc = coll.findOne(doc);
        coll.remove(myDoc);
        gridFSDeleteFile(fileName+".zip");
		return true;
	}
	
	public ArrayList<MyDriveFileInfo> search(String collectionName)
	{
		DBCollection coll = this.db.getCollection(collectionName/*"players"*/);
        System.out.println("Collection "+ collectionName +" selected successfully");
        
        DBCursor cursor = coll.find();
        int i = 1;
		ArrayList<MyDriveFileInfo> mdInfoList = new ArrayList<MyDriveFileInfo>();
        while (cursor.hasNext()) { 
           System.out.println("Document: "+i); 
           BasicDBObject obj = (BasicDBObject) cursor.next();
           MyDriveFileInfo mdInfo = new MyDriveFileInfo();
           
           try{ mdInfo.setFileName(obj.getString("fileName"));} catch(NullPointerException nle){}
           try{ mdInfo.setFileType(obj.getString("type"));} catch(NullPointerException nle){}
           try{ mdInfo.setFileSize(obj.getLong("orgSize"));} catch(NullPointerException nle){}
           try{ mdInfo.setCompressedFileSize(obj.getLong("compressedSize"));} catch(NullPointerException nle){}
           try{ mdInfo.setCreatedDate(obj.getDate("createdDate"));} catch(NullPointerException nle){}
           
           mdInfoList.add(mdInfo);
           System.out.println(obj); 
           i++;
        }
        
        return mdInfoList;
	}
	
	public void gridFSInsert(String fullFilePath, String fileName) throws IOException
	{
		// http://www.mkyong.com/mongodb/java-mongodb-save-image-example/
		
		String newFileName = fileName/*"test-image2"*/;
		File imageFile = new File(fullFilePath/*"C:/Users/Piyush/Desktop/i_mark_bold.png"*/);
		GridFS gfsPhoto = new GridFS(db, "fs");
		GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
		gfsFile.setFilename(newFileName);
		gfsFile.save();
	}
	
	public void gridFSInsert(File file)
	{
		// http://www.mkyong.com/mongodb/java-mongodb-save-image-example/
		
		String newFileName = file.getName();
		File imageFile = file;
		GridFS gfsPhoto = new GridFS(db, "fs");
		GridFSInputFile gfsFile = null;
		try 
		{
			gfsFile = gfsPhoto.createFile(imageFile);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		gfsFile.setFilename(newFileName);
		gfsFile.save();
	}
	
	public void gridFSGet(String fileName)
	{
		String newFileName = fileName;
		GridFS gfsPhoto = new GridFS(db, "fs");
		GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);
		System.out.println(imageForOutput);
	}
	
	public void gridFSDownload(String fileName, String uploadLocation) throws IOException
	{
		String newFileName = fileName;
		GridFS gfsPhoto = new GridFS(db, "fs");
		GridFSDBFile gridFile = gfsPhoto.findOne(newFileName);
		//imageForOutput.writeTo("/"); //output to the web servers
		gridFile.writeTo(new File(uploadLocation+fileName));
	}
	
	public void gridFSDeleteFile(String fileName)
	{
		String newFileName = fileName;
		GridFS gfsPhoto = new GridFS(db, "fs");
		gfsPhoto.remove(gfsPhoto.findOne(newFileName));
	}

}
