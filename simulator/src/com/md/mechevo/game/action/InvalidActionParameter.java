package com.md.mechevo.game.action;

public class InvalidActionParameter extends Error {
    public InvalidActionParameter(String name, String param) {
        super(String.format("Invalid parameter %s in %s", param, name));
    }
}
