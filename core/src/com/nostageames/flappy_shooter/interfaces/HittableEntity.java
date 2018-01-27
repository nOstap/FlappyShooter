package com.nostageames.flappy_shooter.interfaces;

/**
 * Created by nostap on 27.01.18.
 */

public interface HittableEntity {
    void decreaseLife(int damage);
    int getLife();
}
