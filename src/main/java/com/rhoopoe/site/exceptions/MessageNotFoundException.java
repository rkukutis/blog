package com.rhoopoe.site.exceptions;

import java.util.UUID;

public class MessageNotFoundException extends Exception {
    public MessageNotFoundException(String message) {
        super(message);
    }
}
