package hr.fer.oprpp1.hw05.crypto;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.shell.ShellIOException;

public class UtilTest {


	@Test
	public void testHextoByte() {
		byte[] arr = Util.hextobyte("01aE22");
		assertEquals(3, arr.length);
		assertEquals(1, arr[0]);
		assertEquals(-82,arr[1]);
		assertEquals(34, arr[2]);
	}
	
	@Test
	public void testHextoByteMethodShouldThrowShellIOExceptionSequanceNotEven() {
		assertThrows(ShellIOException.class, () -> {
			Util.hextobyte("01aE22a");
		});
	}

	@Test
	public void testHextoByteMethodShouldThrowIllegalArgumentExceptionStringContainsInvalidHexCode() {
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("01aE22aJ");
		});
	}

	@Test
	public void testHextoByteWithRandomValues() {
		Random rand = new Random(); 
		byte[] randomBytes = new byte[100];
	  	rand.nextBytes(randomBytes);
		
		String s = Util.bytetohex(randomBytes);
		assertTrue(Arrays.equals(randomBytes, Util.hextobyte(s)));
		// this we cant call because we are working wit primitive types
		// assertEquals(randomBytes, Util.hextobyte(s));
	}
	
	@Test
	public void testBytetohexMethod() {
		assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
		assertEquals("000101ff", Util.bytetohex(new byte[] {0, 1, 1, -1}));
		assertEquals("00017f80", Util.bytetohex(new byte[] {0, 1, 127, -128}));
	}
}
