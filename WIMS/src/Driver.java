
import java.sql.*;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
public class Driver {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		try {			
			Login_Screen login_scr = new Login_Screen(shell, 0);
			login_scr.open();
		
			Connection conn = SQL_Handler.getConnection();
			String query = "SELECT * FROM employee";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String emp_name = rs.getString("name");
				String emp_id = rs.getString("employee_id");
				System.out.println("Name: " + emp_name + " -> ID: " + emp_id);
			}
			System.out.println("Connected to: " + conn.getMetaData().getURL());
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
