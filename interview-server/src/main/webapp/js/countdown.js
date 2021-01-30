/**
 * Timer for surveys.
 */
var countdown = {
	// Current elapsed seconds.
	currentSecs : undefined,
	// Starts the countdown.
	start : function(seconds) {
		countdown.currentSecs = seconds;
		countdown.decrement();
		countdown.keepSessionAlive();
	},
	// Decrements the countdown.
	decrement : function() {
		var currTime = moment().startOf('day')
	    .seconds(countdown.currentSecs--)
	    .format('H:mm:ss');
		
		$("#timer").html(currTime);

		// Continues counting if the survey is still in progress.
		if (viewModel.survey.survey() != undefined) {
			if (countdown.currentSecs !== -1) {
				setTimeout(countdown.decrement, 1000);
			} else {
				// If the counter reaches zero, submits the survey.
				viewModel.survey.submitButton(null, null, true);
			}
		}
	},
	
	// Prevents the session ending while performing a survey.
	keepSessionAlive : function() {
		ajax.get("user/extendUserSession", null, null, false, "noshow");
		
		// Continues refreshing the session each 10 minutes until the countdown is done.
		if (viewModel.survey.survey() != undefined) {
			setTimeout(countdown.keepSessionAlive, 600000);
		}
	}
}