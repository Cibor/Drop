package org.rloop;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HpPotion implements Items{

    public TextureRegion texture;

    float extraHp = 0;

    public HpPotion(float x, Level level){
        extraHp = x;
        texture = level.game.resources.hpPotion;
    }

    @Override
    public void pickUp(Player player) {
        player.statMaxHP += extraHp;
        player.statCurrentHP = player.statMaxHP;
        player.level.game.mainScreen.itemList.add(this);
    }


    @Override
    public TextureRegion getTexture() {
        return texture;
    }
}
