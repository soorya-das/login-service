package ot.learning.loginservice.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import ot.learning.commonlibservice.contract.LoginRequest;
import ot.learning.commonlibservice.response.Response;
import ot.learning.loginservice.model.LoginUser;
import ot.learning.loginservice.util.JwtUtil;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final ot.learning.loginservice.service.LoginUserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Response<LoginRequest>> register(@RequestBody LoginRequest user) {
        return new ResponseEntity<>(
                Response.<LoginRequest>builder()
                        .payload(userService.register(user))
                        .message("SUCCESS")
                        .build(),
                HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Response<String>> login(@RequestBody LoginUser user) {
        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUserName(), user.getPassword()));

        LoginUser dbUser = userService.findByUsername(user.getUserName());
        String token = jwtUtil.generateToken(dbUser.getUserName(), dbUser.getRole());

        return new ResponseEntity<>(
                Response.<String>builder().payload(token).message("SUCCESS").build(),
                HttpStatus.OK);
    }
}
