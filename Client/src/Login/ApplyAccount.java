package Login;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import SocketClient.Client;
import UserData.UserData;


public class ApplyAccount extends JFrame
{
	boolean isApply=false;
	
	private UserData			userData; 	
	private JPanel 				panel=new JPanel();
	
	private JLabel				account;
	private JTextField			accountField;
	private	JLabel				accountLabel;
	
	private JLabel				password;
	private JPasswordField		passwordField;
	private JLabel				passwordLabel;
	
	private JLabel				confirmPassword;
	private JPasswordField		confirmPasswordField;
	private JLabel				confirmPasswordLabel;
	
	private JLabel				email;
	private JTextField			emailField;
	private JLabel				emailLabel;
	
	
	private JButton				applyButton;
	
	public ApplyAccount()
	{
		setSize(350, 350);
		
		panel.setBackground(Color.white);
		panel.setLayout(null);
		
		///////////////////账号////////////////////////
		account=new JLabel("账号",JLabel.LEFT);
		account.setBounds(20, 30, 100, 20);
		panel.add(account);
		
		accountField=new JTextField();
		accountField.setBounds(20, 50, 100, 20);
		accountField.addActionListener(enterAccountActionListener);
		accountField.addFocusListener(accountFocusListener);
		panel.add(accountField);
		
		accountLabel=new JLabel("请输入账号",JLabel.LEFT);
		accountLabel.setBounds(150,50,200,20);
		panel.add(accountLabel);
		
		///////////////////密码///////////////////////
		password=new JLabel("密码",JLabel.LEFT);
		password.setBounds(20, 80, 100, 20);
		panel.add(password);
		
		passwordField=new JPasswordField();
		passwordField.setBounds(20, 100, 100, 20);
		passwordField.addActionListener(enterPasswordListener);
		passwordField.addFocusListener(passwordFocusListener);
		panel.add(passwordField);
		
		passwordLabel=new JLabel("请输入密码",JLabel.LEFT);
		passwordLabel.setBounds(150, 100, 200, 20);
		panel.add(passwordLabel);
		
		//////////////////密码确认/////////////////////
		confirmPassword=new JLabel("密码确认",JLabel.LEFT);
		confirmPassword.setBounds(20, 130, 100, 20);
		panel.add(confirmPassword);
		
		confirmPasswordField=new JPasswordField();
		confirmPasswordField.setBounds(20, 150, 100, 20);
		confirmPasswordField.addActionListener(enterConfirmPasswordListener);
		confirmPasswordField.addFocusListener(confirmPasswordFocusListener);
		panel.add(confirmPasswordField);
		
		confirmPasswordLabel=new JLabel("请确认密码");
		confirmPasswordLabel.setBounds(150, 150, 200, 20);
		panel.add(confirmPasswordLabel);
		
		/////////////////Email///////////////////////
		email=new JLabel("邮箱",JLabel.LEFT);
		email.setBounds(20, 180, 100, 20);
		panel.add(email);
		
		emailField=new JTextField();
		emailField.setBounds(20, 200, 100, 20);
		emailField.addActionListener(enterEmailListener);
		emailField.addFocusListener(emailFocusListener);
		panel.add(emailField);
		
		emailLabel=new JLabel("邮箱是找回密码的唯一途径");
		emailLabel.setBounds(150, 200, 200, 20);
		panel.add(emailLabel);
		
		////////////////登陆按钮//////////////////////
		applyButton=new JButton("开始申请");
		applyButton.setBounds(20, 250, 150, 50);
		applyButton.addActionListener(applyButtonListener);
		panel.add(applyButton);
		
		add(panel);
		
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	ActionListener enterAccountActionListener=new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if (accountField.getText().length()>15)
			{
				accountLabel.setText("账号长度不得超过15");
				return ;
			}
			if (accountField.getText().isEmpty())
			{
				accountLabel.setText("账号不得为空");
				return ;
			}
			if (accountField.getText().indexOf(' ')!=-1)
			{
				accountLabel.setText("账号不得出现空格");
			}
			accountLabel.setText("完成!");
			passwordField.requestFocus();
		}
	};
	
	
	ActionListener enterPasswordListener=new ActionListener() {
		public void actionPerformed(ActionEvent e)
		{
			if (passwordField.getText().length()>15)
			{
				passwordLabel.setText("密码不得超过15位");
				return ;
			}
			if (passwordField.getText().isEmpty())
			{
				passwordLabel.setText("密码不能为空");
				return ;
			}
			if (passwordField.getText().indexOf(' ')!=-1)
			{
				passwordLabel.setText("密码不能有空格");
				return ;
			}
			passwordLabel.setText("完成！");
			
			if (!confirmPasswordField.getText().equals(passwordField.getText()))
			{
				confirmPasswordLabel.setText("两次密码不一致!");
			}
			confirmPasswordField.requestFocus();
		}
	};
	
	ActionListener enterConfirmPasswordListener=new ActionListener() 
	{

		public void actionPerformed(ActionEvent e) 
		{
			if (!confirmPasswordField.getText().equals(passwordField.getText()))
			{
				confirmPasswordLabel.setText("两次密码不一致!");
				return ;
			}
			confirmPasswordLabel.setText("完成!");
			emailField.requestFocus();
		}
	};
	
	ActionListener enterEmailListener=new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (emailField.getText().isEmpty())
			{
				emailLabel.setText("邮箱不得为空");
				return ;
			}
			if (emailField.getText().indexOf(' ')!=-1)
			{
				emailLabel.setText("邮箱不得出现空格");
				return ;
			}
			if (emailField.getText().indexOf('@')!=-1)
			{
				emailLabel.setText("完成");
				return ;
			}
			emailLabel.setText("请输入正确的邮箱");
		}
	};
	
	
	FocusListener accountFocusListener=new FocusListener() {
		
		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			enterAccountActionListener.actionPerformed(null);
		}
		
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	FocusListener passwordFocusListener=new FocusListener() {
		
		@Override
		public void focusLost(FocusEvent e) {
			enterPasswordListener.actionPerformed(null);
		}
		
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	FocusListener confirmPasswordFocusListener=new FocusListener() {
		
		@Override
		public void focusLost(FocusEvent e) {
			enterConfirmPasswordListener.actionPerformed(null);
		}
		
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	FocusListener emailFocusListener=new FocusListener() {
		
		@Override
		public void focusLost(FocusEvent e) {
			enterEmailListener.actionPerformed(null);
			
		}
		
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	ActionListener applyButtonListener=new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			///检查账号
			if (accountField.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "账号不得为空", "申请错误", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			if (accountField.getText().indexOf(' ')!=-1)
			{
				JOptionPane.showMessageDialog(null, "账号不得出现空格", "申请错误", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			if (accountField.getText().length()>15)
			{
				JOptionPane.showMessageDialog(null, "账号长度不得超过15", "申请错误", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			
			////检查密码
			if (passwordField.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "密码不得为空", "申请错误", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			if (passwordField.getText().indexOf(' ')!=-1)
			{
				JOptionPane.showMessageDialog(null, "密码不得出现空格", "申请错误", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			if (passwordField.getText().length()>15)
			{
				JOptionPane.showMessageDialog(null, "密码长度不得超过15", "申请错误", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			//确认密码
			if (!confirmPasswordField.getText().equals(passwordField.getText()))
			{
				JOptionPane.showMessageDialog(null, "两次输入的密码不一致", "申请错误", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			
			//邮箱
			if (!emailLabel.getText().equals("完成"))
			{
				JOptionPane.showMessageDialog(null, "请输入正确的邮箱", "申请错误", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			
			apply();
		}
	};
	
	void apply()
	{
		if (isApply) return ;
		isApply=true;
		userData=new UserData(accountField.getText(),passwordField.getText(),emailField.getText());
		if (Client.sharedClient().applyAccount(userData)) this.dispose();
		isApply=false;
	}
}
