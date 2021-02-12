package it.polito.latazza.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;

public class DataImpl implements DataInterface {
	private Statement stmt = null;
	private ResultSet rset = null;

	private static String DBname = "." + File.separator + "src" + File.separator + "main" + File.separator + "java"
			+ File.separator + "it" + File.separator + "polito" + File.separator + "latazza" + File.separator + "data"
			+ File.separator + "LaTazzaDB1.db";
	private String dbName = "latazza";
	private Connection conn = null;
	private String path = "." + File.separator + "src" + File.separator + "main" + File.separator + "java"
			+ File.separator + "it" + File.separator + "polito" + File.separator + "latazza" + File.separator + "data"
			+ File.separator + "balance.txt";

	private float balance;
	private Map<Integer, Employee> employeeMap = new HashMap<>();
	private Map<Integer, Beverage> beverageMap = new HashMap<>();
	private int idGlobalEmployee = 1;
	private int idGlobalBeverage = 1;
	private float overflow;
	private float max = (float) Integer.MAX_VALUE;
	private float min = 0;

	private Connection getDBConnection() throws SQLException {

		try {
			File file = new File(DBname);
			boolean exists = file.exists();
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + DBname);
			stmt = conn.createStatement();

			if (conn != null) {
				if (exists) {
					return conn;
				} else {
					// NextCapsulesPerBox è in reatà NextRemainingCapsules
					/*
					String sql = "CREATE TABLE beverage (" + "  ID INT(11) PRIMARY KEY NOT NULL,"
							+ "  Name VARCHAR(45) NOT NULL," + "  Capsules INT(11) NOT NULL,"
							+ "  Price FLOAT NOT NULL," + "  CapsulesPerBox INT(11) NOT NULL"
							+ "  NextPrice FLOAT NOT NULL," + "  NextCapsulesPerBox INT(11) NOT NULL);";
					*/
					String sql = "CREATE TABLE beverage (" + 
							"  ID INT PRIMARY KEY NOT NULL," + 
							"  Name CHAR NOT NULL," + 
							"  Capsules INT NOT NULL," + 
							"  Price FLOAT NOT NULL," + 
							"  CapsulesPerBox INT NOT NULL," + 
							"  NextPrice FLOAT NOT NULL," + 
							"  NextCapsulesPerBox INT NOT NULL);";

					stmt.executeUpdate(sql);

					sql = "CREATE TABLE employee (" + "  ID INT(11) PRIMARY KEY NOT NULL,"
							+ "  Name VARCHAR(45) NOT NULL," + "  Surname VARCHAR(45) NOT NULL,"
							+ "  Balance FLOAT NOT NULL);";
					stmt.executeUpdate(sql);

					sql = "CREATE TABLE transactions (" + "  Employee VARCHAR(45) NULL,"
							+ "  Date TEXT PRIMARY KEY NOT NULL," + "  TypeOfTransaction VARCHAR(45) NOT NULL,"
							+ "  BeverageName VARCHAR(45) NULL," + "  Quantity INT(11) NULL," + "  Amount FLOAT NULL);";
					stmt.executeUpdate(sql);

					/*
					 * sql= "CREATE TABLE boxes (" + "  ID INT(11) PRIMARY KEY NOT NULL," +
					 * "  Capsules INT(11) NOT NULL," + "  RemainingCapsules INT(11) NOT NULL," +
					 * "  Price FLOAT NOT NULL," + "  TimeStamp  TIMESTAMP PRIMARY KEY NOT NULL);";
					 * stmt.executeUpdate(sql);
					 */

					conn.setCatalog(dbName);
					stmt.close();
				}

			}

		} catch (SQLException ex) {
			conn.close();
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}

