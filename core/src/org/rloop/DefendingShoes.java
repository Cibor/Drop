package org.rloop;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DefendingShoes implements Items{
    public TextureRegion texture;

    public DefendingShoes(Level level){
        texture = new TextureRegion(level.game.resources.boots);
    }

    @Override
    public void pickUp(Player player) {
        player.level.game.mainScreen.itemList.add(this);
    }


    @Override
    public TextureRegion getTexture() {
        return texture;
    }
}
