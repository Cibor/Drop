package org.rloop;

import com.badlogic.gdx.physics.box2d.*;


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
        game.setScreen(new GameScreen(game)); //TODO: add something
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