$(document).ready(function() {
	$('#options2 tbody').on('click', 'tr', function () {
		var tr = $(this).closest('tr');
		var table = $('#options2').DataTable();
		var row = table.row( tr );
		$('#spellWindow').find(".modal-body").html(format(row.data()));
		$('#spellWindow').modal('show');
	});
});
function format ( d ) {
	var name = '<h5 class="header mb-1">' + d.name + ' <small class="text-secondary">(' + d.englishName + ')'+'</small></h5>';
	var info = name + '<p class ="mb-1 mt-1"><small>' + d.type + '</small></p>';
	info+='<hr class ="line1"/><p class ="mb-2 mt-1">';
	info+='<p class ="mb-1 mt-1"><strong>Требования:</strong> ';
	if (d.level === null) {
		info+= d.prerequisite + '.';
	}
	else if (d.prerequisite === 'Нет'){
		info+=' Уровень ' + d.level + ' или выше';
	} else {
		info+=d.prerequisite + ' и ' +' уровень ' + d.level + ' или выше.';
	}
	info+='</p>';
	info+='<hr class ="mb-1 mt-1" align="center" size="5" color="#822000" />';
	info+=d.description + '<p class="text-right font-weight-light"><small>Источник: '+ d.book + '</small></p>';
	return  info; 
}