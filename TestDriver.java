package wims_v1;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

public class TestDriver {

	public static void main(String[] args) throws SQLException {
		ResultSet rs = null;
		
		/* 
		// Display the titles and sql statements currently stored in the SQL_Handler
		HashMap<String, PreparedStatement> sql_statements = SQL_Handler.getSQLStatements();
		for (Entry<String, PreparedStatement> entry : sql_statements.entrySet()) {
			System.out.println("Title: " + entry.getKey() + "\nStmt: " + entry.getValue().toString());
		}
		*/
		
		/*
		rs = SQL_Handler.getEmpRowByID("123456");
		if (rs.next()) {
			System.out.println("Emp ID: " + rs.getString("employee_id") + "\nSalt: " + rs.getString("salt"));
		}
		else {
			System.out.println("No result set");
		}
		System.out.println(SQL_Handler.isValidUsernamePassword("123456", "password")); //Should return true
		System.out.println(SQL_Handler.isValidUsernamePassword("123456", "wordpass")); //Should return false
		System.out.println(SQL_Handler.isValidUsernamePassword("456123", "password")); //Should return false
		*/
		
		SQL_Handler.insertNewEmployee("098765", "Paul Bayruns", false, SQL_Handler.md5_hash("wordpass" + SQL_Handler.salt), null);
	}

}
