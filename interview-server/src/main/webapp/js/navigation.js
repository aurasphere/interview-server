/**
 * Handles navigation.
 */
var navigation = {

	// Goes to the main menu.
	toMainMenu : function(showAnimation) {
		loading.loadPage("pages/main-menu.html", function() {
			viewModel.bindData(viewModel.mainMenu);
			// Shows the main menu animation.
			if (showAnimation) {
				animation.mainMenu();
			}

			// Hides the logout button.
			$("#logout").hide();
		});
	},
	// Survey list page.
	toSurveyList : function() {
		ajax.get("survey/getSurveyList?" + $.param({
			username : viewModel.currentUser.email(),
			technology : viewModel.currentUser.technology()
		}), "pages/survey-list.html", function(data) {
			viewModel.surveyList.surveys(data.surveyList);
			viewModel.bindData(viewModel.surveyList);

			// Shows the logout button.
			$("#logout").show();

			// Clean up the survey status.
			viewModel.survey.survey(null);

			// Initializes the dialog.
			$("#survey-autosubmit-info").dialog({
				autoOpen : false,
				draggable : false,
				resizable : false,
				dialogClass : 'fixed-dialog',
				height : "auto",
				width : 400,
				modal : true,
				open : function() {
					$('.ui-widget-overlay').addClass('custom-overlay');
				},
				close : function() {
					$('.ui-widget-overlay').removeClass('custom-overlay');
				},
				buttons : {
					Ok : function() {
						$(this).dialog("close");
					}
				}
			});
		});

	},
	// Survey detail page.
	toSurvey : function(data, event) {
		event.preventDefault();
		ajax.get("survey/getSurvey/" + data, "pages/survey.html",
				function(data) {
					// Initializes the survey questions in order to allow
					// checkbox checked bindings.
					data.survey.questions.forEach(function(element) {
						element.checkedAnswers = [];
					});
					viewModel.survey.survey(data.survey);
					viewModel.bindData(viewModel.survey);
					countdown.start(viewModel.survey.survey().time);

					// Logging out is not allowed during a survey.
					$("#logout").hide();

					// Initializes the submit confirmation button and dialog.
					$("#survey-submit-confirm").dialog(
							{
								autoOpen : false,
								draggable : false,
								resizable : false,
								dialogClass : 'fixed-dialog',
								height : "auto",
								width : 400,
								modal : true,
								open : function() {
									$('.ui-widget-overlay').addClass(
											'custom-overlay');
								},
								close : function() {
									$('.ui-widget-overlay').removeClass(
											'custom-overlay');
								},
								buttons : {
									"Conferma" : function() {
										$(this).dialog("close");
										viewModel.survey.submitButton();
									},
									"Annulla" : function() {
										$(this).dialog("close");
									}
								}
							});
					$("#survey-submit-button").click(function() {
						// Prevents the button keeping the focus.
						$("#survey-submit-button").blur();

						$("#survey-submit-confirm").dialog("open");
					});
					
					// Formats the code lines.
					PR.prettyPrint();
				});
	},

	// Goes to the search form.
	toSearchForm : function(callback) {
		// Loads the data for the tech select on the first page.
		ajax.get("survey/getTechList", "pages/search-form.html",
				function(data) {
					// Initializes datepickers.
					$(".date-picker").datepicker();

					// Cleans up the view.
					viewModel.searchForm.errorMessage(null);
					viewModel.searchForm.searchResults(null);
					viewModel.searchForm.technology(null);
					viewModel.searchForm.orderBy(null);
					viewModel.searchForm.emptySearch(false);

					// Binds the form data.
					viewModel.searchForm.techList(data.techList);
					viewModel.bindData(viewModel.searchForm);

					// Initializes the accordions.
					$(".accordion").accordion({
						heightStyle : "content"
					});

					$("#score-delete-confirm").dialog(
							{
								autoOpen : false,
								draggable : false,
								resizable : false,
								dialogClass : 'fixed-dialog',
								height : "auto",
								width : 400,
								modal : true,
								open : function() {
									$('.ui-widget-overlay').addClass(
											'custom-overlay');
								},
								close : function() {
									$('.ui-widget-overlay').removeClass(
											'custom-overlay');
								},
								buttons : {
									"Conferma" : function() {
										$(this).dialog("close");
										var callback = $(this).data(
												"callbackOk");
										if (callback != null) {
											callback();
										}
									},
									"Annulla" : function() {
										$(this).dialog("close");
									}
								}
							});
					// If a callback is specified, executes it.
					if (callback != null) {
						callback();
					}
				});
	},

	// Performs the logout.
	logout : function(event) {
		ajax.get("logout", null, function(data) {
			// Clears the user data.
			viewModel.currentUser.email(null);
			viewModel.currentUser.technology(null);
			viewModel.currentUser.role(null);

			// Hides the logout button.
			$("#logout").hide();

			// Returns to the first page.
			navigation.toMainMenu(false);
		}, true);

	},

	// Performs an account activation.
	toAccountActivation : function(email, token) {
		ajax.post("user/confirmAccount", "pages/activation-landing.html", {
			email : email,
			token : token
		}, function(data) {
			// Binds the data.
			viewModel.activation.message(data.errorMessage);
			viewModel.bindData(viewModel.activation);
		});
	},

	// Performs a password recovery.
	toPasswordRecovery : function(email, token) {
		loading.loadPage("pages/password-recovery-landing.html", function() {
			// Cleans the view and binds the data.
			viewModel.passwordRecoveryLanding.password(null);
			viewModel.passwordRecoveryLanding.errorMessage(null);

			// Binds the new data.
			viewModel.passwordRecoveryLanding.email = email;
			viewModel.passwordRecoveryLanding.token = token;
			viewModel.bindData(viewModel.passwordRecoveryLanding);
		});
	}
}