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

    AudioSettingStage(MainMenuScreen mainMenuSuper, final Skin skin, final TextureRegionDrawable backScreen) {

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
        MusicSli.setValue(mainMenu.game.getOurMusic().getMusicVolume() * 100);
        MusicSli.setWidth(Util.monitorResolutionX(600));
        MusicSli.setPosition(Gdx.graphics.getWidth()/2 - MusicSli.getWidth()/2 - Util.monitorResolutionX(50), Gdx.graphics.getHeight()/2);

        Label MusicCount = new Label(((Float)(mainMenu.game.getOurMusic().getMusicVolume() * 100)).toString(), skin, "subtitle");
        MusicCount.setPosition(MusicSli.getX() + MusicSli.getWidth() + Util.monitorResolutionX(40), MusicSli.getY());

        MusicSli.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider cur = (Slider) actor;
                for(Actor curActor: currentStage.getActors()){
                    if(curActor.getX() == cur.getX() + cur.getWidth() + Util.monitorResolutionX(40) && curActor.getY() == cur.getY()){
                        Label curLab = (Label) curActor;
                        float curval = cur.getValue();
                        curLab.setText(Float.toString(curval));
                    }
                }
                mainMenu.game.getOurMusic().setMusicVolume(((Slider) actor).getValue()/100);
            }
        });

        Label MusiLab = new Label("Music", skin, "subtitle");
        Label VolumeLab = new Label("Volume", skin, "subtitle");
        Label SoundLab = new Label("Sound", skin, "title");
        MusiLab.setPosition(Gdx.graphics.getWidth()/2 - MusiLab.getWidth()/2, MusicSli.getY() + Util.monitorResolutionY(100));

        SoundLab.setPosition(Gdx.graphics.getWidth()/2 - SoundLab.getWidth()/2, MusiLab.getY() + Util.monitorResolutionY(200));

        VolumeLab.setPosition(Gdx.graphics.getWidth()/2 - VolumeLab.getWidth()/2, MusicSli.getY() - Util.monitorResolutionY(100));


        Slider VolumeSli = new Slider(0, 100, 1, false, skin);
        VolumeSli.setValue(mainMenu.game.getOurMusic().getSoundVolume() * 100);
        VolumeSli.setWidth(Util.monitorResolutionX(600));
        VolumeSli.setPosition(Gdx.graphics.getWidth()/2 - VolumeSli.getWidth()/2 - Util.monitorResolutionX(50), VolumeLab.getY() - 100);

        Label VolumeCount = new Label(((Float)(mainMenu.game.getOurMusic().getSoundVolume() * 100)).toString(), skin, "subtitle");
        VolumeCount.setPosition(VolumeSli.getX() + VolumeSli.getWidth() + Util.monitorResolutionX(40), VolumeSli.getY());

        VolumeSli.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider cur = (Slider) actor;
                for(Actor curActor: currentStage.getActors()){
                    if(curActor.getX() == cur.getX() + cur.getWidth() + Util.monitorResolutionX(40) && curActor.getY() == cur.getY()){
                        Label curLab = (Label) curActor;
                        float curval = cur.getValue();
                        curLab.setText(Float.toString(curval));
                    }
                }
                mainMenu.game.getOurMusic().setSoundVolume(((Slider) actor).getValue()/100);
            }
        });


        TextButton backButSound = new TextButton("Back", skin);
        backButSound.setPosition(Gdx.graphics.getWidth()/2 - backButSound.getWidth()/2, Util.monitorResolutionY(100));

        backButSound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainMenu.globalStage.dispose();
                mainMenu.globalStage = new SettingStage(mainMenu, skin, backScreen).currentStage;
                Gdx.input.setInputProcessor(mainMenu.globalStage);
            }
        });

        currentStage.addActor(backButSound);
        currentStage.addActor(VolumeCount);
        currentStage.addActor(VolumeSli);
        currentStage.addActor(VolumeLab);
        currentStage.addActor(MusicSli);
        currentStage.addActor(SoundLab);
        currentStage.addActor(MusicCount);
        currentStage.addActor(MusiLab);
    }
}
