package com.nostageames.flappy_shooter.weapons;

import com.badlogic.gdx.physics.box2d.World;
import com.nostageames.flappy_shooter.entities.Bullet;
import com.nostageames.flappy_shooter.interfaces.HittableEntity;
import com.nostageames.flappy_shooter.entities.Player;
import com.nostageames.flappy_shooter.screens.PlayScreen;

/**
 * Created by nostap on 26.01.18.
 */

public class Weapon {
    public int ammo = -1;
    public int damage = 10;

    public Weapon() {
    }

    public void fire(PlayScreen game) {
        Bullet bullet = new Bullet(game.getWorld()).create(game.getPlayer().getBody().getPosition());
        game.getEntities().add(bullet);
        bullet.release();
    }

    private void decreaseAmmo() {
        if (ammo == -1) return;
        ammo--;
    }

}
