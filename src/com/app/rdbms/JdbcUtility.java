package com.app.rdbms;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JdbcUtility {

	private static String URL = "jdbc:mysql://localhost:3306/rdmsjoins";
	private static String USERNAME = "root";
	private static String PASSWORD = "root";
	private static Connection con = null;
	private static CallableStatement cs = null;

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return con;
	}

	public static ResultSet executeQuery(String sql) throws SQLException {
		cs = con.prepareCall(sql);
		return cs.executeQuery();
	}

	public static boolean execute(String sql) throws SQLException {
		cs = con.prepareCall(sql);
		return cs.execute();
	}

}
