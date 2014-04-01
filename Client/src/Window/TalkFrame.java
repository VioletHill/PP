package Window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import SocketClient.Client;
import UserData.SelfData;

public class TalkFrame extends JFrame
{
	String talkFriend;

	
	JTextArea speak;
	JTextArea speakRecord;
	
	JPanel	panel;
	JPanel 	centerPanel;
	JButton closeButton;
	JButton sendButton;
	
	public TalkFrame(String talkFriend)
	{

		this.talkFriend=talkFriend;
		setTitle("和"+talkFriend+"聊天中");
		setSize(500,550);
		panel=new JPanel();
		panel.setBounds(0, 0, 500, 550);
		panel.setLayout(null);
		
		
		speakRecord=new JTextArea();
		speakRecord.setLineWrap(true);
		speakRecord.setEditable(false);
		JScrollPane speakRecordScroll = new JScrollPane(speakRecord);
		speakRecordScroll.setBounds(0, 0, 490, 300);
		panel.add(speakRecordScroll);
		
		//centerPanel=new JPanel();
		//panel.add(centerPanel);
		
		speak=new JTextArea();
		speak.setLineWrap(true);
		speak.setForeground(Color.red);
		JScrollPane speakScroll = new JScrollPane(speak);
		speakScroll.setBounds(0, 320, 490, 150);
		panel.add(speakScroll);
		
		sendButton=new JButton("发送");
		sendButton.setBounds(320, 480, 60, 30);
		sendButton.setMnemonic(KeyEvent.VK_ENTER);
		sendButton.addActionListener(sendActionListener);
		panel.add(sendButton);
		
		closeButton=new JButton("关闭");
		closeButton.setBounds(400, 480, 60, 30);
		closeButton.setMnemonic(KeyEvent.VK_ESCAPE);
		closeButton.addActionListener(closeActionListener);
		panel.add(closeButton);

		add(panel);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	private void closeTalkWindow()
	{
		setVisible(false);
	}
	
	private void sendMessage()
	{
		if (speak.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "发送消息不能为空", "发送错误", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		String ly_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		speakRecord.append("\n"+SelfData.getSelfData().getAccount()+"\t"+ly_time);
		speakRecord.append("\n"+speak.getText());
		
		Client.sharedClient().sendMessage(talkFriend,ly_time+'\n'+speak.getText());
		speak.setText("");
		speak.requestFocus();
	}
	
	public void addMessage(String account,String message)
	{
		speakRecord.append("\n"+account+'\t'+message);
	}
	
	ActionListener sendActionListener=new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			sendMessage();
			
		}
	};
	
	ActionListener closeActionListener=new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			closeTalkWindow();	
		}
	};
}
