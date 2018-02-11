package ru.raserei.spacegame.ui.buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.ui.screens.MenuScreen;

/**
 * Created by Raserei on 07.02.2018.
 */

public class PlayButton extends Button {
    public PlayButton(TextureRegion region) {
        super(region);
        setHeightProportion(0.1f);
        setTop(0.2f);
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {

    }
}
