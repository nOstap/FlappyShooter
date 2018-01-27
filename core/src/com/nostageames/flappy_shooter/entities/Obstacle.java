package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nostageames.flappy_shooter.interfaces.Updatable;
import com.nostageames.flappy_shooter.utils.Constants;
import com.nostageames.flappy_shooter.utils.Helpers;

import static com.nostageames.flappy_shooter.utils.Constants.PPM;

/**
 * Created by nostap on 26.01.18.
 */

public class Obstacle extends Entity implements Updatable {
    public static final int MAX_WIDTH = 50;
    public static final int MAX_HEIGHT = 50;
    public static final int MIN_HEIGHT = 5;
    public static final int MIN_WIDTH = 5;

    int lifetime = 3000;

    public Obstacle(World world) {
        this.world = world;
    }


    @Override
    public void update(float dt) {
        if (System.currentTimeMillis() - startTime == lifetime) isKilled = true;
        if (b2body.getType() == BodyDef.BodyType.DynamicBody) {
            b2body.applyForce(
                    new Vector2(0, b2body.getMass() * Constants.GRAVITY),
                    b2body.getWorldCenter(),
                    true
            );
        }
    }

    public Obstacle createStatic(float width, float height, Vector2 position) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.angle = Helpers.nextRandom(0.0f, (float) Math.PI);

        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = Constants.OBSTACLE_BIT;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM, height / PPM);

        fdef.shape = shape;
        create(bdef, fdef);
        shape.dispose();
        return this;
    }

    public Obstacle createNonGravity(float width, float height, Vector2 position) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.angle = Helpers.nextRandom(0.0f, (float) Math.PI);

        FixtureDef fdef = new FixtureDef();
        fdef.density = 100;
        fdef.filter.categoryBits = Constants.OBSTACLE_BIT;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM, height / PPM);

        fdef.shape = shape;
        create(bdef, fdef);
        shape.dispose();
        return this;
    }

    public Obstacle createCinematic(float width, float height, Vector2 position) {

        return this;
    }
}
