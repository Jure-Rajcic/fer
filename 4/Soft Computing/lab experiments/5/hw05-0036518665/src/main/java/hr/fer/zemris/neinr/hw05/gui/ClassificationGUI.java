package hr.fer.zemris.neinr.hw05.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.neinr.hw05.ann.ANNAlgorithm;
import hr.fer.zemris.neinr.hw05.ann.BackPropagationANNAlgorithm;
import hr.fer.zemris.neinr.hw05.utils.DataUtils;

public class ClassificationGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<String> points = new ArrayList<>();
	private JTextArea classificationArea;
	
	public ClassificationGUI() {
		
		this.setLayout(new BorderLayout());
		
		initGUI();
	}
	
	private void initGUI() {
		classificationArea = new JTextArea();
		classificationArea.setEditable(false);
		classificationArea.setText("Alfa = 0\nBeta = 0\nGamma = 0\nDelta = 0\nEpsilon = 0");
		
		this.add(classificationArea, BorderLayout.PAGE_START);
		
		MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				points.add(e.getX() + "," + e.getY());
				repaint();
			}
			
		};
		
		MouseListener mouseListener = new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				ANNAlgorithm algorithm = BackPropagationANNAlgorithm.getInstance();
				List<String> patternPoints = DataUtils.convertData(points, 10);
				
				List<Double> pattern = new ArrayList<>();
				for(String s : patternPoints) {
					pattern.add(Double.valueOf(s.split(",")[0]));
					pattern.add(Double.valueOf(s.split(",")[1]));
				}
				
				List<Double> result = algorithm.predict(pattern, false);
				
				StringBuilder sb = new StringBuilder();
				sb.append("Alfa = " + result.get(0) + "\n");
				sb.append("Beta = " + result.get(1) + "\n");
				sb.append("Gamma = " + result.get(2) + "\n");
				sb.append("Delta = " + result.get(3) + "\n");
				sb.append("Epsilon = " + result.get(4));
				
				classificationArea.setText(sb.toString());
				
				repaint();
			}
		};
		
		this.addMouseMotionListener(mouseMotionListener);
		this.addMouseListener(mouseListener);
		
		nextPatternAction.putValue(Action.NAME, "Next pattern");
		JButton patternButton = new JButton(nextPatternAction);
		this.add(patternButton, BorderLayout.PAGE_END);
	}
	
	private Action nextPatternAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			points.clear();
			classificationArea.setText("Alfa = 0\nBeta = 0\nGamma = 0\nDelta = 0\nEpsilon = 0");
			repaint();
		}
	};

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		if(this.points.size() >= 2) {
			for(int i = 2; i < this.points.size(); i++) {
				int x1 = Integer.valueOf(this.points.get(i - 2).split(",")[0]);
				int y1 = Integer.valueOf(this.points.get(i - 2).split(",")[1]);
				int x2 = Integer.valueOf(this.points.get(i - 1).split(",")[0]);
				int y2 = Integer.valueOf(this.points.get(i - 1).split(",")[1]);
				g2d.drawLine(x1, y1, x2, y2);
			}
		}
	}
}
