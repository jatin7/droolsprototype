package com.uhg.sherlock.drools.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.uhg.sherlock.drools.common.Utils;

public class DBConnection {

	public static Connection getConnection()
			throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection connection = DriverManager.getConnection(Utils.getString("sherlock.drools.sqlConString"));
		return connection;
	}

}
