package com.uhg.sherlock.drools.dao;

import com.uhg.sherlock.drools.common.Constants;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rlingaia on 6/9/2017.
 */
public class ClaimDaoHelper {
	private static Set<String> npiSet = new HashSet<String>();
	private static Set<String> tinSet = new HashSet<String>();
	private static Set<String> cptSet = new HashSet<String>();
	private static Set<String> procCodeSet = new HashSet<String>();
	private static Set<String> taxonomySet = new HashSet<String>();
	static CallableStatement stmt = null;

	static {
		try {
			populatePCodeList();
			populateNPIList();
			populateTINList();
			populateTaxonomyList();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Exception thrown while populating codes from SQLServer");
		}

	}

	public static Set<String> getNpiSet() {
		return npiSet;
	}

	public static Set<String> getTinSet() {
		return tinSet;
	}

	public static Set<String> getCptSet() {
		return cptSet;
	}

	public static Set<String> getProcCodeSet() {
		return procCodeSet;
	}

	public static Set<String> getTaxonomySet() {
		return taxonomySet;
	}

	public static void populatePCodeList() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		try (Connection con = DBConnection.getConnection()) {
			stmt = DBConnection.getConnection().prepareCall("exec " + "srk_drools_getproccd");

			if (stmt.execute()) {
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					procCodeSet.add(rs.getString(1));
				}
				rs.close();
				stmt.close();
			}
		}
	}

	public static void populateNPIList() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {

		try (Connection con = DBConnection.getConnection()) {
			stmt = con.prepareCall("exec " + Constants.SP_GETNPI);
			if (stmt.execute()) {
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					npiSet.add(rs.getString(1));
				}
				rs.close();
				stmt.close();
			}
		}
	}

	public static void populateTINList() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		try (Connection con = DBConnection.getConnection()) {
			stmt = con.prepareCall("exec " + Constants.SP_GETTIN);

			if (stmt.execute()) {
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					tinSet.add(rs.getString(1));
				}
				rs.close();
				stmt.close();
			}
		}
	}

	public static void populateTaxonomyList() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		try (Connection con = DBConnection.getConnection()) {
			stmt = con.prepareCall("exec " + Constants.SP_GETTAXONOMY);
			if (stmt.execute()) {
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					taxonomySet.add(rs.getString(1));
				}
				rs.close();
				stmt.close();
			}
		}
	}

	public static void populateCPTList() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		try (Connection con = DBConnection.getConnection()) {
			stmt = con.prepareCall("exec " + Constants.SP_GETCPTCODE);
			if (stmt.execute()) {
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					cptSet.add(rs.getString(1));
				}
				rs.close();
				stmt.close();
			}
		}
	}
}
