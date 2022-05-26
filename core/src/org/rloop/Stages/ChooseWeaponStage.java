package org.rloop.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.rloop.Screens.GameScreen;
import org.rloop.Screens.MainMenuScreen;
import org.rloop.Util;

public class ChooseWeaponStage {
    Stage currentStage;
    public Stage getCurrentStage() {return currentStage;}
    ProgressBar healthBar;
    Window statWindow;

    public ChooseWeaponStage(final MainMenuScreen mainMenu, Skin skin, final TextureRegionDrawable backScreen) {
        currentStage = new Stage(new ScreenViewport());

        Table tab = new Table(skin);
        tab.setFillParent(true);
        tab.background(backScreen);
        currentStage.addActor(tab);

        Window chooseWindow = new Window("", skin);
        chooseWindow.setBounds(Util.monitorResolutionX(20), Util.monitorResolutionY(20), Util.monitorResolutionX(1880), Util.monitorResolutionY(1040));
        chooseWindow.setResizable(false);

        currentStage.addActor(chooseWindow);

        Label choose = new Label("Choose your weapon", skin, "title");
        choose.setWidth(Util.monitorResolutionX((int)choose.getWidth()));
        choose.setHeight(Util.monitorResolutionY((int)choose.getHeight()));
        choose.setFontScale(Gdx.graphics.getWidth() * 1.0f/1920, Gdx.graphics.getHeight() * 1.0f/1080);
        choose.setPosition(Gdx.graphics.getWidth() / 2f - choose.getWidth() / 2, Util.monitorResolutionY(800));
        currentStage.addActor(choose);

        TextureRegionDrawable ret = new TextureRegionDrawable(mainMenu.getGame().resources.mainSword);

        ret.setMinSize(Util.monitorResolutionX(250), Util.monitorResolutionY(300));

        ImageButton magickButton = new ImageButton(ret);
        magickButton.setBounds(Util.monitorResolutionX(600), Gdx.graphics.getHeight()/2f - magickButton.getHeight()/2, Util.monitorResolutionX(250), Util.monitorResolutionY(300));

        magickButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainMenu.dispose();
                mainMenu.getGame().setScreen(mainMenu.getGame().mainScreen = new GameScreen(mainMenu.getGame(), true));
            }
        });
        currentStage.addActor(magickButton);

        TextureRegionDrawable ret2 = new TextureRegionDrawable(mainMenu.getGame().resources.magickBook);

        ret2.setMinSize(Util.monitorResolutionX(250), Util.monitorResolutionY(300));

        ImageButton swordButton = new ImageButton(ret2);
        swordButton.setBounds(Util.monitorResolutionX(1920 - 850), Gdx.graphics.getHeight()/2f - swordButton.getHeight()/2, Util.monitorResolutionX(250), Util.monitorResolutionY(300));

        swordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainMenu.dispose();
                mainMenu.getGame().setScreen(mainMenu.getGame().mainScreen = new GameScreen(mainMenu.getGame(), false));
            }
        });

        currentStage.addActor(swordButton);


    }
}


