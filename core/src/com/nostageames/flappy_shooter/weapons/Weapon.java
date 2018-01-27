package com.nostageames.flappy_shooter.weapons;

import com.badlogic.gdx.physics.box2d.World;
import com.nostageames.flappy_shooter.entities.Bulet;
import com.nostageames.flappy_shooter.interfaces.HurtableEntity;
import com.nostageames.flappy_shooter.entities.Player;

/**
 * Created by nostap on 26.01.18.
 */

public class Weapon {
    public int ammo = -1;
    public int damage = 10;

    public Weapon() {
    }

    public void fire(World world, Player player) {
        new Bulet(world).create(player.getBody().getPosition()).release();
    }

    private void decreaseAmmo() {
        if (ammo == -1) return;
        ammo--;
    }

    private void dealDamage(HurtableEntity entity) {
        entity.decreaseLife(damage);
    }
}
