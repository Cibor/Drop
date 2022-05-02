package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class AudioSettingStage extends Stage {
    Stage currentStage;

    MainMenuScreen mainMenu;

    AudioSettingStage(MainMenuScreen mainMenuSuper, Skin skin, TextureRegionDrawable backScreen) {

        mainMenu = mainMenuSuper;

        currentStage = new Stage(mainMenu.viewport);

        Table tab = new Table(skin);
        tab.setFillParent(true);
        tab.background(backScreen);

        Window window = new Window("", skin);
        System.out.println(Util.monitorResolutionX(1800));
        window.setBounds(Util.monitorResolutionX(1920/2 - 900), Util.monitorResolutionX(40), Util.monitorResolutionX(1800), Util.monitorResolutionY(1000));
        window.setTouchable(null);
        window.setMovable(false);
        window.setResizable(false);

        currentStage.addActor(tab);
        currentStage.addActor(window);

        Slider MusicSli = new Slider(0, 100, 1, false, skin);
        MusicSli.setValue(100);
        MusicSli.setWidth(Util.monitorResolutionX(600));
        MusicSli.setPosition(Gdx.graphics.getWidth()/2 - MusicSli.getWidth()/2 - Util.monitorResolutionX(50), Gdx.graphics.getHeight()/2);

        Label MusicCount = new Label("100", skin, "subtitle");
        MusicCount.setPosition(MusicSli.getX() + MusicSli.getWidth() + Util.monitorResolutionX(40), MusicSli.getY());

        MusicSli.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider cur = (Slider) actor;
                for(Actor curActor: currentStage.getActors()){
                    if(curActor.getX() == cur.getX() + cur.getWidth() + Util.monitorResolutionX(40) && curActor.getY() == cur.getY()){
                        Label curLab = (Label) curActor;
                        Float curval = cur.getValue();
                        curLab.setText(curval.toString());
                    }
                }
                mainMenu.game.MusicVolume = ((Slider) actor).getValue()/100;
            }
        });

        Label MusiLab = new Label("Music", skin, "subtitle");
        Label VolumeLab = new Label("Volume", skin, "subtitle");
        Label AudioLab = new Label("Audio", skin, "title");
        MusiLab.setPosition(Gdx.graphics.getWidth()/2 - MusiLab.getWidth()/2, MusicSli.getY() + Util.monitorResolutionY(100));

        AudioLab.setPosition(Gdx.graphics.getWidth()/2 - AudioLab.getWidth()/2, MusiLab.getY() + Util.monitorResolutionY(200));

        VolumeLab.setPosition(Gdx.graphics.getWidth()/2 - VolumeLab.getWidth()/2, MusicSli.getY() - Util.monitorResolutionY(100));


        Slider VolumeSli = new Slider(0, 100, 1, false, skin);
        VolumeSli.setValue(100);
        VolumeSli.setWidth(Util.monitorResolutionX(600));
        VolumeSli.setPosition(Gdx.graphics.getWidth()/2 - VolumeSli.getWidth()/2 - Util.monitorResolutionX(50), VolumeLab.getY() - 100);

        Label VolumeCount = new Label("100", skin, "subtitle");
        VolumeCount.setPosition(VolumeSli.getX() + VolumeSli.getWidth() + Util.monitorResolutionX(40), VolumeSli.getY());

        VolumeSli.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider cur = (Slider) actor;
                for(Actor curActor: currentStage.getActors()){
                    if(curActor.getX() == cur.getX() + cur.getWidth() + Util.monitorResolutionX(40) && curActor.getY() == cur.getY()){
                        Label curLab = (Label) curActor;
                        Float curval = cur.getValue();
                        curLab.setText(curval.toString());
                    }
                }
                mainMenu.game.AudioVolume = ((Slider) actor).getValue()/100;
            }
        });


        TextButton backButAudio = new TextButton("Back", skin);
        backButAudio.setPosition(Gdx.graphics.getWidth()/2 - backButAudio.getWidth()/2, Util.monitorResolutionY(100));

        backButAudio.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainMenu.globalStage = mainMenu.settingsStage.currentStage;
                Gdx.input.setInputProcessor(mainMenu.globalStage);
            }
        });

        currentStage.addActor(backButAudio);
        currentStage.addActor(VolumeCount);
        currentStage.addActor(VolumeSli);
        currentStage.addActor(VolumeLab);
        currentStage.addActor(MusicSli);
        currentStage.addActor(AudioLab);
        currentStage.addActor(MusicCount);
        currentStage.addActor(MusiLab);
    }
}
