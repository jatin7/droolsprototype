package com.uhg.sherlock.spark.functions;

import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;
import com.uhc.sherlock.orchestration.dto.ClaimDTO;
import com.uhg.sherlock.drools.common.Utils;
import com.uhg.sherlock.drools.dao.BatchProcessStateHelper;
import com.uhg.sherlock.spark.ClaimRuleResult;

/**
 * Created by rlingaia on 6/7/2017.
 */
public class ExecuteBatchVoidFunction implements VoidFunction<JavaRDD<String>> {
	private static final long serialVersionUID = 666155973263523339L;

	static Logger logger = Logger.getLogger("Drools");

	@Override
	public void call(JavaRDD<String> batchIdRDD) throws Exception {
		if (!batchIdRDD.isEmpty()) {
			try {

				List<String> batches = batchIdRDD.collect();

				// Every Dstream is expected to receive max of 1 batch from
				// SQLServer.

				for (String batch : batches) {
					BatchProcessStateHelper.droolsOrcStarted(batch);
					logger.info("Batch: " + batch + " marked started in SQLSERVER");
				}

				long startTime = System.currentTimeMillis();

				Configuration config = HBaseConfiguration.create();

				config.set(TableOutputFormat.OUTPUT_TABLE, Utils.getString("sherlock.drools.resultmaprdb"));

				JavaRDD<ClaimDTO> claimRDD = batchIdRDD.flatMap(new GetClaimsFlatMapFunction()).repartition(10);
				String droolSession = Utils.getString("sherlock.drools.sessionname");

				JavaRDD<ClaimRuleResult> claimRuleResultRDD = claimRDD
						.mapPartitions(new ExecuteRulesFlatMapFunction(droolSession));

				JavaPairRDD<ImmutableBytesWritable, Put> resultRDD = claimRuleResultRDD
						.mapToPair(new MaprDBResultPairFn());

				resultRDD.saveAsNewAPIHadoopFile("DROOLS_PROTOTYPE", ImmutableBytesWritable.class, Put.class,
						TableOutputFormat.class, config);

				long endTime = System.currentTimeMillis();

				logger.info("#########This batch took " + (endTime - startTime) + " milliseconds########");

				for (String batch : batches) {
					BatchProcessStateHelper.droolsOrcCompleted(batch);
					logger.info("Batch:" + batch + " marked completed in SQLSERVER");
				}
				//Runtime.getRuntime().exit(0);
			}
			catch (Exception e) {
				e.printStackTrace();
				for (String batch : batchIdRDD.collect()) {
					BatchProcessStateHelper.droolsOrcAborted(batch, e.getMessage());
					logger.error("Batch:" + batch + " marked aborted in SQLSERVER", e);
				}
			}
		}
	}
}
