package com.uhg.sherlock.drools;

import com.uhc.oracle.Claim;
import com.uhc.sherlock.orchestration.dto.ClaimDTO;
import com.uhc.sherlock.orchestration.dto.ServiceLineDTO;
import com.uhg.sherlock.drools.dao.ClaimDao;
import com.uhg.sherlock.drools.dao.ClaimDaoHelper;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

/**
 * Created by rlingaia on 6/6/2017.
 */

public class RulesExecutorTest {

	protected RulesExecutor rulesExecutor;
	protected List<String> npiFeedList = new ArrayList<String>();
	protected List<String> tinFeedList = new ArrayList<String>();
	protected List<String> procFeedList = new ArrayList<String>();
	protected List<String> txnmyFeedList = new ArrayList<String>();
	@org.junit.Before
	public void setup() throws Exception {
		PowerMockito.mockStatic(ClaimDaoHelper.class);
		ClaimDao claimDao = mock(ClaimDao.class);
		PowerMockito.whenNew(ClaimDao.class).withNoArguments().thenReturn(claimDao);
		Mockito.when(claimDao.lookupPROCCODE()).thenReturn(procFeedList);
		Mockito.when(claimDao.lookupTIN()).thenReturn(tinFeedList);
		Mockito.when(claimDao.lookupNpi()).thenReturn(npiFeedList);
		Mockito.when(claimDao.lookupTXNMYCODE()).thenReturn(txnmyFeedList);
		
	}

	public ClaimDTO generateClaimForRules(boolean NPI, boolean TIN, boolean PROCCODE,boolean TXNMYCODE) {
		String npi = NPI ? "1467527739" : "1467527738";
		String proccode = PROCCODE ? "99203" : "99201";
		String tin = TIN ? "830342633" : "830342632";
		String txnmy = TXNMYCODE ? "1073664843" : "1073664842";
		ClaimDTO c = null;
		List<ServiceLineDTO> serviceLineList = new ArrayList<ServiceLineDTO>();
		ServiceLineDTO serviceLineDTO = new ServiceLineDTO("", proccode, "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "");
		serviceLineList.add(serviceLineDTO);

		c = new ClaimDTO("", "", "", "", "", "", "", "", "", "", tin, "", npi, txnmy, "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "112245", "", serviceLineList);

		return c;

	}

}
