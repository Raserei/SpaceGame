package ru.raserei.spacegame.Bullet;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.engine.Sprite;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.Ship.Ship;


public class Bullet extends Sprite {
    private Vector2 speed = new Vector2(0,0);
    private Rect worldBounds;
    private Ship owner_ship; //wordplay haha
    private int damage;

    public int getDamage() {
        return damage;
    }

    public Bullet(){
        this.regions = new TextureRegion[1];
    }

    public void update(float delta){
        pos.mulAdd(speed,delta);
        if (isOutside(worldBounds)) setDestroyed(true);
    }

    public void resize(Rect worldBounds){
        this.worldBounds = worldBounds;
    }

    public void getReady(Vector2 pos0, Rect worldBounds, Vector2 speed, Ship owner, TextureRegion region){
        this.regions[0] = region;
        this.pos.set(pos0);
        this.worldBounds = worldBounds;
        this.speed.set(speed);
        setHeightProportion(0.02f);
        owner_ship = owner;
        damage = owner.getBulletDamage();
    }

    public Ship getOwner() {
        return owner_ship;
    }
}
