package co.aurasphere.interview.server.model;

/**
 * Represents a question type according to the type or cardinality of answers
 * allowed.
 * 
 * @author Donato Rimenti
 */
public enum QuestionType {

	/**
	 * A question with a single answer.
	 */
	SINGLE_ANSWER,

	/**
	 * A question with multiple answers.
	 */
	MULTIPLE_ANSWERS;
}
