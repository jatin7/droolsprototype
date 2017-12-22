package com.uhg.sherlock.spark.functions;

import com.uhc.sherlock.orchestration.dto.ClaimDTO;
import com.uhg.sherlock.drools.DroolsRulesExecutor;
import com.uhg.sherlock.drools.common.Utils;
import com.uhg.sherlock.spark.ClaimRuleResult;
import org.apache.spark.api.java.function.FlatMapFunction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


/**
 * Created by rlingaia on 6/7/2017.
 */
public class ExecuteRulesFlatMapFunction implements FlatMapFunction<Iterator<ClaimDTO>, ClaimRuleResult> {

	private String sessionName;
    private static final long serialVersionUID = 9126409843815630298L;

    public ExecuteRulesFlatMapFunction(String sessionName) {
		super();
		this.sessionName = sessionName;
	}

	

	@Override
    public Iterable<ClaimRuleResult> call(Iterator<ClaimDTO> t) throws Exception {
        DroolsRulesExecutor rulesExecutor = new DroolsRulesExecutor(sessionName);
        List<ClaimRuleResult> claimRuleResultList = new ArrayList<ClaimRuleResult>();
        while (t.hasNext()) {
            ClaimDTO claimDTO = t.next();
            ClaimRuleResult claimRuleResult = new ClaimRuleResult(claimDTO.getSherlock_claim_id());
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(claimDTO);
            objectList.add(claimRuleResult);
            rulesExecutor.executeRules(objectList);
            claimRuleResultList.add(claimRuleResult);
        }
        return claimRuleResultList;

    }
}
