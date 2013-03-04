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
		
		///////////////////�˺�////////////////////////
		account=new JLabel("�˺�",JLabel.LEFT);
		account.setBounds(20, 30, 100, 20);
		panel.add(account);
		
		accountField=new JTextField();
		accountField.setBounds(20, 50, 100, 20);
		accountField.addActionListener(enterAccountActionListener);
		accountField.addFocusListener(accountFocusListener);
		panel.add(accountField);
		
		accountLabel=new JLabel("�������˺�",JLabel.LEFT);
		accountLabel.setBounds(150,50,200,20);
		panel.add(accountLabel);
		
		///////////////////����///////////////////////
		password=new JLabel("����",JLabel.LEFT);
		password.setBounds(20, 80, 100, 20);
		panel.add(password);
		
		passwordField=new JPasswordField();
		passwordField.setBounds(20, 100, 100, 20);
		passwordField.addActionListener(enterPasswordListener);
		passwordField.addFocusListener(passwordFocusListener);
		panel.add(passwordField);
		
		passwordLabel=new JLabel("����������",JLabel.LEFT);
		passwordLabel.setBounds(150, 100, 200, 20);
		panel.add(passwordLabel);
		
		//////////////////����ȷ��/////////////////////
		confirmPassword=new JLabel("����ȷ��",JLabel.LEFT);
		confirmPassword.setBounds(20, 130, 100, 20);
		panel.add(confirmPassword);
		
		confirmPasswordField=new JPasswordField();
		confirmPasswordField.setBounds(20, 150, 100, 20);
		confirmPasswordField.addActionListener(enterConfirmPasswordListener);
		confirmPasswordField.addFocusListener(confirmPasswordFocusListener);
		panel.add(confirmPasswordField);
		
		confirmPasswordLabel=new JLabel("��ȷ������");
		confirmPasswordLabel.setBounds(150, 150, 200, 20);
		panel.add(confirmPasswordLabel);
		
		/////////////////Email///////////////////////
		email=new JLabel("����",JLabel.LEFT);
		email.setBounds(20, 180, 100, 20);
		panel.add(email);
		
		emailField=new JTextField();
		emailField.setBounds(20, 200, 100, 20);
		emailField.addActionListener(enterEmailListener);
		emailField.addFocusListener(emailFocusListener);
		panel.add(emailField);
		
		emailLabel=new JLabel("�������һ������Ψһ;��");
		emailLabel.setBounds(150, 200, 200, 20);
		panel.add(emailLabel);
		
		////////////////��½��ť//////////////////////
		applyButton=new JButton("��ʼ����");
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
				accountLabel.setText("�˺ų��Ȳ��ó���15");
				return ;
			}
			if (accountField.getText().isEmpty())
			{
				accountLabel.setText("�˺Ų���Ϊ��");
				return ;
			}
			if (accountField.getText().indexOf(' ')!=-1)
			{
				accountLabel.setText("�˺Ų��ó��ֿո�");
			}
			accountLabel.setText("���!");
			passwordField.requestFocus();
		}
	};
	
	
	ActionListener enterPasswordListener=new ActionListener() {
		public void actionPerformed(ActionEvent e)
		{
			if (passwordField.getText().length()>15)
			{
				passwordLabel.setText("���벻�ó���15λ");
				return ;
			}
			if (passwordField.getText().isEmpty())
			{
				passwordLabel.setText("���벻��Ϊ��");
				return ;
			}
			if (passwordField.getText().indexOf(' ')!=-1)
			{
				passwordLabel.setText("���벻���пո�");
				return ;
			}
			passwordLabel.setText("��ɣ�");
			
			if (!confirmPasswordField.getText().equals(passwordField.getText()))
			{
				confirmPasswordLabel.setText("�������벻һ��!");
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
				confirmPasswordLabel.setText("�������벻һ��!");
				return ;
			}
			confirmPasswordLabel.setText("���!");
			emailField.requestFocus();
		}
	};
	
	ActionListener enterEmailListener=new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (emailField.getText().isEmpty())
			{
				emailLabel.setText("���䲻��Ϊ��");
				return ;
			}
			if (emailField.getText().indexOf(' ')!=-1)
			{
				emailLabel.setText("���䲻�ó��ֿո�");
				return ;
			}
			if (emailField.getText().indexOf('@')!=-1)
			{
				emailLabel.setText("���");
				return ;
			}
			emailLabel.setText("��������ȷ������");
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
			///����˺�
			if (accountField.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "�˺Ų���Ϊ��", "�������", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			if (accountField.getText().indexOf(' ')!=-1)
			{
				JOptionPane.showMessageDialog(null, "�˺Ų��ó��ֿո�", "�������", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			if (accountField.getText().length()>15)
			{
				JOptionPane.showMessageDialog(null, "�˺ų��Ȳ��ó���15", "�������", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			
			////�������
			if (passwordField.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "���벻��Ϊ��", "�������", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			if (passwordField.getText().indexOf(' ')!=-1)
			{
				JOptionPane.showMessageDialog(null, "���벻�ó��ֿո�", "�������", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			if (passwordField.getText().length()>15)
			{
				JOptionPane.showMessageDialog(null, "���볤�Ȳ��ó���15", "�������", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			//ȷ������
			if (!confirmPasswordField.getText().equals(passwordField.getText()))
			{
				JOptionPane.showMessageDialog(null, "������������벻һ��", "�������", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			
			//����
			if (!emailLabel.getText().equals("���"))
			{
				JOptionPane.showMessageDialog(null, "��������ȷ������", "�������", JOptionPane.WARNING_MESSAGE);
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
