package ru.raserei.spacegame.Ship;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.raserei.spacegame.Bullet.BulletPool;
import ru.raserei.spacegame.Explosion.ExplosionPool;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.engine.pools.SpritePool;

/**
 * Created by Raserei on 16.02.2018.
 */

public class EnemyShipPool extends SpritePool<EnemyShip> {
    private final Rect worldBounds;
    private final BulletPool bulletPool;
    private final MainShip mainShip;
    private final TextureAtlas atlas;
    private final ExplosionPool explosionPool;
    private final Sound soundBullet;

    public EnemyShipPool(TextureAtlas atlas,Rect worldBounds, BulletPool bulletPool, MainShip mainShip, ExplosionPool explosionPool, Sound soundBullet) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.mainShip = mainShip;
        this.atlas = atlas;
        this.explosionPool = explosionPool;
        this.soundBullet = soundBullet;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(atlas, bulletPool, worldBounds, mainShip, explosionPool, soundBullet);
    }
}
