package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nostageames.flappy_shooter.interfaces.CanKillPlayer;
import com.nostageames.flappy_shooter.interfaces.HittableEntity;
import com.nostageames.flappy_shooter.interfaces.Updatable;
import com.nostageames.flappy_shooter.screens.PlayScreen;
import com.nostageames.flappy_shooter.utils.Constants;
import com.nostageames.flappy_shooter.utils.Helpers;

import static com.nostageames.flappy_shooter.utils.Constants.PPM;

/**
 * Created by nostap on 26.01.18.
 */

public class ObstacleStatic extends Obstacle implements Updatable, CanKillPlayer {
    public static final int MAX_WIDTH = 50;
    public static final int MAX_HEIGHT = 50;
    public static final int MIN_HEIGHT = 5;
    public static final int MIN_WIDTH = 5;

    int life = 50;
    int lifetime = 3000;

    public ObstacleStatic(PlayScreen game) {
        super(game);
    }


    @Override
    public void update(float dt) {
        if (markToKill) kill();
        if (game.getCamera().position.x - (Constants.WIDTH / PPM) > b2body.getPosition().x) kill();
        if (b2body.getType() == BodyDef.BodyType.DynamicBody) {
            b2body.applyForce(
                    new Vector2(0, b2body.getMass() * Constants.GRAVITY),
                    b2body.getWorldCenter(),
                    true
            );
        }
    }

    public ObstacleStatic createStatic(float width, float height, Vector2 position) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.angle = Helpers.nextRandom(0.0f, (float) Math.PI);
        b2body = world.createBody(bdef);

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

    public ObstacleStatic createNonGravity(float width, float height, Vector2 position) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.angle = Helpers.nextRandom(0.0f, (float) Math.PI);
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.density = 50;
        fdef.filter.categoryBits = Constants.OBSTACLE_BIT;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM, height / PPM);

        fdef.shape = shape;
        Fixture fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
        shape.dispose();
        return this;
    }

    public ObstacleStatic createCinematic(float width, float height, Vector2 position) {

        return this;
    }

    @Override
    public void decreaseLife(int damage) {
        if (life == -1) return;
        if (life == 0) { markToKill = true; }
        life -= damage;
    }

    @Override
    public int getLife() {
        return life;
    }
}
