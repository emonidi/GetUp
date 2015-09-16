package com.example.emonidi.getup;

/**
 * Created by emonidi on 16.9.2015 г..
 */
public class WalkingState implements StatesInterface {

    private int steps;

    @Override
    public void Walking() {
        System.out.print("Now walking");
    }

    @Override
    public void Sitting() {
        System.out.print("Not sitting");
    }

    @Override
    public void Notifying() {
        System.out.print("Not notifying");
    }
}
