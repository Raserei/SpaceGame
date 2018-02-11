package ru.raserei.spacegame.ui.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.engine.ActionListener;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.engine.ui.Button;



public class ExitButton extends Button {
    private static final float BUTTON_HEIGHT = 0.2f;

    public ExitButton(TextureAtlas atlas, ActionListener actionListener){
        super(atlas.findRegion("btExit"), actionListener, BUTTON_HEIGHT);
        setHeightProportion(BUTTON_HEIGHT);
    }

    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom());
        setRight(worldBounds.getRight());
    }

}
