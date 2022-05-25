package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import org.rloop.Tiles.Wall;

//@author Marcin Kozik, Andrei Daletski

public class GameContactListener implements ContactListener {



    rloop game;
    public GameContactListener(rloop game){
        this.game = game;

    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        //обработать хуйню
        if(fa == null || fb == null){
            return;
        }
        if(fa.getUserData() == null || fb.getUserData() == null){
            return;
        }

        //Урон монстрам от проджектайла
        if(     (fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == ChasingMonster.class)
                || (fb.getUserData().getClass() == RangeWeaponProjectile.class && fa.getUserData().getClass() == ChasingMonster.class)) {
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

        } else if (((fa.getUserData() instanceof ShootingMonsterProjectile && fb.getUserData() instanceof Wall)
                || (fb.getUserData() instanceof ShootingMonsterProjectile && fa.getUserData() instanceof Wall))) {
                Wall curWall;
                ShootingMonsterProjectile curMonster;
                if(fb.getUserData().getClass() == Wall.class) {
                    curWall = (Wall) fb.getUserData();
                    curMonster = (ShootingMonsterProjectile) fa.getUserData();
                }
                else{
                    curWall = (Wall) fa.getUserData();
                    curMonster = (ShootingMonsterProjectile) fb.getUserData();
                }

//                curMonster.getBody().getWorld().destroyBody(curMonster.getBody());
                game.mainScreen.monstersDied.add(curMonster);
        } else if ((fa.getUserData() instanceof Player && fb.getUserData() instanceof DamageMaker)
                || (fb.getUserData() instanceof Player && fa.getUserData() instanceof DamageMaker)) {
            Player player;
            DamageMaker damageMaker;
            if (fa.getUserData() instanceof Player) {
                player = (Player) fa.getUserData();
                damageMaker = (DamageMaker) fb.getUserData();
            } else {
                player = (Player) fb.getUserData();
                damageMaker = (DamageMaker) fa.getUserData();
            }
            player.addDamageMaker(damageMaker);
            if(damageMaker instanceof ShootingMonsterProjectile && ! (damageMaker instanceof RangeWeaponProjectile)){
                game.mainScreen.monstersDied.add((Monster) damageMaker);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        //обработать хуйню
        if(fa == null || fb == null){
            return;
        }
        if(fa.getUserData() == null || fb.getUserData() == null){
            return;
        }

        if (fa.getUserData() instanceof Player && fb.getUserData() instanceof DamageMaker || fb.getUserData() instanceof Player && fa.getUserData() instanceof DamageMaker) {
            Player player;
            DamageMaker damageMaker;
            if (fa.getUserData() instanceof Player) {
                player = (Player) fa.getUserData();
                damageMaker = (DamageMaker) fb.getUserData();
            } else {
                player = (Player) fb.getUserData();
                damageMaker = (DamageMaker) fa.getUserData();
            }
            player.removeDamageMaker(damageMaker);
        }
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