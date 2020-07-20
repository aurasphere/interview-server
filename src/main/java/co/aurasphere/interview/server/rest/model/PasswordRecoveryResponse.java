package co.aurasphere.interview.server.rest.model;

/**
 * Response for
 * {@link co.aurasphere.interview.server.rest.UserRestController#passwordRecovery(co.aurasphere.interview.server.rest.model.PasswordRecoveryRequest)}.
 * 
 * @author Donato Rimenti
 *
 */
public class PasswordRecoveryResponse extends BaseInterviewServletRestResponse {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new PasswordRecoveryResponse.
	 */
	public PasswordRecoveryResponse() {
	}

	/**
	 * Instantiates a new PasswordRecoveryResponse.
	 *
	 * @param outcome
	 *            the {@link BaseInterviewServletRestResponse#outcome}.
	 * @param errorMessage
	 *            the {@link BaseInterviewServletRestResponse#errorMessage}.
	 */
	public PasswordRecoveryResponse(boolean outcome, String errorMessage) {
		super(outcome, errorMessage);
	}

}