/**
 * Handles page loadings.
 */
var loading = {
	// Shows a loading on a new page.
	show : function() {
		this.loadPage("pages/loading.html");
	},
	// Hides a loading shown in a new page by loading another page.
	hide : function(newPage, callback) {
		this.loadPage(newPage, callback);
	},
	// Shows a loading on the same page. The loading name should be equal to the
	// targetClassName with a "-loading" suffix.
	showInPage : function(targetClassName) {
		$("." + targetClassName).hide();
		$("." + targetClassName + "-loading").load("pages/loading.html",
				function() {
					// Changes the icon color to black since it is showing it on
					// a white page.
					$("#loading-icon").removeClass("white").addClass("black");
				}).fadeIn();

	},
	// Hides a loading shown in the same page. The loading name should be equal
	// to the targetClassName with a "-loading" suffix.
	hideInPage : function(targetClassName, callback) {
		$("." + targetClassName + "-loading").empty();
		$("." + targetClassName).show().fadeIn(callback);
	},
	// Loads a page.
	loadPage : function(page, callback) {
		// Loads a page with a fade effect.
		$("section").load(page, callback).hide().fadeIn();
	}
}
