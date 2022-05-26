package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class OurMusic {
    public Music bgAmbient;
    public Sound dmgSound;
    public Sound monsterDmgSound;

    float SoundVolume = 1;
    float MusicVolume = 1;

    OurMusic() {
        //Musics
        bgAmbient = Gdx.audio.newMusic(Gdx.files.internal("music/ambience_1.mp3"));
        bgAmbient.setLooping(true);

        bgAmbient.play();

        //Sounds
        dmgSound = Gdx.audio.newSound(Gdx.files.internal("music/DamageSound.mp3"));
        monsterDmgSound = Gdx.audio.newSound(Gdx.files.internal("music/MonsterDmgSound.ogg"));
    }

    public void setMusicVolume(float volume) {
        MusicVolume = volume;

        bgAmbient.setVolume(MusicVolume);
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
