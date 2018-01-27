package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nostageames.flappy_shooter.screens.PlayScreen;
import com.nostageames.flappy_shooter.utils.Constants;

/**
 * Created by nostap on 20.01.18.
 */

public class Enemy extends Entity {

    public static final int MAX_WIDTH = 30;
    public static final int MAX_HEIGHT = 30;

    public World world;
    public Body b2body;

    public Enemy(PlayScreen screen) {
        this.world = screen.getWorld();
        defineEnamy();
    }

    public void defineEnamy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(0, Constants.HEIGHT / 2 / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(25 / Constants.PPM, 25 / Constants.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        shape.dispose();
    }

}
