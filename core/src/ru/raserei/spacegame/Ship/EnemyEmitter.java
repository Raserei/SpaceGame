package ru.raserei.spacegame.Ship;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.raserei.spacegame.Bullet.BulletPool;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.engine.math.Rnd;
import ru.raserei.spacegame.engine.utils.Regions;

/**
 * Created by Raserei on 16.02.2018.
 */

public class EnemyEmitter {
    private Rect worldBounds;
    private final EnemyShipPool enemyShipPool;
    private TextureAtlas atlas;

    private float generateTimer;
    private float generateInterval = 4f;

    private int level;

    private final TextureRegion[][] SHIP_REGIONS = new TextureRegion[3][];

    public EnemyEmitter(Rect worldBounds, EnemyShipPool enemyShipPool, TextureAtlas atlas) {
        this.worldBounds = worldBounds;
        this.enemyShipPool = enemyShipPool;
        this.atlas = atlas;
        this.SHIP_REGIONS[0] = Regions.split(atlas.findRegion("enemy0"),1,2,2);
        this.SHIP_REGIONS[1] = Regions.split(atlas.findRegion("enemy1"),1,2,2);
        this.SHIP_REGIONS[2] = Regions.split(atlas.findRegion("enemy2"),1,2,2);
        level = 1;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public void emitEnemy(float delta) {
        generateTimer += delta;
        if (generateInterval <= generateTimer) {
            generateTimer = 0f;
            EnemyShip enemy = enemyShipPool.obtain();

            float type = (float) Math.random();
            int shipType = 0;
            if (type<0.7f) shipType = 0;
            else if (type<0.75f) shipType = 1;
            else shipType = 2;
            enemy.set(shipType,SHIP_REGIONS[shipType], level); //todo: вынести типы в какой-нибудь enum и тащить их из него

            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
