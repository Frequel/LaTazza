package it.polito.latazza.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;

public class TestDataImplWB {

	private DataImpl dataImpTest;

	protected void setUp() throws SQLException {
		dataImpTest = new DataImpl();

	}

	protected void tearDown() {
		dataImpTest.reset();
	}

	@Test
	public void testGetBalance() {
		try {
			setUp();

			assertTrue(dataImpTest.getBalance() >= 0, "Wrong balance");

		} catch (SQLException e) {
			fail("erorr connecting to database");
		}
		this.tearDown();
	}

	@Test
	public void testCreateEmployee() {

		try {
			setUp();
			int id = dataImpTest.createEmployee("Leonardo", "DaVinci");
			assertTrue(dataImpTest.getEmployees().containsKey(id), "employee not created");

		} catch (SQLException e) {
			fail("erorr connecting to database");
		} catch (EmployeeException e) {
			fail("erorr employee already exists into the database");
		}

		// handling the error case
		try {
			dataImpTest.createEmployee("Leonardo", "DaVinci");
			fail("Not recognized that the employee already exists");
		} catch (EmployeeException e) {

		} finally {
			tearDown();
		}
	}

	@Test
	public void testUpdateEmployee() {

		try {
			setUp();
			int id = dataImpTest.createEmployee("Leonardo", "DaVinci");
			dataImpTest.updateEmployee(id, "Francesco", "DaVinci");

		} catch (SQLException e) {
			fail("erorr connecting to database");
		} catch (EmployeeException e) {
			fail("erorr employee already exists into the database");
		}

		// passing the wrong id
		try {
			setUp();
			int id = dataImpTest.createEmployee("Leonardo", "DaVinci");
			dataImpTest.updateEmployee(id - 110000, "Franesco", "Arciderbolina");
			fail("Updated the wrong id");

		} catch (SQLException e) {
			fail("erorr connecting to database");
		} catch (EmployeeException e) {

		} finally {
			tearDown();
		}
	}

	@Test
	public void testSellCapsulesToVisitors() {

		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 200000);
			int bevid = dataImpTest.createBeverage("fanta", 20, 200);
			dataImpTest.buyBoxes(bevid, 1);
			dataImpTest.sellCapsulesToVisitor(bevid, 5);

