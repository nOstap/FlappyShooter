package com.nostageames.flappy_shooter.weapons;

import com.nostageames.flappy_shooter.entities.Bullet;
import com.nostageames.flappy_shooter.screens.PlayScreen;

/**
 * Created by nostap on 26.01.18.
 */

public class Weapon {
    private int ammo = -1;
    private long startTime = 0;
    private int speed = 100;

    public Weapon() {
    }

    public void fire(PlayScreen game) {
        if (System.currentTimeMillis() - speed < startTime) return;
        startTime = System.currentTimeMillis();
        Bullet bullet = new Bullet(game).create(game.getPlayer().getBody().getPosition());
        decreaseAmmo();
        game.getEntities().add(bullet);
        bullet.release();
    }

    private void decreaseAmmo() {
        if (ammo == -1) return;
        ammo--;
    }

}
