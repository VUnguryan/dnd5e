$(document).ready(function() {
	$('a.toggle-vis').on( 'click', function (e) {
        e.preventDefault();
        var column = table.column( $(this).attr('data-column') );
        column.visible( ! column.visible() );
    });
	$('#spells').one('preXhr.dt', function(e, settings, data) {
			data.searchPanes = {};
			data.searchPanes["heroClass.0"] = '[[${heroClass.name}]]';
		}
	)
}