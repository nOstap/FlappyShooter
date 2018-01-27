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

    public Entity create(BodyDef bodyDef, FixtureDef fdef) {
        b2body = world.createBody(bodyDef);
        b2body.createFixture(fdef);
        return this;
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
