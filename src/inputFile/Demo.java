package inputFile;

import org.testng.annotations.Test;

public class Demo {
	//@Test
	public static void main(String[] args) throws Exception {
		ResultUpdate.setExcel();
		ResultUpdate.updateResult("hello how are you?", "Module Name", true, "Website took more than 15 secs to load on ", 0);
	}
}
