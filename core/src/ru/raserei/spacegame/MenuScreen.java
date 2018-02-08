package ru.raserei.spacegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.buttons.Button;
import ru.raserei.spacegame.buttons.ExitButton;
import ru.raserei.spacegame.buttons.PlayButton;
import ru.raserei.spacegame.engine.Base2dScreen;
import ru.raserei.spacegame.engine.math.Rect;

public class MenuScreen extends Base2dScreen {

    private Texture backgroundTexture;
    private Background background;

    private Texture startButtonTexture;
    private Button startButton;

    private Texture exitButtonTexture;
    private Button exitButton;

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

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.7f, 0.3f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        startButton.draw(batch);
        exitButton.draw(batch);
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        startButton.resize(worldBounds);
        exitButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        startButtonTexture.dispose();
        exitButtonTexture.dispose();
        super.dispose();
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
    }
}
