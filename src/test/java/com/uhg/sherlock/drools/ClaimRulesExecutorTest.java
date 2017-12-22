package com.uhg.sherlock.drools;

import com.uhc.oracle.Claim;
import com.uhc.sherlock.orchestration.dto.ClaimDTO;
import com.uhg.sherlock.drools.dao.ClaimDao;
import com.uhg.sherlock.drools.dao.ClaimDaoHelper;
import com.uhg.sherlock.drools.service.ClaimService;
import com.uhg.sherlock.spark.ClaimRuleResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;

/**
 * Created by rlingaia on 6/6/2017.
 */
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor({ "com.uhg.sherlock.drools.dao.ClaimDaoHelper" })

public class ClaimRulesExecutorTest extends RulesExecutorTest {

	private ClaimRuleResult execute(ClaimDTO claim) {
		rulesExecutor = new DroolsRulesExecutor("claimrules");
		ClaimRuleResult claimRuleResult = new ClaimRuleResult(claim.getSherlock_claim_id());
		List<Object> objectList = new ArrayList<Object>();
		objectList.add(claim);
		objectList.add(claimRuleResult);
		rulesExecutor.executeRules(objectList);
		return claimRuleResult;

	}

	private ClaimRuleResult executeRulesGroup(ClaimDTO claim) {
		this.rulesExecutor = new DroolsRulesExecutor("claimgrouprules");
		ClaimRuleResult claimRuleResult = new ClaimRuleResult(claim.getSherlock_claim_id());
		List<Object> objectList = new ArrayList<Object>();
		objectList.add(claim);
		objectList.add(claimRuleResult);
		rulesExecutor.executeRules(objectList);
		return claimRuleResult;

	}

	@PrepareForTest({ ClaimDaoHelper.class, ClaimService.class })
	@Test
	public void testClaimNpi() throws Exception {
		npiFeedList.add("1467527739");
		ClaimDTO claim = generateClaimForRules(true, false, false, false);
		System.out.println("Executing Claim npi Test.");
		ClaimRuleResult claimRuleResult = execute(claim);
		Assert.assertTrue(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("npi", "npi");
		System.out.println("Positive test Rules Output:" + claimRuleResult);
		claim = generateClaimForRules(false, false, false, false);
		claimRuleResult = execute(claim);
		Assert.assertFalse(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("", "");
		System.out.println("Negative testRules Output:" + claimRuleResult);
	}

	@PrepareForTest({ ClaimDaoHelper.class, ClaimService.class })
	@Test
	public void testClaimTIN() throws Exception {
		tinFeedList.add("830342633");
		ClaimDTO claim = generateClaimForRules(false, true, false, false);
		System.out.println("Executing Claim tin Test.");
		ClaimRuleResult claimRuleResult = execute(claim);
		Assert.assertTrue(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("tin", "tin");
		System.out.println("Positive test Rules Output:" + claimRuleResult);
		claim = generateClaimForRules(false, false, false, false);
		claimRuleResult = execute(claim);
		Assert.assertFalse(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("", "");
		System.out.println("Negative testRules Output:" + claimRuleResult);
	}

	@PrepareForTest({ ClaimDaoHelper.class, ClaimService.class })
	@Test
	public void testClaimPROCCODE() throws Exception {
		procFeedList.add("99203");
		ClaimDTO claim = generateClaimForRules(false, false, true, false);
		ClaimRuleResult claimRuleResult = execute(claim);
		System.out.println("Executing Claim proc Test.");
		Assert.assertTrue(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("proccode", "proccode");
		System.out.println("Positive test Rules Output:" + claimRuleResult);
		claim = generateClaimForRules(false, false, false, false);
		claimRuleResult = execute(claim);
		Assert.assertFalse(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("", "");
		System.out.println("Negative testRules Output:" + claimRuleResult);
	}

	@PrepareForTest({ ClaimDaoHelper.class, ClaimService.class })
	@Test
	public void testClaimTXNMYCODE() throws Exception {
		txnmyFeedList.add("1073664843");
		ClaimDTO claim = generateClaimForRules(false, false, false, true);
		ClaimRuleResult claimRuleResult = execute(claim);
		System.out.println("Executing Txnmy Test.");
		Assert.assertTrue(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("txnmycode", "txnmycode");
		System.out.println("Positive test Rules Output:" + claimRuleResult);
		claim = generateClaimForRules(false, false, false, false);
		claimRuleResult = execute(claim);
		Assert.assertFalse(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("", "");
		System.out.println("Negative testRules Output:" + claimRuleResult);
	}

	@PrepareForTest({ ClaimDaoHelper.class, ClaimService.class })
	@Test
	public void testClaimMultipleRules() throws Exception {
		npiFeedList.add("1467527739");
		tinFeedList.add("830342633");
		procFeedList.add("99203");
		ClaimDTO claim = generateClaimForRules(true, false, true, false);
		ClaimRuleResult claimRuleResult = execute(claim);
		System.out.println("Executing multi rule npi,proc Claim Test.");
		Assert.assertTrue(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("npi,proccode", "npi,proccode");

		System.out.println("Executing multi rule npi,tin Claim Test.");
		claim = generateClaimForRules(true, true, false, false);
		claimRuleResult = execute(claim);
		Assert.assertTrue(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("npi,tin", "npi,tin");

		System.out.println("Executing multi rule npi,tin,proc Claim Test.");
		claim = generateClaimForRules(true, true, true, false);
		claimRuleResult = execute(claim);
		Assert.assertTrue(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("npi,tin,proccode", "npi,tin,proccode");

		claim = generateClaimForRules(false, true, true, false);
		claimRuleResult = execute(claim);
		Assert.assertTrue(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("tin,proccode", "tin,proccode");
		
		claim = generateClaimForRules(false, true, true, true);
		claimRuleResult = execute(claim);
		Assert.assertTrue(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("tin,proccode,txnmycode", "tin,proccode,txnmycode");
		

	}

	@PrepareForTest({ ClaimDaoHelper.class, ClaimService.class })
	@Test
	public void testClaimNpiGroupRules() throws Exception {
		npiFeedList.add("1467527739");
		ClaimDTO claim = generateClaimForRules(true, false, false, false);
		System.out.println("Executing Claim npi Test.");
		ClaimRuleResult claimRuleResult = this.executeRulesGroup(claim);
		Assert.assertTrue(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("npi", "npi");
		System.out.println("Positive test Rules Output:" + claimRuleResult);
		claim = generateClaimForRules(false, false, false, false);
		claimRuleResult = execute(claim);
		Assert.assertFalse(Boolean.valueOf(claimRuleResult.getRecommendation()));
		Assert.assertEquals("", "");
		System.out.println("Negative testRules Output:" + claimRuleResult);
	}

}
