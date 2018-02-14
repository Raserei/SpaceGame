package ru.raserei.spacegame.misc;

import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.engine.Sprite;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.engine.pools.SpritePool;
import ru.raserei.spacegame.ships.Ship;

/**
 * Created by Raserei on 13.02.2018.
 */

public class BulletPool extends SpritePool<Bullet>{

    @Override
    protected Bullet newObject(){
        return new Bullet();
    }
}
