package co.aurasphere.interview.server.rest.model;

import java.util.List;

import co.aurasphere.interview.server.model.Survey;
import co.aurasphere.interview.server.rest.UserRestController;

/**
 * Response for
 * {@link UserRestController#getUserSurveyResult(GetUserSurveyResultRequest)}.
 * 
 * @author Donato Rimenti
 */
public class GetUserSurveyResultResponse extends BaseInterviewServletRestResponse {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The survey.
	 */
	private Survey survey;

	/**
	 * The user answers.
	 */
	private List<Integer[]> userAnswers;

	/**
	 * Instantiates a new GetUserSurveyResultResponse.
	 */
	public GetUserSurveyResultResponse() {
	}

	/**
	 * Instantiates a new GetUserSurveyResultResponse.
	 *
	 * @param survey
	 *            the {@link #survey}
	 * @param userAnswers
	 *            the {@link #userAnswers}
	 */
	public GetUserSurveyResultResponse(Survey survey, List<Integer[]> userAnswers) {
		this.survey = survey;
		this.userAnswers = userAnswers;
	}

	/**
	 * Gets the {@link #survey}.
	 *
	 * @return the {@link #survey}
	 */
	public Survey getSurvey() {
		return survey;
	}

	/**
	 * Sets the {@link #survey}.
	 *
	 * @param survey
	 *            the new {@link #survey}
	 */
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	/**
	 * Gets the {@link #userAnswers}.
	 *
	 * @return the {@link #userAnswers}
	 */
	public List<Integer[]> getUserAnswers() {
		return userAnswers;
	}

	/**
	 * Sets the {@link #userAnswers}.
	 *
	 * @param userAnswers
	 *            the new {@link #userAnswers}
	 */
	public void setUserAnswers(List<Integer[]> userAnswers) {
		this.userAnswers = userAnswers;
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
		result = prime * result + ((userAnswers == null) ? 0 : userAnswers.hashCode());
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
		GetUserSurveyResultResponse other = (GetUserSurveyResultResponse) obj;
		if (survey == null) {
			if (other.survey != null)
				return false;
		} else if (!survey.equals(other.survey))
			return false;
		if (userAnswers == null) {
			if (other.userAnswers != null)
				return false;
		} else if (!userAnswers.equals(other.userAnswers))
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
		return "GetUserSurveyResultResponse [survey=" + survey + ", userAnswers=" + userAnswers + "]";
	}
}