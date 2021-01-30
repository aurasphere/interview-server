package co.aurasphere.interview.server.rest.model;

import co.aurasphere.interview.server.rest.UserRestController;

/**
 * Response for
 * {@link UserRestController#createUser(co.aurasphere.interview.server.model.User, javax.servlet.http.HttpServletRequest)}.
 * 
 * @author Donato Rimenti
 */
public class CreateUserResponse extends BaseInterviewServletRestResponse {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new CreateUserResponse.
	 */
	public CreateUserResponse() {
	}

	/**
	 * Instantiates a new CreateUserResponse.
	 *
	 * @param outcome
	 *            the {@link BaseInterviewServletRestResponse#outcome}.
	 */
	public CreateUserResponse(boolean outcome) {
		super(outcome);
	}

	/**
	 * Instantiates a new CreateUserResponse.
	 *
	 * @param errorMessage
	 *            the {@link BaseInterviewServletRestResponse#errorMessage}.
	 */
	public CreateUserResponse(String errorMessage) {
		super(errorMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CreateUserResponse [outcome=" + outcome + ", errorMessage=" + errorMessage + "]";
	}

}