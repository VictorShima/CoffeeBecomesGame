package com.md.mechevo.game.condition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import com.google.gson.*;

public class ConditionDeserializer implements JsonDeserializer<Condition> {
	/**
	 * Gson invokes this call-back method during deserialization when it encounters a field of the
	 * specified type.
	 * <p>
	 * In the implementation of this call-back method, you should consider invoking
	 * {@link com.google.gson.JsonDeserializationContext#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type)}
	 * method to create objects for any non-trivial field of the returned object. However, you
	 * should never invoke it on the the same type passing {@code json} since that will cause an
	 * infinite loop (Gson will call your call-back method again).
	 * 
	 * @param json The Json data being deserialized
	 * @param typeOfT The type of the Object to deserialize to
	 * @param context
	 * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
	 * @throws com.google.gson.JsonParseException if json is not in the expected format of
	 *         {@code typeofT}
	 */
	@Override
	public Condition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		String name = obj.get("name").getAsString();
		String param = obj.get("param").getAsString();
		try {
			// creates an object from the class name with his empty constructor
			// TODO study any possibility of how to player to here
			Class<?> clazz = Class.forName(Condition.class.getPackage().getName() + "." + name);
			Constructor<?> constructor = clazz.getConstructor();
			Condition condition = (Condition) constructor.newInstance();

			condition.setParam(param);

			return condition;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}
}
