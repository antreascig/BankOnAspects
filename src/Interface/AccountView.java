package Interface;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import Controllers.AccountViewController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AccountView extends JFrame {
	
	private JLabel lblAccNum, lblAccBalance;
	
	private AccountViewController controller;
	
	/**
	 * Create the frame.
	 * @param balance 
	 * @param accNum 
	 */
	public AccountView(String accNum, AccountViewController avController) {		
		controller = avController;
		
		initialize();
		
		lblAccNum.setText(accNum);
		
		updateScreen();
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);
	} // AccountView

	private void initialize() {
		JLabel lblAccount = new JLabel("Account:");
		lblAccount.setFont(new Font("Dialog", Font.BOLD, 20));
		
		lblAccNum = new JLabel("...");
		lblAccNum.setFont(new Font("Dialog", Font.BOLD, 18));
		
		JLabel lblBalance = new JLabel("Balance:");
		lblBalance.setFont(new Font("Dialog", Font.BOLD, 20));
		
		lblAccBalance = new JLabel("£0");
		lblAccBalance.setFont(new Font("Dialog", Font.BOLD, 18));
		
		JButton depositButton = new JButton("Deposit");
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deposit();
			}
		});
		
		JButton withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				withdraw();
			}
		});
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeView();
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(depositButton, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addGap(50)
							.addComponent(withdrawButton)
							.addGap(74)
							.addComponent(closeButton))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAccount)
								.addComponent(lblBalance))
							.addGap(45)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAccBalance)
								.addComponent(lblAccNum))))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAccount)
						.addComponent(lblAccNum))
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBalance)
						.addComponent(lblAccBalance))
					.addPreferredGap(ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(closeButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(withdrawButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(depositButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
					.addGap(55))
		);
		getContentPane().setLayout(groupLayout);
	}

	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(null,
				message,
			    "Account Operation Error",
			    JOptionPane.WARNING_MESSAGE);
	} // showException
	
	protected void withdraw() {
		String amountString = (String) JOptionPane.showInputDialog(
				this,
                "Enter amount to withdraw:",
                "Withdraw",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                0);		
		
		while ( !controller.isInteger(amountString) ) {
			if (amountString == null)
				return;
			
			amountString = (String) JOptionPane.showInputDialog(
					this,
	                "Amount Must be An integer Number!"
	                + "\nEnter amount to withdraw:",
	                "Withdrawal",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                0);
		} // while	
		
		int amount = Integer.parseInt(amountString);
		
		
		controller.withdraw(amount);			
		updateScreen();
	} // withdraw

	protected void deposit() {
		String amountString = (String) JOptionPane.showInputDialog(
				this,
                "Enter amount to deposit:",
                "Deposit",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                0);	
		
		while ( !controller.isInteger(amountString) ) {
			if (amountString == null)
				return;
			
			amountString = (String) JOptionPane.showInputDialog(
					this,
	                "Amount Must be An integer Number!"
	                + "\nEnter amount to deposit:",
	                "Deposit",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                0);
		} // while	
		
		int amount = Integer.parseInt(amountString);
		controller.deposit(amount);
		updateScreen();
	} // deposit	

	private void updateScreen() {
		int accBalance = controller.getBalance();
		lblAccBalance.setText("£" + accBalance);
	}
	
	protected void closeView() {
		this.dispose();
	} // closeView
} // AccountView
