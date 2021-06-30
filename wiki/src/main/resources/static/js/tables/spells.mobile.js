	$(document).ready(function() {
		$('a.toggle-vis').on( 'click', function (e) {
			e.preventDefault();
			var column = table.column( $(this).attr('data-column') );
			column.visible( ! column.visible() );
	    });
		var table = $('#spells').DataTable( {
			stateSave: true,
			iDisplayLength : 14,
			dom: 'Bfrtip',
			processing : true,
			serverSide : true,
			ajax : '/spells',
			order : [[1, 'asc']],
			columns : [{
				data : "school",
				render : function(data, type) {
					if (type === 'display') {
						var icon = '';
							switch (data) {
							case 'воплощение':
								icon = '<img class="invert" src="/resources/icons/Icon.2_99.png" title="воплощение" height="25">';
								break;
							case 'вызов':
								icon = '<img class="invert" src="/resources/icons/Icon.4_26.png" title="вызов" height="25">';
								break;
							case 'иллюзия':
								icon = '<img class="invert" src="/resources/icons/Icon.1_39.png" title="иллюзия" height="25">';
								break;
							case 'некромантия':
								icon = '<img class="invert" src="/resources/icons/Icon.3_36.png" title="некромантия" height="25">';
								break;
							case 'ограждение':
								icon = '<img class="invert" src="/resources/icons/Icon.2_38.png" title="ограждение" height="25">';
								break;
							case 'очарование':
								icon = '<img class="invert" src="/resources/icons/Icon.2_21.png" title="очарование" height="25">';
								break;
							case 'преобразование':
								icon = '<img class="invert" src="/resources/icons/Icon.7_38.png" title="преобразование" height="25">';
								break;
							case 'прорицание':
								icon = '<img class="invert" src="/resources/icons/Icon.6_22.png" title="прорицание" height="25">';
								break;
							}
							return icon;
						}
						return data;
					},
					width : "1%",
					className: "text-center"
				}, 
				{
					data : "level",
					width : "1%",
					className: "text-center"
				},
				{
					data : "heroClass",
					render: {
						_: '[, ].name',
						sp:'[].name'
					},
					width : "1%",
					className: "text-center"
				},
				{
					data : "name",
					width : "20%", 
					render : function(data, type, row) {
						if (type === 'display') {
							var school = '';
							var result = '<h6>' + data + ' <small class="text-secondary">(' + row.englishName + ')';
							result += '</small></div>';
							return result;
						}
						return data;
					}, 
				},
				{
					data : "timeCast",
					width : "5%",
					className: "dt-head-center",
					className: "dt-body-center",
					render : function(data, type) {
						if (type === 'display') {
							if (data.includes('1 реакция')){
								return '1 реакция';
							}
							return data;
						}
						return data;
					}
				},
				{
					data : "duration",
					width : "5%",
				    className: "text-center"
				},
				{
					data : "distance",
					width : "5%",
				    className: "text-center"
				},
				{
					data : "ritual",
					width : "3%",
					className: "dt-head-center",
					className: "dt-body-center",
					render : function(data, type) {
						if (type === 'display') {
							var icon = '';
							switch (data) {
							case 'true':
								icon = '<img class="invert" data-toggle="tooltip" data-placement="top" src="/resources/icons/Icon.7_53.png" title="ритуал" height="25">';
								break;
							}
							return icon;
						}
						return data;
					},
					searchable: false
				},
				{
					data : "concentration",
					render : function(data, type) {
						if (type === 'display') {
							var icon = '';
								switch (data) {
								case 'true':
									icon = '<img class="invert" data-toggle="tooltip" data-placement="top" src="/resources/icons/Icon.5_05.png" title="концентрация" height="25">';
									break;
								}
								return icon;
							}
							return data;
						},
						width : "2%",
						className: "text-center",
						searchable: false
					},
					{
						data : "verbalComponent",
						width : "1%",
						className: "text-center",
						searchable: false
					},
					{
						data : "somaticComponent",
						width : "1%",
						className: "text-center",
						searchable: false
					},
					{
						data : "materialComponent",
						width : "1%",
						className: "text-center",
						searchable: false		
					},
					{
						data : "damageType",
						width : "10%",
						className: "text-center",
						searchable: false		
					},
					{
						data : "book",
						width : "5%",
						 className: "text-center",
						searchable: false		
					},
					{
						data : 'englishName',
						width : "1%",
						orderable: false,
					},
					{
						data : 'consumable',
						width : "1%",
						orderable: false,
						searchable: false,
					},],
					columnDefs : [{
						"targets": [ 0 ],
						"visible": false
					},
					{
						"targets": [ 2 ],
						"visible": false
					},
					{
						"targets": [ 4 ],
						"visible": false
					},
					{
						"targets": [ 5 ],
						"visible": false
					},
					{
						"targets": [ 6 ],
						"visible": false
					},
					{
						"targets": [ 7 ],
						"visible": false
					},
					{
						"targets": [ 8 ],
						"visible": false
					},
					{
						"targets": [ 9 ],
						"visible": false
					},
					{
						"targets": [ 10 ],
						"visible": false
					},
					{
						"targets": [ 11 ],
						"visible": false
					},
					{
						"targets": [ 12 ],
						"visible": false
					},
					{
						"targets": [ 13 ],
						"visible": false
					},
					{
						"targets": [ 14 ],
						"visible": false
					},
					{
						targets: [ 15 ],
						visible: false
					}],
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
								 text: 'Школа',
								 action: function ( e, dt, node, config ) {
									 dt.column( 0 ).visible( ! dt.column( 0 ).visible() );
								 }
							 },
							 {
								 text: 'Время',
								 action: function ( e, dt, node, config ) {
									 dt.column( 4 ).visible( ! dt.column( 4 ).visible() );
								 }
							 },
							 ,
							 {
								 text: 'Длительность',
								 action: function ( e, dt, node, config ) {
									 dt.column( 5 ).visible( ! dt.column( 5 ).visible() );
								 }
							 },
							 {
								 text: 'Дистанция',
								 action: function ( e, dt, node, config ) {
									 dt.column( 6 ).visible( ! dt.column( 6 ).visible() );
								 }
							 },
							 {
								 text: 'Ритуал',
								 action: function ( e, dt, node, config ) {
									 dt.column( 7 ).visible( ! dt.column( 7 ).visible() );
								}
							 },
							 {
								 text: 'Концентрация',
								 action: function ( e, dt, node, config ) {
									 dt.column( 8 ).visible( ! dt.column( 8 ).visible() );
								 }
							 },
							 {
								 text: 'Компоненты',
								 action: function ( e, dt, node, config ) {
									 dt.column( 9 ).visible( ! dt.column( 9 ).visible() );
									 dt.column( 10 ).visible( ! dt.column( 10 ).visible() );
									 dt.column( 11 ).visible( ! dt.column( 11 ).visible() );
								 }
							 },
							 {
								 text: 'Тип урона',
								 action: function ( e, dt, node, config ) {
									 dt.column( 12 ).visible( ! dt.column( 12 ).visible() );
								 }
							 },
							 {
								 text: 'Источник',
								 action: function ( e, dt, node, config ) {
									 dt.column( 13 ).visible( ! dt.column( 13 ).visible() );
								 }
							 }
							 ]
						}
						],
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
							previous : "<",
							next : ">",
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
				        }
					},
		ordering : true,
	});
	$('#spells tbody').on('click', 'tr', function () {
		var tr = $(this).closest('tr');
		var row = table.row( tr );
		var url = '/hero/spells/fragment/spell/' + row.data().id;
		$('#spellWindow').find(".modal-body").load(url);
		$('#spellWindow').modal('show');
	});
});