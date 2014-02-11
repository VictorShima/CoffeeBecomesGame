package com.md.mechevo.game.condition;

import java.util.UnknownFormatConversionException;

public class UnknownCondition extends UnknownFormatConversionException {
	public UnknownCondition(String conditionName) {
		super("Unknown condition: " + conditionName);
	}
}
