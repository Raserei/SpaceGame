package ru.raserei.spacegame.Ship;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.Bullet.BulletPool;
import ru.raserei.spacegame.Explosion.ExplosionPool;
import ru.raserei.spacegame.engine.math.Rect;


/**
 * Created by Raserei on 16.02.2018.
 */

public class EnemyShip extends Ship {

    private TextureAtlas atlas;

    private static final int TYPE_AMOUNT = 2;


    //todo: подобрать hp и урон
    private static final String BULLET_TEXTURE = new String ("bulletEnemy");
    private static final Vector2[] BULLET_SPEEDS = new Vector2[]{new Vector2(0,-0.4f),new Vector2(0,-0.4f),new Vector2(0,-0.3f)};
    private static final float[] BULLET_RELOAD_INTERVAL = new float[]{1f, 1.2f, 1.3f};
    private static final Vector2[] SHIP_SPEEDS = new Vector2[]{new Vector2(0,-0.2f),new Vector2(0,-0.15f),new Vector2(0,-0.1f)};
    private static final Vector2 SHIP_APPEARANCE_SPEED = new Vector2(0,-0.8f);
    private static final float[] SHIP_HEIGHT_PROPORTION = new float[]{0.1f,0.15f,0.2f};
    private static final int[] SHIP_HP = new int[]{10,20,30};
    private static final int[] BULLET_DAMAGE = new int[]{5,10,15};
  //  private int shipType;


    private MainShip mainShip;

    @Override
    public void update(float delta) {
        pos.mulAdd(velocity,delta);
        super.update(delta);
        if(getBottom()<worldBounds.getBottom()){
            mainShip.damage(getBulletDamage());
            setDestroyed(true);
            boom();
        }
    }

    public EnemyShip(TextureAtlas atlas, BulletPool bulletPool, Rect worldBounds, MainShip mainShip, ExplosionPool explosionPool, Sound soundBullet) {
        super(bulletPool,explosionPool, soundBullet);
        this.atlas = atlas;
        this.worldBounds = worldBounds;
        bulletRegion = atlas.findRegion(BULLET_TEXTURE);
        this.mainShip = mainShip;
    }

    public void set(int shipType, TextureRegion[] texture){
   //     this.shipType = shipType;
        if (shipType<0||shipType>TYPE_AMOUNT) throw new RuntimeException("Attemp to set invalid ship type");
        velocity = SHIP_SPEEDS[shipType];
        bulletSpeed = BULLET_SPEEDS[shipType];
        this.regions= texture;
        setHeightProportion(SHIP_HEIGHT_PROPORTION[shipType]);
        System.out.println(worldBounds);
        pos.set(0f,0.8f);
        reloadTimer = BULLET_RELOAD_INTERVAL[shipType];
        reloadInterval = BULLET_RELOAD_INTERVAL[shipType];
        hp = SHIP_HP[shipType];
        bulletDamage = BULLET_DAMAGE[shipType];
    }

    @Override
    public void damage(int damage) {
        super.damage(damage);
        if (hp<=0) {
            setDestroyed(true);
            boom();
        }
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }
}
