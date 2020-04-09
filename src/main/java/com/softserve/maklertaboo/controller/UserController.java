package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.constant.HttpStatuses;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.dto.user.UserUpdateDto;
import com.softserve.maklertaboo.security.dto.JWTSuccessLogIn;
import com.softserve.maklertaboo.security.dto.JwtTokensDto;
import com.softserve.maklertaboo.security.dto.LoginDto;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import com.softserve.maklertaboo.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor with parameters
     *
     * @author Vadym Puiko
     */
    @Autowired
    public UserController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JWTTokenProvider jwtTokenProvider,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * The method which save of new user.
     *
     * @param userDto to save
     * @return {@link ResponseEntity} with {@link UserDto}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Save new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = HttpStatuses.CREATED),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    /**
     * The method which return list of users by page.
     *
     * @param page,size {@link Integer} for pageable configuration.
     * @return {@link ResponseEntity} with page of {@link UserDto}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Get all users by page")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @GetMapping("/admin/all")
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findByPage(pageable));
    }

    /**
     * The method which return list of users by provided search criteria (username).
     *
     * @param username URL string with search criteria
     * @return {@link ResponseEntity} with page of {@link UserDto}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Find all users by search criteria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @GetMapping("/search/username")
    public ResponseEntity<Page<UserDto>> searchUserByUsername(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                              @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                              @RequestParam(value = "username") String username) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.searchUserByUsername(pageable, username));
    }

    /**
     * The method which return list of users by provided search criteria (email).
     *
     * @param email URL string with search criteria
     * @return {@link ResponseEntity} with page of {@link UserDto}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Find all users by search criteria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @GetMapping("/search/email")
    public ResponseEntity<Page<UserDto>> searchUserByEmail(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                           @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                           @RequestParam(value = "email") String email) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.searchUserByEmail(pageable, email));
    }

    /**
     * The method which return list of users by provided search criteria (phone).
     *
     * @param phone URL string with search criteria
     * @return {@link ResponseEntity} with page of {@link UserDto}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Find all users by search criteria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @GetMapping("/search/phone")
    public ResponseEntity<Page<UserDto>> searchUserByPhoneNumber(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                                 @RequestParam(value = "phone") String phone) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.searchUserByPhone(pageable, phone));
    }

    /**
     * The method which update of user.
     *
     * @param userUpdateDto for updating
     * @return {@link ResponseEntity} with {@link UserDto}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Update User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @PutMapping("/update/all")
    public ResponseEntity<UserUpdateDto> updateUser(@RequestBody @Valid UserUpdateDto userUpdateDto,
                                                    @RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        userService.updateUser(email, userUpdateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * The method which update of user into admin panel.
     *
     * @param userUpdateDto for updating
     * @return {@link ResponseEntity} with {@link UserDto}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Update User into Admin Panel")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @PutMapping("/admin/update")
    public ResponseEntity<UserUpdateDto> updateUserAdmin(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        userService.updateUserIntoAdminPanel(userUpdateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * The method for delete user by ID.
     *
     * @param id for deleting
     * @return {@link ResponseEntity} with {@link Long}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * The method for updating profile picture
     *
     * @param file to save
     * @return {@link ResponseEntity}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Updating photo in user profile")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST)
    })
    @PutMapping("/profile/update/photo")
    public ResponseEntity<Object> updateUserPhoto(@RequestPart(value = "file") MultipartFile file,
                                                  @RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        userService.updatePhoto(file, email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * The method for deleting profile picture
     *
     * @return {@link ResponseEntity}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Deleting photo in user profile")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST)
    })
    @DeleteMapping("/profile/delete/photo")
    public ResponseEntity<Object> deletePhoto(@RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        userService.deletePhoto(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Get {@link UserDto} by email from access token.
     *
     * @return {@link UserDto}.
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Get User dto by email from access token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @GetMapping("/currentUser")
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByEmail(email));
    }

    /**
     * Get {@link Long} id by email from access token.
     *
     * @return {@link Long}.
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Get Long by email from access token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @GetMapping("/currentUserId")
    public ResponseEntity<Long> getCurrentUserById(@RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByEmail(email).getId());
    }

    /**
     * The method set email and password of user for full authentication
     *
     * @param loginDto {@link LoginDto}
     * @param response {@link HttpServletResponse}
     * @return {@link ResponseEntity}
     * @author Mike Ostapiuk
     */
    @ApiOperation("SignIn for full authentication of user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST)
    })
    @PostMapping("/signIn")
    public ResponseEntity<JWTSuccessLogIn> signIn(@Valid @RequestBody LoginDto loginDto,
                                                  HttpServletResponse response) {
        JWTSuccessLogIn jwtSuccessLogIn = userService.validateLogin(loginDto);
        userService.comparePasswordLogin(loginDto, passwordEncoder);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.addHeader("accesstoken", jwtTokenProvider.generateAccessToken(authentication));
        response.addHeader("refreshtoken", jwtTokenProvider.generateRefreshToken(authentication));
        return ResponseEntity.ok(jwtSuccessLogIn);
    }

    /**
     * The method updating access token by refreshKey token
     *
     * @param refreshToken {@link String}
     * @param response {@link HttpServletResponse}
     * @return {@link ResponseEntity}
     * @author Mike Ostapiuk
     */
    @ApiOperation("Updating access token by refreshKey token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Refresh token is not valid")
    })
    @GetMapping("/refreshTokens")
    public ResponseEntity updateAccessToken(@RequestParam @NotBlank String refreshToken,
                                            HttpServletResponse response) {
        JwtTokensDto newTokens = userService.updateAccessTokens(refreshToken);
        response.addHeader("accesstoken", newTokens.getAccesstoken());
        response.addHeader("refreshtoken", newTokens.getRefreshtoken());
        return ResponseEntity.ok().build();
    }

    /**
     * The method which return user by ID.
     *
     * @param id {@link Long}.
     * @return {@link ResponseEntity} with {@link UserDto}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(id));
    }

}

