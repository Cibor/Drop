package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameStage extends Stage {
    Stage currentStage;
    ProgressBar healthBar;
    Window statWindow;

    GameStage(final GameScreen gameScreenSuper, Skin skin) {
        currentStage = new Stage(new ScreenViewport());

        healthBar = new ProgressBar(0.0f, 1f, 0.01f, false, skin );
        healthBar.setValue(gameScreenSuper.player.statCurrentHP/gameScreenSuper.player.statMaxHP);
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

    protected void update(final GameScreen gameScreenSuper){
        healthBar.setValue(gameScreenSuper.player.statCurrentHP/gameScreenSuper.player.statMaxHP);
    }
}