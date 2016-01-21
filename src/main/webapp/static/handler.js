//Event publisher
/*var publishSignUpClickEvent = function(event) {
	console.log($('#signUp').attr('data-custom-event'));
	var modifiedEvent = $('#signUp').attr('data-custom-event');
	$('#signUp').trigger(modifiedEvent);
	event.preventDefault();
};*/

//Event handler
var signInHandler = function(event) {
	console.log(event.type);
	console.log("clicked");
};

var indexSignUpHandler = function(event) {
	$.ajax({
		url: "signUp.html",
		type: "GET",
		success: function(json) {
			$("#formDiv").replaceWith(json);
		},
		error: function(xhr, status, errorThrown) {
			console.log("status: " + status);
			console.log("error thrown: " + errorThrown);
			console.dir(xhr);
		}
	});
};

var actualsignUpHandler = function(event) {
	console.log("Make a call to server to register the user");
	return true;
};

$(function() {
	$('#signUp').on('click', indexSignUpHandler);
	$('#signIn').on('click', signInHandler);
});
