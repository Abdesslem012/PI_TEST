package com.example.demo.util;

public class MaxParticipantsReachedException extends RuntimeException {
    public MaxParticipantsReachedException(String message) {
        super(message);
    }
}