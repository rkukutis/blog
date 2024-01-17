package com.rhoopoe.site.exceptions;

import java.util.UUID;

public class PostNotFoundException extends Exception {
    public  PostNotFoundException(UUID postId) {
        super("Post " + postId + " not found");
    }
}
