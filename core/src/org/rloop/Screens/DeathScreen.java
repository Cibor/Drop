package org.rloop.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.rloop.Stages.DeathStage;
import org.rloop.rloop;

public class DeathScreen extends ScreenAdapter {

    final rloop game;
    Camera camera;
    Viewport viewport;
    DeathStage deathStage;

    static Skin globalSkin = new Skin(Gdx.files.internal("pixthulhuui/pixthulhu-ui.json"));

    public DeathScreen(rloop game){
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport();
        deathStage = new DeathStage(this, globalSkin);
    }
    @Override
    public void render(float x){
        ScreenUtils.clear(0, 0, 0, 1);

        deathStage.getCurrentStage().act();
        deathStage.getCurrentStage().draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            game.setScreen(new MainMenuScreen(game));
        }
    }

}
