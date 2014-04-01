package SocketClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JOptionPane;

import UserData.SelfData;
import UserData.UserData;
import Window.WindowMain;

public class Client implements Runnable
{
	private final int socketPort=5555;
	private static Client client;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Thread thread;
	
	public Client()
	{
	}
	
	public static Client sharedClient()
	{
		if (client==null)	client=new Client();
		return client;
	}
	
	public void exit()
	{
		try
		{
			thread.stop();
			out.writeObject("-e");
			out.flush();
			socket.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addFriend(String account)
	{
		try
		{
			out.writeObject("-f"+account);
			out.flush();	
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void deleteFriend(String account)
	{
		try
		{
			out.writeObject("-d"+account);
			out.flush();	
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		WindowMain.sharedWindow().removeFriend(account);
	}
	
	public void startAcceptUserData()
	{
		try 
		{
			Vector<String> friend=(Vector<String> )in.readObject();
			SelfData.getSelfData().setFriend(friend);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendMessage(String account,String message)
	{
		try 
		{
			out.writeObject("-t"+account+' '+message);
			out.flush();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	public void startAcceptMessage()		//接受离线消息
	{
		Vector<String> message;
		try {
			message = (Vector<String>)in.readObject();
			for (int i=0; i<message.size(); i++)
			{
				dealWith(message.get(i));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public boolean applyAccount(UserData userData)
	{
		boolean isApplySuccess=false;
		try 
		{	
			socket=new Socket(InetAddress.getLocalHost(), socketPort);
			//发送信息
			out=new ObjectOutputStream(socket.getOutputStream());
			String str="-a"+userData.getAccount()+' '+userData.getPassword()+' '+userData.getEmail();
			out.writeObject(str);
			out.flush();
			//接受消息  是否申请成功
			in=new ObjectInputStream(socket.getInputStream());
			String message=(String)in.readObject();
			if (message.equals("true"))
			{
				JOptionPane.showMessageDialog(null, "账号申请成功", "恭喜你", JOptionPane.NO_OPTION);
				isApplySuccess=true;
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "对不起！该账号已存在","抱歉",JOptionPane.ERROR_MESSAGE);
				isApplySuccess=false;
			}
			socket.close();
			return isApplySuccess;
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 		//异常  连接不到服务器
		{
			JOptionPane.showMessageDialog(null, "无法连接到服务器：\n" +
					                            "可能原因：\n" +
					                            "一、检查网络是否正常;\n" +
					                            "二、服务器维护中\n" +
					                            "请稍后再试", "申请错误", JOptionPane.WARNING_MESSAGE);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return false;
	}
	
	public boolean login(String account, String password)
	{
		boolean canLog=false;
		try 
		{
			
			socket=new Socket(InetAddress.getLocalHost(), socketPort);
			out=new ObjectOutputStream(socket.getOutputStream());
			//发送信息
			String str="-l"+account+' '+password;
			out.writeObject(str);
			out.flush();
			//接受消息  是否申请成功
			in=new ObjectInputStream(socket.getInputStream());
			String message=(String) in.readObject();
			if (message.equals("login"))
			{
				JOptionPane.showMessageDialog(null, "该账号已登陆","登录失败",JOptionPane.ERROR_MESSAGE);
				canLog=false;
				socket.close();
			}
			else if (message.equals("true"))
			{
				canLog=true;
				SelfData.initialize(account);
				startAcceptUserData();
				WindowMain.windowStart();
				startAcceptMessage();
				thread=new Thread(this);
				thread.start();
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "您的密码错误或该账户不存在","登录失败",JOptionPane.ERROR_MESSAGE);
				canLog=false;
				socket.close();
			}
			return canLog;
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 		//异常  连接不到服务器
		{
			JOptionPane.showMessageDialog(null, "无法连接到服务器：\n" +
					                            "可能原因：\n" +
					                            "一、检查网络是否正常;\n" +
					                            "二、服务器维护中\n" +
					                            "请稍后再试", "登陆错误", JOptionPane.WARNING_MESSAGE);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return false;
	}
	
	public void dealWith(String str)
	{
		String account;
		switch (str.charAt(1)) 
		{
		case 'g':
			str=str.substring(2);
			account=str.substring(0, str.indexOf(' '));
			str=str.substring(str.indexOf(' ')+1);
			int result=str.charAt(0)-48;
			switch (result) 
			{
			case 0:
				JOptionPane.showMessageDialog(null, "等待对方确认身份", "添加好友申请", JOptionPane.INFORMATION_MESSAGE, null);
				break;
			case 1:
				JOptionPane.showMessageDialog(null, "该好友不存在", "添加好友失败", JOptionPane.ERROR_MESSAGE, null);
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "该好友未登陆，无法确认信息，请静心等待对方确认", "好友申请成功", JOptionPane.INFORMATION_MESSAGE, null);
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "对方拒绝添加你为好友", "添加好友失败", JOptionPane.ERROR_MESSAGE, null);
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "恭喜你，又多了一位好友", "添加好友成功", JOptionPane.INFORMATION_MESSAGE, null);
				WindowMain.sharedWindow().addNewFriend(account);
				break;
			default:
				break;
			}
			break;
		case 'f':
			str=str.substring(2);
			account=str.substring(0);
			int ok=JOptionPane.showConfirmDialog(null, account+"请求添加你为好友", "来自系统的消息",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
			if (ok==JOptionPane.YES_OPTION)
			{
				WindowMain.sharedWindow().addNewFriend(account);
				try 
				{
					out.writeObject("-g"+account+' '+"yes");
					out.flush();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				
			}
			else 
			{
				try 
				{
					out.writeObject("-g"+account+' '+"no");
					out.flush();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				
			}
			break;
		case 't':
			str=str.substring(2);
			account=str.substring(0,str.indexOf(' '));
			str=str.substring(str.indexOf(' ')+1);
			WindowMain.sharedWindow().addMessage(account, str);
			break ;
		case 'd':
			account=str.substring(2);
			JOptionPane.showMessageDialog(null, account+"将你从好友中移除", "来自系统的消息", JOptionPane.INFORMATION_MESSAGE, null);
			WindowMain.sharedWindow().removeFriend(account);
			break ;
		default:
			break ;
		}
		
	}
	public void run()			//登陆完成后 开始接收来自服务器的消息
	{
		// f    有人请求添加好友
		// g	 添加好友回复
		// t	 好友发送消息
		String account;
		while (true)
		{
			try 
			{
				String str=(String)in.readObject();
				dealWith(str);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}
}
