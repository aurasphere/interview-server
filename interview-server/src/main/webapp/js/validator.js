/**
 * Handles form validations.
 */
var validator = {
	// Validates the registration form by checking that each field is filled
	// with a valid value.
	validateRegistrationForm : function() {
		var name = $("#name");
		var surname = $("#surname");
		var email = $("#email");
		var birthday = $("#birthday");
		var tech = $("#technology");
		var password = $("#password");

		if (this.isEmpty(name.val())) {
			viewModel.registrationForm
					.errorMessage("Il campo nome deve essere valorizzato.");
			name.focus();
			return false;
		}
		if (this.isEmpty(surname.val())) {
			viewModel.registrationForm
					.errorMessage("Il campo cognome deve essere valorizzato.");
			surname.focus();
			return false;
		}
		// Emails must be valid.
		if (this.isEmpty(email.val()) || !this.isEmail(email.val())) {
			viewModel.registrationForm
					.errorMessage("Il campo email deve essere valorizzato con un'email valida.");
			email.focus();
			return false;
		}
		// Dates must be valid.
		if (this.isEmpty(birthday.val()) || !this.isValidDate(birthday.val())) {
			viewModel.registrationForm
					.errorMessage("Il campo data di nascita deve essere valorizzato con una data valida.");
			birthday.focus();
			return false;
		}
		if (this.isEmpty(tech.val())) {
			viewModel.registrationForm
					.errorMessage("Il campo tecnologia deve essere valorizzato.");
			tech.focus();
			return false;
		}

		if (this.isEmpty(password.val())) {
			viewModel.registrationForm
					.errorMessage("Il campo password deve essere valorizzato.");
			password.focus();
			return false;
		}

		// Password must be long at least 8 characters.
		if (password.val().length < 8) {
			viewModel.registrationForm
					.errorMessage("La password deve essere lunga almeno 8 caratteri.");
			password.focus();
			return false;
		}
		return true;
	},

	// Validates the search form.
	validateSearchForm : function() {
		var name = $("#name");
		var surname = $("#surname");
		var email = $("#email");
		var birthday = $("#birthday");
		var dateTakenStart = $("#dateTakenStart");
		var dateTakenEnd = $("dateTakenEnd");
		var technology = $("#technology");

		// At least one field has to be filled.
		if (this.isEmpty(name.val()) && this.isEmpty(surname.val())
				&& this.isEmpty(email.val()) && this.isEmpty(birthday.val())
				&& this.isEmpty(dateTakenStart.val())
				&& this.isEmpty(dateTakenEnd.val())
				&& this.isEmpty(technology.val())) {
			viewModel.searchForm
					.errorMessage("Almeno un campo deve essere valorizzato.");
			name.focus();
			return false;
		}

		// Any date present must be in a valid format.
		if (!this.isOptionalValidDate(birthday.val())) {
			viewModel.searchForm
					.errorMessage("Il campo data di nascita deve essere valorizzato con una data valida.");
			birthday.focus();
			return false;
		}
		if (!this.isOptionalValidDate(dateTakenStart.val())) {
			viewModel.searchForm
					.errorMessage("Il campo data questionario (inizio) deve essere valorizzato con una data valida.");
			dateTakenStart.focus();
			return false;
		}
		if (!this.isOptionalValidDate(dateTakenEnd.val())) {
			viewModel.searchForm
					.errorMessage("Il campo data questionario (fine) deve essere valorizzato con una data valida.");
			dateTakenEnd.focus();
			return false;
		}
		// If an email is present, it must be valid.
		if (!this.isEmpty(email.val()) && !this.isEmail(email.val())) {
			viewModel.searchForm
					.errorMessage("Il campo email deve essere valorizzato con un'email valida.");
			email.focus();
			return false;
		}

		return true;
	},

	// Validates the login form.
	validateLoginForm : function() {
		var email = $("#email");
		var password = $("#password");

		if (this.isEmpty(email.val())) {
			viewModel.login
					.errorMessage("Il campo email deve essere valorizzato.");
			email.focus();
			return false;
		}
		if (this.isEmpty(password.val())) {
			viewModel.login
					.errorMessage("Il campo password deve essere valorizzato.");
			password.focus();
			return false;
		}
		return true;
	},

	// Validates the password recovery form.
	validatePasswordRecoveryForm : function() {
		var password = $("#password");
		// Password must be not empty.
		if (this.isEmpty(password.val())) {
			viewModel.passwordRecoveryLanding
					.errorMessage("Inserire una nuova password.");
			password.focus();
			return false;
		}

		// Password must be long at least 8 characters.
		if (password.val().length < 8) {
			viewModel.passwordRecoveryLanding
					.errorMessage("La password deve essere lunga almeno 8 caratteri.");
			password.focus();
			return false;
		}

		return true;
	},

	// Checks if a field is null or empty.
	isEmpty : function(data) {
		return data == null || data == "";
	},
	// Checks if a field is a valid email.
	isEmail : function(data) {
		var emailRegExp = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
		return data.match(emailRegExp);
	},
	// Checks if a field is a valid date.
	isValidDate : function(data) {
		return moment(data, "DD/MM/YYYY", true).isValid();
	},
	// Checks if a field is a valid date, if present.
	isOptionalValidDate : function(data) {
		return this.isEmpty(data) || this.isValidDate(data);
	},
	// Checks if a field ends with the specified suffix.
	endsWith : function endsWith(str, suffix) {
		return str.indexOf(suffix, str.length - suffix.length) !== -1;
	}
}