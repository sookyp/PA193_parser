package com.company.parser.primitives;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by val on 02/10/16.
 */
public class PDFString extends PDFObject {
	private String value = null;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	@Override
	public PDFString parse(CharSequence bytes, int offset) throws IOException, InvalidException {
		if (offset < bytes.length()) {
			bytes.subSequence(offset, bytes.length());
			// Format: from first ( to first non-escaped )
			Pattern literal_string_pattern = Pattern.compile("(?i)^\\s*\\((?<literalvalue>.*?[^\\\\])\\).*?");
			// Format: from first < but not << to first > but not >>, while
			// accept only hexadecimal digits
			Pattern hex_string_pattern = Pattern.compile("(?i)^\\s*<(?<hexvalue>[^<][0-9a-fA-F]*)>[^>]\\s*$");

			Matcher matcher_literal = literal_string_pattern.matcher(bytes);
			Matcher matcher_hex = hex_string_pattern.matcher(bytes);

			PDFString pdf_string = new PDFString();
			// possible TODO: Implement String decoding if necessary
			if (matcher_literal.matches()) {
				String literal_value = matcher_literal.group("literalvalue");

				Pattern blacklist = Pattern.compile(".*[^\\\\](\\(|\\)|\\[|\\]|\\{|\\}|\\/|%|#).*");
				Matcher blacklist_matcher = blacklist.matcher(literal_value);

				if (blacklist_matcher.matches())
					throw new InvalidException("Unescaped delimiter characters");

				// possible TODO: check for hexadecimal and octal characters if
				// necessary
				literal_value.replaceAll("\\s", "#20");

				if (literal_value.length() > Integer.MAX_VALUE / 2)
					throw new InvalidException("Invalid string");

				pdf_string.setValue(literal_value);
			} else if (matcher_hex.matches()) {
				String hex_value = matcher_hex.group("hexvalue");
				// check for length && check for byte parity - pair of bytes is
				// one character
				if ((hex_value.length() > Integer.MAX_VALUE / 2) || (hex_value.length() % 2 != 0))
					throw new InvalidException("Invalid hex string");
				pdf_string.setValue(hex_value);
			}
			return pdf_string;
		}
		return null;
	}
}