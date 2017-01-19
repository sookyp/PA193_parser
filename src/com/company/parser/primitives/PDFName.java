package com.company.parser.primitives;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by val on 02/10/16.
 */
public class PDFName extends PDFObject {
	private String value = null;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public PDFName parse(CharSequence bytes, int offset) throws IOException, InvalidException {
		if (offset < bytes.length()) {
			bytes.subSequence(offset, bytes.length());
			Pattern name_pattern = Pattern.compile("^\\s*\\/(?<value>[\\S]*?)\\s*");
			Matcher matcher = name_pattern.matcher(bytes);
			if (matcher.matches()) {
				String name = matcher.group("value");
				if (name.length() <= 127) {
					PDFName pdf_name = new PDFName();
					// magic numbers - 33 = 0x21 && 126 = 0x7E
					Character lower_limit = (char) 33;
					Character upper_limit = (char) 126;

					Pattern blacklist = Pattern.compile(".*[^\\\\](\\(|\\)|\\[|\\]|\\{|\\}|\\/|%|#).*");
					matcher = blacklist.matcher(name);
					if (matcher.matches())
						throw new InvalidException("Unescaped delimiter characters");
					for (int i = 0; i < name.length(); i++) {
						if (name.charAt(i) < lower_limit || name.charAt(i) > upper_limit)
							throw new InvalidException("Characters out of bounds");
					}
					pdf_name.setValue(name);
					return pdf_name;
				} else {
					throw new InvalidException("Name exceeds limits");
				}
			}
		}
		return null;
	}
}