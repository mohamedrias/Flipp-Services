package com.wiki.children.webservices.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.wiki.children.domain.Query;
import com.wiki.children.services.QueryService;

/**
 * QueryController class will expose a series of RESTful endpoints
 */
@Controller
public class QueryController {

	@Autowired
	private QueryService queryService;

	@Autowired
	private View jsonView;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(QueryController.class);

	/**
	 * Gets a query by query id.
	 *
	 * @param queryId the queryId
	 *            
	 * @return the query
	 */
//	@RequestMapping(value = "/rest/queries/{queryId}", method = RequestMethod.GET)
	public ModelAndView getQuery(@PathVariable("queryId") String queryId) {
		Query query = null;

		/* validate query Id parameter */
		if (isEmpty(queryId) || queryId.length() > 10) {
			String sMessage = "Error invoking getQuery - Invalid query Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			query = queryService.getQueryById(queryId);
		} catch (Exception e) {
			String sMessage = "Error invoking getQuery.";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Query: " + queryId);
		return new ModelAndView(jsonView, DATA_FIELD, query);
	}
	
	/**
	 * Gets a query by query id.
	 *
	 * @param queryId the queryId
	 *            
	 * @return the query
	 */
	@RequestMapping(value = "/rest/queries/{age}", method = RequestMethod.GET)
	public ModelAndView getPlayListByAge(@PathVariable("age") int age) {
		List<Query> queryList = null;

		/* validate query Id parameter */
		if (age<=0) {
			String sMessage = "Error invoking getPlayListByAge - Invalid age parameter";
			return createErrorResponse(sMessage);
		}

		try {
			queryList = queryService.getQueriesByAge(age);
		} catch (Exception e) {
			String sMessage = "Error invoking getPlayListByAge.";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("No of results found: " + queryList.size());
		return new ModelAndView(jsonView, DATA_FIELD, queryList);
	}

	/**
	 * Gets all querys.
	 *
	 * @return the querys
	 */
	@RequestMapping(value = "/rest/queries/", method = RequestMethod.GET)
	public ModelAndView getQueries() {
		List<Query> queries = null;

		try {
			queries = queryService.getAllQueries();
		} catch (Exception e) {
			String sMessage = "Error getting all queries.";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Total queries found: " + queries.size());
		return new ModelAndView(jsonView, DATA_FIELD, queries);
	}

	/**
	 * Creates a new query.
	 *
	 * @param query  the query
	 *           
	 * @return the model and view
	 */
	@RequestMapping(value = { "/rest/queries/" }, method = { RequestMethod.POST })
	public ModelAndView createQuery(@RequestBody Query query,
			HttpServletResponse httpResponse, WebRequest request) {

		Query queryTobeCreated;
		logger_c.debug("Creating Query: " + query.getPlayListId());

		try {
			queryTobeCreated = queryService.createQuery(query);
		} catch (Exception e) {
			String sMessage = "Error creating new query.";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse.setHeader("Location", request.getContextPath() + "/rest/queries/" + query.getPlayListId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView, DATA_FIELD, queryTobeCreated);
	}

	/**
	 * Updates query with given query id.
	 *
	 * @param query - The query
	 *            
	 * @return the model and view
	 */
	@RequestMapping(value = { "/rest/queries/{queryId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateQuery(@RequestBody Query query, @PathVariable("queryId") String queryId,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating query: " + query.getPlayListId());

		/* validate query Id parameter */
		if (isEmpty(queryId) || queryId.length() > 10) {
			String sMessage = "Error updating query - Invalid query Id parameter";
			return createErrorResponse(sMessage);
		}

		Query queryUpdate = null;

		try {
			queryUpdate = queryService.updateQuery(query);
		} catch (Exception e) {
			String sMessage = "Error updating query.";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView, DATA_FIELD, queryUpdate);
	}

	/**
	 * Deletes the query with the given query id.
	 *
	 * @param queryId
	 *            the query id
	 * @return the model and view
	 */
	@RequestMapping(value = "/rest/queries/{queryId}", method = RequestMethod.DELETE)
	public ModelAndView removeQuery(@PathVariable("queryId") String queryId,
								   HttpServletResponse httpResponse) {

		logger_c.debug("Deleting Query Id: " + queryId);

		if (isEmpty(queryId) || queryId.length() > 10) {
			String sMessage = "Error deleting query - Invalid query Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			queryService.deleteQuery(queryId);
		} catch (Exception e) {
			String sMessage = "Error invoking removeQuery.";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView, DATA_FIELD, null);
	}
	
	

	public static boolean isEmpty(String content) {
		return (null == content) || content.trim().length() == 0;
	}

	/**
	 * Create an error REST response.
	 *
	 * @param sMessage
	 *            the s message
	 * @return the model and view
	 */
	private ModelAndView createErrorResponse(String sMessage) {
		return new ModelAndView(jsonView, ERROR_FIELD, sMessage);
	}

	/**
	 * Injector methods.
	 *
	 * @param queryServiceParam
	 *            the new query service
	 */
	public void setQueryService(QueryService queryServiceParam) {
		queryService = queryServiceParam;
	}

	/**
	 * Injector methods.
	 *
	 * @param view - The new json view
	 *           
	 */
	public void setJsonView(View view) {
		jsonView = view;
	}

}
