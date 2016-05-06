package com.websystique.springmvc.filesystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;


public class OkMessageThread extends Thread
{
	public static Logger _logger = Logger.getLogger(OkMessageThread.class);
	private int port;
	private String ip;
	private Node node;
	
	public OkMessageThread(int port, String ip, Node node)
	{
		this.port = port;
		this.ip = ip;
		this.node = node;
	}
	public void run()
	{
		try
		{
			Socket socket = new Socket(ip, port);
			BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(node._okMessage);
			
			String servermsg ="";
			while((servermsg = in.readLine()) !=null)
			{
				_logger.info("Coordniator message has been send out and the server side returns : "+ servermsg);
			}
			
			out.close();
			in.close();
			socket.close();
			
		}
		catch(IOException e)
		{
			//e.getMessage();
		}
	}
	
}
