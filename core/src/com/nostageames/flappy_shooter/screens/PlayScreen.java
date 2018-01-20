package com.nostageames.flappy_shooter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nostageames.flappy_shooter.FlappyShooter;
import com.nostageames.flappy_shooter.entities.Player;
import com.nostageames.flappy_shooter.scenes.Hud;
import com.nostageames.flappy_shooter.utils.Constants;

/**
 * Created by nostap on 20.01.18.
 */

public class PlayScreen implements Screen {

    private FlappyShooter game;
    private Viewport gamePort;
    private OrthographicCamera gamecam;
    private Player player;
    private Hud hud;
    private Box2DDebugRenderer b2dr;

    public World world;

    public PlayScreen(FlappyShooter game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT);
        hud = new Hud(game.batch);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10.0f), true);
        b2dr = new Box2DDebugRenderer();

        player = new Player(world);
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.b2body.applyLinearImpulse(new Vector2(1.0f, 4.0f), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float dt) {

        handleInput(dt);

        world.step(1 / 60f, 6, 2);
        player.update(dt);
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.update();
    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, gamecam.combined.scl(Constants.PPM));
        game.batch.begin();
        // Draw all your game here;
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public Hud getHud() { return hud; }
}
