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

@SuppressWarnings("serial")
public class BankDemoInterface extends JFrame implements Observer {
	
	private JPanel mainPanel, accountsPanel;
	private CardLayout panels;
	private JMenuBar menuBar;
	private JTextArea serverLog;
	private JTextField textField;
	private ButtonGroup accountRadioGroup;
	private JButton viewAccButton, deleteAccButton;
	private JScrollPane accountScrollPane;
	private JLabel userCountLbl;
	
	private BankDemoController controller;
				
	public BankDemoInterface(BankDemoController bankDemoController) 
	{
		controller = bankDemoController;
		
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
				updateScreen("infoScrPane");
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
				updateScreen("viewAccountPanel");
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
			public void actionPerformed(ActionEvent e) 
			{
				addAccount();
			}
		});
		
		viewAccButton = new JButton("View Account");
		viewAccButton.setEnabled(false);
		viewAccButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				viewAccount();
			}
		});
		
		deleteAccButton = new JButton("Delete Account");
		deleteAccButton.setEnabled(false);
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
				.addComponent(accountScrollPane, GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
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
					.addComponent(viewAccButton)
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
						.addComponent(viewAccButton)
						.addComponent(newAccButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_viewAccountPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
						.addComponent(lblSeravhBy)
						.addComponent(searchOptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(accountScrollPane, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
		);
				
		
		viewAccountPanel.setLayout(gl_viewAccountPanel);
		getContentPane().setLayout(groupLayout);
		
		this.setSize(601, 407);
	} // Initialise
	
	
	@Override
	public void update(Observable o, Object arg) {	
		Pair<?> update = (Pair<?>)arg;
		
		String key = update.getKey();
		String log;
		
		if (key.equals("STATUS"))
		{
			boolean serverRunning = (Boolean) update.getValue();
			
			log = "Server ";
			if (serverRunning)
				log += "is running.";
			else
				log += "has stopped.";
					
			updateServerLog(log);
		}
		else if (key.equals("USER_ADDED"))
		{
			Integer clientNumber = (Integer) update.getValue(); 
			log = "New connection with client# " + clientNumber + "." ;
			updateServerLog(log);
		}
		else if (key.equals("USER_REMOVED"))
		{
			Integer clientNumber = (Integer) update.getValue(); 
			log = "Client# " + clientNumber + "disconnected." ;
			updateServerLog(log);
		} // else if
		else
			throw new RuntimeException("Unforseen key update");
	
		int clientNumber = controller.getClientNumber();
		
		userCountLbl.setText(clientNumber + "");
	} //update

	protected void initializeDemo() 
	{
		menuBar.setVisible(true);
		updateScreen("infoScrPane");		
	} // initializeDemo
	
	private void updateScreen(String panel)
	{	
		// Change screen to the - panel - provided as argument
		panels = (CardLayout) mainPanel.getLayout();  
		panels.show(mainPanel, panel );
		
		if (panel.equals("viewAccountPanel"))
		{
			accountsPanel.removeAll();
			
			ArrayList<String> accountList = controller.getAccountList();
			accountRadioGroup = new ButtonGroup();	
			JRadioButton accountRadio;
			String radioTxt;	
						
			for (String account : accountList)
			{
				radioTxt = account + "\t-\t" + controller.getAccountType(account);
				accountRadio = new JRadioButton(radioTxt);
				accountRadio.setActionCommand(account);
				accountRadio.addActionListener(new ActionListener() {			
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						updateButtons();
					}
				});
				
				accountRadioGroup.add(accountRadio);
				accountsPanel.add(accountRadio);			
			} // for
			
			accountsPanel.repaint();
			accountsPanel.validate();		
			accountScrollPane.revalidate();
			
			accountRadioGroup.clearSelection();
			updateButtons();
		} // if		
	} // updateScreen
	
	protected void updateButtons() 
	{
		ButtonModel selection = accountRadioGroup.getSelection();
		if (selection != null)
		{
			viewAccButton.setEnabled(true);
			deleteAccButton.setEnabled(true);
		} // if
		else
		{
			viewAccButton.setEnabled(false);
			deleteAccButton.setEnabled(false);
		} // else				
	} // updateButtons

	public void updateServerLog(String log)
	{
		serverLog.append(log);
		controller.logServerActivity(log);
	} // updateServerLog
		
	protected void addAccount() 
	{	
		controller.addAccount();
			
		updateScreen("viewAccountPanel");
	} // addAccount
	
	protected void viewAccount() 
	{
		String accNum = accountRadioGroup.getSelection().getActionCommand();
		controller.viewAccount(accNum);
	} // viewAccount
	
	protected void deleteAccount() 
	{
		String accNum = accountRadioGroup.getSelection().getActionCommand();
		controller.removeAccount(accNum);	
		
		updateScreen("viewAccountPanel");	
	} // deleteAccount
	
	protected void closeApplication() 
	{
		// TODO Auto-generated method stub
		controller.stopApplication();
		this.dispose();
	} // closeApplication
}
