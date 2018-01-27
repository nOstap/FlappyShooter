package com.nostageames.flappy_shooter.handlers;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.nostageames.flappy_shooter.entities.Player;
import com.nostageames.flappy_shooter.interfaces.CanDealDamage;
import com.nostageames.flappy_shooter.interfaces.CanKillPlayer;
import com.nostageames.flappy_shooter.interfaces.HittableEntity;

/**
 * Created by nostap on 27.01.18.
 */

public class CollisionHandler implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getUserData();
        Object b = contact.getFixtureB().getUserData();

        // DAMAGE COLLISIONS
        if (a instanceof CanDealDamage || b instanceof CanDealDamage) {
            CanDealDamage dealer = (CanDealDamage) ((a instanceof CanDealDamage) ? a : b);
            Object receiver = dealer == a ? b : a;
            if (receiver instanceof HittableEntity) {
                dealer.dealDamage((HittableEntity) receiver);
            }
        }
        if (a instanceof CanKillPlayer || b instanceof CanKillPlayer) {
            CanKillPlayer killer = (CanKillPlayer) ((a instanceof CanKillPlayer) ? a : b);
            Object player = killer == a ? b : a;
            if (player instanceof Player) {
                ((Player) player).die();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
