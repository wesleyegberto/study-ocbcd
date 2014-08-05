package study.projectbank.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUtils {
	private SQLUtils(){}
	
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch(SQLException e) {
			}
		}
	}
	
	public static void close(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch(SQLException e) {
			}
		}
	}
	
	public static void close(ResultSet rst) {
		if(rst != null) {
			try {
				rst.close();
			} catch(SQLException e) {
			}
		}
	}
}
