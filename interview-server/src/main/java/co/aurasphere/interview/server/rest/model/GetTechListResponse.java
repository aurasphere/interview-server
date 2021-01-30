package co.aurasphere.interview.server.rest.model;

import java.util.List;

import co.aurasphere.interview.server.rest.SurveyRestController;

/**
 * Response for {@link SurveyRestController#getTechList()}.
 * 
 * @author Donato Rimenti
 */
public class GetTechListResponse extends BaseInterviewServletRestResponse {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * List of available technologies.
	 */
	private List<String> techList;

	/**
	 * Instantiates a new GetTechListResponse.
	 */
	public GetTechListResponse() {
	}

	/**
	 * Instantiates a new GetTechListResponse.
	 *
	 * @param techList
	 *            the {@link #techList}.
	 */
	public GetTechListResponse(List<String> techList) {
		this.techList = techList;
	}

	/**
	 * Gets the {@link #techList}.
	 *
	 * @return the {@link #techList}.
	 */
	public List<String> getTechList() {
		return techList;
	}

	/**
	 * Sets the {@link #techList}.
	 *
	 * @param techList
	 *            the new {@link #techList}.
	 */
	public void setTechList(List<String> techList) {
		this.techList = techList;
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
		result = prime * result + ((techList == null) ? 0 : techList.hashCode());
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
		GetTechListResponse other = (GetTechListResponse) obj;
		if (techList == null) {
			if (other.techList != null)
				return false;
		} else if (!techList.equals(other.techList))
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
		return "GetTechListResponse [techList=" + techList + "]";
	}

}
