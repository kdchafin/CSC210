import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BasicArithmeticTest {

	@Test
	void testAddition() {
		//fail("Not yet implemented");
		BasicArithmetic addition = BasicArithmetic.createOperation(0, 0, "+");
		assertEquals(addition.performOperation(), 0);
	}
	
	@Test
	void testSubtraction() {
		BasicArithmetic subtraction = BasicArithmetic.createOperation(12, 6, "-");
		assertEquals(subtraction.performOperation(), 6);
	}
	
	@Test
	void testDivision() {
		BasicArithmetic division = BasicArithmetic.createOperation(12, 6, "/");
		assertEquals(division.performOperation(), 2);
	}
	
	@Test
	void testMultiply() {
		BasicArithmetic multiply = BasicArithmetic.createOperation(2, 6, "*");
		assertEquals(multiply.performOperation(), 12);
	}
}