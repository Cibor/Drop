package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import org.rloop.Tiles.Wall;

//@author Marcin Kozik, Andrei Daletski

public class GameContactListener implements ContactListener {



    rloop game;
    GameContactListener(rloop game){
        this.game = game;

    }
    @Override
    public void beginContact(Contact contact) {
        System.out.println(3);
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa == null || fb == null){
            return;
        }
        if(fa.getUserData() == null || fb.getUserData() == null){
            return;
        }
        if((fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == ChasingMonster.class) || (fb.getUserData().getClass() == RangeWeaponProjectile.class && fa.getUserData().getClass() == ChasingMonster.class)) {
            RangeWeaponProjectile curProjectile;
            ChasingMonster curMonster;
            if (fb.getUserData().getClass() == RangeWeaponProjectile.class) {
                curProjectile = (RangeWeaponProjectile) fb.getUserData();
                curMonster = (ChasingMonster) fa.getUserData();
            } else {
                curProjectile = (RangeWeaponProjectile) fa.getUserData();
                curMonster = (ChasingMonster) fb.getUserData();
            }
            if (!curMonster.isImmune()) {
                curMonster.getHit(curProjectile.damageMonst);
                curMonster.makeImmune();
                //System.out.println(curProjectile.damageMonst);
                //Gdx.audio.newSound(Gdx.files.internal("music/DamageSound.mp3")).play(game.GlobalAudioSound);
                game.mainScreen.monstersDied.add(curProjectile);
            }
            else{
                game.mainScreen.monstersDied.add(curProjectile);
            }
        }
        else if((fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == ShootingMonster.class) || (fb.getUserData().getClass() == RangeWeaponProjectile.class && fa.getUserData().getClass() == ShootingMonster.class)) {
            RangeWeaponProjectile curProjectile;
            ShootingMonster curMonster;
            if (fb.getUserData().getClass() == RangeWeaponProjectile.class) {
                curProjectile = (RangeWeaponProjectile) fb.getUserData();
                curMonster = (ShootingMonster) fa.getUserData();
            } else {
                curProjectile = (RangeWeaponProjectile) fa.getUserData();
                curMonster = (ShootingMonster) fb.getUserData();
            }
            if (!curMonster.isImmune()) {
                curMonster.getHit(curProjectile.damageMonst);
                curMonster.makeImmune();
                game.mainScreen.monstersDied.add(curProjectile);
            }
            else{
                game.mainScreen.monstersDied.add(curProjectile);
            }
        }
        else if((fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == Wall.class) || (fb.getUserData().getClass() == RangeWeaponProjectile.class && fa.getUserData().getClass() == Wall.class)) {
            RangeWeaponProjectile curProjectile;
            if (fb.getUserData().getClass() == RangeWeaponProjectile.class) {
                curProjectile = (RangeWeaponProjectile) fb.getUserData();
            } else {
                curProjectile = (RangeWeaponProjectile) fa.getUserData();
            }
            game.mainScreen.monstersDied.add(curProjectile);
        }
        System.out.println(4);
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa == null || fb == null){
            return;
        }
        if(fa.getUserData() == null || fb.getUserData() == null){
            return;
        }
        if((fa.getUserData().getClass() == ShootingMonsterProjectile.class && Monster.class.isAssignableFrom(fb.getUserData().getClass())) || (fb.getUserData().getClass() == ShootingMonsterProjectile.class && Monster.class.isAssignableFrom(fa.getUserData().getClass())) ){
            contact.setEnabled(false);
        }
        else if ((fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == Player.class) || (fb.getUserData().getClass() == RangeWeaponProjectile.class && fa.getUserData().getClass() == Player.class)){
            contact.setEnabled(false);
        }
        else if ((fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == RangeWeaponProjectile.class)){
            contact.setEnabled(false);
        }
        else if ((fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == ShootingMonsterProjectile.class) || (fb.getUserData().getClass() == RangeWeaponProjectile.class && fa.getUserData().getClass() == ShootingMonsterProjectile.class)){
            contact.setEnabled(false);
        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}