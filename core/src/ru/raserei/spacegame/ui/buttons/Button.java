package ru.raserei.spacegame.ui.buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.engine.Sprite;
import ru.raserei.spacegame.engine.math.Rect;

/**
 * Created by Raserei on 07.02.2018.
 */

public abstract class Button extends Sprite {

    private static final Vector2 SCALE_COEFFICIENT = new Vector2(-0.02f,-0.02f);
    private final Vector2 initPos;

    public Button(TextureRegion region) {
        super(region);
        initPos = pos;
    }
}
