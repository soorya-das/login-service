package ot.learning.loginservice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ot.learning.commonlibservice.contract.LoginRequest;
import ot.learning.commonlibservice.exception.RestException;
import ot.learning.loginservice.config.MapperConfig;
import ot.learning.loginservice.model.LoginUser;
import ot.learning.loginservice.repository.LoginUserRepository;

@Service
@RequiredArgsConstructor
public class LoginUserService {
    private final LoginUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final MapperConfig mapperConfig;

    public LoginRequest register(LoginRequest request) {
        repository
                .findByUserName(request.getUserName())
                .ifPresent(
                        loginUser -> {
                            throw new RestException("Username already exists");
                        });
        LoginUser user = mapperConfig.setModelMapper().map(request, LoginUser.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return request;
    }

    public LoginUser findByUsername(String username) {
        return repository.findByUserName(username).orElse(null);
    }
}
