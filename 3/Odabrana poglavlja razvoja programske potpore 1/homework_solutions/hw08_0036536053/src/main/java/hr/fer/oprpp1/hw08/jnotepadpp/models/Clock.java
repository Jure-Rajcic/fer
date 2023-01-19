package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * 
 * class Clock represents JLable that shows current time
 * 
 * @author Jure Rajcic
 *
 */
public class Clock extends JLabel {

	private static final long serialVersionUID = 1L;

	volatile String time;
	volatile boolean stop = false;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public Clock() {
		this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.setHorizontalAlignment(SwingConstants.RIGHT);
		Thread t = new Thread(() -> {
			while (!stop) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException ex) {
					System.out.println("Thread.sleep method trowed InterruptedException");
				}
				SwingUtilities.invokeLater(() -> 
					this.setText(formatter.format(new Date(System.currentTimeMillis())))
				);
			}
		});
		t.setDaemon(true);
		t.start();
	}

	
	public void stop() {
		stop = true;
	}
}
