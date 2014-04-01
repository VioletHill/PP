package Window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import SocketClient.Client;

import com.sun.org.apache.bcel.internal.generic.NEW;

class Avatar implements Runnable
{
	private boolean isBlink=false;
	private JLabel avatar;
	FriendPanel friendPanel;
	public Avatar(FriendPanel friendPanel)
	{
		this.friendPanel=friendPanel;
		avatar=new JLabel(new ImageIcon("Resource/MainFrame/friend.PNG"));
	}
	
	public JLabel getImage()
	{
		return avatar;
	}
	
	private void setAvator(boolean state)
	{
		if (!isBlink) return ;
		avatar.setVisible(state);
		friendPanel.updateUI();
		try
		{
			Thread.sleep(200);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		setAvator(!state);
	}
	
	public void stopBlink()
	{
		if (!isBlink) return ;
		isBlink=false;
		avatar.setVisible(true);
		friendPanel.updateUI();
	}
	
	public void blink()
	{
		isBlink=true;
		new Thread(this).start();
	}
	
	
	public void run()
	{
		setAvator(false);
	}
}

public class FriendPanel extends JPanel
{
	private String account;
	private TalkFrame talkFrame;
	private Avatar avatar=new Avatar(this);
	
	public FriendPanel(String account)
	{
		this.account=account;
		talkFrame=new TalkFrame(account);
		setBackground(Color.white);
		setPreferredSize(new Dimension(200,50));
		setLayout(new GridLayout(1,3));
		
		
		add(avatar.getImage());
		add(new JLabel(account));

		addMouseListener(panelMouseListener);
	}
	
	public void blink()
	{
		avatar.blink();
	}
	
	public void stopBlink()
	{
		avatar.stopBlink();
	}
	
	public String getAccount()
	{
		return account;
	}
	
	public TalkFrame getTalkFrame()
	{
		return talkFrame;
	}
	
	public void deleteFriend()
	{
		Client.sharedClient().deleteFriend(account);
	}
	
	ActionListener deleteFriendActionListener=new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			deleteFriend();
		}
	};
	
	MouseListener panelMouseListener=new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) 
		{
			setBackground(Color.white);
		}
		
		@Override
		public void mouseEntered(MouseEvent e) 
		{
			setBackground(Color.yellow);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount()==2)
			{
				talkFrame.setVisible(true);
				stopBlink();
			}
			else if (e.getClickCount()==1 && e.getButton()==MouseEvent.BUTTON3)
			{
				JPopupMenu menu=new JPopupMenu();
				JMenuItem deleteFriend=new JMenuItem("É¾³ýºÃÓÑ");
				deleteFriend.addActionListener(deleteFriendActionListener);
				menu.add(deleteFriend);
				menu.show(e.getComponent(),e.getX(),e.getY());
			}
			
		}
	};
	
}
