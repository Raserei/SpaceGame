package ru.raserei.spacegame.Bullet;

import ru.raserei.spacegame.engine.pools.SpritePool;

/**
 * Created by Raserei on 13.02.2018.
 */

public class BulletPool extends SpritePool<Bullet>{

    @Override
    protected Bullet newObject(){
        return new Bullet();
    }
}
