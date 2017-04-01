package wims_v1;

import java.util.HashMap;
import java.util.Map;

public class TestDriver {

	public static void main(String[] args) {
		Map<String, String> items = new HashMap<>();
		items.put("25474568", "20");
		items.put("87645938", "15");
		items.put("14527539", "100");
		items.put("76548930", "12");
		
		LabelFactory.createLabel("123456", "098765", items);
		/*
		Login_Screen loginScr = new Login_Screen();
		if (loginScr.isSuccessfulConnection()) {
			loginScr.dispose();
			
		}
		*/
	}
}
