package hr.fer.zermis.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class RCPositionTester {

	@Test
	public void rowExceptionTest() {
		assertThrows(CalcLayoutException.class, () -> {
			new RCPosition(0, 2);
		});

		assertThrows(CalcLayoutException.class, () -> {
			new RCPosition(6, 2);

		});
	}

	@Test
	public void columnExceptionTest() {
		assertThrows(CalcLayoutException.class, () -> {
			new RCPosition(5, 8);
		});

		assertThrows(CalcLayoutException.class, () -> {
			new RCPosition(3, 0);
		});
	}

	@Test
	public void heightAndWidthExceptionTest() {
		assertThrows(CalcLayoutException.class, () -> {
			new RCPosition(1, 5);
		});

		assertThrows(CalcLayoutException.class, () -> {
			RCPosition.parse("1, 5");
		});
	}
}
