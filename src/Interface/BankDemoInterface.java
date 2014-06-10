package Interface;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.BevelBorder;

import Controllers.BankDemoController;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class BankDemoInterface extends JFrame {

	// GUI Components
	@SuppressWarnings("rawtypes")
	private JList list;
	private JButton addAccButton;
	private JButton viewAccButton;
	private JButton deleteAccButton;
		
	public BankDemoInterface() 
	{
		initialize();		
		updateScreen();
		setLocationRelativeTo(null);
	} // BankDemoInterface

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes" })
	private void initialize() {
		
		addAccButton = new JButton("Add New Account");
		addAccButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAccount();
			}
		});
		
		viewAccButton = new JButton("View Account");
		viewAccButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewAccount();
			}
		});
		viewAccButton.setEnabled(false);
		
		deleteAccButton = new JButton("Delete Account");
		deleteAccButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteAccount();
			}
		});
		deleteAccButton.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeApp();
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(addAccButton)
						.addComponent(deleteAccButton)
						.addComponent(viewAccButton))
					.addGap(90)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
					.addGap(54))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(516, Short.MAX_VALUE)
					.addComponent(btnClose)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(addAccButton)
							.addGap(68)
							.addComponent(viewAccButton)
							.addGap(81)
							.addComponent(deleteAccButton)))
					.addGap(42)
					.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		list = new JList();
		list.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				accountSelectedEvent();
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				accountSelectedEvent();
			}
		});
		scrollPane.setViewportView(list);
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().setLayout(groupLayout);
		
		this.setSize(601, 407);
	} // initialize

	protected void accountSelectedEvent() 
	{
		if ( !list.isSelectionEmpty() )
		{
			viewAccButton.setEnabled(true);
			deleteAccButton.setEnabled(true);
		}
		else
		{
			viewAccButton.setEnabled(false);
			deleteAccButton.setEnabled(false);
		}
	} // accountSelectedEvent

	protected void viewAccount() 
	{
		String accNum;
		if ( list.isSelectionEmpty() )
			return;
		else
		{			
			accNum = (String) list.getSelectedValue();
			
//			System.out.println("Selected Account: " + accNum);
			
			BankDemoController.viewAccount(accNum);
		} // else		
	}

	protected void deleteAccount() {
		String accNum;
		
		if ( list.isSelectionEmpty() )
			return;
		else
		{
			accNum = (String) list.getSelectedValue();
			
			BankDemoController.removeAccount(accNum);
			
			updateScreen();
		} // else		
	} // deleteAccount

	protected void addAccount() 
	{
		String acc1 = "acc1";
		
		BankDemoController.addAccount(acc1, null);
			
		updateScreen();
	} // addAccount
	
	@SuppressWarnings("unchecked")
	private void updateScreen()
	{
		DefaultListModel<String> updatedModel = BankDemoController.getAccountList();
		
		list.setModel(updatedModel);
		
	} // updateScreen
	
	protected void closeApp() 
	{
		this.dispose();
	} // closeApp
}
