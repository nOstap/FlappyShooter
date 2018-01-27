package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.nostageames.flappy_shooter.interfaces.CanDealDamage;
import com.nostageames.flappy_shooter.interfaces.HittableEntity;
import com.nostageames.flappy_shooter.interfaces.Updatable;
import com.nostageames.flappy_shooter.utils.Constants;

/**
 * Created by nostap on 20.01.18.
 */

public class Bullet extends Entity implements Updatable, CanDealDamage {
    public int damage = 1;
    public int speed = 20;
    public int radius = 2;
    public float lifetime = 1000;

    public Bullet(World world) {
        this.world = world;
        entityType = EntityType.BULLET;
    }

    public Bullet create(Vector2 position) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyType.DynamicBody;
        bdef.bullet = true;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        fdef.restitution = 0.1f;
        fdef.filter.categoryBits = Constants.BULLET_BIT;
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / Constants.PPM);

        fdef.shape = shape;
        Fixture fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
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

    @Override
    public void dealDamage(HittableEntity entity) {
        entity.decreaseLife(damage);
    }

    @Override
    public void update(float dt) {
        if (System.currentTimeMillis() - startTime > lifetime) kill();
        if (markToKill) kill();
    }
}
