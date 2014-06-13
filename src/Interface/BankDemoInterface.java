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

import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
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

@SuppressWarnings("serial")
public class BankDemoInterface extends JFrame {
	
	private JPanel mainPanel, accountsPanel;
	private CardLayout panels;
	private JMenuBar menuBar;
	private JTextField textField;
	
	private String currentPanel;
	
	private ButtonGroup accountRadioGroup;
			
	public BankDemoInterface() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			public void actionPerformed(ActionEvent e) 
			{
				viewServerInfo();
			}
		});
		mnView.add(mntmServerStatistics);
		
		JMenuItem mntmAccountStatistics = new JMenuItem("Exit");
		mntmAccountStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				closeApplication();
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
			public void actionPerformed(ActionEvent e) 
			{
				updateViewAccount();
			}
		});
		mnAccounts.add(mntmViewAccounts);
		
		JMenu mnNewMenu = new JMenu("Transfers");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmTransferBetweenAccounts = new JMenuItem("Transfer Between Accounts");
		mnNewMenu.add(mntmTransferBetweenAccounts);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(mainPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
		);
		mainPanel.setLayout(new CardLayout(0, 0));
		
		JPanel startPanel = new JPanel();
		startPanel.setBackground(new Color(173, 216, 230));
		mainPanel.add(startPanel, "startPanel");
		
		JButton btnNewButton = new JButton("Start Demo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				initializeDemo();
			}
		});
		
		JLabel lblNewLabel = new JLabel("Welcome to Bank-On-Aspects Demo");
		lblNewLabel.setFont(new Font("Liberation Serif", Font.BOLD | Font.ITALIC, 24));
		GroupLayout gl_startPanel = new GroupLayout(startPanel);
		gl_startPanel.setHorizontalGroup(
			gl_startPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_startPanel.createSequentialGroup()
					.addGap(196)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(119, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_startPanel.createSequentialGroup()
					.addContainerGap(102, Short.MAX_VALUE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)
					.addGap(99))
		);
		gl_startPanel.setVerticalGroup(
			gl_startPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_startPanel.createSequentialGroup()
					.addGap(42)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGap(63)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(143, Short.MAX_VALUE))
		);
		startPanel.setLayout(gl_startPanel);
		
		JPanel infoScrPane = new JPanel();
		mainPanel.add(infoScrPane, "infoScrPane");
		infoScrPane.setBackground(new Color(245, 245, 245));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel lblOnlineUsers = new JLabel("Online Users:");
		lblOnlineUsers.setFont(new Font("Liberation Serif", Font.BOLD | Font.ITALIC, 20));
		
		JLabel userCountLbl = new JLabel("....");
		lblOnlineUsers.setLabelFor(userCountLbl);
		userCountLbl.setFont(new Font("Liberation Serif", Font.BOLD | Font.ITALIC, 20));
		GroupLayout gl_infoScrPane = new GroupLayout(infoScrPane);
		gl_infoScrPane.setHorizontalGroup(
			gl_infoScrPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
				.addGroup(gl_infoScrPane.createSequentialGroup()
					.addGap(31)
					.addComponent(lblOnlineUsers)
					.addGap(92)
					.addComponent(userCountLbl, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(101, Short.MAX_VALUE))
		);
		gl_infoScrPane.setVerticalGroup(
			gl_infoScrPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_infoScrPane.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_infoScrPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOnlineUsers)
						.addComponent(userCountLbl, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE))
		);
		
		JTextArea serverLog = new JTextArea();
		scrollPane.setViewportView(serverLog);
		serverLog.setWrapStyleWord(true);
		serverLog.setForeground(new Color(0, 0, 0));
		serverLog.setBackground(new Color(255, 255, 255));
		serverLog.setLineWrap(true);
		serverLog.setEditable(false);
		infoScrPane.setLayout(gl_infoScrPane);
		
		JPanel viewAccountPanel = new JPanel();
		mainPanel.add(viewAccountPanel, "viewAccountPanel");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JButton newAccButton = new JButton("New Account");
		newAccButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				addAccount();
			}
		});
		
		JButton vewAccButton = new JButton("View Account");
		vewAccButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				viewAccount();
			}
		});
		
		JButton deleteAccButton = new JButton("Delete Account");
		deleteAccButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				deleteAccount();
			}
		});
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JComboBox<String> searchOptions = new JComboBox<>();
		searchOptions.setModel(new DefaultComboBoxModel<String>(new String[] {"Account Number", "Account Type"}));
		
		JLabel lblSeravhBy = new JLabel("Search By:");
		GroupLayout gl_viewAccountPanel = new GroupLayout(viewAccountPanel);
		gl_viewAccountPanel.setHorizontalGroup(
			gl_viewAccountPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
				.addGroup(gl_viewAccountPanel.createSequentialGroup()
					.addGap(12)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSeravhBy)
					.addGap(26)
					.addComponent(searchOptions, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(35, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_viewAccountPanel.createSequentialGroup()
					.addContainerGap(59, Short.MAX_VALUE)
					.addComponent(newAccButton)
					.addGap(56)
					.addComponent(vewAccButton)
					.addGap(31)
					.addComponent(deleteAccButton)
					.addGap(90))
		);
		gl_viewAccountPanel.setVerticalGroup(
			gl_viewAccountPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_viewAccountPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_viewAccountPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(deleteAccButton)
						.addComponent(vewAccButton)
						.addComponent(newAccButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_viewAccountPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
						.addComponent(lblSeravhBy)
						.addComponent(searchOptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
		);
		
		accountRadioGroup = new ButtonGroup();
		
		accountsPanel = new JPanel();
		accountsPanel.setBackground(new Color(255, 255, 255));
		scrollPane_1.setViewportView(accountsPanel);
		accountsPanel.setLayout(new GridLayout(0, 1, 0, 0));
				
		viewAccountPanel.setLayout(gl_viewAccountPanel);
		getContentPane().setLayout(groupLayout);
		
		this.setSize(601, 407);
	} // Initialise
	
	protected void initializeDemo() 
	{
		BankDemoController.initializeServer();
		viewServerInfo();
		menuBar.setVisible(true);
		
	} // initializeDemo
	
	private void updateScreen()
	{
		if (currentPanel.equals("viewAccountPanel"))
		{
			updateViewAccount();
		} // if
		
	} // updateScreen
	
	protected void updateViewAccount() 
	{
		ArrayList<String> accountList = BankDemoController.getAccountList();
		
		JRadioButton accountRadio;
		String radioTxt;
		
		accountsPanel.removeAll();
		
		for (String account : accountList)
		{
			radioTxt = account + "\t-\t" + BankDemoController.getAccountType(account);
//			System.out.println(radioTxt);
			accountRadio = new JRadioButton(radioTxt);
			accountRadio.setActionCommand(account);

			accountRadioGroup.add(accountRadio);
			accountsPanel.add(accountRadio);
			accountsPanel.validate();
		} // for
		
		panels = (CardLayout) mainPanel.getLayout();
	    
		panels.show(mainPanel, "viewAccountPanel" );		
		currentPanel = "viewAccountPanel";
		
	} // updateViewAccount

	protected void viewServerInfo()
	{
		panels = (CardLayout) mainPanel.getLayout();
	    
		panels.show(mainPanel, "infoScrPane" );
		currentPanel = "infoScrPane";	
	} // viewServerInfo
	
	protected void addAccount() 
	{
		String acc1 = "acc1";
		
		BankDemoController.addAccount(acc1, null);
			
		updateScreen();
	} // addAccount
	
	protected void viewAccount() 
	{
		String accNum = accountRadioGroup.getSelection().getActionCommand();
		BankDemoController.viewAccount(accNum);
	} // viewAccount
	
	protected void deleteAccount() 
	{
		String accNum = accountRadioGroup.getSelection().getActionCommand();
		BankDemoController.removeAccount(accNum);		
	} // deleteAccount
	
	protected void closeApplication() 
	{
		// TODO Auto-generated method stub
		this.dispose();
	} // closeApplication
}
