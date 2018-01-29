package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nostageames.flappy_shooter.interfaces.CanBeKilled;
import com.nostageames.flappy_shooter.interfaces.CanKillPlayer;
import com.nostageames.flappy_shooter.interfaces.Updatable;
import com.nostageames.flappy_shooter.screens.PlayScreen;
import com.nostageames.flappy_shooter.utils.Constants;
import com.nostageames.flappy_shooter.utils.Helpers;

import static com.nostageames.flappy_shooter.utils.Constants.PPM;

/**
 * Created by nostap on 26.01.18.
 */

public class StaticObstacle extends Obstacle implements CanKillPlayer {
    public static final int MAX_WIDTH = 50;
    public static final int MAX_HEIGHT = 50;
    public static final int MIN_HEIGHT = 5;
    public static final int MIN_WIDTH = 5;

    int life = 100;
    int killScore = 100;
    int hitScore = 0;

    public StaticObstacle(PlayScreen game) {
        super(game);
    }
}
