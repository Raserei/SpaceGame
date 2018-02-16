package ru.raserei.spacegame.Field;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import ru.raserei.spacegame.engine.Sprite;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.engine.math.Rnd;

public class Star extends Sprite {
    private final Vector2 STAR_SPEED;
    private Rect worldBounds;

    public Star(TextureRegion region) {
        super(region);
        setRight(Rnd.nextFloat(-0.5f, 0.5f));
        setTop(Rnd.nextFloat(-0.5f, 0.5f));
        setHeightProportion(Rnd.nextFloat(0.01f,0.03f));
        STAR_SPEED = new Vector2(0,-0.2f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(STAR_SPEED, delta);
        checkAndHandleBounds();
    }

    private void checkAndHandleBounds (){
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }
}
