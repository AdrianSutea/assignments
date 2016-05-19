package bank;

public interface BankProc {
	
	/**
     *  @pre a != null
     *  @pre p != null
     *  @post !accounts.isEmpty()
     */
	public void addAccount(Account a, Person p);
	/**
     *  @pre id<0
     *  @post !accounts.contains(account)
     */
	public void removeAccount(int id);
	/**
     *  @pre p != null
     *  @post !persons.isEmpty()
     *  @post persons.size()=persons.size@pre + 1
     */
	public void addPerson(Person p);
	/**
     *  @pre p != null
     *  @post !persons.contains(p)
     */
	public void removePerson(Person p);
		
	/**
     *  @pre p != null
     */
	public void generateReport(Person p);
}
