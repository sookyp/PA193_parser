package com.company.parser.primitives;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by val on 02/10/16.
 */
public class PDFArray extends PDFObject {
	private List<PDFObject> array = new ArrayList<PDFObject>();

	public PDFObject getElement(Integer index) {
		if (this.array.size() > index)
			return this.array.get(index);
		return null;
	}

	public void setElement(PDFObject element, Integer index) {
		// TODO: check bounds
		this.array.set(index, element);
	}

	public void appendElement(PDFObject element) {
		// TODO: check bounds
		this.array.add(element);
	}

	static public List<String> parseIndirectObjectsFromArray(CharSequence bytes) {
		if(bytes.length() < 2) {
			return null;
		}

		CharSequence prefix = bytes.subSequence(0, 1);
		CharSequence suffix = bytes.subSequence(bytes.length() - 1, bytes.length());

		if(prefix.equals("[") && suffix.equals("]")) {
			String regex = "(\\d+\\s\\d\\sR)";

			Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
			Matcher matcher = pattern.matcher(bytes);

			List<String> returnList = new ArrayList<>();
			while (matcher.find()) {
				returnList.add(matcher.group());
			}
			return returnList;
		}

		return null;
	}

	public List<PDFObject> parseArray(CharSequence bytes, int offset) throws IOException, InvalidException {
		if (offset < bytes.length()) {
			bytes.subSequence(offset, bytes.length());
			Pattern array_start_pattern = Pattern.compile("^\\s*\\[(?<content>.*)");
			Matcher start_matcher = array_start_pattern.matcher(bytes);

			String level_0 = null;
			String current_level = null;
			String remaining_level = null;
			if (start_matcher.matches()) {
				List<PDFObject> pdf_array = new ArrayList<PDFObject>();
				// insert level 0
				// pdf_array.add(new PDFArray());
				level_0 = start_matcher.group("content");
				remaining_level = start_matcher.group("content");
				// No closing bracket before opening
				Pattern new_array_start_pattern = Pattern.compile("^\\s*(?<level>[^\\]]*?)\\[");
				Matcher new_start_matcher = new_array_start_pattern.matcher(level_0);

				Pattern array_end_pattern = Pattern.compile("^\\s*(?<endlevel>[^\\[]*?)\\]");
				Matcher end_matcher = array_end_pattern.matcher(level_0);

				Integer level = 0;

				String[] element_list = null;
				PDFObject element = null;
				Integer cycle_counter = 0;
				// InputStream stream = null;
				// TODO: handle indirect references
				while (true) {
					cycle_counter += 1;
					if (new_start_matcher.find()) {

						current_level = new_start_matcher.group(0);
						remaining_level = remaining_level.substring(current_level.length());

						// add to pdf_array to current level
						element_list = current_level.split("\\s+");
						for (int i = 0; i < element_list.length; i++) {
							// stream = new
							// ByteArrayInputStream(current_level.getBytes(Charset.forName("UTF-8")));
							if (element_list[i].trim().isEmpty() || element_list[i].trim().startsWith("["))
								continue;
							element = PDFObject.resolveType(element_list[i]);

							if (element != null) {
								pdf_array.add(element);
								// pdf_array.get(level).appendElement(element);

							}
						}
						// TODO: if we want the actual structure not just the
						// contents then implement the nested walk
						// create new array to next level
						// pdf_array.add(new PDFArray());

						// update matchers
						new_start_matcher = new_array_start_pattern.matcher(remaining_level);
						end_matcher = array_end_pattern.matcher(remaining_level);
						level += 1;

					}
					if (end_matcher.find()) {

						current_level = end_matcher.group(0);
						remaining_level = remaining_level.substring(current_level.length());

						// add to pdf_array to current level
						element_list = current_level.split("\\s+");
						for (int i = 0; i < element_list.length; i++) {
							// stream = new
							// ByteArrayInputStream(current_level.getBytes(Charset.forName("UTF-8")));
							if (element_list[i].trim().isEmpty() || element_list[i].trim().startsWith("]"))
								continue;
							element = PDFObject.resolveType(element_list[i]);

							if (element != null) {
								pdf_array.add(element);
								// pdf_array.get(level).appendElement(element);
							}
						}
						// update matchers
						new_start_matcher = new_array_start_pattern.matcher(remaining_level);
						end_matcher = array_end_pattern.matcher(remaining_level);
						level -= 1;

					}
					if (level > 20) {
						// 20 - magic number to limit nesting
						throw new InvalidException("Too deep array nesting");
					}
					if (cycle_counter > 32000) {
						// 32000 - magic number to limit abuse of
						// [[][][][][][][][]...[][]]
						// also for missing closing brackets
						throw new InvalidException("Invalid array");
					}
					if (level < 0) {
						break;
					}
				}
				return pdf_array;
			}
		}
		return null;
	}
}