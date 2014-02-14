package com.md.mechevo.game.condition;

public class InvalidConditionParameter extends Error {
    public InvalidConditionParameter(String name) {
        super(String.format("Invalid param in %s", name));
    }
}
