package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.nostageames.flappy_shooter.utils.Constants;

/**
 * Created by nostap on 20.01.18.
 */

public class Bulet extends Entity {
    public int damage = 1;
    public int speed = 20;
    public int radius = 2;
    public float lifetime = 100;

    public Bulet(World world) {
        this.world = world;
    }

    public Bulet create(Vector2 position) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyType.DynamicBody;
        bdef.bullet = true;

        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = Constants.BULET_BIT;
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / Constants.PPM);

        fdef.shape = shape;
        create(bdef, fdef);
        shape.dispose();
        return this;
    }

    public void release() {
        b2body.applyLinearImpulse(new Vector2(speed, 0), b2body.getWorldCenter(), false);
    }

    @Override
    public void dispose() {
        world.destroyBody(b2body);
    }
}
