package SocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Server
{
	private final int socketPort=5555;
	private static Server server;
	private ServerSocket serverSocket;
	private Vector<ServerToMutiClient> userSocket=new Vector<ServerToMutiClient>(); 
	
	public static Server sharedServer()
	{
		if (server==null) server=new Server();
		return server;
	}
	
	public Server()
	{
	}
	
	public Vector<ServerToMutiClient> getUserSocket()
	{
		return userSocket;
	}
	
	public void start()
	{
		try 
		{
			serverSocket=new ServerSocket(socketPort);
			while (true)
			{
				ServerToMutiClient stmc=new ServerToMutiClient(serverSocket.accept());
				new Thread(stmc).start();
			}
		}
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null,"服务器建立失败",null, JOptionPane.WARNING_MESSAGE);
			return ;
		}
	}
	public void addServer(ServerToMutiClient server)
	{
		userSocket.add(server);
	}
	public ServerToMutiClient findSocketByAccount(String account)
	{
		for (int i=0; i<userSocket.size(); i++)
		{
			if (userSocket.get(i).getUserData().getAccount().equals(account)) return userSocket.get(i);
		}
		return null;
	}
	public void removeSocketByAccount(String account)
	{
		for (int i=0; i<userSocket.size(); i++)
		{
			if (userSocket.get(i).getUserData().getAccount().equals(account))
			{
				userSocket.remove(i);
				return ;
			}
		}
	}
}
