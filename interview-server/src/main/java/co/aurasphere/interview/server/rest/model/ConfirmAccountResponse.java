package co.aurasphere.interview.server.rest.model;

/**
 * Response for
 * {@link co.aurasphere.interview.server.rest.UserRestController#confirmAccount(ConfirmAccountRequest)}.
 * 
 * @author Donato Rimenti
 *
 */
public class ConfirmAccountResponse extends BaseInterviewServletRestResponse {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new ConfirmAccountResponse.
	 */
	public ConfirmAccountResponse() {
	}

	/**
	 * Instantiates a new ConfirmAccountResponse.
	 *
	 * @param outcome
	 *            the {@link BaseInterviewServletRestResponse#outcome}.
	 * @param errorMessage
	 *            the {@link BaseInterviewServletRestResponse#errorMessage}.
	 */
	public ConfirmAccountResponse(boolean outcome, String errorMessage) {
		super(outcome, errorMessage);
	}

}
