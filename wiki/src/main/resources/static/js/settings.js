$(document).ready( function () {
	$.ajax( {
	    type: 'GET',
	    url: '/settings/',
	    success: function(data) {
	        $('#message').html(data);
	    }
	});
});
var toReload = false;
$('#baseRule').click(function(){
	toReload = true;
	processSettings('base', $('#baseRule'));
});
$('#settingRule').click(function(){
	toReload = true;
	processSettings('setting', $('#settingRule'));
});
$('#moduleRule').click(function(){
	toReload = true;
	processSettings('module', $('#moduleRule'));
});
$('#homeRule').click(function(){
	toReload = true;
	processSettings('home', $('#homeRule'));
});
jQuery(function($) {
   $('#settingDropdown').parent().on('hidden.bs.dropdown', function () {
	   if (toReload){
		   window.location.reload();
	        toReload = false;
	   } 
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