package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;

public class TestBeverage {
	private Beverage bv;

	protected void setUp(int id) {
		try {
			bv = new Beverage(id, "caffe", 0, 0, 0, 0);
		} catch (BeverageException e) {
			e.printStackTrace();
		}
	}
	// teardown is done by the garbage collector

	@Test
	public void test1String() {
		setUp(1);
		try {
			bv.setName("fanta");
			assertEquals(bv.getName(), "fanta", "Error in setting the name");

		} catch (BeverageException e) {
			fail("wrong name");
		}

	}

	@Test
	public void test2String() {
		setUp(1);
		try {
			bv.setName("12#");
			fail("setted the wrong name");

		} catch (BeverageException e) {
			assertEquals(bv.getName(), "caffe", "Error");

		}
		// it should be the name of given in the constructor phase

	}

	@Test
	public void test1Int() {
		setUp(1);
		assertEquals(bv.getID(), 1, "Error in assignment of the id");
		bv.setRemainingCapsules(2);
		assertEquals(bv.getRemainingCapsules(), 2, "error in  assignment remaining capsules");
		bv.setCapsulesPerBox(19);
		assertEquals(bv.getCapsulesPerBox(), 19, "error in  assignment the capsules per box");
		bv.setNewRemainingCap(2);
		assertEquals(bv.getNewRemainingCap(), 2, "error in  assignment the new capsules");

	}

	@Test
	public void test2Int() {
		setUp(Integer.MAX_VALUE + 1);
		assertEquals(bv.getID(), 1, "Error in assignment of the id");
		bv.setRemainingCapsules(Integer.MAX_VALUE + 1);
		assertEquals(bv.getRemainingCapsules(), 0, "error in  assignment remaining capsules");
		bv.setCapsulesPerBox(Integer.MAX_VALUE + 1);
		assertEquals(bv.getCapsulesPerBox(), 0, "error in  assignment the capsules per boss");
			bv.setNewRemainingCap(Integer.MAX_VALUE + 1);
			assertEquals(bv.getNewRemainingCap(), 0, "error in  assignment the new capsules");

	}

	@Test
	public void test3Int() {
		setUp(-25);
		assertEquals(bv.getID(), 1, "Error in assignment of the id");
		bv.setRemainingCapsules(-1);
		assertEquals(bv.getRemainingCapsules(), 0, "error in  assignment remaining capsules");
		bv.setCapsulesPerBox(-1);
		assertEquals(bv.getCapsulesPerBox(), 0, "error in  assignment the capsules per boss");
			bv.setNewRemainingCap(-1);
			assertEquals(bv.getNewRemainingCap(), 0, "error in  assignment the new capsules");


	}

	@Test
	public void test4Int() {
		setUp(Integer.MIN_VALUE);
		assertEquals(bv.getID(), 1, "Error in assignment of the id");
		bv.setRemainingCapsules(Integer.MIN_VALUE);
		assertEquals(bv.getRemainingCapsules(), 0, "error in  assignment remaining capsules");
		bv.setCapsulesPerBox(Integer.MIN_VALUE);
		assertEquals(bv.getCapsulesPerBox(), 0, "error in  assignment the capsules per boss");
			bv.setNewRemainingCap(Integer.MIN_VALUE);
			assertEquals(bv.getNewRemainingCap(), 0, "error in  assignment the new capsules");

	}

	@Test
	public void test1Float() {
		setUp(1);
		try {
			bv.setBoxPrice((float) 3.5);
			assertEquals(3.5, bv.getBoxPrice(), "Error in assignent the box price");

		} catch (BeverageException e) {
			fail("setted the wrong box price ");
		}
		try {
			bv.setPrice((float) 3.5);
			assertEquals(3.5, bv.getPrice(), "Error in assignent the capsules price");

		} catch (BeverageException e) {
			fail("setted the wrong capsules price ");

		}
			bv.setNewPrice((float) 3.5);
			assertEquals(3.5, bv.getNewPrice(), "Error in assignent the capsules price");


	}

	@Test
	public void test2Float() {
		setUp(1);

		try {
			bv.setBoxPrice(Float.MAX_VALUE * 2);
			fail("excpetion not generated ");
		} catch (BeverageException e) {
			assertEquals(0, bv.getBoxPrice(), "Error in assignent the box price");

		}
		try {
			bv.setPrice(Float.MAX_VALUE * 2);
			fail("excpetion not generated ");

		} catch (BeverageException e) {
			assertEquals(0, bv.getPrice(), "Error in assignent the capsules price");

		}
			bv.setNewPrice(Float.MAX_VALUE * 2);
			assertEquals(0.0, bv.getNewPrice(), "Error in assignent the capsules price");

	}

	@Test
	public void test3Float() {
		setUp(1);
		try {
			bv.setBoxPrice((float) -0.5);
			fail("excpetion not generated ");
		} catch (BeverageException e) {

			assertEquals(0, bv.getBoxPrice(), "Error in assignent the box price");

		}
		try {
			bv.setPrice((float) -0.5);
			fail("excpetion not generated ");

		} catch (BeverageException e) {
			assertEquals(0, bv.getPrice(), "Error in assignent the capsules price");

		}

		bv.setNewPrice((float)-0.5);
		assertEquals(0.0, bv.getNewPrice(), "Error in assignent the capsules price");
	}

	@Test
	public void test4Float() {
		setUp(1);
		try {
			bv.setBoxPrice(Float.MIN_VALUE - 1);
			fail("excpetion not generated ");
		} catch (BeverageException e) {

			assertEquals(0, bv.getBoxPrice(), "Error in assignent the box price");

		}
		try {
			bv.setPrice(Float.MIN_VALUE - 1);
			fail("excpetion not generated ");

		} catch (BeverageException e) {
			assertEquals(0, bv.getPrice(), "Error in assignent the capsules price");

		}

		bv.setNewPrice(Float.MIN_VALUE-1);
		assertEquals(0.0, bv.getNewPrice(), "Error in assignent the capsules price");
	}

	@Test
	public void testSwapBoolean() {
		setUp(1);

		try {
			bv.setOldRemainingCap(30);
			bv.setPrice((float) 0.5);
			bv.setNewRemainingCap(50);

			bv.setNewPrice((float) 0.05);

			bv.setFlagNewPrice();

			assertTrue(bv.isNewValuesset(), "flag for new values not seetted");

			bv.swap();

			assertTrue(bv.isNewValuesset() == false, "flag for new values not seetted");
			assertEquals((float) 0.05, bv.getPrice(), "Error in assignent the capsules price");
			assertEquals(50, bv.getOldRemainingCap(), "Error in assignent the remainign capsules ");

		} catch (BeverageException e) {
			fail("beverage excpetion");
		}

		try {
			bv.setOldRemainingCap(30);
			bv.setPrice((float) 0.5);

			bv.setNewRemainingCap(50);
			bv.setNewPrice((float) 0.05);
			bv.setFlagNewPrice();

			bv.setNewPrice((float) 0.10);
			assertEquals((float) 0.10, bv.getNewPrice(), "Error in assignent the capsules price");


		} catch (BeverageException e) {
			fail("beverage exception generated");
		}

		try {
			bv.setOldRemainingCap(30);
			bv.setPrice((float) 0.5);

			bv.setNewPrice((float) 0.05);
			bv.setNewRemainingCap(50);
			bv.setFlagNewPrice();

			bv.setNewRemainingCap(25);
			assertEquals(25, bv.getNewRemainingCap(), "error in the assignment of the number of capsules");


		} catch (BeverageException e) {
			fail("beverage exception generated");
		}

	}

}
