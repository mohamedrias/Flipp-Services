package com.wiki.children.repository;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Element;

import com.clusterpoint.api.*;
import com.clusterpoint.api.request.CPSSearchRequest;
import com.clusterpoint.api.response.CPSSearchResponse;
import com.wiki.children.domain.Query;

@Repository("queryRepository")
public class QueryRepository {
	
	private static final Logger logger_c = Logger.getLogger(QueryRepository.class);
	
	@Autowired
	private CPSConnection conn;
	
	private void terminateConnection(){
		try {
			conn.close();
		} catch (IOException exception) {
			logger_c.error("Error occured while terminating the connection :" + exception.getMessage());
		}
	}
	
	public List<Query> lookupForPlayList(int age){
		
		String query = buildAgeBasedQuery(age);
		System.out.println(query);
		
		try {
			CPSSearchRequest cpsSearchRequest = new CPSSearchRequest(query, 0, 100);
			cpsSearchRequest.setRequestType(0);
			CPSSearchResponse searchResponse = (CPSSearchResponse)conn.sendRequest(cpsSearchRequest);
			
			List<Element> results = searchResponse.getDocuments();    

			for(Element element: results){
				System.out.println(element.getTagName()+ " -- " +element.getTextContent());
			}
			
			terminateConnection();
		} catch (Exception exception) {
			logger_c.error("Error occured while maaking a lookup :" + exception.getMessage());
		}
		return null;
	}
	
	private String buildAgeBasedQuery(int age){
		return "<query>" +
				"<minAge>&lt;="+age+"</minAge>" +
				"<maxAge>&gt;="+age+"</maxAge>" +
				"</query>" +
				"<docs>20</docs>" +
				"<list>" +
				"<document>yes</document>" +
				"</list>";
	}
}
