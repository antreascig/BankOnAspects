package Interface;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.BevelBorder;

import Controllers.BankDemoController;
import Global.Pair;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.CardLayout;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class BankDemoInterface extends JFrame implements Observer {
	
	private JPanel mainPanel, accountsPanel, transferPanel;
	private CardLayout panels;
	private JMenuBar menuBar;
	private JTextArea serverLog;
	private JTextField textField;
	private ButtonGroup accountRadioGroup;
	private JButton viewAccButton, deleteAccButton;
	private JScrollPane accountScrollPane;
	private JLabel userCountLbl;
	
	private BankDemoController controller;
	@SuppressWarnings("rawtypes")
	private JComboBox toList, fromList;
	private JLabel fromBalanceLbl;
	private JLabel fromAccTypeLbl;
	private JLabel toAccTypeLbl;
	private JLabel toBalanceLbl;
	private JTextField amountTxt;
				
	public BankDemoInterface(BankDemoController bankDemoController) 
	{
		setResizable(false);
		controller = bankDemoController;
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		initialize();		
		setLocationRelativeTo(null);	
		
	} // BankDemoInterface

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		
		menuBar = new JMenuBar();
		menuBar.setVisible(false);
		menuBar.setEnabled(true);
		setJMenuBar(menuBar);
		
		JMenu mnView = new JMenu("Application");
		menuBar.add(mnView);
		
		JMenuItem mntmServerStatistics = new JMenuItem("Server Information");
		mntmServerStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateScreen("infoScrPane");
			}
		});
		mnView.add(mntmServerStatistics);
		
		JMenuItem mntmAccountStatistics = new JMenuItem("Exit");
		mntmAccountStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endDemo();
			}
		});
		mnView.add(mntmAccountStatistics);
		
		JMenu mnAccounts = new JMenu("Accounts");
		menuBar.add(mnAccounts);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New Bank Account");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				addAccount();
			}
		});
		mnAccounts.add(mntmNewMenuItem);
		
		JMenuItem mntmViewAccounts = new JMenuItem("View Accounts");
		mntmViewAccounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateScreen("viewAccountPanel");
			}
		});
		mnAccounts.add(mntmViewAccounts);
		
		JMenu mnNewMenu = new JMenu("Transfers");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmTransferBetweenAccounts = new JMenuItem("Transfer Between Accounts");
		mntmTransferBetweenAccounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateScreen("transferPanel");
			}
		});
		mnNewMenu.add(mntmTransferBetweenAccounts);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(173, 216, 230));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		mainPanel.setLayout(new CardLayout(0, 0));
		
		JPanel startPanel = new JPanel();
		startPanel.setBackground(new Color(173, 216, 230));
		mainPanel.add(startPanel, "startPanel");
		
		JButton btnNewButton = new JButton("Enable Server's GUI");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializeDemo();
			}
		});
		
		JLabel lblNewLabel = new JLabel("Welcome to Bank-On-Aspects Demo");
		lblNewLabel.setFont(new Font("Liberation Serif", Font.BOLD | Font.ITALIC, 24));
		
		JButton exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endDemo();
			}
		});
		GroupLayout gl_startPanel = new GroupLayout(startPanel);
		gl_startPanel.setHorizontalGroup(
			gl_startPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_startPanel.createSequentialGroup()
					.addContainerGap(102, Short.MAX_VALUE)
					.addGroup(gl_startPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_startPanel.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)
							.addGap(99))
						.addGroup(gl_startPanel.createSequentialGroup()
							.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
				.addGroup(gl_startPanel.createSequentialGroup()
					.addGap(191)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(239, Short.MAX_VALUE))
		);
		gl_startPanel.setVerticalGroup(
			gl_startPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_startPanel.createSequentialGroup()
					.addGap(42)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(60)
					.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addGap(28))
		);
		startPanel.setLayout(gl_startPanel);
		
		JPanel infoScrPane = new JPanel();
		mainPanel.add(infoScrPane, "infoScrPane");
		infoScrPane.setBackground(new Color(245, 245, 245));
		
		JScrollPane serverScrollPane = new JScrollPane();
		serverScrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel lblOnlineUsers = new JLabel("Online Users:");
		lblOnlineUsers.setFont(new Font("Liberation Serif", Font.BOLD | Font.ITALIC, 20));
		
		userCountLbl = new JLabel("....");
		lblOnlineUsers.setLabelFor(userCountLbl);
		userCountLbl.setFont(new Font("Liberation Serif", Font.BOLD | Font.ITALIC, 20));
		GroupLayout gl_infoScrPane = new GroupLayout(infoScrPane);
		gl_infoScrPane.setHorizontalGroup(
			gl_infoScrPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_infoScrPane.createSequentialGroup()
					.addGap(31)
					.addComponent(lblOnlineUsers)
					.addGap(92)
					.addComponent(userCountLbl, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(106, Short.MAX_VALUE))
				.addComponent(serverScrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
		);
		gl_infoScrPane.setVerticalGroup(
			gl_infoScrPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_infoScrPane.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_infoScrPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOnlineUsers)
						.addComponent(userCountLbl, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
					.addComponent(serverScrollPane, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE))
		);
		
		serverLog = new JTextArea();
		serverScrollPane.setViewportView(serverLog);
		serverLog.setWrapStyleWord(true);
		serverLog.setForeground(new Color(0, 0, 0));
		serverLog.setBackground(new Color(255, 255, 255));
		serverLog.setLineWrap(true);
		serverLog.setEditable(false);
		infoScrPane.setLayout(gl_infoScrPane);
		
		JPanel viewAccountPanel = new JPanel();
		mainPanel.add(viewAccountPanel, "viewAccountPanel");
			
		accountsPanel = new JPanel();
		accountsPanel.setBackground(new Color(255, 255, 255));
		
		
		accountScrollPane = new JScrollPane(accountsPanel);
		accountsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton newAccButton = new JButton("New Account");
		newAccButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAccount();
			}
		});
		
		viewAccButton = new JButton("View Account");
		viewAccButton.setEnabled(false);
		viewAccButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewAccount();
			}
		});
		
		deleteAccButton = new JButton("Delete Account");
		deleteAccButton.setEnabled(false);
		deleteAccButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteAccount();
			}
		});
		
		textField = new JTextField();
		textField.setVisible(false);
		textField.setColumns(10);
		
		JComboBox<String> searchOptions = new JComboBox<>();
		searchOptions.setVisible(false);
		searchOptions.setModel(new DefaultComboBoxModel<String>(new String[] {"Account Number", "Account Type"}));
		
		JLabel lblSeravhBy = new JLabel("Search By:");
		lblSeravhBy.setVisible(false);
		GroupLayout gl_viewAccountPanel = new GroupLayout(viewAccountPanel);
		gl_viewAccountPanel.setHorizontalGroup(
			gl_viewAccountPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_viewAccountPanel.createSequentialGroup()
					.addGap(12)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSeravhBy)
					.addGap(26)
					.addComponent(searchOptions, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(73, Short.MAX_VALUE))
				.addGroup(gl_viewAccountPanel.createSequentialGroup()
					.addContainerGap(143, Short.MAX_VALUE)
					.addComponent(newAccButton)
					.addGap(56)
					.addComponent(viewAccButton)
					.addGap(31)
					.addComponent(deleteAccButton)
					.addGap(90))
				.addComponent(accountScrollPane, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
		);
		gl_viewAccountPanel.setVerticalGroup(
			gl_viewAccountPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_viewAccountPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_viewAccountPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(deleteAccButton)
						.addComponent(viewAccButton)
						.addComponent(newAccButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_viewAccountPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
						.addComponent(lblSeravhBy)
						.addComponent(searchOptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(accountScrollPane, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE))
		);
				
		
		viewAccountPanel.setLayout(gl_viewAccountPanel);
		
		transferPanel = new JPanel();
		transferPanel.setBackground(Color.WHITE);
		mainPanel.add(transferPanel, "transferPanel");	
				
		fromList = new JComboBox<>();
		fromList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setFromAccount();
			}
		});
		fromList.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblFromAccount = new JLabel("From Account:");
		lblFromAccount.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFromAccount.setLabelFor(fromList);
		
		JLabel lblAccountType = new JLabel("Account Type:");
		lblAccountType.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		fromAccTypeLbl = new JLabel("New label");
		fromAccTypeLbl.setBackground(Color.LIGHT_GRAY);
		fromAccTypeLbl.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAccountType.setLabelFor(fromAccTypeLbl);
		
		JLabel lblNewLabel_1 = new JLabel("Balance:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		fromBalanceLbl = new JLabel("New label");
		fromBalanceLbl.setBackground(Color.LIGHT_GRAY);
		fromBalanceLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBackground(Color.BLUE);
		separator_1.setForeground(Color.BLUE);
		
		JButton transferButton = new JButton("Transfer");
		transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				transfer();
			}
		});
		
		JLabel lblToAccount = new JLabel("To Account:");
		lblToAccount.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		toList = new JComboBox<>();
		toList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setToAccount();
			}
		});
		lblToAccount.setLabelFor(toList);
		toList.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel label = new JLabel("Account Type:");
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		toAccTypeLbl = new JLabel("New label");
		label.setLabelFor(toAccTypeLbl);
		toAccTypeLbl.setFont(new Font("Tahoma", Font.BOLD, 12));
		toAccTypeLbl.setBackground(Color.LIGHT_GRAY);
		
		JLabel label_1 = new JLabel("Balance:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		toBalanceLbl = new JLabel("New label");
		label_1.setLabelFor(toBalanceLbl);
		toBalanceLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		toBalanceLbl.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblAmountToTransfer = new JLabel("Amount to Transfer:");
		lblAmountToTransfer.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		amountTxt = new JTextField();
		amountTxt.setText("0");
		lblAmountToTransfer.setLabelFor(amountTxt);
		amountTxt.setColumns(10);
		GroupLayout gl_transferPanel = new GroupLayout(transferPanel);
		gl_transferPanel.setHorizontalGroup(
			gl_transferPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_transferPanel.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_transferPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_transferPanel.createSequentialGroup()
							.addGroup(gl_transferPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblFromAccount)
								.addComponent(lblAccountType))
							.addGroup(gl_transferPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_transferPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(fromList, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_transferPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_transferPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(fromBalanceLbl)
										.addComponent(fromAccTypeLbl, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)))))
						.addGroup(gl_transferPanel.createSequentialGroup()
							.addGap(28)
							.addComponent(lblNewLabel_1)))
					.addGap(18)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_transferPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_transferPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_transferPanel.createSequentialGroup()
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED))
							.addGroup(gl_transferPanel.createSequentialGroup()
								.addComponent(lblToAccount, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
								.addGap(29)))
						.addGroup(gl_transferPanel.createSequentialGroup()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addGap(56)))
					.addGroup(gl_transferPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(toBalanceLbl, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addComponent(toAccTypeLbl, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
						.addComponent(toList, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
					.addGap(45))
				.addGroup(Alignment.LEADING, gl_transferPanel.createSequentialGroup()
					.addGap(36)
					.addComponent(lblAmountToTransfer)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(amountTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
					.addComponent(transferButton, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addGap(81))
		);
		gl_transferPanel.setVerticalGroup(
			gl_transferPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_transferPanel.createSequentialGroup()
					.addGroup(gl_transferPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_transferPanel.createSequentialGroup()
							.addGap(70)
							.addGroup(gl_transferPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFromAccount)
								.addComponent(fromList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(31)
							.addGroup(gl_transferPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAccountType)
								.addComponent(fromAccTypeLbl))
							.addGap(28)
							.addGroup(gl_transferPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1)
								.addComponent(fromBalanceLbl)))
						.addGroup(gl_transferPanel.createSequentialGroup()
							.addGap(31)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_transferPanel.createSequentialGroup()
							.addGap(69)
							.addGroup(gl_transferPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblToAccount)
								.addComponent(toList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(32)
							.addGroup(gl_transferPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(toAccTypeLbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
							.addGap(23)
							.addGroup(gl_transferPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(toBalanceLbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))))
					.addGap(14)
					.addGroup(gl_transferPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(transferButton, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAmountToTransfer)
						.addComponent(amountTxt, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(74))
		);
		transferPanel.setLayout(gl_transferPanel);
		getContentPane().setLayout(groupLayout);
		
		this.setSize(623, 407);
	} // Initialise

	@Override
	public void update(Observable o, Object arg) {	
		Pair<?> update = (Pair<?>)arg;
		
		String key = update.getKey();
		String log;
		Integer clientNumber;
		if (key.equals("STATUS")) {
			boolean serverRunning = (Boolean) update.getValue();			
			log = "Server ";
			if (serverRunning)
				log += "is running.";
			else
				log += "has stopped.";
			clientNumber = controller.getClientNumber();
		} // if
		else if (key.equals("USER_ADDED")) {
			clientNumber = (Integer) update.getValue();
			log = "New connection with client# " + clientNumber + "." ;			
		} // else if
		else if (key.equals("USER_REMOVED")) {
			clientNumber = (Integer) update.getValue(); 
			log = "Client# " + clientNumber + "disconnected." ;
		} // else if
		else
			throw new RuntimeException("Unforseen key update");
		
		updateServerLog(log);
		
		userCountLbl.setText(clientNumber + "");
	} //update

	protected void initializeDemo() {
		menuBar.setVisible(true);
		updateScreen("infoScrPane");
	} // initializeDemo
	
	@SuppressWarnings("unchecked")
	private void updateScreen(String panel) {	
		// Change screen to the - panel - provided as argument
		panels = (CardLayout) mainPanel.getLayout();  
		panels.show(mainPanel, panel );
		
		if (panel.equals("viewAccountPanel")) {
			accountsPanel.removeAll();
			
			ArrayList<String> accountList = controller.getAccountList();
			accountRadioGroup = new ButtonGroup();	
			JRadioButton accountRadio;
			String radioTxt;	
						
			for (String account : accountList) {
				radioTxt = account + "\t-\t" + controller.getAccountType(account);
				accountRadio = new JRadioButton(radioTxt);
				accountRadio.setActionCommand(account);
				accountRadio.addActionListener(new ActionListener() {			
					@Override
					public void actionPerformed(ActionEvent e) {
						updateButtons();
					}
				});	
				accountRadioGroup.add(accountRadio);
				accountsPanel.add(accountRadio);
				
				
			} // for
			
			accountsPanel.repaint();
			accountsPanel.validate();
			accountScrollPane.repaint();
			accountScrollPane.revalidate();
			accountRadioGroup.clearSelection();
			updateButtons();
		} // if
		else if ( panel.equals("transferPanel") ) {			
	
			DefaultComboBoxModel<String> fromModel =  controller.getAccountComboList();
			DefaultComboBoxModel<String> toModel = controller.getAccountComboList();
			fromList.setModel( fromModel );
			toList.setModel(toModel);
			setFromAccount();
			setToAccount();
		} // else if
	} // updateScreen
	
	protected void updateButtons() {
		ButtonModel selection = accountRadioGroup.getSelection();
		if (selection != null) {
			viewAccButton.setEnabled(true);
			deleteAccButton.setEnabled(true);
		} // if
		else {
			viewAccButton.setEnabled(false);
			deleteAccButton.setEnabled(false);
		} // else				
	} // updateButtons

	public void updateServerLog(String log) {
		serverLog.append(log);
		controller.logServerActivity(log);
	} // updateServerLog
	
	protected void setFromAccount() {
		String fromAccNum = fromList.getSelectedItem().toString();
		Integer fromBalance = controller.getBalance(fromAccNum);
		String fromAccType = controller.getAccountType(fromAccNum);
		
		fromAccTypeLbl.setText(fromAccType);
		fromBalanceLbl.setText("£" + fromBalance);	
	} // setFromAccount
	
	protected void setToAccount() {
		String toAccNum = toList.getSelectedItem().toString();
		Integer balance = controller.getBalance(toAccNum);
		String toAccType = controller.getAccountType(toAccNum);
		
		toAccTypeLbl.setText(toAccType);
		toBalanceLbl.setText("£" + balance);
	} // setToAccount
	
	protected void transfer() 
	{
		String fromAccNumber = fromList.getSelectedItem().toString();
		String toAccNumber = toList.getSelectedItem().toString();
		if (fromAccNumber.equals(toAccNumber)) {
			JOptionPane.showMessageDialog(this, "Cannot transfer between the same account. Choose different accounts");
			return;
		} // if
		try {			
			int amount = Integer.parseInt(amountTxt.getText());		
			
			if (amount <= 0) {
				JOptionPane.showMessageDialog(this, "Amount must be a greater than 0");
				return;
			} // if
			
			Boolean completed = controller.transfer(fromAccNumber, toAccNumber, amount);
			
			if (completed)
				JOptionPane.showMessageDialog(this, "Transfer Completed.");
			else
				JOptionPane.showMessageDialog(this, "Transfer Did Not Complete.");
					
		} catch ( NumberFormatException exc) { 
			JOptionPane.showMessageDialog(this, "Amount must be an Integer Number");			
		} //catch
		finally {
			amountTxt.setText("0");
			updateScreen("transferPanel");			
		} //finally
	} // deposit
	
		
	protected void addAccount()
	{
		Object[] options = {"Basic Account",
                			"Business Account"};
		int option = JOptionPane.showOptionDialog(this, "Select Bank Account Type: ", "Bank Account Selection", 
												 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
		if ( option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION )
			return;
		Pair<Integer> newAccountInfo = controller.addAccount(option);
		String message = "New Account created.\nAccount Number: " + newAccountInfo.getKey() + "\nPassword: " + newAccountInfo.getValue();
		JOptionPane.showMessageDialog(this, message);
			
		updateScreen("viewAccountPanel");
	} // addAccount
	
	protected void viewAccount() {
		String accNum = accountRadioGroup.getSelection().getActionCommand();
		controller.viewAccount(accNum);
	} // viewAccount
	
	protected void deleteAccount() {
		String accNum = accountRadioGroup.getSelection().getActionCommand();
		controller.removeAccount(accNum);	
		
		updateScreen("viewAccountPanel");	
	} // deleteAccount
	
	protected void endDemo() {
		controller.stopApplication();
		this.dispose();
	} // closeApplication
} // BankDemoInterface
