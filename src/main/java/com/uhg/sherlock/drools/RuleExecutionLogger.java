package com.uhg.sherlock.drools;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 * Created by rlingaia on 6/12/2017.
 */

public class RuleExecutionLogger {

        private static final Logger logger = Logger.getLogger(RuleExecutionLogger.class);

        public static boolean logRuleExecution(String ruleName){
            //  System.err.println("logRuleExecution " + ruleName);
            MDC.put("EVENTTYPE","RULEEXECUTION");
            logger.info(ruleName);
            return true;
        }
}
