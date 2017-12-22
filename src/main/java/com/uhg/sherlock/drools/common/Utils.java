package com.uhg.sherlock.drools.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * @author vamdiyal
 *
 */
public class Utils {
	private static final Properties properties = new Properties();
	static {
		try {
			properties.load(new FileInputStream(
					"/mapr/datalake/uhc/ei/pi_ara/sherlock_prod/applications/DROOLS/config/drools.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static String getString(String key) throws FileNotFoundException, IOException {
		return properties.getProperty(key);
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = null;
		try {
			/*conn = DriverManager
					.getConnection("jdbc:sqlserver://" + Utils.getString("sherlock.batch.connector.conn.server.ip")
							+ ";user=" + Utils.getString("sherlock.batch.connector.conn.server.user") + ";password="
							+ Utils.getString("sherlock.batch.connector.conn.server.pwd") + ";database="
							+ Utils.getString("sherlock.batch.connector.conn.server.dbname"));*/
			conn = DriverManager
					.getConnection("jdbc:sqlserver://" + "10.106.172.254"
							+ ";user=" +"sherlocksqlt" + ";password="
							+ "Optum@1234" + ";database="
							+ "Sherlock");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception occured while creating connection" + e.getMessage());
		}

		return conn;
	}

}
