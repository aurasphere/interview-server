package co.aurasphere.interview.server.rest.model;

import java.io.Serializable;
import java.util.List;

import co.aurasphere.interview.server.rest.SurveyRestController;

/**
 * Request for {@link SurveyRestController#submitSurvey(SubmitSurveyRequest)}.
 * 
 * @author Donato Rimenti
 */
public class SubmitSurveyRequest implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The user email.
	 */
	private String userEmail;

	/**
	 * The survey name.
	 */
	private String surveyName;

	/**
	 * The user answers.
	 */
	private List<Integer[]> answers;

	/**
	 * Gets the {@link #userEmail}.
	 *
	 * @return the {@link #userEmail}.
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * Sets the {@link #userEmail}.
	 *
	 * @param userEmail
	 *            the new {@link #userEmail}.
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * Gets the {@link #surveyName}.
	 *
	 * @return the {@link #surveyName}.
	 */
	public String getSurveyName() {
		return surveyName;
	}

	/**
	 * Sets the {@link #surveyName}.
	 *
	 * @param surveyName
	 *            the new {@link #surveyName}.
	 */
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	/**
	 * Gets the {@link #answers}.
	 *
	 * @return the {@link #answers}.
	 */
	public List<Integer[]> getAnswers() {
		return answers;
	}

	/**
	 * Sets the {@link #answers}.
	 *
	 * @param answers
	 *            the new {@link #answers}.
	 */
	public void setAnswers(List<Integer[]> answers) {
		this.answers = answers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((surveyName == null) ? 0 : surveyName.hashCode());
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubmitSurveyRequest other = (SubmitSurveyRequest) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (surveyName == null) {
			if (other.surveyName != null)
				return false;
		} else if (!surveyName.equals(other.surveyName))
			return false;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubmitSurveyRequest [userEmail=" + userEmail + ", surveyName=" + surveyName + ", answers=" + answers
				+ "]";
	}

}
