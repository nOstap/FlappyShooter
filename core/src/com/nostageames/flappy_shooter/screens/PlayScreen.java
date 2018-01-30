package com.nostageames.flappy_shooter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.nostageames.flappy_shooter.handlers.CollisionHandler;
import com.nostageames.flappy_shooter.interfaces.InputHandleEntity;
import com.nostageames.flappy_shooter.entities.Player;
import com.nostageames.flappy_shooter.interfaces.Updatable;
import com.nostageames.flappy_shooter.scenes.Hud;
import com.nostageames.flappy_shooter.utils.Constants;
import com.nostageames.flappy_shooter.utils.PolygonRenderer;
import com.nostageames.flappy_shooter.utils.WorldGenerator;

import java.util.Iterator;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import static com.badlogic.gdx.Input.Keys.SPACE;
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

    private float score = 0.0f;
    private int cameraSpeed = 150;
    private final float cameraSpeedRatio = 0.1f;
    private final float cameraMaxSpeed = Player.IMPULSE_X_RATIO;
    private boolean isPlaying = false;
    public RayHandler rayHandler;
    private Array<PointLight> lights;

    public PlayScreen(FlappyShooter game) {
        this.game = game;
        camera = new OrthographicCamera();
        gamePort = new FitViewport(
                Constants.WIDTH / PPM,
                Constants.HEIGHT / PPM,
                camera);
        hud = new Hud(game.batch);
        camera.position.set(gamePort.getWorldWidth() / 2 - 1, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -Constants.GRAVITY), true);
        world.setContactListener(new CollisionHandler());
        rayHandler = new RayHandler(world);
        rayHandler.setBlur(false);
        rayHandler.setAmbientLight(new Color(0x090315ff));
        b2dr = new PolygonRenderer();

        entities = new Array<Entity>();
        lights = new Array<PointLight>();
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

        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }

    private void handleInput(float dt) {
        if (!isPlaying && Gdx.input.isKeyJustPressed(SPACE)) {
            isPlaying = true;
        }
        for (Entity entity : entities) {
            if (entity instanceof InputHandleEntity) {
                ((InputHandleEntity) entity).handleInput(dt);
            }
        }
    }

    private void update(float dt) {
        handleInput(dt);
        if (!isPlaying) return;
        world.step(1 / 60f, 6, 2);
        hud.update(dt, this);
        worldGenerator.update(dt);
        updateEntities(dt);
        updateLights(dt);
        updateCamera(dt);

        if (player.getIsDied()) {
            game.create();
        }
    }

    private void updateCamera(float dt) {
        final float currentDistance = getDistance();
        camera.position.x += cameraSpeed / PPM * dt;
        cameraSpeed += currentDistance * cameraSpeedRatio * dt;
        camera.update();
    }

    private void updateEntities(float dt) {
        for (Entity entity : entities) {
            if (entity instanceof Updatable) {
                ((Updatable) entity).update(dt);
            }
        }
        Iterator<Entity> i = entities.iterator();
        while (i.hasNext()) {
            Entity b = i.next();
            if (b.isKilled) {
                i.remove();
            }
        }
    }

    private void updateLights(float dt) {
//        for (PointLight light: lights) {
//            if(light.getPosition().x < getDistance()-gamePort.getWorldWidth()) {
//                System.out.println(gamePort.getWorldWidth());
//                light.dispose();
//                light.remove();
//            }
//        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public float getDistance() {
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

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return (int) score;
    }

    public void setScore(float score) {
        this.score = score;
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

    public void updateScore(float value) {
        score += value;
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        rayHandler.dispose();
    }

    public Array<PointLight> getLights() {
        return lights;
    }
}
