package com.md.mechevo.game.action;

public class InvalidActionParameter extends Error {
    public InvalidActionParameter(String name) {
        super(String.format("Invalid param in %s", name));
    }
}
