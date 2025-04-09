package org.aau.homework.assignment_02.philipp.ex1.server;

public enum Action {
    CROSS("\u001B[31m X \u001B[0m"),
    CIRCLE("\u001B[32m O \u001B[0m");

    private final String value;

    Action(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
