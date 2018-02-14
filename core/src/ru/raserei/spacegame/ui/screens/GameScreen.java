package ru.raserei.spacegame.ui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.Background;
import ru.raserei.spacegame.misc.BulletPool;
import ru.raserei.spacegame.ships.MainShip;
import ru.raserei.spacegame.Star;
import ru.raserei.spacegame.engine.Base2dScreen;
import ru.raserei.spacegame.engine.math.Rect;

public class GameScreen extends Base2dScreen {

    private Texture backgroundTexture;
    private Background background;

    private Star[] stars;

    private TextureAtlas atlas;
    private MainShip mainShip;
    private BulletPool msbPool;

    //references
    private static final String BG_TEXTURE_NAME = "bg.jpg";
    private static final String  ATLAS_NAME = "mainAtlas.tpack";
    private static final String  STAR_NAME = "star";

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas(ATLAS_NAME);
        backgroundTexture = new Texture(BG_TEXTURE_NAME);
        background = new Background(new TextureRegion(backgroundTexture));
        stars = new Star[64];
        for (int i = 0; i < 64; i++) {
            stars[i] = new Star(new TextureRegion(atlas.findRegion(STAR_NAME)));
        }
        msbPool = new BulletPool();
        mainShip = new MainShip(atlas,msbPool);
    }
    public void update(float delta){
        for (Star s:stars) {
            s.update(delta);
        }
        mainShip.update(delta);
        msbPool.update(delta);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        msbPool.freeAllDestroyedActiveObjects();
        update(delta);
        Gdx.gl.glClearColor(0.7f, 0.3f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        msbPool.drawActiveObjects(batch);
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star s:stars) {
            s.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        msbPool.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
        atlas.dispose();
        msbPool.dispose();
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
