package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.nostageames.flappy_shooter.utils.Constants;

/**
 * Created by nostap on 26.01.18.
 */

public abstract class Entity extends Sprite implements Disposable {

    Body b2body;
    World world;
    long startTime = System.currentTimeMillis();
    public boolean isKilled = false;

    public Body getBody() {
        return b2body;
    }

    public World getWorld() {
        return world;
    }

    public <T> T create(float width, float height, Vector2 position, BodyDef.BodyType bodyType) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = bodyType;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        shape.dispose();
        return (T) this;
    }

    public <T> T create(float radius, Vector2 position, BodyDef.BodyType bodyType) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = bodyType;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / Constants.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        shape.dispose();
        return (T) this;
    }

    void setVelocityX(float x) {
        b2body.setLinearVelocity(x, b2body.getLinearVelocity().y);
    }

    void setVelocityY(float y) {
        b2body.setLinearVelocity(b2body.getLinearVelocity().x, y);
    }

    @Override
    public void dispose() {
        world.destroyBody(b2body);
    }

}
