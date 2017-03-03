package wims_v1;
import java.sql.*;
public class Driver {

	public static void main(String[] args) {
		SQL_Handler sql = new SQL_Handler();
		try {
			Connection conn = sql.getConnection();
			String query = "SELECT * FROM employee";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String emp_name = rs.getString("name");
				String emp_id = rs.getString("employee_id");
				System.out.println("Name: " + emp_name + " -> ID: " + emp_id);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
