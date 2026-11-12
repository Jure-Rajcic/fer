package hr.fer.zemris.neinr.hw05.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import hr.fer.zemris.neinr.hw05.ann.ANNAlgorithm;
import hr.fer.zemris.neinr.hw05.ann.BackPropagationANNAlgorithm;
import hr.fer.zemris.neinr.hw05.utils.DataUtils;

public class TrainGUI extends JComponent {

	private static final long serialVersionUID = 1L;
	private JTextArea batchSizeArea;
	private JTextArea layerArea;
	private JTextArea errorArea;
	private JTextArea epochArea;
	private JTextArea etaArea;
	private JLabel messageLabel;
	private JComboBox<String> algorithmBox;
	private int M;

	public TrainGUI(int M) {
		this.M = M;
		
		initGUI();
	}

	private void initGUI() {
		JLabel algorithmLabel = new JLabel("Choose learning algorithm:");
		algorithmLabel.setSize(150, 20);
		algorithmLabel.setLocation(100, 40);
		this.add(algorithmLabel);
		algorithmBox = new JComboBox<>(new String[] {"Online", "Batch", "Whole"});
		algorithmBox.setSize(190, 20);
		algorithmBox.setLocation(250, 40);
		
		algorithmBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getItem().toString().equals("Batch")) {
					batchSizeArea.setEditable(true);
					batchSizeArea.setBackground(Color.WHITE);
				} else {
					batchSizeArea.setEditable(false);
					batchSizeArea.setBackground(Color.GRAY);
				}
			}
			
		});
		this.add(algorithmBox);
		
		JLabel batchSizeLabel = new JLabel("Batch size:");
		batchSizeLabel.setSize(150, 20);
		batchSizeLabel.setLocation(100, 70);
		this.add(batchSizeLabel);
		batchSizeArea = new JTextArea();
		batchSizeArea.setEditable(false);
		batchSizeArea.setBackground(Color.GRAY);
		batchSizeArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		batchSizeArea.setSize(190, 20);
		batchSizeArea.setLocation(250, 70);
		this.add(batchSizeArea);
		
		JLabel layerLabel = new JLabel("Hidden layers:");
		layerLabel.setSize(150, 20);
		layerLabel.setLocation(100,100);
		this.add(layerLabel);
		layerArea = new JTextArea();
		layerArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		layerArea.setSize(190, 20);
		layerArea.setLocation(250,100);
		this.add(layerArea);
		
		JLabel errorLabel = new JLabel("Max error:");
		errorLabel.setSize(150,20);
		errorLabel.setLocation(100, 130);
		this.add(errorLabel);
		errorArea = new JTextArea();
		errorArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		errorArea.setSize(190, 20);
		errorArea.setLocation(250, 130);
		this.add(errorArea);
		
		JLabel epochLabel = new JLabel("Max no. of epochs:");
		epochLabel.setSize(150, 20);
		epochLabel.setLocation(100, 160);
		this.add(epochLabel);
		epochArea = new JTextArea();
		epochArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		epochArea.setSize(190, 20);
		epochArea.setLocation(250, 160);
		this.add(epochArea);
		
		JLabel etaLabel = new JLabel("Eta:");
		etaLabel.setSize(150, 20);
		etaLabel.setLocation(100, 190);
		this.add(etaLabel);
		etaArea = new JTextArea();
		etaArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		etaArea.setSize(190, 20);
		etaArea.setLocation(250, 190);
		this.add(etaArea);
		
		trainNetworkAction.putValue(Action.NAME, "Train network");
		JButton trainButton = new JButton(trainNetworkAction);
		trainButton.setSize(340, 20);
		trainButton.setLocation(100, 210);
		this.add(trainButton);
		
		messageLabel = new JLabel("");
		messageLabel.setSize(340,20);
		messageLabel.setLocation(100, 240);
		this.add(messageLabel);
	}
	
	private Action trainNetworkAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;
		File f = new File("src/main/resources/data.txt");

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!f.exists()) {
				JOptionPane.showMessageDialog(TrainGUI.this, "Patterns are not defined. Please deifne them.");
			} else {
				String line = null;
				
				try {
					line = Files.readString(Paths.get("src/main/resources/data.txt"));
					Map<Double, List<List<Double>>> data = DataUtils.loadData(line, M);
					
					ANNAlgorithm algorithm = BackPropagationANNAlgorithm.getInstance();
					
					List<Integer> layers = new ArrayList<>();
					layers.add(20);
					if(!layerArea.getText().isBlank())
						for(String s : layerArea.getText().trim().split(","))
							layers.add(Integer.valueOf(s));
					layers.add(data.keySet().size());
					double error = errorArea.getText().isEmpty() ? 0. : Double.valueOf(errorArea.getText());
					int epoch = epochArea.getText().isEmpty() ? 1000 : Integer.valueOf(epochArea.getText());
					double eta = etaArea.getText().isEmpty() ? 0.1 : Double.valueOf(etaArea.getText());
					
					int batchSize = 1;
					if(algorithmBox.getSelectedItem().toString().equals("Batch")) {
						batchSize = Integer.valueOf(batchSizeArea.getText());
					}
					if(algorithmBox.getSelectedItem().toString().equals("Whole")) {
						batchSize = 0;
						for(Double i : data.keySet())
							batchSize += data.get(i).size();
					}
					
					algorithm.train(data, layers, error, epoch, eta, batchSize);
					
					messageLabel.setText("Training succesful.");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	};
}
