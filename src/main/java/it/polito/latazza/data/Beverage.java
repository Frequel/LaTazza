package it.polito.latazza.data;

import it.polito.latazza.exceptions.BeverageException;

public class Beverage {

	private int ID;
	private String name;
	private int remainingCapsules;
	private float price;
	private int CapsulesPerBox;
	private float BoxPrice;

	private int oldRemainingCap = 0;
	private int newRemainingCap = 0;

	private float newPrice = 0;

	private boolean newValuesSet;

	public Beverage(int iD, String name, int remainingCapsules, float price, int CapsulesPerBox, float BoxPrice)
			throws BeverageException {
		String CapsulesPerbox = Integer.toString(CapsulesPerBox);
		String Boxprice = Float.toString(BoxPrice);
		if (name == null || CapsulesPerbox == null || Boxprice == null)
			throw new BeverageException();

		if (!(name.matches("([A-Z]|[a-z])+")) || CapsulesPerBox < 0 || BoxPrice < 0)
			throw new BeverageException();
		else
			this.name = name;

		if (iD < 0 || iD > Integer.MAX_VALUE) {
			ID = 1;
		} else {
			ID = iD;
		}
		this.remainingCapsules = remainingCapsules;
		this.price = price;
		this.CapsulesPerBox = CapsulesPerBox;
		this.BoxPrice = BoxPrice;
		this.newValuesSet = false;
	}

	public int getID() {
		return ID;
	}

	public String getName() {

		return name;
	}

	public int getRemainingCapsules() {
		return remainingCapsules;
	}

	public void setRemainingCapsules(int remainingCapsules) {
		if (remainingCapsules < 0 || remainingCapsules > Integer.MAX_VALUE) {
			remainingCapsules = 0;
		} else {
			this.remainingCapsules = remainingCapsules;
		}
	}

	public float getPrice() {
		return price;
	}

	public void setName(String newName) throws BeverageException {
		if (!(newName.matches("([A-Z]|[a-z])+")))
			throw new BeverageException();
		else
			this.name = newName;

	}

	public void setPrice(float newPrice) throws BeverageException {
		if (newPrice < 0 || newPrice > Float.MAX_VALUE)
			throw new BeverageException();
		else
			this.price = newPrice;
	}

	public int getCapsulesPerBox() {
		return CapsulesPerBox;
	}

	public void setCapsulesPerBox(int capsulesPerBox) {
		if (capsulesPerBox < 0 || capsulesPerBox > Integer.MAX_VALUE) {
			remainingCapsules = 0;
		} else {
			CapsulesPerBox = capsulesPerBox;
		}
	}

	public float getBoxPrice() {
		return BoxPrice;
	}

	public void setBoxPrice(float boxPrice) throws BeverageException {
		if (boxPrice < 0 || boxPrice > Float.MAX_VALUE)
			throw new BeverageException();
		else
			BoxPrice = boxPrice;
	}

	public void setNewRemainingCap(int newRemainingCap)  {
		if (newRemainingCap < 0 || newRemainingCap > Integer.MAX_VALUE) {
			this.newRemainingCap = 0;
		} else {

			this.newRemainingCap = newRemainingCap;
		}

	}

	public void setNewPrice(float newPrice)  {
		if (newPrice < 0 || newPrice > Float.MAX_VALUE) {
			this.newPrice = 0;
		}  else {
			this.newPrice = newPrice;
		
		}

	}

	public void swap() {

		// this.remainingCapsules = this.newRemainingCap;
		this.oldRemainingCap = this.newRemainingCap;

		this.price = this.newPrice;

		this.newRemainingCap = 0;

		this.newPrice = 0;

		this.newValuesSet = false;

	}

	public void setFlagNewPrice() {

		this.newValuesSet = true;

	}

	public int getNewRemainingCap() {
		return newRemainingCap;
	}

	public float getNewPrice() {
		return newPrice;
	}

	public boolean isNewValuesset() {
		return this.newValuesSet;
	}

	public int getOldRemainingCap() {
		return oldRemainingCap;
	}

	public void setOldRemainingCap(int oldRemainingCap) {
		if (oldRemainingCap < 0 || oldRemainingCap > Integer.MAX_VALUE) {
			this.oldRemainingCap = 0;
		}  else {

			this.oldRemainingCap = oldRemainingCap;
		}
	}

}
