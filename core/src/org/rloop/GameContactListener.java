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
            System.out.println(1);
            contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}