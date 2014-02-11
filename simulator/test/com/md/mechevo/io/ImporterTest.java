package com.md.mechevo.io;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.md.mechevo.game.action.Action;
import com.md.mechevo.game.action.ActionDeserializer;
import com.md.mechevo.game.action.AttackAction;
import com.md.mechevo.game.condition.Condition;
import com.md.mechevo.game.condition.ConditionDeserializer;
import com.md.mechevo.game.condition.TrueCondition;

public class ImporterTest {

	@Test
	public void testActionDeserialization() {
		String json = "{\"name\":\"AttackAction\",\"param\":\"banana\"}";
		Gson gson =
						new GsonBuilder().registerTypeAdapter(Action.class,
										new ActionDeserializer()).create();
		Action action = gson.fromJson(json, Action.class);
		Assert.assertTrue(action instanceof AttackAction);
	}

	@Test
	public void testConditionDeserialization() {
		String json = "{\"name\":\"TrueCondition\",\"param\":\"banana\"}";
		Gson gson =
						new GsonBuilder().registerTypeAdapter(Condition.class,
										new ConditionDeserializer()).create();
		Condition condition = gson.fromJson(json, Condition.class);
		Assert.assertTrue(condition instanceof TrueCondition);
	}
}
