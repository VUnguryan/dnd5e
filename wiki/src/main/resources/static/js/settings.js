$('#official').click(function(){
	processSettings();
});

function processSettings() { 
	$.ajax( {
	    type: 'POST',
	    url: '/settings',
	    data: {
	    	homeRule : $('#official').is(':checked'),
	    	},
	    success: function(data) {
	        $('#message').html(data);
	    }
	});
}
$(function() {
	$('[data-toggle="tooltip"]').tooltip({
	     'delay': { show: 600, hide: 100 }
	});
})