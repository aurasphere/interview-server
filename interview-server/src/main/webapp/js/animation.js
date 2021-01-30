/**
 * Handles the animations.
 */
var animation = {
	mainMenu : function() {
		// Animations durations.
		var messageFadeInDuration = 2000;
		var messageSlideUpDuration = 1000;
		var showButtonsAfter = messageFadeInDuration + messageSlideUpDuration;

		// Shows the buttons after all the animations ended.
		setTimeout(function() {
			$(".button").removeClass("invisible");
		}, showButtonsAfter);

		// Hides the buttons in order to show only the slide up after the fade
		// in ended.
		$(".button").addClass("invisible").hide().delay(messageFadeInDuration)
				.slideDown(messageSlideUpDuration);
		// Hides the main messages and then fades it in.
		$("#main-menu-message").hide().fadeIn(messageFadeInDuration);
	}
}