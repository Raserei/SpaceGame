package ru.raserei.spacegame.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.engine.Sprite;
import ru.raserei.spacegame.engine.math.Rect;

/**
 * Created by Raserei on 18.02.2018.
 */

public class GameOverMessage extends Sprite {
    private TextureRegion region;

    private static float appearInterval = 0.3f;
    private Vector2 finalPosition;

    public GameOverMessage(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        finalPosition = new Vector2(0.0f,0f);
        pos.set(0.0f,0.5f+getHalfHeight());
        setHeightProportion(0.04f);
    }

    public void update(float delta){
        if (pos.y>finalPosition.y){
            pos.y-=appearInterval*delta;
        }
    }

    public void restart(){
        pos.set(0.0f,0.5f+getHalfHeight());
    }
}
