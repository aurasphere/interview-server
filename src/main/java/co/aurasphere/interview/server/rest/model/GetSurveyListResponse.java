package co.aurasphere.interview.server.rest.model;

import java.util.Set;

import co.aurasphere.interview.server.rest.SurveyRestController;

/**
 * Response for {@link SurveyRestController#getSurveyList(String, String)}.
 * 
 * @author Donato Rimenti
 */
public class GetSurveyListResponse extends BaseInterviewServletRestResponse {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The survey list.
	 */
	private Set<String> surveyList;

	/**
	 * Instantiates a new GetSurveyListResponse.
	 */
	public GetSurveyListResponse() {
	}

	/**
	 * Instantiates a new GetSurveyListResponse.
	 *
	 * @param surveyList
	 *            the {@link #surveyList}.
	 */
	public GetSurveyListResponse(Set<String> surveyList) {
		this.outcome = true;
		this.surveyList = surveyList;
	}

	/**
	 * Gets the {@link #surveyList}.
	 *
	 * @return the {@link #surveyList}.
	 */
	public Set<String> getSurveyList() {
		return surveyList;
	}

	/**
	 * Sets the {@link #surveyList}.
	 *
	 * @param surveyList
	 *            the new {@link #surveyList}.
	 */
	public void setSurveyList(Set<String> surveyList) {
		this.surveyList = surveyList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.aurasphere.interview.server.rest.model.
	 * BaseInterviewServletRestResponse#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((surveyList == null) ? 0 : surveyList.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.aurasphere.interview.server.rest.model.
	 * BaseInterviewServletRestResponse#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GetSurveyListResponse other = (GetSurveyListResponse) obj;
		if (surveyList == null) {
			if (other.surveyList != null)
				return false;
		} else if (!surveyList.equals(other.surveyList))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.aurasphere.interview.server.rest.model.
	 * BaseInterviewServletRestResponse#toString()
	 */
	@Override
	public String toString() {
		return "GetSurveyListResponse [surveyList=" + surveyList + "]";
	}

}
