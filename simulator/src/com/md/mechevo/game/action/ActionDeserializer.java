package com.md.mechevo.game.action;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ActionDeserializer implements JsonDeserializer<Action> {
	@Override
	public Action deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
		// TODO support all actions
		return new AttackAction(null);
	}
}
