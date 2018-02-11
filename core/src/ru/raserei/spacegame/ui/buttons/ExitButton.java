package ru.raserei.spacegame.ui.buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Created by Raserei on 07.02.2018.
 */

public class ExitButton extends Button {
    public ExitButton(TextureRegion region) {
        super(region);
        setHeightProportion(0.1f);
        setTop(-0.1f);
    }
}
