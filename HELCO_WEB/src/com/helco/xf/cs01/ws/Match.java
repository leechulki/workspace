package com.helco.xf.cs01.ws;

import java.util.ArrayList;
import java.util.List;

public class Match
		implements java.io.Serializable {

	/**
	 * 블럭시퀀스별 정합성 변수
	 * @param ctx
	 * @throws Exception
	 */
	private static final long serialVersionUID = 1L;

	private MatchDuty matchDuty;
	
	private List<MatchDutyD> matchDutyList;
	
	public Match() {
		matchDuty     = new MatchDuty();
		matchDutyList = new ArrayList<MatchDutyD>();
	}

	public MatchDuty getMatchDuty() {
		return matchDuty;
	}

	public void setMatchDuty(MatchDuty matchDuty) {
		this.matchDuty = matchDuty;
	}

	public List<MatchDutyD> getMatchDutyList() {
		return matchDutyList;
	}

	public void setMatchDutyList(List<MatchDutyD> matchDutyList) {
		this.matchDutyList = matchDutyList;
	}

}