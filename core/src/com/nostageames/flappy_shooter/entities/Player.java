package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nostageames.flappy_shooter.interfaces.InputHandleEntity;
import com.nostageames.flappy_shooter.interfaces.UpdatableEntity;
import com.nostageames.flappy_shooter.screens.PlayScreen;
import com.nostageames.flappy_shooter.utils.Constants;
import com.nostageames.flappy_shooter.weapons.Weapon;

/**
 * Created by nostap on 20.01.18.
 */

public class Player extends Entity implements InputHandleEntity, UpdatableEntity {
    private final int playerSize = 50;
    private final float maxVelocityX = 10;
    private final float maxVelocityY = 30;
    private final float impulseXRatio = 2;
    private final float impulseYRatio = 6;
    private Weapon weapon;


    public Player(PlayScreen screen) {

        this.world = screen.getWorld();
        weapon = new Weapon();
        definePlayer();
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(0, Constants.HEIGHT / 2 / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = Constants.BULET_BIT;
        fdef.filter.maskBits = Constants.ALL_BIT - Constants.BULET_BIT;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(playerSize / 2 / Constants.PPM, playerSize / 2 / Constants.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        shape.dispose();
    }

    @Override
    public void update(float dt) {
        if (b2body.getLinearVelocity().x >= maxVelocityX * Constants.PPM) {
            setVelocityX(maxVelocityX / Constants.PPM);
        }
        if (b2body.getLinearVelocity().y >= maxVelocityY * Constants.PPM) {
            setVelocityY(maxVelocityY / Constants.PPM);
        }
    }

    @Override
    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            b2body.setLinearVelocity(0, 0);
            b2body.applyLinearImpulse(new Vector2(impulseXRatio, impulseYRatio), b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            fire();
        }
    }

    private void fire() {
        weapon.fire(world, this);
    }
}
