package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bank.Bank;

public class Gui {
	private JFrame frame = new JFrame();
	private JPanel labelPanel = new JPanel(new GridLayout(3, 1, 20, 20));
	private JPanel textPanel = new JPanel(new GridLayout(3, 1, 20, 20));
	private JLabel userLabel = new JLabel("Username:");
	private JLabel passLabel = new JLabel("Password:");
	private JLabel ssnLabel = new JLabel("SSN:");
	private JTextField userText = new JTextField();
	private JPasswordField passText = new JPasswordField();
	private JTextField ssn = new JTextField();
	private JButton loginBtn = new JButton("Login");
	private static String username = new String();
	private Bank bank;

	public Gui() {
		this.bank=new Bank();
		frame.setLayout(new BorderLayout());
		try
	    {
		    @SuppressWarnings("resource")
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("BankFile.ser"));
		    bank = (Bank)in.readObject();
	    }
	    catch(IOException e1)
	    {
	    	e1.printStackTrace();
	    }
	    catch(ClassNotFoundException e1)
	    {
	    	e1.printStackTrace();
	    }
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = userText.getText();
				if (passText.getPassword().length != 0) {
					if (Objects.equals(username, "Admin")) {
						frame.setVisible(false);
						new AdminGui(username, bank);
					} else {
						frame.setVisible(false);
						if(ssn.getText().length()==10)
							new UserGui(username, Long.parseLong(ssn.getText()), bank);
						else{
							JOptionPane.showMessageDialog(frame, "SSN has wrong length");
						}
					}
				}
			}
		});
		labelPanel.add(userLabel);
		textPanel.add(userText);
		labelPanel.add(ssnLabel);
		textPanel.add(ssn);
		labelPanel.add(passLabel);
		textPanel.add(passText);
		frame.add(labelPanel, BorderLayout.WEST);
		frame.add(textPanel, BorderLayout.CENTER);
		frame.add(loginBtn, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 250);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
