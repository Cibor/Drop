package org.rloop.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.rloop.Screens.GameScreen;
import org.rloop.Util;

public class GameStage extends Stage {
    Stage currentStage;
    public Stage getCurrentStage() {return currentStage;}
    ProgressBar healthBar;
    Window statWindow;

    public GameStage(final GameScreen gameScreenSuper, Skin skin) {
        currentStage = new Stage(new ScreenViewport());

        healthBar = new ProgressBar(0.0f, 1f, 0.01f, false, skin );
        healthBar.setValue(gameScreenSuper.getPlayer().getCurrentHP()/gameScreenSuper.getPlayer().getMaxHP());
        healthBar.setPosition(Util.monitorResolutionX(30), Util.monitorResolutionY(1040 - (int)healthBar.getHeight()));
        healthBar.setColor(1f, 0f, 0f, 1f);
        healthBar.setWidth(Util.monitorResolutionX(300));
        healthBar.setHeight(Util.monitorResolutionY(50));
        currentStage.addActor(healthBar);

        statWindow = new Window("", skin);
        statWindow.setResizable(false);
        statWindow.setBounds(Util.monitorResolutionX(30), Util.monitorResolutionY(1040 - (int)healthBar.getHeight() - 420), Util.monitorResolutionX(300), Util.monitorResolutionY(400));
        currentStage.addActor(statWindow);

        Label statHeader = new Label("Stats", skin);
        statHeader.setWidth(Util.monitorResolutionX((int)statHeader.getWidth()));
        statHeader.setHeight(Util.monitorResolutionY((int)statHeader.getHeight()));
        statHeader.setFontScale(Gdx.graphics.getWidth() * 1.0f/1920, Gdx.graphics.getHeight() * 1.0f/1080);
        statHeader.setPosition(Util.monitorResolutionX((int) (30 + 300/ 2 - statHeader.getWidth()/2)), Util.monitorResolutionY((int) (1040 - (int)healthBar.getHeight() - 40 - statHeader.getHeight())));
        currentStage.addActor(statHeader);

        Label hpHeader = new Label("HP", skin);
        hpHeader.setColor(1f,1f,1f,1f);
        hpHeader.setFontScale(Gdx.graphics.getWidth() * 1.0f/1920, Gdx.graphics.getHeight() * 1.0f/1080);
        hpHeader.setWidth(Util.monitorResolutionX((int)hpHeader.getWidth()));
        hpHeader.setHeight(Util.monitorResolutionY((int)hpHeader.getHeight()));
        hpHeader.setPosition(Util.monitorResolutionX((int) (30 + healthBar.getWidth()/2 - hpHeader.getWidth()/2)) , Util.monitorResolutionY((int) (1040 - (int)healthBar.getHeight() + hpHeader.getHeight()/2)));
        currentStage.addActor(hpHeader);
    }

    public void update(final GameScreen gameScreenSuper){
        healthBar.setValue(gameScreenSuper.getPlayer().getCurrentHP()/gameScreenSuper.getPlayer().getMaxHP());
    }
}