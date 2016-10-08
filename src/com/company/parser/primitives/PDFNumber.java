package com.company.parser.primitives;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by val on 02/10/16.
 */
public class PDFNumber extends PDFObject {
	private String value = null;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		// Format: +/- digit+ .? digit? | +/- . digit+
		if (value.matches("([+-]?(\\d+)[.]?(\\d*)|[+-]?[.](\\d+))")) {
			// only write values if valid format, (possible TODO) check
			// isInteger && isFloat if necessary
			this.value = value;
		}
	}

	@Override
	public PDFNumber parse(CharSequence element, int offset) throws IOException, InvalidException {
		if (offset < element.length()) {
			element.subSequence(offset, element.length());
			// invalid formats are ignored in setValue
			Pattern number_pattern = Pattern.compile("(?i)^\\s*(?<value>[+-]?[.]?\\d*[.]?\\d*)\\s*$");
			Matcher matcher = number_pattern.matcher(element);
			if (matcher.matches()) {
				String value = matcher.group("value");
				// drop Strings with length bigger than 2 billion = number of
				// digits
				if (value.length() > Integer.MAX_VALUE / 2)
					throw new InvalidException("Too big number");
				PDFNumber pdf_number = new PDFNumber();
				pdf_number.setValue(value);
				return pdf_number;
			}
		}
		return null;
	}
}