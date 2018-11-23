package org.eep.chuanglan;

import java.lang.reflect.Type;

import org.eep.chuanglan.model.SmsState;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SmsStateSerializer implements JsonSerializer<SmsState>, JsonDeserializer<SmsState> {

	@Override
	public SmsState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return null == json ? null : SmsState.match(json.getAsString());
	}

	@Override
	public JsonElement serialize(SmsState src, Type typeOfSrc, JsonSerializationContext context) {
		return null == src ? null : new JsonPrimitive(src.name());
	}
}
