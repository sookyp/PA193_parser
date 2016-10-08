package com.company.parser.primitives;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFObject {
	private Integer objectId = null;
	private Integer generationNumber = null;
	private Boolean indirectObject = false;

	public class InvalidException extends Exception {

		private static final long serialVersionUID = 1L;

		public InvalidException(String message) {
			super(message);
		}

	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		if (objectId > 0)
			this.objectId = objectId;
	}

	public Integer getGenerationNumber() {
		return generationNumber;
	}

	public void setGenerationNumber(Integer generationNumber) {
		if (generationNumber >= 0)
			this.generationNumber = generationNumber;
	}

	public Boolean getIndirectObject() {
		return indirectObject;
	}

	public void setIndirectObject(Boolean indirectObject) {
		this.indirectObject = indirectObject;
	}

	/**
	 * @param bytes
	 *            - pdf document represented as a byte stream
	 * @param offset
	 *            - offset to the object from the beginning of the file
	 * @return - if parsing is valid, then newly created object; else null
	 * @throws IOException
	 *             - InputStream.class.skip() in case the stream does not
	 *             support seek
	 * @throws InvalidException
	 */
	public PDFObject parse(CharSequence bytes, int offset) throws IOException, InvalidException {
		// Skip the leading bytes to the desired object
		if (offset < bytes.length()) {
			bytes.subSequence(offset, bytes.length());
			// Format: ^{*}whitespace {65535}digit {1}whitespace {65535}digit
			// {1}whitespace {1}obj {*} {1}endobj$
			// TODO: tweak the regex in case we need it
			Pattern object_pattern = Pattern.compile(
					"(?i)^\\s*(?<objectId>\\d{1,8})\\s(?<generationNumber>\\d{1,8})(?<indirectObjectStart>\\bobj\\b)?(?:.*)(?<indirectObjectEnd>\\bendobj\\b)?\\s*");
			Matcher matcher = object_pattern.matcher(bytes);
			String obj = null;
			String endobj = null;

			if (matcher.matches()) {
				PDFObject pdf_object = new PDFObject();
				pdf_object.setObjectId(Integer.valueOf(matcher.group("objectId")));
				pdf_object.setGenerationNumber(Integer.valueOf(matcher.group("generationNumber")));
				obj = matcher.group("indirectObjectStart");
				endobj = matcher.group("indirectObjectEnd");
				if ((obj != null && !obj.trim().isEmpty()) && (endobj != null && !endobj.trim().isEmpty()))
					// the current regular expression does not match ending - we probably
					// won't use it anyway
					pdf_object.setIndirectObject(true);
				return pdf_object;
			}
		}
		return null;
	}

	public static PDFObject resolveType(String element) {
		element.trim();
		System.out.println(element);
		// InputStream stream = new
		// ByteArrayInputStream(element.getBytes(Charset.forName("UTF-8")));
		// TODO: check for indirect objects before number checking
		// TODO: ensure return values are correct
		if (element.startsWith("<<")) {
			// dictionary or stream
			PDFDictionary pdf_dictionary = new PDFDictionary();
			try {
				pdf_dictionary.parse(element, 0);
				return pdf_dictionary;
			} catch (IOException | InvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (element.startsWith("(") || (element.startsWith("<"))) {
			// string
			PDFString pdf_string = new PDFString();
			try {
				pdf_string.parse(element, 0);
				return pdf_string;
			} catch (IOException | InvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (element.startsWith("[")) {
			// array
			PDFArray pdf_array = new PDFArray();
			try {
				pdf_array.parseArray(element, 0);
				return pdf_array;
			} catch (IOException | InvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (element.startsWith("/")) {
			// name
			PDFName pdf_name = new PDFName();
			try {
				pdf_name.parse(element, 0);
				return pdf_name;
			} catch (IOException | InvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (element.startsWith("t") || element.startsWith("f") || element.startsWith("T")
				|| element.startsWith("F")) {
			// boolean
			PDFBoolean pdf_boolean = new PDFBoolean();
			try {
				pdf_boolean.parse(element, 0);
				return pdf_boolean;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (element.startsWith("n") || element.startsWith("N")) {
			// null
			PDFNull pdf_null = new PDFNull();
			try {
				pdf_null.parse(element, 0);
				return pdf_null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (Character.isDigit(element.charAt(0)) || element.startsWith("+") || element.startsWith("-")
				|| element.startsWith(".")) {
			// number
			PDFNumber pdf_number = new PDFNumber();
			try {
				pdf_number.parse(element, 0);
				return pdf_number;
			} catch (IOException | InvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// ?
			return null;
		}
		return null;
	}

}
