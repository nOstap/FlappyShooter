package com.nostageames.flappy_shooter.interfaces;

/**
 * Created by nostap on 27.01.18.
 */

public interface HurtableEntity {
    public int life = 0;
    public void decreaseLife(int damage);
}
