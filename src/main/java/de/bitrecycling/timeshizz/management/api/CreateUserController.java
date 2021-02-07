package de.bitrecycling.timeshizz.management.api;

import de.bitrecycling.timeshizz.management.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(consumes = "application/json")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest userRequest) {

        return userMapper.toJson(
                userService.createUser(userRequest.getName(), userRequest.getPassword(), userRequest.getEmail()));
    }
}
