package ru.raserei.spacegame.ui.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.raserei.spacegame.engine.ActionListener;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.engine.ui.Button;

public class PlayButton extends Button {
    private final static float BUTTON_HEIGHT = 0.25f;

    public PlayButton(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("btPlay"), actionListener, BUTTON_HEIGHT);
    }

    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom());
        setLeft(worldBounds.getLeft());
    }
}
