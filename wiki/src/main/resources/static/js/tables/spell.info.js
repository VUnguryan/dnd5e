$(document).ready(function() {
	var table = $('#spells').DataTable();
	table.on( 'draw.dt', function () {
		if ( table.data().count() === 1) {
    		table.row(0).select();
    		var tr = $(this).closest('tr');
    		var row = table.row( tr );
			var element = document.getElementById("info");
			var url = '/hero/spells/fragment/spell/' + table.row(0).data().id;
			element.innerHTML = $("#info").load(url);
		}
	});
	$('#spells tbody').on('click', 'tr', function () {
		var tr = $(this).closest('tr');
		var table = $('#spells').DataTable();
		var row = table.row( tr );
		var element = document.getElementById("info");
		var url = '/hero/spells/fragment/spell/' + row.data().id;
		element.innerHTML = $("#info").load(url);
	});
});