	private void insertB(int id, String name, int nCapsules, Float price, int CapsulesPerBox, float nextPrice,
			int nextCapsulesPerBox) {
		String sql = "INSERT INTO beverage VALUES(?,?,?,?,?,?,?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setInt(3, nCapsules);
			pstmt.setFloat(4, price);
			pstmt.setInt(5, CapsulesPerBox);
			pstmt.setFloat(6, nextPrice);
			pstmt.setInt(7, nextCapsulesPerBox);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * private void insertBo(int id, int nCapsules, int rCapsules, Float price,
	 * Timestamp timestamp) { String sql = "INSERT INTO beverage VALUES(?,?,?,?,?)";
	 * 
	 * try (PreparedStatement pstmt = conn.prepareStatement(sql)) { pstmt.setInt(1,
	 * id); pstmt.setInt(2, nCapsules); pstmt.setInt(3, rCapsules);
	 * pstmt.setFloat(4, price); pstmt.setTimestamp(5, timestamp);
	 * pstmt.executeUpdate();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } }
	 */
	private void insertE(int id, String name, String surname, Float balance) {
		String sql = "INSERT INTO employee VALUES(?,?,?,?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, surname);
			pstmt.setFloat(4, balance);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	private void insertT(String name, Timestamp time, String type_of_transaction, String BeverageName, int Quantity,
			float Amount) {
		String sql = "INSERT INTO transactions VALUES(?,?,?,?,?,?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setString(2, time.toString());
			pstmt.setString(3, type_of_transaction);
			pstmt.setString(4, BeverageName);
			pstmt.setInt(5, Quantity);
			pstmt.setFloat(6, Amount);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	private void updateB(int id, String name, int nCapsules, float price, int CapsulesPerBox, float nextPrice,
			int nextCapsulesPerBox) {
		String sql = "UPDATE beverage SET Name = ?, Capsules = ?, Price = ?, CapsulesPerBox = ?, nextPrice = ?, nextCapsulesPerBox = ? WHERE ID = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, name);
			pstmt.setInt(2, nCapsules);
			pstmt.setFloat(3, price);
			pstmt.setInt(4, CapsulesPerBox);
			pstmt.setInt(7, id);
			pstmt.setFloat(5, nextPrice);
			pstmt.setInt(6, nextCapsulesPerBox);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/*
	 * private void updateBo(int id, int nCapsules, int rCapsules, Float price,
	 * Timestamp timestamp) { String sql =
	 * "UPDATE boxes SET nCapsules = ?, rCapsules = ?, Price = ?, CapsulesPerBox = ? WHERE ID = ? AND Timestamp = ?"
	 * ;
	 * 
	 * try (PreparedStatement pstmt = conn.prepareStatement(sql)) { pstmt.setInt(1,
	 * nCapsules); pstmt.setInt(2, rCapsules); pstmt.setFloat(3, price);
	 * pstmt.setInt(4, id); pstmt.setTimestamp(5, timestamp); pstmt.executeUpdate();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } }
	 */

	private void updateE(int id, String name, String surname, Float balance) {
		String sql = "UPDATE employee SET Name = ?, Surname = ?, Balance = ? WHERE ID = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, name);
			pstmt.setString(2, surname);
			pstmt.setFloat(3, balance);
			pstmt.setInt(4, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public DataImpl() throws SQLException {
		conn = getDBConnection();
		try {
			if (conn != null)
				stmt = conn.createStatement();

			try {
				String query = "SELECT * FROM employee;";
				rset = stmt.executeQuery(query);
				try {
					while (rset.next()) {

						Employee emp = new Employee(idGlobalEmployee, rset.getString(2), rset.getString(3),
								rset.getFloat(4));

						employeeMap.put(idGlobalEmployee, emp);
						idGlobalEmployee++;
					}
					query = "SELECT * FROM beverage";
					rset = stmt.executeQuery(query);
					while (rset.next()) {
						Beverage bev = new Beverage(idGlobalBeverage, rset.getString(2),
								rset.getInt(3) + rset.getInt(7), rset.getFloat(4), rset.getInt(5),
								rset.getFloat(4) * rset.getInt(5));
						bev.setOldRemainingCap(rset.getInt(3));
						if (rset.getFloat(6) != 0 && rset.getInt(7) != 0) {
							bev.setNewPrice(rset.getFloat(6));
							bev.setNewRemainingCap(rset.getInt(7));
							bev.setFlagNewPrice();
						}
						beverageMap.put(idGlobalBeverage, bev);
						idGlobalBeverage++;
					}
					/*
					 * query = "SELECT * FROM boxes GROUP BY ID ORDER BY Timestamp;"; rset =
					 * stmt.executeQuery(query); while (rset.next()) { //aggiornare a struttura dati
					 * aggiornata }
					 */
					BufferedReader re = null;
					try {
						re = new BufferedReader(new FileReader(path));
					} catch (FileNotFoundException e) {
						PrintWriter writer = null;
						try {
							writer = new PrintWriter(path, "UTF-8");
						} catch (FileNotFoundException | UnsupportedEncodingException f) {

							e.printStackTrace();
						}

						writer.println(balance);

						writer.close();

					}
					String input_line = null;
					try {
						input_line = re.readLine();
					} catch (IOException e) {

						e.printStackTrace();
					}
					balance = Float.parseFloat(input_line);
				} catch (EmployeeException | BeverageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						rset.close();
					} catch (Throwable ignore) {
					}
				}
			} finally {
				try {
					stmt.close();
				} catch (Throwable ignore) {
				}
			}
		} finally {

		}
	}

	@Override
	public Integer sellCapsules(Integer employeeId, Integer beverageId, Integer numberOfCapsules, Boolean fromAccount)

			throws EmployeeException, BeverageException, NotEnoughCapsules {
		String type = new String();

		float amount = 0;
		if (numberOfCapsules == null)
			throw new NotEnoughCapsules();
		if (!employeeMap.containsKey(employeeId)) {
			throw new EmployeeException();
		}
		if (!beverageMap.containsKey(beverageId)) {
			throw new BeverageException();
		}
		if (beverageMap.get(beverageId).getRemainingCapsules() < numberOfCapsules || numberOfCapsules < min) {
			throw new NotEnoughCapsules();
		}
		Beverage bv = beverageMap.get(beverageId);
		Employee em = employeeMap.get(employeeId);

		overflow = (float) bv.getPrice() * numberOfCapsules;
		if (overflow > max) {
			throw new BeverageException();
		}

		if (bv.getRemainingCapsules() < numberOfCapsules)
			throw new NotEnoughCapsules();
		int old = bv.getOldRemainingCap();
		if (fromAccount) {
			type = "BALANCE";
			/*
			 * if (em.getBalance() < bv.getPrice() * numberOfCapsules) { throw new
			 * EmployeeException(); }
			 */
			
			if (bv.getOldRemainingCap() < numberOfCapsules) {
				em.setBalance(em.getBalance() - bv.getPrice() * bv.getOldRemainingCap()
						- bv.getNewPrice() * (numberOfCapsules - bv.getOldRemainingCap()));
				bv.swap();
				//bv.setRemainingCapsules(bv.getRemainingCapsules() - numberOfCapsules);
				amount = 0;// bv.getPrice() * bv.getOldRemainingCap() + bv.getNewPrice()*(numberOfCapsules
							// -bv.getOldRemainingCap())
				//setOldRemCap  (old-(numofca-old));
				bv.setOldRemainingCap(bv.getOldRemainingCap()-(numberOfCapsules - old));
			} else {
				em.setBalance(em.getBalance() - bv.getPrice() * (float) numberOfCapsules);
				//bv.setRemainingCapsules(bv.getRemainingCapsules() - numberOfCapsules);
				amount = 0;// numberOfCapsules * bv.getPrice();
				//setOldRemca
				bv.setOldRemainingCap(old-numberOfCapsules);
			}
			//bv.setRemainingCapsules(bv.getRemainingCapsules() - numberOfCapsules);
			this.updateE(em.getID(), em.getName(), em.getSurname(), em.getBalance());
		} else {
			type = "CASH";
			if (bv.getOldRemainingCap() < numberOfCapsules) {
				balance += bv.getPrice() * bv.getOldRemainingCap() + bv.getNewPrice()*(numberOfCapsules-bv.getOldRemainingCap());
				bv.swap();
				amount=0;//bv.getPrice() * bv.getOldRemainingCap() + bv.getNewPrice()*(numberOfCapsules-bv.getOldRemainingCap()); 
				bv.setOldRemainingCap(bv.getOldRemainingCap()-(numberOfCapsules - old));
			} else {
				//bv.setRemainingCapsules(bv.getRemainingCapsules() - numberOfCapsules);
				balance += ((float) bv.getPrice()) * numberOfCapsules;
				amount = 0;// numberOfCapsules * bv.getPrice();
				bv.setOldRemainingCap(old-numberOfCapsules);

			}
			//bv.setRemainingCapsules(bv.getRemainingCapsules() - numberOfCapsules);
		}
		
		bv.setRemainingCapsules(bv.getRemainingCapsules() - numberOfCapsules);
		this.updateB(bv.getID(), bv.getName(), bv.getOldRemainingCap(), bv.getPrice(), bv.getCapsulesPerBox(),
				bv.getNewPrice(), bv.getNewRemainingCap());

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(path, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		writer.println(balance);
		writer.close();

		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		this.insertT(em.getName() + " " + em.getSurname(), ts, type, bv.getName(), numberOfCapsules, amount);
		float ret = em.getBalance() * 100;
		return (int) ret;
	}

	@Override
	public void sellCapsulesToVisitor(Integer beverageId, Integer numberOfCapsules)
			throws BeverageException, NotEnoughCapsules {
		// int numberofcapsules=numberOfCapsules;
		if (!beverageMap.containsKey(beverageId)) {
			throw new BeverageException();
		}
		if (numberOfCapsules == null)
			throw new NotEnoughCapsules();
		if (beverageMap.get(beverageId).getRemainingCapsules() < numberOfCapsules || numberOfCapsules < 0) {
			throw new NotEnoughCapsules();
		}

		Beverage bv = beverageMap.get(beverageId);
		
		int old = bv.getOldRemainingCap();
		if (bv.getOldRemainingCap() <= numberOfCapsules) {
			balance += bv.getPrice() * bv.getOldRemainingCap() + bv.getNewPrice()*(numberOfCapsules-bv.getOldRemainingCap());
			bv.swap();
			bv.setOldRemainingCap(bv.getOldRemainingCap()-(numberOfCapsules - old));

			
		} else {
			balance += ((float) bv.getPrice()) * numberOfCapsules;
			bv.setOldRemainingCap(old-numberOfCapsules);
		}
		
		bv.setRemainingCapsules(bv.getRemainingCapsules() - numberOfCapsules);
		
		this.updateB(bv.getID(), bv.getName(), bv.getRemainingCapsules(), bv.getPrice(), bv.getCapsulesPerBox(),
				bv.getNewPrice(), bv.getNewRemainingCap());

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(path, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		writer.println(balance);
		writer.close();

		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		this.insertT(null, ts, "VISITOR", bv.getName(), numberOfCapsules, /* numberOfCapsules * bv.getPrice() */0);
	}

	@Override
	public Integer rechargeAccount(Integer id, Integer amountInCents) throws EmployeeException {
		if (!employeeMap.containsKey(id) || amountInCents < min) {
			throw new EmployeeException();
		}
		Employee em = employeeMap.get(id);

		em.setBalance(em.getBalance() + (float) amountInCents / 100);
		balance += ((float) amountInCents / 100);
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		this.insertT(em.getName() + " " + em.getSurname(), ts, "RECHARGE", null, 0, (float) amountInCents / 100);
		this.updateE(em.getID(), em.getName(), em.getSurname(), em.getBalance());
		return (int) (em.getBalance() * 100);
	}

	@Override
	public void buyBoxes(Integer beverageId, Integer boxQuantity) throws BeverageException, NotEnoughBalance {
		if (!beverageMap.containsKey(beverageId)) {
			throw new BeverageException();
		}
		if (beverageMap.get(beverageId).getBoxPrice() * boxQuantity > balance) {
			throw new NotEnoughBalance();
		}

		Beverage bv = beverageMap.get(beverageId);
		overflow = (float) boxQuantity * bv.getCapsulesPerBox();
		if (bv.isNewValuesset()) {
			bv.setNewRemainingCap(bv.getNewRemainingCap() + boxQuantity * bv.getCapsulesPerBox());

		} else {
			bv.setOldRemainingCap(bv.getOldRemainingCap() + boxQuantity * bv.getCapsulesPerBox());

		}

		if (overflow > max || overflow < min) {
			throw new BeverageException();
		}

		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		bv.setRemainingCapsules(bv.getRemainingCapsules() + bv.getCapsulesPerBox() * boxQuantity);
		this.updateB(bv.getID(), bv.getName(), bv.getRemainingCapsules(), bv.getPrice(), bv.getCapsulesPerBox(),
				bv.getNewPrice(), bv.getNewRemainingCap());
		this.insertT(null, ts, "BUY", bv.getName(), (int) boxQuantity, 0);
		balance -= bv.getBoxPrice() * boxQuantity;

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(path, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		writer.println(balance);
		writer.close();

		// updateBo(beverageId, bv.getCapsulesPerBox(), bv.getRemainingCapsules() /*in
		// realtà da prendere dalla lista nuova*/, bv.getBoxPrice(),bv.getTs());

	}

	@Override
	public List<String> getEmployeeReport(Integer employeeId, Date startDate, Date endDate)
			throws EmployeeException, DateException {
		if (startDate == null || endDate == null)
			throw new DateException();
		if (startDate.getTime() > endDate.getTime() || startDate.getTime() < 0 || endDate.getTime() < 0) {
			throw new DateException();
		}
		if (!employeeMap.containsKey(employeeId)) {
			throw new EmployeeException();
		}

		Employee em = employeeMap.get(employeeId);
		ArrayList<String> arr = new ArrayList<String>();
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");

		try {
			stmt = conn.createStatement();

			try {
				String query = "SELECT * FROM transactions WHERE Date <= '" + dt1.format(endDate)
						+ " 23:59:59' AND Date >= '" + dt1.format(startDate) + " 00:00:00' AND Employee = '"
						+ em.getName() + " " + em.getSurname()
						+ "' AND TypeOfTransaction <> 'BUY' AND TypeOfTransaction <> 'VISITOR'";
				;
				rset = stmt.executeQuery(query);
				try {
					while (rset.next()) {
						String date = rset.getDate("Date").toString() + " " + rset.getTime("Date").toString();
						String typeOfTransaction = rset.getString("TypeOfTransaction");
						String Employee;
						String beverageName;
						String quantity;
						String amount;

						if ((beverageName = rset.getString("BeverageName")) == null)
							beverageName = "";

						if ((Employee = rset.getString("Employee")) == null)
							Employee = "";

						if (rset.getInt("Quantity") != 0) {
							quantity = Integer.toString(rset.getInt("Quantity"));
						} else {
							quantity = "";
						}

						if (rset.getFloat("Amount") != 0) {
							amount = String.format("%.2f €", rset.getFloat("Amount"));
						} else {
							amount = "";
						}

						String i = date + " " + typeOfTransaction + " " + Employee + " " + beverageName + " " + quantity
								+ " " + amount;

						String f = i.replaceAll("^ +| +$|( )+", "$1");

						arr.add(f);
					}
				} finally {

				}
			} finally {

			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

		}

		return arr;
	}

	@Override
	public List<String> getReport(Date startDate, Date endDate) throws DateException {
		if (startDate == null || endDate == null)
			throw new DateException();
		if (startDate.getTime() > endDate.getTime() || startDate.getTime() < 0 || endDate.getTime() < 0) {
			throw new DateException();
		}

		ArrayList<String> arr = new ArrayList<String>();

		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			stmt = conn.createStatement();

			try {
				String query = "SELECT * FROM transactions WHERE Date <= '" + dt1.format(endDate)
						+ " 23:59:59' AND Date >= '" + dt1.format(startDate) + " 00:00:00'";
				rset = stmt.executeQuery(query);
				try {
					while (rset.next()) {

						String date = rset.getDate("Date").toString() + " " + rset.getTime("Date").toString();
						String typeOfTransaction = rset.getString("TypeOfTransaction");
						String Employee;
						String beverageName;
						String quantity;
						String amount;

						if ((beverageName = rset.getString("BeverageName")) == null)
							beverageName = "";

						if ((Employee = rset.getString("Employee")) == null)
							Employee = "";

						if (rset.getInt("Quantity") != 0) {
							quantity = Integer.toString(rset.getInt("Quantity"));
						} else {
							quantity = "";
						}

						if (rset.getFloat("Amount") != 0) {
							amount = String.format("%.2f €", rset.getFloat("Amount"));
						} else {
							amount = "";
						}

						String i = date + " " + typeOfTransaction + " " + Employee + " " + beverageName + " " + quantity
								+ " " + amount;

						String f = i.replaceAll("^ +| +$|( )+", "$1");

						arr.add(f);
					}
				} finally {

				}
			} finally {

			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

		}

		return arr;
	}

	@Override
	public Integer createBeverage(String name, Integer capsulesPerBox, Integer boxPrice) throws BeverageException {

		if (name == null || capsulesPerBox == null || boxPrice == null
				|| beverageMap.values().stream().anyMatch(v -> (v.getName().equals(name)))) {
			throw new BeverageException();
		}
		/*
		 * if(beverageMap.values().stream().anyMatch(v -> (v.getName().equals(name)))) {
		 * throw new BeverageException(); }
		 */
		Beverage bv = new Beverage(idGlobalBeverage, name, 0, (float) boxPrice / (capsulesPerBox * 100), capsulesPerBox,
				(float) boxPrice / 100);

		beverageMap.put(bv.getID(), bv);
		insertB(idGlobalBeverage, name, 0, (float) boxPrice / (capsulesPerBox * 100), capsulesPerBox, 0, 0);
		idGlobalBeverage++;

		return bv.getID();
	}

	@Override
	public void updateBeverage(Integer id, String name, Integer capsulesPerBox, Integer boxPrice)
			throws BeverageException {
		if (name == null || capsulesPerBox == null || boxPrice == null || !beverageMap.containsKey(id)
				|| (beverageMap.values().stream().anyMatch(v -> (v.getName().equals(name)))
						&& !beverageMap.get(id).getName().equals(name))) {
			throw new BeverageException();
		}
		Beverage bv = beverageMap.get(id);
		bv.setName(name);
		bv.setBoxPrice(boxPrice / 100);
		bv.setCapsulesPerBox(capsulesPerBox);

		if (bv.getRemainingCapsules() <= 0) {
			bv.setRemainingCapsules(0);// (bv.getRemainingCapsules());
			bv.setPrice((float) boxPrice / (capsulesPerBox * 100));
			updateB(id, name, bv.getRemainingCapsules(), (float) boxPrice / (capsulesPerBox * 100), capsulesPerBox, 0,
					0);// bv.getNewPrice(), bv.getRemainingCapsules());

		} else if(!bv.isNewValuesset()) {
			bv.setNewRemainingCap(0);
			bv.setNewPrice((float) boxPrice / (capsulesPerBox * 100));
			bv.setBoxPrice((float)boxPrice/100);
			bv.setFlagNewPrice();
			/*
			 * updateB(id, name, bv.getRemainingCapsules(), (float) boxPrice /
			 * (capsulesPerBox * 100), capsulesPerBox, bv.getNewPrice(),
			 * bv.getNewRemainingCap());
			 */
			updateB(id, name, bv.getRemainingCapsules(), bv.getPrice(), bv.getCapsulesPerBox(), bv.getNewPrice(),
					bv.getNewRemainingCap());
		}

		// Date date = new Date();
		// Timestamp ts = new Timestamp(date.getTime());
		// insertBo(id,capsulesPerBox,0,(float)(boxPrice/100),ts);
		// bv.setTs(ts);

	}

	@Override
	public String getBeverageName(Integer id) throws BeverageException {
		if (!beverageMap.containsKey(id)) {
			throw new BeverageException();
		}
		return beverageMap.get(id).getName();
	}

	@Override
	public Integer getBeverageCapsulesPerBox(Integer id) throws BeverageException {
		if (!beverageMap.containsKey(id)) {
			throw new BeverageException();
		}
		return beverageMap.get(id).getCapsulesPerBox();
	}

	@Override
	public Integer getBeverageBoxPrice(Integer id) throws BeverageException {
		if (!beverageMap.containsKey(id)) {
			throw new BeverageException();
		}

		return (int) beverageMap.get(id).getBoxPrice() * 100;
	}

	@Override
	public List<Integer> getBeveragesId() {
		return new ArrayList<Integer>(beverageMap.keySet());
	}

	@Override
	public Map<Integer, String> getBeverages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		for (Beverage i : beverageMap.values()) {
			hm.put(i.getID(), i.getName());
		}
		return hm;
	}

	@Override
	public Integer getBeverageCapsules(Integer id) throws BeverageException {
		if (!beverageMap.containsKey(id)) {
			throw new BeverageException();
		}
		return beverageMap.get(id).getRemainingCapsules();
	}

	@Override
	public Integer createEmployee(String name, String surname) throws EmployeeException {

		if (employeeMap.values().stream().map(x -> x.getName() + x.getSurname())
				.anyMatch(x -> x.equals(name + surname))) {

			throw new EmployeeException();
		}

		Employee em = new Employee(idGlobalEmployee, name, surname, 0);

		employeeMap.put(em.getID(), em);
		insertE(idGlobalEmployee, name, surname, (float) 0);
		idGlobalEmployee++;
		return em.getID();
	}

	@Override
	public void updateEmployee(Integer id, String name, String surname) throws EmployeeException {
		if (name == null || surname == null || name == "" || surname == "")
			throw new EmployeeException();
		if (!employeeMap.containsKey(id)) {
			throw new EmployeeException();
		}
		Employee em = employeeMap.get(id);
		em.setSurname(surname);
		em.setName(name);

		updateE(id, name, surname, (float) 0);

	}

	@Override
	public String getEmployeeName(Integer id) throws EmployeeException {
		if (!employeeMap.containsKey(id)) {
			throw new EmployeeException();
		}

		return employeeMap.get(id).getName();
	}

	@Override
	public String getEmployeeSurname(Integer id) throws EmployeeException {
		if (!employeeMap.containsKey(id)) {
			throw new EmployeeException();
		}

		return employeeMap.get(id).getSurname();
	}

	@Override
	public Integer getEmployeeBalance(Integer id) throws EmployeeException {

		if (!employeeMap.containsKey(id)) {
			throw new EmployeeException();
		}

		return (int) (employeeMap.get(id).getBalance() * 100);
	}

	@Override
	public List<Integer> getEmployeesId() {
		return new ArrayList<Integer>(employeeMap.keySet());
	}

	@Override
	public Map<Integer, String> getEmployees() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		for (Employee i : employeeMap.values()) {
			hm.put(i.getID(), i.getName() + " " + i.getSurname());
		}

		return hm;
	}

	@Override
	public Integer getBalance() {
		return (int) (balance * 100);
	}

	@Override
	public void reset() {
		this.balance = 0;
		this.employeeMap.clear();
		this.beverageMap.clear();
		this.idGlobalEmployee = 1;
		this.idGlobalBeverage = 1;

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(path, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		writer.println(balance);
		writer.close();

		String query1 = "DELETE  FROM employee";
		String query2 = "DELETE  FROM beverage";
		String query3 = "DELETE  FROM transactions";

		try {
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.execute();
			PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			preparedStmt2.execute();
			PreparedStatement preparedStmt3 = conn.prepareStatement(query3);
			preparedStmt3.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
