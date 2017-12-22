package com.uhg.sherlock.drools;
import java.util.List;
/**
 * Implement this interface for providing support for claim engine of your choice
 * Created by rlingaia on 6/6/2017.
 */
public interface RulesExecutor {
    /**
     * Implement this method with claim engine execution logic specific to your claim engine
     * @param objectList
     * @return
     */
    public void executeRules(List<Object> objectList);

    /**
     * Implement this method with claim engine shutdown related logic specific to your claim engine
     */
    public void shutdown();
}
