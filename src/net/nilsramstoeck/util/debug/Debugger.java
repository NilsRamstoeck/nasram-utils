package net.nilsramstoeck.util.debug;

public class Debugger {

	/**
	 * Turns Debugging On/Off
	 */
	public static boolean DEBUG = false;	
	
	/**
	 * System.out.println(Object o) if {@code ServerDashboard.DEBUG} is {@code true}
	 * @param o Object to print
	 */
	public static void debugln(Object o) {
		if (DEBUG) System.out.println(o);
	}


	/**
	 * System.out.println() if {@code ServerDashboard.DEBUG} is {@code true}
	 */
	public static void debugln() {
		if (DEBUG) System.out.println();
	}


	/**
	 * System.out.print(Object o) if {@code ServerDashboard.DEBUG} is {@code true}
	 * @param o Object to print
	 */
	public static void debug(Object o) {
		if (DEBUG) System.out.print(o);
	}

}
