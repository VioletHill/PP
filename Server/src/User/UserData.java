package User;

import java.io.Serializable;
import java.util.Vector;

import SocketServer.Server;
import SocketServer.ServerToMutiClient;

public class UserData implements Serializable
{
	private String account;			//账号
	private String password;
	private String email;
	private boolean isLog;
	private Vector<String> friend=new Vector<String>();
	private Vector<String> message=new Vector<String>();
	
	public UserData()
	{
		account=new String();
		password=new String();
		email=new String();
	}
	
	public UserData(String account,String password,String email)
	{
		this.account=account;
		this.password=password;
		this.email=email;
	}
	
	public UserData(String str)
	{
		account=str.substring(0,str.indexOf(' '));
		str=str.substring(str.indexOf(' ')+1);
		
		password=str.substring(0,str.indexOf(' '));
		str=str.substring(str.indexOf(' ')+1);
		
		email=str.substring(0);
	}
	
	public Vector<String> getFriend()
	{
		return friend;
	}
	
	public String getAccount()
	{
		return account;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	
	//0 等待复返确认
	//1 用户不存在 
	//2 未登陆
	public int addFriendApply(String friendAccount)			
	{
	
		UserData userData=User.sharedUser().getUserDataByAccount(friendAccount);
		if (userData==null) return 1;
		if (userData.getIsLog()==false)
		{
			userData.pushMessage("-f"+this.getAccount());
			return 2;
		}
		
		Server.sharedServer().findSocketByAccount(friendAccount).sendAddFriendByAccount(this.getAccount());
		return 0;
	}
	
	public void addFriend(String account)
	{
		for (int i=0; i<friend.size(); i++)
			if (friend.get(i).equals(account)) return ;
		friend.add(account);
	}
	
	public Vector<String> getMessage()
	{
		return message;
	}
	
	public void pushMessage(String str)
	{
		message.add(str);
	}
	
	public void clearMessage()
	{
		message.clear();
	}
	
	public void removeFriend(String account)
	{
		friend.remove(account);
	}
	public void setIsLog(boolean isLog)
	{
		this.isLog=isLog;
	}
	
	public boolean getIsLog()
	{
		return isLog;
	}
}
