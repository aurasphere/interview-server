package co.aurasphere.interview.server.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents the score for a {@link User} on a {@link Survey}.
 * 
 * @author Donato Rimenti
 */
public class Score implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The name of the survey for this score.
	 */
	private String surveyName;

	/**
	 * The score points.
	 */
	private int score;

	/**
	 * The max score points to the survey.
	 */
	private int maxScore;

	/**
	 * The date and time in which the survey has been taken.
	 */
	private LocalDateTime timeTaken;

	/**
	 * The answers given by the user.
	 */
	private List<List<Integer>> userAnswers;

	/**
	 * Instantiates a new Score.
	 */
	public Score() {
	}

	/**
	 * Instantiates a new Score.
	 *
	 * @param surveyName
	 *            the {@link #surveyName}.
	 * @param score
	 *            the {@link #score}.
	 * @param maxScore
	 *            the {@link #maxScore}.
	 * @param userAnswers
	 *            the {@link #userAnswers}
	 */
	public Score(String surveyName, int score, int maxScore, List<List<Integer>> userAnswers) {
		this.surveyName = surveyName;
		this.score = score;
		this.maxScore = maxScore;
		this.timeTaken = LocalDateTime.now();
		this.userAnswers = userAnswers;
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
	 * Gets the {@link #score}.
	 *
	 * @return the {@link #score}.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the {@link #score}.
	 *
	 * @param score
	 *            the new {@link #score}.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Gets the {@link #timeTaken}.
	 *
	 * @return the {@link #timeTaken}.
	 */
	public LocalDateTime getTimeTaken() {
		return timeTaken;
	}

	/**
	 * Sets the {@link #timeTaken}.
	 *
	 * @param timeTaken
	 *            the new {@link #timeTaken}.
	 */
	public void setTimeTaken(LocalDateTime timeTaken) {
		this.timeTaken = timeTaken;
	}

	/**
	 * Gets the {@link #maxScore}.
	 *
	 * @return the {@link #maxScore}.
	 */
	public int getMaxScore() {
		return maxScore;
	}

	/**
	 * Sets the {@link #maxScore}.
	 *
	 * @param maxScore
	 *            the new {@link #maxScore}.
	 */
	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	/**
	 * Gets the {@link #userAnswers}.
	 *
	 * @return the {@link #userAnswers}.
	 */
	public List<List<Integer>> getUserAnswers() {
		return userAnswers;
	}

	/**
	 * Sets the {@link #userAnswers}.
	 *
	 * @param userAnswers
	 *            the new {@link #userAnswers}.
	 */
	public void setUserAnswers(List<List<Integer>> userAnswers) {
		this.userAnswers = userAnswers;
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
		result = prime * result + ((surveyName == null) ? 0 : surveyName.hashCode());
		result = prime * result + ((timeTaken == null) ? 0 : timeTaken.hashCode());
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
		Score other = (Score) obj;
		if (surveyName == null) {
			if (other.surveyName != null)
				return false;
		} else if (!surveyName.equals(other.surveyName))
			return false;
		if (timeTaken == null) {
			if (other.timeTaken != null)
				return false;
		} else if (!timeTaken.equals(other.timeTaken))
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
		return "Score [surveyName=" + surveyName + ", score=" + score + ", maxScore=" + maxScore + ", timeTaken="
				+ timeTaken + ", userAnswers=" + userAnswers + "]";
	}

}
