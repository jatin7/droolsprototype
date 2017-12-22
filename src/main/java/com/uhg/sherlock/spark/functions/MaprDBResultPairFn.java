/**
 * 
 */
package com.uhg.sherlock.spark.functions;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.spark.api.java.function.PairFunction;

import com.uhg.sherlock.spark.ClaimRuleResult;

import scala.Tuple2;

/**
 * @author vamdiyal
 * 
 */
public class MaprDBResultPairFn implements PairFunction<ClaimRuleResult, ImmutableBytesWritable, Put> {

	private static final String CF = "r";
	private static final String REC_COL = "recommendation";
	private static final String RULES_COL = "claim";

	private static final long serialVersionUID = -3108267573373010933L;

	@Override
	public Tuple2<ImmutableBytesWritable, Put> call(ClaimRuleResult claimRuleResult) throws Exception {

		String rowKey = claimRuleResult.getSherlockId();
		Put put = new Put(rowKey.getBytes());
		put.addColumn(CF.getBytes(), REC_COL.getBytes(), claimRuleResult.getRecommendation().getBytes());

		put.addColumn(CF.getBytes(), RULES_COL.getBytes(), claimRuleResult.getRules().getBytes());

		return new Tuple2<ImmutableBytesWritable, Put>(new ImmutableBytesWritable(rowKey.getBytes()), put);
	}

}
