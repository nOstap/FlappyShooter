package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nostageames.flappy_shooter.interfaces.Updatable;
import com.nostageames.flappy_shooter.screens.PlayScreen;
import com.nostageames.flappy_shooter.utils.Constants;
import com.nostageames.flappy_shooter.utils.Helpers;

import static com.nostageames.flappy_shooter.utils.Constants.PPM;

/**
 * Created by nostap on 26.01.18.
 */

public abstract class Obstacle extends Entity implements Updatable {
    public static final int MAX_WIDTH = 50;
    public static final int MAX_HEIGHT = 50;
    public static final int MIN_HEIGHT = 5;
    public static final int MIN_WIDTH = 5;

    protected int life = 100;
    protected int killScore = 100;
    protected int hitScore = 0;

    public Obstacle(PlayScreen game) {
        this.game = game;
        entityType = EntityType.OBSTACLE;
    }

    @Override
    public void update(float dt) {
        if (markToKill) dispose();
        if (game.getCamera().position.x - (Constants.WIDTH / PPM) > b2body.getPosition().x) dispose();
        if (b2body.getType() == BodyDef.BodyType.DynamicBody) {
            b2body.applyForce(
                    new Vector2(0, b2body.getMass() * Constants.GRAVITY),
                    b2body.getWorldCenter(),
                    true
            );
        }
    }

    public Obstacle create(float width, float height, Vector2 position) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.angle = Helpers.nextRandom(0.0f, (float) Math.PI);
        b2body = game.getWorld().createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = Constants.OBSTACLE_BIT;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM, height / PPM);

        fdef.shape = shape;
        Fixture fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
        shape.dispose();
        return this;
    }

    public int getKillScore() {
        return killScore;
    }

    public void decreaseLife(int damage) {
        if ((life - damage) > 0) {
            life -= damage;
        } else {
            life = 0;
        }
    }

    public int getHitScore() {
        return hitScore;
    }

    public int getLife() {
        return life;
    }
}
