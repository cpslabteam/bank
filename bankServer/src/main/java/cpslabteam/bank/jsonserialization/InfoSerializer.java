package cpslabteam.bank.jsonserialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

public class InfoSerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonGenerationException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writerWithView(JsonViews.Info.class).writeValue(jgen, value);
	}

}
