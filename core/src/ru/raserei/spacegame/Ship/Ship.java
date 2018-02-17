package ru.raserei.spacegame.Ship;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.Explosion.Explosion;
import ru.raserei.spacegame.Explosion.ExplosionPool;
import ru.raserei.spacegame.engine.Sprite;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.Bullet.Bullet;
import ru.raserei.spacegame.Bullet.BulletPool;

/**
 * Created by Raserei on 12.02.2018.
 */

public abstract class Ship extends Sprite {
    private Sound shootSound;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected Rect worldBounds;
    protected Vector2 bulletSpeed;
    protected TextureRegion bulletRegion;

    protected float reloadTimer;
    protected float reloadInterval;

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.3f;
    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    protected int hp;
    protected int bulletDamage;

    protected Vector2 v0;
    protected Vector2 velocity = new Vector2();

    public void update(float delta){
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    public void damage(int damage) {
        frame = 1;
        hp-=damage;
        if (hp<0) hp = 0;
        damageAnimateTimer = 0;
    }


    public Ship(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound) {
        super(region, rows, cols, frames);
        this.bulletPool=bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
    }


    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound){
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.getReady(pos,worldBounds,bulletSpeed,this, bulletRegion);
        shootSound.play();
    }

    public void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public int getBulletDamage() {
        return bulletDamage;
    }


}
