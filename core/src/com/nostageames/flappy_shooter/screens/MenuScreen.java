package com.nostageames.flappy_shooter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.nostageames.flappy_shooter.FlappyShooter;

/**
 * Created by nostap on 20.01.18.
 */

public class MenuScreen implements Screen {
    private FlappyShooter game;

    public MenuScreen() {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        // play screen render
        game.batch.end();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
