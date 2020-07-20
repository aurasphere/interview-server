package co.aurasphere.interview.server.rest.model;

import co.aurasphere.interview.server.model.Survey;
import co.aurasphere.interview.server.rest.SurveyRestController;

/**
 * Response for {@link SurveyRestController#submitSurvey(SubmitSurveyRequest)}.
 * 
 * @author Donato Rimenti
 */
public class SubmitSurveyResponse extends BaseInterviewServletRestResponse {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The submitted survey with the correct answers.
	 */
	private Survey survey;

	/**
	 * Instantiates a new SubmitSurveyResponse.
	 */
	public SubmitSurveyResponse() {
	}

	/**
	 * Instantiates a new SubmitSurveyResponse.
	 *
	 * @param survey
	 *            the {@link #survey}.
	 */
	public SubmitSurveyResponse(Survey survey) {
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
		SubmitSurveyResponse other = (SubmitSurveyResponse) obj;
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
		return "SubmitSurveyResponse [survey=" + survey + "]";
	}

}
