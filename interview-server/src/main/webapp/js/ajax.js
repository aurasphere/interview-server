/**
 * Handles the ajax requests.
 */
var ajax = {
	// Base URL of this application.
	baseUrl : window.location.href.split("?")[0] + "rest/",
	// Ajax POST request.
	post : function(path, newPage, data, callbackOk, sync,
			loadingTargetClassName) {
		$.ajax({
			url : ajax.baseUrl + path,
			method : "POST",
			data : JSON.stringify(data),
			async : !sync,
			contentType : "application/json; charset=utf-8",
			beforeSend : ajax.beforeSendDefault(loadingTargetClassName),
			success : function(data) {
				ajax.handleSuccessDefault(data, newPage, callbackOk,
						loadingTargetClassName);
			},
			error : function(request, status, error) {
				ajax.handleErrorDefault(request, status, error);
			}
		})
	},
	// Ajax GET request.
	get : function(path, newPage, callbackOk, sync, loadingTargetClassName,
			loadingCallback) {
		$.ajax({
			url : ajax.baseUrl + path,
			method : "GET",
			async : !sync,
			beforeSend : ajax.beforeSendDefault(loadingTargetClassName),
			success : function(data) {
				ajax.handleSuccessDefault(data, newPage, callbackOk,
						loadingTargetClassName, loadingCallback);
			},
			error : function(request, status, error) {
				ajax.handleErrorDefault(request, status, error);
			}
		});
	},
	// Default pre-send function.
	beforeSendDefault : function(loadingTargetClassName) {
		// Shows the loading on a new page or in the same page according to the
		// value passed as argument.
		if (loadingTargetClassName != undefined) {
			loading.showInPage(loadingTargetClassName);
		} else {
			loading.show();
		}
	},
	// Default success function.
	handleSuccessDefault : function(data, newPage, callbackOk,
			loadingTargetClassName, loadingCallback) {
		// Saves the current data and loads the next page.
		if (newPage != null) {
			loading.hide(newPage, function() {
				// If a callback is specified, call it.
				if (callbackOk != null) {
					callbackOk(data);
				}
			});
		} else {
			// If a callback is specified, call it.
			if (callbackOk != null) {
				callbackOk(data);
			}
			// If the show loading in page is specified, shows the loading on
			// the same page.
			if (loadingTargetClassName != undefined) {
				loading.hideInPage(loadingTargetClassName, loadingCallback);
			}
		}
	},
	// Default error function.
	handleErrorDefault : function(request, textStatus, errorThrown) {
		// Handles aborted calls by refreshing.
		if (request.statusText == 'abort') {
			return;
		}
		// If an error occurs, get to the error page.
		loading.hide("pages/error.html");
	}
}