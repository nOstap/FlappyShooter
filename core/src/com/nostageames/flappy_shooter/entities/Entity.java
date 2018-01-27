package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.nostageames.flappy_shooter.interfaces.Killable;
import com.nostageames.flappy_shooter.screens.PlayScreen;

/**
 * Created by nostap on 26.01.18.
 */

public abstract class Entity extends Sprite implements Disposable, Killable {

    public enum EntityType {
        PLAYER,
        OBSTACLE,
        BULLET,
        ENEMY
    }

    protected Body b2body;
    protected World world;
    protected PlayScreen game;
    protected long startTime = System.currentTimeMillis();

    public EntityType entityType;
    public boolean isKilled = false;
    public boolean markToKill = false;

    public Body getBody() {
        return b2body;
    }

    public World getWorld() {
        return world;
    }

    void setVelocityX(float x) {
        b2body.setLinearVelocity(x, b2body.getLinearVelocity().y);
    }

    void setVelocityY(float y) {
        b2body.setLinearVelocity(b2body.getLinearVelocity().x, y);
    }

    @Override
    public void kill() {
        isKilled = true;
        dispose();
    }

    @Override
    public void dispose() {
        if (!world.isLocked()) {
            if (b2body != null) {
                world.destroyBody(b2body);
            }
        }
    }

}
