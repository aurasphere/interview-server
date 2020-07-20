package co.aurasphere.interview.server.security;

import java.util.Objects;

import org.jasypt.util.password.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import co.aurasphere.interview.server.dao.UserDao;

/**
 * Provides authentication checking against a custom {@link UserDao}
 * implementation. The user needs to provide a password only if it's asking for
 * admin privileges with an email ending in <code>@aurasphere.co</code>.
 * 
 * @author Donato Rimenti
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

	/**
	 * Logger.
	 */
	private final static Logger LOG = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	/**
	 * The dao for the authentication.
	 */
	@Autowired
	private UserDao userDao;
	
	/**
	 * Encryptor used to check the password.
	 */
	@Autowired
	private PasswordEncryptor encryptor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String user = authentication.getName();
		String pass = Objects.toString(authentication.getCredentials(), null);

		UserDetails loadedUser = userDao.getUserByEmail(user);
		if (loadedUser == null) {
			LOG.warn("Tentativo di accesso bloccato per utente [{}] non trovato.", user);
			throw new UsernameNotFoundException("Email o password errati.");
		}
		
		// [v1.2.0] Double hashing, one performed client-side, the other server-side.
		if (!encryptor.checkPassword(pass, loadedUser.getPassword())) {
			LOG.warn("Tentativo di accesso bloccato per password errata per utente [{}].", user);
			throw new BadCredentialsException("Email o password errati.");
		}
		
		// [v1.3.0] Checks if the account is enabled.
		if (!loadedUser.isEnabled()) {
			LOG.warn("Tentativo di accesso bloccato per account non attivato per utente [{}]", user);
			throw new DisabledException("Account non abilitato.");
		}

		LOG.info("Login effettuato correttamente da utente [{}].", user);
		return new UsernamePasswordAuthenticationToken(loadedUser, pass, loadedUser.getAuthorities());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
