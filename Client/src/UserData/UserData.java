package UserData;

import java.io.Serializable;


public class UserData implements Serializable
{
	private String account;			//ук╨е
	private String password;
	private String email;
	
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
}
