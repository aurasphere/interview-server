var viewModel = {
	// First menu.
	mainMenu : {
		// Login button callback function.
		loginButton : function(data, event) {
			event.preventDefault();

			// Clears all the fields.
			viewModel.login.errorMessage(null);
			viewModel.login.email(null);
			viewModel.login.password(null);
			viewModel.login.lastAttemptedEmail(null);
			viewModel.login.showSendActivationEmail(false);
			viewModel.login.showRecoverPassword(false);

			loading.loadPage("pages/login.html", function() {
				viewModel.bindData(viewModel.login);

				// Focus on the email input.
				$("#email").focus();
			});
		},
		// Registration button callback function.
		registrationButton : function(data, event) {
			event.preventDefault();

			// Clears all the fields.
			viewModel.registrationForm.errorMessage(null);
			viewModel.registrationForm.name(null);
			viewModel.registrationForm.surname(null);
			viewModel.registrationForm.email(null);
			viewModel.registrationForm.birthday(null);
			viewModel.registrationForm.password(null);
			viewModel.registrationForm.technology(null);

			// Loads the data for the technology select on the first page.
			ajax.get("survey/getTechList", "pages/registration-form.html",
					function(data) {
						// Limits the datepicker date.
						$("#birthday").datepicker({
							yearRange : "-100:+0",
							maxDate : -6570
						});

						// Binds the form data.
						viewModel.registrationForm.techList(data.techList);
						viewModel.bindData(viewModel.registrationForm);

						// Focus on the name input with a little delay to allow
						// animations to end.
						setTimeout(function() {
							$("#name").focus();
						}, 600);

					});
		}
	},

	// Registration form.
	registrationForm : {
		name : ko.observable(undefined),
		surname : ko.observable(undefined),
		email : ko.observable(undefined),
		birthday : ko.observable(undefined),
		technology : ko.observable(undefined),
		techList : ko.observableArray(),
		password : ko.observable(undefined),
		errorMessage : ko.observable(undefined),
		submitButton : function(data, event) {
			event.preventDefault();
			// Performs validation.
			if (!validator.validateRegistrationForm()) {
				return;
			}
			ajax.post("user/createUser", "pages/registration-complete.html", {
				name : viewModel.registrationForm.name(),
				surname : viewModel.registrationForm.surname(),
				email : viewModel.registrationForm.email(),
				birthday : moment.utc(viewModel.registrationForm.birthday(),
						"DD-MM-YYYY"),
				technology : viewModel.registrationForm.technology(),
				password : md5(viewModel.registrationForm.password())
			}, function(data) {

				if (data.outcome == false) {
					viewModel.registrationForm.errorMessage(data.errorMessage);
					loading.hide("pages/registration-form.html", function() {
						viewModel.bindData(viewModel.registrationForm);

						// Focus on the email input with a little delay to allow
						// animations to end.
						setTimeout(function() {
							$("#email").focus();
						}, 600);
					})
				} else {
					// Clear the error message if present.
					viewModel.registrationForm.errorMessage(null);
				}
			});
		},

		// Back button callback function.
		backButton : function(data, event) {
			event.preventDefault();
			navigation.toMainMenu(false);
		}
	},

	// Survey list page.
	surveyList : {
		surveys : ko.observable(),
		surveyListEmpty : ko.pureComputed(function() {
			return viewModel.surveyList.surveys().length == 0;
		})
	},

	// Survey page.
	survey : {
		// The current survey.
		survey : ko.observable(undefined),
		// Gets all the answers checked by the user.
		allCheckedAnswers : ko.pureComputed(function() {
			var finalArray = [];
			viewModel.survey.survey().questions.forEach(function(element) {
				var userAnswers = element.checkedAnswers;
				// This ensures that the user answers will always be an array.
				if (!(userAnswers instanceof Array)) {
					userAnswers = [ element.checkedAnswers ];
				}
				finalArray.push(userAnswers);
			})
			return finalArray;
		}),

		// Submit button callback function.
		submitButton : function(data, event, autosubmitted) {
			if (event != null) {
				event.preventDefault();
			}

			// Closes the confirmation dialog if still open.
			if ($("#survey-submit-confirm").dialog("isOpen")) {
				$("#survey-submit-confirm").dialog("close");
			}

			// Checks if we are currently doing any survey.
			if (viewModel.survey.survey()) {
				// Manually forces the refresh of the answers picked by the user
				// since they are not in an observableArray.
				viewModel.survey.survey.valueHasMutated();

				ajax.post("survey/submitSurvey", null, {
					userEmail : viewModel.currentUser.email(),
					surveyName : viewModel.survey.survey().name,
					answers : viewModel.survey.allCheckedAnswers()
				}, function(data) {
					// Gets back to the survey list.
					navigation.toSurveyList();

					// Shows an info dialog if the form was
					// automatically submitted.
					if (autosubmitted) {
						$("#survey-autosubmit-info").dialog("open");
					}

				}, true);
			}
		}
	},

	// Login page.
	login : {
		email : ko.observable(undefined),
		password : ko.observable(undefined),
		errorMessage : ko.observable(undefined),
		lastAttemptedEmail : ko.observable(undefined),
		showSendActivationEmail : ko.observable(undefined),
		showRecoverPassword : ko.observable(undefined),
		// Submit button callback field.
		submitButton : function() {

			// Performs validation.
			if (!validator.validateLoginForm()) {
				// Clears any link.
				viewModel.login.showRecoverPassword(false);
				viewModel.login.showSendActivationEmail(false);
				return;
			}

			// Stores the last attempted email for later.
			viewModel.login.lastAttemptedEmail(viewModel.login.email());
			ajax.post("login?" + $.param({
				username : viewModel.login.email(),
				password : md5(viewModel.login.password())
			}), null, null, function(dataString) {
				var data = JSON.parse(dataString);
				// If the login is unsuccesfull, reloads this page.
				if (data.outcome == false) {
					viewModel.login.errorMessage(data.errorMessage);

					// If the message returned is about the account disabled,
					// shows an option to send again the activation email.
					if (data.errorMessage == "Account non abilitato.") {
						viewModel.login.showSendActivationEmail(true);
					} else {
						viewModel.login.showSendActivationEmail(false);
					}

					// If the message returned is about wrong email or password,
					// shows the recover password link.
					if (data.errorMessage == "Email o password errati.") {
						viewModel.login.showRecoverPassword(true);
					} else {
						viewModel.login.showRecoverPassword(false);
					}

					loading.hide("pages/login.html", function() {
						viewModel.bindData(viewModel.login);

						// Focus on the email input.
						$("#email").focus();
					});
				} else {
					// Saves the user data into session.
					viewModel.currentUser.email(data.user.email);
					viewModel.currentUser.technology(data.user.technology);
					viewModel.currentUser
							.role(data.user.authorities[0].authority);
					// Clears any error message.
					viewModel.login.errorMessage(null);

					// Shows the logout button.
					$("#logout").show();

					// Gets the user role and
					// redirects accordingly.
					if (viewModel.currentUser.role() == "ROLE_ADMIN") {
						navigation.toSearchForm();
					} else {
						navigation.toSurveyList();
					}
				}

			});
		},

		// Back button callback function.
		backButton : function(data, event) {
			event.preventDefault();
			navigation.toMainMenu(false);
		},

		// Sends an activation mail to the last mail inserted.
		sendActivationEmail : function() {
			// Hides the link.
			viewModel.login.showSendActivationEmail(false);
			ajax.get("user/sendAccountActivationEmail?" + $.param({
				email : viewModel.login.lastAttemptedEmail()
			}), null, function(data) {
				// Shows the outcome of the operation.
				viewModel.login.errorMessage(data.errorMessage);
			}, false, "login-messages");
		},

		// Sends a recovery mail to the last mail inserted.
		sendPasswordRecoveryEmail : function() {
			// Hides the link.
			viewModel.login.showRecoverPassword(false);
			ajax
					.get(
							"user/sendRecoverPasswordEmail?" + $.param({
								email : viewModel.login.lastAttemptedEmail()
							}),
							null,
							function(data) {
								// Shows the outcome of the operation.
								viewModel.login
										.errorMessage("Abbiamo inviato una mail di recupero all'ultima email che hai inserito.");
							}, false, "login-messages");
		},
	},
	// Admin search form.
	searchForm : {
		name : ko.observable(undefined),
		surname : ko.observable(undefined),
		email : ko.observable(undefined),
		birthday : ko.observable(undefined),
		dateTakenStart : ko.observable(undefined),
		dateTakenEnd : ko.observable(undefined),
		technology : ko.observable(undefined),
		techList : ko.observableArray(),
		orderBy : ko.observable(undefined),
		surveysOrderBy : ko.observableArray(),
		errorMessage : ko.observable(undefined),
		searchResults : ko.observableArray(),
		emptySearch : ko.observable(false),
		searchedName : ko.observable(undefined),
		searchedSurname : ko.observable(undefined),
		searchedEmail : ko.observable(undefined),
		searchedBirthday : ko.observable(undefined),
		searchedDateTakenStart : ko.observable(undefined),
		searchedDateTakenEnd : ko.observable(undefined),
		searchedTechnology : ko.observable(undefined),
		searchedOrderBy : ko.observable(undefined),
		showPaginator : ko.observable(false),
		// Submit button callback function.
		submitButton : function() {

			// Prevents the button keeping the focus.
			$("#search-button").blur();

			// Performs validation.
			if (!validator.validateSearchForm()) {
				return;
			}

			// Saves the searched data in a different variable to prevent the
			// user changing the input fields and then page.
			viewModel.searchForm.searchedName(viewModel.searchForm.name());
			viewModel.searchForm
					.searchedSurname(viewModel.searchForm.surname());
			viewModel.searchForm.searchedEmail(viewModel.searchForm.email());
			viewModel.searchForm.searchedBirthday(viewModel.searchForm
					.birthday());
			viewModel.searchForm.searchedDateTakenStart(viewModel.searchForm
					.dateTakenStart());
			viewModel.searchForm.searchedDateTakenEnd(viewModel.searchForm
					.dateTakenEnd());
			viewModel.searchForm.searchedTechnology(viewModel.searchForm
					.technology());
			viewModel.searchForm
					.searchedOrderBy(viewModel.searchForm.orderBy());

			viewModel.searchForm.reloadResults(1);
		},
		currentPage : ko.observable(undefined),
		// Utility function used to reload the results at a fixed page.
		reloadResults : function(pageNumber) {
			viewModel.searchForm.currentPage(pageNumber);
			ajax.post("user/searchUser", null, {
				name : viewModel.searchForm.searchedName(),
				surname : viewModel.searchForm.searchedSurname(),
				email : viewModel.searchForm.searchedEmail(),
				birthday : moment.utc(viewModel.searchForm.searchedBirthday(),
						"DD-MM-YYYY"),
				dateTakenStart : moment.utc(viewModel.searchForm
						.searchedDateTakenStart(), "DD-MM-YYYY"),
				dateTakenEnd : moment.utc(viewModel.searchForm
						.searchedDateTakenEnd(), "DD-MM-YYYY"),
				technology : viewModel.searchForm.searchedTechnology(),
				orderBySurveyScore : viewModel.searchForm.searchedOrderBy(),
				page : pageNumber
			}, function(data) {

				// Sets error messages if any.
				if (data.errorMessage != null) {
					viewModel.searchForm.errorMessage(data.errorMessage);
				} else {
					viewModel.searchForm.errorMessage(null);
				}

				viewModel.searchForm.searchResults(data.searchResults);

				if (pageNumber == 1) {
					// Reinitializes the paginator
					// when the page number is 1
					// since the elments may be
					// changed.
					var itemsOnPage = data.searchResults.length;
					var paginator = new Pagination({
						container : $("#paginator"),
						pageClickCallback : viewModel.searchForm.reloadResults,
						maxVisibleElements : 12
					});
					paginator.make(data.pages, itemsOnPage);
				}

				// Checks if some results where returned.
				if (data.searchResults == null
						|| data.searchResults.length == 0) {
					viewModel.searchForm.emptySearch(true);
					viewModel.searchForm.showPaginator(false);
				} else {
					viewModel.searchForm.emptySearch(false);
					viewModel.searchForm.showPaginator(true);
				}

				loading.hideInPage("search-results", function() {

					// Initializes the accordions.
					$(".accordion").accordion("destroy").accordion({
						heightStyle : "content"
					});

				});

			}, false, "search-results");
		},
		// Deletes a user score, invoked by pressing the X on the search
		// results.
		deleteScore : function(email, surveyName, surveyTime, record,
				scoreIndex) {
			$("#score-delete-confirm").data(
					"callbackOk",
					function() {
						// Gets the currently opened accordion element
						// to restore it later.
						var currentAccordion = $(".accordion").accordion(
								"option", "active");

						ajax.post("user/deleteUserSurveyScore", null, {
							email : email,
							surveyName : surveyName,
							surveyTime : surveyTime
						}, function(data) {

							// Manually removes
							// the record.
							record.scores.splice(scoreIndex, 1);
							var data = viewModel.searchForm.searchResults();
							viewModel.searchForm.searchResults([]);
							viewModel.searchForm.searchResults(data);
							viewModel.searchForm.searchResults
									.valueHasMutated();

							loading.hideInPage("search-results", function() {

								// Initializes
								// the
								// accordions.
								$(".accordion").accordion("destroy").accordion(
										{
											heightStyle : "content",
											active : currentAccordion
										});
							});

						}, false, true);
					}).dialog("open");
		},
		// Gets the survey list for the order by section.
		getSurveysForOrder : function() {
			ajax.get("survey/getSurveyList?" + $.param({
				username : viewModel.currentUser.email(),
				technology : viewModel.searchForm.technology()
			}), null, function(data) {
				viewModel.searchForm.surveysOrderBy(data.surveyList);
			}, false, "surveys-order-by", function() {
				// Hides the sorting section if there are no surveys.
				if (viewModel.searchForm.surveysOrderBy().length == 0) {
					$(".surveys-order-by").hide();
				}
			});
		},
		// Creates the accordion header.
		formatAccordionHeader : function(data) {
			var header = "<span>" + data.name + " " + data.surname + "("
					+ data.email + ")";

			// If a search by survey has been performed then adds the survey
			// score on the right corner.
			var searchedSurvey = viewModel.searchForm.searchedOrderBy();
			if (searchedSurvey && data.scores != null) {
				// Gets the max score for the survey.
				var maxScore = 0;
				data.scores.forEach(function(item) {
					if (item.surveyName == searchedSurvey
							&& item.score > maxScore) {
						maxScore = item.score;
					}
				});
				header += "<span class='pull-right'>Punteggio massimo: "
						+ maxScore + "</span>";
			}

			header += "</span>";

			return header;
		},
		// Formats a date.
		formatDate : function(date, withTime) {
			if (withTime) {
				return moment.utc(date).format('DD/MM/YYYY HH:mm');
			} else {
				return moment.utc(date).format('DD/MM/YYYY');
			}
		},
		// Shows the survey score detail.
		showResultDetails : function(surveyName, surveyTime, userAnswers, email) {
			ajax.post("user/getUserSurveyResult", "pages/survey-result.html", {
				email : email,
				surveyName : surveyName,
				surveyTime : surveyTime
			}, function(data) {
				viewModel.surveyResultDetails.userAnswers(userAnswers);
				viewModel.surveyResultDetails.survey(data.survey);
				viewModel.bindData(viewModel.surveyResultDetails);
			});
		}
	},
	// Survey result details page.
	surveyResultDetails : {
		survey : ko.observable(undefined),
		userAnswers : ko.observableArray(),
		backButton : function() {
			navigation.toSearchForm(function() {
				viewModel.searchForm.reloadResults(viewModel.searchForm
						.currentPage());
			});
		},
		// Returns a css class to render the correct answers in a survey result.
		getRightAnswerClass : function(questionNumber, answerNumber) {
			var correctAnswer = viewModel.surveyResultDetails.survey().questions[questionNumber].correctAnswers
					.includes(answerNumber);
			if (correctAnswer) {
				return "survey-correct-answer";
			} else {
				return null;
			}
		}
	},

	// Activation page.
	activation : {
		message : ko.observable(undefined)
	},

	// Password recovery.
	passwordRecoveryLanding : {
		// New password.
		password : ko.observable(undefined),
		email : undefined,
		token : undefined,
		errorMessage : ko.observable(undefined),
		submitButton : function() {

			// Performs validation.
			if (!validator.validatePasswordRecoveryForm()) {
				return;
			}

			// Sends the request.
			ajax
					.post(
							"user/passwordRecovery",
							"pages/password-recovery-outcome.html",
							{
								email : viewModel.passwordRecoveryLanding.email,
								token : viewModel.passwordRecoveryLanding.token,
								newPassword : md5(viewModel.passwordRecoveryLanding
										.password())
							},
							function(data) {
								// Binds the data.
								viewModel.passwordRecoveryOutcome
										.message(data.errorMessage);
								viewModel
										.bindData(viewModel.passwordRecoveryOutcome);
							});
		}
	},

	// Outcome of the operation.
	passwordRecoveryOutcome : {
		message : ko.observable(undefined)
	},

	// Current logged in user.
	currentUser : {
		email : ko.observable(undefined),
		technology : ko.observable(undefined),
		role : ko.observable(undefined)
	},
	// Binds the data into the page.
	bindData : function(data) {
		ko.applyBindings({
			data : data
		}, $(".page-content")[0]);
	}
}