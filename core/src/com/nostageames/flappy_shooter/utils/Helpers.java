package com.nostageames.flappy_shooter.utils;

import com.badlogic.gdx.math.RandomXS128;

/**
 * Created by nostap on 27.01.18.
 */

public class Helpers {

    public static final long seed = System.currentTimeMillis();
    public static final RandomXS128 random = new RandomXS128(seed);

    public static final int nextRandom(int from, int to) {
        return random.nextInt(to - from) + from;
    }

    public static final int nextRandom(float from, float to) {
        return nextRandom((int) from, (int) to);
    }

    public static final class Bounds {
        public final float left;
        public final float right;
        public final float top;
        public final float bottom;

        public Bounds(float left, float right, float top, float bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }
    }
}
