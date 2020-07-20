package co.aurasphere.interview.server.rest.model;

import java.io.Serializable;
import java.time.LocalDate;

import co.aurasphere.interview.server.rest.UserRestController;

/**
 * Request for {@link UserRestController#searchUser(SearchUserRequest)}.
 * 
 * @author Donato Rimenti
 */
public class SearchUserRequest implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The name of the user to search.
	 */
	private String name;

	/**
	 * The surname of the user to search.
	 */
	private String surname;

	/**
	 * The email of the user to search.
	 */
	private String email;

	/**
	 * The birthday of the user to search.
	 */
	private LocalDate birthday;

	/**
	 * The technology of the user to search.
	 */
	private String technology;

	/**
	 * The starting date for a survey of the user to search.
	 */
	private LocalDate dateTakenStart;

	/**
	 * The ending date for a survey of the user to search.
	 */
	private LocalDate dateTakenEnd;

	/**
	 * The name of the survey for the score ordering.
	 */
	private String orderBySurveyScore;

	/**
	 * The number of the page to fetch.
	 */
	private int page;

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
	 * Gets the {@link #surname}.
	 *
	 * @return the {@link #surname}.
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets the {@link #surname}.
	 *
	 * @param surname
	 *            the new {@link #surname}.
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

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
	 * Gets the {@link #birthday}.
	 *
	 * @return the {@link #birthday}.
	 */
	public LocalDate getBirthday() {
		return birthday;
	}

	/**
	 * Sets the {@link #birthday}.
	 *
	 * @param birthday
	 *            the new {@link #birthday}.
	 */
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
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
	 * Gets the {@link #dateTakenStart}.
	 *
	 * @return the {@link #dateTakenStart}.
	 */
	public LocalDate getDateTakenStart() {
		return dateTakenStart;
	}

	/**
	 * Sets the {@link #dateTakenStart}.
	 *
	 * @param dateTakenStart
	 *            the new {@link #dateTakenStart}.
	 */
	public void setDateTakenStart(LocalDate dateTakenStart) {
		this.dateTakenStart = dateTakenStart;
	}

	/**
	 * Gets the {@link #dateTakenEnd}.
	 *
	 * @return the {@link #dateTakenEnd}.
	 */
	public LocalDate getDateTakenEnd() {
		return dateTakenEnd;
	}

	/**
	 * Sets the {@link #dateTakenEnd}.
	 *
	 * @param dateTakenEnd
	 *            the new {@link #dateTakenEnd}.
	 */
	public void setDateTakenEnd(LocalDate dateTakenEnd) {
		this.dateTakenEnd = dateTakenEnd;
	}

	/**
	 * Gets the {@link #page}.
	 *
	 * @return the {@link #page}.
	 */
	public int getPage() {
		return page;
	}

	/**
	 * Sets the {@link #page}.
	 *
	 * @param page
	 *            the new {@link #page}.
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * Gets the {@link #orderBySurveyScore}.
	 *
	 * @return the {@link #orderBySurveyScore}.
	 */
	public String getOrderBySurveyScore() {
		return orderBySurveyScore;
	}

	/**
	 * Sets the {@link #orderBySurveyScore}.
	 *
	 * @param orderBySurveyScore
	 *            the new {@link #orderBySurveyScore}.
	 */
	public void setOrderBySurveyScore(String orderBySurveyScore) {
		this.orderBySurveyScore = orderBySurveyScore;
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
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((dateTakenEnd == null) ? 0 : dateTakenEnd.hashCode());
		result = prime * result + ((dateTakenStart == null) ? 0 : dateTakenStart.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orderBySurveyScore == null) ? 0 : orderBySurveyScore.hashCode());
		result = prime * result + page;
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((technology == null) ? 0 : technology.hashCode());
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
		SearchUserRequest other = (SearchUserRequest) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (dateTakenEnd == null) {
			if (other.dateTakenEnd != null)
				return false;
		} else if (!dateTakenEnd.equals(other.dateTakenEnd))
			return false;
		if (dateTakenStart == null) {
			if (other.dateTakenStart != null)
				return false;
		} else if (!dateTakenStart.equals(other.dateTakenStart))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderBySurveyScore == null) {
			if (other.orderBySurveyScore != null)
				return false;
		} else if (!orderBySurveyScore.equals(other.orderBySurveyScore))
			return false;
		if (page != other.page)
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (technology == null) {
			if (other.technology != null)
				return false;
		} else if (!technology.equals(other.technology))
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
		return "SearchUserRequest [name=" + name + ", surname=" + surname + ", email=" + email + ", birthday="
				+ birthday + ", technology=" + technology + ", dateTakenStart=" + dateTakenStart + ", dateTakenEnd="
				+ dateTakenEnd + ", orderBySurveyScore=" + orderBySurveyScore + ", page=" + page + "]";
	}

}
