package com.openclassrooms.poseidon.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Entity not found");
    }
}
