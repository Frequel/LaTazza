package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;

public class TestDataImpl {

	private DataImpl dataImpTest;
	
	@BeforeEach
	protected void setUp() throws SQLException {
		dataImpTest = new DataImpl();

	}
	
	@AfterEach
	protected void tearDown() {
		dataImpTest.reset();
	}

	@Test
	public void testGetBeverageCapsules1() {

		try {
			//setUp();
			int bvid = dataImpTest.createBeverage("fanta", 2, 200);
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(bvid, 3);
			assertEquals((Integer) 6, dataImpTest.getBeverageCapsules(bvid), "Wrong number of capsules");
		} catch (BeverageException e) {
			fail("Beverage Exception");
		/*} catch (SQLException e) {
			fail("Sql exception opening db");*/
		} catch (NotEnoughBalance e) {
			fail("fail to update balance");
		} catch (EmployeeException e) {
			fail("Failed to create employee");
		} /*finally {
			tearDown();
		}*/
	}

	@Test
	public void testGetBeverageCapsules2() {
		int bvid = 1;
		try {
			setUp();
			bvid = dataImpTest.createBeverage("fanta", 1, 200);
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(bvid, 3);
			dataImpTest.getBeverageCapsules(0);
			fail("exception not generated");

		} catch (BeverageException e) {

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} catch (NotEnoughBalance e) {
			fail("fail to update balance");
		} catch (EmployeeException e) {
			fail("Failed to create employee");
		} finally {
			tearDown();
		}
	}

	@Test
	public void testGetBeverageCapsules3() {
		int bvid = 1;
		try {
			setUp();
			bvid = dataImpTest.createBeverage("fanta", Integer.MAX_VALUE, 200);
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(bvid, 3);
			fail("exception not generated");

		} catch (BeverageException e) {
			try {
				assertEquals((Integer) 0, dataImpTest.getBeverageCapsules(bvid), "Wrong number of capsules");
			} catch (BeverageException e1) {
				fail("Beverage Exception");

			}
		} catch (SQLException e) {
			fail("Sql exception opening db");
		} catch (NotEnoughBalance e) {
			fail("fail to update balance");
		} catch (EmployeeException e) {
			fail("Failed to create employee");
		} finally {
			tearDown();
		}
	}

