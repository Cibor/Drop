package org.rloop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Items {

    void pickUp(Player player);

    TextureRegion getTexture();
}
