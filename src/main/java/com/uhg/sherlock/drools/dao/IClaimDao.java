package com.uhg.sherlock.drools.dao;

import java.util.List;
/**
 * Created by ranjit lingaiah  on 6/06/17.
 */
public interface IClaimDao {
    public List<String> lookupNpi();
    
   public List<String> lookupTIN();
    
    public List<String> lookupPROCCODE();
    
    public List<String> lookupTXNMYCODE();
}
