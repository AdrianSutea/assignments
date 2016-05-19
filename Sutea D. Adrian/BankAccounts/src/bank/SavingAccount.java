package bank;

@SuppressWarnings("serial")
public class SavingAccount extends Account {

	public SavingAccount(int id, Person holder, int sum) {
		super(id, holder, sum);
		addObserver(holder);
		this.setType(0);
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
		if (amount == this.getSum()) {
			this.setSum(0);
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}
}
