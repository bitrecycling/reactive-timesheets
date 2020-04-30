package de.bitrecycling.timeshizz.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * shall be thrown if resource (with given id) is not found. used to produce a 404 response
 */
public class ResourceNotFoundException extends ResponseStatusException {
    public ResourceNotFoundException(String resourceName, String id){
        super(HttpStatus.NOT_FOUND, resourceName + " resource with id="+id+" not found");
    }
    public ResourceNotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }
}
