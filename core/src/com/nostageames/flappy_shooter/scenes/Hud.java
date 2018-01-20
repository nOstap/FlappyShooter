package com.nostageames.flappy_shooter.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nostageames.flappy_shooter.utils.Constants;

/**
 * Created by nostap on 20.01.18.
 */

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer distance;
    private Integer score;

    Label distanceLabel;
    Label scoreLabel;
    Label levelLabel;

    public Hud(SpriteBatch sb) {
        distance = 0;
        score = 0;

        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_WIDTH, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        distanceLabel = new Label(String.format("%d", distance), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("Level", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(distanceLabel). expandX().padTop(10);
        table.add(scoreLabel). expandX().padTop(10);

        stage.addActor(table);
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
