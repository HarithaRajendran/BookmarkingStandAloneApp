
package com.asahi.bookmarkingApp.connectionPool;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.Connection;

public class ConnectionPool {

	private static Vector<Connection> v1 = new Vector<>();

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			for (int i = 1; i <= 5; i++) {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohms?useSSL=false", "root",
						"root");
				v1.add(con);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static void destroyPool() {
		for (int i = 0; i < v1.size(); i++) {
			try {
				v1.remove(0).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Connection getConnectionPool() {
		Connection con = null;
		if (v1.size() == 0) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohms?useSSL=false", "root", "root");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			con = v1.remove(0);
		}
		return con;
	}

	static void putConnectionPool(Connection con) {
		if (v1.size() == 5) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			v1.add(con);
		}
	}

}