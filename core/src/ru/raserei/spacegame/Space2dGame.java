package ru.raserei.spacegame;

import com.badlogic.gdx.Game;

import ru.raserei.spacegame.ui.screens.MenuScreen;

public class Space2dGame extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
