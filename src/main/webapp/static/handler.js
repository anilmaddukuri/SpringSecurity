var handler = function(event) {
	console.log(event.type);
	console.log("clicked");
	event.preventDefault();
};
$(function() {
	$('.form-signin').on('click', handler);
});
