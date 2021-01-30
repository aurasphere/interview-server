package co.aurasphere.interview.server.utilities;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * Email message preparator for interview services. Immutable and Thread Safe.
 * 
 * @author Donato Rimenti
 *
 */
public class InterviewMimeMessagePreparator implements MimeMessagePreparator {

	/**
	 * Sender (From) of the email.
	 */
	private String sender;

	/**
	 * Recipient (To) of the email.
	 */
	private String recipient;

	/**
	 * Subject of the email.
	 */
	private String subject;

	/**
	 * Text (HTML) of the email.
	 */
	private String text;

	/**
	 * Instantiates a new InterviewMimeMessagePreparator.
	 *
	 * @param sender
	 *            the {@link #sender}
	 * @param recipient
	 *            the {@link #recipient}
	 * @param subject
	 *            the {@link #subject}
	 * @param text
	 *            the {@link #text}
	 */
	public InterviewMimeMessagePreparator(String sender, String recipient, String subject, String text) {
		this.sender = sender;
		this.recipient = recipient;
		this.subject = subject;
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.mail.javamail.MimeMessagePreparator#prepare(javax.
	 * mail.internet.MimeMessage)
	 */
	public void prepare(MimeMessage message) throws Exception {
		// Message meta data.
		MimeMessageHelper messageHelper = new MimeMessageHelper(message);
		messageHelper.setFrom(sender);
		messageHelper.setTo(recipient);
		messageHelper.setSubject(subject);
		// True for HTML email, false for plain text.
		messageHelper.setText(text, true);
	}
}