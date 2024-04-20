package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * class PrimListModel represents a list of primitives
 * 
 * @author Jure Rajcic
 *
 */
public class PrimListModel implements ListModel<Integer> {

	private List<Integer> prims = new ArrayList<>();
	private List<ListDataListener> listeners = new ArrayList<>();
	
	public PrimListModel() {
		prims.add(1);
	}

	@Override
	public int getSize() {
		return this.prims.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return this.prims.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListDataListener(ListDataListener listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * Metoda koja računa sljedeći prosti broj i dodaje ga u listu
	 */
	public void next() {
		int position = this.prims.size();
		int current = this.prims.get(prims.size() - 1);
		boolean isPrim = false;
		while(!isPrim) {
			current++;
			int divider = 2;
			while(divider < current) {
				if(current % divider == 0)
					break;
				divider++;
			}
			isPrim = divider == current;
		}
		this.prims.add(current);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);
		for(ListDataListener l : this.listeners) {
			l.intervalAdded(event);
		}
	}
}
