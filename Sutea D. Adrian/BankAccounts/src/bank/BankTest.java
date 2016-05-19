package bank;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.Test;

public class BankTest {
	private Bank bank = new Bank();
	
	@Test
	public void testAddAccount(){
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
		Person p = new Person();
		p.setName("Test");
		p.setSSN(1111111111);
		Account a = new SpendingAccount(99, p, 0);
		boolean test=false;
		bank.addAccount(a, p);
		if(bank.getAllAccounts().get(p)!=null)
			test=true;
		assertTrue(test);
	}
	
	@Test
	public void testAddPerson(){
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
		boolean test=false;
		Person p = new Person();
		p.setName("Test");
		p.setSSN(1234123456);
		bank.addPerson(p);
		if(bank.searchPerson(p)!=null)
			test=true;
		assertTrue(test);
	}
	
	@Test
	public void testRemoveAccount(){
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
		boolean test=true;
		bank.removeAccount(bank.getAccountByID(1).getId());
		if(bank.getAccountByID(1)!=null)
			test=false;
		assertTrue(test);
	}
	
	@Test
	public void testRemovePerson(){
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
		boolean test=true;
		Person p = new Person();
		p=bank.getPerson(0);
		bank.removePerson(p);
		if(bank.searchPerson(p)!=null){
			test=false;
		}
		assertTrue(test);
	}
}
