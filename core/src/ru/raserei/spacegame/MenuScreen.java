package ru.raserei.spacegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.raserei.spacegame.engine.Base2dScreen;

public class MenuScreen extends Base2dScreen {
    Space2dGame game;

    private SpriteBatch batch;
    private Texture background;
    private Texture playerShip;
    private float shipVelocity;
    private Vector2 shipPos;
    private Vector2 shipDestination;
    private Vector2 shipDirection;


    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        playerShip = new Texture("player_ship.png");
        background = new Texture("bg.jpg");
        shipPos = new Vector2(30,51);
        shipVelocity = 500f;
        shipDestination = new Vector2(shipPos);
        shipDirection = new Vector2(0,0);
    }

    @Override
    public void render (float delta) {
        super.render(delta);
        update(delta);
        Gdx.gl.glClearColor(0.1f, 0.15f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(playerShip,shipPos.x-30,shipPos.y-41);
        batch.end();
    }

    void update(float dt){
        if (shipPos.cpy().epsilonEquals(shipDestination)) return;
        if ((shipDestination.cpy().sub(shipPos)).len() <
                (shipPos.cpy().add(shipDirection.cpy().scl(shipVelocity)).scl(dt)).len()){
            shipPos=shipDestination;
        }
        else
            shipPos.add(shipDirection.cpy().scl(shipVelocity).scl(dt));
    }

    @Override
    public void dispose () {
        batch.dispose();
        background.dispose();
        playerShip.dispose();
        super.dispose();
    }

    public void setShipDestination(Vector2 dest){
        shipDestination = dest;
        shipDirection=shipDestination.cpy().sub(shipPos.cpy()).nor();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        setShipDestination(new Vector2(screenX,Gdx.graphics.getHeight()-screenY));
        return super.touchDown(screenX,screenY,pointer,button);
    }
}
