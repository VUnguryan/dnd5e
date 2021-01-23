package com.dnd5e.wiki.util;

public final class HtmlConverter {
	public static String toHtml(String text) {
		StringBuilder builder = new StringBuilder();

		for (String line : text.split("\\r")) {
			builder.append("<p class=\"text-justify mb-1\">");
			builder.append(line);
			builder.append("</p>");
		}
		return builder.toString();
	}
}
