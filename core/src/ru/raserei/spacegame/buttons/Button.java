package ru.raserei.spacegame.buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.raserei.spacegame.engine.Sprite;

/**
 * Created by Raserei on 07.02.2018.
 */

public abstract class Button extends Sprite {
    public Button(TextureRegion region) {
        super(region);
    }
}
