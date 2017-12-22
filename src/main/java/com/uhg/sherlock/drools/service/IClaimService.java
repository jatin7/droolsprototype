package com.uhg.sherlock.drools.service;

import com.uhc.sherlock.orchestration.dto.ClaimDTO;

/**
 * Created by rlingaia on 6/6/2017.
 */
public interface IClaimService {
    /*
    * Validate NPI against a known list of  NPIs
   */
    boolean validateNPI(ClaimDTO claimDTO);
    /*
     * Validate TIN against a known list of  TINs
    */
    
    boolean validateTIN(ClaimDTO claimDTO);
    
    /*
     * Validate PROCCODE against a known list of  PROCCODES
    */
    boolean validatePROCCODE(ClaimDTO claimDTO);
    
 /*
  * Validate TaxonomyCode against a known list of taxonomy codes.
  */
    
    boolean validateTXNMYCODE(ClaimDTO claimDTO);
}
