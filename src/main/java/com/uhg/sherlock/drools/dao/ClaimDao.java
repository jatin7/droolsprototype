package com.uhg.sherlock.drools.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ranjit lingaiah on 6/06/17.
 */
public class ClaimDao implements IClaimDao {

	private static Set<String> npiSet = ClaimDaoHelper.getNpiSet();
	private static Set<String> tinSet = ClaimDaoHelper.getTinSet();
	private static Set<String> procCodeSet = ClaimDaoHelper.getProcCodeSet();
	private static Set<String> taxnmyCodeSet= ClaimDaoHelper.getTaxonomySet();
	
	@Override
	public List<String> lookupNpi() {
		return new ArrayList<String>(npiSet);
	}

	@Override
	public List<String> lookupTIN() {
		return new ArrayList<String>(tinSet);
	}

	@Override
	public List<String> lookupPROCCODE() {
		return new ArrayList<String>(procCodeSet);
	}

	@Override
	public List<String> lookupTXNMYCODE() {
		return new ArrayList<String>(taxnmyCodeSet);
	}

}
