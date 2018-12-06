package de.bitrecycling.timeshizz.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResourceNotFoundException extends ResponseStatusException {
    public ResourceNotFoundException(String resourceName, String id){
        super(HttpStatus.NOT_FOUND, resourceName + " resource with id="+id+" not found");
    }
}
