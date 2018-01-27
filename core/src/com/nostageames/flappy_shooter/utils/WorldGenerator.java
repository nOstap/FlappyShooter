package com.nostageames.flappy_shooter.utils;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.nostageames.flappy_shooter.entities.Enemy;
import com.nostageames.flappy_shooter.entities.Entity;
import com.nostageames.flappy_shooter.entities.Obstacle;

import java.util.Date;
import java.util.Iterator;

/**
 * Created by nostap on 20.01.18.
 */

public class WorldGenerator {

    private World world;
    private RandomXS128 random;
    private float currentGeneratorPosition = 300.0f;
    Array<Entity> entities;

    public WorldGenerator(World world, Array<Entity> entities) {
        this.world = world;
        this.random = new RandomXS128(new Date().getTime());
        this.entities = entities;
    }

    public void nextPart() {
        nextPart(Constants.HEIGHT,
                0, currentGeneratorPosition,
                currentGeneratorPosition + Constants.WIDTH);
    }

    public void nextPart(float top, float bottom, float left, float right) {
        float partWidth = right - left;
        float partHeight = top - bottom;
        int maxObstacles = (int) ((partWidth / Enemy.MAX_WIDTH) * partHeight / Obstacle.MAX_HEIGHT / 10);
        System.out.printf("height: %.0f, width: %.0f, maxObstacles: %d", partHeight, partWidth, maxObstacles);
        for (int i = 0; i < maxObstacles; i++) {
            float width = random.nextInt(Obstacle.MAX_WIDTH) + Obstacle.MIN_WIDTH / Constants.PPM;
            float height = random.nextInt(Obstacle.MAX_HEIGHT) + Obstacle.MIN_HEIGHT / Constants.PPM;
            Vector2 position = new Vector2(
                    (random.nextInt((int) right) + left) / Constants.PPM,
                    (random.nextInt((int) top) + bottom) / Constants.PPM);
            entities.add(new Obstacle(world).<Obstacle>create(width, height, position, BodyDef.BodyType.StaticBody));
        }
        currentGeneratorPosition += partWidth;
    }

    public void update(float dt, float currentDistance) {
        currentDistance *= Constants.PPM;
        if (currentDistance + Constants.WIDTH > currentGeneratorPosition) {
            nextPart();
        }
    }
}
