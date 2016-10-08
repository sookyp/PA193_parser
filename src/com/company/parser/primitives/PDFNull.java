package com.company.parser.primitives;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by val on 02/10/16.
 */
public class PDFNull extends PDFObject {
	@Override
	public PDFNull parse(CharSequence bytes, int offset) throws IOException {
		if (offset < bytes.length()) {
			bytes.subSequence(offset, bytes.length());
			if (Pattern.matches("(?i)^\\s*(null)\\s*$", bytes)) {
				return new PDFNull();
			}
		}
		return null;
	}
}
