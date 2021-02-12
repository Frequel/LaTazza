package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.Date;

import org.junit.jupiter.api.*;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;

class TestNFR2 {
	
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
	public void testNFR2inFR1() {

		int idE;
		double start_time,end_time,avg=0;
		int i, attempts = 100;
		try {
			idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idB = dataImpTest.createBeverage("cocacola", 1000, 1000);
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.getBalance();
			
			for(i=1; i<attempts; i++) {
			start_time=System.currentTimeMillis();
			dataImpTest.sellCapsules(idE, idB, 10, true);
			end_time=System.currentTimeMillis();
			avg += end_time - start_time;
			}
			avg = avg/attempts;
			assertTrue(avg<500,"perfomance test not respected");
			
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
	public void testNFR2inFR2() {
		int idE, i, attempts = 100;
		double start_time, end_time, avg=0;
		try {
			idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idB = dataImpTest.createBeverage("cocacola", 1000, 1000);
			dataImpTest.rechargeAccount(idE, 1000);
			dataImpTest.buyBoxes(idB, 1);

			for(i=1; i<attempts; i++) {
				start_time=System.currentTimeMillis();
				dataImpTest.sellCapsulesToVisitor(idB, 10);
				end_time=System.currentTimeMillis();
				avg += end_time - start_time;
				}
			avg = avg/attempts;
			assertTrue(avg<500,"perfomance test not respected");

		} catch (EmployeeException e) {
			fail("employee exception");
		} catch (BeverageException e) {
			fail("beverage exception");
		} catch (NotEnoughBalance e) {
			fail("not enough balance exception");

		} catch (NotEnoughCapsules e) {
			fail("not enough capsules  exception");

		}	}

	@Test
	public void testNFR2inFR3() {
		
		int idE, i, attempts = 100;
		double start_time, end_time, avg=0;
	try {
		idE = dataImpTest.createEmployee("Leonardo", "Davinci");
		
		for(i=1; i<attempts; i++) {
			start_time=System.currentTimeMillis();
			dataImpTest.rechargeAccount(idE, 10);
			end_time=System.currentTimeMillis();
			avg += end_time - start_time;
			}
		avg = avg/attempts;
		assertTrue(avg<500,"perfomance test not respected");

		} catch (EmployeeException e) {
			fail("employee exception");
		}
	

	}

	@Test
	public void testNFR2inFR4() {
		int i, attempts = 100;
		double start_time, end_time, avg=0;
		try {
			
			int idE=dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 200000);
			int idB = dataImpTest.createBeverage("fanta", 20, 200);
			for(i=1; i<attempts; i++) {
				start_time=System.currentTimeMillis();
				dataImpTest.buyBoxes(idB, 1);
				end_time=System.currentTimeMillis();
				avg += end_time - start_time;
				}
			avg = avg/attempts;
			assertTrue(avg<500,"perfomance test not respected");

		} catch (BeverageException e) {
			fail("beverage does not exist");
		}  catch (NotEnoughBalance e) {
			fail("not enough balance to buy boxex");
		} catch (EmployeeException e) {
			fail("employee exception");
		}
	
	}

	@Test
	public void testNFR2inFR5() { 
		int i, attempts = 100;
		double start_time, end_time, avg=0;
		try {
			Date date = new Date();
			
			int idE=dataImpTest.createEmployee("Leonardo", "Davinci");
			int idE1 = dataImpTest.createEmployee("Paolo","Rossi");
			dataImpTest.rechargeAccount(idE1,1000);
			dataImpTest.rechargeAccount(idE, 200000);
			int idB = dataImpTest.createBeverage("fanta", 20, 200);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.sellCapsulesToVisitor(idB, 5);
			dataImpTest.sellCapsules(idE, idB, 10, true);
			Date date1 = new Date();
			for(i=1; i<attempts; i++) {
				start_time=System.currentTimeMillis();
				dataImpTest.getEmployeeReport(idE1,date,date1);
				end_time=System.currentTimeMillis();
				avg += end_time - start_time;
				}
			avg = avg/attempts;
			assertTrue(avg<500,"perfomance test not respected");
			
		}  catch (BeverageException e) {
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
	public void testNFR2inFR6() { 
		int i, attempts = 100;
		double start_time, end_time, avg=0;
		try {
			Date date = new Date();
			
			int idE=dataImpTest.createEmployee("Leonardo", "Davinci");
			int idE1 = dataImpTest.createEmployee("Paolo","Rossi");
			dataImpTest.rechargeAccount(idE1,1000);
			dataImpTest.rechargeAccount(idE, 200000);
			int idB = dataImpTest.createBeverage("fanta", 20, 200);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.sellCapsulesToVisitor(idB, 5);
			dataImpTest.sellCapsules(idE, idB, 10, true);
			Date date1 = new Date();
			for(i=1; i<attempts; i++) {
				start_time=System.currentTimeMillis();
				dataImpTest.getReport(date,date1);
				end_time=System.currentTimeMillis();
				avg += end_time - start_time;
				}
			avg = avg/attempts;
			assertTrue(avg<500,"perfomance test not respected");
			
		}  catch (BeverageException e) {
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

}
