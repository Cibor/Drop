package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen extends ScreenAdapter {

    Game game;
    OrthographicCamera camera;
    protected Stage stage;

    public MainMenuScreen(final Game game) {


        Skin skin = new Skin(Gdx.files.internal("pixthulhuui/pixthulhu-ui.json"));

        TextButton firstLvl = new TextButton("Test level 1", skin);
        TextButton SecondLvl = new TextButton("Fullscreen", skin);
        TextButton ThirdLvl = new TextButton("Exit", skin);

        Table table = new Table(skin);

        SecondLvl.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (Gdx.graphics.isFullscreen()){
                    Gdx.graphics.setWindowedMode(800, 480);
                }
                else{
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode(Gdx.graphics.getMonitor()));
                }
            }
        });

        ThirdLvl.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });


        table.setFillParent(true);

        table.defaults().space(10);
        table.padBottom(120);
        table.bottom();

        Label gameName = new Label("RLOOP", skin, "title", new Color(0,0.5f,0.43f,1));
        table.defaults().space(200);
        table.add(gameName);table.row();

        table.defaults().space(10);
        table.add(firstLvl); table.row();
        table.add(SecondLvl);table.row();
        table.defaults().bottom();

        table.background(new TextureRegionDrawable(new Texture(Gdx.files.internal("moun3.png"))));

        table.add(ThirdLvl);

        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
//        stage = new Stage(new ScreenViewport());
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        ScreenUtils.clear(0, 0.2f, 0.1f, 1);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
