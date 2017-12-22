package com.uhg.sherlock.drools.service;

/**
 * Created by rlingaia on 6/6/2017.
 */
public class ServiceFactory {

    public IClaimService getClaimService(){
        return new ClaimService();
    }

}
