var options = {
	ajax : {
		url : "/equipments/json",
		type : "GET",
		dataType : "json",
		data : {
			q : "{{{q}}}"
		}
	},
	locale : {
		emptyTitle : "Выбери снаряжение"
	},
	log : 3,
	preprocessData : function(data) {
		var i, l = data.length, array = [];
		if (l) {
			for (i = 0; i < l; i++) {
				array.push($.extend(true, data[i], {
					value : data[i].id,
					text : data[i].name
				}));
			}
		}
		return array;
	}
};

$("#equipList").selectpicker().filter(".with-ajax").ajaxSelectPicker(options);
$("#equipList").selectpicker('refresh');
$('select').trigger('change');

function chooseSelectpicker(index, selectpicker) {
	$(selectpicker).val(index);
	$(selectpicker).selectpicker('refresh');
}