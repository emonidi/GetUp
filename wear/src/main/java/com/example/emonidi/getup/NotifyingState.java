package com.example.emonidi.getup;

/**
 * Created by emonidi on 16.9.2015 г..
 */
public class NotifyingState implements StatesInterface {
    @Override
    public void Walking() {
        System.out.print("Not walking");
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

