package wims_v1;

import java.util.ArrayList;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.DSL.*;

public class SQL_Statement {
	public static String createInsertQuery(String table, ArrayList<String> columns, ArrayList<String> values) {
		StringBuilder insertQuery = new StringBuilder();
		if (columns.size() == values.size()) {
			insertQuery.append("INSERT INTO " + table + " (");
			for (String s : columns) {
				insertQuery.append(s + ", ");
			}
			insertQuery.append(")\nVALUES (");
			for (String s : values) {
				insertQuery.append(s + ", ");
			}
			insertQuery.append(");");
		}
		return insertQuery.toString();
	}
}
