package bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class Bank implements BankProc, Serializable {

	private HashMap<Person, Account> accounts = new HashMap<>();
	private ArrayList<Person> clients = new ArrayList<>();
	
	public void addAccount(Account a, Person p){
		assert p!=null : "Add null person";
		assert a!=null : "Add null account";
		assert isWellFormed() : "Not well formed";
		accounts.put(p, a);
		assert !accounts.isEmpty() : "Empty after adding";
		assert isWellFormed() : "Not well formed";
	}
	
	public void removeAccount(int id){
		assert id<0 : "Negative id";
		assert isWellFormed() : "Not well formed";
		for(Iterator<Entry<Person, Account>> it = accounts.entrySet().iterator(); it.hasNext();){
			Entry<Person, Account> entry = it.next();
			if(entry.getValue().getId()==id)
				it.remove();
		}
		assert isWellFormed() : "Not well formed";
	}
	
	public void addPerson(Person p){
		assert isWellFormed() : "Not well formed";
		assert p==null : "Add null person";
		clients.add(p);
		assert clients.isEmpty() : "Not added person";
		assert isWellFormed() : "Not well formed";
	}
	
	public void removePerson(Person p){
		assert p==null : "Remove null person";
		assert isWellFormed() : "Not well formed";
		clients.remove(p);
		for(Iterator<Entry<Person, Account>> it = accounts.entrySet().iterator(); it.hasNext();){
			Entry<Person, Account> entry = it.next();
			if(entry.getKey().getSSN()==p.getSSN())
				it.remove();
		}
		assert clients.contains(p) : "Didn't remove person";
		assert accounts.containsValue(p) : "Didn't remove accounts";
		assert isWellFormed() : "Not well formed";
	}
	
	public Person getPerson(int i){
		return clients.get(i);
	}

	public boolean isWellFormed(){
		for(Iterator<Entry<Person, Account>> it = accounts.entrySet().iterator(); it.hasNext();){
			Entry<Person, Account> entry = it.next();
			if(entry.getValue().getSum()<0)
				return false;
		}
		return true;
	}
	
	public Person searchPerson(Person p){
		assert p==null : "Search null person";
		assert isWellFormed() : "Not well formed";
		for(Person pers : clients){
			if(pers.getSSN()==p.getSSN())
				return p;
		}
		assert isWellFormed() : "Not well formed";
		return null;
	}
	
	public Account getAccountByID(int id){
		assert id<0 : "Negative id";
		assert isWellFormed() : "Not well formed";
		for(Iterator<Entry<Person, Account>> it = accounts.entrySet().iterator(); it.hasNext();){
			Entry<Person, Account> entry = it.next();
			if(entry.getValue().getId()==id)
				return entry.getValue();
		}
		assert isWellFormed() : "Not well formed";
		return null;
	}
	
	public HashMap<Person, Account> getAllAccounts(){
		assert isWellFormed() : "Not well formed";
		return this.accounts;
	}
	
	public void generateReport(Person p){
		
	}
}
