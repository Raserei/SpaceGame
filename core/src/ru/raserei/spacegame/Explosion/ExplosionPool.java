package ru.raserei.spacegame.Explosion;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.raserei.spacegame.engine.pools.SpritePool;

/**
 * Created by Raserei on 16.02.2018.
 */

public class ExplosionPool extends SpritePool<Explosion> {
    private final TextureRegion explosionRegion;
    private final Sound explosionSound;
 //   private Sound sound;

    public ExplosionPool(TextureAtlas atlas, Sound explosionSound) {
        explosionRegion = atlas.findRegion("explosion");
        this.explosionSound = explosionSound;
 //       this.sound = sound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(explosionRegion, 9, 9, 7, explosionSound);
    }
}
