package org.rloop;

import com.badlogic.gdx.graphics.Texture;
import org.rloop.Items;
import org.rloop.Player;

public class DamagePotion implements Items {

    Texture texture;

    float extraDamage = 0;

    public DamagePotion(float x, Level level){
        extraDamage = x;
        texture = level.game.resources.damagePotion.getTexture();
    }

    @Override
    public void pickUp(Player player) {
        player.playerDamage += extraDamage;
        player.level.game.mainScreen.itemList.add(this);
    }

    @Override
    public void render() {

    }
}
