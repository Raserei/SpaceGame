package ru.raserei.spacegame.ui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.Background;
import ru.raserei.spacegame.Star;
import ru.raserei.spacegame.ui.buttons.Button;
import ru.raserei.spacegame.ui.buttons.ExitButton;
import ru.raserei.spacegame.ui.buttons.PlayButton;
import ru.raserei.spacegame.engine.Base2dScreen;
import ru.raserei.spacegame.engine.math.Rect;

public class MenuScreen extends Base2dScreen {

    private Texture backgroundTexture;
    private Background background;

    private Texture startButtonTexture;
    private Button startButton;

    private Texture exitButtonTexture;
    private Button exitButton;

    private Texture starTexture;
    private Star[] stars;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        backgroundTexture = new Texture("bg.jpg");
        background = new Background(new TextureRegion(backgroundTexture));

        startButtonTexture = new Texture("startButton.jpg");
        startButton = new PlayButton(new TextureRegion(startButtonTexture));

        exitButtonTexture = new Texture("exitButton.jpg");
        exitButton = new ExitButton(new TextureRegion(exitButtonTexture));

        starTexture = new Texture("star.png");
        stars = new Star[64];
        for (int i =0; i<64;i++){
            stars[i]= new Star(new TextureRegion(starTexture));
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
        for (Star s:stars) {
            s.draw(batch);
        }
        startButton.draw(batch);
        exitButton.draw(batch);
        batch.end();
    }

    private void update(float delta){
        for (Star s:stars) {
            s.update(delta);
        }
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        startButton.resize(worldBounds);
        exitButton.resize(worldBounds);
        for (Star s:stars) {
            s.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        startButtonTexture.dispose();
        exitButtonTexture.dispose();
        starTexture.dispose();
        super.dispose();
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {

    }



    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        if (startButton.isMe(touch)) game.setScreen(new GameScreen(game));
    }

}
