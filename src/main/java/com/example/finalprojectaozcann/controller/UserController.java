package com.example.finalprojectaozcann.controller;

import com.example.finalprojectaozcann.model.request.CreateUserRequest;
import com.example.finalprojectaozcann.model.request.UpdateUserRequest;
import com.example.finalprojectaozcann.model.response.GenerateAdminUserResponse;
import com.example.finalprojectaozcann.model.response.GetUserResponse;
import com.example.finalprojectaozcann.service.UserService;
import com.example.finalprojectaozcann.validator.Validator;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final Validator<CreateUserRequest> createUserRequestValidator;
    private final Validator<Long> idValidator;
    private final UserService userService;

    /*
    If not exist any user in db. It will be created an admin user only one time.
     */
    @ApiOperation("generate admin user")
    @GetMapping(path = "/admin")
    public ResponseEntity<GenerateAdminUserResponse> generateAdminUser() {
        return ResponseEntity.ok(userService.generateAdminUser());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CreateUserRequest request, HttpServletRequest httpServletRequest) {
        createUserRequestValidator.validate(request);
        return ResponseEntity.ok(userService.create(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/all")
    public ResponseEntity<Collection<GetUserResponse>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable Long id) {
        idValidator.validate(id);
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GetUserResponse> update(@RequestBody UpdateUserRequest request,
                                                  @PathVariable Long id,
                                                  HttpServletRequest httpServletRequest) {
        idValidator.validate(id);
        return ResponseEntity.ok(userService.updateUser(request, id, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id,
                                            @RequestParam(name = "isHardDeleted", required = false) boolean isHardDeleted,
                                            HttpServletRequest httpServletRequest) {
        idValidator.validate(id);
        return ResponseEntity.ok(userService.deleteUserById(id, isHardDeleted, httpServletRequest));
    }

}
