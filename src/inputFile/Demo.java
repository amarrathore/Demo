package inputFile;

import inputFile.SeleniumUtilities;

public class Demo {	
	public static void s1() {
		SeleniumUtilities.updateResult("Aaar", "BCS", true, "Hello");
	}
	
	public static void s2() {
		SeleniumUtilities.updateResult("Amar1", "BCS Tech", false, "Hi");
	}
	
	public static void main(String[] args) {
		Demo.s1();
		Demo.s2();
	}

}
