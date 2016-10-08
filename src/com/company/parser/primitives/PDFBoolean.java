package com.company.parser.primitives;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFBoolean extends PDFObject {
	private Boolean value = null;

	public PDFBoolean() {
	}

	public PDFBoolean(Boolean value) {
		super();
		this.value = value;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		if (value != null)
			this.value = value;
	}
    public String toString() {
        return String.valueOf(value);
    }

	@Override
	public PDFBoolean parse(CharSequence bytes, int offset) throws IOException {
		if (offset < bytes.length()) {
			bytes.subSequence(offset, bytes.length());
			Pattern booelan_pattern = Pattern.compile("(?i)^\\s*(?<value>true|false)\\s*$");
			Matcher matcher = booelan_pattern.matcher(bytes);
			if (matcher.matches()) {
				return new PDFBoolean(Boolean.valueOf(matcher.group("value")));
			}
		}
		return null;
	}
}
