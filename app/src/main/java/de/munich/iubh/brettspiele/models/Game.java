package de.munich.iubh.brettspiele.models;

public class Game {
    private int icon;
    private Class game;

    public Game(int icon, Class game) {
        this.icon = icon;
        this.game = game;
    }

    public int getIcon() {
        return icon;
    }

    public Class getGame() {
        return game;
    }
}
