package org.rloop;

import com.badlogic.gdx.physics.box2d.*;

//@author Marcin Kozik, Andrei Daletski

public class GameContactListener implements ContactListener {



    rloop game;
    GameContactListener(rloop game){
        this.game = game;

    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa == null || fb == null){
            return;
        }
        if(fa.getUserData() == null || fb.getUserData() == null){
            return;
        }
        if((fa.getUserData().getClass() == Player.class && fb.getUserData().getClass() == ChasingMonster.class) || (fb.getUserData().getClass() == Player.class && fa.getUserData().getClass() == ChasingMonster.class)) {
            Player curPlayer;
            ChasingMonster curMonster;
            if(fb.getUserData().getClass() == Player.class) {
                curPlayer = (Player) fb.getUserData();
                curMonster = (ChasingMonster) fa.getUserData();
            }
            else{
                curPlayer = (Player) fa.getUserData();
                curMonster = (ChasingMonster) fb.getUserData();
            }

            curPlayer.statCurrentHP -= curMonster.damageMonst;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}