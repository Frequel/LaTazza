package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.EmployeeException;

public class TestEmployee {

	private Employee em;

	protected void setUp(int id) {
		try {
			em = new Employee(id, "Mario", "Rossi", 0);
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void tearDown() {
		// work done by the garbage collector
	}

	@Test
	public void test1String() {
		setUp(1);
		try {
			em.setName("Marco");

			assertEquals(em.getName(), "Marco", "Error in setting the name");
		} catch (EmployeeException e) {
			fail("string not setted well");
		}
		try {
			em.setSurname("Calogero");
			assertEquals(em.getSurname(), "Calogero", "Error setting the surname");
		} catch (EmployeeException e) {
			fail("string not setted well");
		}

	}

	@Test
	public void test2String() {
		setUp(1);
		try {
			em.setName("12#");
			fail("Name exception not generated");
		} catch (EmployeeException e) {
			assertEquals(em.getName(), "Mario", "Error");
		}
		try {
			em.setSurname("12)#");
			fail("surName exception not generated");
		} catch (EmployeeException e) {
			assertEquals(em.getSurname(), "Rossi", "Error");
		}

	}

	@Test
	public void test1Int() {
		setUp(1);
		assertEquals(em.getID(), 1, "Error in assignment of the id");

	}

	@Test
	public void test2Int() {
		setUp(Integer.MAX_VALUE + 1);
		assertEquals(em.getID(), 1, "Error in assignment of the id");

	}

	@Test
	public void test3Int() {
		setUp(-35);
		assertEquals(em.getID(), 1, "Error in assignment of the id");
	}

	@Test
	public void test4Int() {
		setUp(Integer.MIN_VALUE);
		assertEquals(em.getID(), 1, "Error in assignment of the id");

	}

	@Test
	public void test1Float() {
		setUp(1);
		try {
			em.setBalance((float) 3.5);
			assertEquals(3.5, em.getBalance(), "Error in assignent the balance");
		} catch (EmployeeException e) {
			fail("exception due to the balance");
		}

	}

	@Test
	public void test2Float() {
		setUp(1);
		try {
			em.setBalance(Float.MAX_VALUE * 2);
			fail("exception not generated");
		} catch (EmployeeException e) {
			assertEquals(0.0, em.getBalance(), "Error in assignent the balance");
		}

	}

	@Test
	public void test3Float() {
		setUp(1);
		try {
			em.setBalance((float) -0.5);
			assertEquals(-0.5, em.getBalance(), "Error in assignent the balance");
		} catch (EmployeeException e) {
			fail("Employee exception");
		}

	}

}
