package com.dewei.chat.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JpaObjectMapper extends ObjectMapper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaObjectMapper() {
		super();
		this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
}