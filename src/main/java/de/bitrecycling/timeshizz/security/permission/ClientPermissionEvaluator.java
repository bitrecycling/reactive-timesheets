package de.bitrecycling.timeshizz.security.permission;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientPermissionEvaluator implements PermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        System.out.println("has permission with 3");
        System.out.println(String.format("authentication: %s, targetDomainObject: %s, permission: %s", authentication, targetDomainObject, permission));
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        System.out.println("has permission with 4");
        System.out.println(String.format("authentication: %s, targetId: %s, targetType: %s, permission: %s", authentication, targetId, targetType, permission));
        return false;
    }
}