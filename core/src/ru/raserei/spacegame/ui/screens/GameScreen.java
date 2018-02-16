package ru.raserei.spacegame.ui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.raserei.spacegame.Bullet.Bullet;
import ru.raserei.spacegame.Explosion.ExplosionPool;
import ru.raserei.spacegame.Field.Background;
import ru.raserei.spacegame.Bullet.BulletPool;
import ru.raserei.spacegame.Ship.EnemyEmitter;
import ru.raserei.spacegame.Ship.EnemyShip;
import ru.raserei.spacegame.Ship.EnemyShipPool;
import ru.raserei.spacegame.Ship.MainShip;
import ru.raserei.spacegame.Field.Star;
import ru.raserei.spacegame.engine.Base2dScreen;
import ru.raserei.spacegame.engine.Sprite;
import ru.raserei.spacegame.engine.math.Rect;

public class GameScreen extends Base2dScreen {

    private Texture backgroundTexture;
    private Background background;

    private Star[] stars;

    private TextureAtlas atlas;
    private MainShip mainShip;
    private BulletPool bulletPool;

    private EnemyShipPool enemyShipPool;
    private EnemyEmitter enemyEmitter;
    private ExplosionPool explosionPool;

    private Sprite gameOver;

    private EnemyShip enemyShip;

    //references
    private static final String BG_TEXTURE_NAME = "textures/bg.png";
    private static final String  ATLAS_NAME = "textures/mainAtlas.tpack";
    private static final String  STAR_NAME = "star";

    private Sound soundExplosion= Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    private Sound soundLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    private Sound soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    private Music music  = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));



    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() { //дилемма - один раз распарсить весь атлас в регионы тут (возможно, это более оптимально) или пусть каждый объект сам отвечает за свой регион
        super.show();
        atlas = new TextureAtlas(ATLAS_NAME);
        backgroundTexture = new Texture(BG_TEXTURE_NAME);
        background = new Background(new TextureRegion(backgroundTexture));

        music.setLooping(true);
        music.play();

        stars = new Star[64];
        for (int i = 0; i < 64; i++) {
            stars[i] = new Star(new TextureRegion(atlas.findRegion(STAR_NAME)));
        }


        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, soundExplosion);
        mainShip = new MainShip(atlas, bulletPool,explosionPool, soundLaser);
        enemyShipPool = new EnemyShipPool(atlas,worldBounds,bulletPool,mainShip,explosionPool, soundBullet);
        enemyEmitter = new EnemyEmitter(worldBounds,enemyShipPool,atlas);
        gameOver = new Sprite(atlas.findRegion("message_game_over")); //todo: вынести в отдельный класс к UI (message?)
        gameOver.setHeightProportion(0.03f);

    }
    public void update(float delta){
        for (Star s:stars) {
            s.update(delta);
        }
        if (mainShip.isAlive()) {
            mainShip.update(delta);
            bulletPool.update(delta);
            enemyEmitter.emitEnemy(delta);
            enemyShipPool.update(delta);
            explosionPool.update(delta);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        bulletPool.freeAllDestroyedActiveObjects();
        enemyShipPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
        checkCollisions();
        update(delta);
        Gdx.gl.glClearColor(0.7f, 0.3f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (mainShip.isAlive()){ //todo: добавить драматическую МХАТовскую паузу перед отрисовкой геймовера
            mainShip.draw(batch);
            bulletPool.drawActiveObjects(batch);
            enemyShipPool.drawActiveObjects(batch);
            explosionPool.drawActiveObjects(batch);
        }
        else gameOver.draw(batch); //todo: dispose пулов объектов, чтоб не жрали ресурс, пока игра висит в геймовере. Переход на другой скрин?
        batch.end();
    }

    public void checkCollisions() {
        // столкновение кораблей
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemy : enemyShipList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.setDestroyed(true);
                enemy.boom();
                mainShip.damage(enemy.getBulletDamage());
                return;
            }
        }

        // нанесение урона вражескому кораблю
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (EnemyShip enemy : enemyShipList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.setDestroyed(true);
                }
            }
        }

        //нанесение урона главному кораблю
        for (Bullet bullet: bulletList) {
            if (bullet.isDestroyed()||bullet.getOwner()== mainShip){
                continue;
            }
            if (mainShip.isBulletCollision(bullet)){
                mainShip.damage(bullet.getDamage());
                bullet.setDestroyed(true);
            }
        }
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star s:stars) {
            s.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        bulletPool.resize(worldBounds);
        enemyShipPool.resize(worldBounds);
        explosionPool.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        explosionPool.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
       mainShip.keyUp(keycode);
       return false;
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch,pointer);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch,pointer);
    }
}
