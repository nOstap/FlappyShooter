package com.nostageames.flappy_shooter.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.nostageames.flappy_shooter.entities.NoGravityObstacle;
import com.nostageames.flappy_shooter.entities.Obstacle;
import com.nostageames.flappy_shooter.entities.StaticObstacle;
import com.nostageames.flappy_shooter.interfaces.Updatable;
import com.nostageames.flappy_shooter.screens.PlayScreen;

import box2dLight.PointLight;

import static com.nostageames.flappy_shooter.utils.Constants.PPM;
import static com.nostageames.flappy_shooter.utils.Helpers.Bounds;
import static com.nostageames.flappy_shooter.utils.Helpers.nextRandom;

/**
 * Created by nostap on 20.01.18.
 */

public class WorldGenerator implements Updatable {

    private final static int PART_WIDTH = 1200;
    private PlayScreen game;
    private float currentGeneratorPosition = Constants.WIDTH;
    private float distanceRatio = 0.1f;
    private float staticObstaclesRatio = 0.1f;
    private float nonGravityObstaclesRatio = 0.3f;
    private boolean oddState = false;

    public WorldGenerator(PlayScreen game) {
        this.game = game;
        nextPart();
    }

    private void nextPart() {
        nextPart(new Bounds(
                        currentGeneratorPosition,
                        currentGeneratorPosition + PART_WIDTH,
                        Constants.HEIGHT,
                        0
                )
        );
    }

    private void nextPart(Bounds bounds) {
        float partWidth = bounds.right - bounds.left;
        generateStaticObstacles(bounds);
        generateNonGravityObjects(bounds);
        generateLights(bounds);
        currentGeneratorPosition += partWidth;
    }

    private void generateStaticObstacles(Bounds bounds) {
        int count = (int) (3 + game.getDistance() * distanceRatio * staticObstaclesRatio);
        for (int i = 0; i < count; i++) {
            float width = nextRandom(Obstacle.MIN_WIDTH, Obstacle.MAX_WIDTH);
            float height = nextRandom(Obstacle.MIN_HEIGHT, Obstacle.MAX_HEIGHT);
            Vector2 position = new Vector2(
                    nextRandom(bounds.left, bounds.right) / PPM,
                    nextRandom(bounds.bottom, bounds.top) / PPM
            );
            game.getEntities().add(new StaticObstacle(game).create(width, height, position));
        }
    }

    private void generateNonGravityObjects(Bounds bounds) {
        int count = (int) (10 + game.getDistance() * distanceRatio * nonGravityObstaclesRatio);
        for (int i = 0; i < count; i++) {
            float width = nextRandom(Obstacle.MIN_WIDTH, Obstacle.MAX_WIDTH);
            float height = nextRandom(Obstacle.MIN_HEIGHT, Obstacle.MAX_HEIGHT);
            Vector2 position = new Vector2(
                    nextRandom(bounds.left, bounds.right) / PPM,
                    nextRandom(bounds.bottom, bounds.top) / PPM
            );
            game.getEntities().add(new NoGravityObstacle(game).create(width, height, position));
        }
    }

    public void generateLights(Bounds bounds) {
        oddState = !oddState;

        float x = bounds.left / PPM;
        float y = oddState ? (bounds.top / PPM + 1) : (bounds.bottom / PPM - 1);
        final int[] colors = new int[]{
                0x2da8ffff,
                0xc032efff,
                0x2388d0ff
        };
        game.getLights().add(new PointLight(
                game.rayHandler,
                Constants.RAYS_NUM,
                new Color(colors[nextRandom(0, 2)]),
                10,
                x, y
        ));
    }

    @Override
    public void update(float dt) {
        float currentDistance = game.getDistance();
        currentDistance *= PPM;
        if (currentDistance + PART_WIDTH > currentGeneratorPosition) {
            nextPart();
        }
    }
}
