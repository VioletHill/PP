package UserData;

import java.util.Vector;

public class SelfData
{
	private static SelfData selfData;
	private String account;
	private Vector<String> friendAccount;
	
	public SelfData(String account)
	{
		this.account=account;
	}
	
	public static void initialize(String account)
	{
		selfData=new SelfData(account);
	}
	
	public static SelfData getSelfData()
	{
		return selfData;
	}
	
	public String getAccount()
	{
		return account;
	}
	
	public void setFriend(Vector<String> friend)
	{
		this.friendAccount=friend;
	}
	
	public Vector<String> getFriend()
	{
		return friendAccount;
	}
}