	@Test
	public void testGetBeverageCapsules4() {
		int bvid = 1;
		try {
			setUp();
			bvid = dataImpTest.createBeverage("fanta", 5, 20);
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 100000000);
			dataImpTest.buyBoxes(bvid, Integer.MAX_VALUE);
			fail("Exception not generated");

		} catch (BeverageException e) {

			

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} catch (NotEnoughBalance e) {
			try {
				assertEquals((Integer) 0, dataImpTest.getBeverageCapsules(bvid), "Wrong number of capsules");
			} catch (BeverageException e1) {
				fail("Beverage Exception");
			}
		} catch (EmployeeException e) {
			fail("Failed to create employee");
		} finally {
			tearDown();
		}
	}

	@Test
	public void testGetBeverageCapsules5() {
		int bvid = 1;
		try {
			setUp();
			bvid = dataImpTest.createBeverage("fanta", 2, 200);
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(bvid, -2);
			fail("Exception not generated");

		} catch (BeverageException e) {
			try {
				assertEquals((Integer) 0, dataImpTest.getBeverageCapsules(bvid), "Wrong number of capsules");
			} catch (BeverageException e1) {
				fail("Beverage Exception");
			}
		} catch (SQLException e) {
			fail("Sql exception opening db");
		} catch (NotEnoughBalance e) {
			fail("fail to update balance");
		} catch (EmployeeException e) {
			fail("Failed to create employee");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testGetBeverageCapsules6() {
		int bvid = 1;
		try {
			setUp();
			bvid = dataImpTest.createBeverage("fanta", 2, 200);
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(bvid, Integer.MIN_VALUE);
			fail("Exception not generated");

		} catch (BeverageException e) {
			try {
				assertEquals((Integer) 0, dataImpTest.getBeverageCapsules(bvid), "Wrong number of capsules");
			} catch (BeverageException e1) {
				fail("Beverage Exception");
			}
		} catch (SQLException e) {
			fail("Sql exception opening db");
		} catch (NotEnoughBalance e) {
			fail("fail to update balance");
		} catch (EmployeeException e) {
			fail("Failed to create employee");

		} finally {
			tearDown();
		}

	}

	@Test
	public void testUpdateBeverage1() {
		try {
			setUp();
			int bvid = dataImpTest.createBeverage("fanta", 50, 200);
			dataImpTest.updateBeverage(bvid, "fanta", 10, 200);
			assertEquals((Integer) 0, dataImpTest.getBeverageCapsules(bvid), "Wrong number of capsules");
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(bvid, 10);
			assertEquals((Integer) 100, dataImpTest.getBeverageCapsules(bvid), "Wrong number of capsules");
		} catch (BeverageException e) {
			fail("Beverage Exception");
		} catch (SQLException e) {
			fail("Sql exception opening db");
		} catch (NotEnoughBalance e) {
			fail("not enough balance");
		} catch (EmployeeException e) {
			fail("impossible to create employee");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testUpdateBeverage2() {
		try {
			setUp();
			int bvid = dataImpTest.createBeverage("fanta", 50, 200);
			dataImpTest.updateBeverage(bvid, "fan12", 10, 200);
			fail("Beverage Exception not generated");

		} catch (BeverageException e) {

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testUpdateBeverage3() {
		try {
			setUp();
			int bvid = dataImpTest.createBeverage("fanta", 50, 200);
			dataImpTest.updateBeverage(bvid, "fanta", Integer.MAX_VALUE + 1, 200);
			fail("Beverage Exception not generated");
		} catch (BeverageException e) {

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testUpdateBeverage4() {
		try {
			setUp();
			int bvid = dataImpTest.createBeverage("fanta", 50, 200);
			dataImpTest.updateBeverage(bvid, "fan12", Integer.MAX_VALUE + 1, 200);
			fail("Beverage Exception not generated");
		} catch (BeverageException e) {

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testUpdateBeverage5() {
		try {
			setUp();
			int bvid = dataImpTest.createBeverage("fanta", 50, 200);
			dataImpTest.updateBeverage(bvid, "fanta", -35, 200);
			fail("Beverage Exception not generated");
		} catch (BeverageException e) {

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testUpdateBeverage6() {
		try {
			setUp();
			int bvid = dataImpTest.createBeverage("fanta", 50, 200);
			dataImpTest.updateBeverage(bvid, "fan12", Integer.MAX_VALUE + 1, 200);
			fail("Beverage Exception not generated");
		} catch (BeverageException e) {

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testUpdateBeverage7() {
		try {
			setUp();
			int bvid = dataImpTest.createBeverage("fanta", 50, 200);
			dataImpTest.updateBeverage(bvid, "fanta", Integer.MAX_VALUE + 1, 200);
			fail("Beverage Exception not generated");
		} catch (BeverageException e) {

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} finally {
			tearDown();
		}
	}

	@Test
	public void testUpdateBeverage8() {
		try {
			setUp();
			int bvid = dataImpTest.createBeverage("fanta", 50, 200);
			dataImpTest.updateBeverage(bvid, "fan12", 100, Integer.MAX_VALUE + 1);
			fail("Beverage Exception not generated");
		} catch (BeverageException e) {

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} finally {
			tearDown();
		}
	}

	@Test
	public void testUpdateBeverage9() {
		try {
			setUp();
			int bvid = dataImpTest.createBeverage("fanta", 50, 200);
			dataImpTest.updateBeverage(bvid, "fanta", 100, -3);
			fail("Beverage Exception not generated");
		} catch (BeverageException e) {

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} finally {
			tearDown();
		}
	}

	@Test
	public void testUpdateBeverage10() {
		try {
			setUp();
			int bvid = dataImpTest.createBeverage("fanta", 50, 200);
			dataImpTest.updateBeverage(bvid, "fan12", 100, -3);
			fail("Beverage Exception not generated");
		} catch (BeverageException e) {
		} catch (SQLException e) {
			fail("Sql exception opening db");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testUpdateBeverage11() {
		try {
			setUp();
			int bvid = dataImpTest.createBeverage("fanta", 50, 200);
			dataImpTest.updateBeverage(bvid, "fanta", 100, Integer.MAX_VALUE + 1);
			fail("Beverage Exception not generated");
		} catch (BeverageException e) {
		} catch (SQLException e) {
			fail("Sql exception opening db");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testUpdateBeverage12() {
		try {
			setUp();
			int bvid = dataImpTest.createBeverage("fanta", 50, 200);
			dataImpTest.updateBeverage(bvid, "fan12", 100, Integer.MAX_VALUE + 1);
			fail("Beverage Exception not generated");
		} catch (BeverageException e) {

		} catch (SQLException e) {
			fail("Sql exception opening db");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testRechargeAccount1() {
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int updated = dataImpTest.rechargeAccount(idE, 2000);

			assertEquals(2000, updated, "Wrong updated amount");

		} catch (SQLException e) {
			fail("Sql exception opening db");

		} catch (EmployeeException e) {
			fail("unable to create employee");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testRechargeAccount2() {
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int updated = dataImpTest.rechargeAccount(idE, 0);

			assertEquals(0, updated, "Wrong updated amount");

		} catch (SQLException e) {
			fail("Sql exception opening db");

		} catch (EmployeeException e) {
			fail("unable to create employee");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testRechargeAccount3() {
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int updated = dataImpTest.rechargeAccount(idE, Integer.MAX_VALUE);

			assertEquals((float) Integer.MAX_VALUE, updated, "Wrong updated amount");

		} catch (SQLException e) {
			fail("Sql exception opening db");

		} catch (EmployeeException e) {
			fail("unable to create employee");
		} finally {
			tearDown();
		}
	}

	@Test
	public void testRechargeAccount4() {
		int idE = 1;
		try {
			setUp();
			idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			//int updated = dataImpTest.rechargeAccount(idE, -35);
			dataImpTest.rechargeAccount(idE, -35);

			//assertEquals(-35, updated, "Wrong updated amount");
			fail("exception not generated");
			
		} catch (SQLException e) {
			fail("Sql exception opening db");
			//assertEquals(0, dataImpTest.getEmployeeBalance(idE), "Wrong updated amount");
		} catch (EmployeeException e) {
			
		} finally {
			tearDown();
		}

	}

	@Test
	public void testRechargeAccount5() {
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			//int updated = dataImpTest.rechargeAccount(idE, Integer.MIN_VALUE);
			dataImpTest.rechargeAccount(idE, Integer.MIN_VALUE);
			// the float rounds the value
			//assertEquals((float) Integer.MIN_VALUE, updated, "Wrong updated amount");
			fail("exception not generated");

		} catch (SQLException e) {
			fail("Sql exception opening db");

		} catch (EmployeeException e) {
			//fail("unable to create employee");
		} finally {
			tearDown();
		}

	}

	@Test
	public void testSellCapsules1() {
		try {
			tearDown();
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idB = dataImpTest.createBeverage("cocacola", 100, 100);
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(idB, 1);
			int balance = dataImpTest.getBalance();

			dataImpTest.sellCapsules(idE, idB, 10, true);

			assertEquals(balance, dataImpTest.getBalance(), "Wrong to sell capsules");

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

	@Test
	public void testSellCapsules2() {
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idB = dataImpTest.createBeverage("cocacola", 100, 100);
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(idB, 1);
			int balance = dataImpTest.getBalance();
			int updated = dataImpTest.sellCapsules(idE, idB, 10, false);

			assertEquals(balance + 10, dataImpTest.getBalance(), "Wrong to sell capsules");

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

	@Test
	public void testSellCapsules3() {
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idB = dataImpTest.createBeverage("cocacola", 100, 100);
			dataImpTest.rechargeAccount(idE, 10000000);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.getBalance();
			dataImpTest.sellCapsules(idE, idB, Integer.MAX_VALUE, true);

			fail("Exception not generated");

		} catch (SQLException e) {
			fail("Sql exception opening db");

		} catch (EmployeeException e) {
			fail("unable to create employee");
		} catch (BeverageException e) {
			fail("unable to create beverage");
		} catch (NotEnoughBalance e) {
			fail("unable to buy boxes");
		} catch (NotEnoughCapsules e) {
			
		} finally {
			tearDown();
		}

	}

	@Test
	public void testSellCapsules4() {
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idB = dataImpTest.createBeverage("cocacola", 100, 100);
			dataImpTest.rechargeAccount(idE, 10000000);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.getBalance();
			dataImpTest.sellCapsules(idE, idB, Integer.MAX_VALUE, false);

			fail("exception not generated");

		} catch (SQLException e) {
			fail("Sql exception opening db");

		} catch (EmployeeException e) {
			fail("unable to create employee");
		} catch (BeverageException e) {
			fail("unable to create beverage");
		} catch (NotEnoughBalance e) {
			fail("unable to buy boxes");
		} catch (NotEnoughCapsules e) {
			
		} finally {
			tearDown();
		}

	}

	@Test
	public void testSellCapsules5() {
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idB = dataImpTest.createBeverage("cocacola", 100, 100);
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.getBalance();
			dataImpTest.sellCapsules(idE, idB, -35, true);

			fail("exception not generated");

		} catch (SQLException e) {
			fail("Sql exception opening db");

		} catch (EmployeeException e) {
			fail("unable to create employee");
		} catch (BeverageException e) {
			fail("unable to create beverage");
		} catch (NotEnoughBalance e) {
			fail("not enough balance");

		} catch (NotEnoughCapsules e) {
		} finally {
			tearDown();
		}

	}

	@Test
	public void testSellCapsules6() {
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idB = dataImpTest.createBeverage("cocacola", 100, 100);
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.getBalance();
			dataImpTest.sellCapsules(idE, idB, -35, false);

			fail("exception not generated");

		} catch (SQLException e) {
			fail("Sql exception opening db");

		} catch (EmployeeException e) {
			fail("unable to create employee");
		} catch (BeverageException e) {
			fail("unable to create beverage");
		} catch (NotEnoughBalance e) {
			fail("unable to buy boxes");
		} catch (NotEnoughCapsules e) {

		} finally {
			tearDown();
		}

	}

	@Test
	public void testSellCapsules7() {
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			int idB = dataImpTest.createBeverage("cocacola", 100, 100);
			dataImpTest.rechargeAccount(idE, 100000);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.getBalance();
			dataImpTest.sellCapsules(idE, idB, Integer.MIN_VALUE, true);

			fail("exception not generated");

		} catch (SQLException e) {
			fail("Sql exception opening db");

		} catch (EmployeeException e) {
			fail("unable to create employee");
		} catch (BeverageException e) {
			fail("unable to create beverage");
		} catch (NotEnoughBalance e) {
			fail("not enough balance");

		} catch (NotEnoughCapsules e) {

		} finally {
			tearDown();
		}

	}

	@Test
	public void testSellCapsules8() {
		try {
			setUp();
			int idE = dataImpTest.createEmployee("Leonardo", "Davinci");
			dataImpTest.rechargeAccount(idE, 100000);
			int idB = dataImpTest.createBeverage("cocacola", 100, 100);
			dataImpTest.buyBoxes(idB, 1);
			dataImpTest.getBalance();
			dataImpTest.sellCapsules(idE, idB, Integer.MIN_VALUE, true);

			fail("exception not generated");

		} catch (SQLException e) {
			fail("Sql exception opening db");

		} catch (EmployeeException e) {
			fail("unable to create employee");
		} catch (BeverageException e) {
			fail("unable to create beverage");
		} catch (NotEnoughBalance e) {
			fail("unable to buy boxes");
		} catch (NotEnoughCapsules e) {
		} finally {
			tearDown();
		}
	}
}