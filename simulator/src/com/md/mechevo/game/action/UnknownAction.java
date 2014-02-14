package com.md.mechevo.game.action;

public class UnknownAction extends Error {
	public UnknownAction(String actionName) {
		super("Unknown Action: " + actionName);
	}
}
