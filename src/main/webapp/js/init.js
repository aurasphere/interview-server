/**
 * Executed on page initialization.
 */
$(document)
		.ready(
				function() {

					// Function for checking the URL query parameters if passed.
					window.getUrlParameter = function getUrlParameter(sParam) {
						var sPageURL = decodeURIComponent(window.location.search
								.substring(1)), sURLVariables = sPageURL
								.split('&'), sParameterName, i;

						for (i = 0; i < sURLVariables.length; i++) {
							sParameterName = sURLVariables[i].split('=');

							if (sParameterName[0] === sParam) {
								return sParameterName[1];
							}
						}
					};

					// Adds endsWith function to String if not present (IE
					// compatibility).
					if (!String.prototype.endsWith) {
						String.prototype.endsWith = function(suffix) {
							return this.indexOf(suffix, this.length
									- suffix.length) !== -1;
						};
					}

					// Shows an alert when leaving the page.
					window.onbeforeunload = function(event) {
						event.returnValue = "Sei sicuro di voler ricaricare la pagina? I dati immessi saranno persi.";
						return "Sei sicuro di voler ricaricare la pagina? I dati immessi saranno persi.";
					}

					// Cleans up before unloading.
					window.onunload = function() {

						// Submits automatically any pending surveys.
						if (viewModel.survey.survey() != undefined) {
							viewModel.survey.submitButton();
						}

						// Automatically logs out if the page gets unloaded.
						if (viewModel.currentUser.email() != undefined) {
							navigation.logout();
							// Deletes manually the cookie to ensure logout on
							// leaving the page.
							document.cookie = 'JSESSIONID=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
						}

						return "I dati inseriti saranno persi ricaricando la pagina. Continuare?";
					};

					// Initializes the datepickers.
					$.datepicker.setDefaults($.datepicker.regional["it"]);

					// If there are email and token passed as query params we
					// want to redirect to the password recovery or to the
					// account activation according to the type parameter.
					var queryEmail = getUrlParameter("email");
					var queryToken = getUrlParameter("token");
					var queryType = getUrlParameter("type");

					// Cleans the url bar before proceeding.
					window.history.replaceState({}, document.title,
							window.location.href.split("?")[0]);

					if (queryEmail && queryToken && queryType) {
						switch (queryType) {
						case "activation":
							navigation.toAccountActivation(queryEmail,
									queryToken);
							break;
						case "recovery":
							navigation.toPasswordRecovery(queryEmail,
									queryToken);
							break;
						}

					} else {
						// Goes to the first page showing the animation.
						navigation.toMainMenu(true);
					}

				});
