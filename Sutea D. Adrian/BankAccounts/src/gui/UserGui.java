package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bank.Account;
import bank.Bank;
import bank.Person;

public class UserGui {
	private JFrame frame = new JFrame();
	private JPanel btnPanel = new JPanel(new GridLayout(4, 1, 50, 50));
	private JPanel inputPanel = new JPanel(new GridLayout(2, 2, 50, 50));
	private JLabel accId = new JLabel("Account id:");
	private JLabel amountLabel = new JLabel("Amount:");
	private JTextField id = new JTextField();
	private JTextField amount = new JTextField();
	private JButton addFunds = new JButton("Add Funds");
	private JButton withdrawFunds = new JButton("Withdraw funds");
	private JButton seeAccounts = new JButton("See my accounts");
	private JButton serialize = new JButton("Serialize");
	private JTable table;
	private JLabel log = new JLabel();
	private Person p = new Person();
	private String columnNames[] = {"Account ID", "Holder", "SSN", "Sum", "Type"};
	//private Bank bank = new Bank();
	
	public UserGui(String name, Long ssn, Bank bank){
		p.setName(name);
		p.setSSN(ssn);
		DefaultTableModel tab = new DefaultTableModel(columnNames,0);
		table=new JTable();
		table.setModel(tab);
		JScrollPane scroll = new JScrollPane(table);
		frame.setLayout(new BorderLayout());
		frame.setSize(800, 600);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		inputPanel.add(accId);
		inputPanel.add(id);
		inputPanel.add(amountLabel);
		inputPanel.add(amount);
		frame.add(inputPanel, BorderLayout.SOUTH);
		log.setText("You are logged in as: "+name);
		btnPanel.add(addFunds);
		btnPanel.add(withdrawFunds);
		btnPanel.add(seeAccounts);
		btnPanel.add(serialize);
		serialize.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("BankFile.ser"));
					out.writeObject(bank);
					out.close();
				}catch(IOException e1){
					e1.printStackTrace();
				}
			}
		});
		addFunds.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Account a = bank.getAccountByID(Integer.parseInt(id.getText()));
				if(a.addFunds(Integer.parseInt(amount.getText()))==false)
					JOptionPane.showMessageDialog(frame, "You can't add sums smaller than 10!");
			}
		});
		withdrawFunds.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Account a = bank.getAccountByID(Integer.parseInt(id.getText()));
				if(Integer.parseInt(amount.getText())>a.getSum()){
					JOptionPane.showMessageDialog(frame, "Insufficient funds");
				}
				else{
					if(a.withdrawFunds(Integer.parseInt(amount.getText()))==false){
						if(a.getType()==1)
							JOptionPane.showMessageDialog(frame, "The amount must be >100");
						else
							JOptionPane.showMessageDialog(frame, "You can only withdraw all the money from a saving account!");
					}
				}
			}
		});
		seeAccounts.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tab.setRowCount(0);
				HashMap<Person, Account> acc = new HashMap<>();
				acc=bank.getAllAccounts();
				for(Iterator<Entry<Person, Account>> it = acc.entrySet().iterator(); it.hasNext();){
					Entry<Person, Account> entry = it.next();
					if(entry.getKey().getSSN()==p.getSSN()){
						String typ = new String();
						if(entry.getValue().getType()==1)
							typ = "SPENDING";
						else
							typ="SAVING";
						
						Object[] rowData={ new Integer(entry.getValue().getId()), entry.getKey().getName(), new Long(entry.getKey().getSSN()), new Integer(entry.getValue().getSum()), typ };
						tab.addRow(rowData);
					}
				}
			}
		});
		frame.add(scroll, BorderLayout.CENTER);
		frame.add(btnPanel, BorderLayout.EAST);
		frame.add(log, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
