package com.nostageames.flappy_shooter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nostageames.flappy_shooter.FlappyShooter;
import com.nostageames.flappy_shooter.entities.Entity;
import com.nostageames.flappy_shooter.interfaces.InputHandleEntity;
import com.nostageames.flappy_shooter.entities.Player;
import com.nostageames.flappy_shooter.interfaces.Updatable;
import com.nostageames.flappy_shooter.scenes.Hud;
import com.nostageames.flappy_shooter.utils.Constants;
import com.nostageames.flappy_shooter.utils.WorldGenerator;

import java.util.Iterator;

import static com.nostageames.flappy_shooter.utils.Constants.PPM;

/**
 * Created by nostap on 20.01.18.
 */

public class PlayScreen implements Screen {

    private FlappyShooter game;
    private Viewport gamePort;
    private OrthographicCamera camera;
    private Player player;
    private Hud hud;
    private Box2DDebugRenderer b2dr;
    private World world;
    private WorldGenerator worldGenerator;
    private Array<Entity> entities;

    private int cameraSpeed = 100;
    private float cameraSpeedRatio = 0.1f;
    private float cameraMaxSpeed = Player.IMPULSE_X_RATIO;

    public PlayScreen(FlappyShooter game) {
        this.game = game;
        camera = new OrthographicCamera();
        gamePort = new FitViewport(
                Constants.WIDTH / PPM,
                Constants.HEIGHT / PPM,
                camera);
        hud = new Hud(game.batch);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -Constants.GRAVITY), true);
        b2dr = new Box2DDebugRenderer();

        entities = new Array<Entity>();
        player = new Player(this);
        worldGenerator = new WorldGenerator(this);
        entities.add(player);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, camera.combined);
        game.batch.begin();


        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public float getPlayerDistance() {
        return this.player.getBody().getPosition().x;
    }

    public Array<Entity> getEntities() {
        return entities;
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

        worldGenerator.update(dt);

        updateEntities(dt);
//        disposeKilledEntities();

        updateCamera(dt);
    }

    private void updateCamera(float dt) {
        final float currentDistance = getPlayerDistance();
        camera.position.x += cameraSpeed / PPM * dt;
        cameraSpeed += currentDistance * cameraSpeedRatio * dt;
        if (currentDistance > camera.position.x) camera.position.x = currentDistance;
        camera.update();
    }

    private void disposeKilledEntities() {
        Iterator<Entity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
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
            if (entity instanceof Updatable) {
                ((Updatable) entity).update(dt);
            }
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
