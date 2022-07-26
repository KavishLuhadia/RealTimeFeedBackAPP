package com.kavish.model.request;


import java.util.ArrayList;
import java.util.List;

public class TwitterStreamRuleRequest {

	private List<TwitterStreamRule> add = new ArrayList<>();

	public List<TwitterStreamRule> getAdd() {
		return add;
	}

	public void setAdd(List<TwitterStreamRule> add) {
		this.add = add;
	}
	
	public void addRule(String value, String tag) {
		this.add.add(new TwitterStreamRule(value,tag));
	}
}

