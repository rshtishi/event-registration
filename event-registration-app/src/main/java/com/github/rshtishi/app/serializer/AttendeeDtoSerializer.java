package com.github.rshtishi.app.serializer;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rshtishi.app.dto.AttendeeDto;

public class AttendeeDtoSerializer implements Serializer<AttendeeDto> {

	@Override
	public byte[] serialize(String topic, AttendeeDto data) {
		byte[] value = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			value = objectMapper.writeValueAsString(data).getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}
