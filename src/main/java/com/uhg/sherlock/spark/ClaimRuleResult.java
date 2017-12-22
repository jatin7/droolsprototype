package com.uhg.sherlock.spark;

import java.io.Serializable;

public class ClaimRuleResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -474732726084377985L;

	public ClaimRuleResult(String sherlockId) {
		super();
		this.sherlockId = sherlockId;
	}

	private String sherlockId;
	private String recommendation = "false";
	private String rules = "";

	public String getSherlockId() {
		return sherlockId;
	}

	public void setSherlockId(String sherlockId) {
		this.sherlockId = sherlockId;
	}

	@Override
	public String toString() {
		return "ClaimRuleResult [sherlockId=" + sherlockId + ", recommendation=" + recommendation + ", rules=" + rules
				+ "]";
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

}
