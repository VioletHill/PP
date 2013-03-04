/*
 * 服务器对users的管理
 * 负责存放所有的users
 * 申请账号
 * 
 */


package User;

import java.util.Vector;	
public class User 
{
	Vector<UserData> userData=new Vector<UserData>();		//所有的用户
	static User user;
	
	public User() 
	{	
	}
	
	public static User sharedUser()
	{
		if (user==null) user=new User();
		return user;
	}
	
	public boolean apply(UserData newUser)
	{
		for (int i=0; i<userData.size(); i++)
		{
			if (userData.get(i).getAccount().equals(newUser.getAccount())) return false;
		}

		userData.add(newUser);
		return true;
	}
	
	public boolean login(String account,String password)
	{
		for (int i=0; i<userData.size(); i++)
		{
			if (userData.get(i).getAccount().equals(account))
			{
				if (userData.get(i).getPassword().equals(password)) return true;
				else return false;
			}
		}
		return false;
	}
	
	public void sendMail()
	{
		
	}
	
	public void showAllUser()
	{
		for (int i=0; i<userData.size(); i++)
		{
			System.out.println(userData.get(i).getAccount()+' '+userData.get(i).getPassword()+' '+userData.get(i).getEmail()+'\n');
		}
	}
	
	public UserData getUserDataByAccount(String account)
	{
		for (int i=0; i<userData.size(); i++)
		{
			if (userData.get(i).getAccount().equals(account)) return userData.get(i);
		}
		return null;
	}
}
