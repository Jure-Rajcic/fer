package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * class PrimDemo represents demonstration for asigment 4
 * 
 * @author Jure Rajcic
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	public PrimDemo() {
		setTitle("Jos samo ovo i gotovoooooooo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		setLocation(100, 100);
		setSize(500, 300);

	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridLayout(1, 0));
		PrimListModel primList = new PrimListModel();
		{
			JList<Integer> list1 = new JList<>(primList);
			panel.add(new JScrollPane(list1));
			JList<Integer> list2 = new JList<>(primList);
			panel.add(new JScrollPane(list2));
		}
		cp.add(panel, BorderLayout.CENTER);

		JButton button = new JButton("Sljedeći");
		button.addActionListener(e -> primList.next());
		cp.add(button, BorderLayout.PAGE_END);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			PrimDemo frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
