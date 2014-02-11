package com.md.mechevo.game.action;

import java.util.UnknownFormatConversionException;

public class UnknownAction extends UnknownFormatConversionException {
	public UnknownAction(String actionName) {
		super("Unknown Action: " + actionName);
	}
}
