package ru.raserei.spacegame.ui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.raserei.spacegame.Background;
import ru.raserei.spacegame.Star;
import ru.raserei.spacegame.engine.Base2dScreen;
import ru.raserei.spacegame.engine.math.Rect;
import ru.raserei.spacegame.ui.buttons.Button;
import ru.raserei.spacegame.ui.buttons.ExitButton;
import ru.raserei.spacegame.ui.buttons.PlayButton;

/**
 * Created by Raserei on 11.02.2018.
 */

public class GameScreen extends Base2dScreen {

    private Texture backgroundTexture;
    private Background background;

    private Texture starTexture;
    private Star[] stars;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        backgroundTexture = new Texture("bg.jpg");
        background = new Background(new TextureRegion(backgroundTexture));
        starTexture = new Texture("star.png");
        stars = new Star[64];
        for (int i =0; i<64;i++){
            stars[i]= new Star(new TextureRegion(starTexture));
        }
    }

    public void update(float delta){
        for (Star s:stars) {
            s.update(delta);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        Gdx.gl.glClearColor(0.7f, 0.3f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star s:stars) {
            s.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
        starTexture.dispose();
    }
}
