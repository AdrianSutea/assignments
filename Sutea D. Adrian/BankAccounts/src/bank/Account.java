package bank;

import java.io.Serializable;
import java.util.Observable;

@SuppressWarnings("serial")
public abstract class Account extends Observable implements Serializable {
	private int id;
	private Person holder;
	private int sum;
	private int type;

	public Account(int id, Person holder, int sum) {
		this.id = id;
		this.holder = holder;
		this.sum = sum;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getHolder() {
		return holder;
	}

	public void setHolder(Person holder) {
		this.holder = holder;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {//1=spending, 0=saving
		this.type = type;
	}

	public abstract boolean withdrawFunds(int amount);

	public abstract boolean addFunds(int amount);
}
