package ru.raserei.spacegame.ships;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.engine.Sprite;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.misc.Bullet;
import ru.raserei.spacegame.misc.BulletPool;

/**
 * Created by Raserei on 12.02.2018.
 */

public abstract class Ship extends Sprite {
    private BulletPool bulletPool;
    protected Rect worldBounds;
    protected Vector2 bulletSpeed;
    protected TextureRegion bulletRegion;


    public Ship(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool) {
        super(region, rows, cols, frames);
        this.bulletPool=bulletPool;
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.getReady(pos,worldBounds,bulletSpeed,this, bulletRegion);
    }

}
