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
	
	public void startAcceptMessage()		//����������Ϣ
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
			//������Ϣ
			out=new ObjectOutputStream(socket.getOutputStream());
			String str="-a"+userData.getAccount()+' '+userData.getPassword()+' '+userData.getEmail();
			out.writeObject(str);
			out.flush();
			//������Ϣ  �Ƿ�����ɹ�
			in=new ObjectInputStream(socket.getInputStream());
			String message=(String)in.readObject();
			if (message.equals("true"))
			{
				JOptionPane.showMessageDialog(null, "�˺�����ɹ�", "��ϲ��", JOptionPane.NO_OPTION);
				isApplySuccess=true;
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "�Բ��𣡸��˺��Ѵ���","��Ǹ",JOptionPane.ERROR_MESSAGE);
				isApplySuccess=false;
			}
			socket.close();
			return isApplySuccess;
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 		//�쳣  ���Ӳ���������
		{
			JOptionPane.showMessageDialog(null, "�޷����ӵ���������\n" +
					                            "����ԭ��\n" +
					                            "һ����������Ƿ�����;\n" +
					                            "����������ά����\n" +
					                            "���Ժ�����", "�������", JOptionPane.WARNING_MESSAGE);
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
			//������Ϣ
			String str="-l"+account+' '+password;
			out.writeObject(str);
			out.flush();
			//������Ϣ  �Ƿ�����ɹ�
			in=new ObjectInputStream(socket.getInputStream());
			String message=(String) in.readObject();
			if (message.equals("login"))
			{
				JOptionPane.showMessageDialog(null, "���˺��ѵ�½","��¼ʧ��",JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "��������������˻�������","��¼ʧ��",JOptionPane.ERROR_MESSAGE);
				canLog=false;
				socket.close();
			}
			return canLog;
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 		//�쳣  ���Ӳ���������
		{
			JOptionPane.showMessageDialog(null, "�޷����ӵ���������\n" +
					                            "����ԭ��\n" +
					                            "һ����������Ƿ�����;\n" +
					                            "����������ά����\n" +
					                            "���Ժ�����", "��½����", JOptionPane.WARNING_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "�ȴ��Է�ȷ�����", "��Ӻ�������", JOptionPane.INFORMATION_MESSAGE, null);
				break;
			case 1:
				JOptionPane.showMessageDialog(null, "�ú��Ѳ�����", "��Ӻ���ʧ��", JOptionPane.ERROR_MESSAGE, null);
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "�ú���δ��½���޷�ȷ����Ϣ���뾲�ĵȴ��Է�ȷ��", "��������ɹ�", JOptionPane.INFORMATION_MESSAGE, null);
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "�Է��ܾ������Ϊ����", "��Ӻ���ʧ��", JOptionPane.ERROR_MESSAGE, null);
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "��ϲ�㣬�ֶ���һλ����", "��Ӻ��ѳɹ�", JOptionPane.INFORMATION_MESSAGE, null);
				WindowMain.sharedWindow().addNewFriend(account);
				break;
			default:
				break;
			}
			break;
		case 'f':
			str=str.substring(2);
			account=str.substring(0);
			int ok=JOptionPane.showConfirmDialog(null, account+"���������Ϊ����", "����ϵͳ����Ϣ",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
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
			JOptionPane.showMessageDialog(null, account+"����Ӻ������Ƴ�", "����ϵͳ����Ϣ", JOptionPane.INFORMATION_MESSAGE, null);
			WindowMain.sharedWindow().removeFriend(account);
			break ;
		default:
			break ;
		}
		
	}
	public void run()			//��½��ɺ� ��ʼ�������Է���������Ϣ
	{
		// f    ����������Ӻ���
		// g	 ��Ӻ��ѻظ�
		// t	 ���ѷ�����Ϣ
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
