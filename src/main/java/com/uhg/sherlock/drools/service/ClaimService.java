package com.uhg.sherlock.drools.service;

import com.uhc.sherlock.orchestration.dto.ClaimDTO;
import com.uhc.sherlock.orchestration.dto.ServiceLineDTO;
import com.uhg.sherlock.drools.dao.ClaimDao;
import com.uhg.sherlock.drools.dao.IClaimDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by rlingaia on 6/6/2017.
 */
public class ClaimService implements IClaimService {
	private static final Logger logger = LoggerFactory.getLogger(ClaimService.class.getName());
	private static IClaimDao dao = new ClaimDao();

	@Override
	public boolean validateNPI(ClaimDTO claimDTO) {

		List<String> npiList = dao.lookupNpi();
		if (npiList.contains(claimDTO.getBill_prov_npi())) {
			return true;
		}

		return false;
	}

	@Override
	public boolean validateTIN(ClaimDTO claimDTO) {
		List<String> tinList = dao.lookupTIN();
		if (tinList.contains(claimDTO.getBill_prov_tin())) {
			return true;
		}

		return false;
	}

	@Override
	public boolean validatePROCCODE(ClaimDTO claimDTO) {
		List<String> pList = dao.lookupPROCCODE();
		for (ServiceLineDTO serviceLineDTO : claimDTO.getServiceList()) {
			if (pList.contains(serviceLineDTO.getProc_cd()))
				return true;
		}
		return false;

	}

	@Override
	public boolean validateTXNMYCODE(ClaimDTO claimDTO) {
		List<String> txList = dao.lookupTXNMYCODE();
		System.out.println("ClaimDTO has this value"+txList);
		if (txList.contains(claimDTO.getBill_prov_tax_cd())) {
			return true;
		}
		return false;
	}

}
