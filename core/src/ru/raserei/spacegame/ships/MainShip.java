package ru.raserei.spacegame.ships;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.misc.Bullet;
import ru.raserei.spacegame.misc.BulletPool;


public class MainShip extends Ship {
    private final static int IMAGE_COLUMNS=1;
    private final static int IMAGE_ROWS=2;
    private final static int IMAGE_FRAMES=2;

    private final static float SHIP_HEIGHT = 0.15f;
    private final static float BOTTOM_MARGIN = - 0.05f;

    private final int RIGHT_DIRECTION = 1;
    private final int LEFT_DIRECTION = -1;

    private final Vector2 v0 = new Vector2(0.5f,0.0f);
    private Vector2 velocity = new Vector2();

    private boolean isPressedLeft;
    private boolean isPressedRight;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), IMAGE_ROWS, IMAGE_COLUMNS, IMAGE_FRAMES, bulletPool);
        setHeightProportion(SHIP_HEIGHT);
        isPressedLeft = false;
        isPressedRight = false;
        bulletSpeed = new Vector2(0,0.6f);
        bulletRegion = atlas.findRegion("bulletMainShip");
    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom()-BOTTOM_MARGIN);
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = true;
                move(LEFT_DIRECTION);
                break;

            case Input.Keys.D:
            case Input.Keys.RIGHT:
                move(RIGHT_DIRECTION);
                isPressedRight = true;
                break;
            case Input.Keys.UP:
            case Input.Keys.W:
                shoot();
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = false;
                if (isPressedRight) move(RIGHT_DIRECTION);
                else stop();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = false;
                if (isPressedLeft) move(LEFT_DIRECTION);
                else stop();
                break;
        }
    }
    @Override
    public void touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
    }

    private void move (int direction){
        velocity.set(v0).scl(direction);
    }

    private void stop (){
        velocity.setZero();
    }

    @Override
    public void update(float delta){
        pos.mulAdd(velocity,delta);
        if (getRight()>worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }

        if (getLeft()<worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }
}
