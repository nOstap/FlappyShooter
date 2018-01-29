package com.nostageames.flappy_shooter.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.nostageames.flappy_shooter.screens.PlayScreen;

/**
 * Created by nostap on 26.01.18.
 */

public abstract class Entity extends Sprite implements Disposable {

    public enum EntityType {
        PLAYER,
        OBSTACLE,
        BULLET,
        ENEMY
    }

    protected Body b2body;
    protected PlayScreen game;
    protected long startTime = System.currentTimeMillis();

    public EntityType entityType;
    public boolean isKilled = false;
    public boolean markToKill = false;

    public Body getBody() {
        return b2body;
    }

    void setVelocityX(float x) {
        b2body.setLinearVelocity(x, b2body.getLinearVelocity().y);
    }

    void setVelocityY(float y) {
        b2body.setLinearVelocity(b2body.getLinearVelocity().x, y);
    }

    @Override
    public void dispose() {
        isKilled = true;
        if (!game.getWorld().isLocked()) {
            if (b2body != null) {
                game.getWorld().destroyBody(b2body);
            }
        }
    }

    public void kill() {
        markToKill = true;
    }

}
