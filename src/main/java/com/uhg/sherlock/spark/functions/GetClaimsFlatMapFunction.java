package com.uhg.sherlock.spark.functions;

import com.uhc.sherlock.orchestration.dto.ClaimDTO;
import com.uhc.sherlock.orchestration.main.GetClaimList;
import org.apache.spark.api.java.function.FlatMapFunction;


/**
 * Created by rlingaia on 6/7/2017.
 */
public class GetClaimsFlatMapFunction implements FlatMapFunction<String, ClaimDTO> {
        private static final long serialVersionUID = 3826351L;

        @Override
        public Iterable<ClaimDTO> call(String batchId) throws Exception {
            return GetClaimList.claimLstFrmBatch(batchId);
        }
}
