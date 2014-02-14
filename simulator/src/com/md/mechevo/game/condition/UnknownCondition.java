package com.md.mechevo.game.condition;

public class UnknownCondition extends Error {
	public UnknownCondition(String conditionName) {
		super("Unknown condition: " + conditionName);
	}
}
