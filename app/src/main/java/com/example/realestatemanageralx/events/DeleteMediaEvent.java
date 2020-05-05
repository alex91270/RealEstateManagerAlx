package com.example.realestatemanageralx.events;

/**
 * Event fired when a user removes a media
 */

public class DeleteMediaEvent {

    public String path;

    public DeleteMediaEvent(String path) {
        this.path = path;
    }
}
