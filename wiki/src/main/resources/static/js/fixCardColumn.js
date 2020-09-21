$(function() {
	var maxHeight = 0;
	$('.card').each(function() {
		if ($(this).height() > maxHeight) {
			maxHeight = $(this).height();
		}
	});
	$('.card').each(function() {
		$(this).css('min-height', maxHeight);
	});
});