package com.wiki.children.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.wiki.children.domain.Query;
import com.wiki.children.repository.QueryRepository;

/**
 * Query Service
 */
@Service
public class QueryService {

	private static final Logger logger_c = Logger.getLogger(QueryService.class);
	
	@Autowired
	private QueryRepository queryRepository;

	/**
	 * Get the query by id.
	 *
	 * @param queryId the query id
	 *            
	 * @return the query by id
	 */
	public Query getQueryById(String queryId) {
		Query query = new Query();

		query.setPlayListId(queryId);
		query.setQuestion("why sun rises in east?");
		query.setDescription("Earth rotates or spins toward the east, and that's why the Sun, Moon, planets, and stars all rise in the east and make their way westward across the sky");
		query.setMinAge(6);
		query.setMaxAge(10);
		
		List<String> references = new ArrayList<String>();
		references.add("url1");
		references.add("url2");
		
		query.setReferences(references);

		return query;
	}

	/**
	 * Gets all queries.
	 *
	 * @return the all queries
	 */
	public List<Query> getAllQueries() {
		List<Query> queries = new ArrayList<Query>();
		List<String> references = null;

		for (int i = 0; i < 10; i++) {
			Query query = new Query();

			query.setPlayListId("Q0"+i);
			query.setQuestion("why sun rises in east?");
			query.setDescription("Earth rotates or spins toward the east, and that's why the Sun, Moon, planets, and stars all rise in the east and make their way westward across the sky");
			query.setMinAge(6);
			query.setMaxAge(10);
			
			references =  new ArrayList<String>();
			references.add("url1");
			references.add("url2");
			
			query.setReferences(references);

			queries.add(query);
		}
		return queries;
	}

	/**
	 * Creates the query.
	 *
	 * @param query the query
	 *            
	 * @return the query
	 */
	public Query createQuery(Query query) {

		logger_c.debug("Adding a new query to database: " + query.getPlayListId());

		query.setPlayListId("12345");
		query.setQuestion("why sun rises in east?");
		query.setDescription("Earth rotates or spins toward the east, and that's why the Sun, Moon, planets, and stars all rise in the east and make their way westward across the sky");
		query.setMinAge(6);
		query.setMaxAge(10);
		
		List<String> references = new ArrayList<String>();
		references.add("url1");
		references.add("url2");
		
		query.setReferences(references);

		return query;
	}

	/**
	 * Update query.
	 *
	 * @param query the query
	 *            
	 * @return the query
	 */
	public Query updateQuery(Query query) {

		logger_c.debug("Updating Query in database: " + query.getPlayListId());

		query.setQuestion("why sun rises in east?");
		query.setDescription("Earth rotates or spins toward the east, and that's why the Sun, Moon, planets, and stars all rise in the east and make their way westward across the sky");
		query.setMinAge(6);
		query.setMaxAge(10);
		
		List<String> references = new ArrayList<String>();
		references.add("url1");
		references.add("url2");
		references.add("url3");
		references.add("url4");
		
		query.setReferences(references);

		return query;
	}

	/**
	 * Delete query.
	 *
	 * @param queryId the query id
	 *           
	 */
	public void deleteQuery(String queryId) {
		logger_c.debug("Deleting query from database: " + queryId);

	}
	
	public List<Query> getQueriesByAge(int age){
		logger_c.debug("Retriving queries by age: " + age);
		
		List<Query> qualified = new ArrayList<Query>();
		queryRepository = new QueryRepository();
		queryRepository.lookupForPlayList(age);
		
		/*for(Query query : getAllQueries()){
			if(query.getMinAge() <= age && query.getMaxAge() >= age){
				qualified.add(query);
			}
		}*/
		
		return qualified;
	}

}
