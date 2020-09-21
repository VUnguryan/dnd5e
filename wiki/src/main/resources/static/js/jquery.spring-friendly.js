(function($) {
	var r20 = /%20/g, rbracket = /\[\]$/, rCRLF = /\r?\n/g, rsubmitterTypes = /^(?:submit|button|image|reset|file)$/i, rsubmittable = /^(?:input|select|textarea|keygen)/i;

	function customBuildParams(prefix, obj, traditional, add) {
		var name;

		if (jQuery.isArray(obj)) {
			jQuery.each(obj, function(i, v) {
				if (traditional || rbracket.test(prefix)) {
					add(prefix, v);

				} else {
					customBuildParams(prefix + "["
							+ (typeof v === "object" ? i : "") + "]", v,
							traditional, add);
				}
			});

		} else if (!traditional && jQuery.type(obj) === "object") {
			for (name in obj) {
				customBuildParams(prefix + "." + name, obj[name], traditional,
						add);
			}

		} else {
			add(prefix, obj);
		}
	}
	$.param = function(a, traditional) {
		var prefix, s = [], add = function(key, value) {
			value = jQuery.isFunction(value) ? value() : (value == null ? ""
					: value);
			s[s.length] = encodeURIComponent(key) + "="
					+ encodeURIComponent(value);
		};
		if (traditional === undefined) {
			traditional = jQuery.ajaxSettings
					&& jQuery.ajaxSettings.traditional;
		}
		if (jQuery.isArray(a) || (a.jquery && !jQuery.isPlainObject(a))) {
			jQuery.each(a, function() {
				add(this.name, this.value);
			});

		} else {
			for (prefix in a) {
				customBuildParams(prefix, a[prefix], traditional, add);
			}
		}
		return s.join("&").replace(r20, "+");
	};
})(jQuery);