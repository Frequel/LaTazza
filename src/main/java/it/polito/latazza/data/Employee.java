package it.polito.latazza.data;

//import java.util.regex.Pattern;

import it.polito.latazza.exceptions.EmployeeException;

public class Employee {
	
	private int ID;
	private String name,surname;
	private float balance;
	
	
	
	
	
	public Employee(int iD, String name, String surname, float balance) throws EmployeeException {
		if(name==null || surname==null)
			throw new EmployeeException();
		if(!(name.matches("([A-Z]|[a-z])+"))|| !(surname.matches("([A-Z]|[a-z])+")))
			throw new EmployeeException();
		else {
		
		this.name = name;
		this.surname = surname;
		}
		if(iD<0 || iD>Integer.MAX_VALUE) {
			ID=1;
		}else
		{
		ID = iD;
		}
		
		this.balance = balance;
	}





	public int getID() {
		return ID;
	}





	public String getName() {
		return name;
	}





	public String getSurname() {
		return surname;
	}





	public float getBalance() {
		return balance;
	}
	
	public void setBalance( float newBalance) throws EmployeeException{
		if(newBalance>(float)Integer.MAX_VALUE)
			throw new EmployeeException();
		else
			balance=newBalance;
	}





	public void setName(String name) throws EmployeeException{
		if(!(name.matches("([A-Z]|[a-z])+")))
			throw new EmployeeException();
		else
			this.name = name;
	
	}





	public void setSurname(String surname) throws EmployeeException{
		if(!(surname.matches("([A-Z]|[a-z])+")))
			throw new EmployeeException();
		else
			this.surname = surname;
	}





	
	

}
