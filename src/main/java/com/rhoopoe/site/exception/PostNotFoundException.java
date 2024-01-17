package com.rhoopoe.site.exception;

import java.util.UUID;

public class PostNotFoundException extends Exception {
    public  PostNotFoundException(UUID postId) {
        super("Post " + postId + " not found");
    }
}
