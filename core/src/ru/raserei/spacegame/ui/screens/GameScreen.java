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
import com.badlogic.gdx.utils.Align;

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
import ru.raserei.spacegame.engine.ActionListener;
import ru.raserei.spacegame.engine.Base2dScreen;
import ru.raserei.spacegame.engine.Sprite;
import ru.raserei.spacegame.engine.fonts.Font;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.ui.GameOverMessage;
import ru.raserei.spacegame.ui.buttons.StartNewGameButton;

public class GameScreen extends Base2dScreen implements ActionListener {


    private enum State {PLAYING, GAME_OVER};
    private State state;

    private Texture backgroundTexture;
    private Background background;

    private Star[] stars;

    private TextureAtlas atlas;
    private MainShip mainShip;
    private BulletPool bulletPool;

    private EnemyShipPool enemyShipPool;
    private EnemyEmitter enemyEmitter;
    private ExplosionPool explosionPool;

    private StringBuilder scoreString;
    private int score;
    private int level;
    private StringBuilder HPString;
    private StringBuilder levelString;

    private GameOverMessage gameOverMessage;
    private StartNewGameButton newGameButton;

    //references
    private static final String BG_TEXTURE_NAME = "textures/bg.png";
    private static final String  ATLAS_NAME = "textures/mainAtlas.tpack";
    private static final String  STAR_NAME = "star";

    private Sound soundExplosion= Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    private Sound soundLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    private Sound soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    private Music music  = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
    private Font font = new Font("fonts/mainFont.fnt", "fonts/mainFont.png");

    private int localLevel;



    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        state = State.PLAYING;
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

        gameOverMessage = new GameOverMessage(atlas);
        newGameButton = new StartNewGameButton(atlas,this);

        scoreString = new StringBuilder();
        HPString = new StringBuilder();
        levelString = new StringBuilder();
        this.font.setWordSize(0.025f);
        startNewGame();

    }

    public void printInfo() {
        scoreString.setLength(0);
        HPString.setLength(0);
        levelString.setLength(0);
        font.draw(batch, scoreString.append("Score:").append(score), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, HPString.append("HP:").append(mainShip.getHp()).append("/").append(mainShip.getFullHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, levelString.append("Stage:").append(Integer.toString(level)), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    public void startNewGame(){
        explosionPool.freeAllActiveObjects();
        enemyShipPool.freeAllActiveObjects();
        bulletPool.freeAllActiveObjects();

        level = 1;
        mainShip.restart();
        score  = 0;
        enemyEmitter.setLevel(level);
        gameOverMessage.restart();

        state = State.PLAYING;
    }

    public void update(float delta){
        for (Star s:stars) {
            s.update(delta);
        }
        explosionPool.update(delta);
        if (mainShip.isAlive()) state = State.PLAYING;
        else state = State.GAME_OVER;
        switch (state) {
            case PLAYING:
                mainShip.update(delta);
                bulletPool.update(delta);
                enemyEmitter.emitEnemy(delta);
                enemyShipPool.update(delta);
                break;
            case GAME_OVER:
                gameOverMessage.update(delta);
        }

        enemyEmitter.setLevel(level);
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

        explosionPool.drawActiveObjects(batch);
        if (state == State.PLAYING){
            mainShip.draw(batch);
            bulletPool.drawActiveObjects(batch);
            enemyShipPool.drawActiveObjects(batch);
        }
        else if (explosionPool.getActiveObjects().isEmpty()) {
            gameOverMessage.draw(batch);
            newGameButton.draw(batch);
        }
        printInfo();
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
                if (enemy.isDestroyed()) addScore(enemy.getBounty());
                enemy.boom();
                mainShip.damage(mainShip.getFullHp()/2);
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
                    if (enemy.isDestroyed()) addScore(enemy.getBounty());
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

    private void addScore (int score){
         this.score+=score;
         localLevel = this.score/3+1;
         if (localLevel>this.level)
         {
             this.level=localLevel;
             mainShip.heal(mainShip.getFullHp());
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
        newGameButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        explosionPool.dispose();
        soundExplosion.dispose();
        soundBullet.dispose();
        soundLaser.dispose();
        music.dispose();
        font.dispose();
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
        newGameButton.touchDown(touch,pointer);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch,pointer);
        newGameButton.touchUp(touch,pointer);
    }

    @Override
    public void actionPerformed(Object src) {
        if(src==newGameButton){
            startNewGame();
            System.out.println("New game started");
        }
        else throw new RuntimeException("unknown src");
    }
}
