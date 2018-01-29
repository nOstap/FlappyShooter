package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nostageames.flappy_shooter.interfaces.CanBeHurted;
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

public class NoGravityObstacle extends Obstacle implements CanBeHurted {
    public static final int MAX_WIDTH = 50;
    public static final int MAX_HEIGHT = 50;
    public static final int MIN_HEIGHT = 5;
    public static final int MIN_WIDTH = 5;

    int life = 50;
    int killScore = 10;
    int hitScore = 0;

    public NoGravityObstacle(PlayScreen game) {
        super(game);
    }


    public NoGravityObstacle create(float width, float height, Vector2 position) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.angle = Helpers.nextRandom(0.0f, (float) Math.PI);
        b2body = game.getWorld().createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = Constants.OBSTACLE_BIT;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM, height / PPM);

        fdef.shape = shape;
        fdef.density = 50;
        fdef.friction = .5f;
        Fixture fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
        shape.dispose();
        return this;
    }
}
