package co.aurasphere.interview.server.rest.model;

import co.aurasphere.interview.server.model.Survey;
import co.aurasphere.interview.server.rest.SurveyRestController;

/**
 * Response for {@link SurveyRestController#getSurvey(String)}.
 * 
 * @author Donato Rimenti
 */
public class GetSurveyResponse extends BaseInterviewServletRestResponse {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The survey.
	 */
	private Survey survey;

	/**
	 * Instantiates a new GetSurveyResponse.
	 */
	public GetSurveyResponse() {
	}

	/**
	 * Instantiates a new GetSurveyResponse.
	 *
	 * @param survey
	 *            the {@link #survey}.
	 */
	public GetSurveyResponse(Survey survey) {
		this.survey = survey;
	}

	/**
	 * Gets the {@link #survey}.
	 *
	 * @return the {@link #survey}.
	 */
	public Survey getSurvey() {
		return survey;
	}

	/**
	 * Sets the {@link #survey}.
	 *
	 * @param survey
	 *            the new {@link #survey}.
	 */
	public void setSurvey(Survey survey) {
		this.survey = survey;
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
		result = prime * result + ((survey == null) ? 0 : survey.hashCode());
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
		GetSurveyResponse other = (GetSurveyResponse) obj;
		if (survey == null) {
			if (other.survey != null)
				return false;
		} else if (!survey.equals(other.survey))
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
		return "GetSurveyResponse [survey=" + survey + "]";
	}
}
