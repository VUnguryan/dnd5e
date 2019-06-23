function sortTable(n) {
	var baseUrl = '[[@{/hero/spells?}]]';
	var sort = getParameterByName('sort');
	var page = getParameterByName('page');
	var parameters = "";
	if (n == 0) {
		if (sort == 'level,desc') {
			parameters = "sort=level,asc";
		} else {
			parameters = "sort=level,desc";
		}
	}
	if (n == 1) {
		if (sort == 'name,desc') {
			parameters = "sort=name,asc";
		} else {
			parameters = "sort=name,desc";
		}
	}
	if (n == 2) {
		if (sort == 'school,desc') {
			parameters = "sort=school,asc";
		} else {
			parameters = "sort=school,desc";
		}
	}
	var type = getParameterByName('classType');
	if (type != null) {
		parameters += "&classType=" + type;
	}
	var school = getParameterByName('schoolType');
	if (school != null) {
		parameters += "&schoolType=" + school;
	}
	location.href = baseUrl + parameters;
}

function getParameterByName(name, url) {
	if (!url)
		url = window.location.href;
	name = name.replace(/[\[\]]/g, "\\$&");
	var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"), results = regex
			.exec(url);
	if (!results)
		return null;
	if (!results[2])
		return '';
	return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function spellClicked(value) {
	var baseUrl = '[[@{/hero/spells/spell/}]]';
	location.href = baseUrl + value;
}

function filterLevel(select) {
	var baseUrl = '[[@{/hero/spells?}]]';
	var minLevel = $("#minLevel").val();
	var maxLevel = $("#maxLevel").val();
	var sort = getParameterByName('sort');
	var parameters = "minLevel=" + minLevel + "&maxLevel=" + maxLevel;
	if (sort != null) {
		parameters += "&sort=" + sort;
	}
	window.location = baseUrl + parameters;
}

function filter(select) {
	var baseUrl = '[[@{/hero/spells?}]]';
	var type = $("#classType").val();
	var schoolType = $("#schoolType").val();
	var timeCast = $("#timeCast").val();
	var verbal = $("#verbal:checked").val();
	var somatic = $("#somatic:checked").val();
	var material = $("#material:checked").val();
	var sort = getParameterByName('sort');
	var parameters = "classType=" + type + "&schoolType=" + schoolType
			+ "&timeCast=" + timeCast + "&verbal=" + verbal + "&somatic="
			+ somatic + "&material=" + material;
	if (sort != null) {
		parameters += "&sort=" + sort;
	}
	window.location = baseUrl + parameters;
}