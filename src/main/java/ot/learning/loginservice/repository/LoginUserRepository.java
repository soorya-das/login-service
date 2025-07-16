package ot.learning.loginservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ot.learning.loginservice.model.LoginUser;

import java.util.Optional;

public interface LoginUserRepository extends JpaRepository<LoginUser, String> {
    Optional<LoginUser> findByUserName(String userName);
}
