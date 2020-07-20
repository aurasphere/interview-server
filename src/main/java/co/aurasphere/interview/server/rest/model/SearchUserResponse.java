package co.aurasphere.interview.server.rest.model;

import java.util.List;

import co.aurasphere.interview.server.model.User;
import co.aurasphere.interview.server.rest.UserRestController;

/**
 * Response for {@link UserRestController#searchUser(SearchUserRequest)}.
 * 
 * @author Donato Rimenti
 */
public class SearchUserResponse extends BaseInterviewServletRestResponse {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The search results.
	 */
	private List<User> searchResults;

	/**
	 * The number of total result pages.
	 */
	private long pages;

	/**
	 * Instantiates a new SearchUserResponse.
	 */
	public SearchUserResponse() {
	}

	/**
	 * Instantiates a new SearchUserResponse.
	 *
	 * @param searchResults
	 *            the {@link #searchResults}.
	 * @param pages
	 *            the {@link #pages}.
	 */
	public SearchUserResponse(List<User> searchResults, long pages) {
		this.searchResults = searchResults;
		this.pages = pages;
	}

	/**
	 * Gets the {@link #searchResults}.
	 *
	 * @return the {@link #searchResults}.
	 */
	public List<User> getSearchResults() {
		return searchResults;
	}

	/**
	 * Sets the {@link #searchResults}.
	 *
	 * @param searchResults
	 *            the new {@link #searchResults}.
	 */
	public void setSearchResults(List<User> searchResults) {
		this.searchResults = searchResults;
	}

	/**
	 * Gets the {@link #pages}.
	 *
	 * @return the {@link #pages}.
	 */
	public long getPages() {
		return pages;
	}

	/**
	 * Sets the {@link #pages}.
	 *
	 * @param pages
	 *            the new {@link #pages}.
	 */
	public void setPages(long pages) {
		this.pages = pages;
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
		result = prime * result + (int) (pages ^ (pages >>> 32));
		result = prime * result + ((searchResults == null) ? 0 : searchResults.hashCode());
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
		SearchUserResponse other = (SearchUserResponse) obj;
		if (pages != other.pages)
			return false;
		if (searchResults == null) {
			if (other.searchResults != null)
				return false;
		} else if (!searchResults.equals(other.searchResults))
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
		return "SearchUserResponse [searchResults=" + searchResults + ", pages=" + pages + "]";
	}
}
