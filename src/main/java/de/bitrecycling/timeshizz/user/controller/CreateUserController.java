package de.bitrecycling.timeshizz.user.controller;

import de.bitrecycling.timeshizz.security.service.UserService;
import de.bitrecycling.timeshizz.user.model.CreateUserRequest;
import de.bitrecycling.timeshizz.user.model.CreateUserResponse;
import de.bitrecycling.timeshizz.user.model.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class CreateUserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN') && hasPermission(#userRequest, 'client')")
    @PostMapping(consumes = "application/json")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest userRequest) {
        return userMapper.toJson(userService.createUser(userRequest.getName(), userRequest.getPassword(), userRequest.getEmail()));
    }
}
