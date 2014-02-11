package com.md.mechevo.game.condition;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.md.mechevo.game.action.Action;
import com.md.mechevo.game.action.AttackAction;

public class ConditionDeserializer implements JsonDeserializer<Action> {
	@Override
	public Action deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
		// TODO support all actions
		return new AttackAction(null);
	}
}
