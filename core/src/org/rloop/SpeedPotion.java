package org.rloop;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpeedPotion implements Items {

    public TextureRegion texture;

    float extraSpeed = 0;

    public SpeedPotion(float x, Level level){
        extraSpeed = x;
        texture = level.game.resources.speedPotion;
    }

    @Override
    public void pickUp(Player player) {
        player.statSpeed += extraSpeed;
        player.level.game.mainScreen.itemList.add(this);
    }


    @Override
    public TextureRegion getTexture() {
        return texture;
    }
}
