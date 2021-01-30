package co.aurasphere.interview.server.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a survey.
 *
 * @author Donato Rimenti
 */
@Document(collection = "survey")
public class Survey implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The name of this survey. Must be unique.
	 */
	@Id
	private String name;

	/**
	 * The questions of this survey.
	 */
	private List<Question> questions;

	/**
	 * The technology on which the questions of this survey are based.
	 */
	private String technology;

	/**
	 * The time available to complete this survey.
	 */
	private int time;

	/**
	 * Gets the {@link #name}.
	 *
	 * @return the {@link #name}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the {@link #name}.
	 *
	 * @param name
	 *            the new {@link #name}.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the {@link #questions}.
	 *
	 * @return the {@link #questions}.
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * Sets the {@link #questions}.
	 *
	 * @param questions
	 *            the new {@link #questions}.
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	/**
	 * Gets the {@link #technology}.
	 *
	 * @return the {@link #technology}.
	 */
	public String getTechnology() {
		return technology;
	}

	/**
	 * Sets the {@link #technology}.
	 *
	 * @param technology
	 *            the new {@link #technology}.
	 */
	public void setTechnology(String technology) {
		this.technology = technology;
	}

	/**
	 * Gets the {@link #time}.
	 *
	 * @return the {@link #time}.
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Sets the {@link #time}.
	 *
	 * @param time
	 *            the new {@link #time}.
	 */
	public void setTime(int time) {
		this.time = time;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((questions == null) ? 0 : questions.hashCode());
		result = prime * result + ((technology == null) ? 0 : technology.hashCode());
		result = prime * result + time;
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
		Survey other = (Survey) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		if (technology != other.technology)
			return false;
		if (time != other.time)
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
		return "Survey [name=" + name + ", questions=" + questions + ", technology=" + technology + ", time=" + time
				+ "]";
	}

}
