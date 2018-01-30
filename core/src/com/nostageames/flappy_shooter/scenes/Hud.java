package com.nostageames.flappy_shooter.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nostageames.flappy_shooter.screens.PlayScreen;
import com.nostageames.flappy_shooter.utils.Constants;

/**
 * Created by nostap on 20.01.18.
 */

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Float distance;
    private Integer score;
    private Integer level;

    Label distanceLabel;
    Label scoreLabel;
    Label levelLabel;

    public Hud(SpriteBatch sb) {
        distance = 0f;
        score = 0;
        level = 1;

        viewport = new FitViewport(Constants.WIDTH , Constants.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        distanceLabel = new Label(
                String.format("Distance: %6.1f", distance),
                new Label.LabelStyle(font, new Color(0xffffffA0)));
        scoreLabel = new Label(
                String.format("Score: %06d", score),
                new Label.LabelStyle(font, new Color(0xffffffA0)));
        levelLabel = new Label(
                String.format("Level: %3d", level),
                new Label.LabelStyle(font, new Color(0xffffffA0)));

        table.add(distanceLabel). expandX().padTop(15);
        table.add(scoreLabel). expandX().padTop(15);
        table.add(levelLabel). expandX().padTop(15);

        stage.addActor(table);
    }

    public <T> void update(float dt, T screen) {
        if(screen instanceof PlayScreen) {
            distance =((PlayScreen) screen).getDistance();
            score = ((PlayScreen) screen).getScore();
            distanceLabel.setText(String.format("Distance: %6.1f", distance));
            scoreLabel.setText(String.format("Score: %06d", score));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
