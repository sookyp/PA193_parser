package com.company.parser.primitives;

import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by val on 02/10/16.
 */
public class PDFDictionary extends PDFObject {
	private Map<String, PDFObject> hashtable = new Hashtable<>();
	private PDFName key = null;
	private PDFObject value = null;
	private Boolean isStream = false;
	private String streamData = null;
    private static final int NOT_FOUND = Integer.MAX_VALUE;

    public Map<String, PDFObject> getHashtable() {
		return hashtable;
	}

	public void setHashtable(Map<String, PDFObject> hashtable) {
		this.hashtable = hashtable;
	}

	public void insertPair(String key, PDFObject value) {
		this.hashtable.put(key, value);
	}

	public Collection<PDFObject> retrieveValues() {
		return this.hashtable.values();
	}

	public PDFObject getValue(PDFName key) {
		return this.hashtable.get(key);
	}

	public Boolean isStream() {
		return this.isStream;
	}

	public String getStream() {
		return this.streamData;
	}

	public void setStream(String data) {
		this.isStream = true;
		this.streamData = data;
	}

	public Map<PDFName, PDFObject> parseDictionary(CharSequence bytes, int offset)
			throws IOException, InvalidException {
		if (offset < bytes.length()) {
			bytes.subSequence(offset, bytes.length());
			Pattern dictionary_start_pattern = Pattern.compile("^\\s*<<(?<content>.*)");
			Matcher start_matcher = dictionary_start_pattern.matcher(bytes);

			String level_0 = null;
			String current_level = null;
			String remaining_level = null;
			if (start_matcher.matches()) {

				Map<PDFName, PDFObject> pdf_dictionary = new Hashtable<PDFName, PDFObject>();
				level_0 = start_matcher.group("content");

				// No closing bracket before opening
				Pattern new_dictionary_start_pattern = Pattern.compile("^((?!>>).)*?<<");
				Matcher new_start_matcher = new_dictionary_start_pattern.matcher(level_0);

				Pattern dictionary_end_pattern = Pattern.compile("^((?!<<).)*?>>");
				Matcher end_matcher = dictionary_end_pattern.matcher(level_0);

				Integer level = 0;
				remaining_level = start_matcher.group("content");

				String[] element_list = null;
				String streamData = null;
				PDFObject element = null;
				Integer cycle_counter = 0;

				Pattern stream_start_pattern = Pattern.compile("^\\s*(stream)(.*?)(endstream)\\s*$");
				Matcher stream_matcher = stream_start_pattern.matcher(remaining_level);
				// InputStream stream = null;
				// TODO: handle indirect references
				// TODO: similarly to arrays, if we care about structure
				// TODO: handle null dictionaries << >>
				// element_list[i].trim().matches("^<<\\s+>>")

				while (true) {
					cycle_counter += 1;
					if (new_start_matcher.find()) {

						current_level = new_start_matcher.group(0);
						remaining_level = remaining_level.substring(current_level.length());

						// add to pdf_array to current level
						element_list = current_level.split("\\s+");
						if (element_list.length % 2 == 0)
							// must be even, otherwise the new dictionary would
							// be a key +1 for brackets
							throw new InvalidException("Invalid key");
						for (int i = 0; i < element_list.length; i++) {
							if (element_list[i].trim().isEmpty() || element_list[i].trim().startsWith("<<"))
								continue;
							element = PDFObject.resolveType(element_list[i]);
							// every odd is a key, type PDFName
							if (i % 2 != 0) {
								/*
								 * Cannot happen if
								 * (element_list[i].trim().isEmpty()) throw new
								 * InvalidException("No key");
								 */

								if (element.getClass() != PDFName.class)
									throw new InvalidException("Wrong key type");
								else
									this.key = (PDFName) element;

							} else {
								this.value = null;
								if (element != null)
									this.value = element;
								if (this.key != null && this.value != null) {
									pdf_dictionary.put(this.key, this.value);
								}
							}
						}

						pdf_dictionary.put(this.key, new PDFDictionary());
						// update matchers
						new_start_matcher = new_dictionary_start_pattern.matcher(remaining_level);
						end_matcher = dictionary_end_pattern.matcher(remaining_level);
						level += 1;

					}

					if (end_matcher.find()) {
						current_level = end_matcher.group(0);
						remaining_level = remaining_level.substring(current_level.length());

						// add to pdf_array to current level
						element_list = current_level.split("\\s+");
						if (element_list.length % 2 != 0)
							// must be odd, otherwise the one key would
							// be without a value +1 for brackets
						throw new InvalidException("Invalid key");

						for (int i = 0; i < element_list.length; i++) {
							if (element_list[i].trim().isEmpty() || element_list[i].trim().startsWith(">>"))
								continue;
							element = PDFObject.resolveType(element_list[i]);

							// every odd is a key, type PDFName
							if (i % 2 != 0) {
								/*
								 * Cannot happen if
								 * (element_list[i].trim().isEmpty()) throw new
								 * InvalidException("No key");
								 */

								if (element.getClass() != PDFName.class)
									throw new InvalidException("Wrong key type");
								else
									this.key = (PDFName) element;

							} else {
								if (element != null)
									this.value = element;
								if (this.key != null && this.value != null) {

									System.out.println(this.key);
									System.out.println(this.value);
									System.out.println(pdf_dictionary);

									pdf_dictionary.put(this.key, this.value);
								}
							}
						}

						// check for streams
						stream_matcher = stream_start_pattern.matcher(remaining_level);

						if (stream_matcher.matches()) {
							streamData = stream_matcher.group(0);
							// this.setStream(streamData);
							// TODO: extract stream data in case we need it 
							// ((PDFDictionary) pdf_dictionary).setStream(streamData);
							// remaining_level.substring(streamData.length());
						}

						// update matchers
						new_start_matcher = new_dictionary_start_pattern.matcher(remaining_level);
						end_matcher = dictionary_end_pattern.matcher(remaining_level);

						level -= 1;
					}

					if (level > 20) {
						// 20 - magic number to limit nesting
						throw new InvalidException("Too deep dictionary nesting");
					}
					if (cycle_counter > 32000) {
						throw new InvalidException("Too big dictionary size");
					}
					if (level < 0) {
						break;
					}
				}
				return pdf_dictionary;
			}
		}
		return null;
	}
}