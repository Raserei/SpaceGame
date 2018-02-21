package ru.raserei.spacegame.Ship;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.Explosion.ExplosionPool;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.Bullet.BulletPool;


public class MainShip extends Ship {
    private final static String IMAGE_REGION = "main_ship";
    private final static int IMAGE_COLUMNS=1;
    private final static int IMAGE_ROWS=2;
    private final static int IMAGE_FRAMES=2;
    private final static int HP = 50;

    private final static float SHIP_HEIGHT = 0.15f;
    private final static float BOTTOM_MARGIN = - 0.05f;

    private final int RIGHT_DIRECTION = 1;
    private final int LEFT_DIRECTION = -1;

    private boolean isPressedLeft;
    private boolean isPressedRight;

    private static final float RELOAD_INTERVAL = 0.2f;
    private static final Vector2 V0 = new Vector2(0.5f,0.0f);

    private boolean isAlive = true;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound) {
        super(atlas.findRegion(IMAGE_REGION), IMAGE_ROWS, IMAGE_COLUMNS, IMAGE_FRAMES, bulletPool, explosionPool, shootSound);
        setHeightProportion(SHIP_HEIGHT);
        isPressedLeft = false;
        isPressedRight = false;
        bulletSpeed = new Vector2(0,0.6f);
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletDamage = 20;
        reloadInterval = RELOAD_INTERVAL;
        v0 = V0;
        hp = HP;
    }

    public void restart(){
        hp = getFullHp();
        isAlive = true;
        pos.x = 0.0f;
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
        super.update(delta);
        if (getRight()>worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }

        if (getLeft()<worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public void damage(int damage) {
        super.damage(damage);
        if (hp<=0) {
            boom();
            isAlive = false;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void shoot() {
        if (isAlive) super.shoot();
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getTop() < getBottom()
                || bullet.getBottom() > pos.y
        );
    }

    public int getHp(){
        return hp;
    }

    public int getFullHp(){
        return HP;
    }

    public void heal(int healthPoint){
        hp+=healthPoint;
        if (hp>HP) hp = HP; //мы веселые индусы,
                            // мы похожи на арбузы
    }
}
