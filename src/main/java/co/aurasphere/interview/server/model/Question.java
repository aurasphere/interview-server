package co.aurasphere.interview.server.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a question inside a {@link Survey}.
 * 
 * @author Donato Rimenti
 */
public class Question implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The question text.
	 */
	private String questionText;

	/**
	 * The answers to this question.
	 */
	private List<String> answers;

	/**
	 * The type of question.
	 */
	private QuestionType type;

	/**
	 * The correct answers to this questions.
	 */
	private Integer[] correctAnswers;

	/**
	 * Gets the {@link #questionText}.
	 *
	 * @return the {@link #questionText}.
	 */
	public String getQuestionText() {
		return questionText;
	}

	/**
	 * Instantiates a new Question.
	 */
	public Question() {
	}

	/**
	 * Instantiates a new Question.
	 *
	 * @param questionText
	 *            the {@link #questionText}.
	 * @param answers
	 *            the {@link #answers}.
	 * @param correctAnswer
	 *            the {@link #correctAnswer}.
	 */
	public Question(String questionText, List<String> answers, int correctAnswer) {
		this.questionText = questionText;
		this.answers = answers;
		this.type = QuestionType.SINGLE_ANSWER;
		this.correctAnswers = new Integer[] { correctAnswer };
	}

	/**
	 * Instantiates a new Question.
	 *
	 * @param questionText
	 *            the {@link #questionText}.
	 * @param answers
	 *            the {@link #answers}.
	 * @param correctAnswers
	 *            the {@link #correctAnswers}.
	 */
	public Question(String questionText, List<String> answers, Integer[] correctAnswers) {
		this.questionText = questionText;
		this.answers = answers;
		this.type = QuestionType.MULTIPLE_ANSWERS;
		this.correctAnswers = correctAnswers;
	}

	/**
	 * Sets the {@link #questionText}.
	 *
	 * @param questionText
	 *            the new {@link #questionText}.
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	/**
	 * Gets the {@link #answers}.
	 *
	 * @return the {@link #answers}.
	 */
	public List<String> getAnswers() {
		return answers;
	}

	/**
	 * Sets the {@link #answers}.
	 *
	 * @param answers
	 *            the new {@link #answers}.
	 */
	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	/**
	 * Gets the {@link #correctAnswers}.
	 *
	 * @return the {@link #correctAnswers}.
	 */
	public Integer[] getCorrectAnswers() {
		return correctAnswers;
	}

	/**
	 * Sets the {@link #correctAnswers}.
	 *
	 * @param correctAnswers
	 *            the new {@link #correctAnswers}.
	 */
	public void setCorrectAnswers(Integer[] correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	/**
	 * Gets the {@link #type}.
	 *
	 * @return the {@link #type}.
	 */
	public QuestionType getType() {
		return type;
	}

	/**
	 * Sets the {@link #type}.
	 *
	 * @param type
	 *            the new {@link #type}.
	 */
	public void setType(QuestionType type) {
		this.type = type;
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
		result = prime * result + Arrays.hashCode(correctAnswers);
		result = prime * result + ((questionText == null) ? 0 : questionText.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Question other = (Question) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (!Arrays.equals(correctAnswers, other.correctAnswers))
			return false;
		if (questionText == null) {
			if (other.questionText != null)
				return false;
		} else if (!questionText.equals(other.questionText))
			return false;
		if (type != other.type)
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
		return "Question [questionText=" + questionText + ", answers=" + answers + ", type=" + type
				+ ", correctAnswers=" + Arrays.toString(correctAnswers) + "]";
	}

}
