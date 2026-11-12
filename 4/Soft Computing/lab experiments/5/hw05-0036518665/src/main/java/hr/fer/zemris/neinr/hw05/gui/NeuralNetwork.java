package hr.fer.zemris.neinr.hw05.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class NeuralNetwork extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private int M = 10;

	public NeuralNetwork() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Neural network");
		setSize(600, 600);
		
		initGUI();
	}

	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		
		this.tabbedPane = new JTabbedPane();
		
		this.tabbedPane.insertTab("Draw patterns", null, new DrawingGUI(), "Draw patterns", 0);
		
		loadData.putValue(Action.NAME, "Load patterns");
		this.tabbedPane.insertTab("Load patterns", null, new JButton(loadData), "Load patterns", 1);
		this.tabbedPane.insertTab("Train", null, new TrainGUI(this.M), "Train", 2);
		this.tabbedPane.insertTab("Classification", null, new ClassificationGUI(), "Classification", 3);
		
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
	}
	
	private Action loadData = new AbstractAction() {

		private static final long serialVersionUID = 1L;
		File f = new File("src/main/resources/data.txt");

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!f.exists())
				JOptionPane.showMessageDialog(NeuralNetwork.this, "Patterns are not defined. Please deifne them.");
		}
		
	};
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new NeuralNetwork().setVisible(true);
			}
		});
	}
}
