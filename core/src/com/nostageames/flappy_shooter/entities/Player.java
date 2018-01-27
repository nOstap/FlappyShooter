package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nostageames.flappy_shooter.interfaces.InputHandleEntity;
import com.nostageames.flappy_shooter.interfaces.Updatable;
import com.nostageames.flappy_shooter.screens.PlayScreen;
import com.nostageames.flappy_shooter.utils.Constants;
import com.nostageames.flappy_shooter.weapons.Weapon;

import static com.nostageames.flappy_shooter.utils.Constants.PPM;

/**
 * Created by nostap on 20.01.18.
 */

public class Player extends Entity implements InputHandleEntity, Updatable {

    public final static float IMPULSE_X_RATIO = 2;
    public final static float IMPULSE_Y_RATIO = 6;
    private final static int PLAYER_SIZE = 50;

    private final float maxVelocityX = 10;
    private final float maxVelocityY = 30;
    private boolean isDied = false;
    private int points = 0;
    private Weapon weapon;
    private PlayScreen game;

    public Player(PlayScreen game) {
        this.game = game;
        weapon = new Weapon();
        definePlayer();
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(0, Constants.HEIGHT / 2 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = game.getWorld().createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = Constants.BULET_BIT;
        fdef.filter.maskBits = Constants.ALL_BIT - Constants.BULET_BIT;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(PLAYER_SIZE / 2 / PPM, PLAYER_SIZE / 2 / PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        shape.dispose();
    }

    @Override
    public void update(float dt) {
        if (b2body.getLinearVelocity().x >= maxVelocityX * PPM) {
            setVelocityX(maxVelocityX / PPM);
        }
        if (b2body.getLinearVelocity().y >= maxVelocityY * PPM) {
            setVelocityY(maxVelocityY / PPM);
        }
        if (this.checkIfVisible()) {
            System.out.println("NOT VISIBLE");
            die();
        }
    }

    private boolean checkIfVisible() {
        return (b2body.getPosition().y * PPM > Constants.HEIGHT ||
                b2body.getPosition().y * PPM < 0 ||
                b2body.getPosition().x < game.getCamera().position.x);
    }

    @Override
    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            b2body.setLinearVelocity(0, 0);
            b2body.applyLinearImpulse(new Vector2(IMPULSE_X_RATIO, IMPULSE_Y_RATIO), b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            fire();
        }
    }

    public void die() {
        isDied = true;
    }

    private void fire() {
        weapon.fire(game.getWorld(), this);
    }
}
