package com.md.mechevo.io;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.md.mechevo.game.action.Action;
import com.md.mechevo.game.action.ActionDeserializer;
import com.md.mechevo.game.action.AttackAction;

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
}
