package com.uhg.sherlock.drools;

import com.uhc.oracle.Claim;
import com.uhg.sherlock.drools.service.ServiceFactory;
import com.uhg.sherlock.spark.ClaimRuleResult;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the driver class that is responsible for executing business claim on rulesInput data and return output
 * Created by rlingaia on 6/6/2017.
 */
public class DroolsRulesExecutor implements RulesExecutor {

    private String sessionName;
    private StatelessKieSession statelessKieSession;
    private Logger ruleLogger = LoggerFactory.getLogger("RuleLogger");
    private static final Logger logger = LoggerFactory.getLogger(DroolsRulesExecutor.class.getName());

    public DroolsRulesExecutor(String sessionName){
        this.sessionName = sessionName;
    }

    private static KieContainer kContainer = null;

    public StatelessKieSession getKieSession() {
        logger.debug("Entering DroolsRuleExecutor.setup()");
        String sessionName = "session-" + this.sessionName;
        StatelessKieSession kieSession;

        if(statelessKieSession == null) {
            logger.debug("Loading new instance of kContainer");
            KieServices ks = KieServices.Factory.get();
            
            this.kContainer = ks.getKieClasspathContainer();
            System.out.println(kContainer+"is the value of kContainer");
        }
        //  KieBaseConfiguration kieBaseConfiguration = KieServices.Factory.get().newKieBaseConfiguration();
        //  kieBaseConfiguration.setOption(EventProcessingOption.STREAM);
        logger.debug("Creating session, sessionName -> " + sessionName);
        statelessKieSession = this.kContainer.newStatelessKieSession(sessionName);
        statelessKieSession.setGlobal("RuleLogger", ruleLogger);
        statelessKieSession.setGlobal("ServiceFactory", new ServiceFactory());

        logger.debug("Exiting DroolsRuleExecutor.setup()");
        return statelessKieSession;
    }

    /**
     * This method is responible for actual executing the claim and returning results
     * @param objectList
     * @return
     */
    public void executeRules(List<Object> objectList){
        logger.debug("Entering DroolsRuleExecutor.executeRules() ");

        if (statelessKieSession == null) {
            this.statelessKieSession = getKieSession();
        }

        statelessKieSession.execute(objectList);
        logger.debug("Exiting DroolsRuleExecutor.executeRules() ");
    }

    /**
     * This method is responsible for executing the cleanup logic
     */
    public void shutdown(){
        //kieSession..
    }

    public void main(String[] argv){
        DroolsRulesExecutor droolsRulesExecutor = new DroolsRulesExecutor("addclaim");
        droolsRulesExecutor.executeRules(new ArrayList<Object>());

    }
}
