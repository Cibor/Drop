package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StartStage extends Stage {
    Stage currentStage;

    Table tableStart;

    MainMenuScreen mainMenu;
    StartStage(MainMenuScreen mainMenuSuper, final Skin skin, final TextureRegionDrawable backScreen){
        mainMenu = mainMenuSuper;

        currentStage = new Stage(mainMenu.viewport);


        TextButton StartGameBut = new TextButton("Start", skin);
        TextButton SettingsBut = new TextButton("Settings", skin);

        SettingsBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainMenu.globalStage.dispose();
                mainMenu.globalStage = new SettingStage(mainMenu, skin, backScreen).currentStage;
                Gdx.input.setInputProcessor(mainMenu.globalStage);
            }
        });

        StartGameBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainMenu.dispose();
                mainMenu.game.setScreen(new GameScreen(mainMenu.game));
            }
        });

        TextButton ExitBut = new TextButton("Exit", skin);

        ExitBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        tableStart = new Table(skin);
        tableStart.setTransform(true);

        tableStart.setFillParent(true);
        tableStart.defaults().space(Util.monitorResolutionY(10));

        Label gameName = new Label("RLOOP", skin, "title", new Color(0,0.5f,0.43f,1));
        tableStart.defaults().space(Util.monitorResolutionY(150));
        tableStart.add(gameName);tableStart.row();
        tableStart.defaults().space(Util.monitorResolutionY(10));

        tableStart.add(StartGameBut); tableStart.row();
        tableStart.add(SettingsBut);tableStart.row();
        tableStart.add(ExitBut);
        tableStart.background(backScreen);

        currentStage.addActor(tableStart);

    }
}
