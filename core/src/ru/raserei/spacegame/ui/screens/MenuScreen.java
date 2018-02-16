package ru.raserei.spacegame.ui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import ru.raserei.spacegame.Field.*;
import ru.raserei.spacegame.engine.ActionListener;
import ru.raserei.spacegame.ui.buttons.ExitButton;
import ru.raserei.spacegame.ui.buttons.PlayButton;
import ru.raserei.spacegame.engine.Base2dScreen;
import ru.raserei.spacegame.engine.math.Rect;

public class MenuScreen extends Base2dScreen implements ActionListener{

    private Texture backgroundTexture;
    private Background background;

    private TextureAtlas atlas;

    private Star[] stars;
    private static final int STAR_AMOUNT = 64;

    private PlayButton playButton;
    private ExitButton exitButton;

    //references
    private static final String BG_TEXTURE_NAME = "textures/bg.png";
    private static final String  ATLAS_NAME = "./textures/menuAtlas.tpack";
    private static final String  STAR_NAME = "star";


    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        backgroundTexture = new Texture(BG_TEXTURE_NAME);
        background = new Background(new TextureRegion(backgroundTexture));
        atlas = new TextureAtlas(ATLAS_NAME);

        playButton = new PlayButton(atlas,this);
        exitButton = new ExitButton(atlas,this);

        stars = new Star[STAR_AMOUNT];
        for (int i =0; i<STAR_AMOUNT;i++){
            stars[i]= new Star(new TextureRegion(atlas.findRegion(STAR_NAME)));
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
        playButton.draw(batch);
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
        playButton.resize(worldBounds);
        exitButton.resize(worldBounds);
        for (Star s:stars) {
            s.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        playButton.touchDown(touch,pointer);
        exitButton.touchDown(touch,pointer);
    }


    @Override
    public void touchUp(Vector2 touch, int pointer) {
        playButton.touchUp(touch,pointer);
        exitButton.touchUp(touch,pointer);
    }


    @Override
    public void actionPerformed(Object src) {
        if (src == exitButton) {
            Gdx.app.exit();
        } else if (src == playButton) {
            game.setScreen(new GameScreen(game));
        } else {
            throw new RuntimeException("Unknown src " + src);
        }
    }

}
