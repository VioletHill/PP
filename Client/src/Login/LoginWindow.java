package Login;
//////////////////////µÇÂ½´°¿Ú/////////////////////////////////////
///////////////////////////////////////////////////////////////


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import SocketClient.Client;
import Window.WindowMain;


public class LoginWindow extends JFrame 
{
	private JLabel 			backgroundImageLabel;
	private ImageIcon 		backgroundIcon;
	private JPasswordField 	passwordField;
	private JTextField		accountField;
	private JButton 		applyAccountButton;
	private JButton 		forgetPassworkButton;
	private JButton			logButton;
	private JPanel			mainPanel;
	private Font			normalFont;
	private Font			enterFont;
	private Font			loginFont;
	private Font			enterLoginFont;
	private boolean			isLogin;
	
	public LoginWindow()
	{
		setTitle("Design by VioletHill");
		setSize(350,250);
		setResizable(false);
		setLocationRelativeTo(null);
		
		isLogin=false;
		
		backgroundIcon=new ImageIcon("Resource/LoginWindow/background.jpg");
		backgroundImageLabel = new JLabel(backgroundIcon);
        backgroundImageLabel.setBounds(0,0,backgroundIcon.getIconWidth(),backgroundIcon.getIconHeight());
        getLayeredPane().add(backgroundImageLabel,new Integer(Integer.MIN_VALUE));
        JPanel jp=(JPanel)this.getContentPane();
        jp.setOpaque(false);
		
		mainPanel=new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setSize(350,250);
		mainPanel.setOpaque(false);
		
		accountField=new JTextField();
		accountField.setBounds(50, 50, 120, 20);
		mainPanel.add(accountField);
		
		applyAccountButton=new JButton("ÕËºÅÉêÇë");
		applyAccountButton.setContentAreaFilled(false);
		applyAccountButton.setBorderPainted(false);
		applyAccountButton.setFocusPainted(false);
		applyAccountButton.setBounds(200, 50, 120, 20);
		applyAccountButton.addMouseListener(mouseEnterApplyAccountMouseListener);
		applyAccountButton.addActionListener(applyAccountActionListener);
		mainPanel.add(applyAccountButton);
		
		normalFont=applyAccountButton.getFont();
		enterFont=new Font(normalFont.getFontName(),normalFont.getStyle(),normalFont.getSize()+3);
		
		passwordField=new JPasswordField();
		passwordField.setBounds(50 ,100 , 120, 20);
		mainPanel.add(passwordField);
		
		forgetPassworkButton=new JButton("Íü¼ÇÃÜÂë");
		forgetPassworkButton.setContentAreaFilled(false);
		forgetPassworkButton.setBorderPainted(false);
		forgetPassworkButton.setFocusable(false);
		forgetPassworkButton.setBounds(200, 100, 120, 20);
		forgetPassworkButton.addMouseListener(mouseEnterForgetPasswordMouseListener);
		mainPanel.add(forgetPassworkButton);
		
		
		logButton=new JButton("µÇ   Â½");
		logButton.setContentAreaFilled(false);
		logButton.setBorderPainted(false);
		logButton.setFocusable(false);
		logButton.setBounds(40, 130, 130, 50);
		
		loginFont=new Font(normalFont.getFontName(),normalFont.getStyle(),normalFont.getSize()+15);
		logButton.setFont(loginFont);
		enterLoginFont=new Font(loginFont.getFontName(), loginFont.getStyle(), loginFont.getSize()+5);	
		
		logButton.addMouseListener(enterLoginMouseListener);
		logButton.addActionListener(loginActionListener);
		mainPanel.add(logButton);
		
		add(mainPanel);
		
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void login()
	{
		String account=accountField.getText();
		String password=passwordField.getText();
		if (account.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "ÕËºÅ²»µÃÎª¿Õ", "µÇÂ½´íÎó", JOptionPane.WARNING_MESSAGE);
			return ;
		}
		if (password.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "ÃÜÂë²»µÃÎª¿Õ", "ÉêÇë´íÎó", JOptionPane.WARNING_MESSAGE);
			return ;
		}
		if (account.length()>15)	account=account.substring(0,15);
		if (password.length()>15)	password=password.substring(0,15);
		if (Client.sharedClient().login(account,password))
		{
			this.dispose();
		}
		else 
		{
			//µÇÂ½Ê§°Ü
		}
	}
	
	ActionListener applyAccountActionListener=new ActionListener() 
	{
		public void actionPerformed(ActionEvent e)
		{
			new ApplyAccount();
		}
	};
	
	ActionListener loginActionListener =new ActionListener() 
	{

		public void actionPerformed(ActionEvent e) 
		{
			if (isLogin) return ;
			isLogin=true;
			login();
			isLogin=false;
		}
	};
	
	MouseListener mouseEnterApplyAccountMouseListener=new MouseListener() 
	{
		
		@Override
		public void mouseReleased(MouseEvent e) 
		{	
		}
		
		@Override
		public void mousePressed(MouseEvent e) 
		{
		}
		
		@Override
		public void mouseExited(MouseEvent e)
		{
			applyAccountButton.setFont(normalFont);
		}
		
		@Override
		public void mouseEntered(MouseEvent e) 
		{
			applyAccountButton.setFont(enterFont);
		}
		
		public void mouseClicked(MouseEvent e) 
		{	
			applyAccountButton.setFont(normalFont);
		}
	};
	
	MouseListener mouseEnterForgetPasswordMouseListener =new MouseListener() 
	{
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
			forgetPassworkButton.setFont(normalFont);
		}
		
		@Override
		public void mouseEntered(MouseEvent e) 
		{
			forgetPassworkButton.setFont(enterFont);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			forgetPassworkButton.setFont(normalFont);	
		}
	};
	
	MouseListener enterLoginMouseListener=new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			logButton.setFont(loginFont);
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			logButton.setFont(enterLoginFont);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			logButton.setFont(loginFont);
			
		}
	};
}
