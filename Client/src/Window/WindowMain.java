package Window;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

import SocketClient.Client;
import UserData.SelfData;

class MessageEffict extends Applet
{
	private AudioClip audioMessage=null;
	private AudioClip audioSystem=null;
	public MessageEffict()
	{
		URL urlSystem=null;
		URL urlMessage=null;
		try 
		{
			urlSystem = new URL("file:"+new File("Resource/Music/system.wav").getAbsolutePath());
			urlMessage= new URL("file:"+new File("Resource/Music/message.wav").getAbsolutePath());
		} 
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		audioSystem=Applet.newAudioClip(urlSystem);
		audioMessage=Applet.newAudioClip(urlMessage);
	}
	public void playMessageMusic()
	{
		if (audioMessage!=null) audioMessage.play();
	}
	public void playSystemMusic()
	{
		if (audioSystem!=null) audioSystem.play();
	}
}

public class WindowMain extends JFrame 
{
	MessageEffict messageEffict=new MessageEffict();
	private JScrollPane scrollPane;
	private JPanel panel;
	private int panelHeight=0;
	private JButton addFriendButton;
	private Vector<FriendPanel> friendPanel=new Vector<FriendPanel>();
	static private WindowMain window;
	
	public static void windowStart()
	{
		window=new WindowMain();
	}
	
	public static WindowMain sharedWindow()
	{
		if (window==null) window=new WindowMain();
		return window;
	}
	public WindowMain()
	{
		setSize(200,600);
		setTitle(SelfData.getSelfData().getAccount());
		setLayout(null);
		
		
		panel=new JPanel();
		panel.setPreferredSize(new Dimension(200,panelHeight));
		panel.setBackground(Color.white);
		
		scrollPane=new JScrollPane(panel);
		scrollPane.setSize(195,530);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(scrollPane);
		
		for (int i=0; i<SelfData.getSelfData().getFriend().size(); i++)
		{
			panelHeight+=50;
			panel.setPreferredSize(new Dimension(200,panelHeight));
			friendPanel.add(new FriendPanel(SelfData.getSelfData().getFriend().get(i)) );
			panel.add(friendPanel.get(i));
		}
		
		addFriendButton=new JButton("添加好友");
		addFriendButton.setBounds(100, 530, 100, 40);
		addFriendButton.addActionListener(addFriendListener);
		add(addFriendButton);
		
		
		addWindowListener(closeListener);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	ActionListener addFriendListener=new ActionListener()
	{
		public void actionPerformed(ActionEvent e) 
		{
			String account;
			account=JOptionPane.showInputDialog(null, "请输入对方账号", "添加好友", JOptionPane.INFORMATION_MESSAGE);
			if (account!=null)
			{
				if (account.equals(SelfData.getSelfData().getAccount()))
				{
					JOptionPane.showMessageDialog(null, "无法添加自己为好友", "添加好友失败", JOptionPane.ERROR_MESSAGE, null);
					return ;
				}
				for (int i=0; i<SelfData.getSelfData().getFriend().size(); i++)
				{
					if (account.equals(SelfData.getSelfData().getFriend().get(i)))
					{
						JOptionPane.showMessageDialog(null, account+"已经是你的好友", "添加好友失败", JOptionPane.ERROR_MESSAGE, null);
						return ;
					}
				}
				Client.sharedClient().addFriend(account);
			}
		}
	};
	
	WindowListener closeListener=new WindowListener() 
	{
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			Client.sharedClient().exit();
		}
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	//public show
	public void addNewFriend(String account)
	{
		for (int i=0; i<SelfData.getSelfData().getFriend().size(); i++)
			if (account.equals(SelfData.getSelfData().getFriend().get(i))) return ;
		
		
		panelHeight+=50;
		panel.setPreferredSize(new Dimension(200,panelHeight));
		messageEffict.playSystemMusic();
		SelfData.getSelfData().getFriend().add(account);
		friendPanel.add(new FriendPanel(account));
		
		panel.add(friendPanel.lastElement());
		panel.updateUI();
	}
	
	public void removeFriend(String account)
	{
		for (int i=0; i<SelfData.getSelfData().getFriend().size(); i++)
		{
			if (account.equals(SelfData.getSelfData().getFriend().get(i)))
			{
				panelHeight-=50;
				panel.setPreferredSize(new Dimension(200,panelHeight));
				messageEffict.playSystemMusic();
				panel.remove(friendPanel.get(i));
				panel.updateUI();
				friendPanel.remove(i);
				SelfData.getSelfData().getFriend().remove(i);
				return ;
			}
		}
	}
	
	public void addMessage(String account, String message)
	{
		for (int i=0; i<friendPanel.size(); i++)
		{
			if (friendPanel.get(i).getAccount().equals(account))
			{
				messageEffict.playMessageMusic();
				if (!friendPanel.get(i).getTalkFrame().isVisible())
				{
					friendPanel.get(i).blink();
				}
				friendPanel.get(i).getTalkFrame().addMessage(account,message);
				return ;
			}
		}
	}
}
