package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Created"),
//            @ApiResponse(code = 400, message = "User already exists")
//    })
    @PostMapping
    public void createUser(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto);
    }

//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "OK"),
//            @ApiResponse(code = 400, message = "Bad request")
//    })

    @PostMapping("/signIn")
    public void signIn (@Valid @RequestBody UserDto userDto, HttpServletResponse response){
        String token = userService.signIn(userDto);
        response.addHeader("token", token);
    }

    @GetMapping("/all")
    public List<UserDto> getAllUser(HttpRequest request) {
        return userService.findAllUser();
    }

    @GetMapping("/all/{page}")
    public Page<UserDto> getAllUser(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 3);
        return userService.findByPage(pageable);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/username/{username}")
    public UserDto getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/exists/email/{email}")
    public Boolean emailExists(@PathVariable String email) {
        return userService.emailExists(email);
    }

    @PutMapping("/update/all")
    public void updateUser(Long id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @PutMapping("/update/{photo}")
    public void updateUserPhoto(Long id, @PathVariable String photo) {
        userService.updateUserPhoto(id, photo);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

