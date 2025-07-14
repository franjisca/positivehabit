package com.side.positivehabbit.repository.user;

import com.side.positivehabbit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String email);


    User findUserByEmailUsingQuerydsl(String email);
}
