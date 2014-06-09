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

@SuppressWarnings("serial")
public class BankDemoInterface extends JFrame {

	// GUI Components
	private JList<DefaultListModel<String>> list;
	private JButton addAccButton;
	private JButton viewAccButton;
	private JButton deleteAccButton;
	
	
	private DefaultListModel<String> accountsList;
	
	public BankDemoInterface() 
	{
		accountsList = new DefaultListModel<>();
		initialize();		
		
	} // BankDemoInterface

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		addAccButton = new JButton("Add New Account");
		addAccButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAccount();
			}
		});
		
		viewAccButton = new JButton("View Account");
		viewAccButton.setEnabled(false);
		
		deleteAccButton = new JButton("Delete Account");
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
		
		list = new JList(accountsList);
		scrollPane.setViewportView(list);
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().setLayout(groupLayout);
		
		this.setSize(601, 407);
	}

	protected void closeApp() 
	{
		this.dispose();
	} // closeApp

	protected void addAccount() 
	{
		String acc1 = "acc1";

		accountsList.addElement(acc1);
	} // addAccount
}
