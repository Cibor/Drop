package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class OurMusic {
    Music bgAmbient;

    float SoundVolume = 1;
    float MusicVolume = 1;

    OurMusic() {
        //bgAmbient = Gdx.audio.newMusic(Gdx.files.internal("music/ambience_1.wav"));
    }

    public void setMusicVolume(float volume) {
        MusicVolume = volume;

        //bgAmbient.setVolume(MusicVolume);
    }

    public void setSoundVolume(float volume) {
        SoundVolume = volume;
    }

    public float getMusicVolume() {
        return MusicVolume;
    }

    public float getSoundVolume() {
        return SoundVolume;
    }

    public void dispose() {
        bgAmbient.dispose();
    }
}
