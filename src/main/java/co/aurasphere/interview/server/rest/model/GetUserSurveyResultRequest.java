package co.aurasphere.interview.server.rest.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import co.aurasphere.interview.server.rest.UserRestController;

/**
 * Request for
 * {@link UserRestController#getUserSurveyResult(GetUserSurveyResultRequest)}.
 * 
 * @author Donato Rimenti
 */
public class GetUserSurveyResultRequest implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The email of the user whose survey needs to be retrieved.
	 */
	private String email;

	/**
	 * The survey name.
	 */
	private String surveyName;

	/**
	 * The time at which the survey was taken.
	 */
	private LocalDateTime surveyTime;

	/**
	 * Gets the {@link #email}.
	 *
	 * @return the {@link #email}.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the {@link #email}.
	 *
	 * @param email
	 *            the new {@link #email}.
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * Gets the {@link #surveyTime}.
	 *
	 * @return the {@link #surveyTime}.
	 */
	public LocalDateTime getSurveyTime() {
		return surveyTime;
	}

	/**
	 * Sets the {@link #surveyTime}.
	 *
	 * @param surveyTime
	 *            the new {@link #surveyTime}.
	 */
	public void setSurveyTime(LocalDateTime surveyTime) {
		this.surveyTime = surveyTime;
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((surveyName == null) ? 0 : surveyName.hashCode());
		result = prime * result + ((surveyTime == null) ? 0 : surveyTime.hashCode());
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
		GetUserSurveyResultRequest other = (GetUserSurveyResultRequest) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (surveyName == null) {
			if (other.surveyName != null)
				return false;
		} else if (!surveyName.equals(other.surveyName))
			return false;
		if (surveyTime == null) {
			if (other.surveyTime != null)
				return false;
		} else if (!surveyTime.equals(other.surveyTime))
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
		return "GetUserSurveyResultRequest [email=" + email + ", surveyName=" + surveyName + ", surveyTime="
				+ surveyTime + "]";
	}

}
