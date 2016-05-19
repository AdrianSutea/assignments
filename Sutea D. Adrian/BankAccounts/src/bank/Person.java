package bank;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("serial")
public class Person implements Serializable, Observer {
	private String name;
	private long SSN;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSSN() {
		return SSN;
	}

	public void setSSN(long sSN) {
		SSN = sSN;
	}

	@Override
	public void update(Observable obs, Object obj) {
		System.out.println(this.name+"'s account has been modified");
	}

}
