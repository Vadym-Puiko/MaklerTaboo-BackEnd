package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.user.JwtTokensDto;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.security.dto.JWTSuccessLogIn;
import com.softserve.maklertaboo.security.dto.LoginDto;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import com.softserve.maklertaboo.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTTokenProvider jwtTokenProvider;


    @PostMapping("/create")
    public void createUser(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto);
    }

    @PostMapping("/signIn")
    public ResponseEntity<JWTSuccessLogIn> signIn(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        JWTSuccessLogIn jwtSuccessLogIn = userService.validateLogin(loginDto);
        userService.comparePasswordLogin(loginDto, passwordEncoder);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.addHeader("accessToken", jwtTokenProvider.generateAccessToken(authentication));
        response.addHeader("refreshToken", jwtTokenProvider.generateRefreshToken(authentication));
        return ResponseEntity.ok(jwtSuccessLogIn);
    }

    @ApiOperation("Updating access token by refresh token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Refresh token is not valid")
    })
    @GetMapping("/refreshTokens")
    public ResponseEntity updateAccessToken(@RequestParam @NotBlank String refreshToken,
                                                             HttpServletResponse response) {
        JwtTokensDto newTokens = userService.updateAccessTokens(refreshToken);
        response.addHeader("accessToken", newTokens.getAccessToken());
        response.addHeader("refreshToken", newTokens.getRefreshToken());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public List<UserDto> getAllUser(HttpRequest request) {
        return userService.findAllUser();
    }

    @GetMapping("/all/{page}/{size}")
    public Page<UserDto> getAllUser(@PathVariable Integer page, @PathVariable Integer size) {
        Pageable pageable = PageRequest.of(page, size);
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
    public void updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto.getId(), userDto);
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

