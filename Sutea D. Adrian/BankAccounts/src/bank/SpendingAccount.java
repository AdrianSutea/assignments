package bank;

@SuppressWarnings("serial")
public class SpendingAccount extends Account {

	public SpendingAccount(int id, Person holder, int sum) {
		super(id, holder, sum);
		addObserver(holder);
		this.setType(1);
	}

	public boolean addFunds(int amount) {
		if (amount < 10)
			return false;
		else{
			this.setSum(this.getSum() + amount);
			setChanged();
			notifyObservers();
		}
		return true;
	}

	public boolean withdrawFunds(int amount) {
		if (amount < 100)
			return false;
		else{
			this.setSum(this.getSum() - amount);
			setChanged();
			notifyObservers();
		}
		return true;
	}
	
}
