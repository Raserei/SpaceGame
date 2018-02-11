package ru.raserei.spacegame.engine.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.engine.ActionListener;
import ru.raserei.spacegame.engine.Sprite;

public abstract class Button extends Sprite {

    private static final float pressScale = 0.8f;
    private boolean pressed;
    private int pointer;
    private ActionListener actionListener;

    public Button(TextureRegion region, ActionListener actionListener, float buttonHeight){
        super(region);
        this.actionListener = actionListener;
        pressed = false;
        setHeightProportion(buttonHeight);
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        if (pressed || !isMe(touch)) {
            return;
        }
        this.pointer = pointer;
        scale = pressScale;
        pressed = true;
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        if (this.pointer != pointer || !pressed) {
            return;
        }
        if (isMe(touch)) {
            actionListener.actionPerformed(this);
        }
        pressed = false;
        scale = 1f;
    }
}