			assertTrue(dataImpTest.getBeverageCapsules(bevid) == 15, "Wrong number of capsules");

		} catch (SQLException e) {
			fail("error connecting to db");
		} catch (BeverageException e) {
			fail("beverage does not exist");
		} catch (NotEnoughCapsules e) {
			fail("not enough capsules to sell ");
		} catch (NotEnoughBalance e) {
			fail("not enough balance to buy boxex");
		} catch (EmployeeException e) {
			fail("employee exception");
		} finally {
			tearDown();
		}

		// handling the exception correctly

		try {
			setUp();

			int bevid = dataImpTest.createBeverage("fanta", 20, 100);
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 1000);
			dataImpTest.createBeverage("coca", 1, 1000);
			dataImpTest.buyBoxes(bevid, 1);

			dataImpTest.sellCapsulesToVisitor(bevid, 50);

			fail("not enough capusles expception shloud be generated");
		} catch (SQLException e) {
			fail("error connecting to db");
		} catch (BeverageException e) {
			fail("beverage does not exist");
		} catch (NotEnoughCapsules e) {

		} catch (NotEnoughBalance e) {
			fail("not enough balance to buy boxex");
		} catch (EmployeeException e) {
			fail("Employee exception");
		} finally {
			tearDown();
		}

		try {
			setUp();

			int bevid = dataImpTest.createBeverage("fanta", 20, 100);
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 200000);
			dataImpTest.createBeverage("coca", 1, 1000);
			dataImpTest.buyBoxes(bevid, 1);

			dataImpTest.sellCapsulesToVisitor(bevid + 10000, 50);
			fail("the beverage should not exist, an exception should be thorwn");
		} catch (SQLException e) {
			fail("error connecting to db");
		} catch (BeverageException e) {

		} catch (NotEnoughCapsules e) {
			fail("not enough capsules to sell ");
		} catch (NotEnoughBalance e) {
			fail("not enough balance to buy boxex");
		} catch (EmployeeException e) {
			fail("Employee exception");
		} finally {
			tearDown();
		}
	}

	@Test
	public void testLoopCoverage1() {

		try {
			setUp();
			assertTrue(dataImpTest.getEmployees().keySet().size() == 0, "Wrong loop coverege expected is 0");

		} catch (SQLException e) {
			fail("erorr connecting to database");
		} finally {
			tearDown();
		}
	}

	@Test
	public void testLoopCoverage2() {
		try {
			setUp();
			dataImpTest.createEmployee("Mario", "rossi");
			dataImpTest.createEmployee("Giovanni", "Antonio");
			dataImpTest.createEmployee("Antonio", "Maria");
			dataImpTest.createEmployee("Francesco", "Antonio");
			dataImpTest.createEmployee("Calogero", "Antonio");

			assertTrue(dataImpTest.getEmployees().keySet().size() == 5, "Wrong loop coverege expected is 5");

		} catch (SQLException e) {
			fail("erorr connecting to database");
		} catch (EmployeeException e) {
			fail("There is already that employee");
		} finally {
			tearDown();
		}
	}

	@Test
	public void testLoopCoverage3() {

		try {
			setUp();
			assertTrue(dataImpTest.getEmployees().keySet().size() == 0, "Wrong loop coverege expected is 0");

		} catch (SQLException e) {
			fail("erorr connecting to database");
		} finally {
			tearDown();
		}
	}

	@Test
	public void testLoopCoverage4() {

		try {
			setUp();
			dataImpTest.createBeverage("fanta", 20, 2000);

			assertTrue(dataImpTest.getBeverages().keySet().size() == 1, "Wrong loop coverege expected is 1");

		} catch (SQLException e) {
			fail("erorr connecting to database");
		} catch (BeverageException e) {
			fail("Beverage already exists");

		} finally {
			tearDown();
		}
	}

	@Test
	public void testLoopCoverage5() {
		try {
			setUp();
			dataImpTest.createBeverage("fanta", 20, 2000);
			dataImpTest.createBeverage("coca", 20, 2000);
			dataImpTest.createBeverage("wine", 20, 2000);
			dataImpTest.createBeverage("juice", 20, 2000);
			dataImpTest.createBeverage("caffe", 20, 2000);

			assertTrue(dataImpTest.getBeverages().keySet().size() == 5, "Wrong loop coverege expected is 5");

		} catch (SQLException e) {
			fail("erorr connecting to database");
		} catch (BeverageException e) {
			fail("Beverage already exists");

		} finally {
			tearDown();
		}
	}

	@Test
	public void testGetEmployeeReport() {
		try {
			setUp();

			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idE1 = dataImpTest.createEmployee("Paolo", "Rossi");
			int idB = dataImpTest.createBeverage("fanta", 20, 200);
			Date date = new Date();
			dataImpTest.rechargeAccount(idE1, 1000);
			dataImpTest.rechargeAccount(idE, 200000);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.sellCapsulesToVisitor(idB, 5);
			dataImpTest.sellCapsules(idE, idB, 10, true);
			Date date1 = new Date();
			List<String> report = dataImpTest.getEmployeeReport(idE1, date, date1);
			String joined = String.join("\n", report);


			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			assertTrue(joined.matches(dt1.format(date)+".+" + " RECHARGE Paolo Rossi 10.00 €"),"Wrong report");
	} catch (BeverageException e) {
			fail("beverage does not exist");
		} catch (NotEnoughCapsules e) {
			fail("not enough capsules to sell ");
		} catch (NotEnoughBalance e) {
			fail("not enough balance to buy boxex");
		} catch (EmployeeException e) {
			fail("employee exception");
		} catch (DateException e) {
			fail("date exception, date is not correct");
		} catch (SQLException e) {
			fail("error connecting to database");
		} finally {
			tearDown();
		}

		// handling exception

		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idE1 = dataImpTest.createEmployee("Paolo", "Rossi");
			int idB = dataImpTest.createBeverage("fanta", 20, 200);
			Date date = new Date();
			dataImpTest.rechargeAccount(idE1, 1000);
			dataImpTest.rechargeAccount(idE, 200000);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.sellCapsulesToVisitor(idB, 5);
			dataImpTest.sellCapsules(idE, idB, 10, true);
			Date date1 = new Date();

			List<String> report = dataImpTest.getEmployeeReport(1000, date, date1);
			fail("employee exception not generated");
		} catch (BeverageException e) {
			fail("beverage does not exist");
		} catch (NotEnoughCapsules e) {
			fail("not enough capsules to sell ");
		} catch (NotEnoughBalance e) {
			fail("not enough balance to buy boxex");
		} catch (EmployeeException e) {

		} catch (DateException e) {
			fail("date exception, date is not correct");
		} catch (SQLException e) {
			fail("error connecting to database");
		} finally {
			tearDown();
		}
		try {
			setUp();
			Date date = new Date();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idE1 = dataImpTest.createEmployee("Paolo", "Rossi");
			dataImpTest.rechargeAccount(idE1, 1000);
			dataImpTest.rechargeAccount(idE, 200000);
			int idB = dataImpTest.createBeverage("fanta", 20, 200);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.sellCapsulesToVisitor(idB, 5);
			dataImpTest.sellCapsules(idE, idB, 10, true);
			Date date1 = new Date();

			List<String> report = dataImpTest.getEmployeeReport(idE1, date1, date);
			fail("date exception not generated");
		} catch (BeverageException e) {
			fail("beverage does not exist");
		} catch (NotEnoughCapsules e) {
			fail("not enough capsules to sell ");
		} catch (NotEnoughBalance e) {
			fail("not enough balance to buy boxex");
		} catch (EmployeeException e) {
			fail("employee exception");

		} catch (DateException e) {

		} catch (SQLException e) {
			fail("error connecting to database");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testGetReport() {

		try {
			setUp();
			Date date1 = new Date();

			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");

			Date date2 = new Date();
			dataImpTest.rechargeAccount(idE, 200000);

			int idB = dataImpTest.createBeverage("fanta", 20, 200);

			Date date3 = new Date();
			dataImpTest.buyBoxes(idB, 1);

			Date date4 = new Date();
			dataImpTest.sellCapsulesToVisitor(idB, 5);

			Date date5 = new Date();
			dataImpTest.sellCapsules(idE, idB, 10, true);

			Date date6 = new Date();

			List<String> report = dataImpTest.getReport(date1, date6);
			String joined = String.join("\n", report);

			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			
			assertTrue(joined.matches(dt1.format(date2)+".+" + " RECHARGE Leonardo Davinci 2000.00 €\n" + dt1.format(date3)+".+"
					+ " BUY fanta 1\n" + dt1.format(date4)+".+" + " VISITOR fanta 5\n" + dt1.format(date5)+".+"
					+ " BALANCE Leonardo Davinci fanta 10"),"Wrong report" );
		
		} catch (BeverageException e) {
			fail("beverage does not exist");
		} catch (NotEnoughCapsules e) {
			fail("not enough capsules to sell ");
		} catch (NotEnoughBalance e) {
			fail("not enough balance to buy boxex");
		} catch (EmployeeException e) {
			fail("employee exception");
		} catch (DateException e) {
			fail("date exception, date is not correct");
		} catch (SQLException e) {
			fail("error connecting to db");
		} finally {
			tearDown();
		}

		// exception handling
		try {
			setUp();
			Date date1 = new Date();

			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");

			dataImpTest.rechargeAccount(idE, 200000);

			int idB = dataImpTest.createBeverage("fanta", 20, 200);

			dataImpTest.buyBoxes(idB, 1);

			dataImpTest.sellCapsulesToVisitor(idB, 5);

			dataImpTest.sellCapsules(idE, idB, 10, true);

			Date date6 = new Date();

			List<String> report = dataImpTest.getReport(date6, date1);
			fail("date exeption not generated");
		} catch (BeverageException e) {
			fail("beverage does not exist");
		} catch (NotEnoughCapsules e) {
			fail("not enough capsules to sell ");
		} catch (NotEnoughBalance e) {
			fail("not enough balance to buy boxex");
		} catch (EmployeeException e) {
			fail("employee exception");
		} catch (DateException e) {

		} catch (SQLException e) {
			fail("error connecting to db");
		} finally {
			tearDown();
		}
	}
	
	@Test
	public void testSellCapsulesToVisitorsNewPrice() {

		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 2000);
			int bevid = dataImpTest.createBeverage("fanta", 20, 200);
			dataImpTest.buyBoxes(bevid, 1);
			dataImpTest.updateBeverage(bevid, "fanta", 20, 100);
			dataImpTest.buyBoxes(bevid, 1);
			dataImpTest.sellCapsulesToVisitor(bevid, 25);

			assertTrue(dataImpTest.getBeverageCapsules(bevid) == 15, "Wrong number of capsules");
			assertEquals(1925,dataImpTest.getBalance(),"wrong new box price");
		} catch (SQLException e) {
			fail("error connecting to db");
		} catch (BeverageException e) {
			fail("beverage does not exist");
		} catch (NotEnoughCapsules e) {
			fail("not enough capsules to sell ");
		} catch (NotEnoughBalance e) {
			fail("not enough balance to buy boxex");
		} catch (EmployeeException e) {
			fail("employee exception");
		} finally {
			tearDown();
		}


	
	}
	
	@Test 
	public void testSellCapsulesNewPrice() {
		
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idB = dataImpTest.createBeverage("cocacola", 1, 100);
			dataImpTest.rechargeAccount(idE, 1000);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.updateBeverage(idB, "cocacola", 2, 100);
			dataImpTest.buyBoxes(idB, 1);

			dataImpTest.sellCapsules(idE, idB, 3, true);

			assertTrue(dataImpTest.getBeverageCapsules(idB) == 0, "Wrong number of capsules");
			assertEquals(800, dataImpTest.getBalance(), "Wrong to sell capsules");

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} catch (EmployeeException e) {
			fail("unable to create employee");
		} catch (BeverageException e) {
			fail("unable to create beverage");
		}catch (NotEnoughBalance e) {
			fail("unable to buy boxes");
		} catch (NotEnoughCapsules e) {
			fail("unable to sell capsules");
		} finally {
			tearDown();
		}

		
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idB = dataImpTest.createBeverage("cocacola", 1, 100);
			dataImpTest.rechargeAccount(idE, 1000);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.updateBeverage(idB, "cocacola", 20, 100);
			dataImpTest.buyBoxes(idB, 1);

			dataImpTest.sellCapsules(idE, idB, 3, false);

		assertTrue(dataImpTest.getBeverageCapsules(idB) == 18, "Wrong number of capsules");
		assertEquals(910, dataImpTest.getBalance(), "Wrong to sell capsules");

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} catch (EmployeeException e) {
			fail("unable to create employee");
		} catch (BeverageException e) {
			fail("unable to create beverage");
		} catch (NotEnoughBalance e) {
			fail("unable to buy boxes");
		} catch (NotEnoughCapsules e) {
			fail("unable to sell capsules");
		} finally {
			tearDown();
		}
		
	}

}