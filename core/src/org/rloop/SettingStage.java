package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingStage extends Stage {
    Stage currentStage;
    Table tableSettings;

    MainMenuScreen mainMenu;
    SettingStage(MainMenuScreen mainMenuSuper, Skin skin, TextureRegionDrawable backScreen) {
        mainMenu =  mainMenuSuper;

        tableSettings = new Table(skin);

        tableSettings.setFillParent(true);

        currentStage = new Stage(mainMenu.viewport);

        TextButton BackBut = new TextButton("Back", skin);

        BackBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainMenu.globalStage = mainMenu.startStage.currentStage;
                Gdx.input.setInputProcessor(mainMenu.globalStage);
            }
        });

        TextButton FullscreenBut = new TextButton("Fullscreen", skin);

        FullscreenBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (Gdx.graphics.isFullscreen()){
                    Gdx.graphics.setWindowedMode(1280, 720);
                    mainMenu.globalStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                }
                else{
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode(Gdx.graphics.getMonitor()));
                    mainMenu.globalStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                }
            }
        });

        TextButton AudioBut = new TextButton("Audio", skin);

        AudioBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainMenu.globalStage = mainMenu.audioStage.currentStage;
                Gdx.input.setInputProcessor(mainMenu.globalStage);
            }
        });

        tableSettings.defaults().space(Util.monitorResolutionY(10));

        Label settingsName = new Label("Settings", skin, "title", new Color(0,0.5f,0.43f,1));
        tableSettings.defaults().space(Util.monitorResolutionY(150));

        tableSettings.add(settingsName);tableSettings.row();
        tableSettings.defaults().space(Util.monitorResolutionY(10));
        tableSettings.add(FullscreenBut);tableSettings.row();
        tableSettings.add(AudioBut);tableSettings.row();
        tableSettings.add(BackBut);
        tableSettings.background(backScreen);

        currentStage.addActor(tableSettings);
    }
}
