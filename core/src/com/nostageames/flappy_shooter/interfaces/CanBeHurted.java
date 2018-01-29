package com.nostageames.flappy_shooter.interfaces;

/**
 * Created by nostap on 27.01.18.
 */

public interface CanBeHurted {
    void decreaseLife(int damage);
    int getHitScore();
    int getLife();
    int getKillScore();
    void kill();
}
