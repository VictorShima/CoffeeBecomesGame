package com.md.mechevo.game.condition;

public class InvalidConditionParameter extends Error {

    public InvalidConditionParameter(String name, String param) {
        super(String.format("Invalid parameter %s in %s", param, name));
    }
}
