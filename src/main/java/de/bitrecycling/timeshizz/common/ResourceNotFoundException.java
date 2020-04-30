package de.bitrecycling.timeshizz.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * shall be thrown if resource (with given id) is not found. used to produce a 404 response
 */
public class ResourceNotFoundException extends ResponseStatusException {
    public ResourceNotFoundException(String resourceName, UUID id) {
        super(HttpStatus.NOT_FOUND, String.format("%s [%s] not found", resourceName, id));
    }

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
