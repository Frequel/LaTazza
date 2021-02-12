package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.*;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;

public class systemAcceptanceTests {

	private DataImpl dataImpTest;

	@BeforeEach
	public void setUp() {
		try {
			dataImpTest = new DataImpl();
		} catch (SQLException e) {
			fail("errror loading the database");
		}

	}

	@AfterEach
	public void tearDown() {
		dataImpTest.reset();
	}

	@Test
	public void testSC1() {

		int idE;
		try {
			idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idB = dataImpTest.createBeverage("cocacola", 100, 100);
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(idB, 1);

			dataImpTest.sellCapsules(idE, idB, 10, true);

			// check the account of employees is updated and the number of capsules have
			// been
			// updated

			assertEquals(99990, (int) dataImpTest.getEmployeeBalance(idE), "wrong employee balance");
			assertEquals(90, (int) dataImpTest.getBeverageCapsules(idB), "wrong update of number of capsules");

		} catch (EmployeeException e) {
			fail("employee exception");
		} catch (BeverageException e) {
			fail("beverage exception");
		} catch (NotEnoughBalance e) {
			fail("not enough balance exception");

		} catch (NotEnoughCapsules e) {
			fail("not enough capsules  exception");

		}
	}

	@Test
	public void testSC1a() {
		int idE, idE1;
		try {
			idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			idE1 = dataImpTest.createEmployee("Paolo", "Rossi");
			int idB = dataImpTest.createBeverage("cocacola", 100, 100);
			dataImpTest.rechargeAccount(idE1, 1000);
			dataImpTest.rechargeAccount(idE, 2);
			dataImpTest.buyBoxes(idB, 1);

			dataImpTest.sellCapsules(idE, idB, 10, true);

			// check the account of employees is updated and the number of capsules have
			// been
			// updated

			assertEquals(-8, (int) dataImpTest.getEmployeeBalance(idE), "wrong employee balance");
			assertEquals(90, (int) dataImpTest.getBeverageCapsules(idB), "wrong update of number of capsules");

		} catch (EmployeeException e) {
			fail("employee exception");
		} catch (BeverageException e) {
			fail("beverage exception");
		} catch (NotEnoughBalance e) {
			fail("not enough balance exception");

		} catch (NotEnoughCapsules e) {
			fail("not enough capsules  exception");

		}
	}

	@Test
	public void testSC5() {

		try {

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
		}

	}

	@Test
	public void testSC6() {

		try {

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
		}

	}

	@Test
	public void testSC7() {

		try {
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 2000);
			int bevid = dataImpTest.createBeverage("fanta", 20, 200);
			dataImpTest.buyBoxes(bevid, 1);
			int pre_balance = dataImpTest.getBalance();
			dataImpTest.sellCapsulesToVisitor(bevid, 5);
			int post_balance = dataImpTest.getBalance();
			assertTrue(post_balance > pre_balance, "Wrong cash account");
			assertTrue(dataImpTest.getBeverageCapsules(bevid) == 15, "Wrong number of capsules");

		} catch (BeverageException e) {
			fail("beverage does not exist");
		} catch (NotEnoughCapsules e) {
			fail("not enough capsules to sell ");
		} catch (NotEnoughBalance e) {
			fail("not enough balance to buy boxex");
		} catch (EmployeeException e) {
			fail("employee exception");
		}
	}

}
