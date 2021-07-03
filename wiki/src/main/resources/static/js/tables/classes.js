$(document).ready(function() {
	$('a.toggle-vis').on( 'click', function (e) {
		e.preventDefault();
		var column = table.column( $(this).attr('data-column') );
		column.visible( ! column.visible() );
	});
	var table = $('#classes').DataTable(
	{
		stateSave : true,
		dom: '<"top"<"left-col"f><"right-col"B>>ti',
		serverSide : true,
		ajax : '/data/classes',
		scrollY: "65vh",
		scrollCollapse: true,
		scroller: true,
		paging: false,
		select: {
			style: 'single'
		},
		autoWidth: false,
		columns : [
		{
			data : "id",
			width : "3%",
			orderable: false,
			searchable: false,
			render : function(data, type, row) {
				if (type === 'display') {
					return '<img class="scale" src="/resources/classes/'+row.id+'.png" height="45">';
				}
				return data;
			},
		},
		{
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
			data : "diceHp",
			width : "1%",
			render : function(data, type, row) {
				if (type === 'display') {
					return '1к' + data;
				}
				return data;
			},
		},
		{
			data : "book",
			width : "10%",
			searchable: false,
		},
		{
			data : "englishName",
			width : "1%",
		},
		],
		columnDefs : [
		{
			targets : [ 3 ],
			visible : false
		},
		{
			targets : [ 4 ],
			visible : false
		},
		],
		buttons: [
		{
			extend: 'collection',
			tag: 'img',
			className: 'btn-main btn-color-main',
			attr: {
				src: '/resources/icons/svg/grid_view_black_24dp.svg'
			},
			action: function ( e, dt, node, config ) {
				window.location.href = "/classes/old";
			},
			titleAttr: 'Плитка'
		},
		{
			extend: 'searchPanes',
			className: 'btn-main btn-sm btn-color-main',
		},
		{
			extend: 'colvis',
			text: 'Столбцы',
			className: 'btn-square btn-color-main',
			buttons: [
			{
				text: 'Иконка',
				action: function ( e, dt, node, config ) {
					dt.column(0).visible(!dt.column(0).visible() );
				}
			},
			{
				text: 'Кость',
				action: function ( e, dt, node, config ) {
					dt.column(2).visible(!dt.column(2).visible() );
				}
			},
			{
				text: 'Источник',
				action: function ( e, dt, node, config ) {
					dt.column(3).visible(!dt.column(3).visible() );
				}
			},
		]},
	],
	language : {
		processing : "Загрузка...",
		searchPlaceholder: "Поиск ",
		search : "_INPUT_",
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
	table.on( 'draw.dt', function () {
		if ( table.data().count() === 1) {
			table.row(0).select();
			var tr = $(this).closest('tr');
			var row = table.row( tr );
			var element = document.getElementById("info");
			var url = '/classes/fragment/' + table.row(0).data().id;
			element.innerHTML = $("#info").load(url);
		}
	});
	$('#classes tbody').on('click', 'tr', function () {
		var tr = $(this).closest('tr');
		var row = table.row( tr );
		var element = document.getElementById("info");
		var url = '/classes/fragment/' + row.data().id;
		element.innerHTML = $("#info").load(url);
	});
	table.buttons().container().appendTo( '#example_wrapper .col-md-6:eq(0)' );
});
function onMoveImg(a){
	if(a.pixelHeight==90){
		function fun(){
			if(a.pixelHeight<150){
				a.pixelLeft-=2;
				a.pixelTop-=2;
				a.pixelWidth+=4;
				a.pixelHeight+=4;
				a.zIndex+=1;
				setTimeout(fun,20);
			}
		}
		fun();
	}
}