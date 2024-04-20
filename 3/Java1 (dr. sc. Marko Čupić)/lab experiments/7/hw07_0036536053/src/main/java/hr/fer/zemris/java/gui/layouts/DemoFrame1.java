package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
// import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * class DemoFrame1 is demonstration for CalcLayout
 * 
 * @author Jure Rajcic
 *
 */
public class DemoFrame1 extends JFrame {

	private static final long serialVersionUID = 1L;

	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// initGUI();
		// pack();

		setSize(500, 500);
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		cp.add(l("tekst 1"), new RCPosition(1, 1));
		// cp.add(l("treba baciti iznimku"), new RCPosition(1, 1)); // throws
		// CalcLayoutException
		cp.add(l("tekst 2"), new RCPosition(2, 3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
		cp.add(l("tekst kraći"), new RCPosition(4, 2));
		cp.add(l("tekst srednji"), new RCPosition(4, 5));
		cp.add(l("tekst"), new RCPosition(4, 7));
	}

	private static JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}

	

	// public static void main(String[] args) {
	// 	// 1. picture of homework
	// 	SwingUtilities.invokeLater(() -> {
	// 		JFrame frame = new JFrame();
	// 		JPanel p = new JPanel(new CalcLayout(3));
	// 		for (int i = 1; i <= 5; i++) {
	// 			for (int j = 1; j <=7; j++) {
	// 				JLabel lable = l(String.format("%d,%d", i, j));
	// 				// System.out.println(lable.getText());
	// 				p.add(lable, new RCPosition(i, j));
	// 				if (i == 1 && j < 6) j = 5;
	// 			}
	// 		}
	// 		frame.add(p);
	// 		frame.pack();
	// 		frame.setVisible(true);
	// 	});
	// }

	// public static void main(String[] args) {
	// 	// 2. picture of homework
	// 	SwingUtilities.invokeLater(() -> {
	// 		JFrame frame = new JFrame();
	// 		JPanel p = new JPanel(new CalcLayout(3));
	// 		p.add(l("1,1"), new RCPosition(1,1));
	// 		p.add(l("2,3"), new RCPosition(2,3));
	// 		p.add(l("2,7"), new RCPosition(2,7));
	// 		p.add(l("4,2"), new RCPosition(4,2));
	// 		p.add(l("4,5"), new RCPosition(4,5));
	// 		p.add(l("4,7"), new RCPosition(4,7));
	// 		frame.add(p);
	// 		frame.pack();
	// 		frame.setVisible(true);
	// 	});
	// }

	public static void main(String[] args) {
	SwingUtilities.invokeLater(() -> {
	new DemoFrame1().setVisible(true);
	});
	}

}
