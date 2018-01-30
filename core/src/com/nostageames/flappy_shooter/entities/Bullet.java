package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.nostageames.flappy_shooter.interfaces.CanDealDamage;
import com.nostageames.flappy_shooter.interfaces.CanBeHurted;
import com.nostageames.flappy_shooter.interfaces.Updatable;
import com.nostageames.flappy_shooter.screens.PlayScreen;
import com.nostageames.flappy_shooter.utils.Constants;

/**
 * Created by nostap on 20.01.18.
 */

public class Bullet extends Entity implements Updatable, CanDealDamage {
    private int damage = 10;
    private int speed = 4;
    private int radius = 3;
    private float lifetime = 3000;

    public Bullet(PlayScreen game) {
        this.game = game;
        entityType = EntityType.BULLET;
    }

    public Bullet create(Vector2 position) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyType.DynamicBody;
        bdef.bullet = true;
        b2body = game.getWorld().createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        fdef.restitution = 0.1f;
        fdef.filter.categoryBits = Constants.BULLET_BIT;
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / Constants.PPM);
        fdef.shape = shape;
        fdef.density = 100;
        Fixture fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
        shape.dispose();
        return this;
    }

    public void release() {
        b2body.applyLinearImpulse(new Vector2(speed, 0), b2body.getWorldCenter(), false);
    }

    @Override
    public void dealDamage(CanBeHurted entity) {
        entity.decreaseLife(damage);
        if (entity.getLife() == 0) {
            game.updateScore(entity.getKillScore());
            entity.kill();
        } else {
            System.out.println(entity.getHitScore());
            game.updateScore(entity.getHitScore());
        }
        kill();
    }

    @Override
    public void update(float dt) {
        if (System.currentTimeMillis() - startTime > lifetime) dispose();
        if (markToKill) dispose();
    }

}
