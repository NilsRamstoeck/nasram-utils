package net.nilsramstoeck.util.swing;

/**
 * A named ComboBox option
 * @author Nils Ramstoeck
 * @param <T> Type of the option value
 */
public class JComboBoxOption<T> {
	/**
	 * Name of the Option. This is what will be displayed in the ComboBox
	 */
	private String name;
	
	/**
	 * Value of the Option
	 */
	private T value;
	
	public JComboBoxOption(String _name, T _value){
		this.name = _name;
		this.value = _value;
	}

	/**
	 * 
	 * @return Name of the options
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * Value getter
	 * @return Value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Name getter
	 * @return Name
	 */
	public String getName() {
		return name;
	}
}
