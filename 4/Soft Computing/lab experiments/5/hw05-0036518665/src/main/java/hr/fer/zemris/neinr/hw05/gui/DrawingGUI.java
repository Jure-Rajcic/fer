package hr.fer.zemris.neinr.hw05.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.neinr.hw05.utils.DataUtils;

public class DrawingGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<String> points = new ArrayList<>();
	private Map<Integer, List<List<String>>> patterns = new LinkedHashMap<>();
	private int current = 1;
	private JTextArea statsLabel;
	
	public DrawingGUI() {
		patterns.put(current, new ArrayList<>());
		
		this.setLayout(new BorderLayout());
		
		initGUI();
	}
	
	private void initGUI() {
		
		statsLabel = new JTextArea();
		statsLabel.setEditable(false);
		this.add(statsLabel, BorderLayout.EAST);
		
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
				patterns.get(current).add(DataUtils.convertData(points, 10));
				points.clear();
				
				repaint();
			}
		};
		
		this.addMouseMotionListener(mouseMotionListener);
		this.addMouseListener(mouseListener);
		
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		
		nextPatternAction.putValue(Action.NAME, "Next pattern");
		JButton patternButton = new JButton(nextPatternAction);
		buttons.add(patternButton);
		
		finishAction.putValue(Action.NAME, "Done");
		JButton finishButton = new JButton(finishAction);
		buttons.add(finishButton);
		
		this.add(buttons, BorderLayout.PAGE_END);
	}

	private Action nextPatternAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			patterns.put(++current, new ArrayList<>());
		}
	};
	
	private Action finishAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DataUtils.saveData(patterns);
			patterns.clear();
			current = 1;
			patterns.put(current, new ArrayList<>());
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
		
		StringBuilder sb = new StringBuilder();
		for(Integer i : patterns.keySet()) {
			sb.append("Znak " + i + ": " + patterns.get(i).size() + " uzoraka");
			sb.append("\n");
		}
		statsLabel.setText(sb.toString());
	}
}
