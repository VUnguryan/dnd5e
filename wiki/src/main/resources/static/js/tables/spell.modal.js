$(document).ready(function() {
	$('#spells tbody').on('click', 'tr', function () {
		var tr = $(this).closest('tr');
		var table = $('#spells').DataTable();
		var row = table.row( tr );
		var url = '/hero/spells/fragment/spell/' + row.data().id;
		$('#spellWindow').find(".modal-body").load(url);
		$('#spellWindow').modal('show');
	});
});