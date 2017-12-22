package com.uhg.sherlock.drools.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import com.uhg.sherlock.drools.common.Constants;
import com.uhg.sherlock.drools.exception.BatchNotFoundException;

public class BatchProcessStateHelper {
	static CallableStatement ps;

	public static void droolsOrcStarted(String batchId)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		try (Connection con = DBConnection.getConnection()) {
			ps = con.prepareCall(Constants.DROOLS_STARTED_SP);
			ps.setString(1, batchId);
			ps.execute();
		}

	}

	public static void droolsOrcCompleted(String batchId)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		try (Connection con = DBConnection.getConnection()) {
			ps = con.prepareCall(Constants.DROOLS_COMPLETED_SP);
			ps.setString(1, batchId);
			ps.execute();
		}
	}

	public static void droolsOrcAborted(String batchId, String abortMessage)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		try (Connection con = DBConnection.getConnection()) {
			ps = con.prepareCall(Constants.BATCH_ABORTED_SP);
			ps.setString(1, batchId);
			ps.setString(2, "3000");
			ps.setTimestamp(3, new Timestamp((new Date()).getTime()));
			ps.setString(4, abortMessage.trim());
			ps.execute();
		}
	}

	public static String getDroolsOrcBatches()
			throws ClassNotFoundException, SQLException, BatchNotFoundException, FileNotFoundException, IOException {
		try (Connection con = DBConnection.getConnection()) {
			ps = con.prepareCall(Constants.DROOLS_ORC_GET_BATCHID);
			ps.registerOutParameter(1, java.sql.Types.VARCHAR);
			ps.execute();
			if (ps.getString(1) == null || "".equalsIgnoreCase(ps.getString(1))) {
				throw new BatchNotFoundException("Batch Not Found");
			}
			return ps.getString(1);
		}
		
	}

	public static void main(String[] args)
			throws ClassNotFoundException, SQLException, BatchNotFoundException, FileNotFoundException, IOException {
		String batchid = getDroolsOrcBatches();
		droolsOrcStarted(batchid);
		droolsOrcCompleted(batchid);
		droolsOrcStarted(batchid);
	}

}
