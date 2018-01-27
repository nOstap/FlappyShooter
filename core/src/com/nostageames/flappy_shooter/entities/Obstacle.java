package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.nostageames.flappy_shooter.interfaces.UpdatableEntity;

/**
 * Created by nostap on 26.01.18.
 */

public class Obstacle extends Entity implements UpdatableEntity {
    public static final int MAX_WIDTH = 100;
    public static final int MAX_HEIGHT = 100;
    public static final int MIN_HEIGHT = 20;
    public static final int MIN_WIDTH = 20;

    int lifetime = 3000;

    public Obstacle(World world) {
        this.world = world;
    }


    @Override
    public void update(float dt) {
        if(System.currentTimeMillis()-startTime == lifetime) isKilled = true;
    }
}
