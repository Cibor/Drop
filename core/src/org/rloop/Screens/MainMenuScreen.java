package org.rloop.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.rloop.Stages.AudioSettingStage;
import org.rloop.Stages.ChooseWeaponStage;
import org.rloop.Stages.SettingStage;
import org.rloop.Stages.StartStage;
import org.rloop.rloop;

public class MainMenuScreen extends ScreenAdapter {

    rloop game;
    public Stage globalStage;
    protected StartStage startStage;
    protected SettingStage settingsStage;
    protected AudioSettingStage audioStage;
    protected ChooseWeaponStage chooseStage;
    public Viewport viewport;

    public MainMenuScreen(final rloop game) {
        this.game = game;
        Skin skin = new Skin(Gdx.files.internal("pixthulhuui/pixthulhu-ui.json"));
        TextureRegionDrawable backScreen = new TextureRegionDrawable(new Texture(Gdx.files.internal("moun3.png")));

        viewport = new ScreenViewport();

        startStage = new StartStage(this, skin, backScreen);
        settingsStage = new SettingStage(this, skin, backScreen);
        audioStage = new AudioSettingStage(this, skin, backScreen);
        chooseStage = new ChooseWeaponStage(this, skin, backScreen);

        globalStage = startStage.currentStage;


        Gdx.input.setInputProcessor(globalStage);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        ScreenUtils.clear(0, 0.2f, 0.1f, 1);

        globalStage.act();
        globalStage.draw();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        globalStage.getViewport().update(width, height, true);
    }

    public rloop getGame() {return game;}
    public Stage getGlobalStage() {return globalStage;}

    @Override
    public void dispose() {
        super.dispose();
        globalStage.dispose();
        startStage.dispose();
        settingsStage.dispose();
        audioStage.dispose();
    }
}
