$('#baseRule').click(function(){
	processSettings('base', $('#baseRule'));
});
$('#settingRule').click(function(){
	processSettings('setting', $('#settingRule'));
});
$('#moduleRule').click(function(){
	processSettings('module', $('#moduleRule'));
});
$('#homeRule').click(function(){
	processSettings('home', $('#homeRule'));
});
jQuery(function($) {
   $('#settingDropdown').parent().on('hidden.bs.dropdown', function () {
	    window.location.reload();
   });
});
function processSettings(urlSufix, rule) { 
	$.ajax( {
	    type: 'POST',
	    url: '/settings/' + urlSufix,
	    data: {
	    	ruleSetting : rule.is(':checked'),
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