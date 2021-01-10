package com.exercise.orders.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {

	private final static String URL = "jdbc:mysql://rafaelmarketplaceserver.mysql.database.azure.com:3306/marketplace?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private final static String USERNAME = "rafaeladmin@rafaelmarketplaceserver";
	private final static String PASSWORD = "Password123456";

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException("Erro na cone��o ", e);
		}
	}

	public static void closeConnection(Connection conn) {
		try {
			if(conn!=null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection conn, PreparedStatement stmt) {

		closeConnection(conn);
		try {
			if(stmt!=null) {
				stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {

		closeConnection(conn, stmt);
		try {
			if(rs!=null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
