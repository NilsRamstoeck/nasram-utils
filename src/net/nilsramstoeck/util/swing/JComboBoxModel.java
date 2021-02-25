package net.nilsramstoeck.util.swing;

import java.util.HashSet;
import java.util.Vector;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * A ComboBoxModel for ComboBoxes using {@code JComboBoxOption}
 * 
 * @author Nils Ramstoeck
 *
 */
public class JComboBoxModel<T> implements MutableComboBoxModel<JComboBoxOption<T>> {

	/**
	 * Current this.entries in the ComboBox
	 */
	private Vector<JComboBoxOption<T>> entries = new Vector<JComboBoxOption<T>>();

	/**
	 * Set of listeners listening to ListDataEvent
	 */
	private HashSet<ListDataListener> listeners = new HashSet<ListDataListener>();

	/**
	 * Index of the currently selected item
	 */
	private int index = -1;

	@Override
	public JComboBoxOption<T> getSelectedItem() {
		if (this.index >= 0) {
			return this.entries.elementAt(this.index);
		} else {
			return null;
		}
	}

	@Override
	public void setSelectedItem(Object item) {
		this.index = this.entries.indexOf(item);
	}

	@Override
	public int getSize() {
		return this.entries.size();
	}

	@Override
	public JComboBoxOption<T> getElementAt(int index) {
		return this.entries.elementAt(index);
	}

	@Override
	public void addElement(JComboBoxOption<T> obj) {
		if (!this.entries.contains(obj)) {
			this.entries.add(obj);
			int index = this.entries.indexOf(obj);
			this.dispatchAddEvent(index);
			if (this.index == -1) this.index = 0;
		}
	}

	@Override
	public void insertElementAt(JComboBoxOption<T> obj, int index) {
		if (!this.entries.contains(obj)) {
			this.entries.add(index, obj);
			this.dispatchAddEvent(index);
			if (this.index == -1) this.index = 0;
		}
	}

	@Override
	public void removeElement(Object obj) {
		if (this.entries.contains(obj)) {
			int index = this.entries.indexOf(obj);
			this.entries.remove(obj);
			this.dispatchRemoveEvent(index);
		}
	}

	@Override
	public void removeElementAt(int index) {
		if (this.entries.size() > index) {
			this.entries.removeElementAt(index);
			this.dispatchRemoveEvent(index);
		}
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		this.listeners.remove(l);
	}

	/**
	 * Dispatches the add element event to all listeners
	 * @param index Index of the new element
	 */
	private void dispatchAddEvent(int index) {
		ListDataEvent e = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index);
		for(ListDataListener l : this.listeners) {
			l.intervalAdded(e);
		}
	}
	
	/**
	 * Dispatches the element removed event to all listeners
	 * @param index Index of the removed element
	 */
	private void dispatchRemoveEvent(int index) {
		ListDataEvent e = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index);
		for(ListDataListener l : this.listeners) {
			l.intervalRemoved(e);
		}
	}
	
}