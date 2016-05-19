package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bank.Account;
import bank.Bank;
import bank.Person;
import bank.SavingAccount;
import bank.SpendingAccount;

public class AdminGui {
	private JFrame frame = new JFrame();
	private JPanel btnPanel = new JPanel(new GridLayout(6, 1, 25, 25));
	private JPanel textPanel = new JPanel(new GridLayout(4, 1, 50, 50));
	private JPanel labelPanel = new JPanel(new GridLayout(4, 1, 50, 50));
	private JPanel inputPanel = new JPanel(new GridLayout(1, 2, 50, 50));
	private JTextField holder = new JTextField();
	private JTextField ssn = new JTextField();
	private JTextField id = new JTextField();
	private JTextField type = new JTextField();
	private JLabel holderLabel = new JLabel("Holder:");
	private JLabel ssnLabel = new JLabel("SSN:");
	private JLabel idLabel = new JLabel("Account ID:");
	private JLabel typeLabel = new JLabel("Type:");
	private JTable tab;
	private JButton addAcc = new JButton("Add account");
	private JButton removeAcc = new JButton("Remove account");
	private JButton addPerson = new JButton("Add person");
	private JButton removePerson = new JButton("Remove person");
	private JButton serialize = new JButton("Serialize");
	private JButton seeAccounts = new JButton("See accounts");
	private JLabel log = new JLabel();
	private String columnNames[] = {"Account ID", "Holder", "SSN", "Sum", "Type"};
	//private Bank bank = new Bank();
	
	public AdminGui(String name, Bank bank){
		frame.setLayout(new BorderLayout());
		btnPanel.add(addAcc);
		btnPanel.add(removeAcc);
		btnPanel.add(addPerson);
		btnPanel.add(removePerson);
		btnPanel.add(serialize);
		btnPanel.add(seeAccounts);
		DefaultTableModel table = new DefaultTableModel(columnNames,0);
		tab=new JTable();
		tab.setModel(table);
		JScrollPane scroll = new JScrollPane(tab);
		seeAccounts.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				table.setRowCount(0);
				HashMap<Person, Account> acc = new HashMap<>();
				acc=bank.getAllAccounts();
				for(Iterator<Entry<Person, Account>> it = acc.entrySet().iterator(); it.hasNext();){
					Entry<Person, Account> entry = it.next();
					String typ = new String();
					if(entry.getValue().getType()==1)
						typ = "SPENDING";
					else
						typ="SAVING";
					
					Object[] rowData={ new Integer(entry.getValue().getId()), entry.getKey().getName(), new Long(entry.getKey().getSSN()), new Integer(entry.getValue().getSum()), typ };
					table.addRow(rowData);
				}
				//Object[] rowData = {new Integer(1), new Integer(2), new Integer(3),new Integer(4)};
				//table.addRow(rowData);
			}
		});
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
		addAcc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String holderName = new String();
				Account a;
				long ss;
				int idd;
				holderName = holder.getText();
				ss=Long.parseLong(ssn.getText());
				idd=Integer.parseInt(id.getText());
				Person p = new Person();
				p.setName(holderName);
				p.setSSN(ss);
				if(Objects.equals(type.getText(), "SPENDING")){
					a = new SpendingAccount(idd, p, 0);
					bank.addAccount(a, p);
					if(bank.searchPerson(p)==null)
						bank.addPerson(p);
				}
				else{
					if(Objects.equals(type.getText(), "SAVING")){
						a = new SavingAccount(idd, p, 0);
						bank.addAccount(a, p);
						if(bank.searchPerson(p)==null)
							bank.addPerson(p);
					}
				}
			}
		});
		removeAcc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bank.removeAccount(Integer.parseInt(id.getText()));
			}
		});
		addPerson.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Person p = new Person();
				p.setName(holder.getText());
				p.setSSN(Long.parseLong(ssn.getText()));
				if(bank.searchPerson(p)==null)
					bank.addPerson(p);			}
		});
		removePerson.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Person p = new Person();
				p.setName(holder.getText());
				p.setSSN(Long.parseLong(ssn.getText()));
				if(bank.searchPerson(p)!=null){
					p=bank.searchPerson(p);
					bank.removePerson(p);
				}
			}
		});
		labelPanel.add(holderLabel);
		labelPanel.add(ssnLabel);
		labelPanel.add(idLabel);
		labelPanel.add(typeLabel);
		textPanel.add(holder);
		textPanel.add(ssn);
		textPanel.add(id);
		textPanel.add(type);
		inputPanel.add(labelPanel);
		inputPanel.add(textPanel);
		log.setText(name);
		frame.add(log, BorderLayout.NORTH);
		frame.add(btnPanel, BorderLayout.EAST);
		frame.add(scroll, BorderLayout.CENTER);
		frame.add(inputPanel, BorderLayout.SOUTH);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
