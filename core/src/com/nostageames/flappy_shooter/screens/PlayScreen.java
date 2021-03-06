package com.nostageames.flappy_shooter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nostageames.flappy_shooter.FlappyShooter;
import com.nostageames.flappy_shooter.entities.Entity;
import com.nostageames.flappy_shooter.interfaces.InputHandleEntity;
import com.nostageames.flappy_shooter.entities.Player;
import com.nostageames.flappy_shooter.interfaces.UpdatableEntity;
import com.nostageames.flappy_shooter.scenes.Hud;
import com.nostageames.flappy_shooter.utils.Constants;
import com.nostageames.flappy_shooter.utils.WorldGenerator;

import java.util.Iterator;

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
    private World world;
    private WorldGenerator worldGenerator;
    private Array<Entity> entities;


    public PlayScreen(FlappyShooter game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(
                Constants.WIDTH / Constants.PPM,
                Constants.HEIGHT / Constants.PPM,
                gamecam);
        hud = new Hud(game.batch);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10.0f), true);
        b2dr = new Box2DDebugRenderer();

        entities = new Array<Entity>();
        player = new Player(this);
        worldGenerator = new WorldGenerator(world, entities);
        entities.add(player);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, gamecam.combined);
        game.batch.begin();


        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public float getPlayerDistance() {
        return this.player.getBody().getPosition().x;
    }

    public Hud getHud() {
        return hud;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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

    @Override
    public void show() {

    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        disposeEntities();
    }

    private void handleInput(float dt) {
        for (Entity entity : entities) {
            if (entity instanceof InputHandleEntity) {
                ((InputHandleEntity) entity).handleInput(dt);
            }
        }
    }

    private void update(float dt) {

        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        hud.update(dt, this);

        worldGenerator.update(dt, getPlayerDistance());

        updateEntities(dt);
//        disposeKilledEntities();

        gamecam.position.x = setCameraCenter();
        gamecam.update();
    }

    private void disposeKilledEntities() {
        Iterator<Entity> entityIterator = entities.iterator();
        while(entityIterator.hasNext()) {
            if (entityIterator.next().isKilled) {
                entityIterator.next().dispose();
                entityIterator.remove();
            }
        }
    }

    private void disposeEntities() {
        for (Entity entity : entities) {
            entity.dispose();
        }
    }

    private void updateEntities(float dt) {
        for (Entity entity : entities) {
            if (entity instanceof UpdatableEntity) {
                ((UpdatableEntity) entity).update(dt);
            }
        }
    }

    private float setCameraCenter() {
        return (player.getBody().getPosition().x + gamePort.getWorldWidth() / 2) - 2;
    }

}
