package ru.raserei.spacegame.ui.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.raserei.spacegame.engine.ActionListener;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.engine.ui.Button;

/**
 * Created by Raserei on 22.02.2018.
 */

public class StartNewGameButton extends Button {
    private static float BUTTON_HEIGHT = 0.02f;

    public StartNewGameButton(TextureAtlas atlas,ActionListener actionListener) {
        super(atlas.findRegion("button_new_game"), actionListener, BUTTON_HEIGHT);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom()+0.1f);
    }
}
