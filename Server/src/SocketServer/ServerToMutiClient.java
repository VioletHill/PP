package SocketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import User.User;
import User.UserData;


/*���ܼ����ļ� (�ȴ�����)
 * 
 * -a   �˺�����
 * -t	����
 * -l	��½
 * -f	��Ӻ���
 * -g   ��������ظ�
 * 
 * 
 * 
 * 
 */
public class ServerToMutiClient implements Runnable
{
	private Socket socket;
	private UserData userClient;			//�˷������ӵ��Ǹ��û�
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public ServerToMutiClient(Socket socket)
	{
		this.socket=socket;
	}
	
	////
	public void setUserData(String account)
	{
		this.userClient=User.sharedUser().getUserDataByAccount(account);
	}
	
	public void sendUserData()
	{
		try 
		{
			out.writeObject(userClient.getFriend());
			out.flush();
			out.writeObject(userClient.getMessage()); 		//������Ϣ
			out.flush();
			userClient.clearMessage();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	
	}
	
	public UserData getUserData()
	{
		return userClient;
	}
	
	public void sendAddFriendByAccount(String account)
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
	
	public void sendMessage(String str)
	{
		try 
		{
			out.writeObject(str);
			out.flush();
		} catch (IOException e) {
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
	
	public void run()
	{
		String account;
		String password;
		String email;
		ServerToMutiClient accountServer;
		try
		{
			in=new ObjectInputStream(socket.getInputStream());								//������ն���Ϣ
			out=new ObjectOutputStream(socket.getOutputStream());							//���巢��Ŀ��
			while (true)																	//���ֺͿͻ�ͨ��
			{
				
				String str=(String)in.readObject();			//��ȡ���͹�������ϢW
				switch (str.charAt(1)) 
				{
				case 'a':
					str=str.substring(2);
					if (User.sharedUser().apply(new UserData(str)))
					{	
						out.writeObject("true");
						out.flush();
					}
					else 
					{	
						out.writeObject("false");
						out.flush();
					}
					socket.close();
					return ;
				case 'l':
					str=str.substring(2);
					account=str.substring(0,str.indexOf(' '));
					str=str.substring(str.indexOf(' ')+1);
					password=str.substring(0);
					if (Server.sharedServer().findSocketByAccount(account)!=null)
					{
						out.writeObject("login");
						out.flush();
						socket.close();
						return ;
					}
					else if (User.sharedUser().login(account,password))
					{	
						out.writeObject("true");
						out.flush();
						setUserData(account);		//���ø��̶߳�Ӧ���û�
						Server.sharedServer().addServer(this);
						userClient.setIsLog(true);
						sendUserData();
						break;
					}
					else
					{
						out.writeObject("false");
						out.flush();	
						socket.close();
						return ;
					}
				case 'f':	
					str=str.substring(2);
					out.writeObject("-g"+str+' '+userClient.addFriendApply(str));	//userClient�������strΪ����
					out.flush();
					
					break;
				case 'g':
					str=str.substring(2);
					account=str.substring(0,str.indexOf(' '));
					str=str.substring(str.indexOf(' ')+1);
					if (str.equals("yes"))
					{
						userClient.addFriend(account);
						User.sharedUser().getUserDataByAccount(account).addFriend(userClient.getAccount());
						Server.sharedServer().findSocketByAccount(account).sendMessage("-g"+userClient.getAccount()+' '+4);
					}
					else 
					{
						Server.sharedServer().findSocketByAccount(account).sendMessage("-g"+userClient.getAccount()+' '+3);
					}
					break ;
				case 't':	
					str=str.substring(2);
					account=str.substring(0,str.indexOf(' '));
					String message=str.substring(str.indexOf(' ')+1);
					accountServer=Server.sharedServer().findSocketByAccount(account);
					if (accountServer!=null) accountServer.sendMessage(userClient.getAccount(),message);
					else
					{
						User.sharedUser().getUserDataByAccount(account).pushMessage("-t"+userClient.getAccount()+' '+message);
					}
					break ;
				case 'd':
					account=str.substring(2);
					userClient.removeFriend(account);
					User.sharedUser().getUserDataByAccount(account).removeFriend(userClient.getAccount());
					accountServer=Server.sharedServer().findSocketByAccount(account);
					if (accountServer!=null) accountServer.sendMessage("-d"+userClient.getAccount());
					else User.sharedUser().getUserDataByAccount(account).pushMessage("-d"+userClient.getAccount());
					break;
				case 'e':
					userClient.setIsLog(false);
					Server.sharedServer().removeSocketByAccount(userClient.getAccount());
					socket.close();
					return ;
				default:
					break;
				}
			}
		}
		catch (IOException ex)
		{
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
