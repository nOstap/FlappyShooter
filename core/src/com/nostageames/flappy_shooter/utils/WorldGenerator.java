package com.nostageames.flappy_shooter.utils;

import com.badlogic.gdx.math.Vector2;
import com.nostageames.flappy_shooter.entities.Obstacle;
import com.nostageames.flappy_shooter.interfaces.Updatable;
import com.nostageames.flappy_shooter.screens.PlayScreen;

import static com.nostageames.flappy_shooter.utils.Constants.PPM;
import static com.nostageames.flappy_shooter.utils.Helpers.Bounds;
import static com.nostageames.flappy_shooter.utils.Helpers.nextRandom;

/**
 * Created by nostap on 20.01.18.
 */

public class WorldGenerator implements Updatable {

    private PlayScreen game;
    private float currentGeneratorPosition = Constants.WIDTH;
    private float distanceRatio = 0.1f;
    private float staticObstaclesRatio = 0.1f;
    private float nonGravityObstaclesRatio = 0.2f;

    public WorldGenerator(PlayScreen game) {
        this.game = game;
    }

    private void nextPart() {
        nextPart(new Bounds(
                        currentGeneratorPosition,
                        currentGeneratorPosition + Constants.WIDTH,
                        Constants.HEIGHT,
                        0
                )
        );
    }

    private void nextPart(Bounds bounds) {
        float partWidth = bounds.right - bounds.left;
        generateStaticObstacles(bounds);
        generateNonGravityObjects(bounds);
        currentGeneratorPosition += partWidth;
    }

    private void generateStaticObstacles(Bounds bounds) {
        int count = (int) (5 + game.getPlayerDistance()* distanceRatio * staticObstaclesRatio);
        for (int i = 0; i < count; i++) {
            float width = nextRandom(Obstacle.MIN_WIDTH, Obstacle.MAX_WIDTH);
            float height = nextRandom(Obstacle.MIN_HEIGHT, Obstacle.MAX_HEIGHT);
            Vector2 position = new Vector2(
                    nextRandom(bounds.left, bounds.right) / PPM,
                    nextRandom(bounds.bottom, bounds.top) / PPM
            );
            game.getEntities().add(new Obstacle(game).createStatic(width, height, position));
        }
    }

    private void generateNonGravityObjects(Bounds bounds) {
        int count = (int) (10 + game.getPlayerDistance()* distanceRatio * nonGravityObstaclesRatio);
        for (int i = 0; i < count; i++) {
            float width = nextRandom(Obstacle.MIN_WIDTH, Obstacle.MAX_WIDTH);
            float height = nextRandom(Obstacle.MIN_HEIGHT, Obstacle.MAX_HEIGHT);
            Vector2 position = new Vector2(
                    nextRandom(bounds.left, bounds.right) / PPM,
                    nextRandom(bounds.bottom, bounds.top) / PPM
            );
            game.getEntities().add(new Obstacle(game).createNonGravity(width, height, position));
        }
    }

    @Override
    public void update(float dt) {
        float currentDistance = game.getPlayerDistance();
        currentDistance *= PPM;
        if (currentDistance + Constants.WIDTH > currentGeneratorPosition) {
            nextPart();
        }
    }
}
