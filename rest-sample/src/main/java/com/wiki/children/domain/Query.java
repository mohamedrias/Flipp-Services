package com.wiki.children.domain;

import java.util.List;

/**
 * Modal Class Query
 */
public class Query{

	private String playListId;
	private String question;
	private String description;
	private int minAge;
	private int maxAge;
	private List<String> references;

	public String getPlayListId() {
		return playListId;
	}
	
	public void setPlayListId(String playListId) {
		this.playListId = playListId;
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getMinAge() {
		return minAge;
	}
	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}
	
	public int getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
	
	public List<String> getReferences() {
		return references;
	}
	public void setReferences(List<String> references) {
		this.references = references;
	}
}
