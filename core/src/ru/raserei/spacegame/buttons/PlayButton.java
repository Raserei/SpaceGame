package ru.raserei.spacegame.buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.raserei.spacegame.engine.Sprite;
import ru.raserei.spacegame.engine.math.Rect;

/**
 * Created by Raserei on 07.02.2018.
 */

public class PlayButton extends Button {
    public PlayButton(TextureRegion region) {
        super(region);
        setHeightProportion(0.1f);
        setTop(0.2f);
    }


}
