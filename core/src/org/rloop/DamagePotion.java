package org.rloop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.rloop.Items;
import org.rloop.Player;

public class DamagePotion implements Items {

    public TextureRegion texture;

    float extraDamage = 0;

    public DamagePotion(float x, Level level){
        extraDamage = x;
        texture = level.game.resources.damagePotion;
    }

    @Override
    public void pickUp(Player player) {
        player.playerDamage += extraDamage;
        player.level.game.mainScreen.itemList.add(this);
    }


    @Override
    public TextureRegion getTexture() {
        return texture;
    }
}
