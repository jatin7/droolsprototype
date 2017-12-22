package com.uhg.sherlock.drools.common;

public class Constants {
	public static final String SP_GETNPI = "srk_drools_getnpi";
	public static final String SP_GETTIN = "srk_drools_gettin";
	public static final String SP_GETCPTCODE = "srk_drools_getcptcodes";
	public static final String SP_GETPROCCODE = "srk_drools_getproccd";
	public static final String SP_GETTAXONOMY = "srk_drools_gettaxonomy";
	public static final String DROOLS_ORC_GET_BATCHID = "{call dbo.usp_srk_DROOLSORC_GetNextAvailableBatchId (?)}";
	public static final String DROOLS_COMPLETED_SP = "{call dbo.usp_srk_DROOLSRORC_Completed (?)}";
	public static final String DROOLS_STARTED_SP = "{call dbo.usp_srk_Drools_Started (?)}";
	public static final String BATCH_ABORTED_SP = "{call dbo.usp_srk_BatchConnector_AbortBatch (?,?,?,?)}";

}
