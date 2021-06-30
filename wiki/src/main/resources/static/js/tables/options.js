$(document).ready(function() {
	$('a.toggle-vis').on( 'click', function (e) {
		e.preventDefault();
		var column = table.column( $(this).attr('data-column') );
		column.visible( ! column.visible() );
	});
	var table = $('#options').DataTable( {
		iDisplayLength : 25,
		dom: '<"top"<"left-col"f><"right-col"B>>rti',
		serverSide : true,
		ajax : '/options',
		scrollY: "65vh",
		scrollCollapse: true,
		scroller: true,
		paging: false,
		select: {
		style: 'single'
		},
		autoWidth: false,
		columns : [{
			data : "name",
			width : "30%", 
			render : function(data, type, row) {
				if (type === 'display') {
					return '<h6>' + data + (row.englishName ? '<small class="text-secondary"> (' + row.englishName + ')</small>' : '') 
							+ '</h6>' + '<div class="text-secondary"><small>' + row.book + '</small></div>';
				}
				return data;
			}, 
		},
		{
			data : "optionTypes",
			width : "10%",
			searchable: false,
		}, 
		{
			data : "prerequisite",
			width : "30%"
		},
		{
			data : "level",
			width : "1%",
			searchable: false,
		},
		{
			data : "book",
			width : "10%",
			searchable: false,
		},
		{
			data : "englishName",
			width : "1%"
		},],
		columnDefs : [
		{
			searchPanes: {
				preSelect: [preSelectOptionType  === null ?  '' : preSelectOptionType]
			},
			"targets": [ 1 ],
		},
		{
			"targets": [ 4 ],
			"visible": false
		},
		{
			"targets": [ 5 ],
			"visible": false
		},],
		buttons: [
            {
                extend: 'searchPanes',
            	className: 'btn-main btn-sm btn-color-main',
            },
            {
				extend: 'colvis',
				text: 'Столбцы',
				className: 'btn-main btn-sm btn-color-main',
				buttons: [{
						text: 'Источник',
						action: function ( e, dt, node, config ) {
							dt.column( 4 ).visible( ! dt.column( 4 ).visible() );
						}
				},]
		}],
		language : {
			processing : "Загрузка...",
			search : "Поиск",
			lengthMenu : "Показывать _MENU_ записей на странице",
			zeroRecords : "Ничего не найдено",
			info : "Показано с _START_ до _END_ из _TOTAL_",
			infoEmpty : "Нет доступных записей",
			infoFiltered : "(filtered from _MAX_ всего)",
			paginate : {
				first : "В начало",
				previous : "Предыдущая",
				next : "Следущая",
				last : "В конец"
			},
			searchPanes: {
				clearMessage : "Сбросить",
				title : {
					_: 'Отфильтровано - %d',
					0: 'Фильтры не активны (Ctrl или Shift для множественного выбора)',
					1: 'Один фильтр выбран',
				},
				collapse: 'Фильтры (%d)'
			},
		},
		ordering : true,
	});
});